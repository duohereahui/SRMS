-- MySQL dump 10.13  Distrib 9.2.0, for macos15.2 (arm64)
--
-- Host: localhost    Database: scientificinformationsystem
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `department_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=724 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'1','1'),(2,'2','2'),(3,'3','3'),(4,'4','4'),(5,'5','5'),(6,'6','6'),(721,'名字','描述'),(723,'学院1','描述1');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab`
--

DROP TABLE IF EXISTS `lab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab` (
  `lab_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `location` varchar(100) DEFAULT NULL,
  `department_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`lab_id`),
  UNIQUE KEY `name` (`name`),
  KEY `lab_department_FK` (`department_id`),
  CONSTRAINT `lab_department_FK` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab`
--

LOCK TABLES `lab` WRITE;
/*!40000 ALTER TABLE `lab` DISABLE KEYS */;
INSERT INTO `lab` VALUES (11,'11','11','11',1),(12,'lab','lab','location',2),(13,'13','13','13',1),(15,'lab5','lab5','location',2),(16,'lab6','lab6','location',6),(17,'lab7','lab7','location',2),(20,'lab8','lab8','location',1);
/*!40000 ALTER TABLE `lab` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_members`
--

DROP TABLE IF EXISTS `project_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_members` (
  `project_id` bigint unsigned NOT NULL,
  `user_id` bigint unsigned NOT NULL,
  `role` enum('leader','participant') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `project_members_user_FK` (`user_id`),
  CONSTRAINT `project_members_projects_FK` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`),
  CONSTRAINT `project_members_user_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_members`
--

LOCK TABLES `project_members` WRITE;
/*!40000 ALTER TABLE `project_members` DISABLE KEYS */;
INSERT INTO `project_members` VALUES (1,1000004,'leader'),(1,1000007,'participant'),(1,1000008,'participant'),(2,1000006,'leader'),(2,1000009,'participant'),(2,1000010,'participant'),(15,1000008,'participant'),(16,1000004,'leader'),(16,1000008,'participant'),(16,1000009,'participant'),(17,1000006,'leader'),(17,1000008,'participant'),(17,1000009,'participant'),(21,1000006,'leader'),(21,1000009,'participant'),(21,1000010,'participant');
/*!40000 ALTER TABLE `project_members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects` (
  `project_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `description` text,
  `status` enum('draft','pending','approved','in_progress','completed') DEFAULT 'draft',
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `actual_end_time` date DEFAULT NULL,
  `budget` decimal(12,2) DEFAULT NULL,
  `actual_funds` decimal(12,2) DEFAULT NULL,
  `leader_id` bigint unsigned NOT NULL,
  `lab_id` bigint unsigned DEFAULT NULL,
  `department_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `projects_user_FK` (`leader_id`),
  KEY `projects_lab_FK` (`lab_id`),
  KEY `projects_department_FK` (`department_id`),
  CONSTRAINT `projects_department_FK` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `projects_lab_FK` FOREIGN KEY (`lab_id`) REFERENCES `lab` (`lab_id`),
  CONSTRAINT `projects_user_FK` FOREIGN KEY (`leader_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,'project1',NULL,'draft',NULL,NULL,NULL,10.00,19999.00,1000004,11,1),(2,'project2',NULL,'draft',NULL,NULL,NULL,10.00,20000.00,1000006,12,2),(3,'project001','','draft',NULL,NULL,NULL,NULL,NULL,1000004,NULL,NULL),(6,'project002','','draft','2025-05-08',NULL,NULL,0.00,NULL,1000004,NULL,NULL),(7,'project002','','draft','2025-05-08','2025-05-09',NULL,0.00,NULL,1000004,NULL,NULL),(8,'project004','','draft','2025-05-08','2025-05-09',NULL,0.00,NULL,1000004,NULL,NULL),(9,'project009','','draft','2025-05-08','2025-05-09',NULL,0.00,NULL,1000004,NULL,NULL),(10,'project009','','draft','2025-05-08','2025-05-09',NULL,150000.00,NULL,1000006,12,2),(12,'project009','','draft','2025-05-08','2025-05-09',NULL,150000.00,NULL,1000006,12,2),(13,'项目标题','项目描述','draft','2025-01-01','2025-12-31',NULL,100000.00,NULL,1000004,11,1),(14,'项目标题','项目描述','draft','2025-01-01','2025-12-31',NULL,100000.00,NULL,1000004,11,1),(15,'项目标题','项目描述','draft','2025-01-01','2025-12-31',NULL,100000.00,NULL,1000004,11,1),(16,'项目标题1',NULL,'draft','2025-01-01','2025-12-31',NULL,100000.00,NULL,1000004,11,1),(17,'项目标题2',NULL,'draft','2025-01-01','2025-12-31',NULL,100000.00,NULL,1000006,12,2),(18,'项目标题3',NULL,'draft','2025-01-01','2025-12-31',NULL,100000.00,NULL,1000006,11,1),(19,'项目标题3',NULL,'draft','2025-01-01','2025-12-31',NULL,100000.00,NULL,1000006,12,2),(20,'项目标题3',NULL,'draft','2025-01-01','2025-12-31',NULL,100000.00,NULL,1000006,12,2),(21,'项目标题3',NULL,'draft','2025-01-01','2025-12-31',NULL,100000.00,NULL,1000006,12,2);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `research_authors`
