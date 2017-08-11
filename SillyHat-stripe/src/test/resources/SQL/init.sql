create user sillyhat_springmvc identified by 'sillyhat_springmvc_pw';

create database sillyhat_springmvc_db;

grant all on sillyhat_springmvc_db.* to sillyhat_springmvc;


DROP TABLE IF EXISTS `T_BASE_USER`;
CREATE TABLE `T_BASE_USER` (
`ID`  INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
`MOZAT_ID`  VARCHAR(100) NOT NULL COMMENT '账号' ,
`CUSTOMER_ID`  VARCHAR(100) NOT NULL COMMENT 'CUSTOMER_ID' ,
PRIMARY KEY (`ID`),
INDEX `IDX_BASE_USER_MOZAT_ID` (`MOZAT_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='用户表';


DROP TABLE IF EXISTS `T_BASE_USER_CARD`;
CREATE TABLE `T_BASE_USER_CARD` (
`ID`  INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
`USER_ID` INT(10) UNSIGNED DEFAULT NULL COMMENT '用户ID',
`CARD_ID`  VARCHAR(100) COMMENT 'CARD ID' ,
`CARD_NUMBER`  VARCHAR(4) NOT NULL COMMENT '卡号后四位' ,
PRIMARY KEY (`ID`),
INDEX `IDX_BASE_USER_CARD_USER_ID` (`USER_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='关联银行卡';