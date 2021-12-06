# Motivación
Se implementa un backend para el servicio de un carrito de compras. Algo muy común que implementa cualquier supermercado o negocio que ofrece compras online.
Para esta aplicación existen dos tipos de carritos, el común y el especial.
El cliente puede realizar varias compras en un día y en simultáneo. Es decir puede gestionar más de un carrito abierto.
Cada producto, representado por un nombre único y un precio, puede administrarse (mediante spring-data-rest y su generación automática de controllers)
Cada producto tiene asociado un precio, pero al momento de realizarse la compra existe un detalle producto que congela el precio del producto en un instante de tiempo.

Este desarrollo representa la construcción de una Api, la cual estará brindando endpoints para 2 grandes puntos del mundo del eCommerce. Por un lado la gestión de un carrito de compras, y por otro la finalización de la misma. Además como extra la API permite administrar productos.

## Compras realizadas (GET)
El servicio deberá brindar el detalle de las compras realizadas por un  usuario en particular (identificado por dni). 
* Filtros: se podrá filtrar por un período (From-To). Si se le envía solo Fecha-From, el servicio deberá devolver todas las compras a partir de la fecha indicada. 
* Ordenamiento: El servicio podrá ser solicitado según 2 tipos de orden: fechas y montos. 

## Gestion de un carrito
* Crear un carrito (especial o común)
* Eliminar un carrito
* Agregar productos a un carrito
* Elminar productos de un carrito
* Consultar el estado de un carrito, con lo cual esta acción devolverá el total de productos que contiene
* Finalización de un carrito. Esta acción cierra el carrito (cambio de estado), a su vez, da un valor final al mismo (con las promociones aplicadas si es que aplican)

### Calcular valor de un carrito (Promociones)
Para calcular el valor de un carrito se deberá tener en cuenta los siguientes puntos a modo de beneficios que pueden disminuir el valor total de la compra.
* Si se compran más de 3 productos, se aplica un descuento de $100 para carritos comunes y de $150 para carritos especiales.
* Si el cliente en un determinado mes calendario, realizó compras por más de $5.000, pasa a ser  considerado VIP y por lo tanto, en sus próximas compras, se le aplicará un “descuento especial” de $500 pesos, solo si la compra supera los $2000 (acumulable con el descuento del punto 1). 
* Si el cliente “compra” 4 productos iguales, entonces se aplicará un descuento extra, realizando la promoción 4x3 en dicho producto. 

## Gestión de productos

Para la gestión de productos se utiliza un proyecto de spring => spring-data-rest, cuyo objetivo es la generación de apis rests para repositorios que administran nuestras entidades.
Para cada repositorio detectado, entonces se genera un controller que utiliza los verbos https (POST, GET, PUT, DELETE) y nos genera automáticamente una API restful para manipular directamente las entidades, sin desarrollo asociado.

En el caso de este backend, lo que hice fue limitar solamente al repositorio de productos esta funcionalidad, esto se logra anotando todos los repositorios que no queremos manejar con spring-data-rest con la siguiente anotación: @RestResource(exported = false)


# OpenApi/Sweagger

Para lograr un correcto uso de la api se procede a utilizar anotaciones OpenApi, y de esta manera generar un contrato claro de uso de todos los endpoints de intereés.

Para nuestro caso vamos a tener documentados los endpoints de gestión de carritos, de consulta de compras y además utilizando el proyecto de spring <b>springdoc-openapi-data-rest</b>, vamos a poder visualizar todos los endpoints para todos los controllers generados por el proyecto <b>spring-data-rest</b>, en nuestro caso concreto sería para la manipulación de productos.

* Acceso a la documentación de la api en la aplicación corriendo http://localhost:9999/swagger-ui.html


# Modo de uso
El proyecto se conecta a una base de datos mysql8 en el puerto localhost:33060. Para levantar correctamente el entorno utilizar el archivo docker/dev/docker-compose.yml
Una vez levantado el compose, se generan 2 servicios (33060 - mysql; 33070 - adminer)

Luego levantar la aplicación spring, esto correrá las migraciones encontradas con flyway. No es necesario crear la base de datos.


# Levantar las bases de dato y el adminer
Al utilizar Flyway contamos con que la primer migración V1.0__initial.sql genera todas las estructuras de datos contra la base de datos y sumado al jdbc url <b>jdbc:mysql://localhost:33060/ecommerce?createDatabaseIfNotExist=true</b> no es necesaria la generación de la base de datos ni correr ningún script manualmente

directorio_docker/dev$ docker-compose up 

- Database: ecommerce
- User:     root
- Password: root

Mysql service (docker) => 33060
Adminer service (docker) => 33070

(referencia de configuración en application-desa.yml y en docker/dev/docker-compose.yml)

# Levantar la aplicacion
mvn spring-boot:run

# Acceso a la aplicacion
http://localhost:9999

# Postman - Tests sobre la api
Para ir probando el backend se generó un proyecto postman, en el cual se fueron agregando ejemplos de uso para cada endpoint

- Acceso : https://www.postman.com/winter-meteor-750703/workspace/ecommerce-lucho




# Tecnologías
* Java 11
* Spring boot + spring
* Flyway
* Mysql
* Docker / Docker-compose (para base de datos mysql y adminer)
* JPA y JpaSpecification

