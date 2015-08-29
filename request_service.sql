/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.5.41 : Database - petty_cash
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`petty_cash` /*!40100 DEFAULT CHARACTER SET utf8 */;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `privillages` int(11) DEFAULT NULL,
  `organization_domain` varchar(25) CHARACTER SET latin1 NOT NULL,
  `admin_id` varchar(36) NOT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`admin_id`),
  KEY `user_admin_fk_ref` (`user_id`),
  CONSTRAINT `user_admin_fk_ref` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`privillages`,`organization_domain`,`admin_id`,`user_id`) values (5,'addmaya.petty.com','1','1'),(5,'interpol.petty.com','3',NULL);

/*Table structure for table `admin_req_fk` */

DROP TABLE IF EXISTS `admin_req_fk`;

CREATE TABLE `admin_req_fk` (
  `admin_id` varchar(36) DEFAULT NULL,
  `request_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  KEY `admin_req_fk_ref` (`admin_id`),
  KEY `req_admin_fk_ref` (`request_id`),
  CONSTRAINT `admin_req_fk_ref` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `req_admin_fk_ref` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin_req_fk` */

/*Table structure for table `balance_edit_trail` */

DROP TABLE IF EXISTS `balance_edit_trail`;

CREATE TABLE `balance_edit_trail` (
  `edit_trail_id` varchar(36) NOT NULL,
  `modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` varchar(36) DEFAULT NULL,
  `original_amount` decimal(10,0) NOT NULL,
  `edit_amount` decimal(10,0) NOT NULL,
  `description` text,
  `petty_account_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`edit_trail_id`),
  KEY `pet_balanc_fk_ref` (`petty_account_id`),
  CONSTRAINT `pet_balanc_fk_ref` FOREIGN KEY (`petty_account_id`) REFERENCES `petty_cash_account` (`petty_account_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `balance_edit_trail` */

/*Table structure for table `organizations` */

DROP TABLE IF EXISTS `organizations`;

CREATE TABLE `organizations` (
  `organization_id` varchar(36) NOT NULL,
  `organization_name` varchar(50) NOT NULL,
  `company_logo` blob,
  `mou` text NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `approval_count` int(11) NOT NULL,
  `region` varchar(50) NOT NULL,
  `district` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `contact_phone` varchar(20) NOT NULL,
  `is_active` smallint(6) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `organizations` */

insert  into `organizations`(`organization_id`,`organization_name`,`company_logo`,`mou`,`created_by`,`created_on`,`approval_count`,`region`,`district`,`address`,`contact_phone`,`is_active`,`description`) values ('2','addmaya',NULL,'','admin','2015-08-27 14:00:25',1,'','','','',1,NULL),('3','interpol',NULL,'','','2015-08-27 15:37:42',2,'','','','',1,NULL);

/*Table structure for table `petty_cash_account` */

DROP TABLE IF EXISTS `petty_cash_account`;

CREATE TABLE `petty_cash_account` (
  `balance_amount` decimal(10,0) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(36) CHARACTER SET latin1 DEFAULT NULL,
  `description` text CHARACTER SET latin1,
  `account_name` varchar(30) CHARACTER SET latin1 DEFAULT NULL,
  `account_provider` int(11) DEFAULT '1',
  `organization_id` varchar(36) DEFAULT NULL,
  `petty_account_id` varchar(36) NOT NULL,
  PRIMARY KEY (`petty_account_id`),
  KEY `org_pt_fk_ref` (`organization_id`),
  CONSTRAINT `org_pt_fk_ref` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`organization_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `petty_cash_account` */

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `reason` varchar(60) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `approval_count` int(11) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `request_amount` decimal(10,0) DEFAULT NULL,
  `mod_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` varchar(36) DEFAULT NULL,
  `request_id` varchar(36) NOT NULL,
  `organization_id` varchar(36) NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `request_user_fk_ref` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `request` */

insert  into `request`(`reason`,`status`,`approval_count`,`created_on`,`request_amount`,`mod_date`,`user_id`,`request_id`,`organization_id`) values ('',NULL,NULL,'2015-08-27 15:43:28',NULL,'0000-00-00 00:00:00',NULL,'',''),('checking client',0,1,'2015-08-29 17:29:00',5000,'2015-08-29 17:29:00','2','0aae0174-e3f7-4970-9aaf-ec2abc54b552','3'),('travel',1,6,'2015-08-27 13:25:12',30000,'2015-08-27 13:25:12','1','1','2'),('travel',1,5,'2015-08-27 16:01:04',30000,'2015-08-27 16:01:03','3','3','3'),('travel to client',0,1,'2015-08-29 12:40:17',5000,'2015-08-29 12:40:17','2','605e26c5-a486-4e75-9445-b5f3b111a305','3'),('transport',5,1,'2015-08-27 13:22:11',30000,'2015-08-27 13:22:11','5','8d960194-307d-41ac-84d5-d3f1293919ee','3'),('travel to client',0,1,'2015-08-29 12:41:26',5000,'2015-08-29 12:41:26','2','90431887-c8ba-4a99-ac82-350bfe781e28','3'),('travel to client',0,1,'2015-08-28 21:05:04',5000,'2015-08-28 21:05:04','2','9e592633-f496-49e6-b5fa-c208cb9dc892','3'),('travel',0,1,'2015-08-27 13:24:48',30000,'2015-08-27 13:24:47','2','b1dc385b-c810-47f9-b972-50d9301b9bcf','3');

/*Table structure for table `trans_edit_trail` */

DROP TABLE IF EXISTS `trans_edit_trail`;

CREATE TABLE `trans_edit_trail` (
  `edit_trail_id` varchar(36) NOT NULL,
  `modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` varchar(36) NOT NULL,
  `original_amount` decimal(10,0) NOT NULL,
  `edit_amount` decimal(10,0) NOT NULL,
  `transaction_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`edit_trail_id`),
  KEY `tr_edit_fk_ref` (`transaction_id`),
  CONSTRAINT `tr_edit_fk_ref` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`transaction_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `trans_edit_trail` */

/*Table structure for table `transactions` */

DROP TABLE IF EXISTS `transactions`;

CREATE TABLE `transactions` (
  `transaction_id` varchar(36) NOT NULL,
  `provider_trans_id` varchar(36) NOT NULL,
  `reason` text,
  `is_credit` smallint(6) NOT NULL,
  `status` smallint(6) DEFAULT NULL,
  `amount` decimal(10,0) NOT NULL,
  `provider_account_id` varchar(36) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `organization_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `organization_trans_fk_ref` (`organization_id`),
  CONSTRAINT `organization_trans_fk_ref` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`organization_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `transactions` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `password` varchar(25) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `user_name` varchar(35) NOT NULL,
  `user_avatar` blob,
  `phone_number` varchar(20) NOT NULL,
  `date_of_birth` date NOT NULL,
  `is_admin` smallint(6) DEFAULT NULL,
  `created_by` varchar(35) NOT NULL,
  `account_status` smallint(6) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_mod_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `organization_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_unique` (`user_name`),
  KEY `user_organization_fk_ref` (`organization_id`),
  CONSTRAINT `user_organization_fk_ref` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`organization_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`password`,`first_name`,`last_name`,`middle_name`,`user_name`,`user_avatar`,`phone_number`,`date_of_birth`,`is_admin`,`created_by`,`account_status`,`created_on`,`last_mod_date`,`organization_id`,`user_id`) values ('lukwago','allan','lukwago',NULL,'allan',NULL,'256771849086','2015-08-20',1,'allan',1,'2015-08-27 14:01:36','0000-00-00 00:00:00','2','1'),('sentongo','alex','sentongo',NULL,'alex',NULL,'','2015-08-05',1,'alex',1,'2015-08-27 15:39:22','0000-00-00 00:00:00','3','2');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
