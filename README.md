**Prueba técnica para java Backend Developer**

Enunciado de la prueba [aqui](https://github.com/leynerp/wasteconfig-microservice/blob/master/assets/Prueba%20T%C3%A9cnica%20Abril%202024.pdf "Title").

Con el objetivo de poder fundamentar las decisiones tomadas durante la implementación de la propuesta de solución se propone dividir la explicación de las acciones realizadas en las siguientes capas o elementos de la arquitectura.

![](assets/Aspose.Words.9b4f8f52-21fc-49a4-96df-26565c32e062.001.png)

*Fig1. Capas de la arquitectura*

1-Capa de DATOS.

Del argumento pasmado en la prueba técnica se infiere el modelo de la *Fig.2,* donde se evidencia lo siguiente

- Relación de 1..1 entre waste\_manager y waste\_manager\_address, esta última tabla se caracteriza como una entidad débil , lo que significa que no almacena ningún registro que no esté relacionado con la tabla padre, y una eliminación en la tabla padre elimina su registro. 

- Relación de 1..n y de n..1 entre waste\_manager y waste\_center\_autorization,. 
- Se utilizó un BD H2 en memoria como se establecía en las indicaciones

![](assets/Aspose.Words.9b4f8f52-21fc-49a4-96df-26565c32e062.002.png)

*Fig2. Modelo de datos*

2-Capa de DATOS.

En la capa de Acceso a Datos se establecieron las relaciones entre entidades según lo explicado en al modelo de datos anterior haciendo uso de las anotaciones correspondiente. Aquí importante señalar lo siguiente:

- Se cambió la forma de establecer el valor por defecto a la variable **isEnabled** esto se hace con el objetivo de que cuando se genera la BD el campo se genere también con un valor por defecto.

- Antes

  private Boolean isEnabled=true;

- Después 

@Column(columnDefinition = "boolean default true")

private Boolean isEnabled


- Se hizo uso del ciclo de vida de los eventos JPA para darle valor a las variables **createdDate** y **lastModifiedDate**.

- Se hizo uso de la anotación @Version (control optimista en entidades persistentes) para la variable **versión.**

3-Capa de SERVICIO.

En esta capa se implementaron los dos servicios solicitados y la relación de uso entre ellos, a continuación, algunos detalles de interés con respecto a las decisiones tomadas:

- Se hizo uso del patrón de diseño DTO, con el objetivo de enviar y recibir los datos, cumpliendo con el requerimiento de que *parte de los campos* 

  *utilizados para la creación del objeto no son de interés para un usuario final*. 

- Para el mapeo entre objetos de Entidades y DTO se utilizó la librería **mapstruct.**

- Según mi experiencia los campos como is\_enabled en la BD se utilizan para deshabilitar o habilitar una fila de una tabla ante un comportamiento determinado (Ejemplo en un sistema de RRHH cuando un trabajador causa baja de una Entidad, no se eliminan sus datos sino que pasa a un estado inactivo), es por eso que se implemento el método de **changeEnabled** en el servicio WasteManager. Generalmente cuando se crea la fila por primera vez si valor es verdadero, por eso el valor por defecto a true.

- Se implemento además el método de **deleteAddressFromWaste** con el objetivo de poder eliminar la dirección de un WasteManager.

- Se implementó el método **findAll** para recuperar los datos insertados , se le agregó paginación correspondiente.


3-Capa de CONTROLADORES.

En esta capa de implementó el Rescontroller para los endpoints de nuestra api, haciendo uso de los verbos HTTP en cada Caso. Se hizo uso de la anotación @Validated para habilitar la validación de los campos de los DTO.  

**Tratamiento de Errores.**

El tratamiento de errores se llevo a cabo por medio del control de excepciones usando **RestControllerAdvice** , capturándose  excepciones personalizadas y definidas por el sistema, además se usó de **BindingResult**, para la validación del valor del campo **nif.**  


**Arquitectura de Micro Servicio**

En la figura 3 se ilustra cómo se organizó la solución para cumplir con los objetivos especificados, se puede decir que:

- Se creó el MS Cloud-config-server para gestionar todas las configuraciones centralizada mente, integrándose a un repositorio público de GitHub.

- Un servidor Eureka para el registro y descubrimiento de los MS.

- El servicio Waste- Manager que responde a la lógica del negocio en este caso.

- Se configuró un Api-Gateway como Load Balancer y Enrutador de las peticiones HTTP. Debido a la importancia que reviste la seguridad en sistema de Software se implemento un filtro a este MS que valida un token de seguridad para todas las peticiones.

  **Aclarar que por cuestión de tiempo no se llegó a implementar el MS que implementa la seguridad, solo se ilustró como se considera que debía de implementar a futuro. Es por eso que actualmente el filtro del Api Gateway solo validad que exista el token en la petición.  

![](assets/Aspose.Words.9b4f8f52-21fc-49a4-96df-26565c32e062.003.png)
*Fig3.Arquitectura de MS*


**Despliegue de la aplicación**

Para poder ejecutar la aplicación y probar su funcionamiento se puede realizar dos acciones:

1. **Descargar el código del repositorio y ejecutar cada servicio en el siguiente orden**

1. Cloud config server
1. Eureka
1. Waste Manager
1. Api Gateway


1. **Descargar el código del repositorio la carpeta *deploy* y ejecutar el Docker-compose.**

- \*\*Antes de correr el archivo compose (compose-spring.yml) se debe de crear una carpeta target dentro de la carpeta DockerFile, dentro poner las compilaciones de cada uno de los microservicios, esto se hace ejecutando el comando ***mvn clean package***.

![](assets/Aspose.Words.cf8c3c9c-25ef-4660-b299-fb2ccf48451a.004.png)

\*\*Tener presente que la aplicación utiliza los siguientes puertos:

`    `*Cloud config server: 6060, Eureka: 8761, Waste Manager( No se especifica Puerto para poder levantar varias instancias ),   Api Gateway(9090)*

![](assets/Aspose.Words.cf8c3c9c-25ef-4660-b299-fb2ccf48451a.005.png)

**Concusiones**

Se considera que se cumplió con los objetivos propuestos la prueba técnica y se cumplió con el plazo establecido. 

**Recomendaciones**

Se recomienda:

- Realizar las pruebas correspondientes al códico, utilizando Junit y 

- Mockito.Implementar las acciones de seguridad mencionadas anteriormente.
