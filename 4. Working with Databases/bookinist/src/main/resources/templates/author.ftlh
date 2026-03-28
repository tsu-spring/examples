<#import "layout.ftlh" as layout>
<#import "components.ftlh" as components>
<#import "/spring.ftl" as spring>

<@layout.page title=author.name>
    <div class="row my-4 g-4">
        <div class="col-md-3">
            <#if author.photoUrl??>
                <img src="${author.photoUrl}" class="img-fluid rounded shadow" alt="${author.name}">
            <#else>
                <div class="bg-light rounded shadow d-flex align-items-center justify-content-center" style="height: 300px;">
                    <i class="fa-solid fa-user fa-5x text-muted"></i>
                </div>
            </#if>
        </div>
        <div class="col-md-9">
            <h1 class="mb-2"><i class="fa-solid fa-pen-nib me-2"></i>${author.name}</h1>
            <#if author.bio?? && author.bio?has_content>
                <p class="lead text-muted">${author.bio}</p>
            </#if>

            <h4 class="mt-4 mb-3">
                <i class="fa-solid fa-book me-2"></i>Books by ${author.name}
                <span class="badge bg-secondary ms-2">${books?size}</span>
            </h4>
        </div>
    </div>

    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 my-2">
        <#list books as book>
            <@components.bookCard book=book/>
        </#list>
    </div>

    <a href="<@spring.url '/'/>" class="btn btn-outline-secondary mt-3 mb-4">
        <i class="fa-solid fa-arrow-left me-1"></i>Back to Books
    </a>
</@layout.page>
