<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title="Server Error">
    <div class="d-flex flex-column align-items-center justify-content-center text-center py-5">
        <h1 class="display-1 fw-bold text-danger">500</h1>
        <h2 class="mb-3">Something Went Wrong</h2>
        <p class="text-muted mb-4 col-md-6">${message!"An unexpected error occurred. Please try again later."}</p>
        <a href="<@spring.url '/'/>" class="btn btn-primary">Back to Home</a>
    </div>
</@layout.page>
