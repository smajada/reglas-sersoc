
const logoutmenu = document.querySelector('#logoutmenu');
const logoutmenuButton = document.querySelector('#logoutmenubutton');

document.addEventListener("mousedown", function(){
    logoutmenu.style.opacity = "0";
});
logoutmenuButton.addEventListener("mouseup", function () {
    logoutmenu.style.opacity = "1";
});

