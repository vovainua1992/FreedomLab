<#include "security.ftl">
<#import "filter.ftl" as filter>
<#import "log.ftl" as login>

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
                <div class="order-3 order-md-1 collapse navbar-collapse h5 font-weight-bold m-0" id="navbarSupportedContent" style>
                    <ul class="navbar-nav me-auto font-weight-bold">
                        <li class="nav-item" >
                            <a class="nav-link p-3  text-white" href="/publish/all">Публікації</a>
                        </li>
                        <li class="nav-item" >
                            <a class="nav-link p-3  text-white" href="/">Про проект</a>
                        </li>
                    </ul>

                </div>
                <#if user??>
                    <a class="order-2 order-md-2 nav-link nav-profile p-2 me-md-5 text-white dropdown-toggle ps-3" data-bs-toggle="dropdown"
                       href="#" role="button" aria-expanded="false">
                        <img class="nav-avatar border-light border rounded-circle"
                             <#if user.avatar?hasContent>src="${user.avatar.url}"
                             <#else>src="/static/icons/image.svg"</#if>/>
                    </a>
                    <ul class="dropdown-menu sm-1 pull-right col-12 col-md-3" style="right: 0px; left: auto;">
                        <li class="">
                            <a class="dropdown-item" href="/user/profile">Профіль</a>
                        </li>
                        <li class="">
                            <a class="dropdown-item" href="/publish/add">Створити публікацію</a>
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
                    <div class="order-2 order-md-2">
                        <a class="col-12 col-md-5 nav-link text-white ms-0 py-1 py-md-3 mx-auto" href="/login">Війти</a>
                        <a class="col-12 col-md-7 nav-link text-white ms-0 py-1 py-md-3 mx-auto" href="/registration">Регістрація</a>
                    </div>
                </#if>
            </nav>
        </div>
    </div>
</div>
