<#include "security.ftl">
<#import "filter.ftl" as filter>
<#import "log.ftl" as login>

<div class="w-100 bg-light bg-gradient">
    <div class="container">
        <nav class="navbar navbar-expand-sm navbar-light  navbar-text">
            <a class="navbar-brand ms-3" href="/user/<#if user??>${user.id}<#else>#</#if>">Freedom</a>
            <button class="navbar-toggler collapsed" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent" style>
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/news">Публікації</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/">Про проект</a>
                    </li>
                </ul>
                <div class="d-inline-flex">
                    <#if user??>
                        <a class="nav-link nav-profile link-secondary dropdown-toggle " data-bs-toggle="dropdown"
                           href="#" role="button" aria-expanded="false">${name}</a>
                        <ul class="dropdown-menu sm-1 pull-right" style="right: 0px; left: auto;width: 200px">
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
                        <a class="nav-link link-secondary" href="/login">Війти</a>
                        <a class="nav-link link-secondary" href="/registration">Регістрація</a>
                    </#if>
                </div>
            </div>
        </nav>
    </div>
</div>
