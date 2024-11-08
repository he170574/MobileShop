-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
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
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `FULL_NAME` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `PHONE_NUMBER` varchar(20) DEFAULT NULL,
  `DATE_OF_BIRTH` date DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `REGISTER_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
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
INSERT INTO `account` VALUES (1,'phong','$2a$12$EH13YGDVPUvQIiAEKl5kSuWsHUft.IrSgBikbeRL2nAjSHpDxrjvK','hothephong1','hothephong2003@gmail.com','0978541603','2000-02-02','Hanoi','2024-10-18 00:00:00',0,1),(2,'dang','$2a$10$0tKmjceR7DnjVQo2eGURROZfyZ7bANR9qu1MUKlUFQvDimjEipURy','nguyen thi k1','x1nt@gmail.com','0987654321','1111-11-11',NULL,'2024-10-19 00:00:00',0,3),(5,'phong1','$2a$10$8yR0jK8j1mW4tS4g8YfngufpUr3wNmlPeHqQjz5ZNWOCvhrXkL9Ym','nguyen thi u2','phonghthe170574@fpt.edu.vn','1234567890','2000-02-02',NULL,'2024-10-23 00:00:00',0,2),(6,'k1','$2a$10$zyDzhAxHy8pySbSzDWFP0.TgicXWBmKvuRXlqcly9JEypsKzigepu','nguyen thi x1','ngnga020203@gmail.com','1098765432','2000-02-20',NULL,'2024-10-24 00:00:00',1,3);
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
-- Table structure for table `blogcategory`
--

DROP TABLE IF EXISTS `blogcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blogcategory` (
  `CategoryID` int NOT NULL AUTO_INCREMENT,
  `CategoryName` varchar(255) NOT NULL,
  `Status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`CategoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blogcategory`
--

