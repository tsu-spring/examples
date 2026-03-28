<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title=book.title>
    <div class="row my-4 g-4">
        <div class="col-md-4 col-lg-3">
            <#if book.coverImage??>
                <img src="<@spring.url '/image/cover/${book.id}'/>" class="img-fluid rounded shadow" alt="${book.title}">
            <#else>
                <img src="${book.coverUrlLarge!''}" class="img-fluid rounded shadow" alt="${book.title}">
            </#if>
        </div>
        <div class="col-md-8 col-lg-9">
            <h1 class="mb-2">${book.title}</h1>
            <h5 class="text-muted mb-3">
                <i class="fa-solid fa-pen-nib me-2"></i>
                <a href="<@spring.url '/author/${book.author.id}'/>" class="text-muted">${book.author.name}</a>
            </h5>

            <#if book.rating??>
                <div class="mb-3">
                    <#assign fullStars = book.rating?floor>
                    <#assign hasHalf = (book.rating - fullStars >= 0.5)>
                    <#list 1..5 as i>
                        <#if (i <= fullStars)>
                            <i class="fa-solid fa-star text-warning fs-5"></i>
                        <#elseif hasHalf && i == fullStars + 1>
                            <i class="fa-solid fa-star-half-stroke text-warning fs-5"></i>
                        <#else>
                            <i class="fa-regular fa-star text-warning fs-5"></i>
                        </#if>
                    </#list>
                    <span class="ms-2 fs-5 fw-semibold">${book.rating}</span>
                    <span class="text-muted">/ 5</span>
                </div>
            <#else>
                <div class="mb-3">
                    <span class="text-muted"><i class="fa-regular fa-star me-1"></i>No rating yet</span>
                </div>
            </#if>

            <div class="mb-3">
                <#list book.genres as genre>
                    <span class="badge bg-primary-subtle text-primary-emphasis me-1 fs-6">${genre.name}</span>
                </#list>
            </div>

            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title mb-3"><i class="fa-solid fa-circle-info me-2"></i>Book Details</h5>
                    <div class="row">
                        <div class="col-sm-6">
                            <ul class="list-unstyled">
                                <li class="mb-2">
                                    <i class="fa-solid fa-calendar-days text-muted me-2"></i>
                                    <strong>Published:</strong> ${book.publishedYear}
                                </li>
                                <li class="mb-2">
                                    <i class="fa-solid fa-barcode text-muted me-2"></i>
                                    <strong>ISBN:</strong> ${book.isbn}
                                </li>
                            </ul>
                        </div>
                        <div class="col-sm-6">
                            <ul class="list-unstyled">
                                <li class="mb-2">
                                    <i class="fa-solid fa-clock text-muted me-2"></i>
                                    <strong>Added:</strong>
                                    <#if book.createdAt??>
                                        ${book.createdAt?keep_before("T")}
                                    <#else>
                                        N/A
                                    </#if>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <a href="<@spring.url '/'/>" class="btn btn-outline-secondary mt-3">
                <i class="fa-solid fa-arrow-left me-1"></i>Back to Books
            </a>
        </div>
    </div>
</@layout.page>
