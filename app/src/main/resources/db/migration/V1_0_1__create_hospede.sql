CREATE TABLE `hospede` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `celular` varchar(20) NOT NULL, 
  `dt_nascimento` date NOT NULL,
  `documento` varchar(20) NOT NULL, 
  `email` varchar(100) NOT NULL, 
  `nome` varchar(100) NOT NULL, 
  `telefone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_HOSPEDE_EMAIL` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
