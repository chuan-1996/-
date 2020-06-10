-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: information
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `classs`
--

DROP TABLE IF EXISTS `classs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classs` (
  `class_name` varchar(44) NOT NULL,
  `school_name` varchar(45) NOT NULL,
  PRIMARY KEY (`class_name`),
  KEY `class_school_idx` (`school_name`),
  CONSTRAINT `class_school` FOREIGN KEY (`school_name`) REFERENCES `school` (`school_name`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classs`
--

LOCK TABLES `classs` WRITE;
/*!40000 ALTER TABLE `classs` DISABLE KEYS */;
INSERT INTO `classs` VALUES ('硕研软件工程2018-2','山东科技大学'),('管理员','山东科技大学');
/*!40000 ALTER TABLE `classs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `A` varchar(255) NOT NULL,
  `B` varchar(255) NOT NULL,
  `C` varchar(255) NOT NULL,
  `D` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `index` int(11) NOT NULL,
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `answer` varchar(20) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`question_id`),
  KEY `test_question_idx` (`index`),
  CONSTRAINT `test_question` FOREIGN KEY (`index`) REFERENCES `test` (`test_index`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES ('选项1','选项2','选项3','选项4','问题1',1,1,'1',1),('选项21','选项22','选项23','选项24','问题2',1,2,'2',1),('选项21','选项22','选项23','选项24','问题3',1,3,'3',1),('选项21','选项22','选项23','选项24','问题4ed',1,4,'123',2),('选项21','选项22','选项23','选项24','问题5',1,5,'12',2),('选项21','选项21','选项21','选项21','问题6',1,6,'1',3),('选项21','选项21','选项21','选项21','问题7',1,7,'2',3),('选项','选项','选项','选项','问题描述',4,42,'2',1),('选项','选项','选项','选项','问题描述',6,43,'1',3),('选项','选项','选项','选项','问题描述',6,44,'234',2),('选项','选项','选项','选项','问题描述6',6,45,'3',1),('选项','选项','选项','选项','问题描述6',6,46,'23',2),('选项','选项','选项','选项','问题描述123',4,47,'2',1),('选项','选项','选项','选项','问题描述',1,48,'23',2),('选项','选项','选项','选项','问题描述',7,49,'23',2),('选项','选项','选项','选项','问题描述',7,50,'1',3);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `school` (
  `school_name` varchar(45) NOT NULL,
  PRIMARY KEY (`school_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
INSERT INTO `school` VALUES ('山东科技大学');
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score` (
  `test_index` int(11) NOT NULL,
  `user_id` varchar(14) NOT NULL,
  `score` int(11) NOT NULL,
  `score_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`score_id`),
  KEY `test_user_idx` (`user_id`),
  CONSTRAINT `test_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` VALUES (1,'201883060064',3,12);
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `test_index` int(11) NOT NULL,
  `test_name` varchar(45) NOT NULL,
  PRIMARY KEY (`test_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'第一章'),(2,'第二章'),(3,'第三章'),(4,'章名000'),(6,'章名test2'),(7,'章名');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user` varchar(10) NOT NULL DEFAULT '未设置',
  `id` varchar(14) NOT NULL,
  `permission` int(11) NOT NULL DEFAULT '0',
  `password` varchar(40) NOT NULL DEFAULT '123456',
  `school` varchar(45) NOT NULL DEFAULT 'null',
  `class` varchar(45) NOT NULL DEFAULT 'null',
  PRIMARY KEY (`id`),
  KEY `user_class_idx` (`class`),
  CONSTRAINT `user_class` FOREIGN KEY (`class`) REFERENCES `classs` (`class_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','000000000000',3,'000000000000','山东科技大学','管理员'),('未设置','123123123',0,'123123123','山东科技大学','硕研软件工程2018-2'),('我1','123456',0,'1234564','山东科技大学','硕研软件工程2018-2'),('田川','201883060064',1,'123456','山东科技大学','硕研软件工程2018-2');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-03 22:22:21
