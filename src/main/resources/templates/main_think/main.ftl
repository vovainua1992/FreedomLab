<#import "../parts/common.ftl" as c>
<#import "../parts/log.ftl" as l>
<#import "../parts/message.ftl" as mess>
<#import "../parts/user.ftl" as user >
<@c.page>
    <@c.left_menu>
    <@user.avatar_box/>
    </@c.left_menu>

    <@c.main>
        <@mess.write/>
        <@mess.list/>
    </@c.main>
</@c.page>