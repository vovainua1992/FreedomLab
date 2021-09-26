<#import "../parts/common.ftl" as c>
<#include "../parts/security.ftl">

<#--Структура сторінки профілю-->
<@c.page>
    <div class="container-xxl">
        <table class="w-100 mt-5">
            <tbody class="w-100">
            <tr class="w-100">
                <td></td>
                <td class="text-center">
                    <h2 class="h2">Налаштування</h2>
                    <h6 class="h6 text-info">Увага! Робота над налаштуваннями ведеться і налаштування будуть зявлятись
                        по мірі появи нового функціоналу.</h6>
                </td>
                <td></td>
            </tr>
            <tr class="w-100">
                <td style="width: 10%;"></td>
                <td style="width: 80%;">
                    <@category_menu />
                </td>
                <td style="width: 10%;"></td>
            </tr>
            </tbody>
        </table>
    </div>

    <script src="/static/scripts/image-editor.js"></script>
</@c.page>

<#--категорії профілю-->
<#macro category_menu>
    <div class="row">
        <div class="col-xxl-1 mt-5">
            <ul class="nav nav-tabs tabs-left" id="myTab" role="tablist">
                <li class="bg-gradient bg-light  " role="presentation">
                    <button class="nav-tab nav-link" id="home-tab" data-bs-toggle="tab" data-bs-target="#home"
                            type="button " role="tab" aria-controls="home" aria-selected="true">Профіль
                    </button>
                </li>
                <li class="bg-gradient bg-light " role="presentation">
                    <button class="nav-link nav-tab" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile"
                            type="button" role="tab" aria-controls="profile" aria-selected="false">Безпека
                    </button>
                </li>
            </ul>
        </div>
        <div class="col-xxl-8 my-5 mx-auto">
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="home" role="tabpanel"
                     aria-labelledby="home-tab"><@seting_main /></div>
                <div class="tab-pane fade" id="profile" role="tabpanel"
                     aria-labelledby="profile-tab"><@setting_security /></div>
            </div>
        </div>
    </div>
</#macro>



<#--    Налаштування профілю користувача-->
<#macro seting_main>
    <@login/>
    <@seting_avatar/>
</#macro>

<#macro login>
    <div class="container-xxl">
        <div class="row">
            <div class="col-auto ms-1">
                <label> Логін: </label>
            </div>
            <div class="col-auto me-auto">
                <div>${name!''}</div>
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal"
                        data-bs-target="#change-login">
                    Змінити логін
                </button>
            </div>
        </div>
    </div>
    <div class="modal" id="change-login" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true" style="margin-top: 10%">
        <div class="modal-dialog">
            <div class="modal-content">
                <form method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Відредагуйте свій логін</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="w-75 mx-auto">
                            <input type="text" value="${name!''}" name="username" class="form-control"
                                   placeholder="User name"/>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Відмінити</button>
                        <button type="submit" class="btn btn-primary">Зберегти</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</#macro>
<!-- Налаштування аватарки користувача-->
<#macro seting_avatar>
    <div class="container-xxl mt-2">
        <div class="row">
            <div id="avatar-edit-box" class="d-block position-relative p-0 mx-auto w-100">
                <img id="avatar-edit" <#if user.avatar?hasContent>src="${user.avatar.origin.url}"
                     <#else>src="/static/icons/image.svg"</#if>
                     style="width: 100%; height: auto;"/>
                <div id="image-selector" class="border shadow-lg rounded-circle border-gray position-absolute"
                     style="
                                 top: 100px;
                                 left: 100px;border-style: dashed!important;">
                </div>
            </div>
            <input class="form-control" id="avatar-file" type="file" name="file"/>
            <a href="#" class="btn btn-outline-primary" onclick="postAvatar()">Оновити аватар</a>
        </div>
    </div>
    <#if user.avatar?hasContent>
        <@c.add '<script> setPositionAndSize(${user.avatar.smallPosX},${user.avatar.smallPosY},${user.avatar.smallSize})</script>'></@c.add>
    </#if>

</#macro>

<!-- Налаштування безпеки -->
<#macro setting_security>
    <form class="justify-content-center align-content-center" method="post">
        <div class="form-groupя row my-1">
            <label class="col-sm-4 col-form-label"> Новий пароль:</label>
            <div class="col-sm-4">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <div class="form-groupя row my-1">
            <label class="col-sm-4 col-form-label"> Підтвердження паролю:</label>
            <div class="col-sm-4">
                <input type="password" name="password2" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <div class="form-group row my-1">
            <label class="col-sm-4 col-form-label"> Почтова скринька:</label>
            <div class="col-sm-4">
                <input type="email" name="email" class="form-control"
                       placeholder="some@some.com" value="${email!''}"/>
            </div>
        </div>
        <div class="offset-sm-4 col-sm-6 mt-3">
            <button class="btn btn-primary m-2" type="submit">Зберегти</button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>
