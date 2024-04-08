/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.19-log : Database - smbms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`smbms` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci */;

USE `smbms`;

/*Table structure for table `smbms_address` */

DROP TABLE IF EXISTS `smbms_address`;

CREATE TABLE `smbms_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `contact` varchar(15) DEFAULT NULL COMMENT '联系人姓名',
  `addressDesc` varchar(50) DEFAULT NULL COMMENT '收货地址',
  `postCode` varchar(15) DEFAULT NULL COMMENT '邮编',
  `tel` varchar(20) DEFAULT NULL COMMENT '联系人电话',
  `createdBy` varchar(20) DEFAULT NULL COMMENT '创建者',
  `creationDate` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyBy` bigint(20) DEFAULT NULL COMMENT '修改者',
  `modifyDate` datetime DEFAULT NULL COMMENT '修改时间',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `smbms_address` */

insert  into `smbms_address`(`id`,`contact`,`addressDesc`,`postCode`,`tel`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`,`userId`) values (1,'王丽','北京市东城区东交民巷44号','100010','13678789999','1','2016-04-13 00:00:00',NULL,NULL,1),(2,'张红丽','北京市海淀区丹棱街3号','100000','18567672312','1','2016-04-13 00:00:00',NULL,NULL,1),(3,'任志强','北京市东城区美术馆后街23号','100021','13387906742','1','2016-04-13 00:00:00',NULL,NULL,1),(4,'曹颖','北京市朝阳区朝阳门南大街14号','100053','13568902323','1','2016-04-13 00:00:00',NULL,NULL,2),(5,'李慧','北京市西城区三里河路南三巷3号','100032','18032356666','1','2016-04-13 00:00:00',NULL,NULL,3),(6,'王国强','北京市顺义区高丽营镇金马工业区18号','100061','13787882222','1','2016-04-13 00:00:00',NULL,NULL,3);

/*Table structure for table `smbms_bill` */

DROP TABLE IF EXISTS `smbms_bill`;

CREATE TABLE `smbms_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `billCode` varchar(30) DEFAULT NULL COMMENT '账单编码',
  `productName` varchar(20) DEFAULT NULL COMMENT '商品名称',
  `productDesc` varchar(50) DEFAULT NULL COMMENT '商品描述',
  `productUnit` varchar(60) DEFAULT NULL COMMENT '商品单元',
  `productCount` decimal(20,2) DEFAULT NULL COMMENT '商品数量',
  `totalPrice` decimal(20,2) DEFAULT NULL COMMENT '总金额',
  `isPayment` int(10) DEFAULT NULL COMMENT '是否支付',
  `createdBy` bigint(20) DEFAULT NULL COMMENT '创建者',
  `creationDate` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyBy` bigint(20) DEFAULT NULL COMMENT '更新者',
  `modifyDate` datetime DEFAULT NULL COMMENT '更新时间',
  `providerID` varchar(20) DEFAULT NULL COMMENT '供应商ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `smbms_bill` */

insert  into `smbms_bill`(`id`,`billCode`,`productName`,`productDesc`,`productUnit`,`productCount`,`totalPrice`,`isPayment`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`,`providerID`) values (1,'BILL2016_001','洗发水、护发素','日用品-洗发、护发','瓶','500.00','25000.00',2,1,'2014-12-14 13:02:03',15,'2019-04-16 21:43:12','13'),(2,'BILL2016_002','香皂、肥皂、药皂','日用品-皂类','块','1000.00','10000.00',2,1,'2016-03-23 04:20:40',NULL,NULL,'13'),(3,'BILL2016_003','大豆油','食品-食用油','斤','300.00','5890.00',2,1,'2014-12-14 13:02:03',NULL,NULL,'6'),(4,'BILL2016_004','橄榄油','食品-进口食用油','斤','200.00','9800.00',2,1,'2013-10-10 03:12:13',NULL,NULL,'7'),(5,'BILL2016_005','洗洁精','日用品-厨房清洁','瓶','500.00','7000.00',2,1,'2014-12-14 13:02:03',NULL,NULL,'9'),(6,'BILL2016_006','美国大杏仁','食品-坚果','袋','300.00','5000.00',2,1,'2016-04-14 06:08:09',NULL,NULL,'4'),(7,'BILL2016_007','沐浴液、精油','日用品-沐浴类','瓶','500.00','23000.00',1,1,'2016-07-22 10:10:22',NULL,NULL,'14'),(8,'BILL2016_008','不锈钢盘碗','日用品-厨房用具','个','600.00','6000.00',2,1,'2016-04-14 05:12:13',NULL,NULL,'14'),(9,'BILL2016_009','塑料杯','日用品-杯子','个','350.00','1750.00',2,1,'2016-02-04 11:40:20',NULL,NULL,'14'),(10,'BILL2016_010','豆瓣酱','食品-调料','瓶','200.00','2000.00',2,1,'2013-10-29 05:07:03',NULL,NULL,'8'),(11,'BILL2016_011','海之蓝','饮料-国酒','瓶','50.00','10000.00',1,1,'2016-04-14 16:16:00',NULL,NULL,'1'),(12,'BILL2016_012','芝华士','饮料-洋酒','瓶','20.00','6000.00',1,1,'2016-09-09 17:00:00',NULL,NULL,'1'),(13,'BILL2016_013','长城红葡萄酒','饮料-红酒','瓶','60.00','800.00',2,1,'2016-11-14 15:23:00',NULL,NULL,'1'),(16,'BILL2016_016','可口可乐','饮料','瓶','2000.00','6000.00',2,1,'2012-03-27 13:03:01',NULL,NULL,'2'),(17,'BEIJING','好喝的玩意',NULL,'头','9999999.00','999999999.00',1,1,'2022-10-22 20:22:32',NULL,NULL,'13'),(18,'BILL-100','卖书的','都说了卖书的','本','32.00','40.00',2,1,'2023-02-07 17:40:56',15,'2023-02-07 17:40:56','1'),(20,'BILL2021_015','芦荟汁','干净又卫生啊，兄弟们','杯','10.00','19.00',2,1,'2023-02-07 18:52:14',15,'2023-02-07 18:52:14','1');

