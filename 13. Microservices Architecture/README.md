# Chapter 13 — Calculator Microservices Example

A runnable implementation of the calculator system from
[`chapter-13-microservices-architecture.md`](./chapter-13-microservices-architecture.md),
built with **Spring Boot 4.0.4** and **Spring Cloud 2025.1.0**.

## Modules

| Module | Port | Role |
|--------|------|------|
| `config-server` | 8888 | Spring Cloud Config Server (native backend, serves `config-repo/`) |
| `discovery-server` | 8761 | Eureka service registry |
| `calculator-service` | 8081 | Arithmetic microservice; Eureka + Config client |
| `math-facade-service` | 8082 | Facade; calls calculator via discovery + load balancer, with a Resilience4j circuit breaker |
| `api-gateway` | 8080 | Spring Cloud Gateway — single entry point |

## Build

```bash
# from this directory, build each module
mvn -f config-server/pom.xml clean package -DskipTests
mvn -f discovery-server/pom.xml clean package -DskipTests
mvn -f calculator-service/pom.xml clean package -DskipTests
mvn -f math-facade-service/pom.xml clean package -DskipTests
mvn -f api-gateway/pom.xml clean package -DskipTests
```

## Run (in this order)

```bash
java -jar config-server/target/config-server-0.0.1-SNAPSHOT.jar
java -jar discovery-server/target/discovery-server-0.0.1-SNAPSHOT.jar
java -jar calculator-service/target/calculator-service-0.0.1-SNAPSHOT.jar
java -jar math-facade-service/target/math-facade-service-0.0.1-SNAPSHOT.jar
java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar
```

## Try it

```bash
# Eureka dashboard
http://localhost:8761

# Calculator directly
http://localhost:8081/math/add?a=10&b=25          # -> 35
http://localhost:8081/math/greeting               # value served by the Config Server

# Facade (calls calculator through service discovery)
http://localhost:8082/api/calculate?a=10&b=25     # -> Result from calculator-service: 10 add 25 = 35
http://localhost:8082/api/history

# Everything through the API Gateway (single entry point, port 8080)
http://localhost:8080/math/add?a=7&b=3
http://localhost:8080/api/calculate?a=100&b=50&op=multiply
```

Stop `calculator-service` and call the facade again to see the Resilience4j
circuit breaker return its fallback instead of a 500.

## Notes — where this deviates from the chapter snippets

The chapter text predates the exact artifact/version details; these three
adjustments were needed to make it build and run on Spring Cloud 2025.1.0:

1. **Gateway starter.** `spring-cloud-starter-gateway` no longer exists in
   Gateway 5.0 — it was split into webflux/webmvc variants. This example uses
   `spring-cloud-starter-gateway-server-webflux`, and routes live under
   `spring.cloud.gateway.server.webflux.routes` (not `spring.cloud.gateway.routes`).

2. **Load-balanced `RestClient.Builder`.** In Spring Cloud 2025.1 the Eureka
   client itself uses `RestClient`. A single `@LoadBalanced` builder would be
   picked up by Eureka, which would then try to load-balance `localhost:8761`
   and fail to register. `RestClientConfig` therefore provides a plain builder
   for infrastructure and a separate `@Bean(defaultCandidate = false)`
   `@LoadBalanced` builder used only for the calculator call.

3. **Circuit breaker aspect.** The `@CircuitBreaker` annotation needs the
   AspectJ weaver. Spring Boot 4 dropped `spring-boot-starter-aop`, so
   `math-facade-service` depends on `org.aspectj:aspectjweaver` directly.

4. **Config Server backend.** The chapter shows a Git backend; this example
   uses the `native` backend (`config-repo/`) so it runs offline.
