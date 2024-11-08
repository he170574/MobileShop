-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mobileshop
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `ACCOUNT_ID` int NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `FULL_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `PHONE_NUMBER` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `DATE_OF_BIRTH` date DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `REGISTER_DATE` date NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `ROLE_ID` int DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT_ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`),
  KEY `ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'phong','$2a$10$Q1cjsct9VzccloEuk32ik.c7jOIgP7zY0V9WRJZ7kKwFk60NwK7da','hothephong1','hothephong2003@gmail.com','0978541603','2003-02-02','Hanoi','2024-10-18',0,1),(2,'dang','$2a$10$Q1cjsct9VzccloEuk32ik.c7jOIgP7zY0V9WRJZ7kKwFk60NwK7da','nguyen thi k1','x1nt@gmail.com','0987654321','1111-11-11',NULL,'2024-10-19',0,3),(5,'phong1','$2a$10$Q1cjsct9VzccloEuk32ik.c7jOIgP7zY0V9WRJZ7kKwFk60NwK7da','nguyen thi u2','phonghthe170574@fpt.edu.vn','1234567890','2000-02-02',NULL,'2024-10-23',0,2),(6,'k1','$2a$10$Q1cjsct9VzccloEuk32ik.c7jOIgP7zY0V9WRJZ7kKwFk60NwK7da','nguyen thi x1','ngnga020203@gmail.com','1098765432','2000-02-20',NULL,'2024-10-24',1,3);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditlog`
--

