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
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend` (
  `id_user_orig` varchar(45) NOT NULL,
  `id_user_dest` varchar(45) NOT NULL,
  `accept_request` int(1) DEFAULT NULL,
  PRIMARY KEY (`id_user_orig`,`id_user_dest`),
  KEY `id_user_dest_idx` (`id_user_dest`),
  CONSTRAINT `id_user_dest_friend` FOREIGN KEY (`id_user_dest`) REFERENCES `user` (`id_user`),
  CONSTRAINT `id_user_orig_friend` FOREIGN KEY (`id_user_orig`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
INSERT INTO `friend` VALUES ('@alexinio','@charlowit',1),('@alexinio','@zizou',1),('@antoniocuvi','@alexinio',1),('@antoniocuvi','@carrasquillo',1),('@antoniocuvi','@madmatt',1),('@antoniocuvi','@zizou',1),('@carrasquillo','@alexinio',1),('@carrasquillo','@nieto_off',1),('@carrasquillo','@zizou',1),('@charlowit','@alexinio',1),('@charlowit','@carrasquillo',1),('@charlowit','@madmatt',1),('@charlowit','@zizou',1),('@elpedrillo69','@madmatt',1),('@elpedrillo69','@patri_b19',1),('@elpedrillo69','@rasAS',1),('@elpedrillo69','@zizou',1),('@madmatt','@zizou',1),('@nieto_off','@pablocassals',1),('@nieto_off','@rasAS',1),('@nieto_off','@raulruid11',1),('@nieto_off','@zizou',1),('@noelia34','@charlowit',1),('@noelia34','@nieto_off',1),('@noelia34','@patri_b19',1),('@noelia34','@rasAS',1),('@noelia34','@raulruid11',1),('@noelia34','@zizou',1),('@olivencia4','@carrasquillo',1),('@olivencia4','@charlowit',1),('@olivencia4','@nieto_off',1),('@olivencia4','@pablocassals',1),('@pablocassals','@zizou',1),('@patri_b19','@antoniocuvi',1),('@patri_b19','@olivencia4',1),('@patri_b19','@raulruid11',1),('@patri_b19','@zizou',1),('@raulruid11','@elpedrillo69',1),('@raulruid11','@madmatt',1),('@raulruid11','@pablocassals',1),('@zizou','@alexinio',1),('@zizou','@antoniocuvi',1),('@zizou','@carrasquillo',1),('@zizou','@charlowit',1),('@zizou','@elpedrillo69',1),('@zizou','@madmatt',1),('@zizou','@nieto_off',1),('@zizou','@noelia34',1),('@zizou','@pablocassals',1),('@zizou','@patri_b19',1);
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-24 19:51:19
