<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title=movie.title>
    <!-- Hero Section -->
    <div class="movie-hero">
        <div class="movie-hero-inner">
            <div class="movie-poster">
                <img src="<@spring.url '/poster/${movie.posterName}'/>" alt="${movie.title}">
            </div>
            <div class="movie-details">
                <div class="movie-meta">
                    <#if movie.rating??>
                        <span class="movie-rating"><i class="fa-solid fa-star"></i> ${movie.rating}</span>
                    </#if>
                    <span>${movie.releaseYear}</span>
                </div>
                <h1>${movie.title}</h1>
                <div class="movie-genres">
                    <span>${movie.genre}</span>
                </div>
            </div>
        </div>
    </div>

    <!-- Content Section -->
    <div class="movie-info-section">
        <div class="movie-main-content">
            <h2>Overview</h2>
            <p>${movie.description}</p>

            <#if movie.actors?? && movie.actors?size gt 0>
                <h2>Cast</h2>
                <div class="movie-cast-list">
                    <#list movie.actors as actor>
                        <span class="movie-cast-tag">${actor}</span>
                    </#list>
                </div>
            </#if>
        </div>

        <div class="movie-sidebar">
            <h3>Movie Info</h3>
            <div class="movie-info-row">
                <span>Director</span>
                <span>${movie.director}</span>
            </div>
            <div class="movie-info-row">
                <span>Year</span>
                <span>${movie.releaseYear}</span>
            </div>
            <div class="movie-info-row">
                <span>Genre</span>
                <span>${movie.genre}</span>
            </div>
        </div>
    </div>
</@layout.page>