DROP TABLE IF EXISTS `auditlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditlog` (
  `AuditID` int NOT NULL AUTO_INCREMENT,
  `UserID` int DEFAULT NULL,
  `Action` varchar(255) DEFAULT NULL,
  `TableName` varchar(255) DEFAULT NULL,
  `ActionDate` datetime DEFAULT NULL,
  PRIMARY KEY (`AuditID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditlog`
--

LOCK TABLES `auditlog` WRITE;
/*!40000 ALTER TABLE `auditlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `CartID` bigint NOT NULL AUTO_INCREMENT,
  `AccountID` int NOT NULL,
  PRIMARY KEY (`CartID`),
  KEY `AccountID` (`AccountID`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`AccountID`) REFERENCES `account` (`ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `CartItemID` bigint NOT NULL AUTO_INCREMENT,
  `ProductID` int NOT NULL,
  `CartID` bigint NOT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`CartItemID`),
  KEY `ProductID` (`ProductID`),
  KEY `CartID` (`CartID`),
  CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`),
  CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`CartID`) REFERENCES `cart` (`CartID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderhistory`
--

DROP TABLE IF EXISTS `orderhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderhistory` (
  `OrderHistoryID` bigint NOT NULL AUTO_INCREMENT,
  `OrderID` bigint NOT NULL,
  `quantity` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `productAmount` double DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `Notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OrderHistoryID`),
  KEY `FK8oa946dcx8crkuwq3wi3vdkm` (`OrderID`),
  KEY `FK9ilva5oy9tab9wn9co8v0oryv` (`product_id`),
  CONSTRAINT `FK8oa946dcx8crkuwq3wi3vdkm` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`),
  CONSTRAINT `FK9ilva5oy9tab9wn9co8v0oryv` FOREIGN KEY (`product_id`) REFERENCES `product` (`ProductID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderhistory`
--

LOCK TABLES `orderhistory` WRITE;
/*!40000 ALTER TABLE `orderhistory` DISABLE KEYS */;
INSERT INTO `orderhistory` VALUES (1,2,2,4,'iphone 15',43798000,0,NULL),(2,2,1,6,'iphone 15 Pro',23670000,0,NULL),(3,3,1,4,'iphone 15',21899000,0,NULL),(4,3,1,5,'iphone 15 Plus',22500000,0,NULL),(5,4,1,4,'iphone 15',21899000,0,NULL),(6,5,4,4,'iphone 15',87596000,0,NULL),(7,5,5,5,'iphone 15 Plus',112500000,0,NULL),(8,5,2,6,'iphone 15 Pro',47340000,0,NULL),(9,5,1,3,'iphone 16 Pro Max ',45899000,0,NULL),(10,5,1,7,'iphone 15 Pro Max ',30499000,0,NULL),(11,5,1,1,'iphone 16 Pro Max 1TB',34999000,0,NULL),(12,6,1,7,'iphone 15 Pro Max ',30499000,0,NULL),(13,7,1,3,'iphone 16 Pro Max ',45899000,0,NULL),(14,7,1,7,'iphone 15 Pro Max ',30499000,0,NULL),(15,8,2,6,'iphone 15 Pro',47340000,0,NULL),(16,8,2,4,'iphone 15',43798000,0,NULL),(17,9,1,5,'iphone 15 Plus',22500000,0,NULL),(18,9,2,7,'iphone 15 Pro Max ',60998000,0,NULL),(19,10,1,6,'iphone 15 Pro',23670000,0,NULL),(20,10,1,5,'iphone 15 Plus',22500000,0,NULL),(21,11,2,6,'iphone 15 Pro',47340000,0,NULL),(22,11,2,5,'iphone 15 Plus',45000000,0,NULL),(23,12,2,6,'iphone 15 Pro',47340000,0,NULL),(24,12,2,5,'iphone 15 Plus',45000000,0,NULL),(25,13,2,6,'iphone 15 Pro',47340000,0,NULL),(26,13,2,5,'iphone 15 Plus',45000000,0,NULL),(27,14,1,6,'iphone 15 Pro',23670000,0,NULL),(28,14,1,7,'iphone 15 Pro Max ',30499000,0,NULL);
/*!40000 ALTER TABLE `orderhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `OrderID` bigint NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` int NOT NULL,
  `OrderDate` datetime DEFAULT NULL,
  `OrderStatus` varchar(50) DEFAULT NULL,
  `TotalAmount` double DEFAULT NULL,
  `ShippingFee` decimal(38,2) DEFAULT NULL,
  `ExpectedDeliveryTime` varchar(255) DEFAULT NULL,
  `OrderCode` varchar(255) DEFAULT NULL,
  `ShippingCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `FKiuxhrhr0yswrg6khrs1s1qeaj` (`ACCOUNT_ID`),
  CONSTRAINT `FKiuxhrhr0yswrg6khrs1s1qeaj` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'2024-11-06 13:33:22','INIT',27500,27500.00,'2024-11-08 23:59:59','HIR908','LDTL9L'),(2,1,'2024-11-06 13:34:27','WATING_PAYMENT',67495500,27500.00,'2024-11-08 23:59:59','HIR908','LDTL9L'),(3,1,'2024-11-08 01:59:41','WATING_PAYMENT',22000,22000.00,'2024-11-08 23:59:59','HIR908','LDTL9L'),(4,1,'2024-11-08 03:38:28','SUCCESS',22000,22000.00,'2024-11-08 23:59:59','OYQ528','LDTL9K'),(5,1,'2024-11-08 04:14:31','WATING_PAYMENT',58996,58996.00,'2024-11-08 23:59:59','IEB906','LDTL9W'),(6,1,'2024-11-08 07:07:30','SUCCESS',22000,22000.00,'2024-11-08 23:59:59','YVL469','LDTLT7'),(7,1,'2024-11-08 07:28:48','SUCCESS',22000,22000.00,'2024-11-08 23:59:59','ZAP085','LDTLTG'),(8,5,'2024-11-08 13:03:49','WATING_PAYMENT',22000,22000.00,'2024-11-08 23:59:59','HMG639','LDTGLG'),(9,2,'2024-11-08 13:05:06','WATING_PAYMENT',22000,22000.00,'2024-11-08 23:59:59','BPL836','LDTGLU'),(10,1,'2024-11-08 20:22:43','WATING_DELIVERY',22000,22000.00,'2024-11-09 23:59:59','ADQ517','LDTU88'),(11,1,'2024-11-08 20:27:09','WATING_DELIVERY',22000,22000.00,'2024-11-09 23:59:59','YFY470','LDTU8E'),(12,1,'2024-11-08 20:28:49','WATING_DELIVERY',22000,22000.00,'2024-11-09 23:59:59','HDF164','LDTUYU'),(13,1,'2024-11-08 20:40:34','WATING_PAYMENT',92362000,22000.00,'2024-11-09 23:59:59','WOM238','LDTUY4'),(14,1,'2024-11-08 20:45:31','WATING_PAYMENT',54191000,22000.00,'2024-11-09 23:59:59','ANA161','LDTUYH');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `postid` bigint NOT NULL AUTO_INCREMENT,
  `authorID` int NOT NULL,
  `brief_info` text,
  `category_post` varchar(255) DEFAULT NULL,
  `content` text,
  `counts` int NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `status_post` varchar(255) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `briefInfo` text,
  `categoryPost` varchar(255) DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `statusPost` bit(1) NOT NULL,
  PRIMARY KEY (`postid`),
  KEY `FKc54rn2ijwxq3tlnjaquu6s6ld` (`authorID`),
  CONSTRAINT `FKc54rn2ijwxq3tlnjaquu6s6ld` FOREIGN KEY (`authorID`) REFERENCES `account` (`ACCOUNT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `ProductID` int NOT NULL AUTO_INCREMENT,
  `ProductName` varchar(255) NOT NULL,
  `ProductDetails` varchar(255) DEFAULT NULL,
  `ProductImage` varchar(255) DEFAULT NULL,
  `CategoryName` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `StockQuantity` int DEFAULT NULL,
  `Deleted` bit(1) DEFAULT NULL,
  `Cost` double DEFAULT NULL,
  PRIMARY KEY (`ProductID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'iphone 16 Pro Max 1TB','Titan Đen 1','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp','samsung',34999000,10,_binary '\0',NULL),(2,'iphone 16 Pro Max 512GB','Titan Trắng','https://res.cloudinary.com/dvshotakm/image/upload/v1729530780/mrhafg0reyjqplyputty.webp','Apple',35999000,10,_binary '\0',NULL),(3,'iphone 16 Pro Max ','Titan Xám','https://res.cloudinary.com/dvshotakm/image/upload/v1728594867/lggz6ruxllph2hatqqzl.webp','Xiaomi',45899000,21,_binary '\0',NULL),(4,'iphone 15','512GB (Natural Titanium)','https://res.cloudinary.com/dvshotakm/image/upload/v1729530797/onlfg5eexbedxpcl2cng.jpg','Apple',21899000,25,_binary '\0',NULL),(5,'iphone 15 Plus','Hồng','https://res.cloudinary.com/dvshotakm/image/upload/v1729364509/vnnqztc04todqxodalya.jpg','samsung',22500000,12,_binary '\0',NULL),(6,'iphone 15 Pro','512GB (Natural Titanium)','https://res.cloudinary.com/dvshotakm/image/upload/v1729364574/d3wqg9mpd8lsueh8f5ia.jpg','Xiaomi',23670000,11,_binary '\0',NULL),(7,'iphone 15 Pro Max ','Titan xanh dương','https://res.cloudinary.com/dvshotakm/image/upload/v1729530821/l2bejiljpjwahredaltn.jpg','Apple',30499000,5,_binary '\0',NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productcategory`
--

DROP TABLE IF EXISTS `productcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productcategory` (
  `CategoryID` int NOT NULL AUTO_INCREMENT,
  `CategoryName` varchar(255) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`CategoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productcategory`
--

LOCK TABLES `productcategory` WRITE;
/*!40000 ALTER TABLE `productcategory` DISABLE KEYS */;
INSERT INTO `productcategory` VALUES (2,'Samsung',0),(3,'Xiaomi',0),(4,'Apple',0);
/*!40000 ALTER TABLE `productcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productcomment`
--

DROP TABLE IF EXISTS `productcomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productcomment` (
  `CommentID` int NOT NULL AUTO_INCREMENT,
  `ProductID` int DEFAULT NULL,
  `ACCOUNT_ID` int NOT NULL,
  `CommentText` varchar(255) DEFAULT NULL,
  `CommentDate` datetime DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL DEFAULT b'0',
  `Order_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`CommentID`),
  KEY `FK5fj4wapgt9g30u2ei576rqjtl` (`ACCOUNT_ID`),
  KEY `FK5yo1dgfy503y4s9v5djx62wx6` (`Order_ID`),
  KEY `FKctxpo65p3svqod0uvbjvotq4e` (`ProductID`),
  CONSTRAINT `FK5fj4wapgt9g30u2ei576rqjtl` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ACCOUNT_ID`),
  CONSTRAINT `FK5yo1dgfy503y4s9v5djx62wx6` FOREIGN KEY (`Order_ID`) REFERENCES `orders` (`OrderID`),
  CONSTRAINT `FKctxpo65p3svqod0uvbjvotq4e` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productcomment`
--

LOCK TABLES `productcomment` WRITE;
/*!40000 ALTER TABLE `productcomment` DISABLE KEYS */;
/*!40000 ALTER TABLE `productcomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productrating`
--

DROP TABLE IF EXISTS `productrating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productrating` (
  `RatingID` int NOT NULL AUTO_INCREMENT,
  `ProductID` int DEFAULT NULL,
  `ACCOUNT_ID` int NOT NULL,
  `RatingValue` int DEFAULT NULL,
  `RatingDate` datetime DEFAULT NULL,
  PRIMARY KEY (`RatingID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productrating`
--

LOCK TABLES `productrating` WRITE;
/*!40000 ALTER TABLE `productrating` DISABLE KEYS */;
/*!40000 ALTER TABLE `productrating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `ROLE_ID` int NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ROLE_ID`),
  UNIQUE KEY `ROLE_NAME` (`ROLE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN',0),(2,'ROLE_STAFF',0),(3,'ROLE_MEMBER',0),(4,'ROLE_SHIPPER',0);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-08 20:57:25
