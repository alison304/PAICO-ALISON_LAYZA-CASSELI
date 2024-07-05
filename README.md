# 🏥 Clínica Odontológica PeruDent 🪥🦷

---

## Desarrolladores 👩‍⚕️🧑‍⚕️

- [Layza Casseli](https://github.com/CasseliLayza)
- [Paico Alison](https://github.com/alison304/odonto-p)

---

## Acerca de 🏥

El repositorio de la Clínica Odontológica PeruDent fue desarrollado con las siguientes tecnologias: El Frontend con Javascript, Backend con Java - Spring Boot para la materia de Backend de CTD-Digital House.

Se administra a los odontólogos ,pacientes y los turnos o citas.

---

### Vistas 🥼

Primer Dropdown _INICIO_, muestra:

- _Inicio Clínica Dental_ : Esta es la página de inicio, muestra la información general de la clínica.

Segundo Dropdown _LISTAS_, muestra:

- _Página de Listado de Odontólogos_: Muestra la lista de odontólogos registrados.
- _Página de Listado de Pacientes_: Muestra la lista de los pacientes registrados.
- _Página de Listado de Turnos_: Muestra la lista de los turnos registrados.

Tercer Dropdown _REGISTRAR_, muestra:

- _Página de Registrar Odontólogo_ : Muestra el formulario para poder registrar a un odontólogo.
- _Página de Registrar Odontólogo_ : Muestra el formulario para poder registrar a un paciente.

Cuarto Dropdown _RESERVAR_, muestra:

- _Página de Reservar Cita_ : Muestra el formulario de reserva de citas.

Quinto Dropdown _BUSCAR_, muestra:

- _Página de Buscar un Odontólogo_ : Permite buscar por Id a un odontólogo registrado.
- _Página de Buscar un Paciente_ : Permite buscar por Id a un paciente registrado.
- _Página de Buscar un Cita_ : Permite buscar por Id una cita registrada.

---

### Estructuración 🏥

- _Controller_ : Es la capa que maneja las solicitudes HTTP entrantes y las respuestas HTTP salientes.
- _DTO (Data Transfer Object)_ : Es un objeto que se utiliza para transferir datos entre diferentes capas de la aplicación.(no contienen lógica, solo llevan datos).
- _DTO Entrada_: Se usa para encapsular los datos que se reciben de una solicitud del cliente.
- _DTO Salida_: Se usa para encapsular los datos que se envían al cliente como respuesta.
- _Entity_ : Representa una tabla en la base de datos.
- _Exception_: Son clases que representan errores o situaciones excepcionales o personalizadas que ocurren durante la ejecución del programa.
- _Repository_: Es la capa que interactúa con la base de datos, manejan la persistencia de las entidades y proporcionan métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar).
- _Service_: Es la capa que contiene la lógica de la aplicación, interactúan con los repositorios para recuperar o persistir datos y con los controladores para proporcionar la funcionalidad necesaria.
- _Service.impl_: Es una subcapa dentro del servicio que contiene las implementaciones concretas de las interfaces de servicio, facilita la inyección de dependencias y el testeo.
- _Utils_: Son clases que contienen métodos utilitarios y funciones auxiliares que no pertenecen a ninguna capa en particular.

---

### Librerías usadas ⚕️

- Java: Es un lenguaje de programación multiplataforma,es un lenguaje de programación orientado a objetos.
- Java Spring Boot: Es una herramienta de código abierto que facilita el uso de marcos de trabajo basados ​​en Java para crear microservicios y aplicaciones web.
- Javascript: Es un lenguaje interpretado de programación orientado a objetos (POO).
- Maven: Es un administrador de compilación y dependencia, le permite crear su código y, al mismo tiempo, administrar sus dependencias para no tener que descargar archivos jar manualmente.
- H2: Es un sistema de gestión de bases de datos (DBMS, por sus siglas en inglés) de código abierto, escrito en Java. Es ligero, rápido y puede ejecutarse tanto en modo embebido como en modo servidor..
- SweetAlert2: Permite crear alertas personalizadas para enviar notificaciones al usuario.
- Bootstrap: Permite construir páginas web responsives de una forma más rápida y sencilla.
- Git/Github: Sistema de control de versiones utilizado para consolidar piezas de código, así como desarrollo ágil de software y puntos de recuperación del mismo.
