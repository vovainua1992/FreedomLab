<#macro login path isRegisterForm>
    <div class="container h-100">
        <div class="mx-auto">
            <#if !isRegisterForm>
                <h3 class="">Вхід</h3>
            <#else>
                <h3>Регістрація</h3>
            </#if>
        </div>
        <form class="row align-items-center form text-center" action="${path}" method="post"
              xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
            <div class="col-sm-4 align-self-center form-group ">
                <div class="row">
                    <label class="form-label w-25">Логін :</label>
                    <input type="text" name="username"
                           value="<#if user??>user.username</#if>"
                           class="w-75 form-control ${(usernameError??)?string('is-invalid', '')}"
                           placeholder="User name"/>
                    <#if usernameError??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-1 col-form-label">Пароль:</label>
                <div class="col-sm-2">
                    <input type="password" name="password"
                           class="form-control ${(passwordError??)?string('is-invalid', '')}"
                           placeholder="Password"/>
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
                </div>
            </div>
            <#if !isRegisterForm>
                <!--  <a href="/registration" class="link-secondary m-2 ">Registration</a>-->
            <#else>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label"> Password:</label>
                    <div class="col-sm-4">
                        <input type="password" name="password2"
                               class="form-control ${(password2Error??)?string('is-invalid', '')}"
                               placeholder="Password"/>
                        <#if password2Error??>
                            <div class="invalid-feedback">
                                ${password2Error}
                            </div>
                        </#if>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label"> Email:</label>
                    <div class="col-sm-4">
                        <input type="email" name="email"
                               value="<#if user??>user.email</#if>"
                               class="form-control ${(emailError??)?string('is-invalid', '')}"
                               placeholder="some@some.com"/>
                        <#if emailError??>
                            <div class="invalid-feedback">
                                ${emailError}
                            </div>
                        </#if>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="g-recaptcha" data-sitekey="6LdWiLsaAAAAAGGAc9o7RiWWDF2bWyJn9R70rBjF"></div>
                    <#if captchaError??>
                        <div class="alert alert-danger" role="alert">
                            ${captchaError}
                        </div>
                    </#if>
                </div>
            </#if>
            <button class="btn sm-1 btn-primary m-2 " type="submit">
                <#if isRegisterForm>
                    Зареєструватись
                <#else>
                    Увійти
                </#if>
            </button>
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