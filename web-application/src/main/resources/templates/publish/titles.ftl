<#import "../parts/common.ftl" as c>
<#import "publish_parts.ftl" as n>


<@c.page>
    <div class="container-fluid px-4 py-5" id="custom-cards">
        <h2 class="text-center">${title}</h2>
        <div class="row row-cols-1 row-cols-lg-3 row-cols-xl-4 row-cols-xxl-6 align-items-stretch g-4 py-5">
            <#list news as publish>
                <@n.publish_title  publish/>
            <#else>
                <div class="text-center justify-content-center h-100">
                    <h3>Немає публікацій</h3>
                </div>
            </#list>
        </div>
    </div>
</@c.page>