# Netbour
<img src="readme/logo.png" alt="Netbour" style="width: 200px;"/>
## Javier J. Medinilla Agüera
<img src="readme/jmedinilla.png" alt="JMedinilla" style="width: 200px;"/>

## Índice

1. [Introducción](#introducción)
2. [Objetivos del proyecto](#objetivos-del-proyecto)
3. [Planificación del proyecto](#planificación-del-proyecto)
	- Costes
4. [Análisis y diseño del sistema](#análisis-y-diseño-del-sistema)
	- Diagrama de la base de datos y entidades
	- Diccionario de datos
	- Ejemplos de consultas a la base de datos
5. [Especificaciones del sistema](#especificaciones-del-sistema)
	- Justificación de las herramientas utilizadas
	- Configuración de la aplicación
	- Especificaciones hardware del sistema
6. [Especificaciones del software](#especificaciones-del-software)
	- Navegación de la aplicación y descripción de operaciones
7. [Código fuente relevante](#código-fuente-relevante)
8. [Conclusiones del proyecto](#conclusiones-del-proyecto)
9. [Apéndice](#apéndice)
10. [Bibliografía](#bibliografía)

## Introducción

La aplicación Netbour funciona en el día a día de la vida de un vecino dentro de su comunidad, con la intención de mejorar y facilitar el trabajo de administrador del presidente y el administrador de la finca, así como la convivencia entre todos los vecinos del inmueble.

La idea tiene lugar debido a la poca presencia en el mercado de aplicaciones similares, de forma que imponerse en el ámbito con un producto de calidad debería ser más sencillo que en otros ámbitos que ya cubren otras aplicaciones ya existentes, como en el campo de los videojuegos, ocio, redes sociales o herramientas en general.

En este caso, la aplicación se distingue del resto debido a su sencillez, la cantidad de opciones que ofrece a los vecinos para comunicarse entre ellos y con el administrador de todos los problemas y temas en general que tengan que ver con la comunidad de vecinos. Por otro lado, y aunque el usuario no sea consciente de ello, también la distingue el uso de tecnologías más contemporáneas y avanzadas que las pocas herramientas similares que ya existen, que han quedado mayormente obsoletas, usan sistemas anticuados e interfaces totalmente alejadas de todos los patrones que define Google como Material Design.

La necesidad de esta aplicación surge, como ya ha sido explicado, a la falta de herramientas similares para la gestión de una comunidad de vecinos. El primer antecedente surge al ver la aplicación que utiliza uno de los profesores que imspiran y evalúan este proyecto integrado, viendo los defectos con los que esta cuenta y lo que se podría mejorar.

## Objetivos del proyecto

El objetivo final consiste en disponer de un producto profesional y de calidad con valor y posición reales dentro del mercado.

Para ello, los objetivos principales de la aplicación consisten en crear y hacer uso de una base de datos no relacional en Firebase, una base de datos estructurada en forma de documento JSON con lectura en tiempo real, de forma que los vecinos puedan consultar todos los datos referentes a la comuniada con la mayor rapidez posible. También se pretenden crear interfaces lo más actualizadas a los tiempos que corren y cumpliendo tantos patrones de Material Design como fueran posibles, un código totalmente refactorizado y documentado, facilitando todo lo posible el mantenimiento de la aplicación, su limpieza y las posibles modificaciones y ampliaciones que pudieran ser necesarias.

## Planificación del proyecto

Fechas estimadas de inicio y fin del proyecto: 25 de marzo de 2017 - 16 de junio de 2017
Recursos necesarios: n/a

Tareas a realizar:
- Diseño de interfaces
- Conexiones a la base de datos
- Lectura de datos
- Funciones CRUD
- Login
- Registro de usuarios
- Notificaciones
- Servicios
- Refactorización
- Desarrollo avanzado de interfaces
- Manuales de administrador y usuario
- Página web
- Documentación del código
- Refactorización e implementación de sugerencias
- Corrección de errores

### Costes
Precio por hora: 6 euros

| Función                        | Fecha de inicio | Fecha de fin | Horas   | Coste total (€) |
|--------------------------------|-----------------|--------------|---------|-----------------|
| Diseño de interfaces           | 27 marzo        | 30 marzo     | 14      | 84              |
| Conexiones a la base de datos  | 31 marzo        | 3 abril      | 8       | 48              |
| Lectura de datos               | 4 abril         | 6 abril      | 8       | 48              |
| Funciones de CRUD              | 7 abril         | 13 abril     | 14      | 84              |
| Login                          | 14 abril        | 19 abril     | 4       | 24              |
| Registro de usuarios           | 20 abril        | 21 abril     | 3       | 18              |
| Notificaciones                 | 24 abril        | 28 abril     | 16      | 96              |
| Servicios                      | 1 mayo          | 3 mayo       | 8       | 48              |
| Refactorización                | 4 mayo          | 12 mayo      | 10      | 60              |
| Desarrollo avanzado interfaces | 10 mayo         | 18 mayo      | 20      | 120             |
| Manuales administrador/usuario | 15 mayo         | 26 mayo      | 12      | 72              |
| Página web                     | 15 mayo         | 26 mayo      | 12      | 72              |
| Documentación del código       | 29 mayo         | 16 junio     | 8       | 48              |
| Implementación sugerencias     | 29 mayo         | 16 junio     | 8       | 48              |
| Corrección de errores          | 29 mayo         | 16 junio     | 4       | 48              |
| **TOTAL**                      |                 |              | **153** | **918**         |

## Análisis y diseño del sistema

### Diagrama de la base de datos y entidades

![Diagrama](readme/eer.png)

En este diagrama se puede apreciar la estructura que sigue la base de datos, pero en formato SQL. La base de datos que utiliza la aplicación está estructurada en JSON, alojada en un servidor de Firebase. Los campos se cumplen casi en su totalidad, con la única excepción de la referencia hacia la comunidad con la que están relacionados el resto de elementos, debido a que, al estar estructurado en forma de árbol, estos elementos no necesitan una referencia a su padre, ya que cuelgan directamente de su nodo.

El nodo principal **netbour** es padre de dos nodos, **communities** y **users**. El nodo **users** contiene una lista de nodos hijo que representan cada uno de los usuarios registrados en la aplicación, contando al administrador y a todos los vecinos de las comunidades que este maneje. Por otro lado, el nodo **communities** contiene toda la información establecida para una comunidad, y de él cuelgan los nodos **documents**, **entries**, **incidences** y **meetings**, los cuales, al igual que el nodo **users**, dan lugar a una lista de nodos hijo que representan los distintos elementos de los que dispone una comunidad.

A continuación se muestra un ejemplo de la estructura que sigue la aplicación en su base de datos Firebase:

![Ejemplo Firebase](readme/exFirebase.png)

Como se puede comprobar, el nodo principal **netbour** contiene todos los hijos que conforman la base de datos, que son las comunidades y los usuarios, mientras que las comunidades están compuestas por sus propios campos, los documentos, las entradas, las incidencias y las juntas que pueden manejar los vecinos de la comunidad.

La clave primaria de cada uno de los elementos de la base de datos, a excepción de las comunidades, son valores únicos leídos al momento de la creación de cada uno de los elementos, siendo los milisegundos del sistema. Por el otro lado, la clave primaria de las comunidades son los códigos que el administrador que ha creado la comunidad, decide otorgarle. Este valor es muy importante, ya que los usuarios tendrán que utilizarlos a la hora de registrarse para poder ver los datos de la comunidad en la que están.

### Diccionario de datos

##### Usuario

- **category**: Campo que representa la categoría del usuario, pudiendo este ser *Administrador*, *Presidente* o *Vecino*, el cual será clave para determinar qué opciones puede o no realizar una vez estuviera dentro de la aplicación. A modo de ejemplo, los administradores pueden editar y eliminar cualquier publicación de la comunidad, mientras que los vecinos tan solo pueden hacer lo mismo con los elementos que ellos mismos han creado.
- **community**: Campo que representa el código de la comunidad al que pertenece el usuario, introducido en el momento del registro. Es obtenido al entrar en la aplicación, de forma que será usado para leer las listas asociadas a esta comunidad. Es decir, que este código es necesario para poder acceder a las incidencias, entradas, documentos y juntas, ya que los nodos de estas cuelgan de su comunidad.
- **deleted**: Campo que define si un elemento de la base de datos ha sido eliminado. En caso de que el administrador decida de dar de baja a un usuario puede decidir eliminarlo, de forma que, cuando el usuario intente conectarse a su comunidad, si la comprobación inicial obtiene un valor *true* para este campo, se le prohibirá el acceso.
- **door**: Campo que representa el distintivo de puerta de la vivienda del usuario en un piso de la comunidad, en el cual hay más viviendas. Este valor puede ser modificado desde el perfil posteriormente.
- **email**: Campo que representa el correo del usuario, así como su usuario para iniciar sesión en la aplicación. No se deberían dar nunca dos o más casos con un email idéntico, ya que en caso de existir un usuario con un email ya incluído en la zona de autentificación de la aplicación, no se permitirán más registros con este valor.
- **floor**: Campo que representa el distintivo del piso o bloque en el que se encuentra la vivienda del vecino al que hace referencia. Este valor puede ser modificado desde el perfil posteriormente.
- **key**: Campo extraído en el momento del registro que representa la hora del sistema
- **name**: Campo que almacena el nombre del usuario. Este valor puede ser modificado desde el perfil posteriormente.
- **phone**: Campo que representa el número de teléfono del usuario a modo informativo, en caso de que algún otro usuario quisiera comunicarse con él directamente. Este valor puede ser modificado desde el perfil posteriormente.
- **photo**: Campo que almacena una URL que contiene una fotografía, en la sección de almacenamiento de Firebase, que generará un enlace directo a la foto que el usuario decida enviar desde su perfil. Este valor puede ser modificado desde el perfil tantas veces como el usuario quiera.

| category | community | deleted | door   | email  | floor  | key  | name   | phone  | photo  |
|----------|-----------|---------|--------|--------|--------|------|--------|--------|--------|
| Long     | String    | Boolean | String | String | String | Long | String | String | String |

##### Comunidad

- **code**: Campo que representa el código de la comunidad, otorgado por el administrador en el momento de su creación e imposible de editar en el futuro. Este valor será necesario para los usuarios que se vayan a registrar, ya que en caso de elegir una comunidad erróneamente, no podrán ver ningún tipo de información al entrar en la aplicación.
- **deleted**: Campo que define si un elemento de la base de datos ha sido eliminado. En caso de que el administrador de la comunidad decida eliminarla, desaparecerá de la lista de comunidades disponibles a las que acceder y gestionar.
- **flats**: Campo que define la cantidad de viviendas que hay en la comunidad, definido por el administrador al momento de su creación y editable en el futuro. Campo meramente informativo para el administrador o los usuarios que quieran verlo desde la ventana de información de la comunidad.
- **municipality**: Campo que define el municipio en el que se encuentra la comunidad, definido por el administrador al momento de su creación y editable en el futuro. Campo meramente informativo para el administrador o los usuarios que quieran verlo desde la ventana de información de la comunidad.
- **number**: Campo que define el número de la comunidad dentro de la calle en la que se encuentra, definido por el administrador al momento de su creación y editable en el futuro. Campo meramente informativo para el administrador o los usuarios que quieran verlo desde la ventana de información de la comunidad.
- **postal**: Campo que define el código postal de la comunidad, definido por el administrador al momento de su creación y editable en el futuro. Campo meramente informativo para el administrador o los usuarios que quieran verlo desde la ventana de información de la comunidad.
- **province**: Campo que define la provincia en la que se encuentra la comunidad, definido por el administrador al momento de su creación y editable en el futuro. Campo meramente informativo para el administrador o los usuarios que quieran verlo desde la ventana de información de la comunidad.
- **street**: Campo que define el tipo de vía y el nombre de la misma en la que se encuentra la comunidad, definida por el administrador al momento de su creación y editable en el futuro. Campo meramente informativo para el administrador o lod usuarios que quieran verlo desde la ventana de información de la comunidad.

| code   | deleted | flats | municipality | number | postal | province | street |
|--------|---------|-------|--------------|--------|--------|----------|--------|
| String | Boolean | Long  | String       | String | String | String   | String |

##### Documento

- **authorEmail**: Campo que almacena el correo del usuario que ha creado e introducido el documento en la comunidad, de forma informativa para el administrador que tiene acceso a la base de datos.
- **deleted**: Campo que define si un elemento de la base de datos ha sido eliminado. En caso de que el administrador de la comunidad o su autor decida eliminarlo, desaparecería de la lista de documentos disponibles.
- **description**: Campo que otorga al documento una descripción del mismo, introducida por el autor al momento de introducir el documento.
- **key**: Campo definido como clave única del documento, extraída de la hora del sistema en el momento de su creación.
- **link**: Campo que almacena la URL del documento que se está introduciendo en la lista de documentos, de forma que al pulsar en dicho documento, se ofrezca al usuario visitar la dirección del mismo.
- **title**: Campo que define un título para el documento, introducido por su autor al momento de la creación del mismo.

| authorEmail | deleted | description | key  | link   | title  |
|-------------|---------|-------------|------|--------|--------|
| String      | Boolean | String      | Long | String | String |

##### Entrada

- **authorEmail**: Campo que almacena el correo del usuario que ha creado e introducido la entrada en la comunidad, de forma informativa para el administrador que tiene acceso a la base de datos.
- **authorName**: Campo que almacena el nombre del usuario que ha creado e introducido la entrada en la comunidad, de forma informativa para todos los usuarios de la comunidad que consulten la lista y puedan ver el nombre de su autor.
- **category**: Campo que define, mediante un 1 o un 2, si la entrada es de categoría *administrativa* en el primer caso, o *comunitaria* en el segundo, indicando su importancia, así como la vista de la aplicación desde la que va a poder ser visualizada la entrada.
- **content**: Campo que otorga a la entrada una descripción de la misma, introducida por el autor al momento de introducir el documento.
- **date**: Campo que define los milisegundos del sistema, extraídos al momento de la creación del elemento. Es un campo redundante, ya que en la totalidad de los casos concidirá con el valor de la clave primaria, pero fue añadido por la claridad que aporta al administrador que revise la base de datos.
- **deleted**: Campo que define si un elemento de la base de datos ha sido eliminado. En caso de que el administrador de la comunidad o su autor decida eliminarlo, desaparecería de la lista de entradas disponibles.
- **key**: Campo definido como clave única de la entrada, extraída de la hora del sistema en el momento de su creación.
- **title**: Campo que define un título para la entrada, introducido por su autor al momento de la creación de la misma.

| authorEmail | authorName | category | content | date | deleted | key  | title  |
|-------------|------------|----------|---------|------|---------|------|--------|
| String      | String     | Long     | String  | Long | Boolean | Long | String |

##### Incidencia

- **authorEmail**: Campo que almacena el correo del usuario que ha creado e introducido la incidencia en la comunidad, de forma informativa para el administrador que tiene acceso a la base de datos.
- **authorName**: Campo que almacena el nombre del usuario que ha creado e introducido la incidencia en la comunidad, de forma informativa para todos los usuarios de la comunidad que consulten la lista y puedan ver el nombre de su autor.
- **date**: Campo que define los milisegundos del sistema, extraídos al momento de la creación del elemento. Es un campo redundante, ya que en la totalidad de los casos concidirá con el valor de la clave primaria, pero fue añadido por la claridad que aporta al administrador que revise la base de datos.
- **deleted**: Campo que define si un elemento de la base de datos ha sido eliminado. En caso de que el administrador de la comunidad o su autor decida eliminarlo, desapareciendo de la lista de incidencias disponibles.
- **description**: Campo que otorga a la incidencia una descripción de la misma, introducida por el autor al momento de introducir el documento.
- **key**: Campo definido como clave única de la incidencia, extraída de la hora del sistema en el momento de su creación.
- **photo**: Campo que almacena una URL que contiene una fotografía, en la sección de almacenamiento de Firebase, que generará un enlace directo a la foto que el usuario decida enviar adjunta a la incidencia. Este valor no podrá ser modificado en el futuro.
- **title**: Campo que define un título para la incidencia, introducido por su autor al momento de la creación de la misma.

| authorEmail | authorName | date | deleted | description | key  | photo  | title  |
|-------------|------------|------|---------|-------------|------|--------|--------|
| String      | String     | Long | Boolean | String      | Long | String | String |

##### Junta

- **authorEmail**: Campo que almacena el correo del usuario que ha creado e introducido la junta en la comunidad, de forma informativa para el administrador que tiene acceso a la base de datos.
- **date**: Campo que define el día, mes y año que el administrador ha definido para la junta al momento de su creación.
- **deleted**: Campo que define si un elemento de la base de datos ha sido eliminado. En caso de que el administrador de la comunidad o su autor decida eliminarlo, desapareciendo de la lista de juntas disponibles.
- **description**: Campo que otorga a la junta una descripción de la misma, introducida por el autor al momento de introducir el documento, que en este caso, solo puede ser un administrador.
- **key**: Campo definido como clave única de la junta, extraída de la hora del sistema en el momento de su creación.

| authorEmail | date | deleted | description | key  |
|-------------|------|---------|-------------|------|
| String      | Long | Boolean | String      | Long |

### Ejemplos de consultas a la base de datos

Las consultas que se realizan a la base de datos Firebase son tan simples, que ni siquiera se pueden considerar como tal. La estructura en forma de documento JSON facilita la recuperación de datos, tan solo apuntando a los nodos donde se encuentra la información relevante a recoger y esperando a que sea la propia herramienta de Firebase quien haga la conexión asíncrona para traerse los datos de forma automática.

Para dar de alta a un usuario tan solo instanciamos un objeto de la autentificación de Firebase y le enviamos un correo y contraseña. Posteriormente, en caso de que la operación haya sido satisfactoria, se procedería a introducir al usuario dentro del apartado de base de datos, con todos sus datos.

Como se puede ver a continuación, el registro consiste en una simple llamada a un método que crea un usuario en la autentificación de Firebase, dado un correo y una contraseña, operación que puede fallar en caso de que el email no sea una dirección válida, ya lo esté usando otro usuario, o la contraseña tenga menos de seis caracteres.

```java
FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    	//
                    }
                });
```

Una vez el usuario haya accedido a la aplicación, cosa que ya hace el propio registro en caso de que haya sido satisfactoria la operación, dispondrá de permisos para leer y escribir en la base de datos al estar autentificado, por lo que podrá ingresar su propia información en el nodo de usuarios, que se hará creando una referencia direccionada hasta el nodo deseado, y aplicando los valores en la misma.

```java
FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
if (user != null) {
	DatabaseReference reference = FirebaseDatabase.getInstance()
    	.getReference().child("users").child(String.valueOf(us.getKey()));
	reference.setValue(us);
}
```

Una vez dentro de la aplicación, el usuario tendrá disponibles varias opciones a las que acceder, pero la gran mayoría se tratan de listas, que siguen exactamente el mismo patrón a la hora de descargar sus datos, método visto a continuación en un ejemplo de código de los múlltiples que hay, para obtener las **incidencias**.

Como se puede apreciar en el siguiente ejemplo, lo primero que se recibe es el código de la comunidad a la que pertenece el usuario o a la que ha accedido el administrador. La dirección de la consulta se forma por los nodos necesarios para acceder a las incidencias de la comunidad en la que se encuentra el usuario, necesitanto por tanto acceso al nodo **netbour**, al nodo **communities**, que, dado el código de la comunidad, se accede a continuación al nodo **incidences**. Esto traerá al usuario todas las incidencias que pertenezcan a la comunidad en tiempo real, ordenadas cronológicamente gracias al método `orderByKey()`. Una vez obtenidos todos los datos, el usuario podrá acceder a todos los hijos (que representan incidencias) para construir la lista, devolviéndosela al adapter una vez termine de recorrer todos los nodos descargados.

```java
communityCode = code;
Query query = FirebaseDatabase.getInstance().getReference()
	.child("communities").child(code).child("incidences").orderByKey();
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<PoIncidence> list = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PoIncidence incidence = snapshot.getValue(PoIncidence.class);
                        if (!incidence.isDeleted()) {
                            list.add(incidence);
                        }
                    }
                    if (list.size() > 0) {
                        listener.returnList(list);
                    } else {
                        listener.returnListEmpty();
                    }
                } else {
                    listener.returnListEmpty();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.returnListEmpty();
            }
        };
```

A continuación se mostrarán ejemplos de cómo se añaden, editan y eliminan las **incidencias**, lo cual servirá también para identificar cómo se hacen el resto de operaciones de la aplicación, ya que todas las listas siguen el mismo patrón para las tres funciones especificadas.

**Añadir**. Este método recibe una incidencia, construye la referencia hasta el nodo **incidences** dentro de la comunidad actual, y guarda el valor en un nodo nuevo, dado por la clave primaria de la incidencia a insertar.

```java
private void setIncidence(PoIncidence incidence, String code, Uri u) {
        incidence.setPhoto(u.toString());
        databaseReference = FirebaseDatabase.getInstance().getReference()
        	.child("communities").child(code).child("incidences").child(String.valueOf(incidence.getKey()));
        databaseReference.setValue(incidence);
        listener.addedIncidence();
}
```

**Editar**. Este método recibe una incidencia, construye la referencia hasta el nodo **incidences** dentro de la comunidad actual y guarda el valor en un nodo ya existente, dado por la clave primaria de la incidencia a editar.

```java
public void editIncidence(PoIncidence incidence, String code) {
    databaseReference = FirebaseDatabase.getInstance().getReference()
       	.child("communities").child(code).child("incidences").child(String.valueOf(incidence.getKey()));
    databaseReference.child("description").setValue(incidence.getDescription());
    databaseReference.child("title").setValue(incidence.getTitle());
    listener.editedIncidence();
}
```

**Eliminar**. Este método funciona exactamente igual que el de editar, ya que no elimina nada en absoluto de la base de datos, sino que dato una clave primaria de incidencia, se construye una referencia apuntando a esta, y se modifica su campo *deleted*, dejando su valor en positivo para que deje de mostrarse en la lista de incidencias, que usa ese valor a modo de filtro.

```java
public void deleteIncidence(PoIncidence item) {
    databaseReference = FirebaseDatabase.getInstance().getReference()
        .child("communities").child(communityCode).child("incidences").child(String.valueOf(item.getKey()));
    databaseReference.child("deleted").setValue(true);
    listener.deletedIncidence(item);
}
```

Estos métodos de selección, adición y edición son exactamente iguales tanto para las incidencias como para las comunidades, los documentos, las entradas, las juntas, los usuarios e incluso el perfil del usuario conectado.

## Especificaciones del sistema

### Justificación de las herramientas utilizadas

Netbour cuenta con una aplicación para dispositivos móviles, un servidor en Firebase y una web a modo publicitario y manual.

La aplicación ha sido desarrollada en Java, para dispositivos móviles con el sistema operativo Android, estando esta disponible a partir de la API 16 (Jelly Bean, 4.0). Este sistema ha sido escogido debido a que es uno de los temas tratados a lo largo del curso formativo y el más extenso de todos ellos, así como al gran número de dispositivos Android que existen en el mercado, ocupando la gran mayoría de cuota de mercado de todos los usuarios de dispositivos móviles en España. En cuanto al lenguaje elegido, Java es la mejor elección, al ser ampliamente conocido, fácil de aprender y muy similar a C#, lenguaje aprendido con anterioridad en el primer curso del ciclo formativo. En un futuro se plantea migrar la aplicación a Kotlin.

Para el servidor, tanto para el almacenamiento, como la propia base de datos, se ha elegido Firebase, una herramienta para bases de datos en tiempo real, con estructura en JSON. Firebase es una plataforma de desarrollo en la nube propiedad de Google. Se trata de una herramienta diseñada para el manejo sencillo, rápido y cómodo de datos, ahorrando todo el tiempo posible al desarrollador, para que centre toda su atención a la creación de la propia aplicación Android, iOS o web, plataformas para las que ha sido diseñada Fireabase. Esta plataofrma cuenta, y provee al usuario, con una API para guardar y sincronizar datos en la nube en tiempo real.

La plataforma Firebase fue elegida debido a que su documentación en la web por parte de sus creadores, es tanto amplia como excelente, así como a su gestión de autentificación, usuarios y sesiones, los cuales el desarrollador no tiene que crear o configurar, ahorrando una cantidad de horas bastante notable. Otras ventajas que ofrece Firebase en comparación a otras herramientas para la gestión de base de datos, como PHP y SQL, son sus apartados de almacenamiento, monetización y análisis. Firebase cuenta con un apartado de almacenamiento que te permite alojar cualquier tipo de archivo de forma totalmente ajena a la propia base de datos, la opción de monetizar tus aplicaciones de forma sencilla y la sección de análisis, que ofrece visión de todas las excepciones ocurridas en la aplicación, mostrándo al desarrollador el *Log* obtenido del teléfono que ha sufrido la excepción, así como información del mismo teléfono, ofreciendo toda la información necesaria de los errores de la aplicación sin tener que contactar en ningún momento con el usuario.

Una de las pocas contrapartes de Firebase, además de su estructura JSON, que no ofrece tantas posibilidades como las bases de datos SQL, se trata de su valor, y es que, aunque tiene una versión gratuita, cuenta con planes de pago, que serán necesarios en caso de que la aplicación que usa la base de datos, cuente con alguna que otra condición restrictiva de la versión gratuita, como son 100 conexiones simultáneas, 1 GB de almacenamiento en base de datos, 5 GB de almacenamiento de archivos, 10 GB de trasnferencias mensuales, etc.

El desarrollo de la aplicación Android se ha realizado en **Android Studio 2.3.2**, con **Firebase 9.6.1** y una web configurada con Apache y PHP para Wordpress en el *hosting* **Siteground**. También se ha utilizado, de forma puntual **Sublime Text** para la edición de texto, **Photoshop** para la creación del logo de la aplicación y **MySQL Workbench** para realizar el diseño de la base de datos. Para el control de versiones y almacenamiento del código fuente de la aplicación, se ha escogido un repositorio público en la web **Github**, usando la consola de Git para Windows.

### Configuración de la aplicación

- **[EN CONSTRUCCIÓN]**

### Especificaciones hardware del sistema

En cuanto al servidor, no es necesaria ninguna especificación de su hardware, debido a que el servidor es una herramienta de Google, alojada en sus servidores, que cuenta con una zona de almacenamiento dedicada a cada base de datos que creen los desarrolladores, usando la cantidad de CPU y RAM que sea necesaria, siempre y cuando no supere un límite que pueda perjudicar al resto de usuarios de Firebase.

En cuanto a la aplicación, tampoco son necesarios requisitos exigentes. Netbour es una aplicación compatible con todas las versiones de Android desde la 4.0 (Jelly Bean) hacia adelante, abarcando así prácticamente el 98 % de todos los teléfonos móviles Android, según la información ofrecida por Google en cuanto al uso de versiones del sistema operativo

![Versiones](readme/versiones.png)

La aplicación Netbour ha sido probada en los siguientes teléfonos móviles antes de su entrega.

Todos estos dispositivos presentaban un rendimiento totalmente óptimo para la aplicación, sin ningún tipo de errores, lentitud, fallos gráficos, etc. La aplicación puede funcionar también en tablets, pero aún no ha sido diseñada para ser ejecutada en ellas de forma óptima y personalizada teniendo en cuenta sus pantallas.

```
TELÉFONO

Fabricante: Motorola
Modelo: MotoG3
API: 23
SO: 6.0
Marca: Motorola
RAM: 2 GB
Proveedor: Simyo
```

```
EMULADOR TELÉFONO (Android Studio)

Fabricante: Nexus
Modelo: 4
API: 22
SO: 5.1
Marca: Nexus
RAM: 1536 MB
Proveedor n/a
```

```
TELÉFONO

Fabricante: Motorola
Modelo: XT1572
API: 25
SO: 7.1.1
Marca: Android
RAM: 3 GB
Proveedor: Movistar
```

```
TELÉFONO

Fabricante: HUAWEI
Modelo: PLK-L01
API: 23
SO: 6.0
Marca: Honor
RAM: 3 GB
Proveedor: Vodafone
```

```
EMULADOR TELÉFONO (Genymotion)

Fabricante: Custom
Modelo: Custom
API: 16
SO: 4.1.1
Marca: Custom
RAM: 2 GB
Proveedor: -
```

## Especificaciones del software

### Navegación de la aplicación y descripción de operaciones

#### SplashScreen
Ventana de inicio de la aplicación. Durante el visionado de este cuadro de diálogo se estarán recuperando los datos del usuario conectado, de modo que se abra el Login en caso de que no haya ninguna sesión iniciada, o Home si ya ha habido una conexión anterior.

![1](readme/views/v1.png)

#### Inicio de sesión
Ventana de inicio de sesión, para usuarios ya existentes. Desde esta vista se puede, o bien introducir tus datos de inicio de sesión, o dirigirse a la ventana de registro para crear una cuenta nueva dado un código de comunidad.

![2](readme/views/v2.png)

#### Registro
Ventana de registro, que permite al usuario crearse una cuenta totalmente nueva, siempre y cuando disponga de un código de comunidad, ya que en caso contrario, no podrá ver ninguna información cuando entre a la aplicación.

![3](readme/views/v3.png)

#### Menú
NavigationDrawe de la aplicación. Al conectarse, si eres un usuario podrás ver la ventana Home, mientras que los administradores serán redirigidos automáticamente a la ventana Comunidades, ya que antes de poder ver cualquier otra sección, tendrás que seleccionar el código de la comunidad que quieren revisar.

![4](readme/views/v4.png)

#### Comunidades
Lista de comunidades para el administrador. Esta opción no está disponible para los usuarios normales, y permite al administrador crear, editar y eliminar una comunidad de vecinos, así como seleccionarla para ver, gracias a su código, el resto de apartados de la aplicación que tengan que ver con una comunidad concreta.

![5](readme/views/v5.png)

#### Formulario Comunidades
Formulario para la creación de una nueva comunidad, o la edición de una ya existente. Todos los datos introducidos, a excepción del código de comunidad, serán meramente informativos y no tendrán ninguna relevancia real, mientras que el código de la comunidad será el que los usuarios tengan que introducir en el futuro a la hora de crearse una cuenta.

![6](readme/views/v6.png)

#### Incidencias
Lista de incidencias de la comunidad. En esencia, todas las listas de la aplicación son exactamente iguales, y el único cambio real se da en el tipo de elementos que carga. Las incidencias representan problemas dentro de la comunidad que los vecinos muestran con el fin de que puedan ser arreglados, o notificar al resto de vecinos del inmueble.

![7](readme/views/v7.png)

#### Menú contextual
Menú que contienen todos las vistas que contienen una lista, a excepción de las juntas. Este menú muestra las distintas opciones que tiene el usuario para ordenar los elementos que está viendo en pantalla. Si se pulsaran dos veces seguidas sobre el mismo elemento de orden, se realizaría el orden inverso.

![8](readme/views/v8.png)

#### Formulario Incidencias
Formulario de incidencias. A grandes rasgos, todos los formularios de la aplicación son iguales. La única diferencia de este radica en la fotografía. El usuario podrá, pulsando en la imagen, seleccionar una imagen de su propia galería, siempre y cuando haya aceptado previamente los permisos de lectura de almacenamiento externo.

![9](readme/views/v9.png)

#### Tablón
Lista de entradas de la comunidad, pensada para que el administrador y el presidente puedan crear notificaciones escritas a los vecinos, con avisos importantes o no, referentes a la comunidad como entidad, ya que hay un segundo tablón, especialmente creado para los propios vecinos, que pueden crear entradas de menor importancia yh con relevancia personal, informando de temas relevantes a la comunidad.

![10](readme/views/v10.png)

#### Documentos
Lista de documentos. Tanto el administrador como el presidente pueden introducir, editar y eliminar documentos en esta lista, que consta de un formulario en el que introducir un título, una descripción, y el enlace hacia el mismo, dando la opción a los vecinos de visitar en enlace adjunto al elemento.

![11](readme/views/v11.png)

#### Juntas
Lista de juntas de la comunidad. Únicamente el administrador puede acceder a la creación de este elemento, al cual introducirá una fecha, y la información relevante de la propia junta que debería interesar a los vecinos.

![12](readme/views/v12.png)

#### Usuarios
Lista de usuarios dados de alta con el código de la comunidad que se está visualizando. Como se puede ver, el resto de usuarios podrán ver el nombre, el teléfono y la vivienda, introducidos por el propio usuario, y el email con el que inician sesión, que es invariable y serviría para mediar en casos de suplantación. Una vez el usuario ha ingresado en la comunidad, podrán ir al perfil para cambiar su fotografía también.

![13](readme/views/v13.png)

#### Perfil
Perfil del usuario conectado. Este cuenta con campos que muestran su información, siendo posible modificar el nombre, el teléfono y la vivienda, pulsando en el botón situado a la derecha de los cuatro campos. Al igual que con las incidencias, el usuario podrá pulsar en la imagen para cambiarla por otra de la galería.

![14](readme/views/v14.png)

#### Acerca de
Apartado exclusivo para el proyecto integrado. Muestra información de la aplicación y toda la información relevante de su autor.

![15](readme/views/v15.png)

#### Licencias de terceros
Apartados de la vista **Acerca de**, donde se pueden ver todas las librerías de terceros utilizadas en el proyecto, así como la licencia bajo la cual están registradas y un enlace directo a la página que la ofrece. También cuentan con la línea que les corresponde dentro del archivo de compilación. Pulsando en los botones de **Apache** y **MIT**, se podrá ver en que consisten ambas licencias, que son las que se encuentran presentes entre las librerías añadidas al proyecto.

![16](readme/views/v16.png)

#### Swipe Menu
Todas las listas cuentan con un menú de deslizamiento para sus elementos, donde se pueden encontrar tres opciones, para editar, eliminar o informar al administrador del elemento, a modo de reporte del mismo, en caso de existir algún problema con él o que el resto de vecinos consideren que no debiera encontrarse ahí.

![17](readme/views/v17.png)

#### Confirmación de edición
Cuadro de diálogo que aparece cuando se intenta editar un elemento de la lista. En caso de que toda la información introducida sea válida, se procederá a mostrar al usuario una previsualización de los cambios realizados, mostrando en gris los campos que no han variado, en rojo el valor antiguo y en verde el nuevo.

![18](readme/views/v18.png)

## Código fuente relevante

- **[EN CONSTRUCCIÓN]**

## Conclusiones del proyecto

Para concluir con la documentación de este proyecto llegamos a las conclusiones del mismo, exponiendo la evolución del propio proyecto a lo largo de los meses que ha durado su desarrollo.

En un principio, sí que es cierto que el tiempo disponible para el desarrollo del proyecto, no es espcialmente corto, pero teniendo en cuenta que su desarrollo coincide con las prácticas en las distintas empresas, se convierte en un tramo especialmente angosto del camino hacia el final del curso. Por eso mismo, me gustaría expresar gratitud y elogiar la iniciativa de Lourdes para dar comienzo a los proyectos de cada uno desde el primer mes de clase, cuando se nos pidieron las primeras entregas, necesitando idear un anteproyecto y un análisis para la base de datos para el mismo mes de octubre. Y aunque ese trabajo realizado entonces no muestra más que la idea del proyecto, ya que ha cambiado su planteamiento muchas veces a lo largo de los meses, cierto es que ha permitido comenzar el proyecto en abril a un ritmo de vértigo, teniéndola prácticamente finalizada a finales de mayo, resultado con el que quedo más que satisfecho.

A la hora de la programación, el modelo presentador (e interactor, como lo he implementado yo) me ha permitido realizar un avance excelentemente eficiente, ahorrándome suficiente tiempo como para no haber tenido que dedicar nada más que los fines de semana para el desarrollo de la aplicación, aunque esto ha sido también facilitado especialmente gracias a Firebase, que me ha ahorrado todo el trabajo necesario para el servidor, dándome un almacenamiento, una base de datos, autentificaciones y una API al momento de vincular la aplicación con el servidor de Google. Cierto es, que a diferencia de como hubiera sido con una base de datos SQL y en mi propio servidor, habría conseguido un proyecto más personal, con funciones mucho más específicas y útiles además de una estructura con claves ajenas, bastante mejor que la estructura JSON en la que se basa la aplicación, pero todo el tiempo ahorrado y la simplicidad que Firebase me ha ofrecido, ha sido considerada como una ventaja a tomar muy en cuenta por delante de una API personalizada.

Gracias al modelo presentador, la anticipación de Lourdes para el diseño del proyecto, y las ventajas de Firebase, la temporalización del proyecto se ha podido cumplir e incluso superar en horas de trabajo, aunque las distintas funciones no han sido desarrolladas en el mismo orden que esta refleja. El diseño de la base de datos ha sido una tarea tediosa, especialmente teniendo en cuenta la estructura que sigue Firebase, pero una vez concluída, el resto del proyecto ha ido cuesta abajo, desarrollando cada parte de la aplicación sin interrupciones, y sin tener que realizar muchos cambios que pudieran retrasar el desarrollo.

En cuanto al propio código, se ha seguido una estructuración absolutamente estricta, favoreciendo la limpieza, los posibles cambios y el mantenimiento a la hora de revisar el propio código en el futuro. La estructuración en cuanto a actividades y fragmentos ha sido detallada extensamente en borradores, para evitar siempre, en todo momento, cualquier tipo de problema que no tuviera una solución concisa y rápida, aunque los pequeños fallos son siempre inevitables. La refactorización de las clases se realiza al mismo tiempo que el desarrollo, dejando atrás un cñodigo perfectamente ordenado durante el propio avance, siguiendo la prioridad en cuanto a las posiciones de cada uno de los métodos que se muestra a continuación:

1. Constantes
2. Componentes de la interfaz
3. Instancias de objetos, Variables de tipos primitivos
4. Interfaces
5. onAttach, onCreate, onCreateView, onViewCreated, onActivityCreated
6. onStart, onResume
7. onPause, onStop
8. onCreateOptionsMenu, onCreateItemSelected
9. onDestroyView, onDestroy, onDetach
10. onActivityResult, onRequestPermissionsResult
11. Métodos Override de las interfaces implementadas, ++ordenados alfabéticamente++
12. Métodos de la propia clase, ++ordenados alfabéticamente++

En cuanto a las funcionalidades que quedan por implementar, que hubiera apreciado poder incluir, y que quedan en la lista de espera para el futuro, serían las siguientes:

- Apartado para gestionar las reservas de las zonas comunes de la comunidad, en caso de haberlas
- Gráficas e información de la eficiencia a la hora de resolver incidencias
- Creación de una API propia en un servidor dedicado
- Implementación de conversaciones privadas entre vecinos o de comunicación directa con el administrador
- Inicio de sesión mediante Google
- Inclusión de pequeños juegos simples para el entretenimiento

Mientras que otros apartados que se esperan a lo largo del futuro, bastante más importantes que las funcionalidades descritas anteriormente, serían:

- Traducción de todo el código de la aplicación de Java a **Kotlin**
- Versión para iOS en **Swift**
- Versión web mediante **JavaScript**

## Apéndice

Para concluir, agradecimientos y menciones especiales:

Profesores del centro y del ciclo formativo que han colaborado directamente en el aprendizaje y proporcionado los apuntes necesarios para la creación del proyecto final:

- **Lourdes Rodríguez**, **Eliseo Moreno**, **Sebastián Millán**, **Francisco García**, **Pablo Blanco**, **Guillermo Raya**, **José María Baez**, **Isabel Quirantes** y **Francisco Javier Moya**.

Tutores laborales en la empresa seleccionada para las prácticas:

- **Cristian Paciencia**, jefe del equipo de Movilidad y **Enrique Ríos**, como persona directamente asignada para mi seguimiento dentro de la empresa. Agradecimientos también por ser los impulsores del estudio de Firebase, cambiando por completo el rumbo de mi proyecto.

Autor del diseño original del logo de la aplicación y la traducción al catalán de la misma:

- **Oriol Monte I Gomez**, estudiante de Económicas en la Universidad de Girona.


## Bibliografía

A continuación se detallan las principales páginas web que han sido de utilidad a lo largo del desarrollo del proyecto integrado:

El mejor tutorial de toda la web para aprender Firebase en tan solo un par de horas, mientras desarrollas un chat en tiempo real:

- **Firebase in a Weekend (Android)** -> https://classroom.udacity.com/courses/ud0352

Página web de Fireabse, herramienta de alojamiento de la base de datos de la aplicación:

- **Firebase** -> https://firebase.google.com

Repositorio de código usado para alojar el proyecto:

- **Github** -> https://github.com/

Páginas web para consultas y tutoriales de funcionalidades concretas:

- **StackOverflow** -> http://stackoverflow.com/

- **SGOliver** -> http://www.sgoliver.net/

- **Hermosa Programación** -> http://www.hermosaprogramacion.com/

Sitio web del cual se han obtenido todos los iconos de la aplicación:

- **Material Design Icons** -> https://materialdesignicons.com/

Web para consultar los detalles de las licencias disponibles, donde se ha obtenido toda la información de las licencias Apache y MIT, presentes en las librerías utilizadas para la aplicación:

- **tl;drLegal** -> https://tldrlegal.com/

Repositorio concreto del cual han sido recopiladas la inmensa mayoría de las librerías utilizadas en el proyecto integrado:

- **Awesome Android UI** -> https://github.com/wasabeef/awesome-android-ui

Librería para la inyección de componentes de la interfaz:

- **Butter Knife** -> http://jakewharton.github.io/butterknife/

Librería para la carga de imágenes asíncrona desde una dirección web:

- **Glide** -> https://github.com/bumptech/glide