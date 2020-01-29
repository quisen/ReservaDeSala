CREATE DATABASE reserva_sala;
USE reserva_sala;

CREATE TABLE `organizacao` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `nome` VARCHAR(45) DEFAULT NULL,
 `id_organizacao_pai` int(11) DEFAULT NULL,
 `tipo_organizacao` char(1) DEFAULT 'M',
 `dominio` VARCHAR(64) DEFAULT NULL,
 `ativo` TINYINT(1) DEFAULT '1',
 `dataCriacao` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
 `dataAlteracao` timestamp NULL DEFAULT NULL,
 PRIMARY KEY (`id`)
);

CREATE TABLE `sala` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `id_organizacao` int(11) DEFAULT NULL,
 `nome` VARCHAR(45) DEFAULT NULL,
 `quantidadePessoasSentadas` int(11) DEFAULT NULL,
 `possuiMultimidia` TINYINT(1) DEFAULT '1',
 `possuiArcon` TINYINT(1) DEFAULT '1',
 `areaDaSala` decimal(9,2) DEFAULT NULL,
 `localizacao` VARCHAR(128) DEFAULT NULL,
 `latitude` double DEFAULT NULL,
 `longitude` double DEFAULT NULL,
 `ativo` TINYINT(1) DEFAULT '1',
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
 `ativo` TINYINT(1) DEFAULT '1',
 PRIMARY KEY (`id`),
 FOREIGN KEY (`id_sala`) REFERENCES `sala` (`id`),
 FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
);


INSERT INTO `reserva_sala`.`organizacao` (`id`, `nome`, `tipo_organizacao`, `dominio`, `ativo`, `dataCriacao`, `dataAlteracao`) VALUES ('1', 'Wise', 'M', 'wises.com.br', '1', '2020-01-28 21:10:00', '2020-01-28 21:10:00');
INSERT INTO `reserva_sala`.`usuario` (`id`, `id_organizacao`, `nome`, `email`, `senha`) VALUES ('1', '1', 'Rodrigo Quisen', 'rodrigo.quisen@wises.com.br', '123');
INSERT INTO `reserva_sala`.`sala` (`id`, `id_organizacao`, `nome`, `quantidadePessoasSentadas`, `possuiMultimidia`, `possuiArcon`, `areaDaSala`, `localizacao`, `latitude`, `longitude`, `ativo`, `dataCriacao`, `dataAlteracao`) VALUES ('1', '1', 'Sala A', '10', '1', '1', '20', '3 Andar', '-25.4520819', '-49.2640187', '1', '2020-01-28 21:15:00', '2020-01-28 21:15:00');
