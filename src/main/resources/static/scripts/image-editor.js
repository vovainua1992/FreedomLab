const token = $("meta[name='_csrf']").attr("content");
 dragElement(document.getElementById("image-selector"));

function dragElement(elmnt) {
    const box = document.getElementById('avatar-edit')
    let minX = 0, minY = 0, maxX, maxY;
    let pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    let posTop, posLeft;
    let elementSize;
    let newSize;


    /* otherwise, move the DIV from anywhere inside the DIV:*/
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

    function setX(x){
        console.log(x)
        if (x<minX){
            elmnt.style.left = minX+'px';
        }else if(x>maxX){
            elmnt.style.left = maxX+'px';
        }else {
            elmnt.style.left = x + "px";
        }
    }

    function setY(y){
        if (y<minY){
            elmnt.style.top = minY+'px';
        }else if(y>maxY){
            elmnt.style.top = maxY+'px';
        }else {
            elmnt.style.top = y + "px";
        }
    }

    function updateMaxSize() {
        elementSize = elmnt.offsetWidth;
        maxX = box.offsetWidth-elementSize;
        maxY = box.offsetHeight-elementSize;
        console.log(maxX+'max');
        console.log(maxY+'max')

    }
}
