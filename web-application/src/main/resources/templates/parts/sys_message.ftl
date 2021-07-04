<#macro message>
    <div class="container-sm w-50">
        <div class="row ">
            <#assign
            known = pov??
            >
            <#if succ??>
                <@success succ/>
            </#if>
            <#if warn??>
                <@warning warn/>
            </#if>
            <#if dang??>
                <@danger dang/>
            </#if>
        </div>
    </div>
</#macro>

<#macro success message>
    <div class="alert alert-success p-2 m-2" role="alert">
        <p class="my-auto">${message}</p>
    </div>
</#macro>

<#macro danger message>
    <div class="alert alert-danger p-2 m-2 " role="alert">
        <p class="my-auto">${message}</p>
    </div>
</#macro>

<#macro warning message>
    <div class="alert alert-warning p-2 m-2" role="alert">
        <p class="my-auto">${message}</p>
    </div>
</#macro>