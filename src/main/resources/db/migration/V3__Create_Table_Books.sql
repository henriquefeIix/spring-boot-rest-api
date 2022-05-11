CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `isbn` varchar(13) NOT NULL,
  `number_copies` int NOT NULL,
  `publication_date` date NOT NULL,
  `synopsis` varchar(500),
  `title` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_isbn_uk` (`isbn`)
) ENGINE=InnoDB;