/*
Navicat MySQL Data Transfer

Source Server         : Local
Source Server Version : 100126
Source Host           : localhost:3306
Source Database       : EPSIdea

Target Server Type    : MYSQL
Target Server Version : 100126
File Encoding         : 65001

Date: 2018-03-20 11:37:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) COLLATE utf8_bin NOT NULL,
  `user` smallint(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user` (`user`),
  CONSTRAINT `document_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of document
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` text COLLATE utf8_bin NOT NULL,
  `document` int(4) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `document` (`document`),
  CONSTRAINT `message_document` FOREIGN KEY (`document`) REFERENCES `document` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `room` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `room` (`room`),
  CONSTRAINT `module_room` FOREIGN KEY (`room`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of module
-- ----------------------------

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `text` text COLLATE utf8_bin NOT NULL,
  `answers` text COLLATE utf8_bin NOT NULL,
  `valide_answer` tinyint(2) NOT NULL,
  `survey` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `survey` (`survey`),
  CONSTRAINT `FOREIGN KEY` FOREIGN KEY (`survey`) REFERENCES `survey` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of question
-- ----------------------------

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of room
-- ----------------------------

-- ----------------------------
-- Table structure for skills
-- ----------------------------
DROP TABLE IF EXISTS `skills`;
CREATE TABLE `skills` (
  `id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `survey` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `survey` (`survey`),
  CONSTRAINT `FROREIGN KEY` FOREIGN KEY (`survey`) REFERENCES `survey` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of skills
-- ----------------------------

-- ----------------------------
-- Table structure for survey
-- ----------------------------
DROP TABLE IF EXISTS `survey`;
CREATE TABLE `survey` (
  `id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `owner` text COLLATE utf8_bin NOT NULL,
  `required_point` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of survey
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` smallint(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `surname` varchar(50) COLLATE utf8_bin NOT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_bin NOT NULL,
  `rank` mediumint(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_module
-- ----------------------------
DROP TABLE IF EXISTS `user_module`;
CREATE TABLE `user_module` (
  `user` smallint(2) NOT NULL,
  `module` tinyint(2) NOT NULL,
  UNIQUE KEY `user` (`user`,`module`),
  KEY `user_module_module` (`module`),
  CONSTRAINT `user_module_module` FOREIGN KEY (`module`) REFERENCES `module` (`id`),
  CONSTRAINT `user_module_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_module
-- ----------------------------

-- ----------------------------
-- Table structure for user_skills
-- ----------------------------
DROP TABLE IF EXISTS `user_skills`;
CREATE TABLE `user_skills` (
  `user` smallint(2) NOT NULL,
  `validate` tinyint(1) NOT NULL,
  `level` tinyint(2) NOT NULL,
  `point` tinyint(2) NOT NULL,
  `skills` tinyint(2) NOT NULL,
  KEY `user` (`user`),
  KEY `skills` (`skills`),
  CONSTRAINT `user_skills_skills` FOREIGN KEY (`skills`) REFERENCES `skills` (`id`),
  CONSTRAINT `user_skills_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_skills
-- ----------------------------

-- ----------------------------
-- Table structure for user_survey
-- ----------------------------
DROP TABLE IF EXISTS `user_survey`;
CREATE TABLE `user_survey` (
  `survey` tinyint(2) NOT NULL,
  `user` smallint(2) NOT NULL,
  UNIQUE KEY `user` (`survey`),
  UNIQUE KEY `user_2` (`user`),
  CONSTRAINT `user_survey_survey` FOREIGN KEY (`survey`) REFERENCES `survey` (`id`),
  CONSTRAINT `user_survey_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_survey
-- ----------------------------
