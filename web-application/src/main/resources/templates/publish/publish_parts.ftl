<#import "../user/user_parts.ftl" as us>
<#include "../parts/security.ftl" />

<!-- Постер публікації -->
<#macro publish_title publish>
    <a href="/news/${publish.id}" class="text-decoration-none  m-1">
        <div class="row text-dark p-1 rounded-5 border-light bg-light border" style=" overflow: hidden;">
            <div class="col-4">
                <img src="${publish.getImageUrl()!'/static/icons/image.svg'}">
            </div>
            <div class="col-8" style="max-height: 200px;">
                <div class="container">
                    <div class="row-cols-1">
                        <div class="col">
                            <h3 class="h3">${publish.titleNames}</h3>
                        </div>
                        <div class="col"  >
                           ${publish.textHtml}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </a>
</#macro>

<#macro publish_title_legacy publish>
    <#assign url = publish.getImageUrl()>
    <div class="col" style="min-height: 450px; ">
        <a href="/news/${publish.id}" class="card card-cover h-100 overflow-hidden bg-dark rounded-5 shadow-lg"
           style=" text-decoration: none;
                   background-size: cover;
                   background-position: center;
                   background-repeat: no-repeat;
                   background-image: url('${url!'/static/icons/image.svg'}');"
        >
            <div class="d-flex flex-column h-100  text-dark  ">
                <div class="bg-white  d-flex justify-content-center" style="height: 15%;">
                    <h2 class="h2 align-middle mt-auto text-center">${publish.titleNames}</h2>
                </div>
                <ul class="d-flex list-unstyled mt-auto">
                    <li class="me-auto">
                    </li>
                    <li class="d-flex align-items-center me-3">
                        <small class="fw-bold">${publish.author.username}</small>
                    </li>
                    <li class="d-flex align-items-center">
                        <svg class="bi me-2" width="1em" height="1em">
                            <use xlink:href="#calendar3"></use>
                        </svg>
                        <small class="fw-bold"></small>
                    </li>
                </ul>
            </div>
        </a>
    </div>
</#macro>
<!--Редагування постеру новини-->
<#macro poster_edit publish>
    <div class="container-fluid px-4 py-5" id="custom-cards">
        <div class="row row-cols-4 align-items-stretch g-4 py-5">
            <div class="col-2 offset-lg-2 col-lg-3 mt-5 pt-5">
                <h4 class="h4 text-center">Налаштування публікації</h4>
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
                    <button class="form-control mt-2" type="submit">Оновити</button>
                </form>
            </div>
            <@publish_title  publish/>
        </div>
    </div>
</#macro>

<!-- Редактор контенту-->
<#macro content publish>
    <div class="bg-light">
        <div class="container-xl py-5">
            <div class="row bg-body">
                <section class="[ main-content ]  [ formatted ]"
                         id="edit" data-editable
                         data-name="main-content">
                    ${publish.textHtml!'текст'}
                </section>
            </div>
        </div>
    </div>
    <@us.author  user />
    <script src="https://cdn.jsdelivr.net/npm/ContentTools@1.6.1/build/content-tools.min.js"></script>
    <script src="/static/scripts/editor.js"></script>
    <script>
        putNews(${publish.id})
        myFunc()
    </script>
</#macro>

<#macro info>
    <div class="container-md py-5">
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
