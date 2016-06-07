//Obtenemos la referencia a la base de datos.
var ref = new Firebase("https://blinding-inferno-2140.firebaseio.com");
var alumnos = [];
var nombres=[];
var materia1 = [];
var materia2 = [];
var materiasOrdenadas = [];
var materiasCarrera;
var carrera;

$(document).on("ready", main);

function main(){
	getCarrera();
	//alert("Espere mientras carga");
	//getDatos();
	//$("#botonResultados").on("click", imprimirMaterias);
	//$("#botonMasSolicitadas").on("click", contarMaterias);
	//$("#botonGrafica").on("click", dibujarGrafico);
	$("#reg").on("click", function(e){
		e.preventDefault();
		imprimirMaterias();
	});
	$("#lis").on("click", function(e){
		e.preventDefault();
		getDatos();
		//contarMaterias();
		mostrarLista();
	});
	$("#gra").on("click", function(e){
		e.preventDefault();
		getDatos();
	});

	$("#registros").css({
		"opacity":"0"
	});

	$("#lista").css({
		"opacity":"0"
	});

	$("#grafica").css({
		"opacity":"1"
	});

	ref.child("users").on("value", function(snapshot){
		if($("#grafica").css("opacity") == 1){
			setTimeout(getDatos(), 1000);
		}
	})

}

//Agregamos un callback asíncrono para leer los datos en la referencia a la bd.
function leerDatos(){
	var refUsers = ref.child("users");

	refUsers.on("value", function(snapshot){
		console.log(snapshot.val());

	}, function(errorObject){
		console.log("Hubo un fallo al traer los datos: " + errorObject.code);

	});
}

//Obtener datos con child()
function datosChild(){
	ref.once("value", function(snapshot){
		var nameSnapshot = snapshot.child("users");
		var name = nameSnapshot.val();
		
		document.write(name);
	});
}


function imprimirMaterias(){
	var i = 0;
	var materias = "<table class='table' style='width:100%; text-align:center;'>";
		for(i = 0; i < alumnos.length; i++){
			//$("body").append("<br/><br/>" + (i+1) + ".- " + alumnos[i]);
			//$("body").append("<br/>" + materia1[i] + "");
			//$("body").append("<br/>" + materia2[i] + "");
			materias += "<tr class='success'><th>Nombre del Alumno </th><td>"+ nombres[i]+"</td></tr>"
						+ "<tr><th>Materia </th><td>" + materia1[i].replace("Semestre", "Semestre - ") + 
						"</td></tr><tr><th>Materia </th><td>" + materia2[i].replace("Semestre", "Semestre - ") +"</td></tr>";
		}
		materias+="</table>"
	$("#registros").html(materias);

	$("#registros").css({
		"opacity":"1"
	});

	$("#lista").css({
		"opacity":"0"
	});

	$("#grafica").css({
		"opacity":"0"
	});
}


//Iterar todos los hijos de un nodo.
function getDatos(){	
	getMaterias();

		alumnos = [];
		materia1 = [];
		materia2 = [];
		materiasOrdenadas = [];

	var refUsers = ref.child("users");
	// Las que van a los arrays de materias.
	var mat1, mat2;

	refUsers.once("value", function(snapshot){
		snapshot.forEach(function(childSnapshot){

			var key = childSnapshot.key();

				var refCar = refUsers.child(key+"/Carrera");
				var nombre  = refUsers.child(key+"/Nombre");
				var refMat1 = refUsers.child(key+"/Materias/Materia1");
				var refMat2 = refUsers.child(key+"/Materias/Materia2");
				var carreraActual;

				//Las que van al objeto de jsons.
				var mate1;
				var mate2;
				var mate1Aux;
				var mate2Aux;

				//console.log(nombre);
				//console.log(key);

				refCar.on("value", function(nombreCarrera){
					console.log("CARRERA *****: " + nombreCarrera.val());
					carreraActual = nombreCarrera.val();
				});

				if(carreraActual == carrera){
					alumnos.push(key);


					nombre.on("value",function(nombre){
						nom=nombre.val();
						console.log(nom);
						nombres.push(nom);
					});

					refMat1.on("value", function(nombreMateria){
						console.log(nombreMateria.val());
						mat1 = nombreMateria.val();
						mate1Aux = mat1.split("Semestre");
						//mate1 = mate1Aux[1].split("\n");
						mate1 = mate1Aux[1];
						console.log("MAAAAAAAAAAATE1: " + mate1);
						materia1.push(mat1);
					});
					

					refMat2.on("value", function(nombreMateria){
						console.log(nombreMateria.val());
						mat2 = nombreMateria.val();
						mate2Aux = mat2.split("Semestre");
						//mate2 = mate2Aux[1].split("\n");
						mate2 = mate2Aux[1];
						console.log("MAAAAAAAAAAATE2: " + mate2);
						materia2.push(mat2);
					});

					console.log("MAT 1 : " + mate1[0]);
					console.log("MAT 2 : " + mate2[0]);

					var i = 0;
					for(i = 0; i < materiasCarrera.length; i++){
						if(mate1 == materiasCarrera[i]['id'] || mate2 == materiasCarrera[i]['id']){
							materiasCarrera[i]['alumnoEscogio'] += nom + "<br>";
							console.log("****** Alumno escogió: " + materiasCarrera[i]['alumnoEscogio']);
						}
					}
						
					//alert("Llenos");
				}

		});
		
		//imprimirMaterias();
		setTimeout(contarMaterias(), 6000);
	});
}

