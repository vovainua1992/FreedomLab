<#include "../parts/security.ftl">

<#macro avatar_box>
    <div class="container">
        <div class="row">
            <@avatar />
        </div>
    </div>
</#macro>

<#macro avatar>
    <img class="img-thumbnail" style="width:200px; height:200px;" src="/static/icons/image.svg"/>
</#macro>

<#macro author user>
    <div class="container-xl">
        <div class="row">
            <div class="offset-xl-10  col-xl-2">
                <h4 class="h4 text-muted">Автор: ${user.getUsername()}.</h4>
            </div>
        </div>
    </div>
</#macro>