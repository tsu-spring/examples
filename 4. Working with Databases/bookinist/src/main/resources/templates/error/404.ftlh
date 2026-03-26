<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title="Not Found">
    <div class="d-flex flex-column align-items-center justify-content-center text-center py-5">
        <h1 class="display-1 fw-bold text-muted">404</h1>
        <h2 class="mb-3">Page Not Found</h2>
        <p class="text-muted mb-4 col-md-6">${message!"The page you're looking for doesn't exist."}</p>
        <a href="<@spring.url '/'/>" class="btn btn-primary">Back to Home</a>
    </div>
</@layout.page>
