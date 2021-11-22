-- Adminer 4.8.1 MySQL 8.0.27 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `carrito_de_compra`;
CREATE TABLE `carrito_de_compra` (
  `tipo` varchar(31) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_creacion` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `carrito_de_compra_detalles_productos`;
CREATE TABLE `carrito_de_compra_detalles_productos` (
  `carrito_de_compra_id` bigint NOT NULL,
  `detalles_productos_id` bigint NOT NULL,
  UNIQUE KEY `UK_l79eovl69gffmb215o2lhqt0` (`detalles_productos_id`),
  KEY `FK6fnn2m8l6s28125ajyasexy40` (`carrito_de_compra_id`),
  CONSTRAINT `FK6fnn2m8l6s28125ajyasexy40` FOREIGN KEY (`carrito_de_compra_id`) REFERENCES `carrito_de_compra` (`id`),
  CONSTRAINT `FKdwtium1i64dg4gwq2h5acsial` FOREIGN KEY (`detalles_productos_id`) REFERENCES `detalle_producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `dni` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `compra`;
CREATE TABLE `compra` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `monto` double DEFAULT NULL,
  `monto_sin_descuentos` double DEFAULT NULL,
  `carrito_de_compra_id` bigint NOT NULL,
  `cliente_dni` bigint NOT NULL,
  `estado_orden_de_compra_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl80mj2sv1sedpmmt6qpadpunp` (`carrito_de_compra_id`),
  KEY `FK6suq2e2jifvxeodn4gpd2o5oo` (`cliente_dni`),
  KEY `FK2u2etn7khyc4ri5fkjq3aivxb` (`estado_orden_de_compra_id`),
  CONSTRAINT `FK2u2etn7khyc4ri5fkjq3aivxb` FOREIGN KEY (`estado_orden_de_compra_id`) REFERENCES `estado_compra` (`id`),
  CONSTRAINT `FK6suq2e2jifvxeodn4gpd2o5oo` FOREIGN KEY (`cliente_dni`) REFERENCES `cliente` (`dni`),
  CONSTRAINT `FKl80mj2sv1sedpmmt6qpadpunp` FOREIGN KEY (`carrito_de_compra_id`) REFERENCES `carrito_de_compra` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `compra_estados_anteriores`;
CREATE TABLE `compra_estados_anteriores` (
  `compra_id` bigint NOT NULL,
  `estados_anteriores_id` bigint NOT NULL,
  UNIQUE KEY `UK_r6gwyrlyjk2jrh8a410axq7a8` (`estados_anteriores_id`),
  KEY `FKgao8lgap48efarnbupdhc1f1x` (`compra_id`),
  CONSTRAINT `FKgao8lgap48efarnbupdhc1f1x` FOREIGN KEY (`compra_id`) REFERENCES `compra` (`id`),
  CONSTRAINT `FKl0rp7qq974xa93ioxgppb476t` FOREIGN KEY (`estados_anteriores_id`) REFERENCES `estado_compra` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `compra_promociones_aplicadas`;
CREATE TABLE `compra_promociones_aplicadas` (
  `compra_id` bigint NOT NULL,
  `promociones_aplicadas_id` bigint NOT NULL,
  UNIQUE KEY `UK_75w9llgvc9lsv8urrg5nns6ox` (`promociones_aplicadas_id`),
  KEY `FK720kxbhe7ky1ff1i0e5lyyo4e` (`compra_id`),
  CONSTRAINT `FK720kxbhe7ky1ff1i0e5lyyo4e` FOREIGN KEY (`compra_id`) REFERENCES `compra` (`id`),
  CONSTRAINT `FK7wj5ghgyqy08s8omgwc30rlqs` FOREIGN KEY (`promociones_aplicadas_id`) REFERENCES `promocion_aplicada` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `detalle_producto`;
CREATE TABLE `detalle_producto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `precio` double NOT NULL,
  `producto_nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK87nfcjp890osrakocp8q4bckr` (`producto_nombre`),
  CONSTRAINT `FK87nfcjp890osrakocp8q4bckr` FOREIGN KEY (`producto_nombre`) REFERENCES `producto` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `estado_compra`;
CREATE TABLE `estado_compra` (
  `tipo` varchar(31) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_fin` datetime(6) DEFAULT NULL,
  `fecha_inicio` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto` (
  `nombre` varchar(255) NOT NULL,
  `precio` double NOT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `promocion_aplicada`;
CREATE TABLE `promocion_aplicada` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descuento` double DEFAULT NULL,
  `promocion` varchar(255) DEFAULT NULL,
  `resumen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- 2021-11-22 22:03:26

DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
