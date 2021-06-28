<#macro write>
    <#assign tags =[]>
    <p>
        <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThink"
                aria-expanded="false" aria-controls="collapseThink">
            Поділитись думкою.
        </button>
    </p>
    <div class="collapse <#if message??>show</#if>" id="collapseThink">
        <div class="card card-body">
            <div>
                <div class="container mx-auto">
                    <form method="post" enctype="multipart/form-data">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <div class="row">
                            <div class="col-lg-8">
                                <input class="form-control ${(textError??)?string('is-invalid', '')}"
                                       value="<#if message??>${message.text}</#if>" style="width:100%;" type="text"
                                       name="text" placeholder="Текст повідомлення"/>
                                <#if textError??>
                                    <div class="invalid-feedback">
                                        ${textError}
                                    </div>
                                </#if>
                            </div>
                            <div class="col-lg-2">
                                <button type="submit">Відправити</button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col mt-3">
                                <p>
                                    <a data-bs-toggle="collapse" href="#collapseAddFile" role="button"
                                       aria-expanded="false" aria-controls="collapseAddFile">
                                        <img class="" src="/static/icons/paperclip.svg" style="width:35px;"/>
                                    </a>
                                </p>
                                <div class="collapse" id="collapseAddFile">
                                    <div class="card card-body">
                                        <div>
                                            <input type="file" name="file"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <label class="form-label">Теги :</label>
                        </div>
                        <p>
                            <a class="" data-bs-toggle="collapse" href="#collapseAddTag" role="button"
                               aria-expanded="false" aria-controls="collapseAddTag">
                                <img class="img-thumbnail" src="/static/icons/plus.svg" style="width:25px;"/>
                            </a>
                        </p>
                        <div class="collapse my-3 row <#if message??>show</#if>" id="collapseAddTag">
                            <div class="card card-body">
                                <div>
                                    <input type="text" name="tag" placeholder="Тег"
                                           value="<#if message??>${message.tag}</#if>"
                                           class="form-control  ${(tagError??)?string('is-invalid', '')}"/>
                                    <#if tagError??>
                                        <div class="invalid-feedback">
                                            ${tagError}
                                        </div>
                                    </#if>
                                    <button class="btn btn-primary">Добавити</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</#macro>

<#macro print message>
    <div class="col me-1">
        <b>${message.id}</b>
        <span>${message.text}</span>
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
        <div>
            <#if message.filename??>
                <img src="/img/${message.filename}" width="100px;" height="100px;">
            </#if>
        </div>
    </div>
</#macro>
<#macro list>
    <div>Топ дописів</div>
    <#list messages as message>
        <@print message />
    <#else>
        Немає повідомленнь
    </#list>
</#macro>

