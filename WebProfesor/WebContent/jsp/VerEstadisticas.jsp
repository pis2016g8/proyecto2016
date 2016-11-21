 <!DOCTYPE html>
<html lang="en">
 <head>
  <title>Ver Estadisticas</title>
 
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/global_source.js"></script>
  <script type="text/javascript" src="../js/verEstadisticaScript.js"></script>
  <script type="text/javascript" src="../js/header.js"></script>
 </head>

<body id="estadisticasBody" onload="cargarEstadisticas()">
 <jsp:include page="Header.jsp"></jsp:include>
  <div class="container">
	<ul class="nav nav-tabs" style="background-color: white;color:#374588">
	  <li class="active"><a data-toggle="tab" href="#estadisticas">Estadísticas</a></li>
	  <li><a data-toggle="tab" href="#ranking">Ranking</a></li>
	</ul>
	
	<div class="tab-content" style="background-color: white;color:#374588">
	  <div id="estadisticas" class="tab-pane fade in active">
	    <table class="table table-hover" >
		    <thead>
		      <tr>
		       <th>ID Problema</th>
		       <th>Nivel</th>
			   <th>Mundo</th>
			   <th>  </th>
			   <th>Cantidad de intentos</th>
			   <th>Porcentaje de error</th>
		      </tr>
		    </thead>
		     <tbody id="tbodyestadisticas"></tbody>
		  </table>	  
		</div>
	  <div id="ranking" class="tab-pane fade">
	    <table class="table table-hover">
		    <thead>
		      <tr>
		        <th>Jugador</th>
				<th>Puntos</th>
			  </tr>
			</thead>
		   <tbody id="tbodyranking"></tbody>
		   </table>	  
		</div>
	 </div>
   </div> 
   
</body>
</html>
