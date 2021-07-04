<#import "../parts/common.ftl" as c>
<#include "../parts/security.ftl">

<@c.page>
    <div class="container-fluid h-100 bg-gradient bg-white">
        <div class="row row-cols-2 h-100">
            <div class="p-3 col-sm-6 col-md-6  col-lg-3 col-12 col-xl-2 col-xxl-1 h-100">
                <div class="row shadow  rounded-5 border border-1 bg-light"
                     style="border-color: #747e7b; position: relative;">
                    <div class="col-12 my-2 text-center">Публікації</div>
                    <div class="col-12 my-2 text-center">Повідомлення</div>
                    <div class="col-12 my-2 text-center">Анонімне листування</div>
                    <div class="col-12 my-2 text-center">Анонімний чат</div>
                    <div class="col-12 my-2 text-center">Оголошення</div>
                    <div class="col-12 my-2 text-center">Робота</div>
                    <div class="col-12 my-2 text-center">Нотатки</div>
                    <div class="col-12 my-2 text-center">Пости</div>
                    <div class="col-12 my-2 text-center">Друзі</div>
                    <div class="col-12 my-2 text-center">Підписники</div>
                    <div class="col-12 my-2 text-center">Групи</div>
                    <div class="col-12 my-2 text-center">Фотографії</div>
                </div>
            </div>
            <div class="p-3 col-sm-6 col-md-6  col-lg-9 col-12 col-xl-10  col-xxl-11 h-100">
                <div class="text-center">
                    <h3 class="h3">
                        <#if isCurrentUser>
                            Ваші ${isSubscribers?string('підписники','підписки')}
                        <#else>
                            ${isSubscribers?string('Підписники','Підписки')} ${account.username}
                        </#if>
                        <div class="row m-5 row-cols-md-2 row-cols-sm-1 row-cols-lg-3 row-cols-xl-5 row-cols-xxl-6">
                            <#list subscribers as user>

                                <div class="card col m-2" style="width: 18rem; height: 100px;">
                                    <img src="/static/icons/image.svg" class="card-img-overlay rounded-circle "
                                         style="width: 100px;">
                                    <div class="ms-5  card-body h-100">
                                        <p class="ms-4 my-3 card-text">${user.username}</p>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </h3>
                </div>
            </div>
        </div>
    </div>
</@c.page>