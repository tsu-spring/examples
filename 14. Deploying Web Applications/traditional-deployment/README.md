# Traditional WAR Deployment

კლასიკურად, ცალკე ვებ სერვერზე გასაშვებად, ჩვენს Spring Boot აპლიკაციაში უნდა ვქნათ შემდეგი 3 რამ:

### 1. Project Packaging Type

POM-ში პროექტის პაკეტირების ტიპი უნდა მივუთითოთ `war`:

```xml
<packaging>war</packaging>
```

### 2. Tomcat as Provided Dependency

Tomcat-ის სტარტერ პროექტი (რომელიც გვთავაზობდა Embedded ვარიანტს) უნდა გახდეს `provided` ტიპის:

```xml
<dependencies>
    <!-- … -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
    <!-- … -->
</dependencies>
```

### 3. SpringBootServletInitializer Subclass and Configure Method

უნდა აღვწეროთ კონფიგურაციის (კომპონენტ) კლასი, რომელიც გააფართოებს SpringBootServletInitializer კლასს და გადაფარავს
configure() მეთოდს:

```java
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

# Deployment

ამ ყველაფრის მერე უშვებთ `mvn clean package` ბრძანებას და `target` საქაღალდეში არსებულ `*.war` ფაილი გადაგაქვთ ვებ
სერვერის შესაბამის საქაღალდეში. ძირითადად ეს არის `webapps` საქაღალდე (Tomcat-ის შემთხვევაში), და შემდეგ უშვებთ
`bin\startup.bat` სკრიპტს, რომელიც "სტარტავს" ვებ სერვერს.

ასევე, შეგიძლიათ ტომკატის სერვერზე თქვენი ვებ პროექტის გაშვება ინტელიჯეიდანაც (ფასიან ვერსიას აქვს [**Run on Tomcat**]
გაშვების კონფიგურაცია).

პ.ს. აუცილებლად მიაქციეთ ყურადღება სისტემაში დაყენებული JDK-ისა და Spring Boot-ის ვერსიებს. წესით, ყველაზე ბოლო ვერსიის
ტომკატის სერვერზე ყველაფერი ნორმალურად გაეშვება.
