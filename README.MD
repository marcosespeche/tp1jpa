# Desarrollo del Trabajo Práctico de Persistencia con JPA

## Creación de paquetes y entidades

Luego de analizar el diagrama de clases presentado, lo primero a realizar fue la creación de dos paquetes en la carpeta *src/main/java/com.marcosespeche.tp1*, uno para las entidades del negocio y otro para las interfaces que servirán para la persistencia de dichas entidades. Además, se añadió una clase llamada *EntidadBase* que contiene un atributo *long id* que funcionará como su clave primaria y además implementa la interfaz *serializable* para permitir que todas las demás clases que hereden de *EntidadBase* tengan también una clave primaria del mismo tipo y que implementen la interfaz mencionada. El atributo de clave primaria también fue configurado para que sea autoincremental y gestionado por el motor de base de datos, en este caso *H2*.

## Creación de repositorios

A cada entidad le corresponde una interfaz de repositorio, así que se creó una para cada una. Todas deben extender *JpaRepository* para funcionar, parametrizado en el tipo de objeto (nombre de la clase) que va a guardar el repositorio y en el tipo de dato que es la clave primaria. Como todas heredan de entidad base, la clave primaria de todas es *long id* así que la parametrización quedaría <*nombre_clase*, long>. Además, antes de la declaración de la interfaz, hay que añadir la anotación *@Repository* para que funcione.

## Configuración de las entidades

Primero se añadió a cada entidad, las anotaciones básicas que deben tener: *@Builder* para que implementen el patrón de diseño del mismo nombre, *@NoArgsConstructor* y *@AllArgsConstructor* para generar los constructores sin argumentos y con todos los argumentos, *@Entity* para indicar que es una entidad que será persistida por algún motor de base de datos, y *@Data* para generar todos los métodos *getters y setters*.

Luego, analizando el diagrama de clases, se añadieron sin mayores complicaciones los atributos de negocio con su tipo de dato correspondiente, a excepción de los *enums* que fueron tratados como *String* a petición del docente a cargo.

## Relaciones entre clases

Primero se definieron las relaciones entre clases basándose en la navegabilidad y cardinalidad según el diagrama de clases, es decir, por ejemplo para la clase *Pedido*, como un *Cliente* tiene *n* pedidos y la navegabilidad va de *Cliente* a *Pedido*, se añadio en la clase *Cliente* un nuevo atributo que representa la relación entre ambas clases. En este caso, como es del tipo *OneToMany* se utilizó una Lista parametrizada en *Pedido*. 
> Acá surgió un problema: En un principio se había utilizado *ArrayList* para representar la relación. pero ya que JPA e Hibernate al parecer no soportan el uso de este tipo de dato, la relación se configuró para que sea de tipo *List* y como es una clase abstracta, al momento de instanciarla se la creó con el constructor de un *ArrayList*
En la etiqueta @OneToMany, se usa en algunas entidades la configuración *cascade = CascadeType.ALL*, esto para indicar que si por ejemplo se guarda en la Base de Datos un objeto de tipo *Cliente*, también deben guardarse en la base de datos los objetos de *Pedido* que se relacionen con el cliente. Esto sin embargo, no puede usarse de forma irresponsable, ya que *CascadeType.ALL* hace que TODAS las operaciones que se realicen sobre una instancia se realicen sobre las que se relacionan también, incluyendo guardar (incluso si ya estaban guardadas en la base de datos, lo cual podría generar problemas), borrar, actualizar, etc.
También se añadió la anotación @JoinColumn que se usa para indicar con la configuración *name = nombre_fk* el nombre con el que el motor creará la columna con la clave foránea de la relación. JPA se encarga de definir en qué clase se guardará dicha clave foránea. Como es una relación de 1 a N, se guarda la clave del 1 en la del N.
De forma análoga, se fueron añadiendo el resto de relaciones según el diagrama de clases.

## Método main

Primero se creó la estructura básica de un método main de Spring, buscando en internet la plantilla. A medida que se fue avanzando con el trabajo práctico, se añadieron relaciones en la clase main con las interfaces de los repositorios a usar, usando la anotación *@Autowired*. Esto es para que Spring utilice su funcionalidad de *Inversión de Control* e *Inyección de Dependencias* para que en tiempo dinámico se creen objetos del tipo deseado y se los vincule con la clase que tiene la lógica de negocio. 
Usando el patrón de diseño *Builder*, se crean primero un rubro y 3 productos, con el método *.add* se añaden al rubro para indicar la relación que tienen. Como la clase *Rubro* tiene la configuración de *CascadeType.ALL*, al guardar el rubro se guardan los productos en una sola transacción. Luego se crean un cliente, un domicilio que se relaciona con el cliente, y un pedido. Se recupera de la base de datos los productos previamente creados y se los usa para crear los detalles del pedido. Luego se crea la factura, se vinculan todas las entidades necesarias al pedido y se lo persiste en la base de datos.

>Recordar que existe el archivo *aplication.properties*, fue configurado copiando y pegando el ejemplo otorgado por la cátedra. 

# Conclusiones y aprendizajes

El uso de frameworks como Spring, dependencias como Lombock, JPA, SpringDev, etc. facilitan mucho el desarrollo de programas en Java, gracias a las anotaciones y a la inyección de dependencias, ya que nos ahorran muchas líneas de código. Sin embargo, no fue tarea fácil acostumbrarse al nuevo marco de trabajo, fueron muchos conceptos nuevos a la vez, como JPA, Hibernate, Spring, Spring Boot, Gradle, Lombock, etc. Por lo demás, la creación de las relaciones en sí no fue difícil. El mayor problema surgió porque en mi ignorancia decidí añadir la configuración *CascadeType.ALL* en todas las relaciones, lo cual fue imprudente y me generó ciertos errores, al querer persistir por ejemplo los detalles del pedido, Hibernate también quería guardar en la Base de Datos los productos relacionados, pero ya existían en la base de datos, por lo que tuve que quitar el cascadeo. Así, me di cuenta también que al guardar el Pedido no me guardaba el cliente, porque la relación va de cliente hacia pedido, entonces el cascadeo se produce también en esa dirección. Si hubiese guardado el cliente se hubiese guardado el pedido pero no al revés. Fue una experiencia muy enriquecedora y me dejó con ganas de seguir aprendiendo sobre el desarrollo del backend en arquitecturas cliente-servidor. 
