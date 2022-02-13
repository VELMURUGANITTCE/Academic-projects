const searchBar = document.querySelector(".search input"),
searchIcon = document.querySelector(".search button"),
usersList = document.querySelector(".users-list");

searchIcon.onclick = ()=>{
  searchBar.classList.toggle("show");
  searchIcon.classList.toggle("active");
  searchBar.focus();
  if(searchBar.classList.contains("active")){
    searchBar.value = "";
    searchBar.classList.remove("active");
  }
}

searchBar.onkeyup = ()=>{
  let searchTerm = searchBar.value;
  if(searchTerm != ""){
    searchBar.classList.add("active");
  }else{
    searchBar.classList.remove("active");
  }
  let xhr = new XMLHttpRequest();
  xhr.open("POST", "BackEnd-Php/Search.php", true);
  xhr.onload = ()=>{
    if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status === 200){
          let data = xhr.response;
          usersList.innerHTML = data;
        }
    }
  }
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send("searchTerm=" + searchTerm);
}

setInterval(() =>{
  let xhr = new XMLHttpRequest();
  xhr.open("GET", "BackEnd-Php/Home.php", true);
  xhr.onload = ()=>{
    if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status === 200){
          let data = xhr.response;
          if(!searchBar.classList.contains("active")){
            usersList.innerHTML = data;
          }
        }
    }
  }
  xhr.send();
}, 500);

function ShowAllUsersList() {
    var rcl = document.getElementById("recent_user_list");
    var acl = document.getElementById("all_user_list");
    rcl.style.display = "none";
    acl.style.display = "block";
}
function HideAllUsersList() {
    var rcl = document.getElementById("recent_user_list");
    var acl = document.getElementById("all_user_list");
    rcl.style.display = "block";
    acl.style.display = "none";  
}
function PopUpUserDetail() {
    var pud = document.getElementsByClassName("PopUp-UserDetail")[0];
    pud.style.display = "block";
}
function HidePopUpUserDetail() {
    var pud = document.getElementsByClassName("PopUp-UserDetail")[0];
    pud.style.display = "none";
}
