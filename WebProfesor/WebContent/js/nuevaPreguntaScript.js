/**
 * 
 */

$(document).ready(function() {

	  $("#lista").change(function() {
	    var el = $(this) ;
	    $("#lista2").append("<option>SHIPPED</option>");
	  });

});

/****FUNCION PARA TRAER LOS MUNDOS DESDE EL SERVIDOR****/
function cargarMundos(){
	var settings = {
			  "async": true,
			  "crossDomain": true,
			  "url": getUrl("listarmundosprofesor"),
			  "method": "GET",
			  "headers": {
				"Access-Control-Allow-Origin": "*",
				"content-type": "application/json",
			    "cache-control": "no-cache"
			  }
			}

			$.ajax(settings).done(function (response) {
				var mundos= response;
				for(i = 0;i<mundos.lista_mundos.length ;i++){
					var valor = response.lista_mundos[i].nombre;
					var cell = $('<option>');
					cell.val(response.lista_mundos[i].id_mundo);
					cell.text(response.lista_mundos[i].nombre);
					$('#lista').append(cell);	
				}
				
			});
}
/****FUNCION PARA TRAER LOS NIVELES DEL MUNDO SELECCIONADO DESDE EL SERVIDOR****/

function cargarNiveles(){
		var id_mundo = $('#lista').val();

		var settings = {
			  "async": true,
			  "crossDomain": true,
			  "url": getUrl("listarnivelesmundoprofesor?id_mundo="+id_mundo),
			  "method": "GET",
			  "headers": {
			    "cache-control": "no-cache",
			    "Access-Control-Allow-Origin": "*",
			  }
			}

			$.ajax(settings).done(function (response) {
				var niveles= response;
				$('#lista2').empty();
				var celldefault = $('<option>');
				celldefault.val("");
				celldefault.text("Seleccione el nivel");
				$('#lista2').append(celldefault);
				for(i = 0;i<niveles.lista.length ;i++){
					var cell = $('<option>');
					cell.val(niveles.lista[i].id_nivel); //este es el que voy a usar para guardar el problema
					cell.text(niveles.lista[i].num_nivel);
					$('#lista2').append(cell);
				}
			});
};


/****FUNCION PARA GUARDAR EL PROBLEMA EN EL SERVIDOR****/
function guardarProblema(){
	var mundo = $('#lista').val();
	var nivel = $('#lista2 option:selected').html();
	var resp = $('#respuesta').val();
	var ayuda = $('#ayuda').val();
	var puntos = $('#puntaje').val();
	var desc = $('#descripcion').val();
	var URLimagen = $('#imagenP').val();
	var settings = {
			  "async": true,
			  "crossDomain": true,
			  "url": getUrl("agregarproblema?desc=" + desc + "&resp=" + resp + "&exp=" + puntos + "&ayuda=" + ayuda + "&cont=" + URLimagen + "&id_mundo=" + mundo + "&num_nivl=" + nivel + "&nick_prof=marce_fing"),
			  "method": "POST",
			  "headers": {
			    "cache-control": "no-cache",
			    "Access-Control-Allow-Origin": "*",
			  }
			}

			$.ajax(settings).done(function (response) {
			  $('#myModal').hide();
			  alert("Problema Agregado");
			  window.location.href = "../jsp/VerEstadisticas.jsp"
			});

}

function habilitarCrear(){
	$('#crearP').attr('disabled', false);
}

