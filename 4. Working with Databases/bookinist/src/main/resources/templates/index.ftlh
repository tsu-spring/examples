<#import "layout.ftlh" as layout>
<#import "components.ftlh" as components>

<@layout.page title="Home">
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 my-4">
        <#list bookPage.content>
            <#items as book>
                <@components.bookCard book=book/>
            </#items>
        <#else>
            <h3>No books yet.</h3>
        </#list>
    </div>

    <@components.pagination bookPage=bookPage/>
</@layout.page>
