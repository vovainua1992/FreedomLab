<#include "security.ftl">
<#import "filter.ftl" as filter>
<#import "log.ftl" as login>

<div class="w-100 bg-blue-gradient">
    <div class="container">
        <div class="row align-content-center">
            <nav class="col navbar navbar-expand-md navbar-light  navbar-text p-0">
                <a class="navbar-brand ms-3 p-0 logo"  href="/user/<#if user??>${user.id}<#else>#</#if>">Freedom</a>
                <button class="navbar-toggler collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse h5 font-weight-bold m-0" id="navbarSupportedContent" style>
                    <ul class="navbar-nav me-auto font-weight-bold">
                        <li class="nav-item">
                            <a class="nav-link ps-3 py-2 py-md-3 text-white" href="/news">Публікації</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ps-3 py-2 py-md-3 text-white" href="/">Про проект</a>
                        </li>
                    </ul>
                    <div class="row">
                        <#if user??>
                            <a class="nav-link nav-profile text-white dropdown-toggle py-md-3 " data-bs-toggle="dropdown"
                               href="#" role="button" style="margin-right: 120px;" aria-expanded="false">${name}</a>
                            <ul class="dropdown-menu sm-1 pull-right" style="right: 120px; left: auto;width: 200px">
                                <li class="">
                                    <a class="dropdown-item" href="/user/profile">Профіль</a>
                                </li>
                                <li class="">
                                    <a class="dropdown-item" href="/news/add">Створити публікацію</a>
                                </li>
                                <li class="">
                                    <a class="dropdown-item" href="/news/my">Мої публікації</a>
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
                            <a class="col-12 col-md-5 nav-link text-white ms-3 ms-md-0 py-md-3 mx-auto" href="/login">Війти</a>
                            <a class="col-12 col-md-7 nav-link text-white ms-3 ms-md-0 py-md-3 mx-auto" href="/registration">Регістрація</a>
                        </#if>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</div>
