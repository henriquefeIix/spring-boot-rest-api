CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(50),
  `role` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_role_uk` (`role`)
) ENGINE=InnoDB;