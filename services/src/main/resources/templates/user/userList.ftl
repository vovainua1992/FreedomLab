<#import "../parts/common.ftl" as c>

<@c.page>
    List of users
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/edit/${user.id}">edit</a></td>
                <td><a href="/user/delete/${user.id}">delete</a></td>
            </tr>
        <#else>
            Яке лихо немає користувачів
        </#list>
        </tbody>
    </table>
</@c.page>