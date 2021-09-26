<#import "../parts/common.ftl" as c>
<#import "publish_parts.ftl" as p/>


<#include "../parts/security.ftl">

<@c.page>
    <@hat publish></@hat>
    <div class="container-xxl p-1 bg-blue-dark shadow-lg rounded-5">

        <div class="row m-1">
            <div class="col bg-white rounded-5">
                <div class="container-xl my-1 my-lg-4 p-0 px-lg-3">
                    <div class="row justify-content-end">
                        <div class="col-12">

                        </div>
                    </div>
                    <div class="row mb-lg-4">
                        <!-- Блок заголовку -->
                        <h2 class="col text-center">${publish.titleNames} </h2>
                    </div>
                    <!-- Блок контенту публікації -->
                    <div class="row">
                        <section class="[ main-content ]  [ formatted ]"
                                 id="edit" data-editable
                                 data-name="main-content">
                            ${publish.textHtml}
                        </section>
                    </div>
                </div>

                <#if isEdit>
                    <@c.add '<script src="https://cdn.jsdelivr.net/npm/ContentTools@1.6.1/build/content-tools.min.js"></script>'/>
                    <@c.add '<script src="/static/scripts/editor.js"></script>'/>
                    <@c.add '<script>putNews(${publish.id})</script>'/>
                    <@c.add ''/>
                </#if>
            </div>
        </div>
    </div>
    <@publish_footer publish/>

</@c.page>

<#macro hat publish>
    <div class="container-xxl mt-5 ">
        <div class="row mb-1">
            <div class="col me-auto my-auto text-center text-muted" style="font-weight: bold!important;">
                <#if publish.isActive()>
                    Опубліковано:
                    <#if publish.datePublication?has_content>
                        ${publish.dateToString(publish.datePublication)}
                    <#else>
                        Дуже давно
                    </#if>
                <#else>
                    Створено:
                    <#if publish.dateCreate?has_content>
                        ${publish.dateToString(publish.dateCreate)}
                    <#else>
                        Дуже давно
                    </#if>
                </#if>
            </div>
            <div class="col-auto"><@publish_post publish/></div>
        </div>
    </div>
</#macro>

<!-- Публікування публікацій -->
<#macro publish_post publish>
    <#if isEdit>
        <#if !publish.isActive()>
            <a class="text-decoration-none" href="/publish/edit/${publish.id}">
                <p class="btn btn-outline-secondary my-auto"> Опублікувати</p>
            </a>
            <div class="ct-app">
                <div class="ct-widget ct-ignition ct-ignition--ready ct-widget--active">
                    <a class="publish-upload" href="/publish/edit/${publish.id}">
                        <img src="/static/icons/cloud-upload.svg" class="filter-green rounded-circle">
                    </a>
                </div>
            </div>
        </#if>
    </#if>
</#macro>

<#macro publish_footer publish>
    <div class="container-xxl pt-2 px-0 ">
        <div class="row">
            <div class="col-12 p-0 col-sm-auto mx-auto mx-md-0 ms-md-auto"><@likes publish></@likes></div>
            <div class="col-12 p-0 col-sm-auto col-md-6 mx-auto mx-md-0"><@author_box publish.author></@author_box></div>
        </div>
    </div>
</#macro>

<!-- Блок вподобайок -->
<#macro likes publish>
    <div class="container-xl h-100 px-0">
        <div class="row ms-md-3 h-100 rounded">
            <div class="col-auto my-sm-auto mb-3 mx-auto h3 p-0 text-muted">
                <div class="h5 d-inline">Вподобайки:</div>
                <div class="me-2 d-inline">${publish.likes}</div>
                <a class="d-inline ms-3 position-relative" href="/publish/like/${publish.id}">
                    <#if publish.meLiked>
                        <img class="position-absolute" src="/static/icons/heart.svg" style="height: 30px;">
                    <#else>
                        <img class="position-absolute" src="/static/icons/heart-empty.svg" style="height:30px;">
                    </#if>
                </a>
            </div>
        </div>
    </div>
</#macro>

<!--Блок творця -->
<#macro author_box author>
    <div class="container-xl px-0">
        <div class="row m-2 rounded">
            <div class="col-auto mx-auto mb-3 me-sm-auto position-relative p-0 m-0 author-name">
                <div class="d-inline text-dark">${author.username}<span class="p-0 position-absolute subscribe">
                <#if user??>
                    <#if user.equals(author)>

                    <#elseif author.isSubscriber(user)>
                        <a class="btn p-1 display-7 btn-outline-secondary"
                           href="/subscribe/remove/${author.id}">Відписатися</a>
                    <#else>
                        <a class="btn p-1 display-7 btn-outline-secondary" href="/subscribe/add/${author.id}">Підписатися</a>
                    </#if>
                </#if>
                </span></div>
                <img class="author-avatar rounded-circle d-inline"
                     <#if author.avatar??>src="${author.avatar.small.url}"
                     <#else>src="/static/icons/image.svg"</#if>/>

            </div>
        </div>
    </div>
</#macro>
