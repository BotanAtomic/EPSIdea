/*
Navicat MySQL Data Transfer

Source Server         : Local
Source Server Version : 100126
Source Host           : localhost:3306
Source Database       : EPSIdea

Target Server Type    : MYSQL
Target Server Version : 100126
File Encoding         : 65001

Date: 2018-03-23 11:24:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for documents
-- ----------------------------
DROP TABLE IF EXISTS `documents`;
CREATE TABLE `documents` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT '',
  `user` smallint(2) NOT NULL,
  `module` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user`) USING BTREE,
  CONSTRAINT `document_user` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of documents
-- ----------------------------
INSERT INTO `documents` VALUES ('2', 'text.pdf', 'lol', '1', '2');
INSERT INTO `documents` VALUES ('3', 'deleguespromo.jpg__952x396_q85_crop_subsampling-2_upscale.jpg', '', '8', '2');
INSERT INTO `documents` VALUES ('5', 'facture 1.pdf', '', '8', '2');
INSERT INTO `documents` VALUES ('6', 'Script.zip', '', '8', '2');
INSERT INTO `documents` VALUES ('7', 'Script.zip', '', '8', '2');
INSERT INTO `documents` VALUES ('8', 'CV.pdf', '', '8', '2');

-- ----------------------------
-- Table structure for messages
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` text COLLATE utf8_bin NOT NULL,
  `document` int(4) NOT NULL,
  `date` bigint(20) NOT NULL,
  `module` int(255) DEFAULT NULL,
  `owner` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `document` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of messages
-- ----------------------------

-- ----------------------------
-- Table structure for modules
-- ----------------------------
DROP TABLE IF EXISTS `modules`;
CREATE TABLE `modules` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `room` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of modules
-- ----------------------------
INSERT INTO `modules` VALUES ('1', 'Graphisme C++', '2');
INSERT INTO `modules` VALUES ('2', 'Algo C++', '2');
INSERT INTO `modules` VALUES ('3', 'Algo C', '3');
INSERT INTO `modules` VALUES ('4', 'JavaFX ', '1');

-- ----------------------------
-- Table structure for rooms
-- ----------------------------
DROP TABLE IF EXISTS `rooms`;
CREATE TABLE `rooms` (
  `id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of rooms
-- ----------------------------
INSERT INTO `rooms` VALUES ('1', 'Java', 'java.png', 'Java est un langage de programmation orienté objet créé par James Gosling et Patrick Naughton, employés de Sun Microsystems, avec le soutien de Bill Joy');
INSERT INTO `rooms` VALUES ('2', 'C++', 'c++.png', 'C++ est un langage de programmation compilé permettant la programmation sous de multiples paradigmes (comme la programmation procédurale, orientée objet ou générique). Ses bonnes performances, et sa compatibilité avec le C en font un des langages de progr');
INSERT INTO `rooms` VALUES ('3', 'C', 'c.jpg', 'C est un langage de programmation impératif et généraliste. Inventé au début des années 1970 pour réécrire UNIX, C est devenu un des langages les plus utilisés. De nombreux langages plus modernes comme C++, C#, Java et PHP reprennent des aspects de C.');
INSERT INTO `rooms` VALUES ('4', 'PHP', 'php.png', 'PHP: Hypertext Preprocessor4, plus connu sous son sigle PHP (acronyme récursif), est un langage de programmation libre5, principalement utilisé pour produire des pages Web dynamiques via un serveur HTTP4, mais pouvant également fonctionner comme n\'importe');
INSERT INTO `rooms` VALUES ('5', 'C#', 'sharp.png', 'C# est un langage de programmation orienté objet, commercialisé par Microsoft depuis 20022 et destiné à développer sur la plateforme Microsoft .NET.');

-- ----------------------------
-- Table structure for skills
-- ----------------------------
DROP TABLE IF EXISTS `skills`;
CREATE TABLE `skills` (
  `id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `survey` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of skills
-- ----------------------------
INSERT INTO `skills` VALUES ('1', 'Java', '0');
INSERT INTO `skills` VALUES ('2', 'C++', '0');
INSERT INTO `skills` VALUES ('3', 'C', '0');

-- ----------------------------
-- Table structure for surveys
-- ----------------------------
DROP TABLE IF EXISTS `surveys`;
CREATE TABLE `surveys` (
  `id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `owner` int(11) NOT NULL,
  `module` int(11) DEFAULT NULL,
  `question` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `answers` text COLLATE utf8_bin,
  `valid_answer` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of surveys
-- ----------------------------
INSERT INTO `surveys` VALUES ('1', 'Quiz C++', '1', '2', 'Aimez vous le C++ ?', 0x4F75693B4E6F6E3B426F663B5427657320717569203F, '2');
INSERT INTO `surveys` VALUES ('2', 'Quiz C++ Algo', '8', '2', 'Le C++ est-il parfait ?', 0x4F753B4E6F6E3B537572203F3B596573737373, '3');
INSERT INTO `surveys` VALUES ('3', 'Operateur', '7', '2', ' Choisissez l\'opérateur qui ne peut pas être surchargé.', 0x2F3B28293B3A3A25, '3');
INSERT INTO `surveys` VALUES ('4', 'Fichier en tête', '2', '2', 'Un fichier d\'en-tête défini par l\'utilisateur est inclus par l\'instruction suivante en général.', 0x23696E636C756465202266696C652E68223B23696E636C756465203C666963686965722E683E3B23696E636C756465203C666963686965723E3B23696E636C75646520666963686965722E68, '1');
INSERT INTO `surveys` VALUES ('5', 'Syntaxe', '7', '2', 'Peut - on declarer plusieurs fois une même variable ?', 0x4F75693B4E6F6E3B417665632063617373653B53616E732061766973, '2');
INSERT INTO `surveys` VALUES ('6', 'Pointeur', '1', '2', 'Nous pouvons utiliser ce pointeur dans la fonction membre statique de la classe.', 0x567261693B567261693B466175783B46617578, '1');

-- ----------------------------
-- Table structure for user_survey
-- ----------------------------
DROP TABLE IF EXISTS `user_survey`;
CREATE TABLE `user_survey` (
  `survey` tinyint(2) NOT NULL,
  `user` smallint(2) NOT NULL,
  `result` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_survey
-- ----------------------------
INSERT INTO `user_survey` VALUES ('1', '8', '1');
INSERT INTO `user_survey` VALUES ('2', '8', '0');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` smallint(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `surname` varchar(50) COLLATE utf8_bin NOT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `rank` mediumint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'Botan', 'Bobohihi', 'Ahmad', 'ahmed.botan94@gmail.com', '159357456botan', '4 rue Pierre de Ronsard', '0');
INSERT INTO `users` VALUES ('2', 'Libbrecht', 'Leuleu', 'Maxime', 'libbrecht.maxime97@gmail.com', '123456', '', '0');
INSERT INTO `users` VALUES ('7', 'FILIPE', 'YoYo', 'Yoan', 'yoanfilipe@yahoo.fr', 'azerty', '', '0');
INSERT INTO `users` VALUES ('8', 'masson', 'susu7496', 'sullivan', 'sullivan.masson1@epsi.fr', 'botan99', '', '0');
INSERT INTO `users` VALUES ('9', 'oinion', 'ikniokn', 'noiunio', 'ionio@gmail.com', '123456', '', '0');
