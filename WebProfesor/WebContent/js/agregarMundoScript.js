/**
 * 
 */

/****FUNCION PARA GUARDAR EL MUNDO EN EL SERVIDOR****/
function guardarMundo(){
	var nombre = $('#nombre').val();
	var exp = $('#experiencia').val();
	var desc = $('#descripcion').val();
	var URLimagen = $('#imagen').val();
	var settings = {
			  "async": true,
			  "crossDomain": true,
			  "url": getUrl("agregarmundo?nombre=" + nombre + "&imagen=" + URLimagen + "&exp=" + exp + "&desc=" + desc),
			  "method": "POST",
			  "headers": {
			    "cache-control": "no-cache",
			    "Access-Control-Allow-Origin": "*",
			  }
			}

			$.ajax(settings).done(function (response) {
				alert ("Mundo Agregado");
				window.location.href = "../jsp/VerEstadisticas.jsp"
			});
}
