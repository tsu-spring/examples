<#import "layout.ftlh" as layout>
<#import "components.ftlh" as components>

<@layout.page title="Home">
    <section class="search-section">
        <input type="text" placeholder="Search movies..." id="searchInput">
    </section>

    <h2>All Movies</h2>
    <div class="grid" id="movieGrid">
        <#list movies>
            <#items as movie>
                <@components.movieCard movie.id movie.title movie.releaseYear movie.rating movie.posterName/>
            </#items>
        <#else>
            <h3>No movies yet.</h3>
        </#list>
    </div>
</@layout.page>