//Las materias más escogidas.
function contarMaterias(){
	/*

	Ejemplo de ordenamiento de array asociativo
*/
	//var items = [ {id:"TAP", value:3, perc:0.5}, {id:"GRAF", value:2, perc:1.3}, {id:"TAL", value:1, perc:0.2} ]
	//items.sort(function (a, b){
	 //   return (b.value - a.value)
	//})

	//items.sort(function (a, b) {
	 //   return (b.perc - a.perc)
	//})
	
	//console.log(materiasCarrera[10]['id']);
	//console.log(materiasCarrera.length);
	//var escogido = materiasCarrera[7]['escogido'] + 1;
	//materiasCarrera[7]['escogido'] = escogido;
	//console.log("La materia " + materiasCarrera[7]['id'] + " fue escogida " + (materiasCarrera[7]['escogido'] + 10) + " veces.");

	//Deja el array como [semestre, materiaMaestro];
	//var materiaActualAux = materia1[1].split("Semestre");
	//console.log(materiaActualAux);

	//Deja el array como [materia, maestro];
	//var materiaActual = materiaActualAux[1].split("\n");
	//console.log(materiaActual);

	//if(materiaActual[0] == "Taller de investigacion II"){
	//	alert("Taller :v /");
	//}

	var materiaActualAux1, materiaActual1;
	var materiaActualAux2, materiaActual2;
	var encontradas = 0;
	var nombre = "";
	var refUsers = ref.child("users");

	for(var i = 0; i < alumnos.length; i++){
		if(materia1[i] != "Vacio"){
			console.log("MATERIA1[i] : " + materia1[i]);
			materiaActualAux1 = materia1[i].split("Semestre");
						console.log("MATERIA1AUX[i] : " + materiaActualAux1);
			//materiaActual1 = materiaActualAux1[1].split("\n");
			materiaActual1 = materiaActualAux1[1];
			console.log("MATERIAACTUAL1 : " + materiaActual1);
		}else{
						materiaActual1 = "Vacio";
		}

		if(materia2[i] != "Vacio"){
			materiaActualAux2 = materia2[i].split("Semestre");
			//materiaActual2 = materiaActualAux2[1].split("\n");
			materiaActual2 = materiaActualAux2[1];
		}else{
						materiaActual2 = "Vacio";
		}
			//console.log(materiaActual2);


		//For para el array de la primera materia
		for(var j = 0; j < materiasCarrera.length; j++){
			//console.log(materiasCarrera[j]['id']);
			//console.log(materiaActual1[0]);
			//console.log(materiaActual2[0]);

			if(materiaActual1 == materiasCarrera[j]['id'] || materiaActual2 == materiasCarrera[j]['id']){
				materiasCarrera[j]['escogido'] ++;
				encontradas++;
			}

			if(encontradas == 2){
				encontradas = 0;
				break;
			}

		}
	}

	//$("body").append("<br/> <br/> Tópicos avanzados de programación fue elegida: " + materiasCarrera[20]['escogido'] + " veces");

	materiasCarrera.sort(function (a, b) {
	    return (b.escogido - a.escogido)
	})

	materiasOrdenadas = materiasCarrera.sort();

	setTimeout(actualizarGrafica(), 6000);
}

