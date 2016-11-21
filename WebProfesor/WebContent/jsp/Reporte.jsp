<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Mensaje</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/global_source.js"></script>
<script type="text/javascript" src="../js/header.js"></script>

</head>
<script type="text/javascript" src="../js/reporteScript.js"></script>

<body  id="mensaje" onload="cargarMensaje()">	
 <jsp:include page="Header.jsp"></jsp:include>
 <div class="container">
 <h2 class= "col-sm-offset-3 col-sm-9">Reporte</h2>
 <form class="form-horizontal">
    <div class="form-group"></div>
    <div class="form-group">
      <label class="control-label col-sm-offset-1 col-sm-2" for="asunto">Asunto:</label>
      <div class="col-sm-7">
      	<input id ="asunto" class="form-control" readonly>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-offset-1 col-sm-2" for="remitente">Remitente:</label>
            <div class="col-sm-7">
      	<input id="remitente" class="form-control" readonly>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-offset-1 col-sm-2" for="contenido">Mensaje:</label>
            <div class="col-sm-7">
      	<textarea id="contenido" class="form-control" readonly></textarea>
      </div>
    </div>
    <div class="form-group"></div>
   
  </form>
  

 
  
</div>
</body>
</html>