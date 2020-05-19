CREATE DATABASE arena_rmg;
USE arena_rmg;

CREATE TABLE `esporte` (
	`id` INT AUTO_INCREMENT NOT NULL,
    `nome` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `usuario` (
	`id` INT AUTO_INCREMENT NOT NULL,
	`    ` INT NOT NULL,
    `id_esporte` INT DEFAULT NULL,
    `nome` VARCHAR(50) NOT NULL,
    `apelido` VARCHAR(50) DEFAULT NULL,
    `email` VARCHAR(50) NOT NULL,
    `contato` VARCHAR(50) NOT NULL,
    `senha` VARCHAR(50) NOT NULL,
    `token_senha` VARCHAR(50) NOT NULL,
    `token_expo` VARCHAR(50) DEFAULT NULL,
    `idade` INT NOT NULL,
    `creditos` INT DEFAULT 0,
    `ativo` INT DEFAULT 1,
    `aprovado` INT DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_esporte`) REFERENCES `esporte` (`id`)
);

CREATE TABLE `nivel` (
	`id` INT AUTO_INCREMENT NOT NULL,
    `nome` VARCHAR(50) NOT NULL,
    `graduacao` INT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `matricula` (
	`id` INT AUTO_INCREMENT NOT NULL,
    `id_usuario` INT NOT NULL,
    `id_esporte` INT NOT NULL,
    `id_nivel` INT NOT NULL,
    `data` DATETIME NOT NULL,
    `vencimento` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
    FOREIGN KEY (`id_esporte`) REFERENCES `esporte` (`id`),
    FOREIGN KEY (`id_nivel`) REFERENCES `nivel` (`id`)
);

CREATE TABLE `pagamento` (
	`id` INT AUTO_INCREMENT NOT NULL,
    `id_matricula` INT NOT NULL,
    `data` DATETIME NOT NULL,
    `valor` DOUBLE NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_matricula`) REFERENCES `matricula` (`id`)
);

CREATE TABLE `produto` (
	`id` INT AUTO_INCREMENT NOT NULL,
    `nome` VARCHAR(50) NOT NULL,
    `    ` VARCHAR(50) NOT NULL,
    `valor` DOUBLE NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `comanda` (
	`id` INT AUTO_INCREMENT NOT NULL,
    `id_usuario` INT NOT NULL,
    `data_criacao` DATETIME NOT NULL,
    `data_pagamento` DATETIME DEFAULT NULL,
    `paga` INT DEFAULT 0,
    `forma_pagamento` VARCHAR(50) NOT NULL,
	`valor_total` DOUBLE DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
);

CREATE TABLE `consumacao` (
	`id` INT AUTO_INCREMENT NOT NULL,
	`id_comanda` INT NOT NULL,
	`id_produto` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_comanda`) REFERENCES `comanda` (`id`),
    FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
);

CREATE TABLE `check_in` (
	`id` INT AUTO_INCREMENT NOT NULL,
    `horario_inicio` DATETIME NOT NULL,
    `horario_fim` DATETIME DEFAULT NULL,
	`id_usuario` INT DEFAULT NULL,
    `id_comanda` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
    FOREIGN KEY (`id_comanda`) REFERENCES `comanda` (`id`)
);

CREATE TABLE `feed` (
	`id` INT AUTO_INCREMENT NOT NULL,
    `titulo` VARCHAR(50) NOT NULL,
    `texto` VARCHAR(500) NOT NULL,
    `data` DATETIME NOT NULL,
    `imagem` LONGBLOB DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `funcionamento` (
	`id` INT AUTO_INCREMENT NOT NULL,
    `abertura` DATETIME NOT NULL,
    `fechamento` DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);