--

DROP TABLE IF EXISTS `research_authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `research_authors` (
  `research_id` bigint unsigned DEFAULT NULL,
  `author_id` bigint unsigned DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  KEY `research_authors_research_outputs_FK` (`research_id`),
  KEY `research_authors_user_FK` (`author_id`),
  CONSTRAINT `research_authors_research_outputs_FK` FOREIGN KEY (`research_id`) REFERENCES `research_outputs` (`research_id`),
  CONSTRAINT `research_authors_user_FK` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `research_authors`
--

LOCK TABLES `research_authors` WRITE;
/*!40000 ALTER TABLE `research_authors` DISABLE KEYS */;
INSERT INTO `research_authors` VALUES (3,1000004,NULL),(3,1000007,NULL),(3,1000008,NULL),(4,1000006,NULL),(4,1000009,NULL),(4,1000010,NULL),(15,1000004,NULL),(15,1000009,NULL),(15,1000010,NULL),(21,1000004,NULL),(21,1000009,NULL),(21,1000010,NULL);
/*!40000 ALTER TABLE `research_authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `research_outputs`
--

DROP TABLE IF EXISTS `research_outputs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `research_outputs` (
  `research_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `type` enum('paper','patent') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  `status` enum('draft','submitted','published') DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `description` text,
  `journal_name` varchar(100) DEFAULT NULL,
  `paper_type` enum('JA','CA') DEFAULT NULL,
  `impact_factor` int DEFAULT NULL,
  `doi` varchar(100) DEFAULT NULL,
  `patent_number` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`research_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `research_outputs`
--

LOCK TABLES `research_outputs` WRITE;
/*!40000 ALTER TABLE `research_outputs` DISABLE KEYS */;
INSERT INTO `research_outputs` VALUES (3,'pptitle1','paper',NULL,'draft',NULL,NULL,'jnname','JA',NULL,NULL,NULL),(4,'pptitle2','paper',NULL,'submitted',NULL,NULL,'jnname','JA',NULL,NULL,NULL),(7,'深度学习在自然语言处理中的应用1','paper','2023-03-15','draft','/uploads/papers/2023/dl-nlp.pdf','探讨深度学习在NLP领域的最新进展','AI研究学报','CA',5,'10.1234/ai.2023.12345',NULL),(8,'智能图像识别装置2','patent','2023-08-01','submitted','/uploads/patents/2023/image-recognition.pdf','基于卷积神经网络的图像识别设备专利',NULL,NULL,NULL,NULL,'CN202310123456.7'),(9,'深度学习在自然语言处理中的应用2','paper','2023-03-15','draft','/uploads/papers/2023/dl-nlp.pdf','探讨深度学习在NLP领域的最新进展','AI研究学报','CA',5,'10.1234/ai.2023.12345',NULL),(15,'深度学习在自然语言处理中的应用3','paper','2023-03-15','draft','/uploads/papers/2023/dl-nlp.pdf','探讨深度学习在NLP领域的最新进展','AI研究学报','CA',5,'10.1234/ai.2023.12345',NULL),(21,'深度学习在自然语言处理中的应用4','paper','2023-03-15','draft','/uploads/papers/2023/dl-nlp.pdf','探讨深度学习在NLP领域的最新进展','AI研究学报','CA',5,'10.1234/ai.2023.12345',NULL);
/*!40000 ALTER TABLE `research_outputs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource` (
  `resource_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `type` enum('equipment','consumables','software') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` enum('available','in_use','maintenance','scrapped') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `lab_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`resource_id`),
  KEY `resource_lab_FK` (`lab_id`),
  CONSTRAINT `resource_lab_FK` FOREIGN KEY (`lab_id`) REFERENCES `lab` (`lab_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (1,'re1','everywhere','equipment','available',NULL,11),(2,'re2',NULL,'consumables','available',NULL,12),(3,'re3',NULL,'software','available',NULL,11),(4,'re4',NULL,'consumables','available',NULL,11),(5,'re5',NULL,'equipment','available',NULL,12),(6,'re6',NULL,'software','in_use',NULL,12),(7,'equip1','null','equipment','available','2025-05-07',11),(8,'equip2','null','equipment','available','2025-05-07',11);
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` enum('admin','teacher','student') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `department_id` bigint unsigned DEFAULT NULL,
  `lab_id` bigint unsigned DEFAULT NULL,
  `teacher_id` bigint unsigned DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `user_user_FK` (`teacher_id`),
  KEY `user_department_FK_1` (`department_id`),
  KEY `user_lab_FK` (`lab_id`),
  CONSTRAINT `user_department_FK` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `user_lab_FK` FOREIGN KEY (`lab_id`) REFERENCES `lab` (`lab_id`),
  CONSTRAINT `user_user_FK` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000014 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1000000,'18703671482','379268262@qq.com','379268262','chxht0393','admin','2025-04-22 23:49:12',NULL,NULL,NULL,NULL),(1000001,'18703671482','18703671482@qq.com','18703671482','chxht0393','admin','2025-04-27 17:47:07',NULL,NULL,NULL,NULL),(1000002,'12345676789','1111111111@qq.com','ciallo1234','a123123','admin','2025-04-27 18:35:03',NULL,NULL,NULL,NULL),(1000004,'12345676789','teacher@teacher.com','teacher001','teacher','teacher','2025-04-27 20:41:56',1,11,NULL,NULL),(1000006,'12345676780','teacher2@teacher.com','teacher002','teacher','teacher','2025-04-29 19:38:00',2,12,NULL,NULL),(1000007,'12341234123','stu1@stu.com','stu1','123456','student','2025-04-29 19:44:57',1,11,1000004,NULL),(1000008,'89089088908','stu2@stu.com','stu2','123456','student','2025-04-29 19:45:43',1,11,1000004,NULL),(1000009,'12344567123','stu3@stu.com','stu3','123456','student','2025-04-29 20:29:14',2,12,1000006,NULL),(1000010,'89089088906','stu4@stu.com','stu4','123456','student','2025-04-29 22:08:15',2,12,1000006,NULL),(1000011,'19089088906','stu5@stu.com','stu5','123456','student','2025-05-08 18:55:48',2,12,1000006,NULL),(1000012,'12089088906','stu6@stu.com','stu6','123456','student','2025-05-08 19:02:51',2,12,1000006,NULL),(1000013,'17089088906','stu7@stu.com','stu7','123456','student','2025-05-08 19:08:55',1,11,1000004,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'scientificinformationsystem'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-09 22:07:42
