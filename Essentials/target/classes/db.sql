CREATE TABLE Warps (
    name varchar(16) PRIMARY KEY NOT NULL,
    world varchar(16) NOT NULL,
    x double NOT NULL,
    y double NOT NULL,
    z double NOT NULL,
    yaw float NOT NULL,
    pitch float NOT NULL
);

CREATE TABLE Homes (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid varchar(128) NOT NULL,
    name varchar(16) NOT NULL,
    world varchar(16) NOT NULL,
    x double NOT NULL,
    y double NOT NULL,
    z double NOT NULL,
    yaw float NOT NULL,
    pitch float NOT NULL
);