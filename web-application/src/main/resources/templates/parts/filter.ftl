<#macro search_messages>
    <div class="me-5">
        <form method="get" action="/main">
            <input type="text" name="filter" value="${filter?ifExists}">
            <button class="btn btn-outline-primary" type="submit">Пошук</button>
        </form>
    </div>
</#macro>