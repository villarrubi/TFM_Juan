package com.mycompany.tfm_juan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona las preguntas del juego AQUILAE
 */
public class QuestionManager {
    
    public static class Question {
        String question;
        String answer;
        
        public Question(String q, String a) {
            this.question = q;
            this.answer = a;
        }
        
        public String getQuestion() {
            return question;
        }
        
        public String getAnswer() {
            return answer;
        }
    }
    
    private static final Question[] ALL_QUESTIONS = {
        //NO SE PUEDE PONER COMILLAS DOBLES EN UNA PREGUNTA ( "" ), HAY QUE PONER SIMPLES ('') -- se pueden poner doble comilla simple pero sino detecta que acaba la pregunta y dará error
        new Question("Juan maricon", "Que alguien le tire un zapato al bujarrilla este"),
        new Question("¿Qué héroe es considerado el progenitor del pueblo romano? ", "Eneas"),
        new Question("¿De qué ciudad provenía Eneas?", "De Troya"),
        new Question("¿Quién fue el primer rey de Roma?", "Rómulo"),
        new Question("¿De qué divinidad era hijo Eneas?", "Venus"),
        new Question("¿De qué divinidad era hijo Eneas?", "Venus"),
        new Question("¿Qué reina púnica tubo un romance con Eneas?", "Dido"),
        new Question("¿Quién fundó Alba Longa?", "Iulio Ascanio, el hijo de Eneas"),
        new Question("¿De qué ciudad provenían los fundadores de Roma?", "Alba Longa"),
        new Question("¿Qué año se fundó Roma?", "753 a.C."),
        new Question("¿De qué divinidad eran hijos Rómulo y Remo?", "De Marte"),
        new Question("¿Quién alimentó a Rómulo y Remo?", "La loba Luperca"),
        new Question("¿Qué era el pomerium?", "La línea sagrada que delimitaba el núcleo religioso y político de la ciudad de Roma"),
        new Question("¿Sobre qué colina se fundó Roma?", "El Palatino"),
        new Question("¿Cómo consiguió mujeres Roma en sus orígenes?", "Mediante el rapto de las sabinas"),
        new Question("¿Cuántos reyes tubo Roma?", "7"),
        new Question("¿Cuál era el puerto más importante de Roma?", "Ostia"),
        new Question("¿Bajo qué reinado se construyó el Circo Máximo?", "Bajo el reinado de Tarquinio Prisco"),
        new Question("¿Qué templo coronaba la colina Capitolina?", "El templo de Júpiter Óptimo Máximo"),
        new Question("¿Qué divinidades conformaban la Triada Capitolina?", "Júpiter, Juno y Minerva"),
        new Question("¿Quién fue el último rey de Roma?", "Tarquinio el Soberbio"),
        new Question("¿En qué año se proclamó la República?", "509 a.C."),
        new Question("¿Cuáles son las cuatro magistraturas principales del cursus honorum?", "Cuestor, edil, pretor y cónsul"),
        new Question("¿Qué dos tipos de ciudadanos había en Roma?", "Patricios y plebeyos"),
        new Question("¿A quiénes se consideraba miembros de la familia romana?", "Pater familias, mater familias, hijos y descendencia, esclavos y clientes"),
        new Question("¿Qué otras civilizaciones convivían con Roma en la península itálica durante la República temprana?", "Los etruscos y los griegos"),
        new Question("¿Dónde se refugiaron los plebeyos durante la secesión de la plebe?", "En el monte Aventino"),
        new Question("¿Qué poder particular tenían los tribunos de la plebe?", "El derecho de veto contra las acciones de los cónsules y el Senado"),
        new Question("¿Quiénes fueron los decenviros?", "Los diez hombres elegidos para redactar las primeras leyes sagradas de Roma"),
        new Question("¿Cómo se llama al primer código escrito de leyes romanas?", "La Ley de las XII tablas"),
        new Question("¿En qué año ocurrió el primer saqueo galo de Roma?", "390 a.C."),
        new Question("¿Cómo se llamaba el líder de los senones?", "Breno"),
        new Question("¿Cómo murió Tarpeya?", "Fue sepultada por escudos"),
        new Question("¿Qué hacían los romanos en la roca Tarpeya?", "Ejecutar a los enemigos del estado, arrojándolos desde la cima"),
        new Question("¿Qué permitía la Lex Ovinia?", "La inclusión de los plebeyos en el Senado"),
        new Question("¿Qué rey griego intentó conquistar la península itálica en el siglo III a.C.?", "Pirro de Epiro"),
        new Question("¿La conquista de qué ciudad marcó el final de la resistencia griega en el sur de la península itálica?", "Tarento"),
        new Question("¿Quién fue el pionero en componer literatura en lengua latina?", "Livio Andrónico"),
        new Question("¿Cuántas guerras púnicas hubo?", "3"),
        new Question("¿De qué civilización era colonia Cartago?", "Los fenicios"),
        new Question("¿Dónde se desarrolló la Primera Guerra Púnica?", "En Sicilia"),
        new Question("¿Quién fue el líder de los cartagineses en la Segunda Guerra Púnica?", "Aníbal Barca"),
        new Question("¿Qué daño corporal sufrió el propio Aníbal durante la conquista de la península itálica?", "Perdió un ojo"),
        new Question("Nombra 3 batallas de la Segunda Guerra Púnica", "Tesino, Trebia, Trasimenos, Cannas, Zama…"),
        new Question("¿En qué género literario destacaron Plauto y Terencio?", "En la comedia palliata"),
        new Question("¿Qué ciudad hispana se defendió tenazmente de la conquista romana?", "Numancia"),
        new Question("¿Por qué fue asesinado Tiberio Graco?", "Por intentar realizar una reforma agraria"),
        new Question("¿Qué corriente filosófica defendía Lucrecio?", "El epicureísmo"),
        new Question("¿Quién fue el romano con más consulados en toda la República?", "Cayo Mario (7)"),
        new Question("¿Qué dos bandos políticos había en la República tardía?", "Optimates y populares"),
        new Question("¿Quiénes se enfrentaron en la Primera Guerra Civil?", "Cayo Mario y Lucio Cornelio Sila"),
        new Question("¿Quién sofocó la revuelta de los socii?", "Sila"),
        new Question("¿Quién fue Sertorio?", "Un lugarteniente de Cayo Mario que lideró una fuerte resistencia contra Roma desde Hispania"),
        new Question("¿Quién lideró una revuelta esclavista?", "Espartaco"),
        new Question("¿Quiénes conformaron el primer Triunvirato?", "Julio César, Pompeyo Magno y Craso"),
        new Question("¿Con quién se casó Julia, la hija de César?", "Con Pompeyo"),
        new Question("¿Quién detuvo el golpe de estado de Catilina?", "Cicerón"),
        new Question("¿Qué militar conquistó Judea y liberó el Mediterráneo de la piratería?", "Pompeyo Magno"),
        new Question("¿Cómo llamaban los romanos al Mediterráneo?", "Mare Nostrum"),
        new Question("¿Quién fue el mayor enemigo de César en las Galias?", "Vercingétorix"),
        new Question("¿Dónde derrotó por completo César a la coalición gala?", "En la batalla de Alesia"),
        new Question("¿Qué cargo ocupaba César durante la conquista de las Galias?", "El proconsulado"),
        new Question("¿Cómo murió Craso?", "Derrotado por los partos en Carras"),
        new Question("¿Qué río cruzó César, marcando así el inicio de la Segunda Guerra Civil?", "El Rubicón"),
        new Question("Menciona 3 batallas de la Segunda Guerra Civil", "Farsalia, Tapso, Alejandría, Munda, Dirraquio, Ilerda…"),
        new Question("¿Cómo murió Pompeyo?", "Traicionado y decapitado en Egipto"),
        new Question("¿Cómo se llamaba el hijo de César y Cleopatra?", "Cesarión"),
        new Question("¿Quién reformó el desfasado calendario lunar romano por uno solar?", "Julio César"),
        new Question("¿Qué obras compuso César?", "Los comentarios de la Guerra de las Galias y la Guerra Civil"),
        new Question("¿En qué año fue asesinado Julio César?", "44 a.C."),
        new Question("¿En qué fecha fue asesinado Julio César?", "En las Idus de Marzo (15M)"),
        new Question("¿Quiénes lideraron de la conspiración para asesinar a Julio César?", "Bruto y Casio"),
        new Question("¿Dónde se derrotó a los cesaricidas?", "En la batalla de Filipos"),
        new Question("¿Quiénes conformaron el segundo Triunvirato?", "Octaviano, Marco Antonio y Lépido"),
        new Question("¿Qué general comandaba las tropas de Augusto?", "Agripa"),
        new Question("¿Junto a quién cogobernó Egipto Cleopatra VII?", "Su hermano, Ptolomeo XIII"),
        new Question("¿Con quiénes tuvo un romance Cleopatra VII?", "Julio César y Marco Antonio"),
        new Question("¿Qué suceso marca el comienzo del Imperio?", "La batalla de Accio"),
        new Question("¿En qué año se produjo la batalla de Accio?", "31 a.C."),
        new Question("¿Quién ejerció como ministro de cultura durante el principado de Augusto?", "Mecenas"),
        new Question("¿Quién fue nombrado princeps senatus?", "Octavio Augusto"),
        new Question("Menciona alguna obra de Virgilio", "La Eneida, las Bucólicas o las Geórgicas"),
        new Question("Menciona alguna obra de Horacio", "Las Odas, Sátiras, Ars Poeticae o el Carmen Saeculare"),
        new Question("Menciona alguna obra de Ovidio", "El Ars Amandi, Metamorfosis, Heroidas…"),
        new Question("¿Qué obra escribió Tito Livio?", "El Ab urbe condita"),
        new Question("¿Dónde se produjo la mayor derrota romana en tierras germanas?", "En el bosque de Teotoburgo"),
        new Question("¿Quién fue Arminio?", "Un líder querusco que unificó a los pueblos germánicos y derrotó a Roma en Teutoburgo"),
        new Question("¿Qué emperador romano tubo el reinado más largo?", "Augusto, quien gobernó 41 años"),
        new Question("¿Bajo el mandato de qué emperador se termina de conquistar la península ibérica?", "Bajo el mandato de Augusto"),
        new Question("Nombra 3 emperadores de la dinastía Julio-Claudia", "Augusto, Tiberio, Calígula, Claudio o Nerón"),
        new Question("¿Quién fue Sejano?", "Un miembro de la guardia pretoriana, que llegó a gobernar de facto el imperio durante el retiro de Tiberio a la isla de Capri (26-31)"),
        new Question("¿Qué era la guardia pretoriana?", "La unidad de élite encargada de proteger a los emperadores romanos"),
        new Question("¿Bajo el reinado de qué emperador se conquistó Britania?", "Bajo el mandato de Claudio"),
        new Question("¿A qué familiar mandó ejecutar Nerón?", "A su propia madre"),
        new Question("¿Bajo el reinado de qué emperador se produjo el incendio de Roma?", "Bajo el mandato de Nerón"),
        new Question("¿Qué corriente filosófica defendía Séneca?", "El estoicismo"),
        new Question("¿Qué obra escribió Lucano?", "La Farsalia"),
        new Question("¿Quiénes se enfrentaron en el Año de los cuatro emperadores (68-69)?", "Galba, Otón, Vitelio y Vespasiano"),
        new Question("¿Qué victoria conmemora el Arco de Tito?", "La toma de Jerusalén"),
        new Question("¿Qué general conquistó Caledonia (Escocia)?", "Agrícola"),
        new Question("¿Durante el reinado de qué dinastía se construyó el Coliseo?", "Los Flavios"),
        new Question("¿Bajo el reinado de qué emperador erupcionó el Vesubio?", "Bajo el mandato de Tito"),
        new Question("¿Qué ciudades quedaron sepultadas por el monte Vesubio?", "Pompeya y Herculano"),
        new Question("¿Qué emperador estableció el limes germanicus?", "Domiciano"),
        new Question("¿Qué era la damnatio memoriae?", "Una condena romana que buscaba borrar el legado de una persona tras su muerte o caída en desgracia"),
        new Question("Nombra 3 emperadores de la dinastía Antonina", "Nerva, Trajano, Adriano, Antonino Pío, Marco Aurelio o Cómodo"),
        new Question("¿Qué emperador conquistó la Dacia?", "Trajano"),
        new Question("¿Quién fue Decébalo?", "El último rey de la Dacia y uno de los enemigos más formidables de Roma"),
        new Question("¿A qué territorio actual equivale la Dacia?", "Rumanía"),
        new Question("¿Qué emperador estableció Arabia y Armenia como provincias romanas?", "Trajano"),
        new Question("¿Dónde se encuentra el Muro de Adriano?", "En la frontera entre Inglaterra y Escocia"),
        new Question("¿Qué emperador erigió el Panteón de Agripa?", "Adriano"),
        new Question("¿Cuál era la capital del imperio parto?", "Ctesifonte"),
        new Question("¿Qué doctrina filosófica seguía Marco Aurelio?", "El estoicismo"),
        new Question("¿Qué emperador proclamó la Constitutio Antoniniana?", "Caracalla"),
        new Question("¿En qué consistía la Constitutio Antoniniana?", "Concedía la ciudadanía romana a todos los hombres libres del Imperio"),
        new Question("¿Qué emperador nombró un dios solar como principal deidad del panteón romano?", "Heliogábalo"),
        new Question("¿Qué fue el Imperio de Palmira?", "Un estado independiente que surgió durante la crisis del siglo III en Egipto, Siria y Judea"),
        new Question("¿Qué emperador trajo la paz al imperio tras la crisis del siglo III?", "Diocleciano"),
        new Question("¿Durante el reinado de qué emperador se produjo la Gran persecución contra los cristianos?", "Durante el mandato de Diocleciano"),
        new Question("¿Qué fue la tetrarquía?", "Un sistema de gobierno imperial formado por cuatro líderes: dos augustos y dos césares de menor poder"),
        new Question("¿A quién derrotó Constantino en la batalla del Puente Milvio?", "Al emperador Majencio"),
        new Question("¿En qué año se promulgó el Edicto de Milán?", "En el 313"),
        new Question("¿En qué consistía el Edicto de Milán?", "Garantizaba la libertad religiosa en todo el Imperio Romano"),
        new Question("¿Quién promulgó el Edicto de Milán?", "El emperador Constantino"),
        new Question("¿Cuál fue el primer concilio ecuménico de la Iglesia cristiana?", "El Concilio de Nicea (325)"),
        new Question("¿A qué ciudad trasladó Constantino la capital del Imperio?", "A Bizancio, la futura Constantinopla"),
        new Question("¿Qué fue la Gran Conspiración del 367?", "Un ataque simultaneo de pictos, germanos y francos sobre las fronteras del Imperio"),
        new Question("¿Dónde derrotaron los godos al emperador Valente?", "En el desastre de Adrianópolis (378)"),
        new Question("¿En qué año se promulgó el Edicto de Tesalónica?", "En el 381"),
        new Question("¿En qué consistía el Edicto de Tesalónica?", "Establecía el cristianismo como religión oficial del Imperio Romano"),
        new Question("¿Quién promulgó el Edicto de Tesalónica?", "El emperador Teodosio"),
        new Question("¿Entre quiénes repartió el Imperio Teodosio?", "Entre sus hijos Arcadio (Oriente) y Honorio (Occidente)"),
        new Question("¿En qué año se produjo la división del Imperio romano?", "En el 395"),
        new Question("¿A dónde se trasladó la capital del Imperio romano de Occidente en el siglo V?", "A Rávena"),
        new Question("¿En qué año se produjo el saqueo de Roma?", "En el 410"),
        new Question("¿Qué es el Código Teodosiano?", "Una recopilación de leyes, promulgadas en el siglo V, que buscaban la centralización legal y religiosa del Imperio romano"),
        new Question("¿Qué tribu bárbara conquistó el África romana?", "Los vándalos"),
        new Question("¿Quién fue Atila?", "Fue un rey de los hunos que derrotó en repetidas ocasiones a los ejércitos romanos en el siglo V"),
        new Question("¿Dónde se derrotó a Atila?", "En la batalla de los Campos Cataláunicos (451)"),
        new Question("¿Quién fue el último emperador del Imperio romano de Occidente?", "Rómulo Augústulo"),
        new Question("¿En qué año cayó el Imperio romano de Occidente?", "En el 476"),
        new Question("¿Qué líder de los hérulos depuso al último emperador del Imperio romano de Occidente?", "Odoacro"),
        new Question("¿En qué año cayó el Imperio romano de Oriente?", "En 1453"),
        new Question("¿Qué emperador del Imperio romano de Oriente intentó recuperar los territorios occidentales en el siglo VI?", "Justiniano I"),
        new Question("¿De qué era dios Jano?", "De los comienzos y las puertas"),
        new Question("¿Dónde se custodiaba el erario público de Roma?", "En el templo de Saturno"),
        new Question("Menciona 3 divinidades orientales cuyo culto se introdujo en el imperio romano", "Isis, Mitra y Cibeles"),
        new Question("¿Cuál era el animal sagrado de Juno?", "El pavo real"),
        new Question("¿De qué era dios Apolo?", "De la música, las artes, el sol, la profecía, la poesía…"),
        new Question("¿Quién era el dios de la medicina?", "Esculapio (Asclepio)"),
        new Question("¿Dónde se realizaban los desfiles y el entrenamiento militar en la Antigua Roma?", "En el Campo de Marte"),
        new Question("¿Cuál era el animal sagrado de Júpiter?", "El águila"),
        new Question("¿De qué era diosa Ceres?", "De la agricultura"),
        new Question("Menciona 3 ciudades de la Hispania romana", "Emerita Augusta, Tarraco, Corduba, Gades, Caesar Augusta, Italica…"),
        new Question("¿Cuál era la ciudad más importante del Egipto romano?", "Alejandría"),
        new Question("¿Cuál era la ciudad más importante de la Siria romana?", "Antioquía"),
        new Question("¿Qué mar baña las costas orientales de la península itálica?", "El Adriático"),
        new Question("¿Qué mar baña las costas occidentales de la península itálica?", "El Tirreno"),
        new Question("¿Cómo llamaban los romanos a la actual Cerdeña?", "Sardinia"),
        new Question("¿Cómo llamaban los romanos a la actual Lyon?", "Lugdunum"),
        new Question("¿Cómo llamaban los romanos a la actual Marsella?", "Massilia"),
        new Question("¿Cómo llamaban los romanos a la actual París?", "Lutetia"),
        new Question("¿Cómo llamaban los romanos a la actual Estambul?", "Bizancio"),
        new Question("¿Cómo llamaban los romanos a la actual Mérida?", "Emerita Augusta"),
        new Question("¿Cómo llamaban los romanos al mar Negro?", "Ponto Euxino"),
        new Question("¿Quién era el flamen dialis?", "El sacerdote de Júpiter"),
        new Question("¿Quiénes eran los dioscuros?", "Cástor y Pólux"),
        new Question("¿Quiénes eran las vestales?", "Sacerdotisas vírgenes dedicadas a mantener encendido el fuego sagrado de Roma en el templo de Vesta"),
        new Question("¿Qué eran los faunos?", "Criaturas mitológicas con cuerpo de hombre y patas y cuernos de cabra, asociados con la naturaleza, la lascivia y la música"),
        new Question("¿Qué eran los lares?", "Divinidades protectoras del hogar y la familia"),
        new Question("¿Qué dios tenía la función de psicopompo?", "Mercurio"),
        new Question("¿Cómo se llamaba el barquero del inframundo?", "Caronte"),
        new Question("¿Dónde vivían los dioses?", "En el monte Olimpo"),
        new Question("¿Dónde vivían las musas?", "En el monte Helicón"),
        new Question("¿Dónde vivía la Sibila?", "En Cumas"),
        new Question("¿Qué era la Sibila?", "Una sacerdotisa de Apolo que profetizaba el futuro y le ofreció los Libros Sibilinos a Tarquinio el Soberbio"),
        new Question("¿Cuál es el equivalente griego de Proserpina?", "Perséfone"),
        new Question("¿Cuál es el equivalente griego de Vulcano?", "Hefesto"),
        new Question("Declina rosa-ae en singular y plural", "rosa, -am, -ae, -ae, -a, -ae, -ae, -as, -arum, -is, -is"),
        new Question("Declina domus-i en singular y plural", "domus, -e, -um, -i, -o, -o, -i, -i, -os, -orum, -is, -is"),
        new Question("Declina vir-i en singular y plural", "vir, -um, -i, -o, -o, -i, -i, -os, -orum, -is, -is"),
        new Question("Declina vis-vis en singular y plural", "vis, vim, vis, vi, vi, vires, vires, virium, viribus, viribus"),
        new Question("Declina lex-legis en singular y plural", "lex, legem, legis, legi, lege, leges, leges, legum, legibus, legibus"),
        new Question("Declina peplum-i en singular y plural", "peplum, -um, -i, -o, -o, -a, -a, -orum, -is, -is"),
        new Question("Declina mare-is en singular y plural", "mare, -em, -is, -i, -i, -ia, -ia, -ium, -ibus, -ibus"),
        new Question("Declina el pronombre personal en primera persona en singular y plural", "ego, me, mei, mihi, me, nos, nos, nostrum, nobis, nobis"),
        new Question("Enuncia los 3 pronombres demostrativo del latín", "hic-haec-hoc, iste-a-ud y ille-a-ud"),
        new Question("Enuncia el verbo amo", "amo, amas, amare, amavi, amatum"),
        new Question("¿Cuántas declinaciones tiene el latín?", "5"),
        new Question("¿Cuántas conjugaciones tiene el latín?", "4"),
        new Question("Conjuga el verbo moneo en presente de indicativo", "moneo, mones, monet, monemus, monetis, monent"),
        new Question("Conjuga el verbo lego en presente de indicativo de la voz pasiva", "legor, legeris, legitur, legimus, legimini, leguntur"),
        new Question("¿Cuál es el participio de presente del verbo amo?", "Amans-amantis"),
        new Question("¿Cuál es el infinitivo de futuro del verbo lego?", "Lecturus esse"),
        new Question("¿Cuál es el participio de futuro pasivo del verbo audio?", "Audiendum sum"),
        new Question("Enuncia el verbo fero", "fero, fers, ferre, tuli, latum"),
        new Question("Enuncia el verbo volo", "volo, vis, velle, volui, (-)"),
        new Question("¿Cómo se expresa la procedencia en latín?", "E/ex, a/ab, de + Ablativo"),
        new Question("¿Cómo se expresa el lugar a donde en latín?", "Ad, in + Acusativo"),
        new Question("¿Qué construcción encontramos en ''Urbe capta, milites discesserunt''?", "Un Ablativo Absoluto"),
        new Question("¿Quién es el sujeto del infinitivo en la frase ''Magister pueros laborare iubet''?", "El Acusativo pueros"),
        new Question("¿Qué valor tiene cum en esta frase ''Cum hostes appropinquarent, Romani urbem defendere pareverunt''?", "Tiene un valor temporal-causal"),
        new Question("¿Qué valor tiene la oración de ut en ''Milites laborant ut urbem defendant''?", "Tiene un valor final"),
        new Question("¿Cómo se traduce la frase ''Carthago delenda est''?", "Cartago debe ser destruida"),
        new Question("¿Cómo se traduce la frase ''Puella librum legit, quem magister dederat''?", "La niña lee el libro, que el maestro le había entregado"),
        new Question("¿Qué valor sintáctico tiene laetum en la frase ''Puerum laetum video''?", "Complemento predicativo"),
        new Question("¿Qué valor sintáctico tiene regina en la frase ''Cleopatra, regina aegyptorum, legit multos libros''?", "Aposición"),
        new Question("¿Qué significan las siglas S.P.Q.R?", "Senatus Populusque Romanus"),
        new Question("¿Cómo se llaman las lenguas que evolucionaron del latín?", "Lenguas romances"),
        new Question("¿Qué era el latín vulgar?", "El latín hablado por el pueblo, que contrasta con el latín clásico de la literatura y las clases cultas"),
        new Question("¿Cómo se dice ''campamento'' en latín?", "Castra"),
        new Question("¿Cómo se dice ''soldado'' en latín?", "Miles"),
        new Question("¿Cómo se dice ''jinete'' en latín?", "Eques"),
        new Question("¿Cómo se dice ''espada'' en latín?", "Gladius"),
        new Question("¿Cómo se dice ''lanza'' en latín?", "Pilum"),
        new Question("Nombra 3 restos romanos que podemos encontrar a día de hoy en Castilla y León", "El acueducto de Segovia, la villa romana La Olemeda o Almenara-Puras, las minas de las Médulas, la ciudad de Clunia…"),
    };
    