/*Table structure for table `smbms_provider` */

DROP TABLE IF EXISTS `smbms_provider`;

CREATE TABLE `smbms_provider` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proCode` varchar(15) DEFAULT NULL COMMENT '供应商编码',
  `proName` varchar(15) DEFAULT NULL COMMENT '供应商名称',
  `proDesc` varchar(50) DEFAULT NULL COMMENT '供应商描述',
  `proContact` varchar(15) DEFAULT NULL COMMENT '供应商联系人',
  `proPhone` varchar(20) DEFAULT NULL COMMENT '供应商电话',
  `proAddress` varchar(30) DEFAULT NULL COMMENT '供应商地址',
  `proFax` varchar(20) DEFAULT NULL COMMENT '供应商传真',
  `createdBy` bigint(20) DEFAULT NULL COMMENT '创建者',
  `creationDate` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyDate` datetime DEFAULT NULL COMMENT '更新时间',
  `modifyBy` bigint(20) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `smbms_provider` */

insert  into `smbms_provider`(`id`,`proCode`,`proName`,`proDesc`,`proContact`,`proPhone`,`proAddress`,`proFax`,`createdBy`,`creationDate`,`modifyDate`,`modifyBy`) values (1,'BJ_GYS001','北京三木堂商贸有限公司','长期合作伙伴，主营产品:茅台、五粮液、郎酒、酒鬼酒、泸州老窖、赖茅酒、法国红酒等','张国强','13566669999','北京市丰台区育芳园北路','010-58858787',1,'2013-03-21 16:52:07','2019-04-12 16:44:03',10),(2,'HB_GYS001','石家庄帅益食品贸易有限公司','长期合作伙伴，主营产品:饮料、水饮料、植物蛋白饮料、休闲食品、果汁饮料、功能饮料等','王军','13309094212','河北省石家庄新华区','0311-67738876',1,'2016-04-13 04:20:40',NULL,NULL),(3,'GZ_GYS001','深圳市泰香米业有限公司','初次合作伙伴，主营产品：良记金轮米,龙轮香米等','郑程瀚','13402013312','广东省深圳市福田区深南大道6006华丰大厦','0755-67776212',1,'2014-03-21 16:56:07',NULL,NULL),(4,'GZ_GYS002','深圳市喜来客商贸有限公司','长期合作伙伴，主营产品：坚果炒货.果脯蜜饯.天然花茶.营养豆豆.特色美食.进口食品.海味零食.肉脯肉','林妮','18599897645','广东省深圳市福龙工业区B2栋3楼西','0755-67772341',1,'2013-03-22 16:52:07',NULL,NULL),(5,'JS_GYS001','兴化佳美调味品厂','长期合作伙伴，主营产品：天然香辛料、鸡精、复合调味料','徐国洋','13754444221','江苏省兴化市林湖工业区','0523-21299098',1,'2015-11-22 16:52:07',NULL,NULL),(6,'BJ_GYS002','北京纳福尔食用油有限公司','长期合作伙伴，主营产品：山茶油、大豆油、花生油、橄榄油等','马莺','13422235678','北京市朝阳区珠江帝景1号楼','010-588634233',1,'2012-03-21 17:52:07',NULL,NULL),(7,'BJ_GYS003','北京国粮食用油有限公司','初次合作伙伴，主营产品：花生油、大豆油、小磨油等','王驰','13344441135','北京大兴青云店开发区','010-588134111',1,'2016-04-13 00:00:00',NULL,NULL),(8,'ZJ_GYS001','慈溪市广和绿色食品厂','长期合作伙伴，主营产品：豆瓣酱、黄豆酱、甜面酱，辣椒，大蒜等农产品','薛圣丹','18099953223','浙江省宁波市慈溪周巷小安村','0574-34449090',1,'2013-11-21 06:02:07',NULL,NULL),(9,'GX_GYS001','优百商贸有限公司','长期合作伙伴，主营产品：日化产品','李立国','13323566543','广西南宁市秀厢大道42-1号','0771-98861134',1,'2013-03-21 19:52:07',NULL,NULL),(10,'JS_GYS002','南京火头军信息技术有限公司','长期合作伙伴，主营产品：不锈钢厨具等','陈女士','13098992113','江苏省南京市浦口区浦口大道1号新城总部大厦A座903室','025-86223345',1,'2013-03-25 16:52:07',NULL,NULL),(11,'GZ_GYS003','广州市白云区美星五金制品厂','长期合作伙伴，主营产品：海绵床垫、坐垫、靠垫、海绵枕头、头枕等','梁天','13562276775','广州市白云区钟落潭镇福龙路20号','020-85542231',1,'2016-12-21 06:12:17',NULL,NULL),(12,'BJ_GYS004','北京隆盛日化科技','长期合作伙伴，主营产品：日化环保清洗剂，家居洗涤专卖、洗涤用品网、墙体除霉剂、墙面霉菌清除剂等','孙欣','13689865678','北京市大兴区旧宫','010-35576786',1,'2014-11-21 12:51:11',NULL,NULL),(13,'SD_GYS001','山东豪克华光联合发展有限公司','长期合作伙伴，主营产品：洗衣皂、洗衣粉、洗衣液、洗洁精、消杀类、香皂等','吴洪转','13245468787','山东济阳济北工业区仁和街21号','0531-53362445',1,'2015-01-28 10:52:07',NULL,NULL),(14,'BILL—20020','啥也不是','突然粗话v一条船v','法外狂徒','13592384383','外星人的M38星','11212121122121',1,'2022-09-24 14:59:13',NULL,NULL),(15,'NBCSS_02','憨批玩意','长期合作伙伴','主营产品：酒、头包','1234567','马里亚纳海沟','110-123445',1,'2023-02-07 16:11:02','2023-02-07 16:12:28',10),(16,'GZ_GYS155','广州市白云胡华五金制品厂','长期合作伙伴，主营产品：海绵床垫、坐垫、靠垫、海绵枕头、头枕等','高启强','17779674555','中国北京市大兴区旧宫','010-35576785',1,'2023-02-07 15:44:55','2023-02-07 16:16:04',1);

