

function selectCategory(){
    let select_category=document.getElementById('categories').selectedIndex;
    let select_tags = document.getElementById('tags').children;
    let filterDto = {
        category_id : select_category,
        select_tags :Array.from(select_tags)
    }
    let xhr;
    let  payload = new FormData();
    payload.append('category_id',select_category);
    let xhrComplete = function (ev) {
        let response;
        if (ev.target.readyState != 4) {
            return;
        }
        xhr = null
        xhrComplete = null
        if (parseInt(ev.target.status) == 200) {
            response = ev.target.responseText;
            console.log(response)
        }
    }
    xhr = new XMLHttpRequest();
    xhr.open('POST', '/publishes/filter');
    xhr.setRequestHeader("x-csrf-token", token);
    xhr.addEventListener('readystatechange', xhrComplete);
    console.log(payload);
    xhr.send(payload);
}