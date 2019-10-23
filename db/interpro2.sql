SELECT DISTINCT 
	est.idPersona, 
	est.estrato, 
	eta.personalidad, 
	eta.puntajeEncuesta, 
	eta.puntajeEvaluacion, 
	per.nombre, 
	per.apellido, 
	per.sexo, 
	est.viveConPadres, 
	apf.area,
	rpa.valor
FROM interpro.Estudiante as est
INNER JOIN interpro.Persona as per ON per.idPersona = est.idPersona
INNER JOIN interpro.EstudianteGrado as estg ON estg.idEstudiante = est.idEstudiante
INNER JOIN interpro.Grado as gd ON estg.idGrado = gd.idGrado
INNER JOIN interpro.Encuesta as eta ON estg.idEstudiante = eta.idEstudiante AND estg.idGrado = eta.idGrado
INNER JOIN interpro.AreaProfesional as apf ON apf.idAreaProfesional = eta.idAreaProfesional
INNER JOIN interpro.ResultadoPorAmbiente as rpa ON rpa.idEncuesta = eta.idEncuesta
WHERE gd.grado = 8 AND est.idPersona > 100;


SELECT max(idArea) from interpro.Area;