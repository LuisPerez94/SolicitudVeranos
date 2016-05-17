//Obtenemos la referencia a la base de datos.
var ref = new Firebase("https://blinding-inferno-2140.firebaseio.com");
var alumnos = [];
var materia1 = [];
var materia2 = [];


$(document).on("ready", main);

function main(){
	//alert("Espere mientras carga");
	getUsers();
	$("#botonResultados").on("click", imprimirMaterias);
	$("#botonMasSolicitadas").on("click", contarMaterias);
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

		for(i = 0; i < alumnos.length; i++){
			$("body").append("<br/><br/>" + (i+1) + ".- " + alumnos[i]);
			$("body").append("<br/>" + materia1[i] + "");
			$("body").append("<br/>" + materia2[i] + "");
		}
}


//Iterar todos los hijos de un nodo.
function getUsers(){
	var refUsers = ref.child("users");
	var mat1, mat2;

	refUsers.once("value", function(snapshot){
		snapshot.forEach(function(childSnapshot){

			var key = childSnapshot.key();
			var refMat1 = refUsers.child(key+"/Materias/Materia1");
			var refMat2 = refUsers.child(key+"/Materias/Materia2");


			console.log(key);
			alumnos.push(key);

			refMat1.on("value", function(nombreMateria){
				console.log(nombreMateria.val());
				mat1 = nombreMateria.val();
				materia1.push(mat1);
			});
			

			refMat2.on("value", function(nombreMateria){
				console.log(nombreMateria.val());
				mat2 = nombreMateria.val();
				materia2.push(mat2);
			});
			

			//alert("Llenos");

		});
		
		//imprimirMaterias();
	});
}

//Las materias más escogidas.
function contarMaterias(){
	// Primer semestre
	var calculoDiferencial = 0;
	var fundamentosProgramacion = 0;
	var tallerEtica = 0;
	var matematicasDiscretas = 0;
	var tallerAdministracion = 0;
	var fundamentosInvestigacon = 0;

	//Segundo semestre
	var calculoIntegral = 0;
	var programacionOrientadaObjetos = 0;
	var contabilidadFinanciera = 0;
	var quimica = 0;
	var algebraLineal = 0;
	var probabilidadEstadistica = 0;

	//Tercer semestre
	var calculoVectorial = 0;
	var estructuraDatos = 0;
	var culturaEmpresarial = 0;
	var investigacionOperaciones = 0;
	var sistemasOperativos = 0;
	var fisicaGeneral = 0;

	//Cuarto semestre
	var ecuacionesDiferenciales = 0;
	var metodosNumericos = 0;
	var topicosAvanzadosProgramacion = ["TAP", 0];
	var fundamentosBaseDeDatos = 0;
	var tallerSistemasOperativos = 0;
	var principiosElectricos = 0;

	//Quinto semestre
	var desarrolloSustentable = 0;
	var fundamentosTelecomunicaciones = 0;
	var tallerBaseDeDatos = 0;
	var simulacion = 0;
	var fundamentosIngenieriaSoftware = 0;
	var arquitecturaComputadora = 0;

	//Sexto semestre
	var lenguajesAutomatas1 = 0;
	var administracionBaseDeDatos = 0;
	var graficacion = 0;
	var redesComputadora = 0;
	var ingenieriaSoftware = 0;
	var lenguajesInterfaz = 0;

	//Septimo semestre
	var lenguajesAutomatas2 = 0;
	var conmutacionEnrutamientoRedesDatos = 0;
	var tallerInvestigacion1 = 0;
	var gestionProyectosSoftware = 0;
	var sistemasProgramables = 0;

	//Octavo semestre
	var programacionLogicaFuncional = 0;
	var administracionRedes = 0;
	var tallerInvestigacion2 = 0;
	var programacionWeb = 0;

	//Noveno semestre
	var inteligenciaArtificial = 0;

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

	for(var i = 0; i < alumnos.length; i++){
		var materiaActualAux1 = materia1[i].split("Semestre");
		var materiaActual1 = materiaActualAux1[1].split("\n");

		var materiaActualAux2 = materia2[i].split("Semestre");
		var materiaActual2 = materiaActualAux1[1].split("\n");


		if(materiaActual1[0] == topicosAvanzadosProgramacion[0] || materiaActual2[0] == topicosAvanzadosProgramacion[0]){
			topicosAvanzadosProgramacion[1] ++;
		}
	}

	$("body").append("<br/> <br/> Tópicos avanzados de programación fue elegida: " + topicosAvanzadosProgramacion[1] + " veces");
}








