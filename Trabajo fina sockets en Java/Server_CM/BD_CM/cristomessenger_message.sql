-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cristomessenger
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
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id_user_orig` varchar(45) NOT NULL,
  `id_user_dest` varchar(45) NOT NULL,
  `datetime` timestamp NOT NULL,
  `read` int(1) DEFAULT NULL,
  `sent` int(1) DEFAULT NULL,
  `text` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id_user_orig`,`id_user_dest`,`datetime`),
  KEY `id_user_dest_idx` (`id_user_dest`),
  CONSTRAINT `id_user_dest_mes` FOREIGN KEY (`id_user_dest`) REFERENCES `user` (`id_user`),
  CONSTRAINT `id_user_orig_mes` FOREIGN KEY (`id_user_orig`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES ('@alexinio','@carrasquillo','2019-10-10 14:34:12',1,1,'you must leave the cafeine life bro..'),('@alexinio','@zizou','2019-12-13 10:05:13',0,1,'Hola jaime!'),('@charlowit','@madmatt','2019-10-12 20:30:20',1,1,'illooo que hase gay?'),('@charlowit','@madmatt','2019-10-12 20:30:25',1,1,'tuuu mamona'),('@charlowit','@madmatt','2019-10-12 20:32:35',1,1,'Programando cosas random'),('@elpedrillo69','@rasAS','2019-10-10 14:13:34',1,1,'want to hang out? im bored'),('@madmatt','@charlowit','2019-12-10 20:31:15',1,1,'que dice el tio aqui haciendo cosillas gays! tu que?'),('@nieto_off','@raulruid11','2019-10-10 12:13:35',1,1,'bro im on my way to your house, prepare me something sweet'),('@noelia34','@patri_b19','2019-10-10 12:20:34',0,0,'are you better?'),('@noelia34','@patri_b19','2019-10-10 16:34:46',0,0,'are you better?'),('@olivencia4','@nieto_off','2019-10-10 19:44:56',0,1,'why you dont want to play rocket league?'),('@olivencia4','@patri_b19','2019-10-10 12:34:14',0,1,'wasap, when you want to do the DI proyect??'),('@pablocassals','@olivencia4','2019-10-10 07:34:46',0,0,'chss wake up we are in class '),('@patri_b19','@nieto_off','2019-10-10 16:34:34',1,1,'you told me that you need help with PSP'),('@raulruid11','@pablocassals','2019-10-10 14:34:23',1,1,'this night sounds like party ya?'),('@zizou','@alexinio','2019-12-13 10:04:30',0,1,'Hola Alex!'),('@zizou','@elpedrillo69','2019-10-16 08:11:49',0,1,'Eres un golfo!!!');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-12  9:21:54
