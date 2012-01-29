# HeidiSQL Dump 
#
# --------------------------------------------------------
# Host:                 127.0.0.1
# Database:             optiphone
# Server version:       5.0.20a-community
# Server OS:            Win32
# Target-Compatibility: Standard ANSI SQL
# HeidiSQL version:     3.2 Revision: 1129
# --------------------------------------------------------

/*!40100 SET CHARACTER SET latin1;*/
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ANSI';*/
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;*/


#
# Database structure for database 'optiphone'
#

CREATE DATABASE /*!32312 IF NOT EXISTS*/ "optiphone" /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_bin */;

USE "optiphone";


#
# Table structure for table 'cow'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "cow" (
  "cowid" int(10) unsigned NOT NULL auto_increment,
  "mid" int(3) unsigned default NULL,
  "username" char(50) default NULL,
  PRIMARY KEY  ("cowid")
) /*!40100 DEFAULT CHARSET=latin1 COLLATE=latin1_bin*/;



#
# Dumping data for table 'cow'
#

LOCK TABLES "cow" WRITE;
/*!40000 ALTER TABLE "cow" DISABLE KEYS;*/
REPLACE INTO "cow" ("cowid", "mid", "username") VALUES
	('901','5955','张佩瑜');
REPLACE INTO "cow" ("cowid", "mid", "username") VALUES
	('902','5956','赵年峰');
/*!40000 ALTER TABLE "cow" ENABLE KEYS;*/
UNLOCK TABLES;


#
# Table structure for table 'mtab'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "mtab" (
  "mid" int(10) unsigned NOT NULL auto_increment,
  "mname" varchar(1000) NOT NULL,
  PRIMARY KEY  ("mid")
) /*!40100 DEFAULT CHARSET=latin1 COLLATE=latin1_bin*/;



#
# Dumping data for table 'mtab'
#

LOCK TABLES "mtab" WRITE;
/*!40000 ALTER TABLE "mtab" DISABLE KEYS;*/
REPLACE INTO "mtab" ("mid", "mname") VALUES
	('1','机器1');
REPLACE INTO "mtab" ("mid", "mname") VALUES
	('3','23232');
REPLACE INTO "mtab" ("mid", "mname") VALUES
	('4','234234');
/*!40000 ALTER TABLE "mtab" ENABLE KEYS;*/
UNLOCK TABLES;


#
# Table structure for table 'user'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "user" (
  "userid" int(10) unsigned NOT NULL auto_increment,
  "username" char(50) NOT NULL,
  "email" char(50) NOT NULL,
  "pass" char(50) NOT NULL,
  "cowid" int(10) unsigned NOT NULL,
  "paperid" char(50) NOT NULL,
  PRIMARY KEY  ("userid")
) /*!40100 DEFAULT CHARSET=latin1 COLLATE=latin1_bin*/;



#
# Dumping data for table 'user'
#

LOCK TABLES "user" WRITE;
/*!40000 ALTER TABLE "user" DISABLE KEYS;*/
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('1','王涛','wt0427a@126.com','1111111','901','341122198904270433');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('5','高丽娜','cg0227a@163.com','168168','901','340323199002271315');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('2','高丽娜','zw1003a@163.com','168168','901','342221198610030039');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('6','高丽娜','zdy0701a@163.com','168168','901','320684198007015936');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('3','高丽娜','xx0227a@163.com','168168','901','421087199102273234');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('4','高丽娜','zj0110a@163.com','168168','901','342921199101104813');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('7','高丽娜','zj0525a@163.com','168168','901','511024198805251738');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('8','高丽娜','cxz0125a@163.com','168168','901','622102199101257013');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('9','高丽娜','lgq1023a@163.com','168168','901','320981198910234032');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('10','高丽娜','sjq1205a@163.com','168168','901','310113199312051722');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('11','高丽娜','cyj0202a@163.com','168168','901','340406198902020874');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('12','高丽娜','pzp1001a@163.com','168168','901','320684198610017864');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('13','高丽娜','lbt0701a@163.com','168168','901','320684197807017732');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('14','高丽娜','xqy0814a@163.com','168168','901','341123198908145432');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('15','高丽娜','lxy0609a@163.com','168168','901','420982198706090094');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('16','高丽娜','zff1208a@163.com','168168','901','342623198912087924');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('17','高丽娜','zj0311a@163.com','168168','901','342422198703113615');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('18','高丽娜','zch1108a@163.com','168168','901','330327199111087270');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('19','高丽娜','cc1009a@163.com','168168','901','620502198310092382');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('20','高丽娜','xw1024a@163.com','168168','901','362233198510240054');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('21','高丽娜','wcy0913a@163.com','168168','901','532128199009133116');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('22','高丽娜','lhl0823a@163.com','168168','901','321322198808234819');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('23','高丽娜','mx0522a@163.com','168168','901','610502198805226235');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('24','高丽娜','lpj0122a@163.com','168168','901','460102199101220016');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('25','高丽娜','zql0617a@163.com','168168','901','410221198506173036');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('26','高丽娜','cl1016a@163.com','168168','901','310225199310161638');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('27','高丽娜','zy1021a@163.com','168168','901','341224198710211388');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('28','高丽娜','lzx0714a@163.com','168168','901','310102198607142855');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('29','高丽娜','cl0216a@163.com','168168','901','652901198702165235');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('30','高丽娜','zwb0226a@163.com','168168','901','152104199002260612');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('31','高丽娜','lxf1007a@163.com','168168','901','321322198510074832');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('32','高丽娜','wyp0408a@163.com','168168','901','410728199204086275');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('33','高丽娜','zwf0205a@163.com','168168','901','421181199302053110');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('34','高丽娜','sr1003a@163.com','168168','901','622322199110033421');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('35','高丽娜','zyf1024a@163.com','168168','901','410481198910244016');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('36','高丽娜','fj0207a@163.com','168168','901','530323198702070021');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('37','高丽娜','zy1105a@163.com','168168','901','330184198911055527');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('38','高丽娜','wx0106a@163.com','168168','901','320684198501063490');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('39','高丽娜','rmh0410a@163.com','168168','901','320684198504109685');
REPLACE INTO "user" ("userid", "username", "email", "pass", "cowid", "paperid") VALUES
	('40','高丽娜','hzm0411a@163.com','168168','901','310102198604111631');
/*!40000 ALTER TABLE "user" ENABLE KEYS;*/
UNLOCK TABLES;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE;*/
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;*/