/*Table structure for table `smbms_role` */

DROP TABLE IF EXISTS `smbms_role`;

CREATE TABLE `smbms_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleCode` varchar(30) DEFAULT NULL COMMENT '角色编码',
  `roleName` varchar(15) DEFAULT NULL COMMENT '角色名称',
  `createdBy` bigint(20) DEFAULT NULL COMMENT '创建者',
  `creationDate` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyBy` bigint(20) DEFAULT NULL COMMENT '更新者',
  `modifyDate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `smbms_role` */

insert  into `smbms_role`(`id`,`roleCode`,`roleName`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`) values (1,'SMBMS_ADMIN','系统管理员',1,'2021-09-23 10:38:11',NULL,NULL),(2,'SMBMS_MANAGER','经理',1,'2021-03-20 10:39:07',NULL,NULL),(3,'SMBMS_EMPLOYEE','普通员工',1,'2020-10-14 10:39:41',NULL,NULL),(4,'DELIVERY','配送员',1,'2023-02-04 17:04:26',1,'2023-02-04 17:33:26');

/*Table structure for table `smbms_user` */

DROP TABLE IF EXISTS `smbms_user`;

CREATE TABLE `smbms_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userCode` varchar(15) DEFAULT NULL COMMENT '用户编码',
  `userName` varchar(15) DEFAULT NULL COMMENT '用户名字',
  `userPassword` varchar(20) DEFAULT NULL COMMENT '用户密码',
  `gender` int(10) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `address` varchar(30) DEFAULT NULL COMMENT '地址',
  `userRole` bigint(20) DEFAULT NULL COMMENT '用户角色',
  `createdBy` bigint(20) DEFAULT NULL COMMENT '创建者',
  `creationDate` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyBy` bigint(20) DEFAULT NULL COMMENT '更新者',
  `modifyDate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `smbms_user` */

insert  into `smbms_user`(`id`,`userCode`,`userName`,`userPassword`,`gender`,`birthday`,`phone`,`address`,`userRole`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`) values (1,'admin','admin','1111111',1,'2009-06-18','13627483923','北京朝阳区',1,1,'2021-10-20 16:48:47',1,'2019-08-07 16:49:30'),(2,'liming','李明','1234560',2,'1983-08-20','17829345823','河北唐山二逼区',2,1,'2004-07-30 16:55:52',1,'2021-01-06 16:56:17'),(5,'hanlubiao','韩璐彪','99999',2,'1982-07-23','15637829384','河南郑州市金水区',2,1,'1981-06-11 16:53:46',1,'2021-07-23 16:54:16'),(7,'wangyang','汪洋','0000000',2,'1992-07-17','18762349873','湖北武汉Ⅹ',3,1,'2015-03-13 17:03:32',1,'2020-10-23 17:03:47'),(8,'zhaoyan','赵燕','0000000',2,'1984-11-15','18273927621','北京市朝阳群众',3,1,'2018-12-22 17:13:46',1,'2022-01-21 17:13:57'),(10,'sunlei','孙磊','0000000',2,'1987-03-06','17823478923','四川成都天府之国',3,1,'2014-03-28 16:58:12',1,'2020-11-13 16:58:26'),(11,'sunxing','孙兴','0000000',2,'1980-01-11','1872346789','美国华盛顿垃圾',3,1,'2017-03-11 17:01:28',1,'2020-08-23 17:01:40'),(12,'zhangchen','张晨','0000000',1,'1985-03-08','17283674839','安徽芜湖起飞',3,1,'2017-07-13 17:07:23',1,'2021-07-23 17:07:35'),(13,'dengchao','邓超','0000000',2,'2012-02-24','13267389237','天津某某某',3,1,'2021-07-15 16:50:40',1,'2019-10-27 16:50:53'),(14,'yangg','杨过','0000000',2,'1984-07-13','19873627883','云南哪哪哪',3,1,'2015-08-28 17:05:38',1,'2021-05-13 17:05:53'),(15,'zhaoming','赵敏','0000000',2,'1983-11-11','18723887398','河南商丘项城',3,1,'2018-07-21 17:11:45',1,'2021-06-22 17:11:58'),(20,'liushisan','刘十三','1234567',2,'2023-02-04','16692384383','云边镇',2,1,'2023-02-04 14:11:30',1,'2023-02-04 16:26:35'),(23,'chengshuang','程霜','123456',1,'2016-09-30','17779674555','新加坡',2,1,'2023-02-04 14:27:22',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
