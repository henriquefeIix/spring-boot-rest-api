CREATE TABLE `loans` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `return_date` date DEFAULT NULL,
  `return_forecast` date NOT NULL,
  `loan_date` date NOT NULL,
  `book_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `loans_book_id_fk` (`book_id`),
  KEY `loans_user_id_fk` (`user_id`),
  CONSTRAINT `loans_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `loans_book_id_fk` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB;