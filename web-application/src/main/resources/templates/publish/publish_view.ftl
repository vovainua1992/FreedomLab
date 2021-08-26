<#import "../parts/common.ftl" as c>
<#import "publish_parts.ftl" as p/>
<#import "../parts/footer_scripts.ftl" as s/>

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
                    <@s.add '<script src="https://cdn.jsdelivr.net/npm/ContentTools@1.6.1/build/content-tools.min.js"></script>'/>
                    <@s.add '<script src="/static/scripts/editor.js"></script>'/>
                    <@s.add '<script>putNews(${publish.id})</script>'/>
                </#if>
            </div>
        </div>
    </div>
</@c.page>

<#macro hat publish>
    <div class="container-xxl">
        <div class="row">
            <div class="col me-auto text-muted" style="font-weight: bold!important;"> Опубліковано: ${publish.getDateTimeString()}</div>
            <div class="col-auto"><@edit_top/></div>
        </div>
    </div>
</#macro>

<!-- Верхній блок управління публікацією для адміна або творця -->
<#macro edit_top>
    <#if isEdit>
            <a class="me-3" href="/publish/edit/${publish.id}"><img src="/static/icons/wrench.svg"
                                                                    style="height: 25px;width: 25px;"></a>
            <a href="/publish/activate/${publish.id}">
                <img src="${publish.active?string("/static/images/eye.svg","/static/images/eyehide.svg")}"
                     style="height: 30px;width: 30px;"/>
            </a>
    </#if>
</#macro>

<!--Блок творця -->
<#macro author_box author>
    <div class="container-xl pb-3 ">
        <div class="row m-2 rounded">
            <div class="col-auto ms-auto mt-1">
                <#if user.equals(author)>

                <#elseif author.isSubscriber(user)>
                    <a class=" m-3 btn p-1 btn-outline-primary" href="/subscribe/remove/${author.id}">Відписатися</a>
                <#else>
                    <a class=" m-3 btn p-1 btn-outline-primary" href="/subscribe/add/${author.id}">Підписатися</a>
                </#if>
            </div>
            <div class="col-auto display-7 my-auto text-center text-muted">${author.username}</div>
            <div class="col-auto"><img class="author-avatar rounded-circle"
                                       <#if author.avatar??>src="${author.avatar.url}"
                                       <#else>src="/static/icons/image.svg"</#if>/></div>
        </div>
    </div>
</#macro>
