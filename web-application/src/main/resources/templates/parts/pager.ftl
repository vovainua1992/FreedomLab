<#macro pager url page>
    <#if page.getTotalPages() gt 7>
        <#assign
        totalPages = page.getTotalPages()
        pageNumber = page.getNumber() + 1

        head = (pageNumber > 4)?then([1, -1], [1, 2, 3])
        tail = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])
        bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1], [])
        bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2], [])

        body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tail
        >
    <#else>
        <#assign body = 1..page.getTotalPages()>
    </#if>


    <ul class="pagination" style="padding-left: 50%!important;">
        <#if page.hasPrevious()>
            <li class="page-item">
                <a class="page-link" href="${url}?page=${page.previousPageable().getPageNumber()}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </#if>
        <#list body as p>
            <#if (p -1)==page.getNumber()>
                <li class="page-item active"><a class="page-link" href="#">${p}</a></li>
            <#elseif p == -1>
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">...</a>
                </li>
            <#else>
                <li class="page-item"><a class="page-link" href="${url}?page=${p-1}">${p}</a></li>
            </#if>
        </#list>
        <#if page.hasNext()>
            <li class="page-item">
                <a class="page-link" href="${url}?page=${page.nextPageable().getPageNumber()}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </#if>

    </ul>
</#macro>
