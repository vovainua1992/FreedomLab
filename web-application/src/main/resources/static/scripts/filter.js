const token = $("meta[name='_csrf']").attr("content");

function selectCategory(){
    let select_category=document.getElementById('categories').selectedIndex;
    let select_tags = document.getElementById('tags').children;
    let filterDto = {
        categoryId : select_category,
        tags : Array.from(select_tags)
    }
    console.log(select_category);
    let xhr;
    let  payload = new FormData();
    console.log(filterDto)
    payload.append('filter',JSON.stringify(filterDto));
    let xhrComplete = function (ev) {
        let response;
        if (ev.target.readyState != 4) {
            return;
        }
        xhr = null
        xhrComplete = null
        if (parseInt(ev.target.status) == 200) {
            response = ev.target.responseText;
            let parser = new DOMParser();

            // Parse the text
            let doc = parser.parseFromString(response, "text/html");

            console.log(doc);
            document.body.parentNode.replaceChild(doc.body, document.body);
        }
    }
    xhr = new XMLHttpRequest();
    xhr.open('POST', '/publish/filter');
    xhr.setRequestHeader("x-csrf-token", token);
    xhr.addEventListener('readystatechange', xhrComplete);
    console.log(payload);
    xhr.send(payload);
}