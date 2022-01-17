CREATE TABLE `acompanhante` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `dt_nascimento` date NOT NULL,
  `documento` varchar(20) NOT NULL, 
  `nome` varchar(100) NOT NULL, 
  `hospede_id` int(11) NOT NULL,
  PRIMARY KEY (`id`), 
  KEY `FK_ACOMPANHANTE_HOSPEDE` (`hospede_id`), 
  CONSTRAINT `FK_ACOMPANHANTE_HOSPEDE` FOREIGN KEY (`hospede_id`) REFERENCES `hospede` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;