DROP SCHEMA IF EXISTS `recipes`;

CREATE SCHEMA `recipes`;
USE `recipes`;

--
-- Creating a user/roles tables for spring security
--
CREATE TABLE `user` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(80) NOT NULL,
    `enabled` tinyint NOT NULL,
    
    PRIMARY KEY (`id`)
)AUTO_INCREMENT=1;

CREATE TABLE `role` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `role` varchar(50) NOT NULL,
    
    PRIMARY KEY (`id`)
)AUTO_INCREMENT=1;

CREATE TABLE `users_roles` (
	`user_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    
    CONSTRAINT `FK_USERS` FOREIGN KEY (`user_id`)
    REFERENCES `user`(`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
	CONSTRAINT `FK_ROLES` FOREIGN KEY (`role_id`)
    REFERENCES `role`(`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    );

--
-- Creating a recipe related tables--
--

CREATE TABLE `ingredient_category` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `category_name` VARCHAR(50) DEFAULT NULL,
    `is_deleted` TINYINT DEFAULT false,
    PRIMARY KEY (`id`)
) AUTO_INCREMENT=1;

CREATE TABLE `ingredients` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (`id`),
    `category_id` INT DEFAULT NULL,
    `is_deleted` TINYINT DEFAULT false,
    CONSTRAINT `FK_CATEGORY` FOREIGN KEY (`category_id`)
		REFERENCES `ingredient_category` (`id`)
		ON DELETE NO ACTION ON UPDATE NO ACTION
) AUTO_INCREMENT=1;

CREATE TABLE `units` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
) AUTO_INCREMENT=1;
    
CREATE TABLE `videos` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `url` VARCHAR(500) DEFAULT NULL,
    `title` VARCHAR(100) DEFAULT NULL,
    `source` VARCHAR(100) DEFAULT NULL,
    `status` BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (`id`)
) AUTO_INCREMENT=1;

CREATE TABLE `recipes` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `author_id` INT NOT NULL,
    `title` VARCHAR(100) DEFAULT NULL,
    `short_description` TEXT DEFAULT NULL,
    `instructions` TEXT DEFAULT NULL,
    `video_id` INT DEFAULT NULL,
    `is_deleted` TINYINT DEFAULT false,
    PRIMARY KEY (`id`),
    KEY `FK_VIDEO_idx` (`video_id`),
    CONSTRAINT `FK_VIDEO` FOREIGN KEY (`video_id`)
		REFERENCES `videos`(`id`)
		ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `FK_USER` FOREIGN KEY (`author_id`)
		REFERENCES `user`(`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) AUTO_INCREMENT=1;
    
CREATE TABLE `recipes_ingredients` (
  `recipe_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  `amount` FLOAT DEFAULT NULL,
  `unit_id` INT DEFAULT NULL,

  PRIMARY KEY (`recipe_id`, `ingredient_id`),

  CONSTRAINT `FK_RECIPE` FOREIGN KEY (`recipe_id`) 
		REFERENCES `recipes` (`id`) 
		ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_INGREDIENT` FOREIGN KEY (`ingredient_id`) 
		REFERENCES `ingredients` (`id`) 
		ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_UNITS` FOREIGN KEY (`unit_id`)
		REFERENCES `units` (`id`)
		ON DELETE NO ACTION ON UPDATE NO ACTION
);



