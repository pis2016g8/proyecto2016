<!DOCTYPE html>
<html lang="es">
<head>

	<title>Header</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script>
		$(document).ready(function(){
			$('.dropdown-toggle').dropdown();    
		});
	</script>

	<style type="text/css">

		.navbar-default .navbar-nav > .active > a, 
		.navbar-default .navbar-nav > .active > a:hover{
			background-color: #273161;
		}
		
		.navbar-default .navbar-nav > li > a:hover,
		.navbar-default .navbar-nav > li > a:focus{
			background-color: #273161;
		}
		
		.navbar-default .navbar-nav .open .dropdown-menu{
			background-color: #374588;
		}
		
		.navbar-default .navbar-nav .open .dropdown-menu > li > a:hover,
		.navbar-default .navbar-nav .open .dropdown-menu > li > a:focus{
			background-color: #273161;
		}
		
		.navbar-default .navbar-nav .open .dropdown-menu > .active > a,
		.navbar-default .navbar-nav .open .dropdown-menu > .active > a:hover,
		.navbar-default .navbar-nav .open .dropdown-menu > .active > a:focus{
			background-color: #273161;
		}
		
		.navbar .nav > li.dropdown.open.active > a:hover, 
		.navbar .nav > li.dropdown.open > a{
			color: #fff;
			background-color: #273161;
			border-color: #fff;
		}
		
		.btn, .btn-default{
			color: white;
			background-color: #374588;
		}
		
		.btn:hover,
		.btn:focus,
		.btn:focus:disabled,
		.btn:hover:disabled{
			background-color: #273161;
			color: white;
		}
		
	</style>

</head>

<body data-spy="scroll" data-target=".navbar" data-offset="50" style="background-color:#e0e0eb; color:#0000a4; border:#374588;">

	<nav class="navbar navbar-default" style="background-color:#374588; color:white; border:#374588;">
		<div class="container-fluid">
			<div class="navbar-header" align="center" >
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav navbar-left">
					<li><img src="../Imagenes/Logo.png" class="img-rounded" alt="Brand" width="90px" height="55px" ></li>
				</ul>
				<ul class="nav navbar-nav">
					<li><a></a></li>
				</ul>
				<ul class="nav navbar-nav">
					<li><a href="VerEstadisticas.jsp" style="color:white;">Estadisticas</a></li>
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:white;">Nuevo<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="AgregarMundo.jsp" style="color:white;">Mundo</a></li>
							<li><a href="AgregarNivel.jsp" style="color:white;">Nivel</a></li>
							<li><a href="AgregarProblema.jsp" style="color:white;">Problema</a></li>
						</ul>
					</li>
					<li><a id="msj" href="VerMensajes.jsp" style="color:white;">Mensajes</a></li>
					
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:white;">Modificar<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="ModificarProblema.jsp" style="color:white;">Problema</a></li>
						</ul>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="../login.jsp" style="color:white;"><span class="glyphicon glyphicon-log-out" style="color:white;"></span> SALIR</a></li>
				</ul>
			</div>
		</div>
	</nav>

</body>
</html>