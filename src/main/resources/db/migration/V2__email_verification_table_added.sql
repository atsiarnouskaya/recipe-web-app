CREATE TABLE `email_verification` (
  `id` int NOT NULL AUTO_INCREMENT ,
  `user_id` int DEFAULT NULL,
  `email` varchar(80) DEFAULT NULL,
  `verification_code` varchar(80) DEFAULT NULL,
  `verification_code_expires_at` datetime DEFAULT NULL,
  `verification_code_attempts` int DEFAULT 0,
  `is_verified` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);