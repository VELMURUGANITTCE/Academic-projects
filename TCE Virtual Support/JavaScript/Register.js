function change_register1() {
    var x = document.getElementById("student_register");
    var y = document.getElementById("staff_register");
    var a = document.getElementsByClassName("slider-tab")[0];
    var b = document.getElementsByClassName("slide")[0];
    var c = document.getElementsByClassName("staff_register")[0];
    x.style.display = "block";
    y.style.display = "none";
    a.style.left = 0;
    b.style.color = "#fff";
    c.style.color = "#000";
}
function change_register2() {
    var x = document.getElementById("student_register");
    var y = document.getElementById("staff_register");
    var a = document.getElementsByClassName("slider-tab")[0];
    var b = document.getElementsByClassName("slide")[0];
    var c = document.getElementsByClassName("staff_register")[0];
    x.style.display = "none";
    y.style.display = "block";
    a.style.left = "50%";
    b.style.color = "#000";
    c.style.color = "#fff";
}