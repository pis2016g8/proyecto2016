<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/loginStyle.css" media="screen" />
<title>Login Profesor</title>


<script type="text/javascript">
	function validarDatos(){
		var usuario = document.getElementById("usuario");
		var pass = document.getElementById("password");
		if ((usuario.value == "admin") &&(pass.value == "admin")){
			//alert("ok");
			return true;
		}else if (usuario.value =='' || usuario.value == "" || usuario.value == null){
			alert("ingrese su usuario");
			usuario.focus();
			return false;
		}else if (pass.value =='' || pass.value == "" || pass.value == null){
			alert("ingrese su password");
			pass.focus();
			return false;
		}else {
			alert ("usuario o contraseña incorrectos")
			return false;
		}
	}
</script>
</head>
<body>	
	<div class="login-page">
	<center><div><img alt="titulo" src="Imagenes/Logo.png" align="top"></div></center>
	<h1></h1>
	<h1></h1>
	<h1></h1>
	<h1></h1>
  	<div class="form">
    <form class="login-form" action="jsp/VerEstadisticas.jsp" method="post" onsubmit="return validarDatos();">
      <input type="text" placeholder="username" id ="usuario"/>
      <input type="password" placeholder="password" id ="password"/>
      <button>login</button>
    </form>
  </div>
</div>

</body>
</html>