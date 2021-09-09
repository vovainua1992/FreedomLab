<#import "../parts/common.ftl" as c>
<#import "publish_parts.ftl" as p/>


<#include "../parts/security.ftl">

<@c.page>

    <div class="container-xxl mt-5 p-1 bg-blue-dark shadow-lg rounded-5">

        <div class="row m-1">
            <div class="col bg-white rounded-5">
                <div class="container-xl mt-5">
                    <div class="row justify-content-end">
                        <div class="col-12">
                            <@hat publish></@hat>
                        </div>
                    </div>
                    <div class="row">
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
                <@author_box publish.author/>

                <#if isEdit>
                    <@c.add '<script src="https://cdn.jsdelivr.net/npm/ContentTools@1.6.1/build/content-tools.min.js"></script>'/>
                    <@c.add '<script src="/static/scripts/editor.js"></script>'/>
                    <@c.add '<script>putNews(${publish.id})</script>'/>
                    <@c.add ''/>
                </#if>
            </div>
        </div>
    </div>
</@c.page>

<#macro hat publish>
    <div class="container-xxl">
        <div class="row">
            <div class="col me-auto text-muted" style="font-weight: bold!important;">
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

<!--Блок творця -->
<#macro author_box author>
    <div class="container-xl pb-3 ">
        <div class="row m-2 rounded">
            <div class="col-auto ms-auto mt-1">
                <#if user??>
                    <#if user.equals(author)>

                    <#elseif author.isSubscriber(user)>
                        <a class=" m-3 btn p-1 btn-outline-primary"
                           href="/subscribe/remove/${author.id}">Відписатися</a>
                    <#else>
                        <a class=" m-3 btn p-1 btn-outline-primary" href="/subscribe/add/${author.id}">Підписатися</a>
                    </#if>
                </#if>
            </div>
            <div class="col-auto display-7 my-auto text-center text-muted">${author.username}</div>
            <div class="col-auto"><img class="author-avatar rounded-circle"
                                       <#if author.avatar??>src="${author.avatar.small.url}"
                                       <#else>src="/static/icons/image.svg"</#if>/></div>
        </div>
    </div>
</#macro>
