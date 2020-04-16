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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id_user` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `surname1` varchar(45) DEFAULT NULL,
  `surname2` varchar(45) DEFAULT NULL,
  `photo` varchar(45) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('@alexinio','Alejandro','1234','Muñoz','Gutierrez','C:users/Desktop/misfotos/yopatinando',0),('@antoniocuvi','Antonio','1234','Guerrero','Carrillo','C:users/Desktop/misfotos/yodelegado',0),('@carrasquillo','Antonio','1234','Carrasco','Villegas','C:users/Desktop/misfotos/yoensannicolas',0),('@charlowit','Carlos','1234','Galvez','Payares','C:users/Desktop/misfotos/yocomiendoespetos',1),('@elpedrillo69','Pedro','1234','Garcia','Infantes','C:users/Desktop/misfotos/yoenlamae',0),('@fresilla','freson','1234','demonio','primo','eyeyey',0),('@golfo','Golfo','1234','Golfisimo','Raul','url',0),('@infante96','Pedro','1234','Garcia','Infante','',0),('@madmatt','Mateo','1234','Cabello','Rodriguez','C:users/Desktop/misfotos/yoconc#',0),('@magodeprog','Mateo','1234','El Gran Mago','De La Programacion','/home/mage/image.jpg',0),('@nieto_off','Alejandro','1234','Nieto','Alarcón','C:users/Desktop/misfotos/yoenlaplaya',0),('@noelia34','Noelia','1234','Hernandez','Rodriguez','C:users/Desktop/misfotos/yoaburrida',1),('@olivencia4','Antonio','1234','Olivencia','Lopez','C:users/Desktop/misfotos/yoenmitierra',0),('@pablocassals','Pabli','1234','Castro','Salazar','C:users/Desktop/misfotos/yoenlascruces',0),('@patri_b19','Patricia','1234','Burgos','Puerta','C:users/Desktop/misfotos/yoenmicasa',1),('@pedrillogolfo','Pedro','1234','Garcia','Infantes','C:users/Desktop/misfotos/yoenlamae',0),('@rasAS','Raúl','1234','Arenas','Senabre','C:users/Desktop/misfotos/yoenmicanarias',0),('@raulruid11','Raul','1234','Ruiz','Idañez','C:users/Desktop/misfotos/yodefiesta',0),('@zizou','Jaime','1234','Matas','Bustos',' ',0),('Bartolo','Bartolo','Bartolo','Muñoz','Lopez','',0),('granada','1','1','1','1','',0),('lolo','Lolazo','1234','Lalozo','Tralala',' ',0),('Pablo','Pablo','pablo','Castro','Salazar',NULL,0);
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

-- Dump completed on 2020-02-24 19:51:19
