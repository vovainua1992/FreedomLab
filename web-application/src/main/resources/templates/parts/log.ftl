<#macro login path isRegisterForm>
    <div class="container text-center justify-content-center  d-flex" style="height: calc(100% - 80px);min-height:${isRegisterForm?string('750px','500px')}; ">
        <form class="align-content-center align-self-center" action="${path}" method="post"
              xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html" style="${isRegisterForm?string('','margin-top:-100px;')}width: 400px;">
            <div class="logo-b m-3" style="">Freedom</div>
            <h3 class="h3 mb-5 text-center">${isRegisterForm?string('Регістрація','Авторизація')}</h3>
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
                       class="form-control

                        ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password" id="passwordFloat">
                <label for="passwordFloat">Пароль</label>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
            <#if isRegisterForm>
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
                <div class="mx-auto my-5 text-center">
                    <div class="g-recaptcha align-content-center" style="display: inline-block;"
                         data-sitekey="6LdWiLsaAAAAAGGAc9o7RiWWDF2bWyJn9R70rBjF"></div>
                    <#if captchaError??>
                        <div class="alert alert-danger" role="alert">
                            ${captchaError}
                        </div>
                    </#if>
                </div>
            </#if>
            <button class="btn-secondary btn w-50 mx-auto  my-4" type="submit">
                ${isRegisterForm?string('Зареєструватись','Увійти')}
            </button>
            <a class=" link-secondary px-4 mx-5 pb-5" style="" href="/registration">Регістрація</a>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </div>
</#macro>

<#macro logout>
    <form class="me-3 " action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input class="dropdown-item" type="submit" value="Вихід"/>
    </form>
</#macro>
