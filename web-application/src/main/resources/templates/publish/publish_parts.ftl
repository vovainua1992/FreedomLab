<#import "../user/user_parts.ftl" as us>
<#include "../parts/security.ftl" />

<!-- Постер публікації -->
<#macro publish_title publish isEdit>
    <#if isEdit>
        <div class="h-auto m-3 row"> <@publish_view publish isEdit/></div>
    <#else>
        <a href="/news/${publish.id}" class="text-decoration-none h-auto m-3 row">
            <@publish_view publish isEdit/>
        </a>
    </#if>
</#macro>

<#macro publish_view publish isEdit>
    <div class="bg-blue-gradient rounded-5 p-1 shadow-lg">
        <div class="row mx-0 align-items-center text-dark rounded-5 bg-white " style="overflow: hidden;">
            <#if isEdit>
            <form class="form align-middle"
                  enctype="multipart/form-data"
                  action="/news/update_poster" method="post">
                <label class="form-text">Заголовок:</label>
                <input class="form-control" type="text" placeholder="Назва новини"
                       name="title" value="${publish.titleNames}"/>
                <input type="hidden" name="publishId" value="${publish.id}"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-check form-switch my-2">
                    <input class="form-check-input" name="checkVisible" type="checkbox"
                           id="flexSwitchCheckDefault" ${publish.active?string("checked"," ")} >
                    <label class="form-check-label" for="flexSwitchCheckDefault">Активована</label>
                </div>
                </#if>
                <div class="col-12 col-md-5 col-lg-4 col-xl-3  align-content-center">
                    <img class="rounded mx-auto d-block" src="${publish.getImageUrl()!'/static/icons/image.svg'}">
                </div>
                <div class="col-12 col-md-7 col-lg-8 col-xl-9" style="max-height: 200px;">
                    <div class="container">
                        <div class="row-cols-1">
                            <div class="col">
                                <h3 class="h3">${publish.titleNames}</h3>
                            </div>
                            <div class="col">
                                ${publish.textHtml}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container mt-2">
                    <div class="row">
                        <button class="col align-self-start form-control" type="submit">Оновити</button>
                        <a class="col align-self-center btn-outline-primary btn" href="/news/${publish.id}"
                           style="text-decoration: none;">Зберегти</a>
                        <a class="col align-self-end btn-danger btn" href="/news/delete/${publish.id}"
                           style="text-decoration: none;">Видалити</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</#macro>

<!-- Постер публікації -->
<#macro publish_title_LEGACY publish >
    <a href="/news/${publish.id}" class="text-decoration-none h-auto m-3 row">
        <div class="bg-blue-gradient rounded-5 p-1 shadow-lg">
            <div class="row mx-0 align-items-center text-dark rounded-5 bg-white " style="overflow: hidden;">
                <div class="col-12 col-md-5 col-lg-4 col-xl-3  align-content-center">
                    <img class="rounded mx-auto d-block" src="${publish.getImageUrl()!'/static/icons/image.svg'}">
                </div>
                <div class="col-12 col-md-7 col-lg-8 col-xl-9" style="max-height: 200px;">
                    <div class="container">
                        <div class="row-cols-1">
                            <div class="col">
                                <h3 class="h3">${publish.titleNames}</h3>
                            </div>
                            <div class="col">
                                ${publish.textHtml}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </a>
</#macro>


<!--Редагування постеру новини-->
<#macro poster_edit publish>
    <div class="container-fluid px-4 py-5" id="custom-cards">
        <div class="row g-4 py-5">
            <@publish_title  publish/>
            <div class="col mt-3">
                <h4 class="h4 text-center">Налаштування заголовку публікації</h4>
                <form class="form align-middle"
                      enctype="multipart/form-data"
                      action="/news/update_poster" method="post">
                    <label class="form-text">Заголовок:</label>
                    <input class="form-control" type="text" placeholder="Назва новини"
                           name="title" value="${publish.titleNames}"/>
                    <input type="hidden" name="publishId" value="${publish.id}"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <label class="form-text">Заставка для публікації:</label>
                    <input class="form-control" type="file" name="file"/>
                    <div class="form-check form-switch my-2">
                        <input class="form-check-input" name="checkVisible" type="checkbox"
                               id="flexSwitchCheckDefault" ${publish.active?string("checked"," ")} >
                        <label class="form-check-label" for="flexSwitchCheckDefault">Активована</label>
                    </div>

                    <div class="container mt-2">
                        <div class="row">
                            <button class="col align-self-start form-control" type="submit">Оновити</button>
                            <a class="col align-self-center btn-outline-primary btn" href="/news/${publish.id}"
                               style="text-decoration: none;">Зберегти</a>
                            <a class="col align-self-end btn-danger btn" href="/news/delete/${publish.id}"
                               style="text-decoration: none;">Видалити</a>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</#macro>

<#macro info>
    <div class="container-md py-5 h-100">
        <div class="row pt-5 mt-3">
            <h4 class="h3 text-center">Додаткова інформація</h4>
            <p class="text-center text-muted">Згодом тут буде розгорнута інструкція до редагування публікацій</p>
            <p class="h5">На даний момент налаштування досить просте потрібно можна вибрати заставку для публікації та
                її назву.
                Також тут є перемикач активності публікації. Якщо публікація не активна то бачити її буде лише автор у
                вкладці мої публікації.
                Кнопка оновити відповідає за оновлення налаштувань публікації.</p>
        </div>
    </div>
</#macro>
