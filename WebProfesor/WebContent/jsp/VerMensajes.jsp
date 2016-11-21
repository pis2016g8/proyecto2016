 <!DOCTYPE html>
<html lang="en">
 <head>
  <title>Ver Mensajes</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/global_source.js"></script>
  <script type="text/javascript" src="../js/verMensajesScript.js"></script>
  <script type="text/javascript" src="../js/header.js"></script>
</head>

<body id ='mensajes' onload="cargarMensajes()">
 <jsp:include page="Header.jsp"></jsp:include>
  <div class="container">
	<ul class="nav nav-tabs" style="background-color: white;color:#75507b">
	  <li class="active"><a data-toggle="tab" href="#mnsjn">Mensajes nuevos</a></li>
	  <li><a data-toggle="tab" href="#mnsjl">Mensajes leidos</a></li>
	  <li><a data-toggle="tab" href="#reportesnuevos">Reportes nuevos</a></li>
	  <li><a data-toggle="tab" href="#reportesleidos">Reportes leidos</a></li>
	</ul>
	
	<div class="tab-content" style="background-color: white;color:#374588">
	  <div id="mnsjn" class="tab-pane fade in active">
	    <table class="table table-hover" >
		    <thead>
		      <tr>
		        <th>Remitente</th>
		        <th>Asunto</th>
		        <th>Fecha</th>
		      </tr>
		    </thead>
		     <tbody id="tbodynuevos" style="cursor:pointer;"></tbody>
		  </table>	  
		</div>
	  <div id="mnsjl" class="tab-pane fade">
	    <table class="table table-hover">
		    <thead>
		      <tr>
		        <th>Remitente</th>
		        <th>Asunto</th>
		        <th>Fecha</th>
		      </tr>
		    </thead>
		     <tbody id="tbodyviejos" style="cursor:pointer;"></tbody>
		  </table>	  
	 </div>
	 <div id="reportesnuevos" class="tab-pane fade">
	    <table class="table table-hover">
		    <thead>
		      <tr>
		        <th>Remitente</th>
		        <th>Asunto</th>
		        <th>Fecha</th>
		      </tr>
		    </thead>
		     <tbody id="tbodyrepnuevos" style="cursor:pointer;"></tbody>
		  </table>	  
	 </div>
	 <div id="reportesleidos" class="tab-pane fade">
	    <table class="table table-hover">
		    <thead>
		      <tr>
		        <th>Remitente</th>
		        <th>Asunto</th>
		        <th>Fecha</th>
		      </tr>
		    </thead>
		     <tbody id="tbodyrepviejos" style="cursor:pointer;"></tbody>
		  </table>	  
	 </div>
	 </div>
   </div> 
   
</body>
</html>