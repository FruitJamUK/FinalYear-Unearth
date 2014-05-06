var latitude = 0;
var longitude = 0;
var q = "";

function get_location() {
console.log("get_location entered");
if (navigator.geolocation){ //offer local search
  navigator.geolocation.getCurrentPosition(show_map);
  }
else if(!navigator.geolocation){console.log("no geolocation");}
}

function show_map(position) {
  console.log("show_map entered");
  latitude = position.coords.latitude;
  console.log("latitude="+latitude);
  longitude = position.coords.longitude;
  console.log("longitude="+longitude);
  
//ungrey button
  $("#ltlnSubmit").removeClass("disabled");
}

function no_info() {
	console.log("no_info entered");
}

function localSearch() {
	//console.log(latitude+" "+longitude);
	window.location = "/results/"
					+latitude+"/"+longitude;
}	

function querySearch() {
	q = $("#query:input").val();
	//console.log(q);
	if(q!==null&&q!==""){
	window.location = "/results/"
							+q;}
}

//single function should handle other function calls
//console.log("before get_location");
get_location(); //stick in button
//console.log("after get_location");

$(document).ready(function(){
$("#query").bind("keypress",
		function(e){ if(e.keyCode==13){querySearch();} });
$("#querySubmit").click(querySearch);
$("#ltlnSubmit").click(localSearch);
});