    // Lista de preguntas disponibles (se irá reduciendo conforme se usen)
    private static List<Question> availableQuestions = new ArrayList<>();
    
    // Inicializar la lista de preguntas disponibles
    static {
        resetQuestions();
    }
    
    /**
     * Obtiene una pregunta aleatoria que no se haya usado aún
     * @return Una pregunta aleatoria, o null si se acabaron las preguntas
     */
    public static Question getRandomQuestion() {
        if (availableQuestions.isEmpty()) {
            return null; // O podrías resetear las preguntas automáticamente
        }
        
        // Tomar una pregunta aleatoria y eliminarla de las disponibles
        int randomIndex = (int)(Math.random() * availableQuestions.size());
        return availableQuestions.remove(randomIndex);
    }
    
    /**
     * Reinicia todas las preguntas para que vuelvan a estar disponibles
     */
    public static void resetQuestions() {
        availableQuestions.clear();
        Collections.addAll(availableQuestions, ALL_QUESTIONS);
    }
    
    /**
     * Obtiene el número de preguntas disponibles
     * @return Número de preguntas que aún no se han usado
     */
    public static int getAvailableQuestionsCount() {
        return availableQuestions.size();
    }
    
    /**
     * Obtiene el número total de preguntas
     * @return Número total de preguntas en el sistema
     */
    public static int getTotalQuestionsCount() {
        return ALL_QUESTIONS.length;
    }
}