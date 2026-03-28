<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title="Add Book">
    <div class="row my-4 justify-content-center">
        <div class="col-lg-8">
            <h2 class="mb-4"><i class="fa-solid fa-plus me-2"></i>Add New Book</h2>

            <form method="post" action="<@spring.url '/book/new'/>" enctype="multipart/form-data" novalidate>


                <@spring.bind "bookForm.title"/>
                <div class="mb-3">
                    <label for="title" class="form-label">Title <span class="text-danger">*</span></label>
                    <input type="text" class="form-control <#if spring.status.errorMessages?has_content>is-invalid</#if>"
                           id="title" name="${spring.status.expression}" value="${spring.status.value!''}"
                           placeholder="Enter book title">
                    <#list spring.status.errorMessages as error>
                        <div class="invalid-feedback">
                            <i class="fa-solid fa-circle-exclamation me-1"></i>${error}
                        </div>
                    </#list>
                </div>


                <@spring.bind "bookForm.authorId"/>
                <div class="mb-3">
                    <label for="authorId" class="form-label">Author <span class="text-danger">*</span></label>
                    <select class="form-select <#if spring.status.errorMessages?has_content>is-invalid</#if>"
                            id="authorId" name="${spring.status.expression}">
                        <option value="">Select an author</option>
                        <#list authors as author>
                            <option value="${author.id}" <#if (spring.status.value!'') == author.id?string>selected</#if>>${author.name}</option>
                        </#list>
                    </select>
                    <#list spring.status.errorMessages as error>
                        <div class="invalid-feedback">
                            <i class="fa-solid fa-circle-exclamation me-1"></i>${error}
                        </div>
                    </#list>
                </div>

                <div class="row mb-3">

                    <@spring.bind "bookForm.publishedYear"/>
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="publishedYear" class="form-label">Published Year <span class="text-danger">*</span></label>
                        <input type="number" class="form-control <#if spring.status.errorMessages?has_content>is-invalid</#if>"
                               id="publishedYear" name="${spring.status.expression}" value="${spring.status.value!''}"
                               placeholder="e.g. 2024">
                        <#list spring.status.errorMessages as error>
                            <div class="invalid-feedback">
                                <i class="fa-solid fa-circle-exclamation me-1"></i>${error}
                            </div>
                        </#list>
                    </div>


                    <@spring.bind "bookForm.isbn"/>
                    <div class="col-md-6">
                        <label for="isbn" class="form-label">ISBN <span class="text-danger">*</span></label>
                        <input type="text" class="form-control <#if spring.status.errorMessages?has_content>is-invalid</#if>"
                               id="isbn" name="${spring.status.expression}" value="${spring.status.value!''}"
                               placeholder="e.g. 9780141439518">
                        <#list spring.status.errorMessages as error>
                            <div class="invalid-feedback">
                                <i class="fa-solid fa-circle-exclamation me-1"></i>${error}
                            </div>
                        </#list>
                    </div>
                </div>


                <@spring.bind "bookForm.rating"/>
                <div class="mb-3">
                    <label for="rating" class="form-label">Rating <small class="text-muted">(optional, 0-5)</small></label>
                    <input type="number" class="form-control <#if spring.status.errorMessages?has_content>is-invalid</#if>"
                           id="rating" name="${spring.status.expression}" value="${spring.status.value!''}"
                           step="0.1" min="0" max="5" placeholder="e.g. 4.5">
                    <#list spring.status.errorMessages as error>
                        <div class="invalid-feedback">
                            <i class="fa-solid fa-circle-exclamation me-1"></i>${error}
                        </div>
                    </#list>
                </div>


                <@spring.bind "bookForm.genreIds"/>
                <div class="mb-3">
                    <label class="form-label">Genres <span class="text-danger">*</span></label>
                    <div class="row">
                        <#list genres as genre>
                            <div class="col-md-4 col-6">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox"
                                           name="genreIds" value="${genre.id}"
                                           id="genre_${genre.id}"
                                           <#if bookForm.genreIds?? && bookForm.genreIds?seq_contains(genre.id)>checked</#if>>
                                    <label class="form-check-label" for="genre_${genre.id}">${genre.name}</label>
                                </div>
                            </div>
                        </#list>
                    </div>
                    <#list spring.status.errorMessages as error>
                        <div class="text-danger small mt-1">
                            <i class="fa-solid fa-circle-exclamation me-1"></i>${error}
                        </div>
                    </#list>
                </div>


                <div class="mb-4">
                    <label for="coverImage" class="form-label">Cover Image <small class="text-muted">(optional)</small></label>
                    <input type="file" class="form-control" id="coverImage" name="coverImage" accept="image/*">
                </div>

                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-success">
                        <i class="fa-solid fa-floppy-disk me-1"></i>Save Book
                    </button>
                    <a href="<@spring.url '/'/>" class="btn btn-outline-secondary">
                        <i class="fa-solid fa-xmark me-1"></i>Cancel
                    </a>
                </div>
            </form>
        </div>
    </div>
</@layout.page>
