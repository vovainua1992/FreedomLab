<#import "../parts/common.ftl" as c>

<@c.page>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <div class="mt-1 h-100 text-center">
        <main class="form-signin h-100 d-flex align-items-center">
            <form action="/login" method="post"
                  xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html"
                  class="align-middle">
                <img class="mb-4  " src="/static/images/logo.png" alt="" width="270" height="60">
                <h1 class="h3 mb-3 fw-normal">Авторизація</h1>
                <div class="form-floating mb-2 shadow">
                    <input type="text" name="username"
                           value="<#if user??>user.username</#if>" class="form-control" id="floatingInput"
                           placeholder="User name">
                    <label for="floatingInput">Логін</label>
                </div>
                <div class="form-floating mb-2 shadow">
                    <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                           name="password">
                    <label for="floatingPassword">Пароль</label>
                </div>
                <button class="w-75 btn btn-lg btn-primary my-5 shadow" type="submit">Війти</button>
                <a class="mt-5 link-secondary" href="/registration">Регістрація</a>
                <p class="mt-5 mb-3 text-muted">©2021</p>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
        </main>
    </div>
</@c.page>
