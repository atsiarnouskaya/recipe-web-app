CREATE TABLE ingredient_category
(
    id            INT AUTO_INCREMENT NOT NULL,
    category_name VARCHAR(50)        NULL,
    is_deleted    TINYINT DEFAULT 0  NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE ingredients
(
    id          INT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(50)        NULL,
    category_id INT                NULL,
    is_deleted  TINYINT DEFAULT 0  NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE recipes
(
    id                INT AUTO_INCREMENT NOT NULL,
    author_id         INT                NOT NULL,
    title             VARCHAR(100)       NULL,
    short_description LONGTEXT           NULL,
    instructions      LONGTEXT           NULL,
    video_id          INT                NULL,
    is_deleted        TINYINT DEFAULT 0  NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE recipes_ingredients
(
    recipe_id     INT   NOT NULL,
    ingredient_id INT   NOT NULL,
    amount        FLOAT NULL,
    unit_id       INT   NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (recipe_id, ingredient_id)
);

CREATE TABLE `role`
(
    id     INT AUTO_INCREMENT NOT NULL,
    `role` VARCHAR(50)        NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE units
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50)        NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE user
(
    id       INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(50)        NOT NULL,
    password VARCHAR(80)        NOT NULL,
    enabled  TINYINT            NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE users_fav_recipes
(
    recipe_id INT NOT NULL,
    user_id   INT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (recipe_id, user_id)
);

CREATE TABLE users_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, role_id)
);

CREATE TABLE videos
(
    id     INT AUTO_INCREMENT   NOT NULL,
    url    VARCHAR(500)         NULL,
    title  VARCHAR(100)         NULL,
    source VARCHAR(100)         NULL,
    status TINYINT(1) DEFAULT 0 NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

ALTER TABLE ingredients
    ADD CONSTRAINT FK_CATEGORY FOREIGN KEY (category_id) REFERENCES ingredient_category (id) ON DELETE NO ACTION;

CREATE INDEX FK_CATEGORY ON ingredients (category_id);

ALTER TABLE users_fav_recipes
    ADD CONSTRAINT FK_FAV_RECIPE FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE NO ACTION;

ALTER TABLE users_fav_recipes
    ADD CONSTRAINT FK_FAV_USER FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE NO ACTION;

CREATE INDEX FK_FAV_USER ON users_fav_recipes (user_id);

ALTER TABLE recipes_ingredients
    ADD CONSTRAINT FK_INGREDIENT FOREIGN KEY (ingredient_id) REFERENCES ingredients (id) ON DELETE NO ACTION;

CREATE INDEX FK_INGREDIENT ON recipes_ingredients (ingredient_id);

ALTER TABLE recipes_ingredients
    ADD CONSTRAINT FK_RECIPE FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE NO ACTION;

ALTER TABLE users_roles
    ADD CONSTRAINT FK_ROLES FOREIGN KEY (role_id) REFERENCES `role` (id) ON DELETE NO ACTION;

CREATE INDEX FK_ROLES ON users_roles (role_id);

ALTER TABLE recipes_ingredients
    ADD CONSTRAINT FK_UNITS FOREIGN KEY (unit_id) REFERENCES units (id) ON DELETE NO ACTION;

CREATE INDEX FK_UNITS ON recipes_ingredients (unit_id);

ALTER TABLE recipes
    ADD CONSTRAINT FK_USER FOREIGN KEY (author_id) REFERENCES user (id) ON DELETE NO ACTION;

CREATE INDEX FK_USER ON recipes (author_id);

ALTER TABLE users_roles
    ADD CONSTRAINT FK_USERS FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE NO ACTION;

ALTER TABLE recipes
    ADD CONSTRAINT FK_VIDEO FOREIGN KEY (video_id) REFERENCES videos (id) ON DELETE NO ACTION;

CREATE INDEX FK_VIDEO_idx ON recipes (video_id);