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
				//console.log(nombreMateria.val());
				mat1 = nombreMateria.val();
				materia1.push(mat1);
			});
			

			refMat2.on("value", function(nombreMateria){
				//console.log(nombreMateria.val());
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
	/*

	Ejemplo de ordenamiento de array asociativo
*/
	//items.sort(function (a, b){
	 //   return (b.value - a.value)
	//})

	//items.sort(function (a, b) {
	 //   return (b.perc - a.perc)
	//})
	

	var materiasSistemas = [
		{id:"Calculo diferencial", 								semestre: 1, escogido: 0},
		{id:"Fundamentos de programacion", 						semestre: 1, escogido: 0},
		{id:"Taller de etica", 									semestre: 1, escogido: 0},
		{id:"Matematicas discretas", 							semestre: 1, escogido: 0},
		{id:"Taller de administracion", 						semestre: 1, escogido: 0},
		{id:"Fundamentos de investigación", 					semestre: 1, escogido: 0},
		{id:"Calculo integral", 								semestre: 2, escogido: 0},
		{id:"Programacion orientada a objetos", 				semestre: 2, escogido: 0},
		{id:"Contabilidad financiera", 							semestre: 2, escogido: 0},
		{id:"Quimica", 											semestre: 2, escogido: 0},
		{id:"Algebra lineal", 									semestre: 2, escogido: 0},
		{id:"Probabilidad y estadistica", 						semestre: 2, escogido: 0},
		{id:"Calculo vectorial", 								semestre: 3, escogido: 0},
		{id:"Estructura de datos", 								semestre: 3, escogido: 0},
		{id:"Cultura empresarial", 								semestre: 3, escogido: 0},
		{id:"Investigacion de operaciones", 					semestre: 3, escogido: 0},
		{id:"Sistemas operativos", 								semestre: 3, escogido: 0},
		{id:"Fisica general", 									semestre: 3, escogido: 0},
		{id:"Ecuaciones diferenciales", 						semestre: 4, escogido: 0},
		{id:"Metodos numericos", 								semestre: 4, escogido: 0},
		{id:"Topicos avanzados de programacion", 				semestre: 4, escogido: 0},
		{id:"Fundamentos de base de datos", 					semestre: 4, escogido: 0},
		{id:"Taller de sistemas operativos", 					semestre: 4, escogido: 0},
		{id:"Principios electricos y aplicaciones digitales", 	semestre: 4, escogido: 0},
		{id:"Desarrollo sustentable", 							semestre: 5, escogido: 0},
		{id:"Fundamentos de telecomunicaciones", 				semestre: 5, escogido: 0},
		{id:"Taller de base de datos", 							semestre: 5, escogido: 0},
		{id:"Simulacion", 										semestre: 5, escogido: 0},
		{id:"Fundamentos de ingenieria de software", 			semestre: 5, escogido: 0},
		{id:"Arquitectura de computadora", 						semestre: 5, escogido: 0},
		{id:"Lenguajes y automatas I", 							semestre: 6, escogido: 0},
		{id:"Administracion de base de datos", 					semestre: 6, escogido: 0},
		{id:"Graficacion", 										semestre: 6, escogido: 0},
		{id:"Redes de computadora", 							semestre: 6, escogido: 0},
		{id:"Ingenieria de software", 							semestre: 6, escogido: 0},
		{id:"Lenguajes de interfaz", 							semestre: 6, escogido: 0},
		{id:"Lenguajes y automatas II", 						semestre: 7, escogido: 0},
		{id:"Conmutacion y enrutamiento de redes de datos", 	semestre: 7, escogido: 0},
		{id:"Taller de investigacion I", 						semestre: 7, escogido: 0},
		{id:"Gestion de proyectos de software", 				semestre: 7, escogido: 0},
		{id:"Sistemas programables", 							semestre: 7, escogido: 0},
		{id:"Programacion logica y funcional", 					semestre: 8, escogido: 0},
		{id:"Administracion de redes", 							semestre: 8, escogido: 0},
		{id:"Taller de investigacion II", 						semestre: 8, escogido: 0},
		{id:"Programacion web", 								semestre: 8, escogido: 0},
		{id:"Inteligencia artificial", 							semestre: 9, escogido: 0},
	]

	//console.log(materiasSistemas[10]['id']);
	//console.log(materiasSistemas.length);
	//var escogido = materiasSistemas[7]['escogido'] + 1;
	//materiasSistemas[7]['escogido'] = escogido;
	//console.log("La materia " + materiasSistemas[7]['id'] + " fue escogida " + (materiasSistemas[7]['escogido'] + 10) + " veces.");

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

	for(var i = 0; i < alumnos.length; i++){
		materiaActualAux1 = materia1[i].split("Semestre");
		materiaActual1 = materiaActualAux1[1].split("\n");

		materiaActualAux2 = materia2[i].split("Semestre");
		materiaActual2 = materiaActualAux2[1].split("\n");

			console.log(materiaActual2[0]);


		//For para el array de la primera materia
		for(var j = 0; j < materiasSistemas.length; j++){
			//console.log(materiasSistemas[j]['id']);
			//console.log(materiaActual1[0]);
			//console.log(materiaActual2[0]);

			if(materiaActual1[0] == materiasSistemas[j]['id'] || materiaActual2[0] == materiasSistemas[j]['id']){
				materiasSistemas[j]['escogido'] ++;
				console.log("Materia encontrada: " + materiasSistemas[j]['id']);
				encontradas++;
			}

			if(encontradas == 2){
				encontradas = 0;
				break;
			}

		}
	}

	//$("body").append("<br/> <br/> Tópicos avanzados de programación fue elegida: " + materiasSistemas[20]['escogido'] + " veces");

	materiasSistemas.sort(function (a, b) {
	    return (b.escogido - a.escogido)
	})

	var materiasOrdenadas = materiasSistemas.sort();

	$("body").append("<br/><br/> Estas fueron las materias más solicitadas: ");

	for(var k = 0; materiasOrdenadas[k]['escogido'] > 0; k++){
		$("body").append("<br/>" + materiasOrdenadas[k]['id'] + " | Semestre: " + materiasOrdenadas[k]['semestre'] 
			+ " fue elegida: " + materiasOrdenadas[k]['escogido'] + " veces.");
	}

	//console.log(materiasSistemas.sort());
}








