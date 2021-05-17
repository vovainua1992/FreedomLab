<#import "../parts/common.ftl" as c>
<#import "../parts/log.ftl" as l>
<#include "../parts/security.ftl">


<@c.page>
    <div class="container-xl mt-5">
        <div class="d-flex  " style="width: 1248px; height: 700px;">
                <#include "../mail/mail.ftl" />
        </div>
    </div>

</@c.page>