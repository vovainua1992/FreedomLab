<#import "../parts/common.ftl" as c>
<#import "publish_parts.ftl" as parts>

<@c.page>
    <div class="container-xl">
        <div class="row ">
            <ul class="nav nav-tabs tabs-left" id="myTab" role="tablist">
                <li class="bg-gradient bg-light  " role="presentation">
                    <button class="nav-tab nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#poster"
                            type="button " role="tab" aria-controls="home" aria-selected="true">Налаштування
                    </button>
                </li>
                <li class="bg-gradient bg-light " role="presentation">
                    <button class="nav-link nav-tab" id="profile-tab" data-bs-toggle="tab" data-bs-target="#content"
                            type="button" role="tab" aria-controls="profile" aria-selected="false">Додаткова інформація
                    </button>
                </li>
            </ul>
        </div>
        <div class="row">
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="poster" role="tabpanel"
                     aria-labelledby="home-tab"><@parts.publish_title publish true/></div>
                <div class="tab-pane fade h-100" id="content" role="tabpanel"
                     aria-labelledby="profile-tab"><@parts.info/></div>
            </div>
        </div>
    </div>
    <script src="/static/scripts/index.js"></script>
</@c.page>
