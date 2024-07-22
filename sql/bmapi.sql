/* 初始化数据库 */

-- 创建库
create database if not exists bmapi;

-- 切换库
use bmapi;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for interface_info
-- ----------------------------
DROP TABLE IF EXISTS `interface_info`;
CREATE TABLE `interface_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口地址',
  `requestHeader` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求头',
  `responseHeader` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应头',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '接口状态（0-关闭，1-开启）',
  `method` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求类型',
  `userId` bigint(20) NOT NULL COMMENT '创建人',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
  `requestParams` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '接口信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interface_info
-- ----------------------------
INSERT INTO `interface_info` VALUES (1, '测试接口', '测试接口', 'http://localhost:8111/api/name/json', '{\n  \"Content-Type\": \"application/json\"\n}', '{\n  \"Content-Type\": \"application/json\"\n}', 1, 'POST', 1808803559295180802, '2024-07-08 13:01:56', '2024-07-09 00:09:16', 0, '[\n  {\n    \"userName\": \"userName\",\n    \"type\":string\n  }\n]');
INSERT INTO `interface_info` VALUES (2, '陆弘文', '白志强', 'www.leslee-kuhn.net', '潘懿轩', '马鸿涛', 0, '陈峻熙', 3982575846, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (3, '毛建辉', '罗文', 'www.rosaria-kilback.io', '冯子默', '彭哲瀚', 0, '赵远航', 121776355, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (4, '彭雨泽', '蔡煜祺', 'www.norris-bergstrom.biz', '董思源', '田晓博', 0, '潘擎宇', 740, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (5, '傅志强', '陈梓晨', 'www.jordan-reinger.com', '金志强', '熊锦程', 0, '邓睿渊', 35542559, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (6, '吕黎昕', '孔越彬', 'www.fe-okon.info', '万伟宸', '林昊然', 0, '孟荣轩', 1445, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (7, '夏雪松', '许子骞', 'www.lashawna-legros.co', '蔡昊然', '胡鹏涛', 0, '钟立辉', 34075514, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (8, '严钰轩', '阎志泽', 'www.kay-funk.biz', '莫皓轩', '郭黎昕', 0, '龚天宇', 70956, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (9, '萧嘉懿', '曹熠彤', 'www.margarette-lindgren.biz', '田泽洋', '邓睿渊', 0, '梁志强', 98, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (10, '杜驰', '冯思源', 'www.vashti-auer.org', '黎健柏', '武博文', 0, '李伟宸', 9, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (11, '史金鑫', '蔡鹏涛', 'www.diann-keebler.org', '徐烨霖', '阎建辉', 0, '李烨伟', 125, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (12, '林炫明', '贾旭尧', 'www.dotty-kuvalis.io', '梁雨泽', '龙伟泽', 0, '许智渊', 79998, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (13, '何钰轩', '赖智宸', 'www.andy-adams.net', '崔思淼', '白鸿煊', 0, '邵振家', 7167482751, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (14, '魏志强', '于立诚', 'www.ione-aufderhar.biz', '朱懿轩', '万智渊', 0, '唐昊强', 741098, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (15, '严君浩', '金胤祥', 'www.duane-boyle.org', '雷昊焱', '侯思聪', 0, '郝思', 580514, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (16, '姚皓轩', '金鹏', 'www.lyda-klein.biz', '杜昊强', '邵志泽', 0, '冯鸿涛', 6546, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (17, '廖驰', '沈泽洋', 'www.consuelo-sipes.info', '彭昊然', '邓耀杰', 0, '周彬', 7761037, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (18, '赖智渊', '邓志泽', 'www.emerson-mann.co', '熊明哲', '贺哲瀚', 0, '田鹏', 381422, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (19, '许涛', '陆致远', 'www.vella-ankunding.name', '贾哲瀚', '莫昊焱', 0, '袁越彬', 4218096, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (20, '吕峻熙', '沈鹏飞', 'www.shari-reichel.org', '郭鸿煊', '覃烨霖', 0, '熊黎昕', 493, '2024-07-03 16:08:14', '2024-07-03 16:08:14', 0, '');
INSERT INTO `interface_info` VALUES (21, '22', '11', '11', '11', '11', 0, '11', 1808803559295180802, '2024-07-05 15:47:15', '2024-07-05 15:47:41', 1, '');
INSERT INTO `interface_info` VALUES (23, '许擎宇', '薛聪健', 'www.cary-king.net', '{\n  \"Content-Type\": \"application/json\"\n}', '{\n  \"Content-Type\": \"application/json\"\n}', 0, '石炫明', 9500534531, '2024-07-03 16:08:14', '2024-07-08 13:02:25', 0, '[\n  {\n    \"username\": \"username\",\n    \"type\":string\n  }\n]');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `unionId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信开放平台id',
  `mpOpenId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公众号openId',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `accessKey` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'api签名key',
  `accessSecret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'api签名密钥',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_unionId`(`unionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1809981689116569603 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1808803559295180802, 'baimao', 'b2da29e9d11a96e368c9ca52cc815218', NULL, NULL, 'baimao', NULL, NULL, 'admin', 'baimao', 'abcdefgh', '1970-01-01 00:00:00', '2024-07-08 00:05:51', 0);
INSERT INTO `user` VALUES (1809981689116569602, 'baimao2', 'b2da29e9d11a96e368c9ca52cc815218', NULL, NULL, NULL, NULL, NULL, 'user', '6d9935d220275e2671c43dab0c942048', 'b0633d66e3a4006542a223d24df6e105', '2024-07-08 00:03:57', '2024-07-08 00:03:57', 0);

-- ----------------------------
-- Table structure for user_interface_info
-- ----------------------------
DROP TABLE IF EXISTS `user_interface_info`;
CREATE TABLE `user_interface_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` bigint(20) NOT NULL COMMENT '调用用户 id',
  `interfaceInfoId` bigint(20) NOT NULL COMMENT '接口 id',
  `totalNum` int(11) NOT NULL DEFAULT 0 COMMENT '总调用次数',
  `leftNum` int(11) NOT NULL DEFAULT 0 COMMENT '剩余调用次数',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0-正常，1-禁用',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户调用接口关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_interface_info
-- ----------------------------
INSERT INTO `user_interface_info` VALUES (1, 1808803559295180802, 1, 24, 0, 0, '2024-07-09 15:27:26', '2024-07-20 22:34:42', 0);
INSERT INTO `user_interface_info` VALUES (2, 1808803559295180802, 2, 12, 1, 0, '2024-07-20 15:42:55', '2024-07-20 15:42:55', 0);
INSERT INTO `user_interface_info` VALUES (3, 1808803559295180802, 3, 22, 1, 0, '2024-07-20 15:42:55', '2024-07-20 22:30:34', 0);
INSERT INTO `user_interface_info` VALUES (4, 1808803559295180802, 4, 1, 1, 0, '2024-07-20 15:42:55', '2024-07-20 22:30:34', 0);
INSERT INTO `user_interface_info` VALUES (5, 1808803559295180802, 5, 2, 1, 0, '2024-07-20 15:42:55', '2024-07-20 22:30:34', 0);
INSERT INTO `user_interface_info` VALUES (6, 1808803559295180802, 6, 5, 1, 0, '2024-07-20 15:42:55', '2024-07-20 22:30:34', 0);

SET FOREIGN_KEY_CHECKS = 1;
