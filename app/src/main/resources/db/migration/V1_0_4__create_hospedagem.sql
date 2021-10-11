CREATE TABLE `hospedagem` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `dt_check_in` datetime(6) DEFAULT NULL, 
  `dt_check_out` datetime(6) DEFAULT NULL, 
  `dt_entrada` date NOT NULL, 
  `dt_saida` date NOT NULL, 
  `valor` float NOT NULL, 
  `acomodacao_id` int(11) NOT NULL, 
  `hospede_id` int(11) NOT NULL, 
  `status` varchar(40) NOT NULL, 
  PRIMARY KEY (`id`), 
  KEY `FK_HOSPEDAGEM_ACOMODACAO` (`acomodacao_id`), 
  KEY `FK_HOSPEDAGEM_HOSPEDE` (`hospede_id`), 
  CONSTRAINT `FK_HOSPEDAGEM_ACOMODACAO` FOREIGN KEY (`acomodacao_id`) REFERENCES `acomodacao` (`id`), 
  CONSTRAINT `FK_HOSPEDAGEM_HOSPEDE` FOREIGN KEY (`hospede_id`) REFERENCES `hospede` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
