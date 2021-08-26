<#import "../user/user_parts.ftl" as us>
<#include "../parts/security.ftl" />
<!-- Постер публікації -->
<#macro publish_title publish isEdit>
    <#if isEdit>
        <form class="form align-middle"
        enctype="multipart/form-data"
        action="/publish/update_poster" method="post">
        <input type="hidden" name="publishId" value="${publish.id}"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="container-xxl">
            <div class="row pb-1">
                <div class="col-12 col-md-6  col-lg-4">
                    <label class="form-text">Заставка для публікації:</label>
                    <input class="form-control" id="image-file" type="file" name="file"/>
                </div>
                <div class="col-12 col-md-6">
                    <label class="form-text">Заголовок:</label>
                    <input class="form-control" id="input-text" type="text" placeholder="Назва новини"
                           name="title" value="${publish.titleNames}"/>
                </div>
                <div class="col-5 col-lg-2 ms-auto mt-auto form-check form-switch">
                    <input onclick="checkActive()" id="checkbox" class="ms-auto form-check-input" name="checkVisible" type="checkbox"
                           id="flexSwitchCheckDefault" ${publish.active?string("checked"," ")} >
                    <label id="active-text" class="ms-1 form-check-label" for="flexSwitchCheckDefault">${publish.active?string("Активована","Деактивована")}</label>
                </div>
            </div>
        </div>
    </#if>
    <div class="h-auto m-3 row">
        <div class="bg-blue-gradient rounded-5 p-1 shadow-lg">
            <div class="container-xxl p-0">
                <div class="row mx-0 align-items-center text-dark rounded-5 bg-white "
                     style="overflow: hidden;">
                    <div class="col-12 col-md-5 col-lg-4 col-xl-3  align-content-center">
                        <#if !isEdit><a href="/publish/${publish.id}" class="text-decoration-none h-auto m-3 row"></#if>
                            <img ${isEdit?string('id="image"','')} class="rounded mx-auto d-block w-100 h-auto"
                                                                   src="${publish.getImageUrl()!'/static/icons/image.svg'}"/>
                            <#if !isEdit></a></#if>
                    </div>
                    <div class="col-12 col-md-7 col-lg-8 col-xl-9" style="max-height: 200px;">
                        <div class="container p-0">
                            <div class="row-cols-1">
                                <div class="col">
                                    <#if !isEdit><a href="/publish/${publish.id}"
                                                    class="text-decoration-none text-secondary h-auto m-3 row"></#if>
                                        <h3 id="title" class="h3 text-center">${publish.titleNames}</h3>
                                        <#if !isEdit></a></#if>
                                </div>
                                <div class="col">${publish.textHtml}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#if isEdit>
        <div class="container mt-2">
            <div class="row justify-content-end">
                <button class="col-12 my-1 col-md-6 btn-outline-primary btn" type="submit">
                    Зберегти
                </button>
                <a class="col-12 my-1 col-md-6 btn-outline-primary btn"
                   href="/publish/edit/${publish.id}"
                   style="text-decoration: none;">Відмінити</a>
                <a class="col-6 mt-5 col-md-2 btn-danger btn" href="/publish/delete/${publish.id}"
                   style="text-decoration: none;">Видалити</a>
            </div>
        </div>
        </form>
    </#if>
</#macro>

<#macro publish_filter filter>

</#macro>

<#macro info>

    <div class="container-md py-5 h-100">
        <div class="row pt-5 mt-3">
            <h4 class="h3 text-center">Додаткова інформація</h4>
            <p class="text-center text-muted">(Проект активно дописується тому довідкова інформація часто устаріває)</p>
            <p>Головне що потрібно знати, коли користуєшся даним сайтом він ніразу не User-friendly. При виборі нової
                заставки, або зміні заголовку у вікні передперегляду - можна побачити, як зміниться зовнішній вигляд
                заголовку публікації.
                В верхньому правому куті відображається перемикач активності публікації. Кнопка відмінити - відповідає
                за відміну вчинених змін. Кнопка зберегти зберігає внесенні зміни та відправляє на сторінку "Мої
                публікації" кнопка видалити видаляє публікацію. </p>
        </div>
    </div>
</#macro>
