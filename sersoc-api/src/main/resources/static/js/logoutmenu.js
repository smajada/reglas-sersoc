
const logoutmenu = document.querySelector('#logoutmenu');
const logoutmenuButton = document.querySelector('#logoutmenubutton');

document.addEventListener("mousedown", function(){
    console.log("clicked outside")
    logoutmenu.style.opacity = "0";
});
logoutmenuButton.addEventListener("mouseup", function () {
    console.log("clicked inside")
    logoutmenu.style.opacity = "1";
});

