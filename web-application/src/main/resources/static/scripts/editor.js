const token = $("meta[name='_csrf']").attr("content");
let id_news;
editor = ContentTools.EditorApp.get();
editor.init('*[data-editable]', 'data-name');
ContentTools.IMAGE_UPLOADER = imageUploader;




function addImagesClass(){
    const list = document.getElementById('edit').getElementsByTagName("IMG");
    for (element of list){
        element.classList.add("ce-element--type-image");
    }
    const list2 = document.getElementById('edit').getElementsByTagName("IFRAME");
    for (element of list2){
        element.classList.add("ce-element--type-image");
    }
}

function putNews(id){
    id_news = id;
}

function imageUploader(dialog) {
    var image, xhr, xhrComplete, xhrProgress;
    dialog.addEventListener('imageuploader.fileready', function (ev) {
        var formData;
        var file = ev.detail().file;
        xhrProgress = function (ev) {
            dialog.progress((ev.loaded / ev.total) * 100);
        }
        xhrComplete = function (ev) {
            var response;
            if (ev.target.readyState != 4) {
                return;
            }
            xhr = null
            xhrProgress = null
            xhrComplete = null
            if (parseInt(ev.target.status) == 200) {
                response = JSON.parse(ev.target.responseText);
                console.log(response.url)
                image = {
                    size: response.size,
                    url: response.url
                };

                dialog.populate(image.url, image.size);
            } else {
                new ContentTools.FlashUI('no');
            }
        }
        dialog.state('uploading');
        dialog.progress(0);
        formData = new FormData();
        formData.append('image', file);
        formData.append("test","1")
        xhr = new XMLHttpRequest();
        xhr.upload.addEventListener('progress', xhrProgress);
        xhr.addEventListener('readystatechange', xhrComplete);
        xhr.open('POST', '/image/add', true);
        xhr.setRequestHeader("x-csrf-token", token);
        xhr.send(formData);
    });

    dialog.addEventListener('imageuploader.save', function () {
        var crop, cropRegion, formData;
        xhrComplete = function (ev) {
            if (ev.target.readyState !== 4) {
                return;
            }
            xhr = null
            xhrComplete = null
            dialog.busy(false);
            if (parseInt(ev.target.status) === 200) {
                var response = JSON.parse(ev.target.responseText);
                dialog.save(
                    response.url,
                    response.size,
                    {
                        'alt': response.alt,
                        'data-ce-max-width': response.size[0]
                    });

            } else {
                new ContentTools.FlashUI('no');
            }
        }
        dialog.busy(true);
        formData = new FormData();
        formData.append('url', image.url);
        formData.append('width', 600);
        if (dialog.cropRegion()) {
            formData.append('crop', dialog.cropRegion());
        }
        xhr = new XMLHttpRequest();
        xhr.addEventListener('readystatechange', xhrComplete);
        xhr.open('POST', '/image/insert', true);
        xhr.setRequestHeader("x-csrf-token", token);
        xhr.send(formData);
    });
    dialog.addEventListener('imageuploader.clear', function () {
        dialog.clear();
        image = null;
    });
}

editor.addEventListener('saved', function (ev) {
    var name, payload, regions, xhr;
    addImagesClass();
    regions = ev.detail().regions;
    if (Object.keys(regions).length == 0) {
        return;
    }
    this.busy(true);
    payload = new FormData();
    let jsonDate = {
        name: "first",
        imgUrl: "/static",
        regions: []
    };
    for (name in regions) {
        jsonDate.regions.push({"name": name,"region": regions[name]});
        payload.append(name, regions[name]);
    }
    function onStateChange(ev) {
        if (ev.target.readyState == 4) {
            editor.busy(false);
            if (ev.target.status == '200') {
                new ContentTools.FlashUI('ok');
            } else {
                new ContentTools.FlashUI('no');
            }
        }
    };
    xhr = new XMLHttpRequest();
    xhr.addEventListener('readystatechange', onStateChange);
    xhr.open('POST', '/publish/update/'+id_news);
    xhr.setRequestHeader("x-csrf-token", token);
    xhr.setRequestHeader("Content-Type", "application/json; charset=utf-8");
    console.log(jsonDate)
    xhr.send(JSON.stringify(jsonDate));
});
