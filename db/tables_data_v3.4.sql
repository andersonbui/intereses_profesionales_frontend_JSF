/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  debian
 * Created: 29/10/2021
 */

INSERT INTO interpro.TipoChaside (idTipoChaside, nombre, profesion) VALUES(1, 'C', 'Administrativas y Contables');
INSERT INTO interpro.TipoChaside (idTipoChaside, nombre, profesion) VALUES(2, 'H', 'Humanísticas y Sociales');
INSERT INTO interpro.TipoChaside (idTipoChaside, nombre, profesion) VALUES(3, 'A', 'Artísticas');
INSERT INTO interpro.TipoChaside (idTipoChaside, nombre, profesion) VALUES(4, 'S', 'Medicina y Cs. de la Salud');
INSERT INTO interpro.TipoChaside (idTipoChaside, nombre, profesion) VALUES(5, 'I', 'Ingeniería y Computación');
INSERT INTO interpro.TipoChaside (idTipoChaside, nombre, profesion) VALUES(6, 'D', 'Defensa y Seguridad');
INSERT INTO interpro.TipoChaside (idTipoChaside, nombre, profesion) VALUES(7, 'E', 'Ciencias Exactas y Agrarias');

INSERT INTO interpro.ClaseChaside (idClaseChaside, nombre) VALUES(1, 'INTERESES');
INSERT INTO interpro.ClaseChaside (idClaseChaside, nombre) VALUES(2, 'APTITUDES');

INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (1,1,'Organizativo;Supervisión;Orden;Análisis y síntesis;Colaboración;Cálculo');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (2,1,'Precisión Verbal;Organización;Relación de Hechos;Lingüística;Orden;Justicia');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (3,1,'Estético;Armónico;Manual;Visual;Auditivo');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (4,1,'Asistir;Investigativo;Precisión;Senso-Perceptivo;Analítico;Ayudar');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (5,1,'Cálculo;Científico;Manual;Exacto;Planificar');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (6,1,'Justicia;Equidad;Colaboración;Espíritu de Equipo;Liderazgo');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (7,1,'Investigación;Orden;Organización;Análisis y Síntesis;Numérico');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (1,2,'Persuasivo;Objetivo;Práctico;Tolerante;Responsable;Ambicioso');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (2,2,' Responsable;Justo;Conciliador;Persuasivo;Sagaz;Imaginativo');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (3,2,'Sensible;Imaginativo;Creativo;Detallista;Innovador;Intuitivo');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (4,2,'Altruista;Solidario;Paciente;Comprensivo;Respetuoso;Persuasivo');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (5,2,'Preciso;Práctico;Crítico;Analítico;Rígido');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (6,2,'Arriesgado;Solidario;Valiente;Agresivo;Persuasivo');
INSERT INTO interpro.TipoClaseChaside (idTipoChaside,idClaseChaside,descripcion) VALUES (7,2,'Metódico;Analítico;Obervador;Introvertido;Paciente');

INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (1,'¿Aceptarías trabajar escribiendo artículos en la sección económica de un diario?',1,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (2,'¿Te ofrecerías para organizar la despedida de soltero de uno de tus amigos?',1,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (3,'¿Te gustaría dirigir un proyecto de urbanización en tu provincia?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (4,'¿A una frustración siempre opones un pensamiento positivo?',4,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (5,'¿Te dedicarías a socorrer a personas accidentadas o atacadas por asaltantes?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (6,'¿Cuando eras chico, te interesaba saber cómo estaban construidos tus juguetes?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (7,'¿Te interesan más los misterios de la naturaleza que los secretos de la tecnología?',7,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (8,'¿Escuchás atentamente los problemas que te plantean tus amigos?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (9,'¿Te ofrecerías para explicar a tus compañeros un determinado tema que ellos no entendieron?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (10,'¿Sos exigente y crítico con tu equipo de trabajo?',5,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (11,'¿Te atrae armar rompecabezas o puzzles?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (12,'¿Podés establecer la diferencia conceptual entre macroeconomía y microeconomía?',1,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (13,'¿Usar uniforme te hace sentir distinto, importante?',6,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (14,'¿Participarías como profesional en un espectáculo de acrobacia aérea?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (15,'¿Organizas tu dinero de manera que te alcance hasta el próximo cobro?',1,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (16,'¿Convencés fácilmente a otras personas sobre la validez de tus argumentos?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (17,'¿Estás informado sobre los nuevos descubrimientos que se están realizando sobre la Teoría del Big-Bang?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (18,'¿Ante una situación de emergencia actuás rápidamente?',6,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (19,'¿Cuando tenés que resolver un problema matemático, perseverás hasta encontrar la solución?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (20,'¿Si te convocara tu club preferido para planificar, organizar y dirigir un campo de deportes, aceptarías?',1,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (21,'¿Sos el que pone un toque de alegría en las fiestas?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (22,'¿Crees que los detalles son tan importantes como el todo?',3,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (23,'¿Te sentirías a gusto trabajando en un ámbito hospitalario?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (24,'¿Te gustaría participar para mantener el orden ante grandes desórdenes y cataclismos?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (25,'¿Pasarías varias horas leyendo algún libro de tu interés?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (26,'¿Planificás detalladamente tus trabajos antes de empezar?',5,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (27,'¿Entablás una relación casi personal con tu computadora?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (28,'¿Disfrutás modelando con arcilla?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (29,'¿Ayudás habitualmente a los no videntes a cruzar la calle?',4,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (30,'¿Considerás importante que desde la escuela primaria se fomente la actitud crítica y la participación activa?',2,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (31,'¿Aceptarías que las mujeres formaran parte de las fuerzas armadas bajo las mismas normas que los hombres?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (32,'¿Te gustaría crear nuevas técnicas para descubrir las patologías de algunas enfermedades a través del microscopio?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (33,'¿Participarías en una campaña de prevención contra la enfermedad de Chagas?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (34,'¿Te interesan los temas relacionados al pasado y a la evolución del hombre?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (35,'¿Te incluirías en un proyecto de investigación de los movimientos sísmicos y sus consecuencias?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (36,'¿Fuera de los horarios escolares, dedicás algún día de la semana a la realización de actividades corporales?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (37,'¿Te interesan las actividades de mucha acción y de reacción rápida en situaciones imprevistas y de peligro?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (38,'¿Te ofrecerías para colaborar como voluntario en los gabinetes espaciales de la NASA?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (39,'¿Te gusta más el trabajo manual que el trabajo intelectual?',3,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (40,'¿Estarías dispuesto a renunciar a un momento placentero para ofrecer tu servicio como profesional?',4,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (41,'¿Participarías de una investigación sobre la violencia en el fútbol?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (42,'¿Te gustaría trabajar en un laboratorio mientras estudiás?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (43,'¿Arriesgarías tu vida para salvar la vida de otro que no conoces?',6,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (44,'¿Te agradaría hacer un curso de primeros auxilios?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (45,'¿Tolerarías empezar tantas veces como fuere necesario hasta obtener el logro deseado?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (46,'¿Distribuís tu horarios del día adecuadamente para poder hacer todo lo planeado?',1,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (47,'¿Harías un curso para aprender a fabricar los instrumentos y/o piezas de las máquinas o aparatos con que trabajas?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (48,'¿Elegirías una profesión en la tuvieras que estar algunos meses alejado de tu familia, por ejemplo el marino?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (49,'¿Te radicarías en una zona agrícola-ganadera para desarrollar tus actividades como profesional?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (50,'¿Cuando estás en un grupo trabajando, te entusiasma producir ideas originales y que sean tenidas en cuenta?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (51,'¿Te resulta fácil coordinar un grupo de trabajo?',1,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (52,'¿Te resultó interesante el estudio de las ciencias biológicas?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (53,'¿Si una gran empresa solicita un profesional como gerente de comercialización, te sentirías a gusto desempeñando ese rol?',1,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (54,'¿Te incluirías en un proyecto nacional de desarrollo de la principal fuente de recursos de tu provincia?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (55,'¿Tenés interés por saber cuales son las causas que determinan ciertos fenómenos, aunque saberlo no altere tu vida?',7,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (56,'¿Descubriste algún filósofo o escritor que haya expresado tus mismas ideas con antelación?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (57,'¿Desearías que te regalen algún instrumento musical para tu cumpleaños?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (58,'¿Aceptarías colaborar con el cumplimiento de las normas en lugares públicos?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (59,'¿Crees que tus ideas son importantes,y haces todo lo posible para ponerlas en práctica?',5,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (60,'¿Cuando se descompone un artefacto en tu casa, te disponés prontamente a repararlo?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (61,'¿Formarías parte de un equipo de trabajo orientado a la preservación de la flora y la fauna en extinción?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (62,'¿Acostumbrás a leer revistas relacionadas con los últimos avances científicos y tecnológicos en el área de la salud?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (63,'¿Preservar las raíces culturales de nuestro país, te parece importante y necesario?',2,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (64,'¿Te gustaría realizar una investigación que contribuyera a hacer más justa la distribución de la riqueza?',1,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (65,'¿Te gustaría realizar tareas auxiliares en una nave, como por ejemplo izado y arriado de velas, pintura y conservación del casco, arreglo de averías, conservación de motores, etc?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (66,'¿Crees que un país debe poseer la más alta tecnología armamentista, a cualquier precio?',6,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (67,'¿La libertad y la justicia son valores fundamentales en tu vida?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (68,'¿Aceptarías hacer una práctica rentada en una industria de productos alimenticios en el sector de control de calidad?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (69,'¿Consideras que la salud pública debe ser prioritaria, gratuita y eficiente para todos?',4,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (70,'¿Te interesaría investigar sobre alguna nueva vacuna?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (71,'¿En un equipo de trabajo, preferís el rol de coordinador?',1,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (72,'¿En una discusión entre amigos, te ofrecés como mediador?',2,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (73,'¿Estás de acuerdo con la formación de un cuerpo de soldados profesionales?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (74,'¿Lucharías por una causa justa hasta las últimas consecuencias?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (75,'¿Te gustaría investigar científicamente sobre cultivos agrícolas?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (76,'¿Harías un nuevo diseño de una prenda pasada de moda, ante una reunión imprevista?',3,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (77,'¿Visitarías un observatorio astronómico para conocer en acción el funcionamiento de los aparatos?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (78,'¿Dirigirías el área de importación y exportación de una empresa?',1,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (79,'¿Te inhibís al entrar a un lugar nuevo con gente desconocida?',7,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (80,'¿Te gratificaría el trabajar con niños?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (81,'¿Harías el diseño de un afiche para una campaña contra el sida?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (82,'¿Dirigirías un grupo de teatro independiente?',3,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (83,'¿Enviarías tu curriculum a una empresa automotriz que solicita gerente para su área de producción?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (84,'¿Participarías en un grupo de defensa internacional dentro de alguna fuerza armada?',6,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (85,'¿Te costearías tus estudios trabajando en una auditoría?',1,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (86,'¿Sos de los que defendés causas perdidas?',2,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (87,'¿Ante una emergencia epidémica participarías en una campaña brindando tu ayuda?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (88,'¿Sabrías responder que significa ADN y ARN?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (89,'¿Elegirías una carrera cuyo instrumento de trabajo fuere la utilización de un idioma extranjero?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (90,'¿Trabajar con objetos te resulta más gratificante que trabajar con personas?',5,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (91,'¿Te resultaría gratificante ser asesor contable en una empresa reconocida?',1,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (92,'¿Ante un llamado solidario, te ofrecerías para cuidar a un enfermo?',4,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (93,'¿Te atrae investigar sobre los misterios del universo, por ejemplo los agujeros negros?',7,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (94,'¿El trabajo individual te resulta más rápido y efectivo que el trabajo grupal?',7,2);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (95,'¿Dedicarías parte de tu tiempo a ayudar a personas de zonas carenciadas?',2,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (96,'¿Cuando elegís tu ropa o decorás un ambiente, tenés en cuenta la combinación de los colores, las telas o el estilo de los muebles?',3,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (97,'¿Te gustaría trabajar como profesional dirigiendo la construcción de una empresa hidroeléctrica?',5,1);
INSERT INTO interpro.PreguntaChaside (idPreguntaChaside,enunciado,idTipoChaside,idClaseChaside) VALUES (98,'¿Sabés qué es el PBI?',1,1);