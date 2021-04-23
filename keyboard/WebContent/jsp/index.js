function allFrameHidden() {
    document.getElementById("iframe_1").style.display="none";
    document.getElementById("iframe_2").style.display="none";
    document.getElementById("iframe_3").style.display="none";
}
function frame1_visible() {
    allFrameHidden();
    document.getElementById("iframe_1").style.display="inline";
}
function frame2_visible() {
    allFrameHidden();
    document.getElementById("iframe_2").style.display="inline";
}
function frame3_visible() {
    allFrameHidden();
    document.getElementById("iframe_3").style.display="inline";
}
