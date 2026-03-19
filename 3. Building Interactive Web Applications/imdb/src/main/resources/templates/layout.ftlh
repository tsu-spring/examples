<#import "/spring.ftl" as spring>

<#macro page title="IMDB">
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title} | Moviedb</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
              rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="stylesheet" href="<@spring.url relativeUrl='/css/imdb.css'/>">
    </head>
    <body>

    <nav class="navbar">
        <div class="inner">
            <a href="<@spring.url relativeUrl='/'/>" class="logo">Movie<span>db</span></a>
            <a href="<@spring.url relativeUrl='/add-movie'/>" class="btn-add">Add Movie</a>
        </div>
    </nav>

    <main>
        <#nested />
    </main>

    <footer>&copy; 2024 Moviedb. Demo database.</footer>
    </body>
    </html>
</#macro>
