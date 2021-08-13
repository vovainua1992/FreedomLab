<#import "../parts/common.ftl" as c>
<#import "publish_parts.ftl" as p>
<#import "../parts/pager.ftl" as pager>


<@c.page>
    <div class="container-xxl">
        <#list news.content as publish>
            <@p.publish_title  publish/>
        <#else>
            <div class="text-center justify-content-center h-100">
                <h3>Немає публікацій</h3>
            </div>
        </#list>
        <div class="row">
            <div class="col"><@pager.pager url news/></div>
        </div>
    </div>
</@c.page>
