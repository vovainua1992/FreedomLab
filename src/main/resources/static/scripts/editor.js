const token = $("meta[name='_csrf']").attr("content");
let id_news;
editor = ContentTools.EditorApp.get();
editor.init('*[data-editable]', 'data-name');
ContentTools.IMAGE_UPLOADER = imageUploader;
function myFunc(){
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

    // Set up the event handlers

    dialog.addEventListener('imageuploader.fileready', function (ev) {

        // Upload a file to the server
        var formData;
        var file = ev.detail().file;

        // Define functions to handle upload progress and completion
        xhrProgress = function (ev) {
            // Set the progress for the upload
            dialog.progress((ev.loaded / ev.total) * 100);
        }

        xhrComplete = function (ev) {
            var response;

            // Check the request is complete
            if (ev.target.readyState != 4) {
                return;
            }

            // Clear the request
            xhr = null
            xhrProgress = null
            xhrComplete = null

            // Handle the result of the upload
            if (parseInt(ev.target.status) == 200) {
                // Unpack the response (from JSON)
                response = JSON.parse(ev.target.responseText);
                console.log(response.url)
                // Store the image details
                image = {
                    size: response.size,
                    url: response.url
                };

                // Populate the dialog
                dialog.populate(image.url, image.size);

            } else {
                // The request failed, notify the user
                new ContentTools.FlashUI('no');
            }
        }

        // Set the dialog state to uploading and reset the progress bar to 0
        dialog.state('uploading');
        dialog.progress(0);

        // Build the form data to post to the server
        formData = new FormData();
        formData.append('image', file);
        // Make the request
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

        // Define a function to handle the request completion
        xhrComplete = function (ev) {
            // Check the request is complete
            if (ev.target.readyState !== 4) {
                return;
            }

            // Clear the request
            xhr = null
            xhrComplete = null

            // Free the dialog from its busy state
            dialog.busy(false);

            // Handle the result of the rotation
            if (parseInt(ev.target.status) === 200) {
                // Unpack the response (from JSON)
                var response = JSON.parse(ev.target.responseText);

                // Trigger the save event against the dialog with details of the
                // image to be inserted.
                dialog.save(
                    response.url,
                    response.size,
                    {
                        'alt': response.alt,
                        'data-ce-max-width': response.size[0]
                    });

            } else {
                // The request failed, notify the user
                new ContentTools.FlashUI('no');
            }
        }

        // Set the dialog to busy while the rotate is performed
        dialog.busy(true);

        // Build the form data to post to the server
        formData = new FormData();
        formData.append('url', image.url);

        // Set the width of the image when it's inserted, this is a default
        // the user will be able to resize the image afterwards.
        formData.append('width', 600);

        // Check if a crop region has been defined by the user
        if (dialog.cropRegion()) {
            formData.append('crop', dialog.cropRegion());
        }

        // Make the request
        xhr = new XMLHttpRequest();
        xhr.addEventListener('readystatechange', xhrComplete);
        xhr.open('POST', '/image/insert', true);
        xhr.setRequestHeader("x-csrf-token", token);
        xhr.send(formData);
    });

    dialog.addEventListener('imageuploader.clear', function () {
        // Clear the current image
        dialog.clear();
        image = null;
    });
}

window.addEventListener('load', function() {
    var editor;

});

editor.addEventListener('saved', function (ev) {
    var name, payload, regions, xhr;

    myFunc();
    // Check that something changed
    regions = ev.detail().regions;
    if (Object.keys(regions).length == 0) {
        return;

    }
    // Set the editor as busy while we save our changes

    this.busy(true);
    // Collect the contents of each region into a FormData instance
    payload = new FormData();

    //payload.append('__page__', window.location.pathname);


    let jsonDate = {
        name: "first",
        imgUrl: "/static",
        regions: []
    };
    for (name in regions) {
        jsonDate.regions.push({"name": name,"region": regions[name]});
        payload.append(name, regions[name]);


    }
    // Send the update content to the server to be saved
    function onStateChange(ev) {
        // Check if the request is finished
        if (ev.target.readyState == 4) {
            editor.busy(false);
            if (ev.target.status == '200') {
                // Save was successful, notify the user with a flash
                new ContentTools.FlashUI('ok');
            } else {
                // Save failed, notify the user with a flash
                new ContentTools.FlashUI('no');
            }
        }



    };
    xhr = new XMLHttpRequest();
    xhr.addEventListener('readystatechange', onStateChange);
    xhr.open('POST', '/news/update/'+id_news);
    xhr.setRequestHeader("x-csrf-token", token);
    xhr.setRequestHeader("Content-Type", "application/json; charset=utf-8");
    console.log(jsonDate)
    xhr.send(JSON.stringify(jsonDate));
});
