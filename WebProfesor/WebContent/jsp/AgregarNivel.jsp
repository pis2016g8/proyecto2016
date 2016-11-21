 <!DOCTYPE html>
<html lang="en">
 <head>
  <title>Agregar Nivel</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/global_source.js"></script>
  <script type="text/javascript" src="../js/agregarNivelScript.js"></script>
  <script type="text/javascript" src="../js/header.js"></script>
 </head>  
 <body id="addNivel" onload="cargarMundos()">
  <jsp:include page="Header.jsp"></jsp:include>
  <div class="container">
   <h2 class= "text-center">Datos del nuevo nivel</h2>
   <form class="form-horizontal">
    <div class="form-group"></div>
     <div class="col-sm-offset-3 col-sm-5" style="padding-left: 10%">
      <select id ="lista" class="form-control" onchange="habilitarCrear()" >
       <option value ="">Seleccione el mundo</option>
	  </select>
	 </div>
     <div class="form-group"></div>
     <div class="form-group"></div>
     <div class="form-group"></div>
     <div class="form-group"></div>
     <div class="form-group">
        <button id="crearN" type="submit" onclick="AgregarNivel()" class="btn btn-default center-block" disabled="disabled">AGREGAR NIVEL</button>
    </div>
   </form>
  </div>
 </body>
</html>