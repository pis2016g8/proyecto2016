/**
 * 
 */

var problemas
var problema 

/************FUNCION PARA TRAER LOS PROBLEMAS DEL SERVIDOR*************/
function cargarProblemas(){

		var id_mundo =$('#lista').val();
		var id_nivel = $('#lista2').val();

		var settings = {
			  "async": true,
			  "crossDomain": true,
			  "url": getUrl("listarproblemasnivelprofesor?id_mundo="+id_mundo + "&id_nivel=" + id_nivel),
			  "method": "GET",
			  "headers": {
			    "cache-control": "no-cache",
			    "Access-Control-Allow-Origin": "*",
			  }
			}

			$.ajax(settings).done(function (response) {
				problemas= response;
				$('#idproblema').empty();
				var celldefault = $('<option>');
				celldefault.val("");
				celldefault.text("Seleccione el Problema");
				$('#idproblema').append(celldefault);
				for(i = 0;i<problemas.problemas_nivel.length ;i++){
					var cell = $('<option>');
					cell.val(i); //este es el que voy a usar para guardar el problema
					cell.text(problemas.problemas_nivel[i].id_problema);
					$('#idproblema').append(cell);
				}
			});
};


/***********FUNCION PARA TRAER LOS DATOS DEL PROBLEMA SELECCIONADO***********/
function cargar_datos_problema()
{
	habilitarCrear();
	var id_problema= $('#idproblema').val();
	problema = problemas.problemas_nivel[id_problema]
	var resp = $('#respuesta');
	resp.val(problema.respuesta);
	resp.text(problema.respuesta);
	var ayuda = $('#ayuda')
	ayuda.val(problema.ayuda);
	ayuda.text(problema.ayuda);
	var puntos = $('#puntaje');
	puntos.val(problema.puntos_exp);
	puntos.text(problema.puntos_exp);
	var desc = $('#descripcion')
	desc.val(problema.descripcion);
	desc.text(problema.descripcion);
	var URLimagen = $('#imagenP');
	URLimagen.val(problema.contenido);
	URLimagen.text(problema.contenido);
	
}

/*************FUNCION QUE ENVÍA LOS CAMBIOS AL SERVIDOR************/
function actualizarProblema(){
	var mundo = $('#lista').val();
	var nivel = $('#lista2 option:selected').html();
	var resp = $('#respuesta').val();
	var ayuda = $('#ayuda').val();
	var puntos = $('#puntaje').val();
	var desc = $('#descripcion').val();
	var URLimagen = $('#imagenP').val();
	var getproblemaid = problema.id_problema;
	
	var settings = {
			  "async": true,
			  "crossDomain": true,
			  "url": getUrl("modificarproblema?id_problema=" + getproblemaid + "&desc=" + desc + "&resp=" + resp + "&exp=" + puntos + "&ayuda=" + ayuda + "&cont=" + URLimagen + "&id_mundo=" + mundo + "&num_nivl=" + nivel + "&nick_prof=marce_fing"),
			  "method": "POST",
			  "headers": {
			    "cache-control": "no-cache",
			    "Access-Control-Allow-Origin": "*",
			  }
			}

			$.ajax(settings).done(function (response) {
			  $('#myModal').hide();
			  alert("Problema Modificado");
			  window.location.href = "../jsp/VerEstadisticas.jsp"
			});

}