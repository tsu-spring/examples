<#import "/spring.ftl" as spring>

<#macro page title="Home">
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title} | ${appTitle}</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
              rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="stylesheet" href="<@spring.url relativeUrl='/css/bookinist.css'/>">
    </head>
    <body class="d-flex flex-column min-vh-100">

    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container">
            <a class="navbar-brand" href="<@spring.url relativeUrl='/'/>"><i class="fa-solid fa-book-open me-2"></i>${appTitle}</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="<@spring.url relativeUrl='/'/>">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<@spring.url relativeUrl='/about'/>">About Us</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<@spring.url relativeUrl='/contact'/>">Contact Us</a>
                    </li>
                </ul>
                <a href="<@spring.url '/book/new'/>" class="btn btn-success me-3">
                    <i class="fa-solid fa-plus me-1"></i>Add Book
                </a>
                <form class="d-flex" role="search" method="post" action="<@spring.url '/search'/>">
                    <div class="input-group">
                        <input type="text" class="form-control" name="query" placeholder="Search using book's title"/>
                        <button class="btn btn-outline-success" type="submit"><i class="fa-solid fa-magnifying-glass me-1"></i>Search</button>
                    </div>
                </form>
            </div>
        </div>
    </nav>

    <main class="container flex-grow-1">
        <#nested />
    </main>

    <footer class="mt-auto py-3 bg-body-tertiary">
        <div class="container d-flex flex-column flex-md-row justify-content-center align-items-center">
            <span class="text-body-secondary">&copy; 2026 ${appTitle}. All rights reserved.</span>
        </div>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
            crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>
