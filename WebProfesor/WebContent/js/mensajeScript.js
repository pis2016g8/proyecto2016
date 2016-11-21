/**
 * 
 */
function cargarMensaje(){
	//alert("entro al cargar");
// funcion para obtener parametro de la url (el id del mensaje)
	(function($) {  
		    $.get = function(key)   {  
		        key = key.replace(/[\[]/, '\\[');  
		        key = key.replace(/[\]]/, '\\]');  
		        var pattern = "[\\?&]" + key + "=([^&#]*)";  
		        var regex = new RegExp(pattern);  
		        var url = unescape(window.location.href);  
		        var results = regex.exec(url);  
		        if (results === null) {  
		            return null;  
		        } else {  
		            return results[1];  
		        }  
		    }  
		})(jQuery); 
	var param = $.get("source");
	
	//---------------obtengo el mensaje con el id  y lo cargo------------
	var settings = {
  		"async": true,
  		"crossDomain": true,
		"url" : getUrl("vermensaje?id_mensaje=" + param),
  		"method": "GET",
  		"headers": {
  			"Access-Control-Allow-Origin": "*",
    		"cache-control": "no-cache",
  			}
		}

	$.ajax(settings).done(function (response) {		    	    			    	    
		var mensaje= response;
		$("#asunto").val(mensaje.asunto);
		$("#remitente").val(mensaje.remitente);
		$("#contenido").text(mensaje.contenido);
			 				 	
		$("#destinatario").val(mensaje.remitente);
		$("#asuntoEnviar").val(mensaje.asunto);
		

	});
	
	//------------marcar mensaje como leido--------------
	var settings = {
		"async": true,
		"crossDomain": true,
	    "url": getUrl("mensajeleido?nick=marce_fing&id_mensaje=" + param),
		"method": "POST",
		"headers": {
			"Access-Control-Allow-Origin": "*",
			"cache-control": "no-cache",
		}
	}

	$.ajax(settings).done(function (response) {
		
	});
	
}

/****************RESPONDER MENSAJE**************/

function guardarMensaje(){
	
	var destinatario = $('#destinatario').val();
	var asunto = $('#asuntoEnviar').val();
	var contenido = $('#mensajeEnviar').val();
	
	var settings = {
  		"async": true,
  		"crossDomain": true,
		"url": getUrl("respondermensaje?destinatario=" + destinatario + "&asunto=" + asunto + "&contenido=" + contenido + "&remitente=marce_fing"),		
		"method": "POST",
  		"headers": {
  			"Access-Control-Allow-Origin": "*",
    		"cache-control": "no-cache",
  		}
	}

	$.ajax(settings).done(function (response) {

		window.location.href = "../jsp/VerMensajes.jsp"
		alert("Mensaje enviado");

	});
	
	

}

