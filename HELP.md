# Postman - api
https://www.postman.com/research-observer-50009927/workspace/ecommerce-lucho

# Modo de uso
El proyecto se conecta a una base de datos mysql8 en el puerto localhost:33060. Para levantar correctamente el entorno utilizar el archivo docker/dev/docker-compose.yml
Una vez levantado el compose, se generan 2 servicios (33060 - mysql; 33070 - adminer)

Luego levantar la aplicación spring, esto correrá las migraciones encontradas con flyway. No es necesario crear la base de datos.

#Acceso a la aplicacion
http://localhost:9999

#Acceso a la documentacion OpenApi/Sweagger
http://localhost:9999/swagger-ui.html

