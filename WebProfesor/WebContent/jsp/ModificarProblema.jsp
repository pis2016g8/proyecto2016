 <!DOCTYPE html>
<html lang="en">
 <head>
  <title>Modificar Problema</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/global_source.js"></script>
  <script type="text/javascript" src="../js/modificarProblema.js"></script>
  <script type="text/javascript" src="../js/nuevaPreguntaScript.js"></script>
  <script type="text/javascript" src="../js/header.js"></script>
 </head>

  

<body id="addMProblema" onload="cargarMundos()">
  <jsp:include page="Header.jsp"></jsp:include>
  <div class="container">
   <h2 class= "text-center">Seleccione el mundo, el nivel y el problema a modificar.</h2>
   <form class="form-horizontal">
    <div class="form-group"></div>
     <div class="col-sm-offset-4 col-sm-4">
      <select id ="lista" class="form-control" onchange="cargarNiveles()">
       <option value ="">Seleccione el mundo</option>
	  </select>
	 </div>
	 <div class="form-group"></div>
     <div class="form-group"></div>
     <div class="col-sm-offset-4 col-sm-4">
       <select id ="lista2" class="form-control" onchange="cargarProblemas()" size="1">
		<option value = "">Seleccione el nivel</option>
	   </select> 
	  </div>
	  <div class="form-group"></div>
     <div class="form-group"></div>
     <div class="col-sm-offset-4 col-sm-4">
       <select id ="idproblema" class="form-control" onchange="cargar_datos_problema()" size="1">
		<option value = "">Seleccione el problema</option>
	   </select> 
	  </div>
     <div class="form-group"></div>
      <div class="form-group"></div>
     <div class="form-group">
        <button id="crearP" type="button" class="btn btn-default center-block" data-toggle="modal" data-target="#myModal" disabled="disabled">Continuar</button>
    </div>
   </form>
  
  <div id="myModal" class="modal fade" role="dialog" style="color: #374588;">
  <div class="modal-dialog">
    <div class="modal-content">
     <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">&times;</button>
      <h4 class="modal-title" align="center">Complete los siguientes datos:</h4>
     </div>
     <div class="modal-body">
      <form class="form-horizontal">
       <div class="form-group"></div>
       <div class="form-group">
        <label class="control-label col-sm-offset-1 col-sm-2" for="imagenP">Imagen:</label>
        <div class="col-sm-7">
    		<input type="text" class="form-control" id="imagenP" required="required" >
       </div>
       </div>
	   <div class="form-group"></div>
       <div class="form-group">
      	<label class="control-label col-sm-offset-1 col-sm-2" for="descripcion">Descripción:</label>
      	<div class="col-sm-7">
         <input type="text" class="form-control" id="descripcion" required="required" >
      	</div>
       </div>
       <div class="form-group"></div>
       <div class="form-group">
       <label class="control-label col-sm-offset-1 col-sm-2" for="puntaje">Puntaje:</label>
       <div class="col-sm-7">
        <input type="number" min="0" class="form-control" id="puntaje" required="required" >
       </div>
      </div>
      <div class="form-group"></div>
      <div class="form-group">
       <label class="control-label col-sm-offset-1 col-sm-2" for="respuesta">Respuesta:</label>
       <div class="col-sm-7">
        <input type="text" class="form-control" id="respuesta" required="required" >
       </div>
      </div>
      <div class="form-group"></div>
      <div class="form-group">
       <label class="control-label col-sm-offset-1 col-sm-2" for="ayuda">Ayuda:</label>
       <div class="col-sm-7">
        <input type="text" class="form-control" id="ayuda" required="required" >
       </div>
      </div>
      <div class="form-group"></div>
      <div class="form-group">
        <button type="submit" onclick="actualizarProblema()" class="btn btn-default center-block" role=""data-dismiss="modal">MODIFICAR PROBLEMA</button>
      </div>
     </form>
    </div>
   </div>
  </div>
   </div>
</div>
  
</body>
</html>