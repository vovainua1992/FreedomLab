const imageFile = document.getElementById("image-file");
const imgPreview = document.getElementById("image");
const input = document.getElementById('input-text');
const title = document.getElementById('title');
const checkbox = document.getElementById('checkbox');
const activeText = document.getElementById('active-text');

imageFile.addEventListener("change", function () {
    getImgData();
});
function getImgData() {
    const files = imageFile.files[0];
    if (files) {
        const fileReader = new FileReader();
        fileReader.readAsDataURL(files);
        fileReader.addEventListener("load", function () {
            imgPreview.src = this.result;
        });
    }
}

input.addEventListener('input', updateValue);

function updateValue(e) {
    title.textContent = e.target.value;
}

function checkActive(){
    if(checkbox.checked===true){
        activeText.textContent = 'Активована';
    }else {
        activeText.textContent = 'Деактивована';
    }
}
