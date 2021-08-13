<#import "../parts/common.ftl" as c>
<#import "../parts/pager.ftl" as p>
<@c.page>
    <div class="container-xxl ">
        <div class="row"><div class="my-3 col h3 text-center">Список користувачів</div></div>
        <table class="table mx-auto" style="max-width: 960px;">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Role</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <#list users.content as user>
                <tr>
                    <th scope="row">${user.username}</th>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td>
                        <a class="btn mx-1 btn-primary" href="/user/edit/${user.id}">edit</a>
                        <a class="btn mx-1 btn-primary" href="/user/delete/${user.id}">delete</a>
                    </td>
                </tr>
            <#else>
                Яке лихо немає користувачів
            </#list>
            </tbody>
        </table>
        <@p.pager "/user" users/>
    </div>
</@c.page>
