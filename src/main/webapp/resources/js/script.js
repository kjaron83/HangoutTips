// JavaScript Document

function checkLocationIsLoaded(path) {
	$.ajax({
		"url": "/api/" + path,
		"dataType": "json",
	    "success": function (data) {
	        if ( data["success"] )
	        	location = "/" + path;
	        else
	        	window.setTimeout(function() { checkLocationIsLoaded(path); }, 2000);
	    },		
	});	
}