<#import "../parts/common.ftl" as c>
<#import "publish_parts.ftl" as news>
<#include "../parts/security.ftl">

<@c.page>

    <div class="row  align-items-center">
        <div class="col-lg-4  mx-auto mt-5">
            <form class="form align-middle"
                  enctype="multipart/form-data"
                  action="/news/add" method="post">
                <label class="form-text">Заголовок:</label>
                <input class="form-control" type="text" placeholder="Назва новини"
                       name="title" value="${title}"  />
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <label class="form-text">Заставка для новини:</label>
                <input class="form-control" type="file" name="file"/>
                <button class="form-control mt-2" type="submit">Оновити</button>
            </form>
            <form class="form align-middle" action="/news/confirm" method="post">
                <input type="hidden" name="title" value="${title}"/>
                <input type="hidden" name="img_url" value="${imgUrl}"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="user" value="${user.id}"/>

            </form>
        </div>
        <div class="col-lg-6 mt-5">
            <div class="card mx-auto mt-3" style="width: 18rem;">
                <img class="card-img-top" style="height: 430px;"
                     src="${imgUrl}"
                     alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title">
                        <a  href="#" class="link link-secondary">
                            ${title}
                        </a>
                    </h5>
                </div>
            </div>
        </div>
    </div>
</@c.page>