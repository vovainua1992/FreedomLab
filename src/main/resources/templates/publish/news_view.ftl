<#import "../parts/common.ftl" as c>
<#import "../parts/log.ftl" as l>
<#include "../parts/security.ftl">

<@c.page>

    <div class="container-xl mt-5" xmlns="http://www.w3.org/1999/html">
        <div class="row">
            <h2 class="col text-center">${publish.titleNames} </h2>
            <#if isEdit>
            <a href="/news/edit/${publish.id}"
               style=" margin-right:20px;background-image: url('/static/images/pencil.svg');
                        width: 25px;height: 25px; background-repeat: no-repeat; background-size: cover;"></a>
            <a href="/news/activate/${publish.id}"
               style="margin-right: 50px; background-image: url(${publish.active?string("/static/images/eye.svg","/static/images/eyehide.svg")});
                       width: 25px;height: 25px; background-repeat: no-repeat; background-size: cover;"></a>
                       </#if>
        </div>
        <div class="row">
            <section class="[ main-content ]  [ formatted ]"
                     id="edit" data-editable
                     data-name="main-content">
                ${publish.textHtml}
            </section>
        </div>
    </div>
    <div class="container-xl" style="margin-top: 100px;">
        <div class="row">
            <div class="text-muted offset-10 fw-bold col-2 align-self-end ">
                Автор: ${publish.author.username}.

            </div>
        </div>
    </div>

    <#if isEdit>

        <script src="/static/scripts/content-tools.min.js"></script>
        <script src="/static/scripts/editor.js"></script>
        <script>
            putNews(${publish.id})
        </script>
    </#if>
</@c.page>