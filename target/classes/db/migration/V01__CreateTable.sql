-- write sql to create member table
CREATE TABLE member (
     id MEDIUMINT NOT NULL AUTO_INCREMENT,
     first_name CHAR(30) NOT NULL,
     last_name CHAR(30) NOT NULL,
     age INT NOT NULL,
     gender CHAR(6) NOT NULL,
     balance INT NOT NULL,
     PRIMARY KEY (id)
);