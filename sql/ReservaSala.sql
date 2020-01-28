CREATE DATABASE reserva_sala;
USE reserva_sala;

CREATE TABLE `organizacao` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `nome` VARCHAR(45) DEFAULT NULL,
 `id_organizacao_pai` int(11) DEFAULT NULL,
 `tipo_organizacao` char(1) DEFAULT 'M',
 `dominio` VARCHAR(64) DEFAULT NULL,
 `ativo` tinyint(4) DEFAULT '1',
 `dataCriacao` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
 `dataAlteracao` timestamp NULL DEFAULT NULL,
 PRIMARY KEY (`id`)
);

CREATE TABLE `sala` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `id_organizacao` int(11) DEFAULT NULL,
 `nome` VARCHAR(45) DEFAULT NULL,
 `quantidadePessoasSentadas` int(11) DEFAULT NULL,
 `possuiMultimidia` tinyint(4) DEFAULT '1',
 `possuiArcon` tinyint(4) DEFAULT '1',
 `areaDaSala` decimal(9,2) DEFAULT NULL,
 `localizacao` VARCHAR(128) DEFAULT NULL,
 `latitude` double DEFAULT NULL,
 `longitude` double DEFAULT NULL,
 `ativo` tinyint(4) DEFAULT '1',
 `dataCriacao` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
 `dataAlteracao` timestamp NULL DEFAULT NULL,
 PRIMARY KEY (`id`),
 FOREIGN KEY (`id_organizacao`) REFERENCES `organizacao` (`id`)
);

CREATE TABLE `usuario` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `id_organizacao` int(11) DEFAULT NULL,
 `nome` VARCHAR(45) DEFAULT NULL,
 `email` VARCHAR(45) DEFAULT NULL,
 `senha` VARCHAR(256) DEFAULT NULL,
 PRIMARY KEY (`id`),
 FOREIGN KEY (`id_organizacao`) REFERENCES `organizacao` (`id`)
);

CREATE TABLE `alocacao_sala` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `id_sala` int(11) DEFAULT NULL,
 `id_usuario` int(11) DEFAULT NULL,
 `data_hora_inicio` DATETIME DEFAULT NULL,
 `data_hora_fim` DATETIME DEFAULT NULL,
 `descricao` VARCHAR(45) DEFAULT NULL,
 `dataCriacao` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
 `dataAlteracao` timestamp NULL DEFAULT NULL,
 `ativo` tinyint(4) DEFAULT '1',
 PRIMARY KEY (`id`),
 FOREIGN KEY (`id_sala`) REFERENCES `sala` (`id`),
 FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
);
