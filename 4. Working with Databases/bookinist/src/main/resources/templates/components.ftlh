<#import "/spring.ftl" as spring>

<#macro starRating rating="">
    <#if rating?is_number>
        <div class="mb-2">
            <#assign fullStars = rating?floor>
            <#assign hasHalf = (rating - fullStars >= 0.5)>
            <#list 1..5 as i>
                <#if (i <= fullStars)>
                    <i class="fa-solid fa-star text-warning"></i>
                <#elseif hasHalf && i == fullStars + 1>
                    <i class="fa-solid fa-star-half-stroke text-warning"></i>
                <#else>
                    <i class="fa-regular fa-star text-warning"></i>
                </#if>
            </#list>
            <small class="text-muted ms-1">${rating}</small>
        </div>
    <#else>
        <div class="mb-2">
            <small class="text-muted">No rating yet</small>
        </div>
    </#if>
</#macro>

<#macro bookCard book>
    <div class="col">
        <div class="card h-100 shadow-sm">
            <div class="row g-0 h-100">
                <div class="col-4">
                    <a href="<@spring.url '/book/${book.id}'/>">
                        <#if book.coverImage??>
                            <img src="<@spring.url '/image/cover/${book.id}'/>" class="img-fluid rounded-start h-100 object-fit-cover" alt="${book.title}">
                        <#else>
                            <img src="${book.coverUrlMedium!''}" class="img-fluid rounded-start h-100 object-fit-cover" alt="${book.title}">
                        </#if>
                    </a>
                </div>
                <div class="col-8 d-flex flex-column">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title mb-1">
                            <a href="<@spring.url '/book/${book.id}'/>" class="text-decoration-none text-dark">${book.title}</a>
                        </h5>
                        <p class="text-muted mb-2">
                            <a href="<@spring.url '/author/${book.author.id}'/>" class="text-muted text-decoration-none">${book.author.name}</a>
                        </p>
                        <div class="mb-2">
                            <#list book.genres as genre>
                                <span class="badge bg-primary-subtle text-primary-emphasis me-1">${genre.name}</span>
                            </#list>
                        </div>
                        <div class="mt-auto">
                            <@starRating rating=book.rating!/>
                            <ul class="list-unstyled mb-0 small text-muted">
                                <li><strong>Year:</strong> ${book.publishedYear}</li>
                                <li><strong>ISBN:</strong> ${book.isbn}</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>

<#macro pagination bookPage extraParams="">
    <#if (bookPage.totalPages > 1)>
        <#assign current = bookPage.number>
        <#assign last = bookPage.totalPages - 1>
        <#assign params = extraParams>
        <#if params?has_content><#assign params = params + "&"></#if>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item <#if !bookPage.hasPrevious()>disabled</#if>">
                    <a class="page-link" href="?${params}page=${current - 1}&size=${bookPage.size}">
                        <i class="fa-solid fa-chevron-left"></i>
                    </a>
                </li>

                <li class="page-item <#if current == 0>active</#if>">
                    <a class="page-link" href="?${params}page=0&size=${bookPage.size}">1</a>
                </li>

                <#if (current > 2)>
                    <li class="page-item disabled"><span class="page-link">...</span></li>
                </#if>

                <#list 0..<bookPage.totalPages as i>
                    <#if (i > 0) && (i < last) && (i >= current - 1) && (i <= current + 1)>
                        <li class="page-item <#if i == current>active</#if>">
                            <a class="page-link" href="?${params}page=${i}&size=${bookPage.size}">${i + 1}</a>
                        </li>
                    </#if>
                </#list>

                <#if (current < last - 2)>
                    <li class="page-item disabled"><span class="page-link">...</span></li>
                </#if>

                <#if (last > 0)>
                    <li class="page-item <#if current == last>active</#if>">
                        <a class="page-link" href="?${params}page=${last}&size=${bookPage.size}">${last + 1}</a>
                    </li>
                </#if>

                <li class="page-item <#if !bookPage.hasNext()>disabled</#if>">
                    <a class="page-link" href="?${params}page=${current + 1}&size=${bookPage.size}">
                        <i class="fa-solid fa-chevron-right"></i>
                    </a>
                </li>
            </ul>
        </nav>
    </#if>
</#macro>

<#macro fieldErrors path>
    <@spring.bind path/>
    <#list spring.status.errorMessages as error>
        <div class="error-msg">
            <i class="fa-solid fa-circle-exclamation"></i>
            <span>${error}</span>
        </div>
    </#list>
</#macro>

<#macro formGroup path id label>
    <@spring.bind path/>
    <div class="form-group <#if spring.status.errorMessages?has_content>error</#if>">
        <label for="${id}">${label}</label>
        <#nested />
        <@fieldErrors path/>
    </div>
</#macro>
