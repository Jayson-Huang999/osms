/*
Navicat MySQL Data Transfer

Source Server         : connection
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : osms

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2022-03-12 15:08:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for osms_activity
-- ----------------------------
DROP TABLE IF EXISTS `osms_activity`;
CREATE TABLE `osms_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `preferentiallevel` bigint DEFAULT NULL,
  `starttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `remain` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_osms_activity_level2` (`preferentiallevel`) USING BTREE,
  KEY `FK_osms_activity_product` (`product_id`) USING BTREE,
  CONSTRAINT `FK_osms_activity_product` FOREIGN KEY (`product_id`) REFERENCES `osms_product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='活动表';

-- ----------------------------
-- Records of osms_activity
-- ----------------------------
INSERT INTO `osms_activity` VALUES ('1', '1', '80', '2022-03-01 12:56:44', '2022-03-10 12:56:49', '20', '10', '1');
INSERT INTO `osms_activity` VALUES ('2', '3', '75', '2022-03-09 09:57:54', '2022-03-10 09:57:56', '20', '15', '1');
INSERT INTO `osms_activity` VALUES ('3', '9', '99', '2022-03-09 10:42:48', '2022-03-10 10:42:50', '20', '17', '1');
INSERT INTO `osms_activity` VALUES ('4', '21', '88', '2022-03-09 10:43:11', '2022-03-09 10:43:14', '20', '13', '1');
INSERT INTO `osms_activity` VALUES ('5', '8', '50', '2022-03-09 11:24:13', '2022-03-09 11:24:15', '20', '3', '1');
INSERT INTO `osms_activity` VALUES ('6', '10', '97', '2022-03-10 12:11:09', '2022-03-12 12:11:12', '20', '19', '1');
INSERT INTO `osms_activity` VALUES ('7', '15', '44', '2022-03-10 12:11:47', '2022-03-17 12:11:50', '20', '19', '1');
INSERT INTO `osms_activity` VALUES ('8', '11', '80', '2022-03-10 15:49:22', '2022-03-25 15:49:24', '20', '19', '0');
INSERT INTO `osms_activity` VALUES ('9', '12', '66', '2022-03-11 10:50:50', '2022-03-25 10:50:53', '20', '12', '1');
INSERT INTO `osms_activity` VALUES ('10', '2', '77', '2022-03-12 13:39:56', '2022-03-16 13:39:58', '20', '13', '1');
INSERT INTO `osms_activity` VALUES ('11', '48', '99', '2022-03-12 13:40:15', '2022-03-16 13:40:28', '40', '13', '1');
INSERT INTO `osms_activity` VALUES ('12', '7', '98', '2022-03-12 13:41:28', '2022-03-14 13:41:39', '40', '11', '1');
INSERT INTO `osms_activity` VALUES ('13', '6', '95', '2022-03-23 13:41:53', '2022-03-14 13:42:12', '50', '20', '2');
INSERT INTO `osms_activity` VALUES ('14', '45', '60', '2022-03-12 13:42:38', '2022-03-15 13:42:40', '80', '11', '1');
INSERT INTO `osms_activity` VALUES ('15', '35', '88', '2022-03-12 14:17:20', '2022-03-15 14:17:22', '40', '20', '1');
INSERT INTO `osms_activity` VALUES ('16', '50', '80', '2022-03-12 06:54:31', '2022-03-12 16:00:00', '10', '5', '1');

-- ----------------------------
-- Table structure for osms_address
-- ----------------------------
DROP TABLE IF EXISTS `osms_address`;
CREATE TABLE `osms_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `receivername` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `receiverphone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `receiverprovince` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `receivercity` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `receiverdistrict` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `receiveraddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `is_default` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_osms_user_address` (`user_id`) USING BTREE,
  CONSTRAINT `FK_osms_user_address` FOREIGN KEY (`user_id`) REFERENCES `osms_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='收货地址信息表';

-- ----------------------------
-- Records of osms_address
-- ----------------------------
INSERT INTO `osms_address` VALUES ('1', '1', '小李儿子', '668', '四川省', '成都市', '双流区', '江安校区', '0');
INSERT INTO `osms_address` VALUES ('2', '1', '真的牛', '54445445444', '天津市', '天津市', '河北区', '对对对', '1');
INSERT INTO `osms_address` VALUES ('3', '1', '好好好', '15883336849', '山西省', '晋城市', '泽州县', '不知道', '0');
INSERT INTO `osms_address` VALUES ('4', '1', 'ssda', 'sddsa', '天津市', '天津市', '河西区', 'dsads', null);
INSERT INTO `osms_address` VALUES ('5', '1', '糖分', '1515551', '辽宁省', '本溪市', '南芬区', '大撒大撒', null);

-- ----------------------------
-- Table structure for osms_admins
-- ----------------------------
DROP TABLE IF EXISTS `osms_admins`;
CREATE TABLE `osms_admins` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `lastlogintime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of osms_admins
-- ----------------------------
INSERT INTO `osms_admins` VALUES ('1', 'lgl', '123', '123456', '2022-03-02 23:19:58');

-- ----------------------------
-- Table structure for osms_brand
-- ----------------------------
DROP TABLE IF EXISTS `osms_brand`;
CREATE TABLE `osms_brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `logourl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `describ` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商品品牌信息表';

-- ----------------------------
-- Records of osms_brand
-- ----------------------------
INSERT INTO `osms_brand` VALUES ('1', '海澜之家', null, null, '1');
INSERT INTO `osms_brand` VALUES ('2', '与狼共舞', null, null, '1');
INSERT INTO `osms_brand` VALUES ('3', '森马', null, null, '1');
INSERT INTO `osms_brand` VALUES ('4', '杰克琼斯', null, null, '1');
INSERT INTO `osms_brand` VALUES ('5', '斐乐', null, null, '1');
INSERT INTO `osms_brand` VALUES ('6', '华为', null, null, '2');
INSERT INTO `osms_brand` VALUES ('7', '小米', null, null, '2');
INSERT INTO `osms_brand` VALUES ('8', 'OPPO', null, null, '2');
INSERT INTO `osms_brand` VALUES ('9', '百草味', null, null, '3');
INSERT INTO `osms_brand` VALUES ('10', '达利园', null, null, '3');
INSERT INTO `osms_brand` VALUES ('11', '康师傅', null, null, '3');
INSERT INTO `osms_brand` VALUES ('12', '得力', null, null, '4');
INSERT INTO `osms_brand` VALUES ('13', '富居', null, null, '4');
INSERT INTO `osms_brand` VALUES ('14', '网易严选', null, null, '4');

-- ----------------------------
-- Table structure for osms_cart_product
-- ----------------------------
DROP TABLE IF EXISTS `osms_cart_product`;
CREATE TABLE `osms_cart_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `specification_id` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_Reference_17` (`user_id`) USING BTREE,
  KEY `FK_osms_cart_product2` (`specification_id`) USING BTREE,
  CONSTRAINT `FK_osms_cart_product2` FOREIGN KEY (`specification_id`) REFERENCES `osms_specification` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`user_id`) REFERENCES `osms_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of osms_cart_product
-- ----------------------------
INSERT INTO `osms_cart_product` VALUES ('69', '1', '61', '1');
INSERT INTO `osms_cart_product` VALUES ('71', '1', '63', '1');

-- ----------------------------
-- Table structure for osms_category
-- ----------------------------
DROP TABLE IF EXISTS `osms_category`;
CREATE TABLE `osms_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商品类别表';

-- ----------------------------
-- Records of osms_category
-- ----------------------------
INSERT INTO `osms_category` VALUES ('1', '时尚服饰');
INSERT INTO `osms_category` VALUES ('2', '数码产品');
INSERT INTO `osms_category` VALUES ('3', '食品饮料');
INSERT INTO `osms_category` VALUES ('4', '日常用品');

-- ----------------------------
-- Table structure for osms_comments
-- ----------------------------
DROP TABLE IF EXISTS `osms_comments`;
CREATE TABLE `osms_comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `specification_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `content` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `score` int DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of osms_comments
-- ----------------------------
INSERT INTO `osms_comments` VALUES ('1', '1', '11', '60', '1501941401719926785', '真不错', '80', '2022-03-11 11:41:25');
INSERT INTO `osms_comments` VALUES ('2', '1', '11', '61', '1501941401732509697', '？？？？', '80', '2022-03-11 15:04:55');
INSERT INTO `osms_comments` VALUES ('3', '1', '11', '60', '1502141820103954433', '6+6', '60', '2022-03-11 15:11:36');
INSERT INTO `osms_comments` VALUES ('4', '1', '11', '60', '1502141820103954433', '333', '60', '2022-03-11 15:11:44');
INSERT INTO `osms_comments` VALUES ('5', '1', '11', '62', '1501941401732509698', '3333', '100', '2022-03-11 15:12:03');
INSERT INTO `osms_comments` VALUES ('6', '1', '1', '1', '1501775260569530369', '???', '40', '2022-03-11 15:12:32');
INSERT INTO `osms_comments` VALUES ('7', '1', '11', '61', '1501940627086450690', '6', '80', '2022-03-11 15:13:26');
INSERT INTO `osms_comments` VALUES ('8', '1', '11', '60', '1501940627103227906', '88888888', '80', '2022-03-11 15:20:49');
INSERT INTO `osms_comments` VALUES ('9', '1', '11', '60', '1502504014251474946', 'zhenbucuo', '80', '2022-03-12 12:44:46');
INSERT INTO `osms_comments` VALUES ('10', '1', '2', '10', '1501162215520055297', '123', '80', '2022-03-12 14:49:24');

-- ----------------------------
-- Table structure for osms_dict
-- ----------------------------
DROP TABLE IF EXISTS `osms_dict`;
CREATE TABLE `osms_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of osms_dict
-- ----------------------------
INSERT INTO `osms_dict` VALUES ('1', '待付款');
INSERT INTO `osms_dict` VALUES ('2', '待发货');
INSERT INTO `osms_dict` VALUES ('3', '已发货');
INSERT INTO `osms_dict` VALUES ('4', '已完成');
INSERT INTO `osms_dict` VALUES ('5', '已取消');

-- ----------------------------
-- Table structure for osms_favorite
-- ----------------------------
DROP TABLE IF EXISTS `osms_favorite`;
CREATE TABLE `osms_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_osms_favorite` (`user_id`) USING BTREE,
  KEY `FK_osms_favorite2` (`product_id`) USING BTREE,
  CONSTRAINT `FK_osms_favorite` FOREIGN KEY (`user_id`) REFERENCES `osms_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_osms_favorite2` FOREIGN KEY (`product_id`) REFERENCES `osms_product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户商品收藏';

-- ----------------------------
-- Records of osms_favorite
-- ----------------------------
INSERT INTO `osms_favorite` VALUES ('5', '1', '8');
INSERT INTO `osms_favorite` VALUES ('16', '1', '3');
INSERT INTO `osms_favorite` VALUES ('17', '1', '5');
INSERT INTO `osms_favorite` VALUES ('18', '1', '17');
INSERT INTO `osms_favorite` VALUES ('19', '1', '22');

-- ----------------------------
-- Table structure for osms_image
-- ----------------------------
DROP TABLE IF EXISTS `osms_image`;
CREATE TABLE `osms_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_osms_product_img` (`product_id`) USING BTREE,
  CONSTRAINT `FK_osms_product_img` FOREIGN KEY (`product_id`) REFERENCES `osms_product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of osms_image
-- ----------------------------
INSERT INTO `osms_image` VALUES ('1', '1', 'https://img14.360buyimg.com/n0/jfs/t1/194137/21/19040/133701/611f73deE947df510/5b188640123ea14a.jpg');
INSERT INTO `osms_image` VALUES ('2', '2', 'https://img14.360buyimg.com/n0/jfs/t1/127362/11/22946/131708/6226aa98Ed40bb28c/842a2985375f7759.jpg');
INSERT INTO `osms_image` VALUES ('3', '3', 'https://img14.360buyimg.com/n0/jfs/t1/158386/6/27513/142299/62025a29E7a1250f8/0a966098e52611f4.jpg');
INSERT INTO `osms_image` VALUES ('4', '4', 'https://img14.360buyimg.com/n0/jfs/t1/184796/4/18155/70493/61114669Ec5b069b5/249bdf8b02bcb9b9.jpg');
INSERT INTO `osms_image` VALUES ('5', '5', 'https://img14.360buyimg.com/n0/jfs/t1/141162/3/4430/83981/5f27b70aE2bb6b548/0a76d5de548b44ad.jpg');
INSERT INTO `osms_image` VALUES ('6', '6', 'https://img14.360buyimg.com/n0/jfs/t1/200382/6/4521/92731/6123a2a6E52129bc6/02056748462df942.jpg');
INSERT INTO `osms_image` VALUES ('7', '7', 'https://img14.360buyimg.com/n0/jfs/t1/101093/28/22422/389661/620b6594E96264e81/f97b974a6f2a1ec7.jpg');
INSERT INTO `osms_image` VALUES ('8', '8', 'https://img14.360buyimg.com/n0/jfs/t1/114128/6/20052/125592/620a47a2E989727c2/4bf37772cbb5bcc4.jpg');
INSERT INTO `osms_image` VALUES ('9', '9', 'https://img14.360buyimg.com/n0/jfs/t1/95101/24/22737/173314/62262fe8Ea51a8bc0/4010e8229b367a5c.jpg');
INSERT INTO `osms_image` VALUES ('10', '10', 'https://img14.360buyimg.com/n0/jfs/t1/223542/27/6483/193583/62262ee7Ee025ddeb/368014e148d4b6e6.jpg');
INSERT INTO `osms_image` VALUES ('11', '11', 'https://img14.360buyimg.com/n0/jfs/t1/115003/31/21026/258197/62262f11Ea985f682/f81398a22689d797.jpg');
INSERT INTO `osms_image` VALUES ('12', '12', 'https://img14.360buyimg.com/n0/jfs/t1/136271/2/23983/92463/621f67e2Ed864bc66/ba0e62f911ea8e8f.jpg');
INSERT INTO `osms_image` VALUES ('13', '13', 'https://img14.360buyimg.com/n0/jfs/t1/88452/18/20170/91007/61ed3fb5E99fb4641/d7e0989c3b6d7128.jpg');
INSERT INTO `osms_image` VALUES ('14', '14', 'https://img14.360buyimg.com/n0/jfs/t1/146223/38/23313/189716/621e1f73E1035a8db/7fd5d9319c7fc353.jpg');
INSERT INTO `osms_image` VALUES ('15', '15', 'https://img14.360buyimg.com/n0/jfs/t1/109183/2/24208/85166/621c2741Edf8d0c35/85a908118fae659a.jpg');
INSERT INTO `osms_image` VALUES ('16', '16', 'https://img14.360buyimg.com/n0/jfs/t1/135030/25/24613/160800/621ec795E67e59f54/4fdac772639b6002.jpg');
INSERT INTO `osms_image` VALUES ('17', '17', 'https://img14.360buyimg.com/n0/jfs/t1/209823/35/5464/184159/61697197Ec2a48eec/e28a2833555b00a6.jpg');
INSERT INTO `osms_image` VALUES ('18', '18', 'https://img14.360buyimg.com/n0/jfs/t1/87418/10/24032/169044/6226e57cE93e70f55/09790d2a6c9941d6.jpg');
INSERT INTO `osms_image` VALUES ('19', '19', 'https://img14.360buyimg.com/n0/jfs/t1/170247/17/12852/67106/604f1593E65e7d374/dc2e256bf9e1a020.jpg');
INSERT INTO `osms_image` VALUES ('20', '20', 'https://img14.360buyimg.com/n0/jfs/t1/131748/1/23502/116692/620726d2E9cebf984/b05423ae0b574fcf.jpg');
INSERT INTO `osms_image` VALUES ('21', '21', 'https://img14.360buyimg.com/n0/jfs/t1/93240/4/23433/201797/621c4db7E2d6ef824/56be83182f11a6e3.jpg');
INSERT INTO `osms_image` VALUES ('22', '22', 'https://img14.360buyimg.com/n0/jfs/t1/105706/2/21455/257642/62256941Ed8ef90ff/15824a82b12d217f.jpg');
INSERT INTO `osms_image` VALUES ('23', '23', 'https://img14.360buyimg.com/n0/jfs/t1/155911/32/18569/106971/6061aa9dE80ab4a75/322a43e898b50284.jpg');
INSERT INTO `osms_image` VALUES ('24', '24', 'https://img14.360buyimg.com/n0/jfs/t1/115447/4/20921/448734/6225c32dE5a9de180/2b02036da6a5cb58.jpg');
INSERT INTO `osms_image` VALUES ('25', '25', 'https://img14.360buyimg.com/n0/jfs/t1/146696/21/21004/591276/620ccdf3Ea908e52d/5742b881d8d7f10b.jpg');
INSERT INTO `osms_image` VALUES ('26', '26', 'https://img14.360buyimg.com/n0/jfs/t1/120716/5/24470/122435/6224b950E0ae6d700/c60f1ad6e3aa517d.jpg');
INSERT INTO `osms_image` VALUES ('27', '27', 'https://img14.360buyimg.com/n0/jfs/t1/100960/29/23996/247879/6226b364Eb3d9659c/7462b3ddba19bb34.jpg');
INSERT INTO `osms_image` VALUES ('28', '28', 'https://img14.360buyimg.com/n0/jfs/t1/108324/22/23414/290427/621c7ea0Ead44b1f6/518e9c329e05f8f1.jpg');
INSERT INTO `osms_image` VALUES ('29', '29', 'https://img14.360buyimg.com/n0/jfs/t1/108549/35/23255/389223/62207a90E6053e799/38651231be9a0fa2.jpg');
INSERT INTO `osms_image` VALUES ('30', '30', 'https://img14.360buyimg.com/n0/jfs/t1/212916/32/13588/351652/62207aafE15aa054e/bf8d5126574d54a7.jpg');
INSERT INTO `osms_image` VALUES ('31', '31', 'https://img14.360buyimg.com/n0/jfs/t1/117226/21/20266/319108/62207a1dE2e2d6fca/912a2427cd46eff4.jpg');
INSERT INTO `osms_image` VALUES ('32', '32', 'https://img14.360buyimg.com/n0/jfs/t1/219116/36/13773/239873/62216175E1e7041e1/1196e085f5a7f780.jpg');
INSERT INTO `osms_image` VALUES ('33', '33', 'https://img14.360buyimg.com/n0/jfs/t1/209862/34/19077/205800/6225995cE7e933511/1a9e054c6305cb37.jpg');
INSERT INTO `osms_image` VALUES ('34', '34', 'https://img14.360buyimg.com/n0/jfs/t1/99529/17/22546/226195/621c973fEdc789d58/fe4f1091d8be8bc5.jpg');
INSERT INTO `osms_image` VALUES ('35', '35', 'https://img14.360buyimg.com/n0/jfs/t1/212738/28/13253/262693/62147e65Ecec850a3/3f3b0e7c0daab2ec.jpg');
INSERT INTO `osms_image` VALUES ('36', '36', 'https://img14.360buyimg.com/n0/jfs/t1/104298/26/23762/290124/620f5717Ecd9e1d73/0ff11d36fb48fdce.jpg');
INSERT INTO `osms_image` VALUES ('37', '37', 'https://img14.360buyimg.com/n1/s546x546_jfs/t1/202487/9/15927/249235/61a03c21Eb0593e21/aa9d114da76ea09d.jpg');
INSERT INTO `osms_image` VALUES ('38', '38', 'https://img14.360buyimg.com/n0/jfs/t1/105322/19/18815/188248/5e97d122Ed7a72c03/494c66713acf53cf.jpg');
INSERT INTO `osms_image` VALUES ('39', '39', 'https://img14.360buyimg.com/n0/jfs/t1/49029/33/14768/93166/5db94104E7751bd72/ce5a393c51529c4c.jpg');
INSERT INTO `osms_image` VALUES ('40', '40', 'https://img14.360buyimg.com/n0/jfs/t1/93034/11/9354/132278/5e0d9c0cE0cc81eeb/75104f3f0105c076.jpg');
INSERT INTO `osms_image` VALUES ('41', '41', 'https://img14.360buyimg.com/n0/jfs/t21196/90/1916464187/375142/cfb1e837/5b3ed9abNda52b7dc.jpg');
INSERT INTO `osms_image` VALUES ('42', '42', 'https://img14.360buyimg.com/n0/jfs/t1/115965/19/6292/235177/5eb8cc2cE22080168/1ffccd627f9566ad.jpg');
INSERT INTO `osms_image` VALUES ('43', '43', 'https://img14.360buyimg.com/n0/jfs/t1/175539/31/4273/175236/60780e7bEdab48599/c0f681915a664897.jpg');
INSERT INTO `osms_image` VALUES ('44', '44', 'https://img14.360buyimg.com/n0/jfs/t1/159494/34/13136/238944/60501a4aE0db98667/51358a215875ffee.jpg');
INSERT INTO `osms_image` VALUES ('45', '45', 'https://img14.360buyimg.com/n0/jfs/t1/84816/37/5435/258229/5dedc34eE33f31c69/fd0d2534e01b0362.jpg');
INSERT INTO `osms_image` VALUES ('46', '46', 'https://img14.360buyimg.com/n0/jfs/t1/188760/22/19643/690186/61249e93E97e00ca5/88ea3ee6de9a9e11.jpg');
INSERT INTO `osms_image` VALUES ('47', '47', 'https://img14.360buyimg.com/n0/jfs/t1/215104/2/13730/438649/621f1dc6Ede194449/01482620d492ebc3.jpg');
INSERT INTO `osms_image` VALUES ('48', '48', 'https://img14.360buyimg.com/n0/jfs/t19264/75/437854441/345202/d7f4fb81/5a794e31Nd2c613b8.jpg');
INSERT INTO `osms_image` VALUES ('49', '49', './image/202203/-fc004508c2454004ba4d477061937065.png');
INSERT INTO `osms_image` VALUES ('50', '50', './image/202203/-36b47538fc9446d9a5610a1dbdb5aa9b.png');

-- ----------------------------
-- Table structure for osms_messages
-- ----------------------------
DROP TABLE IF EXISTS `osms_messages`;
CREATE TABLE `osms_messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `content` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `state` int DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `seller_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk` (`seller_id`),
  CONSTRAINT `fk` FOREIGN KEY (`seller_id`) REFERENCES `osms_seller` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of osms_messages
-- ----------------------------
INSERT INTO `osms_messages` VALUES ('1', '1', '3', '？？？', '1', '2022-03-08 09:35:39', null);
INSERT INTO `osms_messages` VALUES ('2', '1', '3', '？？？', '1', '2022-03-08 09:36:49', null);
INSERT INTO `osms_messages` VALUES ('3', '1', '11', 'haoma', '1', '2022-03-11 09:54:50', '1');
INSERT INTO `osms_messages` VALUES ('4', '1', '13', '123', '1', '2022-03-11 09:56:30', '2');
INSERT INTO `osms_messages` VALUES ('5', '1', '12', '123', '1', '2022-03-11 10:00:50', '1');
INSERT INTO `osms_messages` VALUES ('6', '1', '1', '你好', '1', '2022-03-12 13:30:28', '1');
INSERT INTO `osms_messages` VALUES ('7', '1', '1', '你好', '1', '2022-03-12 13:30:30', '1');
INSERT INTO `osms_messages` VALUES ('8', '1', '1', '你好', '0', '2022-03-12 13:30:32', '1');
INSERT INTO `osms_messages` VALUES ('9', '1', '12', '123', '1', '2022-03-12 05:54:11', '1');
INSERT INTO `osms_messages` VALUES ('10', '1', '11', '123\n', '1', '2022-03-12 06:11:21', '1');

-- ----------------------------
-- Table structure for osms_orders
-- ----------------------------
DROP TABLE IF EXISTS `osms_orders`;
CREATE TABLE `osms_orders` (
  `id` bigint NOT NULL,
  `address_id` bigint DEFAULT NULL,
  `specification_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `status` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `preferentialprice` decimal(10,2) DEFAULT NULL,
  `totalprice` decimal(10,2) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_Reference_14` (`address_id`) USING BTREE,
  KEY `FK_Reference_15` (`user_id`) USING BTREE,
  KEY `FK_Reference_16` (`specification_id`) USING BTREE,
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`address_id`) REFERENCES `osms_address` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`user_id`) REFERENCES `osms_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`specification_id`) REFERENCES `osms_specification` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='订单明细表';

-- ----------------------------
-- Records of osms_orders
-- ----------------------------
INSERT INTO `osms_orders` VALUES ('1501157758447104001', '1', '14', '1', '5', '4', '0.00', '1992.00', '2022-03-08 19:27:54');
INSERT INTO `osms_orders` VALUES ('1501161779287273473', '1', '14', '1', '5', '4', '0.00', '1992.00', '2022-03-08 19:43:53');
INSERT INTO `osms_orders` VALUES ('1501161779354382338', '1', '157', '1', '5', '1', '0.00', '22.90', '2022-03-08 19:43:53');
INSERT INTO `osms_orders` VALUES ('1501161779354382339', '1', '85', '1', '5', '1', '0.00', '6588.00', '2022-03-08 19:43:53');
INSERT INTO `osms_orders` VALUES ('1501161994413125634', '1', '14', '1', '1', '4', '0.00', '1992.00', '2022-03-08 19:44:44');
INSERT INTO `osms_orders` VALUES ('1501161994480234498', '1', '157', '1', '1', '1', '0.00', '22.90', '2022-03-08 19:44:44');
INSERT INTO `osms_orders` VALUES ('1501161994480234499', '1', '85', '1', '1', '1', '0.00', '6588.00', '2022-03-08 19:44:44');
INSERT INTO `osms_orders` VALUES ('1501162215520055297', '1', '10', '1', '4', '1', '0.00', '98.00', '2022-03-08 19:45:37');
INSERT INTO `osms_orders` VALUES ('1501162734900719617', '1', '14', '1', '1', '4', '0.00', '1992.00', '2022-03-08 19:47:41');
INSERT INTO `osms_orders` VALUES ('1501162734900719618', '1', '157', '1', '1', '1', '0.00', '22.90', '2022-03-08 19:47:41');
INSERT INTO `osms_orders` VALUES ('1501162734900719619', '1', '85', '1', '1', '1', '0.00', '6588.00', '2022-03-08 19:47:41');
INSERT INTO `osms_orders` VALUES ('1501163200963391490', '1', '14', '1', '1', '4', '0.00', '1992.00', '2022-03-08 19:49:32');
INSERT INTO `osms_orders` VALUES ('1501163200963391491', '1', '157', '1', '1', '1', '0.00', '22.90', '2022-03-08 19:49:32');
INSERT INTO `osms_orders` VALUES ('1501163200963391492', '1', '85', '1', '1', '1', '0.00', '6588.00', '2022-03-08 19:49:32');
INSERT INTO `osms_orders` VALUES ('1501163373353480193', '1', '14', '1', '1', '4', '0.00', '1992.00', '2022-03-08 19:50:13');
INSERT INTO `osms_orders` VALUES ('1501163373416394753', '1', '157', '1', '1', '1', '0.00', '22.90', '2022-03-08 19:50:13');
INSERT INTO `osms_orders` VALUES ('1501163373416394754', '1', '85', '1', '1', '1', '0.00', '6588.00', '2022-03-08 19:50:13');
INSERT INTO `osms_orders` VALUES ('1501163652845121537', '1', '14', '1', '1', '4', '0.00', '1992.00', '2022-03-08 19:51:20');
INSERT INTO `osms_orders` VALUES ('1501163652845121538', '1', '157', '1', '1', '1', '0.00', '22.90', '2022-03-08 19:51:20');
INSERT INTO `osms_orders` VALUES ('1501163652845121539', '1', '85', '1', '1', '1', '0.00', '6588.00', '2022-03-08 19:51:20');
INSERT INTO `osms_orders` VALUES ('1501164123601219586', '1', '14', '1', '1', '4', '0.00', '1992.00', '2022-03-08 19:53:12');
INSERT INTO `osms_orders` VALUES ('1501164123601219587', '1', '157', '1', '1', '1', '0.00', '22.90', '2022-03-08 19:53:12');
INSERT INTO `osms_orders` VALUES ('1501164123601219588', '1', '85', '1', '1', '1', '0.00', '6588.00', '2022-03-08 19:53:12');
INSERT INTO `osms_orders` VALUES ('1501165199809294337', '1', '6', '1', '5', '1', '0.00', '68.00', '2022-03-08 19:57:29');
INSERT INTO `osms_orders` VALUES ('1501165199809294338', '1', '19', '1', '5', '1', '0.00', '459.00', '2022-03-08 19:57:29');
INSERT INTO `osms_orders` VALUES ('1501775260569530369', '1', '1', '1', '4', '5', '0.00', '340.00', '2022-03-10 12:21:38');
INSERT INTO `osms_orders` VALUES ('1501775260594696193', '1', '73', '1', '1', '1', '0.00', '459.00', '2022-03-10 12:21:38');
INSERT INTO `osms_orders` VALUES ('1501775260594696194', '1', '64', '1', '1', '1', '0.00', '459.00', '2022-03-10 12:21:38');
INSERT INTO `osms_orders` VALUES ('1501940626868346882', '1', '16', '1', '1', '6', '0.00', '2988.00', '2022-03-10 23:18:45');
INSERT INTO `osms_orders` VALUES ('1501940627073867778', '1', '63', '1', '1', '1', '0.00', '459.00', '2022-03-10 23:18:45');
INSERT INTO `osms_orders` VALUES ('1501940627073867779', '1', '71', '1', '1', '1', '0.00', '459.00', '2022-03-10 23:18:45');
INSERT INTO `osms_orders` VALUES ('1501940627086450689', '1', '75', '1', '1', '1', '0.00', '899.00', '2022-03-10 23:18:45');
INSERT INTO `osms_orders` VALUES ('1501940627086450690', '1', '61', '1', '4', '2', '0.00', '638.00', '2022-03-10 23:18:45');
INSERT INTO `osms_orders` VALUES ('1501940627103227906', '1', '60', '1', '4', '3', '0.00', '957.00', '2022-03-10 23:18:45');
INSERT INTO `osms_orders` VALUES ('1501940627111616513', '1', '83', '1', '1', '1', '0.00', '1339.00', '2022-03-10 23:18:45');
INSERT INTO `osms_orders` VALUES ('1501941401719926785', '1', '60', '1', '4', '1', '0.00', '319.00', '2022-03-10 23:21:50');
INSERT INTO `osms_orders` VALUES ('1501941401732509697', '1', '61', '1', '4', '1', '0.00', '319.00', '2022-03-10 23:21:50');
INSERT INTO `osms_orders` VALUES ('1501941401732509698', '1', '62', '1', '4', '1', '0.00', '319.00', '2022-03-10 23:21:50');
INSERT INTO `osms_orders` VALUES ('1501941401732509699', '1', '92', '1', '2', '1', '0.00', '7488.00', '2022-03-10 23:21:50');
INSERT INTO `osms_orders` VALUES ('1501941401732509700', '1', '86', '1', '1', '1', '0.00', '6688.00', '2022-03-10 23:21:50');
INSERT INTO `osms_orders` VALUES ('1502140995717697537', '1', '89', '1', '2', '7', '0.00', '52416.00', '2022-03-11 12:34:56');
INSERT INTO `osms_orders` VALUES ('1502141820103954433', '1', '60', '1', '4', '20', '0.00', '6380.00', '2022-03-11 12:38:13');
INSERT INTO `osms_orders` VALUES ('1502150229469650946', '1', '60', '1', '2', '1', '0.00', '319.00', '2022-03-11 13:11:38');
INSERT INTO `osms_orders` VALUES ('1502150229574508546', '1', '157', '1', '2', '1', '0.00', '22.90', '2022-03-11 13:11:38');
INSERT INTO `osms_orders` VALUES ('1502150229587091457', '1', '75', '1', '2', '1', '0.00', '899.00', '2022-03-11 13:11:38');
INSERT INTO `osms_orders` VALUES ('1502150705313439745', '1', '60', '1', '2', '1', '0.00', '319.00', '2022-03-11 13:13:31');
INSERT INTO `osms_orders` VALUES ('1502150705326022658', '1', '78', '1', '2', '2', '0.00', '1158.00', '2022-03-11 13:13:31');
INSERT INTO `osms_orders` VALUES ('1502150705326022659', '1', '157', '1', '2', '1', '0.00', '22.90', '2022-03-11 13:13:31');
INSERT INTO `osms_orders` VALUES ('1502151396001972226', '1', '61', '1', '2', '1', '0.00', '319.00', '2022-03-11 13:16:16');
INSERT INTO `osms_orders` VALUES ('1502151396001972227', '1', '60', '1', '2', '1', '0.00', '319.00', '2022-03-11 13:16:16');
INSERT INTO `osms_orders` VALUES ('1502151396018749441', '1', '78', '1', '2', '2', '0.00', '1158.00', '2022-03-11 13:16:16');
INSERT INTO `osms_orders` VALUES ('1502164674426130433', '1', '63', '1', '2', '1', '156.06', '302.94', '2022-03-11 14:09:02');
INSERT INTO `osms_orders` VALUES ('1502164801966526465', '1', '63', '1', '2', '1', '156.06', '302.94', '2022-03-11 14:09:32');
INSERT INTO `osms_orders` VALUES ('1502166203879084033', '1', '60', '1', '5', '1', '0.00', '319.00', '2022-03-11 14:15:07');
INSERT INTO `osms_orders` VALUES ('1502174969257242626', '1', '60', '1', '2', '1', '0.00', '319.00', '2022-03-11 14:49:56');
INSERT INTO `osms_orders` VALUES ('1502175210995953665', '1', '63', '1', '2', '1', '156.06', '302.94', '2022-03-11 14:50:54');
INSERT INTO `osms_orders` VALUES ('1502175393259433985', '1', '63', '1', '2', '1', '156.06', '302.94', '2022-03-11 14:51:37');
INSERT INTO `osms_orders` VALUES ('1502179283421851649', '1', '60', '1', '2', '2', '0.00', '1276.00', '2022-03-11 15:07:05');
INSERT INTO `osms_orders` VALUES ('1502179283585429506', '1', '61', '1', '2', '2', '0.00', '1276.00', '2022-03-11 15:07:05');
INSERT INTO `osms_orders` VALUES ('1502208333070712834', '1', '60', '1', '1', '1', '0.00', '638.00', '2022-03-11 17:02:31');
INSERT INTO `osms_orders` VALUES ('1502208333112655874', '1', '61', '1', '1', '1', '0.00', '638.00', '2022-03-11 17:02:31');
INSERT INTO `osms_orders` VALUES ('1502284755682955266', '1', '60', '1', '2', '1', '0.00', '319.00', '2022-03-11 22:06:11');
INSERT INTO `osms_orders` VALUES ('1502292654782124033', '1', '60', '1', '1', '1', '0.00', '638.00', '2022-03-11 22:37:35');
INSERT INTO `osms_orders` VALUES ('1502292654782124034', '1', '61', '1', '1', '1', '0.00', '638.00', '2022-03-11 22:37:35');
INSERT INTO `osms_orders` VALUES ('1502327658027327489', '1', '60', '1', '1', '1', '0.01', '2955.68', '2022-03-12 00:56:39');
INSERT INTO `osms_orders` VALUES ('1502327658039910402', '1', '75', '1', '1', '2', '0.01', '2955.68', '2022-03-12 00:56:39');
INSERT INTO `osms_orders` VALUES ('1502327658052493313', '1', '76', '1', '1', '1', '0.01', '2955.68', '2022-03-12 00:56:39');
INSERT INTO `osms_orders` VALUES ('1502329094282866689', '1', '75', '1', '1', '1', '0.00', '1448.44', '2022-03-12 01:02:23');
INSERT INTO `osms_orders` VALUES ('1502329094282866690', '1', '78', '1', '1', '1', '0.00', '1448.44', '2022-03-12 01:02:23');
INSERT INTO `osms_orders` VALUES ('1502467955593535490', '1', '60', '1', '1', '1', '0.00', '1193.64', '2022-03-12 10:14:08');
INSERT INTO `osms_orders` VALUES ('1502467955685810178', '1', '75', '1', '1', '1', '0.00', '1193.64', '2022-03-12 10:14:08');
INSERT INTO `osms_orders` VALUES ('1502480856475746305', '3', '54', '1', '1', '1', '3.73', '118.35', '2022-03-12 11:05:26');
INSERT INTO `osms_orders` VALUES ('1502481208151359490', '3', '17', '1', '1', '2', '0.00', '1799.28', '2022-03-12 11:06:49');
INSERT INTO `osms_orders` VALUES ('1502481208163942402', '3', '18', '1', '1', '2', '0.00', '1799.28', '2022-03-12 11:06:49');
INSERT INTO `osms_orders` VALUES ('1502482810966884353', '1', '63', '1', '1', '1', '156.07', '296.88', '2022-03-12 11:13:12');
INSERT INTO `osms_orders` VALUES ('1502483338794876929', '1', '63', '1', '1', '1', '156.07', '296.88', '2022-03-12 11:15:17');
INSERT INTO `osms_orders` VALUES ('1502483577501106178', '1', '63', '1', '1', '1', '156.07', '296.88', '2022-03-12 11:16:14');
INSERT INTO `osms_orders` VALUES ('1502485464749162498', '2', '63', '1', '2', '1', '156.07', '296.88', '2022-03-12 11:23:44');
INSERT INTO `osms_orders` VALUES ('1502489495529766913', '2', '60', '1', '2', '1', '0.01', '312.62', '2022-03-12 11:39:45');
INSERT INTO `osms_orders` VALUES ('1502504014251474946', '2', '60', '1', '4', '2', '156.06', '638.00', '2022-03-12 12:37:27');
INSERT INTO `osms_orders` VALUES ('1502504014259863554', '2', '62', '1', '2', '2', '156.06', '1547.36', '2022-03-12 12:37:27');
INSERT INTO `osms_orders` VALUES ('1502504014259863555', '2', '63', '1', '2', '1', '156.06', '1547.36', '2022-03-12 12:37:27');
INSERT INTO `osms_orders` VALUES ('1502507057231646722', '1', '83', '1', '2', '1', '749.84', '577.38', '2022-03-12 12:49:31');
INSERT INTO `osms_orders` VALUES ('1502527442285748225', '2', '32', '1', '2', '1', '5.57', '266.99', '2022-03-12 14:10:32');
INSERT INTO `osms_orders` VALUES ('1502536893654773762', '2', '60', '1', '2', '2', '156.06', '922.12', '2022-03-12 14:48:04');
INSERT INTO `osms_orders` VALUES ('1502536893776408577', '2', '64', '1', '2', '1', '156.06', '922.12', '2022-03-12 14:48:04');

-- ----------------------------
-- Table structure for osms_payinfo
-- ----------------------------
DROP TABLE IF EXISTS `osms_payinfo`;
CREATE TABLE `osms_payinfo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `status` bigint DEFAULT NULL,
  `postage` decimal(10,2) DEFAULT NULL,
  `payment` decimal(10,2) DEFAULT NULL,
  `platform` int DEFAULT NULL,
  `paymenttype` bigint DEFAULT NULL,
  `paymenttime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_Reference_18` (`order_id`) USING BTREE,
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`order_id`) REFERENCES `osms_orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='支付信息表';

-- ----------------------------
-- Records of osms_payinfo
-- ----------------------------

-- ----------------------------
-- Table structure for osms_product
-- ----------------------------
DROP TABLE IF EXISTS `osms_product`;
CREATE TABLE `osms_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `seller_id` bigint DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `describ` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` bigint DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_osms_product_brand` (`brand_id`) USING BTREE,
  KEY `FK_osms_product_category` (`category_id`) USING BTREE,
  KEY `FK_osms_seller_product` (`seller_id`) USING BTREE,
  CONSTRAINT `FK_osms_product_brand` FOREIGN KEY (`brand_id`) REFERENCES `osms_brand` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_osms_product_category` FOREIGN KEY (`category_id`) REFERENCES `osms_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_osms_seller_product` FOREIGN KEY (`seller_id`) REFERENCES `osms_seller` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商品信息表';

-- ----------------------------
-- Records of osms_product
-- ----------------------------
INSERT INTO `osms_product` VALUES ('1', '1', '1', '1', 'HLA海澜之家简约净色短袖T恤男22夏季新品新疆棉衣服时尚圆领百搭短T男上衣', '', '6', '2022-03-08 10:06:35', '2022-03-08 10:06:38', '0');
INSERT INTO `osms_product` VALUES ('2', '1', '1', '1', 'HLA海澜之家印花短袖t恤男22夏季新款经典圆领冰爽棉凉感黑科技T恤', null, '6', '2022-03-08 10:24:36', '2022-03-08 10:24:40', '0');
INSERT INTO `osms_product` VALUES ('3', '2', '1', '1', '【周杰伦同款】夹克男2021秋季新款时尚百搭防风两面穿茄克男沉稳大方衣服外套男', null, '6', '2022-03-08 10:32:26', '2022-03-08 10:32:29', '0');
INSERT INTO `osms_product` VALUES ('4', '2', '2', '1', '【防风抗皱】与狼共舞连帽夹克男可拆卸帽春秋季新款男士外套', null, '6', '2022-03-08 10:32:57', '2022-03-08 10:32:59', '0');
INSERT INTO `osms_product` VALUES ('5', '2', '2', '1', '与狼共舞夹克男2022春季卡其色潮流工装翻领休闲复合麂皮绒男士外套男装', null, '6', '2022-03-08 10:36:22', '2022-03-08 10:36:24', '0');
INSERT INTO `osms_product` VALUES ('6', '1', '2', '1', '【金丝刺绣】与狼共舞夹克男2022春季新款休闲男装上衣棒球领潮流修身外套男士', null, '6', '2022-03-08 10:38:03', '2022-03-08 10:38:06', '0');
INSERT INTO `osms_product` VALUES ('7', '2', '3', '1', '森马外套男春秋同款浅色宽松型工装服潮流装休闲牛仔衣潮牌夹克衣服潮流男装', null, '6', '2022-03-08 10:43:29', '2022-03-08 10:43:34', '0');
INSERT INTO `osms_product` VALUES ('8', '2', '3', '1', '森马官方旗舰浅蓝色牛仔外套男美式hiphop工装夹克国潮痞帅水洗做旧褂子上衣服男装联名', null, '6', '2022-03-08 10:46:18', '2022-03-08 10:46:21', '0');
INSERT INTO `osms_product` VALUES ('9', '1', '4', '1', 'JackJones杰克琼斯春季男士潮流日常百搭字母街头个性棒球外', null, '6', '2022-03-08 11:06:29', '2022-03-08 11:06:34', '0');
INSERT INTO `osms_product` VALUES ('10', '2', '4', '1', 'JackJones杰克琼斯夏男士OVERSIZED印花刺绣潮纯棉短袖T恤', null, '6', '2022-03-08 11:10:18', '2022-03-08 11:10:23', '0');
INSERT INTO `osms_product` VALUES ('11', '1', '4', '1', 'JackJones杰克琼斯秋季男士装直筒棉弹复古水洗牛仔长裤', null, '6', '2022-03-08 11:13:26', '2022-03-08 11:13:29', '0');
INSERT INTO `osms_product` VALUES ('12', '1', '5', '1', 'FILA 斐乐男女通用WHITE LINE系列官方情侣款套头卫衣2021秋季新款男女同款休闲运动卫衣', null, '6', '2022-03-08 11:20:52', '2022-03-08 11:20:54', '0');
INSERT INTO `osms_product` VALUES ('13', '2', '5', '1', 'FILA斐乐官方女子连衣裙2021春季新款针织显瘦优雅连衣裙', null, '6', '2022-03-08 11:25:01', '2022-03-08 11:25:06', '0');
INSERT INTO `osms_product` VALUES ('14', '2', '5', '1', '【倪妮同款】斐乐官方女子半身裙2022春季新款商务修身百褶半身长裙打底裙子', null, '6', '2022-03-08 11:26:32', '2022-03-08 11:26:33', '0');
INSERT INTO `osms_product` VALUES ('15', '3', '6', '2', '华为智选手机 优畅享20 5G手机 （碎屏险） 优畅享20', null, '6', '2022-03-08 11:40:32', '2022-03-08 11:40:38', '0');
INSERT INTO `osms_product` VALUES ('16', '4', '6', '2', '【送66W闪充】华为P50 Pro 新品手机华为麒麟芯片 8+256GB', null, '6', '2022-03-08 11:44:27', '2022-03-08 11:44:30', '0');
INSERT INTO `osms_product` VALUES ('17', '5', '6', '2', 'HUAWEI nova 9 4G全网通 120Hz 后置5000万超感知影像 搭载HarmonyOS 2', null, '6', '2022-03-08 14:07:18', '2022-03-08 14:07:19', '0');
INSERT INTO `osms_product` VALUES ('18', '4', '6', '2', '华为 Mate40 Pro 5G 手机【支持鸿蒙HarmonyOs】', null, '6', '2022-03-08 14:11:31', '2022-03-08 14:11:33', '0');
INSERT INTO `osms_product` VALUES ('19', '5', '6', '2', '华为 HUAWEI Mate 40 RS 保时捷设计麒麟9000芯片 超感知徕卡电影五摄', null, '6', '2022-03-08 14:17:17', '2022-03-08 14:17:19', '0');
INSERT INTO `osms_product` VALUES ('20', '3', '7', '2', '【现货速发】小米12 5G新品手机 骁龙8 Gen1 黑色【官方标配】', null, '6', '2022-03-08 14:23:08', '2022-03-08 14:23:10', '0');
INSERT INTO `osms_product` VALUES ('21', '5', '7', '2', 'Redmi K40 骁龙870 三星AMOLED 120Hz高刷直屏 4800万高清三摄 12GB+256GB 幻境 游戏电竞5G手机', null, '6', '2022-03-08 14:37:36', '2022-03-08 14:37:38', '0');
INSERT INTO `osms_product` VALUES ('22', '4', '7', '2', 'Redmi K50 电竞版 全新骁龙8 双VC液冷散热 OLED柔性直屏', null, '6', '2022-03-08 14:41:10', '2022-03-08 14:41:11', '0');
INSERT INTO `osms_product` VALUES ('23', '3', '7', '2', '小米11 Pro 5G 骁龙888 2K AMOLED四曲面柔性屏 67W无线闪充 3D玻璃工艺', null, '6', '2022-03-08 14:44:35', '2022-03-08 14:44:39', '0');
INSERT INTO `osms_product` VALUES ('24', '5', '8', '2', 'OPPO Find X5 Pro 全新骁龙8 自研影像芯片 哈苏影像 5000万双主摄 120Hz 80W超级闪充 5G手机', null, '6', '2022-03-08 14:49:08', '2022-03-08 14:49:11', '0');
INSERT INTO `osms_product` VALUES ('25', '4', '8', '2', 'OPPO Reno7手机5G全网通英雄联盟限定版Reno7Pro', null, '6', '2022-03-08 14:52:21', '2022-03-08 14:52:23', '0');
INSERT INTO `osms_product` VALUES ('26', '5', '8', '2', 'OPPO K9s 骁龙778G 120Hz电竞屏 5000mAh长续航 5G手机', null, '6', '2022-03-08 14:56:08', '2022-03-08 14:56:10', '0');
INSERT INTO `osms_product` VALUES ('27', '6', '9', '3', '百草味 网红小零食手撕面包整箱办公室早餐饼干蛋糕软面包家庭装儿童代餐 华夫饼', null, '6', '2022-03-08 15:10:03', '2022-03-08 15:10:04', '0');
INSERT INTO `osms_product` VALUES ('28', '7', '9', '3', '百草味 手撕面包1000g/箱 原味整箱办公室早餐休闲食品面包点心', null, '6', '2022-03-08 15:12:14', '2022-03-08 15:12:17', '0');
INSERT INTO `osms_product` VALUES ('29', '8', '9', '3', '百草味 猪肉铺 休闲零食肉干肉脯靖江特产小吃', null, '6', '2022-03-08 15:16:18', '2022-03-08 15:16:20', '0');
INSERT INTO `osms_product` VALUES ('30', '8', '9', '3', '百草味 去骨凤爪135g 无骨鸡爪麻辣休闲小吃网红零食', null, '6', '2022-03-08 15:22:32', '2022-03-08 15:22:36', '0');
INSERT INTO `osms_product` VALUES ('31', '7', '9', '3', '百草味 鸭脖 休闲零食特产卤味熟食小吃', null, '6', '2022-03-08 15:26:39', '2022-03-08 15:26:41', '0');
INSERT INTO `osms_product` VALUES ('32', '6', '10', '3', '达利园早餐点心礼盒', null, '6', '2022-03-08 15:30:42', '2022-03-08 15:30:44', '0');
INSERT INTO `osms_product` VALUES ('33', '8', '10', '3', '达利 杂粮粥 年货礼盒', null, '6', '2022-03-08 15:34:29', '2022-03-08 15:34:31', '0');
INSERT INTO `osms_product` VALUES ('34', '7', '10', '3', '达利园青梅绿茶年货饮料官方自营酸甜果茶夏日清凉饮品瓶装整箱装', null, '6', '2022-03-08 15:38:31', '2022-03-08 15:38:33', '0');
INSERT INTO `osms_product` VALUES ('35', '6', '11', '3', '康师傅方便面 泡面袋面劲爽拉面家庭装休闲零食食品夜宵', null, '6', '2022-03-08 15:42:33', '2022-03-08 15:42:36', '0');
INSERT INTO `osms_product` VALUES ('36', '8', '11', '3', '康师傅 3+2苏打夹心饼干蛋糕营养早餐办公室休闲零食小吃', null, '6', '2022-03-08 15:47:31', '2022-03-08 15:47:33', '0');
INSERT INTO `osms_product` VALUES ('37', '7', '11', '3', '康师傅12种口味混合茶饮料果味饮料330ml', null, '6', '2022-03-08 15:50:02', '2022-03-08 15:50:04', '0');
INSERT INTO `osms_product` VALUES ('38', '6', '11', '3', '康师傅 蛋酥卷鸡蛋卷营养早餐办公室休闲零食小吃饼干蛋糕点心', null, '6', '2022-03-08 15:55:50', '2022-03-08 15:55:53', '0');
INSERT INTO `osms_product` VALUES ('39', '9', '12', '4', '得力（deli）挂钩强力粘胶贴墙壁壁挂承重厨房挂勾无痕粘贴门后免打孔粘钩', null, '6', '2022-03-08 16:00:02', '2022-03-08 16:00:04', '0');
INSERT INTO `osms_product` VALUES ('40', '10', '12', '4', '得力(deli)办公家用生活剪刀 1把', null, '6', '2022-03-08 16:02:20', '2022-03-08 16:02:22', '0');
INSERT INTO `osms_product` VALUES ('41', '9', '12', '4', '得力(deli)250ml(9盎司)加厚一次性杯子 水杯/纸杯', null, '6', '2022-03-08 16:06:32', '2022-03-08 16:06:34', '0');
INSERT INTO `osms_product` VALUES ('42', '10', '12', '4', '得力(deli)中性笔水笔签字笔', null, '6', '2022-03-08 16:09:08', '2022-03-08 16:09:09', '0');
INSERT INTO `osms_product` VALUES ('43', '9', '13', '4', '富居(FOOJO)牙刷杯 旅行便携漱口杯出差简约分隔洗漱杯', null, '6', '2022-03-08 16:17:17', '2022-03-08 16:17:20', '0');
INSERT INTO `osms_product` VALUES ('44', '10', '13', '4', '富居(FOOJO)化妆镜 LED智能美妆镜', null, '6', '2022-03-08 16:20:30', '2022-03-08 16:20:32', '0');
INSERT INTO `osms_product` VALUES ('45', '9', '13', '4', '富居(FOOJO)浴帘 浴室门帘 【防水加厚】隔断帘窗帘', null, '6', '2022-03-08 16:23:21', '2022-03-08 16:23:23', '0');
INSERT INTO `osms_product` VALUES ('46', '10', '14', '4', '网易严选 棉拖鞋 日式人字纹棉拖鞋 ', null, '6', '2022-03-08 16:27:52', '2022-03-08 16:27:53', '0');
INSERT INTO `osms_product` VALUES ('47', '9', '14', '4', ' 网易严选 床垫家纺 乳胶复合床褥床垫 泰国乳胶床垫', null, '6', '2022-03-08 16:39:18', '2022-03-08 16:39:20', '0');
INSERT INTO `osms_product` VALUES ('48', '10', '14', '4', '网易严选 玩趣彩虹四季拖鞋 家居浴室拖鞋', null, '6', '2022-03-08 16:57:46', '2022-03-08 16:57:48', '0');
INSERT INTO `osms_product` VALUES ('49', '1', '2', '4', 'test', '发的', '1', '2022-03-12 06:27:17', '2022-03-12 06:27:17', '1');
INSERT INTO `osms_product` VALUES ('50', '12', '1', '1', 'test', '很不错。', '1', '2022-03-12 06:53:21', '2022-03-12 06:53:21', '0');

-- ----------------------------
-- Table structure for osms_replies
-- ----------------------------
DROP TABLE IF EXISTS `osms_replies`;
CREATE TABLE `osms_replies` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `seller_id` bigint DEFAULT NULL,
  `messages_id` bigint DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_r_m` (`messages_id`),
  KEY `fk_r_s` (`seller_id`),
  CONSTRAINT `fk_r_m` FOREIGN KEY (`messages_id`) REFERENCES `osms_messages` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_r_s` FOREIGN KEY (`seller_id`) REFERENCES `osms_seller` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of osms_replies
-- ----------------------------
INSERT INTO `osms_replies` VALUES ('1', '1', '8', '你好。。。。。。。', '2022-03-12 06:57:20');

-- ----------------------------
-- Table structure for osms_seller
-- ----------------------------
DROP TABLE IF EXISTS `osms_seller`;
CREATE TABLE `osms_seller` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `onlinetime` datetime DEFAULT NULL,
  `status` bigint DEFAULT NULL,
  `identification` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `facephoto` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商家表';

-- ----------------------------
-- Records of osms_seller
-- ----------------------------
INSERT INTO `osms_seller` VALUES ('1', '张三的店', '123456', 'zs', '123456', '四川成都', '2022-03-11 10:41:18', '2', null, null);
INSERT INTO `osms_seller` VALUES ('2', null, null, null, null, null, null, null, null, null);
INSERT INTO `osms_seller` VALUES ('3', null, null, null, null, null, null, null, null, null);
INSERT INTO `osms_seller` VALUES ('4', null, null, null, null, null, null, null, null, null);
INSERT INTO `osms_seller` VALUES ('5', null, null, null, null, null, null, null, null, null);
INSERT INTO `osms_seller` VALUES ('6', null, null, null, null, null, null, null, null, null);
INSERT INTO `osms_seller` VALUES ('7', null, null, null, null, null, null, null, null, null);
INSERT INTO `osms_seller` VALUES ('8', null, null, null, null, null, null, null, null, null);
INSERT INTO `osms_seller` VALUES ('9', null, null, null, null, null, null, null, null, null);
INSERT INTO `osms_seller` VALUES ('10', null, null, null, null, null, null, null, null, null);
INSERT INTO `osms_seller` VALUES ('12', '123', '1', '1', '1', '12', '2022-03-12 06:51:06', '2', null, null);

-- ----------------------------
-- Table structure for osms_specification
-- ----------------------------
DROP TABLE IF EXISTS `osms_specification`;
CREATE TABLE `osms_specification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `size` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_osms_product_specification` (`product_id`) USING BTREE,
  CONSTRAINT `FK_osms_product_specification` FOREIGN KEY (`product_id`) REFERENCES `osms_product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商品规格';

-- ----------------------------
-- Records of osms_specification
-- ----------------------------
INSERT INTO `osms_specification` VALUES ('1', '中蓝', '1', 'https://img14.360buyimg.com/n0/jfs/t1/194137/21/19040/133701/611f73deE947df510/5b188640123ea14a.jpg', 'S', '68.00', '20');
INSERT INTO `osms_specification` VALUES ('2', '黑色', '1', 'https://img14.360buyimg.com/n0/jfs/t1/205086/21/2636/127522/61235695Ebdd8bb7e/75022d6c9ae2bbb4.jpg', 'S', '68.00', '20');
INSERT INTO `osms_specification` VALUES ('3', '米白', '1', 'https://img14.360buyimg.com/n0/jfs/t1/197175/7/4589/42051/6123569aE6214a8a5/af13f393c3786409.jpg', 'S', '68.00', '20');
INSERT INTO `osms_specification` VALUES ('4', '中蓝', '1', 'https://img14.360buyimg.com/n0/jfs/t1/194137/21/19040/133701/611f73deE947df510/5b188640123ea14a.jpg', 'M', '68.00', '20');
INSERT INTO `osms_specification` VALUES ('5', '黑色', '1', 'https://img14.360buyimg.com/n0/jfs/t1/205086/21/2636/127522/61235695Ebdd8bb7e/75022d6c9ae2bbb4.jpg', 'M', '68.00', '20');
INSERT INTO `osms_specification` VALUES ('6', '米白', '1', 'https://img14.360buyimg.com/n0/jfs/t1/197175/7/4589/42051/6123569aE6214a8a5/af13f393c3786409.jpg', 'M', '68.00', '20');
INSERT INTO `osms_specification` VALUES ('7', '中蓝', '1', 'https://img14.360buyimg.com/n0/jfs/t1/194137/21/19040/133701/611f73deE947df510/5b188640123ea14a.jpg', 'L', '68.00', '20');
INSERT INTO `osms_specification` VALUES ('8', '黑色', '1', 'https://img14.360buyimg.com/n0/jfs/t1/205086/21/2636/127522/61235695Ebdd8bb7e/75022d6c9ae2bbb4.jpg', 'L', '68.00', '20');
INSERT INTO `osms_specification` VALUES ('9', '米白', '1', 'https://img14.360buyimg.com/n0/jfs/t1/197175/7/4589/42051/6123569aE6214a8a5/af13f393c3786409.jpg', 'L', '68.00', '20');
INSERT INTO `osms_specification` VALUES ('10', '蓝灰镶拼', '2', 'https://img14.360buyimg.com/n0/jfs/t1/127362/11/22946/131708/6226aa98Ed40bb28c/842a2985375f7759.jpg', 'M', '98.00', '20');
INSERT INTO `osms_specification` VALUES ('11', '米白花纹', '2', 'https://img14.360buyimg.com/n0/jfs/t1/91511/32/24377/105625/6226aa9aE4e09c1fc/acec69409a04b01a.jpg', 'M', '98.00', '20');
INSERT INTO `osms_specification` VALUES ('12', '蓝灰镶拼', '2', 'https://img14.360buyimg.com/n0/jfs/t1/127362/11/22946/131708/6226aa98Ed40bb28c/842a2985375f7759.jpg', 'L', '98.00', '20');
INSERT INTO `osms_specification` VALUES ('13', '米白花纹', '2', 'https://img14.360buyimg.com/n0/jfs/t1/91511/32/24377/105625/6226aa9aE4e09c1fc/acec69409a04b01a.jpg', 'L', '98.00', '20');
INSERT INTO `osms_specification` VALUES ('14', '印花', '3', 'https://img14.360buyimg.com/n0/jfs/t1/158386/6/27513/142299/62025a29E7a1250f8/0a966098e52611f4.jpg', 'S', '498.00', '20');
INSERT INTO `osms_specification` VALUES ('15', '印花', '3', 'https://img14.360buyimg.com/n0/jfs/t1/158386/6/27513/142299/62025a29E7a1250f8/0a966098e52611f4.jpg', 'M', '498.00', '20');
INSERT INTO `osms_specification` VALUES ('16', '印花', '3', 'https://img14.360buyimg.com/n0/jfs/t1/158386/6/27513/142299/62025a29E7a1250f8/0a966098e52611f4.jpg', 'L', '498.00', '20');
INSERT INTO `osms_specification` VALUES ('17', '黑色', '4', 'https://img14.360buyimg.com/n0/jfs/t1/184796/4/18155/70493/61114669Ec5b069b5/249bdf8b02bcb9b9.jpg', 'S', '459.00', '18');
INSERT INTO `osms_specification` VALUES ('18', '蓝色', '4', 'https://img14.360buyimg.com/n0/jfs/t1/205986/10/8669/85805/6152767aEc78b45f6/677256cdf9fddc68.jpg', 'S', '459.00', '18');
INSERT INTO `osms_specification` VALUES ('19', '绿色', '4', 'https://img14.360buyimg.com/n0/jfs/t1/218909/34/13001/91175/621086ecE5bbf928f/b579443094c72811.jpg', 'S', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('20', '黑色', '4', 'https://img14.360buyimg.com/n0/jfs/t1/184796/4/18155/70493/61114669Ec5b069b5/249bdf8b02bcb9b9.jpg', 'M', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('21', '蓝色', '4', 'https://img14.360buyimg.com/n0/jfs/t1/205986/10/8669/85805/6152767aEc78b45f6/677256cdf9fddc68.jpg', 'M', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('22', '绿色', '4', 'https://img14.360buyimg.com/n0/jfs/t1/218909/34/13001/91175/621086ecE5bbf928f/b579443094c72811.jpg', 'M', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('23', '黑色', '4', 'https://img14.360buyimg.com/n0/jfs/t1/184796/4/18155/70493/61114669Ec5b069b5/249bdf8b02bcb9b9.jpg', 'L', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('24', '蓝色', '4', 'https://img14.360buyimg.com/n0/jfs/t1/205986/10/8669/85805/6152767aEc78b45f6/677256cdf9fddc68.jpg', 'L', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('25', '绿色', '4', 'https://img14.360buyimg.com/n0/jfs/t1/218909/34/13001/91175/621086ecE5bbf928f/b579443094c72811.jpg', 'L', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('26', '卡其', '5', 'https://img14.360buyimg.com/n0/jfs/t1/141162/3/4430/83981/5f27b70aE2bb6b548/0a76d5de548b44ad.jpg', 'S', '599.00', '20');
INSERT INTO `osms_specification` VALUES ('27', '卡其', '5', 'https://img14.360buyimg.com/n0/jfs/t1/141162/3/4430/83981/5f27b70aE2bb6b548/0a76d5de548b44ad.jpg', 'M', '599.00', '20');
INSERT INTO `osms_specification` VALUES ('28', '卡其', '5', 'https://img14.360buyimg.com/n0/jfs/t1/141162/3/4430/83981/5f27b70aE2bb6b548/0a76d5de548b44ad.jpg', 'L', '599.00', '20');
INSERT INTO `osms_specification` VALUES ('29', '黑色刺绣', '6', 'https://img14.360buyimg.com/n0/jfs/t1/200382/6/4521/92731/6123a2a6E52129bc6/02056748462df942.jpg', 'S', '419.00', '20');
INSERT INTO `osms_specification` VALUES ('30', '黑色刺绣', '6', 'https://img14.360buyimg.com/n0/jfs/t1/200382/6/4521/92731/6123a2a6E52129bc6/02056748462df942.jpg', 'M', '419.00', '20');
INSERT INTO `osms_specification` VALUES ('31', '黑色刺绣', '6', 'https://img14.360buyimg.com/n0/jfs/t1/200382/6/4521/92731/6123a2a6E52129bc6/02056748462df942.jpg', 'L', '419.00', '20');
INSERT INTO `osms_specification` VALUES ('32', '米色', '7', 'https://img14.360buyimg.com/n0/jfs/t1/101093/28/22422/389661/620b6594E96264e81/f97b974a6f2a1ec7.jpg', 'S', '278.00', '19');
INSERT INTO `osms_specification` VALUES ('33', '粉色', '7', 'https://img14.360buyimg.com/n0/jfs/t1/111488/31/21988/453873/620b6595E5ce8ac6e/67f9b95657a8ac80.jpg', 'S', '278.00', '20');
INSERT INTO `osms_specification` VALUES ('34', '黑色', '7', 'https://img14.360buyimg.com/n0/jfs/t1/173221/2/27721/403050/620b659dE8e8f1796/2ebde21ab7ca0c8e.jpg', 'S', '278.00', '20');
INSERT INTO `osms_specification` VALUES ('35', '米色', '7', 'https://img14.360buyimg.com/n0/jfs/t1/101093/28/22422/389661/620b6594E96264e81/f97b974a6f2a1ec7.jpg', 'M', '278.00', '20');
INSERT INTO `osms_specification` VALUES ('36', '粉色', '7', 'https://img14.360buyimg.com/n0/jfs/t1/111488/31/21988/453873/620b6595E5ce8ac6e/67f9b95657a8ac80.jpg', 'M', '278.00', '20');
INSERT INTO `osms_specification` VALUES ('37', '黑色', '7', 'https://img14.360buyimg.com/n0/jfs/t1/173221/2/27721/403050/620b659dE8e8f1796/2ebde21ab7ca0c8e.jpg', 'M', '278.00', '20');
INSERT INTO `osms_specification` VALUES ('38', '米色', '7', 'https://img14.360buyimg.com/n0/jfs/t1/101093/28/22422/389661/620b6594E96264e81/f97b974a6f2a1ec7.jpg', 'L', '278.00', '20');
INSERT INTO `osms_specification` VALUES ('39', '粉色', '7', 'https://img14.360buyimg.com/n0/jfs/t1/111488/31/21988/453873/620b6595E5ce8ac6e/67f9b95657a8ac80.jpg', 'L', '278.00', '20');
INSERT INTO `osms_specification` VALUES ('40', '黑色', '7', 'https://img14.360buyimg.com/n0/jfs/t1/173221/2/27721/403050/620b659dE8e8f1796/2ebde21ab7ca0c8e.jpg', 'L', '278.00', '20');
INSERT INTO `osms_specification` VALUES ('41', '黑色', '8', 'https://img14.360buyimg.com/n0/jfs/t1/114128/6/20052/125592/620a47a2E989727c2/4bf37772cbb5bcc4.jpg', 'M', '126.00', '20');
INSERT INTO `osms_specification` VALUES ('42', '蓝色', '8', 'https://img14.360buyimg.com/n0/jfs/t1/137392/8/21453/139118/620a47a2E10b4e960/167cd60e68f0164f.jpg', 'M', '126.00', '20');
INSERT INTO `osms_specification` VALUES ('44', '黑色', '8', 'https://img14.360buyimg.com/n0/jfs/t1/114128/6/20052/125592/620a47a2E989727c2/4bf37772cbb5bcc4.jpg', 'L', '126.00', '20');
INSERT INTO `osms_specification` VALUES ('45', '蓝色', '8', 'https://img14.360buyimg.com/n0/jfs/t1/137392/8/21453/139118/620a47a2E10b4e960/167cd60e68f0164f.jpg', 'L', '126.00', '20');
INSERT INTO `osms_specification` VALUES ('46', '黑色', '9', 'https://img14.360buyimg.com/n0/jfs/t1/95101/24/22737/173314/62262fe8Ea51a8bc0/4010e8229b367a5c.jpg', 'S', '499.00', '20');
INSERT INTO `osms_specification` VALUES ('47', '白色', '9', 'https://img14.360buyimg.com/n0/jfs/t1/117508/16/22128/187235/62262fe6Ebced4b14/4dc2d574755bfdb1.jpg', 'S', '499.00', '20');
INSERT INTO `osms_specification` VALUES ('49', '黑色', '9', 'https://img14.360buyimg.com/n0/jfs/t1/95101/24/22737/173314/62262fe8Ea51a8bc0/4010e8229b367a5c.jpg', 'M', '499.00', '20');
INSERT INTO `osms_specification` VALUES ('50', '白色', '9', 'https://img14.360buyimg.com/n0/jfs/t1/117508/16/22128/187235/62262fe6Ebced4b14/4dc2d574755bfdb1.jpg', 'M', '499.00', '20');
INSERT INTO `osms_specification` VALUES ('51', '黑色', '9', 'https://img14.360buyimg.com/n0/jfs/t1/95101/24/22737/173314/62262fe8Ea51a8bc0/4010e8229b367a5c.jpg', 'L', '499.00', '20');
INSERT INTO `osms_specification` VALUES ('52', '白色', '9', 'https://img14.360buyimg.com/n0/jfs/t1/117508/16/22128/187235/62262fe6Ebced4b14/4dc2d574755bfdb1.jpg', 'L', '499.00', '20');
INSERT INTO `osms_specification` VALUES ('53', '蓝色', '10', 'https://img14.360buyimg.com/n0/jfs/t1/223542/27/6483/193583/62262ee7Ee025ddeb/368014e148d4b6e6.jpg', 'S', '124.50', '20');
INSERT INTO `osms_specification` VALUES ('54', '白色', '10', 'https://img14.360buyimg.com/n0/jfs/t1/66415/30/16857/180516/62262ee5Ea638f5e0/b41bedd54d6a939b.jpg', 'S', '124.50', '19');
INSERT INTO `osms_specification` VALUES ('55', '蓝色', '10', 'https://img14.360buyimg.com/n0/jfs/t1/223542/27/6483/193583/62262ee7Ee025ddeb/368014e148d4b6e6.jpg', 'M', '124.50', '20');
INSERT INTO `osms_specification` VALUES ('56', '白色', '10', 'https://img14.360buyimg.com/n0/jfs/t1/66415/30/16857/180516/62262ee5Ea638f5e0/b41bedd54d6a939b.jpg', 'M', '124.50', '20');
INSERT INTO `osms_specification` VALUES ('57', '蓝色', '10', 'https://img14.360buyimg.com/n0/jfs/t1/223542/27/6483/193583/62262ee7Ee025ddeb/368014e148d4b6e6.jpg', 'L', '124.50', '20');
INSERT INTO `osms_specification` VALUES ('58', '白色', '10', 'https://img14.360buyimg.com/n0/jfs/t1/66415/30/16857/180516/62262ee5Ea638f5e0/b41bedd54d6a939b.jpg', 'L', '124.50', '20');
INSERT INTO `osms_specification` VALUES ('60', '深蓝', '11', 'https://img14.360buyimg.com/n0/jfs/t1/115003/31/21026/258197/62262f11Ea985f682/f81398a22689d797.jpg', 'S', '319.00', '2');
INSERT INTO `osms_specification` VALUES ('61', '深蓝', '11', 'https://img14.360buyimg.com/n0/jfs/t1/115003/31/21026/258197/62262f11Ea985f682/f81398a22689d797.jpg', 'M', '319.00', '15');
INSERT INTO `osms_specification` VALUES ('62', '深蓝', '11', 'https://img14.360buyimg.com/n0/jfs/t1/115003/31/21026/258197/62262f11Ea985f682/f81398a22689d797.jpg', 'L', '319.00', '18');
INSERT INTO `osms_specification` VALUES ('63', '白色', '12', 'https://img14.360buyimg.com/n0/jfs/t1/136271/2/23983/92463/621f67e2Ed864bc66/ba0e62f911ea8e8f.jpg', 'S', '459.00', '11');
INSERT INTO `osms_specification` VALUES ('64', '灰色', '12', 'https://img14.360buyimg.com/n0/jfs/t1/220983/39/13747/160958/621f67e2Ec8b17d97/58257aa00c0efa63.jpg', 'S', '459.00', '19');
INSERT INTO `osms_specification` VALUES ('65', '红色', '12', 'https://img14.360buyimg.com/n0/jfs/t1/215470/28/13606/123492/621f67e3E78871897/0a67131a47fd017f.jpg', 'S', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('69', '白色', '12', 'https://img14.360buyimg.com/n0/jfs/t1/136271/2/23983/92463/621f67e2Ed864bc66/ba0e62f911ea8e8f.jpg', 'M', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('70', '灰色', '12', 'https://img14.360buyimg.com/n0/jfs/t1/220983/39/13747/160958/621f67e2Ec8b17d97/58257aa00c0efa63.jpg', 'M', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('71', '红色', '12', 'https://img14.360buyimg.com/n0/jfs/t1/215470/28/13606/123492/621f67e3E78871897/0a67131a47fd017f.jpg', 'M', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('72', '白色', '12', 'https://img14.360buyimg.com/n0/jfs/t1/136271/2/23983/92463/621f67e2Ed864bc66/ba0e62f911ea8e8f.jpg', 'L', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('73', '灰色', '12', 'https://img14.360buyimg.com/n0/jfs/t1/220983/39/13747/160958/621f67e2Ec8b17d97/58257aa00c0efa63.jpg', 'L', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('74', '红色', '12', 'https://img14.360buyimg.com/n0/jfs/t1/215470/28/13606/123492/621f67e3E78871897/0a67131a47fd017f.jpg', 'L', '459.00', '20');
INSERT INTO `osms_specification` VALUES ('75', '蓝色', '13', 'https://img14.360buyimg.com/n0/jfs/t1/88452/18/20170/91007/61ed3fb5E99fb4641/d7e0989c3b6d7128.jpg', 'S', '899.00', '11');
INSERT INTO `osms_specification` VALUES ('76', '蓝色', '13', 'https://img14.360buyimg.com/n0/jfs/t1/88452/18/20170/91007/61ed3fb5E99fb4641/d7e0989c3b6d7128.jpg', 'M', '899.00', '17');
INSERT INTO `osms_specification` VALUES ('77', '蓝色', '13', 'https://img14.360buyimg.com/n0/jfs/t1/88452/18/20170/91007/61ed3fb5E99fb4641/d7e0989c3b6d7128.jpg', 'L', '899.00', '20');
INSERT INTO `osms_specification` VALUES ('78', '黑色', '14', 'https://img14.360buyimg.com/n0/jfs/t1/146223/38/23313/189716/621e1f73E1035a8db/7fd5d9319c7fc353.jpg', 'S', '579.00', '17');
INSERT INTO `osms_specification` VALUES ('79', '黑色', '14', 'https://img14.360buyimg.com/n0/jfs/t1/146223/38/23313/189716/621e1f73E1035a8db/7fd5d9319c7fc353.jpg', 'M', '579.00', '20');
INSERT INTO `osms_specification` VALUES ('80', '黑色', '14', 'https://img14.360buyimg.com/n0/jfs/t1/146223/38/23313/189716/621e1f73E1035a8db/7fd5d9319c7fc353.jpg', 'L', '579.00', '20');
INSERT INTO `osms_specification` VALUES ('83', '亮黑', '15', 'https://img14.360buyimg.com/n0/jfs/t1/109183/2/24208/85166/621c2741Edf8d0c35/85a908118fae659a.jpg', '6+128', '1339.00', '19');
INSERT INTO `osms_specification` VALUES ('84', '樱雪晴空', '15', 'https://img14.360buyimg.com/n0/jfs/t1/149379/23/22699/85571/621c276bE35f29e80/502c6ce5fb9c255f.jpg', '6+128', '1299.00', '20');
INSERT INTO `osms_specification` VALUES ('85', '曜金黑', '16', 'https://img14.360buyimg.com/n0/jfs/t1/135030/25/24613/160800/621ec795E67e59f54/4fdac772639b6002.jpg', '8+256', '6588.00', '20');
INSERT INTO `osms_specification` VALUES ('86', '雪域白', '16', 'https://img14.360buyimg.com/n0/jfs/t1/128732/2/25335/158157/621ec798Eb0da821d/f462eb444ce56601.jpg', '8+256', '6688.00', '20');
INSERT INTO `osms_specification` VALUES ('87', '拂晓粉', '16', 'https://img14.360buyimg.com/n0/jfs/t1/92630/23/23348/160114/621ec79bE2471e16d/2ffa19fbd45e9a2f.jpg', '8+256', '7198.00', '20');
INSERT INTO `osms_specification` VALUES ('88', '可可查金', '16', 'https://img14.360buyimg.com/n0/jfs/t1/158653/23/28066/159778/621ec792E77e480f8/f3a6d98ae262b228.jpg', '8+256', '6588.00', '20');
INSERT INTO `osms_specification` VALUES ('89', '曜金黑', '16', 'https://img14.360buyimg.com/n0/jfs/t1/135030/25/24613/160800/621ec795E67e59f54/4fdac772639b6002.jpg', '8+512', '7488.00', '20');
INSERT INTO `osms_specification` VALUES ('90', '雪域白', '16', 'https://img14.360buyimg.com/n0/jfs/t1/128732/2/25335/158157/621ec798Eb0da821d/f462eb444ce56601.jpg', '8+512', '7488.00', '20');
INSERT INTO `osms_specification` VALUES ('91', '拂晓粉', '16', 'https://img14.360buyimg.com/n0/jfs/t1/92630/23/23348/160114/621ec79bE2471e16d/2ffa19fbd45e9a2f.jpg', '8+512', '8188.00', '20');
INSERT INTO `osms_specification` VALUES ('92', '可可查金', '16', 'https://img14.360buyimg.com/n0/jfs/t1/158653/23/28066/159778/621ec792E77e480f8/f3a6d98ae262b228.jpg', '8+512', '7488.00', '20');
INSERT INTO `osms_specification` VALUES ('93', '亮黑色', '17', 'https://img14.360buyimg.com/n0/jfs/t1/209823/35/5464/184159/61697197Ec2a48eec/e28a2833555b00a6.jpg', '8+128', '2589.00', '20');
INSERT INTO `osms_specification` VALUES ('94', '普罗旺斯', '17', 'https://img14.360buyimg.com/n0/jfs/t1/208795/33/5394/189188/61697126Ef35c9553/4ab32637f25bb916.jpg', '8+128', '2589.00', '20');
INSERT INTO `osms_specification` VALUES ('95', '绮境森林', '17', 'https://img14.360buyimg.com/n0/jfs/t1/201562/21/11681/214106/61697000E3f03d5b5/03866b116bc2dacb.jpg', '8+128', '2589.00', '20');
INSERT INTO `osms_specification` VALUES ('96', '亮黑色', '17', 'https://img14.360buyimg.com/n0/jfs/t1/209823/35/5464/184159/61697197Ec2a48eec/e28a2833555b00a6.jpg', '8+128', '2889.00', '20');
INSERT INTO `osms_specification` VALUES ('97', '普罗旺斯', '17', 'https://img14.360buyimg.com/n0/jfs/t1/208795/33/5394/189188/61697126Ef35c9553/4ab32637f25bb916.jpg', '8+128', '2889.00', '20');
INSERT INTO `osms_specification` VALUES ('98', '绮境森林', '17', 'https://img14.360buyimg.com/n0/jfs/t1/201562/21/11681/214106/61697000E3f03d5b5/03866b116bc2dacb.jpg', '8+128', '2889.00', '20');
INSERT INTO `osms_specification` VALUES ('99', '亮黑色', '18', 'https://img14.360buyimg.com/n0/jfs/t1/87418/10/24032/169044/6226e57cE93e70f55/09790d2a6c9941d6.jpg', '8+128', '6799.00', '20');
INSERT INTO `osms_specification` VALUES ('100', '釉白色', '18', 'https://img14.360buyimg.com/n0/jfs/t1/130179/14/23626/166163/6226e57dE5d969ab4/39f7520d9f76b695.jpg', '8+128', '6799.00', '20');
INSERT INTO `osms_specification` VALUES ('101', '秘银色', '18', 'https://img14.360buyimg.com/n0/jfs/t1/92626/26/24066/170476/6226e57aE1c038b16/12cb00c433b78746.jpg', '8+128', '6799.00', '20');
INSERT INTO `osms_specification` VALUES ('102', '亮黑色', '18', 'https://img14.360buyimg.com/n0/jfs/t1/87418/10/24032/169044/6226e57cE93e70f55/09790d2a6c9941d6.jpg', '8+256', '6978.00', '20');
INSERT INTO `osms_specification` VALUES ('103', '釉白色', '18', 'https://img14.360buyimg.com/n0/jfs/t1/130179/14/23626/166163/6226e57dE5d969ab4/39f7520d9f76b695.jpg', '8+256', '6978.00', '20');
INSERT INTO `osms_specification` VALUES ('104', '秘银色', '18', 'https://img14.360buyimg.com/n0/jfs/t1/92626/26/24066/170476/6226e57aE1c038b16/12cb00c433b78746.jpg', '8+256', '7289.00', '20');
INSERT INTO `osms_specification` VALUES ('105', '陶瓷黑', '19', 'https://img14.360buyimg.com/n0/jfs/t1/170247/17/12852/67106/604f1593E65e7d374/dc2e256bf9e1a020.jpg', '8+256', '10999.00', '20');
INSERT INTO `osms_specification` VALUES ('106', '陶瓷白', '19', 'https://img14.360buyimg.com/n0/jfs/t1/166766/33/13158/59549/604f1655E646e641d/394477abb2c521c7.jpg', '8+256', '10999.00', '20');
INSERT INTO `osms_specification` VALUES ('107', '陶瓷黑', '19', 'https://img14.360buyimg.com/n0/jfs/t1/170247/17/12852/67106/604f1593E65e7d374/dc2e256bf9e1a020.jpg', '12+512', '13999.00', '20');
INSERT INTO `osms_specification` VALUES ('108', '陶瓷白', '19', 'https://img14.360buyimg.com/n0/jfs/t1/166766/33/13158/59549/604f1655E646e641d/394477abb2c521c7.jpg', '12+512', '13999.00', '20');
INSERT INTO `osms_specification` VALUES ('109', '黑色', '20', 'https://img14.360buyimg.com/n0/jfs/t1/131748/1/23502/116692/620726d2E9cebf984/b05423ae0b574fcf.jpg', '8+128', '3699.00', '20');
INSERT INTO `osms_specification` VALUES ('110', '蓝色', '20', 'https://img14.360buyimg.com/n0/jfs/t1/172745/1/28471/120407/620726dcEc14dc8dd/c777a136d44817a9.jpg', '8+128', '3699.00', '20');
INSERT INTO `osms_specification` VALUES ('111', '紫色', '20', 'https://img14.360buyimg.com/n1/s546x546_jfs/t1/166476/21/24775/120037/620726dcEdd44129a/5c632b811e65c4fd.jpg', '8+128', '3699.00', '20');
INSERT INTO `osms_specification` VALUES ('112', '黑色', '20', 'https://img14.360buyimg.com/n0/jfs/t1/131748/1/23502/116692/620726d2E9cebf984/b05423ae0b574fcf.jpg', '8+256', '3999.00', '20');
INSERT INTO `osms_specification` VALUES ('113', '蓝色', '20', 'https://img14.360buyimg.com/n0/jfs/t1/172745/1/28471/120407/620726dcEc14dc8dd/c777a136d44817a9.jpg', '8+256', '3999.00', '20');
INSERT INTO `osms_specification` VALUES ('114', '紫色', '20', 'https://img14.360buyimg.com/n1/s546x546_jfs/t1/166476/21/24775/120037/620726dcEdd44129a/5c632b811e65c4fd.jpg', '8+256', '3999.00', '20');
INSERT INTO `osms_specification` VALUES ('115', '黑色', '20', 'https://img14.360buyimg.com/n0/jfs/t1/131748/1/23502/116692/620726d2E9cebf984/b05423ae0b574fcf.jpg', '12+256', '4399.00', '20');
INSERT INTO `osms_specification` VALUES ('116', '蓝色', '20', 'https://img14.360buyimg.com/n0/jfs/t1/172745/1/28471/120407/620726dcEc14dc8dd/c777a136d44817a9.jpg', '12+256', '4399.00', '20');
INSERT INTO `osms_specification` VALUES ('117', '紫色', '20', 'https://img14.360buyimg.com/n1/s546x546_jfs/t1/166476/21/24775/120037/620726dcEdd44129a/5c632b811e65c4fd.jpg', '12+256', '4399.00', '20');
INSERT INTO `osms_specification` VALUES ('118', '幻境', '21', 'https://img14.360buyimg.com/n0/jfs/t1/93240/4/23433/201797/621c4db7E2d6ef824/56be83182f11a6e3.jpg', '8+128', '1999.00', '20');
INSERT INTO `osms_specification` VALUES ('119', '亮黑', '21', 'https://img14.360buyimg.com/n0/jfs/t1/213512/6/13573/184281/621c4d7dE08ad7579/21995004c7241c9b.jpg', '8+128', '1999.00', '20');
INSERT INTO `osms_specification` VALUES ('120', '幻境', '21', 'https://img14.360buyimg.com/n0/jfs/t1/93240/4/23433/201797/621c4db7E2d6ef824/56be83182f11a6e3.jpg', '8+256', '2199.00', '20');
INSERT INTO `osms_specification` VALUES ('121', '亮黑', '21', 'https://img14.360buyimg.com/n0/jfs/t1/213512/6/13573/184281/621c4d7dE08ad7579/21995004c7241c9b.jpg', '8+256', '2199.00', '20');
INSERT INTO `osms_specification` VALUES ('122', '幻境', '21', 'https://img14.360buyimg.com/n0/jfs/t1/93240/4/23433/201797/621c4db7E2d6ef824/56be83182f11a6e3.jpg', '12+256', '2399.00', '20');
INSERT INTO `osms_specification` VALUES ('123', '亮黑', '21', 'https://img14.360buyimg.com/n0/jfs/t1/213512/6/13573/184281/621c4d7dE08ad7579/21995004c7241c9b.jpg', '12+256', '2399.00', '20');
INSERT INTO `osms_specification` VALUES ('124', '冰斩', '22', 'https://img14.360buyimg.com/n0/jfs/t1/105706/2/21455/257642/62256941Ed8ef90ff/15824a82b12d217f.jpg', '12+128', '3599.00', '20');
INSERT INTO `osms_specification` VALUES ('125', '暗影', '22', 'https://img14.360buyimg.com/n0/jfs/t1/110764/23/25110/240484/622569b6E72f02d9b/186991d9e2fabef9.jpg', '12+128', '3599.00', '20');
INSERT INTO `osms_specification` VALUES ('126', '银翼', '22', 'https://img14.360buyimg.com/n0/jfs/t1/144612/25/23542/249347/62256995Eb1b785ea/53492ce7fce60927.jpg', '12+128', '3599.00', '20');
INSERT INTO `osms_specification` VALUES ('127', '冰斩', '22', 'https://img14.360buyimg.com/n0/jfs/t1/105706/2/21455/257642/62256941Ed8ef90ff/15824a82b12d217f.jpg', '12+256', '3899.00', '20');
INSERT INTO `osms_specification` VALUES ('128', '暗影', '22', 'https://img14.360buyimg.com/n0/jfs/t1/110764/23/25110/240484/622569b6E72f02d9b/186991d9e2fabef9.jpg', '12+256', '3899.00', '20');
INSERT INTO `osms_specification` VALUES ('129', '银翼', '22', 'https://img14.360buyimg.com/n0/jfs/t1/144612/25/23542/249347/62256995Eb1b785ea/53492ce7fce60927.jpg', '12+256', '3899.00', '20');
INSERT INTO `osms_specification` VALUES ('130', '黑色', '23', 'https://img14.360buyimg.com/n0/jfs/t1/155911/32/18569/106971/6061aa9dE80ab4a75/322a43e898b50284.jpg', '8+128', '3999.00', '20');
INSERT INTO `osms_specification` VALUES ('131', '绿色', '23', 'https://img14.360buyimg.com/n0/jfs/t1/173463/11/1008/114330/6061ad87E5ec543a3/c5dcf7ec2096851a.jpg', '8+128', '3999.00', '20');
INSERT INTO `osms_specification` VALUES ('132', '紫色', '23', 'https://img14.360buyimg.com/n0/jfs/t1/163380/2/15014/114418/6061aea2Eb3459ae5/ed0160cb7afdfb1f.jpg', '8+128', '3999.00', '20');
INSERT INTO `osms_specification` VALUES ('133', '黑色', '23', 'https://img14.360buyimg.com/n0/jfs/t1/155911/32/18569/106971/6061aa9dE80ab4a75/322a43e898b50284.jpg', '8+256', '4299.00', '20');
INSERT INTO `osms_specification` VALUES ('134', '绿色', '23', 'https://img14.360buyimg.com/n0/jfs/t1/173463/11/1008/114330/6061ad87E5ec543a3/c5dcf7ec2096851a.jpg', '8+256', '4299.00', '20');
INSERT INTO `osms_specification` VALUES ('135', '紫色', '23', 'https://img14.360buyimg.com/n0/jfs/t1/163380/2/15014/114418/6061aea2Eb3459ae5/ed0160cb7afdfb1f.jpg', '8+256', '4299.00', '20');
INSERT INTO `osms_specification` VALUES ('136', '黑色', '23', 'https://img14.360buyimg.com/n0/jfs/t1/155911/32/18569/106971/6061aa9dE80ab4a75/322a43e898b50284.jpg', '12+256', '4699.00', '20');
INSERT INTO `osms_specification` VALUES ('137', '绿色', '23', 'https://img14.360buyimg.com/n0/jfs/t1/173463/11/1008/114330/6061ad87E5ec543a3/c5dcf7ec2096851a.jpg', '12+256', '4699.00', '20');
INSERT INTO `osms_specification` VALUES ('138', '紫色', '23', 'https://img14.360buyimg.com/n0/jfs/t1/163380/2/15014/114418/6061aea2Eb3459ae5/ed0160cb7afdfb1f.jpg', '12+256', '4699.00', '20');
INSERT INTO `osms_specification` VALUES ('139', '黑釉', '24', 'https://img14.360buyimg.com/n0/jfs/t1/115447/4/20921/448734/6225c32dE5a9de180/2b02036da6a5cb58.jpg', '12+256', '6299.00', '20');
INSERT INTO `osms_specification` VALUES ('140', '白瓷', '24', 'https://img14.360buyimg.com/n0/jfs/t1/95136/11/23873/106876/6226b702Ee0322db2/f408e5cd3471338b.jpg', '12+256', '6299.00', '20');
INSERT INTO `osms_specification` VALUES ('141', '黑釉', '24', 'https://img14.360buyimg.com/n0/jfs/t1/115447/4/20921/448734/6225c32dE5a9de180/2b02036da6a5cb58.jpg', '12+512', '6799.00', '20');
INSERT INTO `osms_specification` VALUES ('142', '白瓷', '24', 'https://img14.360buyimg.com/n0/jfs/t1/95136/11/23873/106876/6226b702Ee0322db2/f408e5cd3471338b.jpg', '12+512', '6799.00', '20');
INSERT INTO `osms_specification` VALUES ('143', '星语心愿', '25', 'https://img14.360buyimg.com/n0/jfs/t1/146696/21/21004/591276/620ccdf3Ea908e52d/5742b881d8d7f10b.jpg', '8+256', '3499.00', '20');
INSERT INTO `osms_specification` VALUES ('144', '暮雪金', '25', 'https://img14.360buyimg.com/n0/jfs/t1/218764/17/12487/538088/620ccdf3E25ece19d/e41bead9423587f3.jpg', '8+256', '3499.00', '20');
INSERT INTO `osms_specification` VALUES ('145', '星夜黑', '25', 'https://img14.360buyimg.com/n0/jfs/t1/133021/6/22353/514055/620ccdf3E2e9c9a47/55d711434eae68e0.jpg', '8+256', '3499.00', '20');
INSERT INTO `osms_specification` VALUES ('146', '星语心愿', '25', 'https://img14.360buyimg.com/n0/jfs/t1/146696/21/21004/591276/620ccdf3Ea908e52d/5742b881d8d7f10b.jpg', '12+256', '3799.00', '20');
INSERT INTO `osms_specification` VALUES ('147', '暮雪金', '25', 'https://img14.360buyimg.com/n0/jfs/t1/218764/17/12487/538088/620ccdf3E25ece19d/e41bead9423587f3.jpg', '12+256', '3799.00', '20');
INSERT INTO `osms_specification` VALUES ('148', '星夜黑', '25', 'https://img14.360buyimg.com/n0/jfs/t1/133021/6/22353/514055/620ccdf3E2e9c9a47/55d711434eae68e0.jpg', '12+256', '3799.00', '20');
INSERT INTO `osms_specification` VALUES ('149', '幻紫流沙', '26', 'https://img14.360buyimg.com/n0/jfs/t1/120716/5/24470/122435/6224b950E0ae6d700/c60f1ad6e3aa517d.jpg', '8+128', '1699.00', '20');
INSERT INTO `osms_specification` VALUES ('150', '黑曜武士', '26', 'https://img14.360buyimg.com/n0/jfs/t1/112806/12/26310/112713/6224b998E3b2d955f/a323606ddf51a3d0.jpg', '8+128', '1699.00', '20');
INSERT INTO `osms_specification` VALUES ('151', '霓幻银海', '26', 'https://img14.360buyimg.com/n0/jfs/t1/112806/12/26310/112713/6224b998E3b2d955f/a323606ddf51a3d0.jpg', '8+128', '1699.00', '20');
INSERT INTO `osms_specification` VALUES ('152', '幻紫流沙', '26', 'https://img14.360buyimg.com/n0/jfs/t1/120716/5/24470/122435/6224b950E0ae6d700/c60f1ad6e3aa517d.jpg', '8+128', '1999.00', '20');
INSERT INTO `osms_specification` VALUES ('153', '黑曜武士', '26', 'https://img14.360buyimg.com/n0/jfs/t1/112806/12/26310/112713/6224b998E3b2d955f/a323606ddf51a3d0.jpg', '8+128', '1999.00', '20');
INSERT INTO `osms_specification` VALUES ('154', '霓幻银海', '26', 'https://img14.360buyimg.com/n0/jfs/t1/112806/12/26310/112713/6224b998E3b2d955f/a323606ddf51a3d0.jpg', '8+128', '1999.00', '20');
INSERT INTO `osms_specification` VALUES ('155', '原味', '27', 'https://img14.360buyimg.com/n0/jfs/t1/100960/29/23996/247879/6226b364Eb3d9659c/7462b3ddba19bb34.jpg', '1000g', '29.90', '20');
INSERT INTO `osms_specification` VALUES ('156', '夹心', '27', 'https://img14.360buyimg.com/n0/jfs/t1/120254/3/20916/235387/621c7bd1Efbfc707d/839c91566468bbf6.jpg', '800g', '29.90', '20');
INSERT INTO `osms_specification` VALUES ('157', '手撕面包', '28', 'https://img14.360buyimg.com/n0/jfs/t1/108324/22/23414/290427/621c7ea0Ead44b1f6/518e9c329e05f8f1.jpg', '1000g', '22.90', '20');
INSERT INTO `osms_specification` VALUES ('158', '酵母软面包', '28', 'https://img14.360buyimg.com/n0/jfs/t1/134213/10/23316/238949/621c9f00E83520f30/18abe145fe73099e.jpg', '800g', '29.90', '20');
INSERT INTO `osms_specification` VALUES ('159', '肉松乳酪面包', '28', 'https://img14.360buyimg.com/n0/jfs/t1/132681/27/24955/227726/621c7c52Edf820ff4/d5e9fe141d40d417.jpg', '520g', '25.90', '20');
INSERT INTO `osms_specification` VALUES ('160', '白芝麻味', '29', 'https://img14.360buyimg.com/n0/jfs/t1/108549/35/23255/389223/62207a90E6053e799/38651231be9a0fa2.jpg', '100g', '32.70', '20');
INSERT INTO `osms_specification` VALUES ('161', '特色风味', '29', 'https://img14.360buyimg.com/n0/jfs/t1/97566/39/24535/381591/6221748dE2ee2a1ab/217b41a262b6b820.jpg', '500g', '59.90', '20');
INSERT INTO `osms_specification` VALUES ('162', '藤椒味', '30', 'https://img14.360buyimg.com/n0/jfs/t1/212916/32/13588/351652/62207aafE15aa054e/bf8d5126574d54a7.jpg', '135g', '53.70', '20');
INSERT INTO `osms_specification` VALUES ('163', '柠檬酸辣味', '30', 'https://img14.360buyimg.com/n0/jfs/t1/100272/29/22957/362149/622079eeE48f2c18e/622a6b87876d2abf.jpg', '135g', '53.70', '20');
INSERT INTO `osms_specification` VALUES ('164', '甜辣味', '31', 'https://img14.360buyimg.com/n0/jfs/t1/117226/21/20266/319108/62207a1dE2e2d6fca/912a2427cd46eff4.jpg', '170g', '30.80', '20');
INSERT INTO `osms_specification` VALUES ('165', '五香味', '31', 'https://img14.360buyimg.com/n0/jfs/t1/96520/2/24127/319108/62207a1fE956aa95e/02d5b8c7964eaceb.jpg', '170g', '30.80', '20');
INSERT INTO `osms_specification` VALUES ('166', '软面包', '32', 'https://img14.360buyimg.com/n0/jfs/t1/219116/36/13773/239873/62216175E1e7041e1/1196e085f5a7f780.jpg', '1500g', '36.90', '20');
INSERT INTO `osms_specification` VALUES ('167', '瑞士卷', '32', 'https://img14.360buyimg.com/n0/jfs/t1/209670/35/18283/188397/62188eb3E74cafe89/b03cb4aecadd4b78.jpg', '1500g', '39.90', '20');
INSERT INTO `osms_specification` VALUES ('168', '菠萝包', '32', 'https://img14.360buyimg.com/n0/jfs/t1/148001/27/22230/229359/62215e1dEe1d37012/b5a56db82bcf97ed.jpg', '600g', '29.90', '20');
INSERT INTO `osms_specification` VALUES ('169', '桂圆莲子八宝粥', '33', 'https://img14.360buyimg.com/n0/jfs/t1/209862/34/19077/205800/6225995cE7e933511/1a9e054c6305cb37.jpg', '360g', '34.90', '20');
INSERT INTO `osms_specification` VALUES ('170', '黑米紫薯八宝粥', '33', 'https://img14.360buyimg.com/n0/jfs/t1/187691/10/21557/182769/62259ab6Ec0ec3099/ec78182c14ee40b5.jpg', '280g', '32.80', '20');
INSERT INTO `osms_specification` VALUES ('171', '南瓜小米粥', '33', 'https://img14.360buyimg.com/n0/jfs/t1/93981/26/23278/171458/62259b1bEab56c5cf/75fe57545d8bb88b.jpg', '280g', '35.90', '20');
INSERT INTO `osms_specification` VALUES ('172', '原味', '34', 'https://img14.360buyimg.com/n0/jfs/t1/99529/17/22546/226195/621c973fEdc789d58/fe4f1091d8be8bc5.jpg', '500ml*5瓶', '15.90', '20');
INSERT INTO `osms_specification` VALUES ('173', '原味', '34', 'https://img14.360buyimg.com/n0/jfs/t1/97696/19/24585/215175/621c973eE6093a096/0cdcc190436e768a.jpg', '500ml*15瓶', '32.90', '20');
INSERT INTO `osms_specification` VALUES ('174', '红烧+香辣+酸菜', '35', 'https://img14.360buyimg.com/n0/jfs/t1/212738/28/13253/262693/62147e65Ecec850a3/3f3b0e7c0daab2ec.jpg', '各8袋', '35.90', '20');
INSERT INTO `osms_specification` VALUES ('175', '红烧+酸菜', '35', 'https://img14.360buyimg.com/n0/jfs/t1/212738/28/13253/262693/62147e65Ecec850a3/3f3b0e7c0daab2ec.jpg', '各12袋', '35.90', '20');
INSERT INTO `osms_specification` VALUES ('176', '红烧', '35', 'https://img14.360buyimg.com/n0/jfs/t1/199293/23/13799/154958/61711b94E39f5b238/ec86de33667f2255.jpg', '15袋', '26.00', '20');
INSERT INTO `osms_specification` VALUES ('177', '奶油', '36', 'https://img14.360buyimg.com/n0/jfs/t1/104298/26/23762/290124/620f5717Ecd9e1d73/0ff11d36fb48fdce.jpg', '500g', '26.00', '20');
INSERT INTO `osms_specification` VALUES ('178', '巧克力', '36', 'https://img14.360buyimg.com/n0/jfs/t1/217187/14/12738/320089/620f57afE4f5b00b0/4971b6f3febf0913.jpg', '500g', '26.00', '20');
INSERT INTO `osms_specification` VALUES ('179', '12种口味', '37', 'https://img14.360buyimg.com/n1/s546x546_jfs/t1/202487/9/15927/249235/61a03c21Eb0593e21/aa9d114da76ea09d.jpg', '各1瓶', '22.80', '20');
INSERT INTO `osms_specification` VALUES ('180', '茉莉柚茶+青梅绿茶+蜂蜜柚子', '37', 'https://img14.360buyimg.com/n0/jfs/t1/202487/9/15927/249235/61a03c21Eb0593e21/aa9d114da76ea09d.jpg', '各4瓶', '22.80', '20');
INSERT INTO `osms_specification` VALUES ('181', '冰红茶', '37', 'https://img14.360buyimg.com/n0/jfs/t1/119072/6/1093/120177/5e942126E09da6f27/c9a2190ece9010ab.jpg', '12瓶', '22.80', '20');
INSERT INTO `osms_specification` VALUES ('182', '奶油味', '38', 'https://img14.360buyimg.com/n0/jfs/t1/105322/19/18815/188248/5e97d122Ed7a72c03/494c66713acf53cf.jpg', '384g', '39.80', '20');
INSERT INTO `osms_specification` VALUES ('183', '芝麻味', '38', 'https://img14.360buyimg.com/n0/jfs/t1/86683/6/18886/192958/5e97cf70E12538822/a91ff6dad769c5cb.jpg', '384g', '39.80', '20');
INSERT INTO `osms_specification` VALUES ('184', '圆形', '39', 'https://img14.360buyimg.com/n0/jfs/t1/49029/33/14768/93166/5db94104E7751bd72/ce5a393c51529c4c.jpg', '3只', '8.90', '20');
INSERT INTO `osms_specification` VALUES ('185', '方形', '39', 'https://img14.360buyimg.com/n0/jfs/t1/54847/30/14877/88474/5db94150E65cb959f/b953406fe8d187d5.jpg', '3只', '8.90', '20');
INSERT INTO `osms_specification` VALUES ('186', '黑色', '40', 'https://img14.360buyimg.com/n0/jfs/t1/93034/11/9354/132278/5e0d9c0cE0cc81eeb/75104f3f0105c076.jpg', '170mm', '4.80', '20');
INSERT INTO `osms_specification` VALUES ('187', '红色', '40', 'https://img14.360buyimg.com/n0/jfs/t1/18709/10/1303/126807/5c10caa5E58d72b60/44d1d1823be0d019.jpg', '170mm', '4.80', '20');
INSERT INTO `osms_specification` VALUES ('188', '黑色', '40', 'https://img14.360buyimg.com/n0/jfs/t1/93034/11/9354/132278/5e0d9c0cE0cc81eeb/75104f3f0105c076.jpg', '170mm', '4.80', '20');
INSERT INTO `osms_specification` VALUES ('189', '红色', '40', 'https://img14.360buyimg.com/n0/jfs/t1/18709/10/1303/126807/5c10caa5E58d72b60/44d1d1823be0d019.jpg', '170mm', '4.80', '20');
INSERT INTO `osms_specification` VALUES ('190', '蓝色', '41', 'https://img14.360buyimg.com/n0/jfs/t21196/90/1916464187/375142/cfb1e837/5b3ed9abNda52b7dc.jpg', '100只', '19.00', '20');
INSERT INTO `osms_specification` VALUES ('191', '四色', '41', 'https://img14.360buyimg.com/n0/jfs/t1/107865/7/16425/407748/5eb23098E21e99115/8410ccad413883ec.jpg', '100只', '22.20', '20');
INSERT INTO `osms_specification` VALUES ('192', '0.38mm', '42', 'https://img14.360buyimg.com/n0/jfs/t1/115965/19/6292/235177/5eb8cc2cE22080168/1ffccd627f9566ad.jpg', '12只', '14.90', '20');
INSERT INTO `osms_specification` VALUES ('193', '0.5mm', '42', 'https://img14.360buyimg.com/n0/jfs/t1/209999/4/7639/529193/6180a4c6Ee99f5ecd/e660c94f982b70a1.jpg', '80只', '55.00', '20');
INSERT INTO `osms_specification` VALUES ('194', '拼色', '43', 'https://img14.360buyimg.com/n0/jfs/t1/175539/31/4273/175236/60780e7bEdab48599/c0f681915a664897.jpg', '1只', '9.90', '20');
INSERT INTO `osms_specification` VALUES ('195', '透明黑', '43', 'https://img14.360buyimg.com/n0/jfs/t1/162710/12/17363/148653/606d703cEdac5cc23/aefbf403bbe38535.jpg', '1只', '15.90', '20');
INSERT INTO `osms_specification` VALUES ('196', '天空蓝', '43', 'https://img14.360buyimg.com/n0/jfs/t1/148909/39/4013/61599/5f20dc77E24dbcdf1/7d1b8862752f6c84.jpg', '1只', '10.90', '20');
INSERT INTO `osms_specification` VALUES ('198', '方形', '44', 'https://img14.360buyimg.com/n0/jfs/t1/159494/34/13136/238944/60501a4aE0db98667/51358a215875ffee.jpg', '1个', '29.90', '20');
INSERT INTO `osms_specification` VALUES ('199', '圆形', '44', 'https://img14.360buyimg.com/n0/jfs/t1/175211/6/1111/168276/6062e8b5Ef70a6193/840d4ec0924e7b4c.jpg', '1个', '29.90', '20');
INSERT INTO `osms_specification` VALUES ('200', '方格', '45', 'https://img14.360buyimg.com/n0/jfs/t1/84816/37/5435/258229/5dedc34eE33f31c69/fd0d2534e01b0362.jpg', '150*180', '16.90', '20');
INSERT INTO `osms_specification` VALUES ('201', '卡通猫', '45', 'https://img14.360buyimg.com/n0/jfs/t1/124311/6/4106/438849/5ed9a050E50639add/2c047edffbdabfe2.jpg', '180*180', '21.70', '20');
INSERT INTO `osms_specification` VALUES ('202', '半拖', '46', 'https://img14.360buyimg.com/n0/jfs/t1/188760/22/19643/690186/61249e93E97e00ca5/88ea3ee6de9a9e11.jpg', '茶色', '22.00', '20');
INSERT INTO `osms_specification` VALUES ('203', '半拖', '46', 'https://img14.360buyimg.com/n0/jfs/t1/204870/14/1711/654258/611b66b7E5ccde9f1/b2cac37284705792.jpg', '浅灰', '22.00', '20');
INSERT INTO `osms_specification` VALUES ('204', '半拖', '46', 'https://img14.360buyimg.com/n0/jfs/t1/195745/31/18727/628191/611b691aE9a4b5082/d2a48b514482c3bb.jpg', '浅蓝', '22.00', '20');
INSERT INTO `osms_specification` VALUES ('205', '白色', '47', 'https://img14.360buyimg.com/n0/jfs/t1/215104/2/13730/438649/621f1dc6Ede194449/01482620d492ebc3.jpg', '90*200', '179.00', '20');
INSERT INTO `osms_specification` VALUES ('206', '白色', '47', 'https://img14.360buyimg.com/n0/jfs/t1/86599/32/23727/437832/621f1de2Ed3c30ada/28478d7da1367533.jpg', '120*200', '239.00', '20');
INSERT INTO `osms_specification` VALUES ('207', '白色', '47', 'https://img14.360buyimg.com/n0/jfs/t1/101867/28/24885/442239/621f1e03Ef6aac9af/4afffe7e55d61285.jpg', '150*200', '299.00', '20');
INSERT INTO `osms_specification` VALUES ('208', '水泥灰', '48', 'https://img14.360buyimg.com/n0/jfs/t19264/75/437854441/345202/d7f4fb81/5a794e31Nd2c613b8.jpg', '1双', '22.90', '20');
INSERT INTO `osms_specification` VALUES ('209', '夜色黑', '48', 'https://img14.360buyimg.com/n0/jfs/t19264/75/437854441/345202/d7f4fb81/5a794e31Nd2c613b8.jpg', '1双', '22.90', '20');
INSERT INTO `osms_specification` VALUES ('210', '樱花粉', '48', 'https://img14.360buyimg.com/n0/jfs/t19264/75/437854441/345202/d7f4fb81/5a794e31Nd2c613b8.jpg', '1双', '22.90', '20');
INSERT INTO `osms_specification` VALUES ('211', '奶油黄', '48', 'https://img14.360buyimg.com/n0/jfs/t19264/75/437854441/345202/d7f4fb81/5a794e31Nd2c613b8.jpg', '1双', '22.90', '20');
INSERT INTO `osms_specification` VALUES ('212', '默认', '49', 'https://img14.360buyimg.com/n0/jfs/t1/127362/11/22946/131708/6226aa98Ed40bb28c/842a2985375f7759.jpg', '12', '21.00', '12');
INSERT INTO `osms_specification` VALUES ('213', '123', '50', '', '10', '10.00', '10');

-- ----------------------------
-- Table structure for osms_user
-- ----------------------------
DROP TABLE IF EXISTS `osms_user`;
CREATE TABLE `osms_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `headimage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role` bigint(20) unsigned zerofill DEFAULT NULL,
  `score` bigint DEFAULT NULL,
  `level` bigint DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `is_deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

-- ----------------------------
-- Records of osms_user
-- ----------------------------
INSERT INTO `osms_user` VALUES ('1', '李四', 'e10adc3949ba59abbe56e057f20f883e', '小李', '1', '666', '77', './image/202203/-9316e163ca214138a894e98ae6c6247a.jpg', '00000000000000000001', '9951', '2', '2022-03-02 12:58:18', '2022-03-12 12:11:19', '0');
INSERT INTO `osms_user` VALUES ('3', 'lgl', '202cb962ac59075b964b07152d234b70', 'll', null, '15883320854', '1585155465@qq.com', null, '00000000000000000001', '0', '0', '2022-03-12 14:59:58', '2022-03-12 15:01:06', '0');
