<#import "../parts/common.ftl" as c>
<#import "../parts/log.ftl" as l>
<@c.page>
    ${message?ifExists}
    <div class="container h-100 text-center justify-content-center d-flex align-items-center">

        <form class="align-content-center align-self-center" action="/registration" method="post"
              xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html" style="width: 400px;">
            <h3 class="h3 mb-3 text-center">Регістрація</h3>
            <div class="form-floating shadow">
                <input type="text" name="username" id="loginFloat"
                       value="<#if user??>user.username</#if>"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="User name"/>
                <label for="loginFloat">Логін</label>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
            <div class="mt-2 form-floating shadow">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password" id="passwordFloat">
                <label for="passwordFloat">Пароль</label>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
            <div class="mt-2 form-floating shadow">
                <input type="password" name="password2" id="passwordConfirmFloat"
                       class="form-control ${(password2Error??)?string('is-invalid', '')}"
                placeholder="Password"/>
                <label for="passwordConfirmFloat">Підтвердження паролю</label>
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
            <div class="mt-2 form-floating  shadow">
                <input type="email" name="email" id="emailFloat"
                       value="<#if user??>user.email</#if>"
                       class="form-control ${(emailError??)?string('is-invalid', '')}"
                placeholder="some@some.com"/>
                <label for="emailFloat"> email:</label>
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
            <div class="mx-auto my-3 text-center">
                <div class="g-recaptcha align-content-center" style="display: inline-block;" data-sitekey="6LdWiLsaAAAAAGGAc9o7RiWWDF2bWyJn9R70rBjF"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
            <button class="btn-secondary btn w-50 mx-auto" type="submit">
                Зареєструватись
            </button>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </div>
</@c.page>