function ListDams() {
    for(i=0;i<dam_data.length;i++) {
        var dam_name = dam_data[i].Name;
        document.getElementById("ListDams").innerHTML += "<a class='grid-item' href='dam_profile.html?"+i+"'><b>"+dam_name+"</b></a>";
    }
}
function ShowDamProfile() {
    var dam_name = dam_data[name].Name;
    var dam_river = dam_data[name].River;
    var dam_location = dam_data[name].Location;
    document.getElementById("DamName").innerHTML = dam_name;
    document.getElementById("DamRiver").innerHTML = dam_river;
    document.getElementById("DamLocation").innerHTML = dam_location;
/*  var dam_ph = dam_data[name].Data.Ph;
    var dam_level = dam_data[name].Data.Level;
    var dam_temperature = dam_data[name].Data.Temperature;
    var dam_flow = dam_data[name].Data.Flow;
    document.getElementById("DamPh").innerHTML = dam_ph;
    document.getElementById("DamLevel").innerHTML = dam_level;
    document.getElementById("DamTemperature").innerHTML = dam_temperature;
    document.getElementById("DamFlow").innerHTML = dam_flow;
*/
}
function ListOfDamsData() {
 /* 
     for(i=0;i<dam_data.length;i++) {
        var dam_name = dam_data[i].Name;
        var dam_river = dam_data[i].River;
        var dam_location = dam_data[i].Location;
        var dam_ph = dam_data[i].Data.Ph;
        var dam_level = dam_data[i].Data.Level;
        var dam_temperature = dam_data[i].Data.Temperature;
        var dam_flow = dam_data[i].Data.Flow;
        document.getElementById("DamsData").innerHTML += "<tr class='table_tr4'><td>"+dam_name+"</td><td>"+dam_river+"</td><td>"+dam_location+"</td><td>"+dam_ph+"</td><td>"+dam_level+"</td><td>"+dam_temperature+"</td><td>"+dam_flow+"</td></tr>";
    }
*/
}


function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}
