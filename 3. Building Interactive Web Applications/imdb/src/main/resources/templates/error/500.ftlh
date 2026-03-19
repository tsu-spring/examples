<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title="Server Error">
    <div class="error-page">
        <h1>500</h1>
        <p>${message!"Something went wrong. Please try again later."}</p>
        <a href="<@spring.url '/'/>" class="btn-back">Back to Home</a>
    </div>
</@layout.page>
