const token = $("meta[name='_csrf']").attr("content");
let box = document.getElementById('avatar-edit');
const imageSelector = document.getElementById("image-selector");
let minX = 0, minY = 0, maxX, maxY;
let pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
let posTop, posLeft;
let elementSize;
let newSize;
let file;
const imageFile = document.getElementById("image-file");

dragElement(imageSelector);
imageFile.addEventListener("change", function () {
    getImgData();
resizeImageSelector()
});


function getImgData() {
    file = imageFile.files[0];
    if (file) {
        const fileReader = new FileReader();
        fileReader.readAsDataURL(file);
        fileReader.addEventListener("load", function () {
            box.src = this.result;
        });
    }
}

function resizeImageSelector() {
    updateMaxSize();
    elementSize = getMinSize();
    imageSelector.style.width = elementSize+'px';
    imageSelector.style.height =elementSize+'px';
    setX(-(elementSize/2+elementSize/2));
    setY(-(elementSize/2+elementSize/2));
}

function getMinSize() {
    if ( box.offsetWidth>box.offsetHeight)
        return box.offsetHeight;
    else
        return box.offsetWidth;
}

function setX(x){
    posLeft = x;
    if (x<minX){
        imageSelector.style.left = minX+'px';
    }else if(x>maxX){
        imageSelector.style.left = maxX+'px';
    }else {
        imageSelector.style.left = x + "px";
    }
}

function setY(y){
    posTop = y;
    if (y<minY){
        imageSelector.style.top = minY+'px';
    }else if(y>maxY){
        imageSelector.style.top = maxY+'px';
    }else {
        imageSelector.style.top = y + "px";
    }
}

function updateMaxSize() {
    elementSize = imageSelector.offsetWidth;
    maxX = box.offsetWidth-elementSize;
    maxY = box.offsetHeight-elementSize;
}

function dragElement(elmnt) {
    elmnt.onmousedown = dragMouseDown;
    elmnt.onwheel = dragMouseOver;


    function dragMouseOver(e){
        const maxSize=Math.min(box.offsetWidth,box.offsetHeight);
        elementSize = elmnt.offsetWidth;
        let delta = e.deltaY || e.detail || e.wheelDelta;
        delta = delta/10;
        newSize= elementSize +delta;
        if (maxSize>newSize&&20<newSize){
            elmnt.style.width = newSize+'px';
            elmnt.style.height = newSize+'px';
            updateMaxSize();
            setX(elmnt.offsetLeft-delta/2);
            setY(elmnt.offsetTop-delta/2);
            elementSize = newSize;
        }else {
        }

    }

    function dragMouseDown(e) {
        updateMaxSize();
        e = e || window.event;
        e.preventDefault();
        // get the mouse cursor position at startup:
        pos3 = e.clientX;
        pos4 = e.clientY;
        document.onmouseup = closeDragElement;
        // call a function whenever the cursor moves:
        document.onmousemove = elementDrag;
    }

    function elementDrag(e) {
        e = e || window.event;
        e.preventDefault();
        // calculate the new cursor position:
        pos1 = pos3 - e.clientX;
        pos2 = pos4 - e.clientY;
        posTop = elmnt.offsetTop - pos2;
        posLeft = elmnt.offsetLeft - pos1
        if (maxX > posLeft && minX < posLeft) {
            pos3 = e.clientX;
            elmnt.style.left = posLeft + "px";
        }

        if (maxY > posTop && minY < posTop) {
            pos4 = e.clientY;
            elmnt.style.top = posTop + "px";
        }

    }

    function closeDragElement() {
        /* stop moving when mouse button is released:*/
        document.onmouseup = null;
        document.onmousemove = null;
    }


}

function postAvatar(){
    if (!file)
        return;
    let xhr;
    let  payload = new FormData();
    payload.append('file',file);
    payload.append('size',elementSize);
    payload.append('posX',posLeft);
    payload.append('posY',posTop);
    let xhrComplete = function (ev) {
        var response;
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
    xhr.open('POST', '/user/avatar/update/');
    xhr.setRequestHeader("x-csrf-token", token);
    xhr.addEventListener('readystatechange', xhrComplete);
    console.log(payload);
    xhr.send(payload);
}
