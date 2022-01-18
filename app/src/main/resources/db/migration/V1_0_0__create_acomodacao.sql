CREATE TABLE `acomodacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `capacidade` int(11) NOT NULL, 
  `descricao` longtext, 
  `nome` varchar(100) NOT NULL, 
  `preco` float NOT NULL, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
