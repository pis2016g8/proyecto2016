 <!DOCTYPE html>
<html lang="en">
<head>
  <title>Agregar Mundo</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/global_source.js"></script>
  <script type="text/javascript" src="../js/agregarMundoScript.js"></script>
  <script type="text/javascript" src="../js/header.js"></script>
</head>

<body id="addMundo">
 <jsp:include page="Header.jsp"></jsp:include>

<div class="container">
  <h2 class= "text-center">Datos del nuevo mundo</h2>
  <form class="form-horizontal">
    <div class="form-group"></div>
    <div class="form-group">
      <label class="control-label col-sm-offset-1 col-sm-3" for="nombre">Nombre:</label>
      <div class="col-sm-5">
        <input type="text" class="form-control" id="nombre" placeholder="Ingrese el nombre del mundo" required="required">
      </div>
    </div>
        <div class="form-group">
      <label class="control-label col-sm-offset-1 col-sm-3" for="imagen">Imagen:</label>
      <div class="col-sm-5">
        <input type="text" class="form-control" id="imagen" placeholder="Ingrese la URL de la imagen del mundo" required="required">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-offset-1 col-sm-3" for="experiencia">Experiencia:</label>
      <div class="col-sm-5">
        <input type="number" min="0" class="form-control" id="experiencia" placeholder="Ingrese una experiencia para el mundo" required="required">
      </div>
    </div>
    
    <div class="form-group">
      <label class="control-label col-sm-offset-1 col-sm-3" for="descripcion">Descripcion:</label>
      <div class="col-sm-5">
        <input type="text" class="form-control" id="descripcion" placeholder="Ingrese una descripcion para el mundo" required="required">
      </div>
    </div>
    <div class="form-group"></div>
    <div class="form-group">
        <button type="submit" onclick="guardarMundo()" class="btn btn-default center-block">GUARDAR MUNDO</button>
    </div>
  </form>
</div>

</body>
</html>
 