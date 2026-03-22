/*
 Navicat Premium Dump SQL

 Source Server         : MySQL_DB_Connection
 Source Server Type    : MySQL
 Source Server Version : 80044 (8.0.44)
 Source Host           : localhost:3306
 Source Schema         : ids_giuseppe_de_marco_db

 Target Server Type    : MySQL
 Target Server Version : 80044 (8.0.44)
 File Encoding         : 65001

 Date: 22/03/2026 19:23:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for film
-- ----------------------------
DROP TABLE IF EXISTS `film`;
CREATE TABLE `film`  (
  `id` int NOT NULL,
  `titolo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id_regista` int NOT NULL,
  `anno_di_uscita` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `id_genere` int NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_genere`(`id_genere` ASC) USING BTREE,
  INDEX `FK_regista`(`id_regista` ASC) USING BTREE,
  CONSTRAINT `FK_genere` FOREIGN KEY (`id_genere`) REFERENCES `generi` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_regista` FOREIGN KEY (`id_regista`) REFERENCES `registi` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of film
-- ----------------------------
INSERT INTO `film` VALUES (4, 'Natale al DIMES', 3, '2026', 4, '2026-03-22 16:47:03', '2026-03-22 16:47:03', NULL);
INSERT INTO `film` VALUES (5, 'Interstellar', 4, '2014', 5, '2026-03-22 16:47:49', '2026-03-22 16:47:49', NULL);
INSERT INTO `film` VALUES (6, 'Tenet', 4, '2020', 6, '2026-03-22 16:58:52', '2026-03-22 16:58:52', NULL);

-- ----------------------------
-- Table structure for generi
-- ----------------------------
DROP TABLE IF EXISTS `generi`;
CREATE TABLE `generi`  (
  `id` int NOT NULL,
  `genere` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of generi
-- ----------------------------
INSERT INTO `generi` VALUES (1, 'gfa', '2026-03-21 16:22:14', '2026-03-21 16:22:14', NULL);
INSERT INTO `generi` VALUES (2, 'prova', '2026-03-21 16:23:37', '2026-03-21 16:23:37', NULL);
INSERT INTO `generi` VALUES (3, 'prova3', '2026-03-21 16:24:44', '2026-03-21 16:24:44', NULL);
INSERT INTO `generi` VALUES (4, 'Comico', '2026-03-22 16:47:03', '2026-03-22 16:47:03', NULL);
INSERT INTO `generi` VALUES (5, 'Fantascienza', '2026-03-22 16:47:49', '2026-03-22 16:47:49', NULL);
INSERT INTO `generi` VALUES (6, 'Sci-fi', '2026-03-22 16:58:52', '2026-03-22 16:58:52', NULL);

-- ----------------------------
-- Table structure for registi
-- ----------------------------
DROP TABLE IF EXISTS `registi`;
CREATE TABLE `registi`  (
  `id` int NOT NULL,
  `nome` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cognome` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of registi
-- ----------------------------
INSERT INTO `registi` VALUES (1, 'fdagf', 'agfagfa', '2026-03-21 16:22:14', '2026-03-21 16:22:14', NULL);
INSERT INTO `registi` VALUES (2, 'ciao', 'ciao', '2026-03-21 16:23:36', '2026-03-21 16:23:36', NULL);
INSERT INTO `registi` VALUES (3, 'Giuseppe', 'De Marco', '2026-03-22 16:47:03', '2026-03-22 16:47:03', NULL);
INSERT INTO `registi` VALUES (4, 'Christopher', 'Nolan', '2026-03-22 16:47:49', '2026-03-22 16:47:49', NULL);

-- ----------------------------
-- Table structure for stati
-- ----------------------------
DROP TABLE IF EXISTS `stati`;
CREATE TABLE `stati`  (
  `id` int NOT NULL,
  `id_film` int NOT NULL,
  `stato_visione` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_film_stati`(`id_film` ASC) USING BTREE,
  CONSTRAINT `FK_film_stati` FOREIGN KEY (`id_film`) REFERENCES `film` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stati
-- ----------------------------
INSERT INTO `stati` VALUES (4, 4, 'DA_VEDERE', '2026-03-22 16:47:03', '2026-03-22 16:47:03', NULL);
INSERT INTO `stati` VALUES (5, 5, 'VISTO', '2026-03-22 16:47:49', '2026-03-22 16:47:49', NULL);
INSERT INTO `stati` VALUES (6, 6, 'IN_VISIONE', '2026-03-22 16:58:52', '2026-03-22 16:58:52', NULL);

-- ----------------------------
-- Table structure for valutazioni_personali
-- ----------------------------
DROP TABLE IF EXISTS `valutazioni_personali`;
CREATE TABLE `valutazioni_personali`  (
  `id` int NOT NULL,
  `id_film` int NOT NULL,
  `valutazione_personale` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `deleted_at` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_film_valutazioni_personali`(`id_film` ASC) USING BTREE,
  CONSTRAINT `FK_film_valutazioni_personali` FOREIGN KEY (`id_film`) REFERENCES `film` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of valutazioni_personali
-- ----------------------------
INSERT INTO `valutazioni_personali` VALUES (4, 4, '5', '2026-03-22 16:47:02.5356277', '2026-03-22 16:47:03', NULL);
INSERT INTO `valutazioni_personali` VALUES (5, 5, '5', '2026-03-22 16:47:48.5527242', '2026-03-22 16:47:49', NULL);
INSERT INTO `valutazioni_personali` VALUES (6, 6, '5', '2026-03-22 16:58:51.6545199', '2026-03-22 16:58:52', NULL);

SET FOREIGN_KEY_CHECKS = 1;
