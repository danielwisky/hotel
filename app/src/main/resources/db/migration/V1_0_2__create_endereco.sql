CREATE TABLE `endereco` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `bairro` varchar(100) NOT NULL, 
  `cep` varchar(10) NOT NULL, 
  `cidade` varchar(100) NOT NULL,
  `logradouro` varchar(140) NOT NULL,
  `complemento` varchar(100) DEFAULT NULL,
  `estado` varchar(40) NOT NULL, 
  `numero` varchar(10) NOT NULL,
  `hospede_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ENDERECO_HOSPEDE` (`hospede_id`),
  CONSTRAINT `FK_ENDERECO_HOSPEDE` FOREIGN KEY (`hospede_id`) REFERENCES `hospede` (`id`) ON DELETE CASCADE
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