LOCK TABLES `blogcategory` WRITE;
/*!40000 ALTER TABLE `blogcategory` DISABLE KEYS */;
INSERT INTO `blogcategory` VALUES (1,'Tin công nghệ',1),(2,'Khám phá',1),(3,'Game',1),(4,'Long',1),(5,'Quyên',1),(6,'Hạnh',1),(7,'Toàn',1),(8,'LOng1',1);
/*!40000 ALTER TABLE `blogcategory` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `OrderHistoryID` int NOT NULL AUTO_INCREMENT,
  `OrderID` int DEFAULT NULL,
  `quantity` decimal(10,2) DEFAULT '0.00',
  `product_id` int DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `productAmount` decimal(10,2) DEFAULT '0.00',
  `cost` decimal(10,2) DEFAULT '0.00',
  `Notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OrderHistoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderhistory`
--

LOCK TABLES `orderhistory` WRITE;
/*!40000 ALTER TABLE `orderhistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` int NOT NULL,
  `OrderDate` datetime DEFAULT NULL,
  `OrderStatus` varchar(50) DEFAULT NULL,
  `TotalAmount` decimal(10,2) DEFAULT '0.00',
  `ShippingFee` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`OrderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `postID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text,
  `briefInfo` text,
  `thumbnail` varchar(255) DEFAULT NULL,
  `authorID` int NOT NULL,
  `createdDate` datetime DEFAULT NULL,
  `categoryPost` int DEFAULT NULL,
  `statusPost` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`postID`),
  KEY `authorID` (`authorID`),
  KEY `FK_Post_Category` (`categoryPost`),
  CONSTRAINT `FK_Post_Category` FOREIGN KEY (`categoryPost`) REFERENCES `blogcategory` (`CategoryID`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`authorID`) REFERENCES `account` (`ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',1,'2020-02-02 00:00:00',1,1),(9,'2','2','2','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',1,'2020-02-02 00:00:00',2,1),(10,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',2,'2020-02-02 00:00:00',3,1),(11,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',2,'2020-02-02 00:00:00',4,1),(12,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',5,'2020-02-02 00:00:00',2,1),(13,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',2,'2020-02-02 00:00:00',2,1),(14,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',6,'2020-02-02 00:00:00',4,1),(15,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',2,'2020-02-02 00:00:00',5,1),(17,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',1,'2020-02-02 00:00:00',2,1),(22,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',2,'2020-02-02 00:00:00',1,1),(23,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',2,'2020-02-02 00:00:00',3,1),(24,'long','long','long','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp',1,'2020-02-02 00:00:00',4,1),(25,'12131311aseadasdasd','<h2><strong>Bật, tắt chế độ máy bay</strong></h2><p>Bật/tắt chế độ máy bay trên là một cách hiệu quả để khắc phục lỗi iPhone không kết nối được WiFi. Khi bật chế độ này, mọi kết nối không dây như WiFi, Bluetooth, mạng di động sẽ bị ngắt, giúp hệ thống thiết lập lại các kết nối mạng. Việc tắt và bật lại chức năng này cũng giúp làm mới kết nối mạng WiFi. Đồng thời, giúp iPhone tìm kiếm và kết nối lại với WiFi từ đầu.</p><p>Đôi khi, iPhone sẽ gặp phải vấn đề về xung đột tín hiệu, khiến bị lỗi không thể kết nối WiFi hoặc mạng không hoạt động ổn định. Bằng cách bật chế độ máy bay, những xung đột này có thể được giải quyết, khôi phục khả năng kết nối ổn định. Đây là một cách không cần thao tác phức tạp nhưng vẫn rất hữu ích. Vì vậy, nó thường được nhiều người áp dụng khi gặp sự cố iPhone không bắt được WIFI.</p><figure class=\"image\"><img src=\"https://cdn-media.sforum.vn/storage/app/media/thanhhuyen/iPhone%20kh%C3%B4ng%20k%E1%BA%BFt%20n%E1%BB%91i%20%C4%91%C6%B0%E1%BB%A3c%20wifi/iphone-khong-ket-noi-duoc-wifi-1.jpg\" alt=\"Bật tắt chế độ máy bay sửa lỗi iPhone không kết nối được WiFi\"><figcaption>Giải pháp khắc phục lỗi kết nối WiFi đơn giản</figcaption></figure><p>Những chiếc smartphone đến từ nhà <a href=\"https://cellphones.com.vn/apple\">Apple</a> luôn gây được sức hút lớn. Chúng không chỉ sở hữu những tính năng riêng biệt mà còn là thiết bị cung cấp khả năng lướt web và tìm kiếm thông tin nhanh chóng. Vì vậy, mọi người đừng quên tham khảo bộ sưu tập iPhone đến từ <a href=\"https://cellphones.com.vn/\">CellphoneS</a> dưới đây để chọn cho mình sản phẩm phù hợp nhất!</p>','1ádasdasdasdasdasdasd','https://res.cloudinary.com/dvshotakm/image/upload/v1731010687/y5uvadw2cwpbrsbxiwrg.jpg',1,'2024-11-08 03:18:09',1,1),(26,'Apple123123','<h2><strong>Bật, tắt chế độ máy bay</strong></h2><p>Bật/tắt chế độ máy bay trên là một cách hiệu quả để khắc phục lỗi iPhone không kết nối được WiFi. Khi bật chế độ này, mọi kết nối không dây như WiFi, Bluetooth, mạng di động sẽ bị ngắt, giúp hệ thống thiết lập lại các kết nối mạng. Việc tắt và bật lại chức năng này cũng giúp làm mới kết nối mạng WiFi. Đồng thời, giúp iPhone tìm kiếm và kết nối lại với WiFi từ đầu.</p><p>Đôi khi, iPhone sẽ gặp phải vấn đề về xung đột tín hiệu, khiến bị lỗi không thể kết nối WiFi hoặc mạng không hoạt động ổn định. Bằng cách bật chế độ máy bay, những xung đột này có thể được giải quyết, khôi phục khả năng kết nối ổn định. Đây là một cách không cần thao tác phức tạp nhưng vẫn rất hữu ích. Vì vậy, nó thường được nhiều người áp dụng khi gặp sự cố iPhone không bắt được WIFI.</p><figure class=\"image\"><img src=\"https://cdn-media.sforum.vn/storage/app/media/thanhhuyen/iPhone%20kh%C3%B4ng%20k%E1%BA%BFt%20n%E1%BB%91i%20%C4%91%C6%B0%E1%BB%A3c%20wifi/iphone-khong-ket-noi-duoc-wifi-1.jpg\" alt=\"Bật tắt chế độ máy bay sửa lỗi iPhone không kết nối được WiFi\"><figcaption>Giải pháp khắc phục lỗi kết nối WiFi đơn giản</figcaption></figure><p>Những chiếc smartphone đến từ nhà <a href=\"https://cellphones.com.vn/apple\">Apple</a> luôn gây được sức hút lớn. Chúng không chỉ sở hữu những tính năng riêng biệt mà còn là thiết bị cung cấp khả năng lướt web và tìm kiếm thông tin nhanh chóng. Vì vậy, mọi người đừng quên tham khảo bộ sưu tập iPhone đến từ <a href=\"https://cellphones.com.vn/\">CellphoneS</a> dưới đây để chọn cho mình sản phẩm phù hợp nhất!</p>','12312312312312','https://res.cloudinary.com/dvshotakm/image/upload/v1731010869/wfkd68wt9aonvm10mygy.jpg',2,'2024-11-08 03:21:12',1,1),(27,'lọngksdfjhjsadhfjas','<p>ádfsdafnasndfjksadjf</p>','ádfasdfasdfsadfs','https://res.cloudinary.com/dvshotakm/image/upload/v1731011481/gxy3fyovyxhoyu200umd.jpg',5,'2024-11-08 03:31:24',1,1),(28,'qưewqeqweqweqw','<p>qưeqweqwewqeqweqw</p>','qưeqweqweqwewqeqw','https://res.cloudinary.com/dvshotakm/image/upload/v1731012534/hqyocbknj4npvxdo2tef.jpg',2,'2024-11-08 03:48:57',1,1),(29,'123123123123123','<p>12312312312312312</p>','12312321312312312','https://res.cloudinary.com/dvshotakm/image/upload/v1731012875/u9feozi13q2gqqkoplmn.jpg',1,'2024-11-08 03:54:38',2,1),(30,'123123123123123123','<p>123123123123123123123</p>','123123123123123123','https://res.cloudinary.com/dvshotakm/image/upload/v1731013148/ieyoiogb4w1apqudjoqz.webp',2,'2024-11-08 03:59:10',1,1),(31,'ưqerwerqwerweq','<p>ưqerqwerweqrwqe</p>','ưerwqerwqerqwer','https://res.cloudinary.com/dvshotakm/image/upload/v1731016219/cpaipl7ponqeuhlatzkw.jpg',2,'2024-11-08 04:50:21',2,1),(32,'qưeqweqweqweqw','<p>qưeqweqweqweqw</p>','qưeqweqweqwe','https://res.cloudinary.com/dvshotakm/image/upload/v1731019903/xyspt7qkbl3skwcb6qzo.jpg',1,'2024-11-08 05:51:45',1,1),(33,'123123123123123','<h2><strong>Bật, tắt chế độ máy bay</strong></h2><p>Bật/tắt chế độ máy bay trên là một cách hiệu quả để khắc phục lỗi iPhone không kết nối được WiFi. Khi bật chế độ này, mọi kết nối không dây như WiFi, Bluetooth, mạng di động sẽ bị ngắt, giúp hệ thống thiết lập lại các kết nối mạng. Việc tắt và bật lại chức năng này cũng giúp làm mới kết nối mạng WiFi. Đồng thời, giúp iPhone tìm kiếm và kết nối lại với WiFi từ đầu.</p><p>Đôi khi, iPhone sẽ gặp phải vấn đề về xung đột tín hiệu, khiến bị lỗi không thể kết nối WiFi hoặc mạng không hoạt động ổn định. Bằng cách bật chế độ máy bay, những xung đột này có thể được giải quyết, khôi phục khả năng kết nối ổn định. Đây là một cách không cần thao tác phức tạp nhưng vẫn rất hữu ích. Vì vậy, nó thường được nhiều người áp dụng khi gặp sự cố iPhone không bắt được WIFI.</p><figure class=\"image\"><img src=\"https://cdn-media.sforum.vn/storage/app/media/thanhhuyen/iPhone%20kh%C3%B4ng%20k%E1%BA%BFt%20n%E1%BB%91i%20%C4%91%C6%B0%E1%BB%A3c%20wifi/iphone-khong-ket-noi-duoc-wifi-1.jpg\" alt=\"Bật tắt chế độ máy bay sửa lỗi iPhone không kết nối được WiFi\"><figcaption>Giải pháp khắc phục lỗi kết nối WiFi đơn giản</figcaption></figure><p>Những chiếc smartphone đến từ nhà <a href=\"https://cellphones.com.vn/apple\">Apple</a> luôn gây được sức hút lớn. Chúng không chỉ sở hữu những tính năng riêng biệt mà còn là thiết bị cung cấp khả năng lướt web và tìm kiếm thông tin nhanh chóng. Vì vậy, mọi người đừng quên tham khảo bộ sưu tập iPhone đến từ <a href=\"https://cellphones.com.vn/\">CellphoneS</a> dưới đây để chọn cho mình sản phẩm phù hợp nhất!</p>','123122312312312','https://res.cloudinary.com/dvshotakm/image/upload/v1731019971/vh73btnrxpnoshs8nned.webp',1,'2024-11-08 05:52:54',8,1);
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
  `ProductDetails` text,
  `ProductImage` text,
  `CategoryName` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `StockQuantity` int DEFAULT NULL,
  `Deleted` bit(1) DEFAULT NULL,
  `Cost` bigint DEFAULT NULL,
  PRIMARY KEY (`ProductID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'iphone 16 Pro Max 1TB','Titan Đen 1','https://res.cloudinary.com/dvshotakm/image/upload/v1728594813/mflrimfyqklobs6bjnau.webp','samsung',34999000,10,_binary '\0',NULL),(2,'iphone 16 Pro Max 512GB','Titan Trắng','https://res.cloudinary.com/dvshotakm/image/upload/v1729530780/mrhafg0reyjqplyputty.webp','Apple',35999000,10,_binary '\0',NULL),(3,'iphone 16 Pro Max ','Titan Xám','https://res.cloudinary.com/dvshotakm/image/upload/v1728594867/lggz6ruxllph2hatqqzl.webp','Xiaomi',45899000,21,_binary '\0',NULL),(4,'iphone 15','512GB (Natural Titanium)','https://res.cloudinary.com/dvshotakm/image/upload/v1729530797/onlfg5eexbedxpcl2cng.jpg','Apple',21899000,25,_binary '\0',NULL),(5,'iphone 15 Plus','Hồng','https://res.cloudinary.com/dvshotakm/image/upload/v1729364509/vnnqztc04todqxodalya.jpg','samsung',22500000,12,_binary '\0',NULL),(6,'iphone 15 Pro','512GB (Natural Titanium)','https://res.cloudinary.com/dvshotakm/image/upload/v1729364574/d3wqg9mpd8lsueh8f5ia.jpg','Xiaomi',23670000,11,_binary '\0',NULL),(7,'iphone 15 Pro Max ','Titan xanh dương','https://res.cloudinary.com/dvshotakm/image/upload/v1729530821/l2bejiljpjwahredaltn.jpg','Apple',30499000,5,_binary '\0',NULL),(8,'Iphone','Ip','https://res.cloudinary.com/dvshotakm/image/upload/v1731003280/x3yc1vtltuz8m6tvjahi.jpg','Samsung',123,12,_binary '\0',213),(9,'Iphone1','Ip1','https://res.cloudinary.com/dvshotakm/image/upload/v1731005899/rdecsnxilg4mglh6fmip.jpg','Samsung',1,12,_binary '\0',1);
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
  `CategoryName` varchar(100) NOT NULL,
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
  `CommentText` text NOT NULL,
  `CommentDate` datetime DEFAULT NULL,
  PRIMARY KEY (`CommentID`)
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
  `ROLE_NAME` varchar(50) NOT NULL,
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

-- Dump completed on 2024-11-08 14:52:33
