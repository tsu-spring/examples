<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title="Not Found">
    <div class="error-page">
        <h1>404</h1>
        <p>${message!"The page you're looking for doesn't exist."}</p>
        <a href="<@spring.url '/'/>" class="btn-back">Back to Home</a>
    </div>
</@layout.page>
