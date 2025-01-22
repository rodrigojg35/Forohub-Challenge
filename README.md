*******************************************
Bienvenidos a mi proyecto:
Forohun para el Challenge de Alura Latam Oracle Next One
*******************************************
```
Me llamo Rodrigo Jurado González y este es mi proyecto para el challenge de foro hub

## Descripción
Este proyecto es una aplicación web creada con las especificaciones dadas en el challenge de foro hub, realizada a partir de un solo controlador que mapea las rutas
principales del CRUD para topicos, asi como tambien un login.

A continuación explicaré los diferentes apartados de mi proyecto:

## 1. Estructura del proyecto
El proyecto está dividido en diferentes subpaquetes siguiendo uns estructura base MVC
- **Dto**: Modelos para un enfoque usando DTOs (data transfer object), y poder deserializar de una manera limpia
- **Controller**: Clase controladora principal para mapear las rutas que procesan las solicitudes al cliente
- **Model**: Contiene las clases que representan los objetos de la aplicación, mismos que se usarán de entidades para funcionar con Entity Manager al mapear la BD
- **Repository**: Contiene las interfaces que extienden de JpaRepository para la persistencia de los objetos
- **Service**: Contiene las clases que realizan la lógica detrás de cada opción en el menú
- **Security**: Contiene las clases de configuración de la seguridad de la aplicación (Spring Security)
- **Excepcion**: Contiene la lógica para cachar y desplegar información de los errores de manera estandarizada a nivel de la aplicación con códigos de error y mensajes de error
personalizables según el caso (decidí optar por esta lógica para que fuera mas sencillo poner errores específicos para cada caso)

## 2. Funcionalidades (endpoints)
La aplicación cuenta con las siguientes funcionalidades en base a las rutas:

POST /auth
No necesita token
Es el punto de entrada de la aplicación, en donde se puede realizar el login de usuario.
Para el login de usuario, se dispone de la base de datos una tabla usuario con el atributo id, nombre, email y contraseña como se muestran a continuación;

| ID | Nombre   | Email                | Contraseña (sin encriptar)        |
|----|----------|----------------------|-----------------------------------|
| 1  | Enrique  | enrique@hotmail.com  | passProyectoPruebaEnrique         |
| 2  | Juan     | juan@hotmail.com     | securedPasswordForTestsJuan       |
| 3  | Rodrigo  | ro.jg01@hotmail.com  | superpassRodrigo                  |

Aquí como se puede ver, coloco las contraseñas como tal para que se pueda probar el sistema, ya que dentro de la carpeta db > migration  de flyway, se encuentra el
script sql listo (V1 y V2) para la creación de las tablas y los usuarios se encontrarán ya disponibles con las contraseñas encriptadas, esto por practicidad ya que
no existe como tal un endpoint de registro de usuario. Así se verían en la BD al crearse la tabla (automatico con flyway)

| ID | Nombre   | Email                | Contraseña en la BD (encriptada con bcrypt)                        |
|----|----------|----------------------|--------------------------------------------------------------------|
| 1  | Enrique  | enrique@hotmail.com  | $2a$10$ATh4EiyMyufvTkR7kI8ikeVobrBro2wx6ms0zM/WQLZBTFlm7rani       |
| 2  | Juan     | juan@hotmail.com     | $2a$10$7vF58kNXx9cMLRJIjQZTk.rF0OfeDR1RHXs77ZcIc3AzZJTnMU2ey       |
| 3  | Rodrigo  | ro.jg01@hotmail.com  | $2a$10$KXk71VAn0DHUr/ok1dbLCubD4.8ICQsleh0CcvSwzUi7i5cJYWdjG       |


Para iniciar sesión se deberá usar el siguiente cuerpo de la solicitud:

{
    "email": string,
    "password": string   // CONTRASEÑA SIN ENCRIPTAR
}


Ejemplo respuesta: STATUS 200 OK
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbnJpcXVlQGhvdG1haWwuY29tIiwiaWF0IjoxNzM3N
              TI2MDEzLCJleHAiOjE3Mzc1Mjk2MTN9.geZOmL80DPCRxMDa6o4a4_FyLtcw8Em9G9rOIuhS6xc",
    "expiresIn": 3600000
}


GET /topicos
Requiere token
Devuelve los resultados de los tópicos que existan en la BD, por defecto se crean 2 tópicos pero conforme se creen más usando el endpoint de subida, aqui irán apareciendo.
Devuelve un Pageable customizado con el DTO PagedResponse para mostrar paginación simple y con los parámetros en español

Ejemplo respuesta:  STATUS 200 OK
{
    "contenido": [
        {
            "id": 1,
            "titulo": "Problema con el curso",
            "mensaje": "No puedo acceder al curso, profesor",
            "fechaCreacion": "2021-01-01",
            "autor": "Rodrigo",
            "curso": "Java"
        },
        ...
    ],
    "totalDePaginas": 1,
    "totalDeTopicos": 3
}


GET /topicos/{id}
Requiere token
Variable en el path ID 
Devuelve el tópico en específico que se busque por su ID. Notese que mientras que en el anterior siendo un endpoint general, devuelve los tópicos con estructura básica,
aquí se devuelve completo con objetos hijos

Ejemplo respuesta:  STATUS 200 OK
{
    "id": 1,
    "titulo": "Problema con el curso",
    "mensaje": "No puedo acceder al curso, profesor",
    "fechaCreacion": "2021-01-01",
    "autor": {
        "id": 3,
        "nombre": "Rodrigo",
        "email": "ro.jg01@hotmail.com"
    },
    "curso": "Java"
}

POST /topicos
Requiere token

Para crear un nuevo topico.

Cuerpo de solicitud:
{
    "idAutor": int,  // debe ser un id de usuario existente (arriba)
    "curso": string,
    "titulo": string,
    "mensaje": string
}

Ejemplo respuesta:  STATUS 201 CREATED
{
    "id": 3,
    "titulo": "El titulo es obligatorio",
    "mensaje": "El mensaje es obligatorio",
    "fechaCreacion": "2025-01-21",
    "autor": {
        "id": 2,
        "nombre": "Juan",
        "email": "juan@hotmail.com"
    },
    "curso": "El nombre del curso no debería estar vacío"
}

Aqui se devuelve un 201 pero ademas se devuelve el objeto creado en el cuerpo de la solicitud.
Igualmente en la cabecera de la respuesta Location  se incluye la URI del recurso generado: /topicos/{id_generado}


POST /topicos/{id}
Requiere token
Variable en el path ID

Para crear actualizar un topico por medio de su ID.

Cuerpo de solicitud:
{
    "idAutor": int,  // debe ser un id de usuario existente (arriba)
    "curso": string,
    "titulo": string,
    "mensaje": string
}

Aquí se mandan los atributos que se quieran editar, mandar el cuerpo vacío mandará un error solicitando almenos uno a editar. Igualmente se cuenta con las validaciones
correspondientes a la lógica de negocio en cuanto a no repetir mismo título y mensaje por tópico, validación de correo, etc.

Ejemplo respuesta:  STATUS 200 OK
{
    "id": 2,
    "titulo": "El titulo es obligatorio",
    "mensaje": "El mensaje es obligatorio2",
    "fechaCreacion": "2021-01-02",
    "autor": {
        "id": 2,
        "nombre": "Juan",
        "email": "juan@hotmail.com"
    },
    "curso": "El nombre del curso no debería estar vacío"
}

Devuelve el objeto tal y como quedó editado

DELETE /topicos/{id}
Requiere token
Variable en el path ID 

Elimina un tópico por medio de su ID

Ejemplo respuesta:  STATUS 204 NO content

El caso de éxito, por estandares REST, no se suele necesitar cuerpo de resultado mas que definir el éxico con respuesta vacía.

## 3. Consideraciones

// ERRORES

Los errores están cachados con la lógica dentro del paquete de exception como mencioné anteriormente.
Esto fue práctico ya que para errores globales, se manejan desde el GlobalExceptionHandler para cacharlos y devolver una respuesta estándar, como en el caso de los
Bad request y method not allowed (aunque spring security terminó modificandolo)

@ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Map<String, String> handleHttpRequestMethodNotSupportedException(org.springframework.web.HttpRequestMethodNotSupportedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "El método de solicitud no es compatible");
        return error;
    }

Sin embargo, tambien existe la clase customexception para definir una excepción lanzable personalizada que se instancia definiendo el mensaje y el código de status HTTP
para mandarlo a la clase padre (super) al implementar la interfaz RuntimeException (quiero pensar era la mas conveniente).

public class CustomException extends RuntimeException {

    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }


Esto con el fin de "cachar" estas mismas excepciones particulares con el CustomExceptionHandler y terminar devolviendolo como respuesta en formato estándar.

@ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, ex.getStatus());
    }

Con ello, dentro del código, al pasar por validaciones cada parte del proceso de la lógica de negocio en el service, se puede facilmente invocar una respuesta de error
al lanzarla con la clase misma para excepciones

if(!user.get().getPassword().equals(password)) {
            throw new
                    CustomException("La contraseña no es correcta", HttpStatus.UNAUTHORIZED);
}

Lo que arroja una respuesta de la siguiente manera:   STATUS 401 unauthorized
{
    "error": "Credenciales incorrectas"
}

// De lo último

En el caso de la entidad/tabla topico, el atributo status quiero pensar que era para el borrado lógico pero no vi esa parte entre los requisitos de la lógica de negocio
por loque dejé el atributo disponible y solamente que se autollene como true/1 (porque en la entidad es bool pero en la BD es tinyint)  pero aun asi quedaria ahi
por si mas adelante hiciese falta implementarse. Por motivos de tiempo no puse esa parte aunque hubiese estado interesante

Se que hay muchas cosas mas que faltan por explicar pero igualmente el cógico está explicado y comentado en la mayoría de partes para explicar la lógica que yo utilizé

Agradezco mucho la atención y retroalimentación para este proyecto, a quien guste.

Rodrigo Jurado.

