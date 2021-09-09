<#include "security.ftl">
<#import "filter.ftl" as filter>
<#import "log.ftl" as login>

<#macro navbar>
    <div class="w-100 bg-blue-gradient">
        <div class="container">
            <div class="row align-content-center">
                <nav class="col navbar navbar-expand-md navbar-light  navbar-text p-0">
                    <a class="order-1 navbar-brand ms-3 p-0 logo" href="/user/<#if user??>${user.id}<#else>#</#if>">Freedom</a>
                    <button class="order-0 navbar-toggler collapsed" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="order-3 order-md-1 collapse navbar-collapse h5 font-weight-bold m-0"
                         id="navbarSupportedContent" style>
                        <ul class="navbar-nav me-auto font-weight-bold">
                            <li class="nav-item">
                                <a class="nav-link p-3  text-white" href="/publish/all">Публікації</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link p-3  text-white" href="/">Про проект</a>
                            </li>
                        </ul>

                    </div>
                    <#if user??>
                        <a class="order-2 order-md-2 nav-link nav-profile p-2 me-md-5 text-white dropdown-toggle ps-3"
                           data-bs-toggle="dropdown"
                           href="#" role="button" aria-expanded="false">
                            <img class="nav-avatar border-light border rounded-circle"
                                 <#if user.avatar?hasContent>src="${user.avatar.small.url}"
                                 <#else>src="/static/icons/image.svg"</#if>/>
                        </a>
                        <ul class="dropdown-menu sm-1 pull-right col-12 col-md-3" style="right: 0px; left: auto;">
                            <li class="">
                                <a class="dropdown-item" href="/user/profile">Профіль</a>
                            </li>
                            <li class="">
                                <button type="button" class="dropdown-item" data-bs-toggle="modal"
                                        data-bs-target="#staticBackdrop">
                                    Створити публікацію
                                </button>
                            </li>
                            <li class="">
                                <a class="dropdown-item" href="/publish/my">Мої публікації</a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <#if isAdmin>
                                <li class="nav-item">
                                    <a class="dropdown-item link-danger" href="/user">Список користувачів</a>
                                </li>
                            </#if>
                            <li>
                                <@login.logout/>
                            </li>
                        </ul>
                    <#else>
                        <div class="row order-2 order-md-2">
                            <a class="col-12 nav-link h5 text-white py-4 m-0" href="/login">Війти</a>
                        </div>
                    </#if>
                </nav>
            </div>
        </div>
    </div>
    <@create_publish_modal/>
</#macro>

<!-- Модальне меню для створення публікацій-->
<#macro create_publish_modal>
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form  enctype="multipart/form-data"
                       action="/publish/add" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Створити нову публікацію</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <label class="form-text">Імя публікації:</label>
                        <input class="form-control" id="image-file" type="text" name="name"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Відмінити</button>
                        <button type="submit" class="btn btn-primary">Створити</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</#macro>