function mostrarLista(){
	var lista = "";

	//$("body").append("<br/><br/> Estas fueron las materias más solicitadas: ");
	lista += "<br/><br/> Estas fueron las materias más solicitadas para " + carrera + ":<br /> ";

	lista+="<table class='table-striped' style='width:100%; text-align:center;'>";
	lista+="<tr><th>Materia</th><th>Semestre</th><th>Veces solicitada</th>" +
			"<th>La solicitaron</th></tr>"

	for(var k = 0; materiasOrdenadas[k]['escogido'] > 0; k++){
		//$("body").append("<br/>" + materiasOrdenadas[k]['id'] + " | Semestre: " + materiasOrdenadas[k]['semestre'] 
		//	+ " fue elegida: " + materiasOrdenadas[k]['escogido'] + " veces.");
		lista += "<tr><td>" + materiasOrdenadas[k]['id'] + " </td><td>" + materiasOrdenadas[k]['semestre'] 
			+ "</td><td>" + materiasOrdenadas[k]['escogido'] + "</td>" + 
			"<td>" + materiasOrdenadas[k]['alumnoEscogio'] + "</td></tr>"

			console.log("***** MATERIA: " + materiasOrdenadas[k]['id'] + "<br>" +
				"La escogieron: " + materiasOrdenadas[k]['alumnoEscogio'] + "\nCantidad: " +
				materiasOrdenadas[k]['escogido']);
	}

	lista+="</table>";

	//console.log(materiasCarrera.sort());
	$("#lista").html(lista);

	$("#registros").css({
		"opacity":"0"
	});

	$("#lista").css({
		"opacity":"1"
	});

	$("#grafica").css({
		"opacity":"0"
	});
}

function actualizarGrafica(){
	google.setOnLoadCallback(dibujarGrafico);
     // Tabla de datos: valores y etiquetas de la gráfica
     var data = google.visualization.arrayToDataTable([
     	['Texto', 'Veces solicitada'],
     	[materiasOrdenadas[0]['id'], materiasOrdenadas[0]['escogido']],
     	[materiasOrdenadas[1]['id'], materiasOrdenadas[1]['escogido']],
     	[materiasOrdenadas[2]['id'], materiasOrdenadas[2]['escogido']],
     	[materiasOrdenadas[3]['id'], materiasOrdenadas[3]['escogido']],
     	[materiasOrdenadas[4]['id'], materiasOrdenadas[4]['escogido']],
     	/*['Texto1', 20.21],
     	['Texto2', 4.28],
     	['Texto3', 17.26],
     	['Texto4', 10.25]*/
     	]);
     var options = {
     	title: 'Las materias más escogidas para ' + carrera
     }
     // Dibujar el gráfico
     new google.visualization.ColumnChart( 
     //ColumnChart sería el tipo de gráfico a dibujar
     document.getElementById('grafica')
     ).draw(data, options);

     setTimeout(dibujarGrafico(), 1000);

}

function dibujarGrafico() {
	$("#registros").css({
		"opacity":"0"
	});

	$("#lista").css({
		"opacity":"0"
	});

	$("#grafica").css({
		"opacity":"1"
	}); 

		console.log(materiasCarrera);


 }

function getCarrera(){
	cadVariables = location.search.substring(1,location.search.length);
	arrVariables = cadVariables.split("&");

	for (i=0; i<arrVariables.length; i++) {
  		arrVariableActual = arrVariables[i].split("=");
  	if (isNaN(parseFloat(arrVariableActual[1])))
    	eval(arrVariableActual[0]+"='"+unescape(arrVariableActual[1])+"';");
  	else
    	eval(arrVariableActual[0]+"="+arrVariableActual[1]+";");

	}

	console.log("****CARRERA: " + arrVariableActual[1]);

	switch(parseInt(arrVariableActual[1])){
		case 1:
			carrera = "Administracion";
			break;
		case 2:
			carrera = "Bioquimica";
			break;
		case 3:
			carrera = "Electrica";
			break;
		case 4:
			carrera = "Electronica";
			break;
		case 5:
			carrera = "Industrial";
			break;
		case 6:
			carrera = "Mecatronica";
			break;
		case 7:
			carrera = "Mecanica";
			break;
		case 8:
			carrera = "Quimica";
			break;
		case 9:
			carrera = "Sistemas";
			break;
	}

	console.log("****CARRERA: " + carrera);

	$("#cabecera").html("Estás viendo las materias de " + carrera);

	getMaterias();

}






