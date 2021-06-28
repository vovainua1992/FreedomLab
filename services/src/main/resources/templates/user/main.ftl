<#import "../parts/common.ftl" as c>
<#include "../parts/security.ftl">

<@c.page>
    <div class="container-fluid h-100 bg-gradient bg-white">
        <div class="modal fade" id="select-avatar" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">

                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Вибір та налаштування аватару</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div id="avatar-edit-box" class="d-block position-relative mx-auto" style="width: 300px;">
                            <img id = "avatar-edit" src="/static/icons/image.svg"
                                 style="width: 100%; height: auto;"/>
                            <div id="image-selector" class="border rounded-circle shadow-lg border-gray position-absolute"
                                 style="
                                 top: 100px;
                                 right: 100px;
                                 width: 100px;
                                 height: 100px;border-style: dashed!important;">
                            </div>
                        </div>
                        (Знаходиться у розробці)
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрити</button>
                        <!-- Кнопка відкривання меню завантаження аватара
                        <button type="button"  data-bs-toggle="modal" data-bs-dismiss="modal"
                                data-bs-target="#load-avatar" class="btn btn-primary">Завантажити аватар</button>
                                -->
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="load-avatar" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        ...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row row-cols-2 h-100">
            <div class="pe-3 pt-1 pb-3 col-sm-6 col-md-6  col-lg-3 col-12 col-xl-2 col-xxl-1 h-100 sticky-top">
                <div class="row shadow border rounded-right-5 border-1 bg-light sticky-top"
                     style="border-color: #747e7b;">
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
            <div class="col-sm-6 p-3 col-md-6  col-lg-10 col-12 col-xl-2  col-xxl-1 h-100">
                <div class="row shadow mb-3 rounded-5 border border-1 bg-light">
                    <div class="col-12 mt-2">
                        <input class="w-100 my-2 px-2" data-bs-toggle="modal"
                               data-bs-target="#select-avatar" type="image" src="/static/icons/image.svg"/>
                    </div>
                    <div class="col-12 my-2  text-center">Добавити в друзі</div>
                    <#if !isCurrentUser>
                        <#if isSubscriber>
                            <a class="text-dark my-2 text-center text-decoration-none"
                               href="/user/unsubscribe/${account.id}" class="col-12 my-2 text-center">Відписатись</a>
                        <#else >
                            <a class="text-dark my-2 text-center text-decoration-none"
                               href="/user/subscribe/${account.id}" class="col-12 my-2 text-center">Підписатись</a>
                        </#if>
                    </#if>
                </div>

                <div class="row shadow mb-3 rounded-5 border border-1 bg-light">
                    <div class="col-12 my-2  text-center">Підписники
                        <a href="/user/subscribers/${account.id}"
                           class="d-block text-decoration-none ms-2 position-relative text-muted small">
                            ${subscribers_count}
                            <span class="badge position-absolute  translate-middle bg-info"
                                  style="left: 120px;top: 5px; font-size: 8px;">
                                ${subscribers_dif}
                            </span>
                        </a>
                    </div>
                </div>

                <div class="row shadow mb-3 rounded-5 border border-1 bg-light">
                    <div class="col-12 my-2 text-center">Підписки
                        <a href="/user/subscriptions/${account.id}"
                           class="d-block text-decoration-none ms-2 position-relative text-muted small">${subscriptions_count}
                        </a>
                    </div>
                </div>
            </div>
            <div class="p-3 col-sm-12 col-md-8 col-lg-6 col-12 col-xl-8  col-xxl-10">
                <div class="row shadow-lg rounded-5 border border-1 bg-light h-100">
                    <h3 class="h3 text-center">${account.username}</h3>
                    <div class="col-12 h-20 text-center">Інформація профілю</div>
                    <div class="col-12 h-20 text-center">Фото</div>
                    <div class="col-12 h-75 text-center">Коментарі</div>

                </div>
            </div>
        </div>
    </div>
    <script src="/static/scripts/image-editor.js"></script>
</@c.page>