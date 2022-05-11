CREATE TABLE `book_authors` (
  `book_id` bigint NOT NULL,
  `author_id` bigint NOT NULL,
  KEY `author_id_fk` (`author_id`),
  KEY `book_id_fk` (`book_id`),
  CONSTRAINT `book_id_fk` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `author_id_fk` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`)
) ENGINE=InnoDB;