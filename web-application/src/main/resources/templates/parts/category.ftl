<#macro select categories isFilter>
    <label class="form-text" for="categories${isFilter?string('','C')}">Категорія:</label>
    <select class="form-select" name="categories${isFilter?string('','C')}" id="categories${isFilter?string('','C')}" ${isFilter?string('onchange=selectCategory()',' ')} >
    <#if isFilter>
        <#if selectCategory??>
            <option value="-1" <@is_selected -1 selectCategory /> >Усі</option>
        <#else>
            <option value="-1">Усі</option>
        </#if>
    </#if>
        <#if selectCategory??>
            <#list categories as category>
                <option value="${category.id}" <@is_selected category.id selectCategory />>${category.name}</option>
            </#list>
        <#else>
            <#list categories as category>
                <option value="${category.id}">${category.name}</option>
            </#list>
        </#if>

    </select>
</#macro>

<#macro is_selected category selectCategory>
    <#if category==selectCategory>
        selected
    </#if>

</#macro>