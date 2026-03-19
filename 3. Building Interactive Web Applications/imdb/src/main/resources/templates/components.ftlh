<#import "/spring.ftl" as spring>

<#macro movieCard id title releaseYear rating="" posterName="#">
    <a href="<@spring.url '/movie/${id}'/>" class="card">
        <img src="<@spring.url '/poster/${posterName}'/>" alt="${title}">
        <div class="info">
            <h3>${title}</h3>
            <div class="meta">
                <span>${releaseYear}</span>
                <#if rating?has_content>
                    <span class="rating">${rating}</span>
                </#if>
            </div>
        </div>
    </a>
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
