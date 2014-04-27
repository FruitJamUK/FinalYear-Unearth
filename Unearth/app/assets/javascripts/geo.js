function get_location() {
console.log("get_location entered");
if (navigator.geolocation){ //offer local search
  navigator.geolocation.getCurrentPosition(show_map);}
else {console.log("no geolocation");} //maybe display no geolocation?
}

function show_map(position) {
  console.log("show_map entered");
  var latitude = position.coords.latitude;
  var longitude = position.coords.longitude;
}

function no_info() {
	console.log("no_info entered");
	//grey out button
	
	//var latitude = false;
	//var longitude = false;
	
	//do nothing? no redirect at least
}

function localSearch() {
  //redirect to JSON route (temporary)
  <!--
	window.location.href = "/goog/"+latitude+"/"+longitude;
	//--> //move this out of function? move to button functionality (ungrey button)
}	

//single function should handle other function calls
console.log("before get_location");
//get_location(); //stick in button
console.log("after get_location");