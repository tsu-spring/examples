<#import "layout.ftlh" as layout>
<#import "components.ftlh" as components>

<@layout.page title="Search Results">
    <div class="my-4">
        <h4><i class="fa-solid fa-magnifying-glass me-2"></i>Search results for: <em>"${query}"</em></h4>
        <p class="text-muted">${bookPage.totalElements} book(s) found</p>
    </div>

    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 my-2">
        <#list bookPage.content>
            <#items as book>
                <@components.bookCard book=book/>
            </#items>
        <#else>
            <h5 class="text-muted">No books found matching your search.</h5>
        </#list>
    </div>

    <@components.pagination bookPage=bookPage extraParams="query=${query}"/>
</@layout.page>
