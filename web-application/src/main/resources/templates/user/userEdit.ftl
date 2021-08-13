<#import "../parts/common.ftl" as c>

<@c.page>
    <div class="container-xxl pt-2 auto-min-height">
        <div class="row justify-content-center h-100">
            <form class="col align-self-center form-control" style="max-width: 480px;" action="/user" method="post">
                <h3 class="text-center col-12">Налаштування ролей користувача: "${user.username}" </h3>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Роль</th>
                        <th scope="col">Наявність</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list roles as role>
                        <tr>
                            <th scope="row"><label class="ms-1 form-check-label" for="check${role}">${role}</label></th>
                            <td>
                                <input id="check${role}"
                                       class="ms-5 form-check-input"
                                       type="checkbox"
                                       name="${role}" ${user.roles?seq_contains(role)?string("checked","")}/>
                            </td>
                        </tr>
                        <input id="check${role}" class="ms-5 form-check-input" type="checkbox"
                               name="${role}" ${user.roles?seq_contains(role)?string("checked","")}>
                        <label class="ms-1 form-check-label" for="check${role}">${role}</label>
                    </#list>
                    </tbody>
                </table>
                <input type="hidden" value="${user.id}" name="userId">
                <input type="hidden" value="${_csrf.token}" name="_csrf"/>
                <div class="container-xxl">
                    <div class="row justify-content-center">
                        <a href="/user" type="submit" class="col-5 mx-1 rounded my-3 btn btn-primary">Відмінити</a>
                        <button type="submit" class="col-5 mx-1 rounded my-3 btn btn-primary">Зберегти</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</@c.page>
