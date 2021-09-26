<#import "../parts/common.ftl" as c>
<#import "../parts/pager.ftl" as p>
<@c.page>
    <div class="container-xxl ">
        <div class="row"><div class="my-3 col h3 text-center">${isSubscribers?string('Список підписників','Ви підписані на такі канали')}</div></div>
        <table class="table mx-auto" style="max-width: 960px;">
            <thead>
            <tr>
                <th scope="col">${isSubscribers?string('Підписник','Канал')}</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <#list subscribers.content as subscriber>
                <tr>

                    <th scope="row">
                        <div class="d-inline">${subscriber.username}</div>
                        <img class="author-avatar rounded-circle d-inline"
                             <#if subscriber.avatar??>src="${subscriber.avatar.small.url}"
                             <#else>src="/static/icons/image.svg"</#if>/>
                    </th>
                    <td>
                        <#if user.equals(subscriber)>
                            <#elseif isSubscribers>

                        <#elseif subscriber.isSubscriber(user)>
                            <a class="btn p-1 display-7 btn-outline-secondary"
                               href="/subscribe/remove/${subscriber.id}">Відписатися</a>
                        <#else>
                            <a class="btn p-1 display-7 btn-outline-secondary" href="/subscribe/add/${subscriber.id}">Підписатися</a>
                        </#if>
                    </td>
                </tr>
            <#else>
              <td>
                  ${isSubscribers?string('У вас немає підписників','Ви ще ні накого не підписались')}
              </td>
            </#list>
            </tbody>
        </table>
        <@p.pager url subscribers/>
        <@c.footer_disable_auto/>
    </div>
</@c.page>
