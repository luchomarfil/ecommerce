#Motivación
Se implementa un backend para el servicio de un carrito de compras. Algo muy común que implementa cualquier supermercado o negocio que ofrece compras online.
Para esta aplicación existen dos tipos de carritos, el común y el especial.
El cliente puede realizar varias compras en un día y en simultáneo. Es decir puede gestionar más de un carrito abierto.
Cada producto puede administrarse (mediante spring-data-rest y su generación automática de controllers)
Cada producto tiene asociado un precio, pero al momento de realizarse la compra existe un detalle producto que congela el precio del producto en un instante de tiempo.

Este desarrollo representa la construcción de una Api, la cual estará brindando endpoints para 2 grandes puntos del mundo del eCommerce. Por un lado la gestión de un carrito de compras, y por otro la finalización de la misma. Además como extra la API permite administrar productos.

##Compras realizadas (GET)
El servicio deberá brindar el detalle de las compras realizadas por un  usuario en particular (identificado por dni). 
* Filtros: se podrá filtrar por un período (From-To). Si se le envía solo Fecha-From, el servicio deberá devolver todas las compras a partir de la fecha indicada. 
* Ordenamiento: El servicio podrá ser solicitado según 2 tipos de orden: fechas y montos. 

##Gestion de un carrito
* Crear un carrito (especial o común)
* Eliminar un carrito
* Agregar productos a un carrito
* Elminar productos de un carrito
* Consultar el estado de un carrito, con lo cual esta acción devolverá el total de productos que contiene
* Finalización de un carrito. Esta acción cierra el carrito (cambio de estado), a su vez, da un valor final al mismo (con las promociones aplicadas si es que aplican)

###Calcular valor de un carrito (Promociones)
Para calcular el valor de un carrito se deberá tener en cuenta los siguientes puntos a modo de beneficios que pueden disminuir el valor total de la compra.
* Si se compran más de 3 productos, se aplica un descuento de $100 para carritos comunes y de $150 para carritos especiales.
* Si el cliente en un determinado mes calendario, realizó compras por más de $5.000, pasa a ser  considerado VIP y por lo tanto, en sus próximas compras, se le aplicará un “descuento especial” de $500 pesos, solo si la compra supera los $2000 (acumulable con el descuento del punto 1). 
* Si el cliente “compra” 4 productos iguales, entonces se aplicará un descuento extra, realizando la promoción 4x3 en dicho producto. 





# Modo de uso
El proyecto se conecta a una base de datos mysql8 en el puerto localhost:33060. Para levantar correctamente el entorno utilizar el archivo docker/dev/docker-compose.yml
Una vez levantado el compose, se generan 2 servicios (33060 - mysql; 33070 - adminer)

Luego levantar la aplicación spring, esto correrá las migraciones encontradas con flyway. No es necesario crear la base de datos.


#Levantar las bases de dato y el adminer
directorio_docker/dev$ docker-compose up 

#Levantar la aplicacion
mvn spring-boot:run

#Acceso a la aplicacion
http://localhost:9999

#Acceso a la documentacion OpenApi/Sweagger
http://localhost:9999/swagger-ui.html


#Tecnologías
* Java 11
* Spring boot + spring
* Flyway
* Mysql
* Docker / Docker-compose (para base de datos mysql y adminer)
* JPA y JpaSpecification

