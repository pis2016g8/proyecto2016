/**
 * 
 */
var estadisticas;

/***funcion para otrener el porcentaje***/
function porcentage(item)
{
	if (item.cant_intentos > 0)
	{
		var porcentaje = (item.cant_intentos-item.cant_aciertos)*100/item.cant_intentos;
		
	}
	else
	{
		var porcentaje = 0;
	}
	return porcentaje;
}

function load_table(item)
{
	var porcentaje = porcentage(item);
	var eachrow = "<tr>"
    + "<td> " + item.id_problema + "</td>"
    + "<td> " + item.nro_nivel + "</td>"
    + "<td> " + item.nombre_mundo + " </td>"
    +"<td> <a id=" + item.id_problema + " href=#>" + "link" + "</a>" + "</td>"
    + "<td> " + item.cant_intentos + " </td>"
    + "<td> " + Math.round(porcentaje) + "%</td>"
    + "</tr>";
	$('#tbodyestadisticas').append(eachrow);

	var url = item.url_problema;
	$("#"+item.id_problema).attr("href",url);

}

function order_by_param() {
	estadisticas.sort(function(a, b) { 
	    return (porcentage(b)-porcentage(a));
	})
	
	$.each(estadisticas, function (index, item) {
		var porcentaje = porcentage(item);
		load_table(item); 
	});
}

function cargarEstadisticas(){
//------------cargar datos de estadisticas-------------------
	var settings = {
	  		"async": false,
	  		"crossDomain": true,
	  		"url":getUrl("verestadisticas"),
			"method": "GET",
	  		"headers": {
	  			"Access-Control-Allow-Origin": "*",
	    		"cache-control": "no-cache",
	  			}
			}
	$.ajax(settings).done(function(response){
		estadisticas=response.lista;
		order_by_param();
		CargarRanking();
	});
	
}
function CargarRanking()
{
	//------------cargar datos de ranking-------------------
	var settings = {
	  		"async": true,
	  		"crossDomain": true,
	  		"url": getUrl("verranking"),
	  		"method": "GET",
	  		"headers": {
	  			"Access-Control-Allow-Origin": "*",
	    		"cache-control": "no-cache",
	  			}
			}
			
	$.ajax(settings).done(function (response) {
		var estadisticas= response.listaPuntos;
		$.each(estadisticas, function (index, item) {
			
    		var eachrow = "<tr>"
                + "<td> " + item.nick + " </td>"
                + "<td> " + item.puntos+ " </td>"
                + "</tr>";
    		$('#tbodyranking').append(eachrow);
    		
		});
	});
}

function Salir(){
	window.location.href = "login.html"
}

