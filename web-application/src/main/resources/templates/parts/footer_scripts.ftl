<#assign
    scripts = []>

<#macro add script_tag>
   <#assign scripts= scripts+[script_tag]>
</#macro>

<#macro getAll>
    <#list scripts as script>
        ${script}
    <#else>
    </#list>
</#macro>
