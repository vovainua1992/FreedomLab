<#import "sys_message.ftl" as sys>
<#import "navbar.ftl" as n>
<#assign
scripts = []
footer_auto = true>

<#macro add script_tag>
    <#assign scripts= scripts+[script_tag]>
</#macro>

<#macro getAllScripts>
    <#list scripts as script>
        ${script}
    <#else>
    </#list>
</#macro>

<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>FreedomLab</title>

        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="_csrf" content="${_csrf.token}"/>

        <link rel="stylesheet" href="/static/content-editor.css">
        <link rel="stylesheet" href="/static/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
              crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="/static/content-tools.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body class="d-flex flex-column bg-light h-100">

        <@n.navbar></@n.navbar>
        <@sys.message />

        <#nested>

        <@footer/>
        <@getAllScripts/>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
                integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
                integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc"
                crossorigin="anonymous"></script>

    </body>
    </html>
</#macro >

<#macro mail_page>
    <!DOCTYPE html>
    <html lang="en" >
    <head>
        <meta charset="UTF-8">
        <title>FreedomLab</title>

        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="_csrf" content="${_csrf.token}"/>
    </head>
    <body style="width: 100%; height: 100%;background: linear-gradient(#fafae3,#d7d7c8);">
        <div style="width: 80%;height: 100%; margin: auto;">
            <div style="width: 70%;height: 100%;margin-left: auto;"><#nested></div>
        </div>

    </body>
    </html>
</#macro>

<#macro footer_disable_auto>
    <#assign footer_auto = false/>
</#macro>

<#macro footer>
            <footer class="${footer_auto?string('mt-auto','')} py-3">
                <div class="text-center bg-light bg-gradient">
                    © 2021, FreedomLab. Всі права збережені.
                </div>
            </footer>
</#macro>

<#macro left_menu>
    <div class="col-2 ms-3">
        <#nested>
    </div>
</#macro>

<#macro main>
    <div class="col-9 me-5">
        <#nested>
    </div>

</#macro>

<#macro info_box>
    <div class="container">
        <div class="row my-auto">
            <div class="col-lg-6 mx-auto">
                <#nested>
            </div>
        </div>
    </div>
</#macro>


