CREATE TABLE `acompanhante_hospedagem` (
  `hospedagem_id` int(11) NOT NULL, 
  `acompanhante_id` int(11) NOT NULL, 
  KEY `FK_HOSPEDAGEM_ACOMPANHANTE` (`acompanhante_id`), 
  KEY `FK_ACOMPANHANTE_HOSPEDAGEM` (`hospedagem_id`), 
  CONSTRAINT `FK_ACOMPANHANTE_HOSPEDAGEM` FOREIGN KEY (`hospedagem_id`) REFERENCES `hospedagem` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_HOSPEDAGEM_ACOMPANHANTE` FOREIGN KEY (`acompanhante_id`) REFERENCES `acompanhante` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
