<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<#import "components.ftlh" as components>

<@layout.page title="Add Movie">
    <div class="form-container">
        <h1>Add New Movie</h1>
        <form id="movieForm" action="<@spring.url '/add-movie'/>" method="post" enctype="multipart/form-data">

            <@components.formGroup "movie.title" "title" "Title">
                <@spring.formInput "movie.title" 'id="title" placeholder="Enter movie title"'/>
            </@components.formGroup>

            <@components.formGroup "movie.description" "description" "Description">
                <@spring.formTextarea "movie.description" 'id="description" rows="5" placeholder="Brief plot summary..."'/>
            </@components.formGroup>

            <div class="row">
                <@components.formGroup "movie.releaseYear" "releaseYear" "Release Year">
                    <@spring.formInput "movie.releaseYear" 'id="releaseYear" placeholder="2026"' "number"/>
                </@components.formGroup>

                <@components.formGroup "movie.genre" "genre" "Genre">
                    <@spring.formSingleSelect "movie.genre" genres 'id="genre"'/>
                </@components.formGroup>
            </div>

            <@components.formGroup "movie.director" "director" "Director">
                <@spring.formInput "movie.director" 'id="director" placeholder="Director name"'/>
            </@components.formGroup>

            <@components.formGroup "movie.actors" "actors" "Actors">
                <@spring.formInput "movie.actors" 'id="actors" placeholder="Separate with commas (e.g. Tom Hanks, Tim Allen)"'/>
            </@components.formGroup>

            <@components.formGroup "movie.poster" "poster" "Poster Image">
                <@spring.formInput "movie.poster" 'id="poster" accept="image/*"' "file"/>
            </@components.formGroup>

            <button type="submit" class="btn-submit">Save Movie</button>

            <#if savedMovie??>
                <div class="alert-success">
                    <i class="fa-solid fa-circle-check"></i> Successfully created movie
                    <a href="<@spring.url '/movie/${savedMovie.id}'/>">${savedMovie.title}</a>
                </div>
            </#if>
        </form>
    </div>
</@layout.page>
