<#import "../parts/common.ftl" as c>
<#import "publish_parts.ftl" as p>
<#import "../parts/pager.ftl" as pager>


<@c.page>
    <@filter categories tags![]></@filter>
    <div class="container-xxl ${isMy?string('pt-5','')}">
        <#if isMy>
        <div class="border-dark border rounded shadow bg-white table-responsive-md">
            <table class="table table-hover mx-auto my-0">
                <thead>
                <tr>
                    <th scope="col">id</th>
                    <th scope="col">Імя</th>
                    <th scope="col">Дата створення</th>
                    <th scope="col">Дата публіквання</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                </#if>
                <#list publishes.content as publish>
                    <#if !isMy>
                        <@p.publish_title  publish false></@p.publish_title>
                    <#else>
                        <tr>
                            <th scope="row">${publish.id}</th>
                            <td>${publish.titleNames}</td>
                            <td><#if publish.dateCreate??>${publish.dateToString(publish.dateCreate)}<#else>Дуже давно</#if></td>
                            <td><#if publish.datePublication??>${publish.dateToString(publish.datePublication)}<#else>Не опубліковано</#if></td>
                            <td>
                                <a class="mx-1" href="/publish/${publish.id}">
                                    <img src="/static/icons/pencil.svg" style="width: 15px; height: 15px;"/>
                                </a>
                            </td>
                            <td>
                                <a class="mx-1" href="/publish/delete/${publish.id}">
                                    <img src="/static/icons/trash.svg" style="width: 15px;  height: 15px;"/>
                                </a>
                            </td>
                        </tr>

                    </#if>
                <#else>
                    <th>
                        <div class="text-center justify-content-center h-100">
                            <h3>Немає публікацій</h3>
                        </div>
                    </th>
                </#list>
                <#if isMy>
                </tbody>
            </table>
        </div>
        </#if>

    </div>
    <div class="container-xxl mt-auto">
        <div class="row">
            <div class="col"><@pager.pager url publishes/></div>
        </div>
    </div>
    <@c.footer_disable_auto/>
    <@c.add '<script src="/static/scripts/filter.js"></script>'/>
</@c.page>

<#macro filter categories tags>
    <div class="container-xxl mt-2 ">
        <div class="row mx-3 py-1 border-blue rounded-5 bg-white">
            <div class="col">
                <label for="categories">Категорії:</label>
                <select name="categories" id="categories" onchange="selectCategory()">
                    <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>
                </select>

            </div>
            <div class="col">
                <label for="tags">Теги:</label>
                <datalist id="tags">
                    <#list tags as tag>
                        <div class="btn btn-outline-light">${tag}</div>
                    <#else>

                    </#list>
                </datalist>
            </div>
        </div>
    </div>
</#macro>




<!-- Модальне меню для видалення публікацій-->
<#macro create_publish_modal >
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Видалення публікації</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Ви бажаэте видалити публыкацію -"${publish.titleNames}"</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Відмінити</button>
                    <a class="btn mx-1 btn-primary" href="/user/delete/${publish.id}">delete</a>
                </div>
            </div>
        </div>
    </div>
</#macro>