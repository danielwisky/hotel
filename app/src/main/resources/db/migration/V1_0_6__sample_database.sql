LOCK TABLES `acomodacao` WRITE;
INSERT INTO `acomodacao`
VALUES
    (
        1, 4, 'Quarto Duplo com Vista da Piscina', 'Quarto', 299
    );
UNLOCK TABLES;

LOCK TABLES `hospede` WRITE;
INSERT INTO `hospede`
VALUES
    (
        1, '(99) 9 9999-9999', '1918-07-04 00:00:00.000000', '999.999.999-99', 'steven.rogers@marvel.com', 'Steven Rogers', '(99) 9999-9999'
    ),
    (
        2, '(99) 9 9999-9999', '1970-05-29 00:00:00.000000', '999.999.999-99', 'thor@marvel.com', 'Thor', '(99) 9999-9999'
    ),
    (
        3, '(99) 9 9999-9999', '1990-10-14 00:00:00.000000', '999.999.999-99', 'strange@marvel.com', 'Doutor Stephen Vincent Strange', '(99) 9999-9999'
    ),
    (
        4, '(99) 9 9999-9999', '1990-10-14 00:00:00.000000', '999.999.999-99', 'peter.parker@marvel.com', 'Peter Parker', '(99) 9999-9999'
    ),
    (
        5, '(99) 9 9999-9999', '1970-05-29 00:00:00.000000', '999.999.999-99', 'tony.stark@marvel.com', 'Tony Stark', '(99) 9999-9999'
    );
UNLOCK TABLES;

LOCK TABLES `acompanhante` WRITE;
INSERT INTO `acompanhante`
VALUES
    (
        1, '1980-01-01 00:00:00.000000', '999.999.999-99', 'Pepper Potts', 5
    ),
    (
        2, '1990-10-14 00:00:00.000000', '999.999.999-99', 'Mary Jane Watson', 4
    ),
    (
        3, '1970-10-14 00:00:00.000000', '999.999.999-99', 'Wong', 3
    ),
    (
        4, '1970-05-29 00:00:00.000000', '999.999.999-99', 'Loki', 2
    ),
    (
        5, '1918-07-04 00:00:00.000000', '999.999.999-99', 'Peggy Carter', 1
    );
UNLOCK TABLES;

LOCK TABLES `endereco` WRITE;
INSERT INTO `endereco`
VALUES
    (
        1, 'Manhattan', '99999-999', 'Nova Iorque', 'Park Avenue', 'Torre Stark', 'Nova Iorque', '200', 5
    ),
    (
        2, 'Manhattan', '99999-999', 'Nova Iorque', 'Park Avenue', 'Torre Stark', 'Nova Iorque', '200', 1
    ),
    (
        3, 'Manhattan', '99999-999', 'Nova Iorque', 'Park Avenue', 'Torre Stark', 'Nova Iorque', '200', 2
    ),
    (
        4, 'Manhattan', '99999-999', 'Nova Iorque', 'Park Avenue', 'Torre Stark', 'Nova Iorque', '200', 3
    ),
    (
        5, 'Manhattan', '99999-999', 'Nova Iorque', 'Park Avenue', 'Torre Stark', 'Nova Iorque', '200', 4
    );
UNLOCK TABLES;

LOCK TABLES `hospedagem` WRITE;
INSERT INTO `hospedagem`
VALUES
    (
        1, '2020-10-10 14:00:00.000000', '2020-10-11 14:00:00.000000', '2020-10-10', '2020-10-11', 299, 1, 4, 'PAGO'
    );
UNLOCK TABLES;

LOCK TABLES `acompanhante_hospedagem` WRITE;
INSERT INTO `acompanhante_hospedagem`
VALUES
    (1, 2);
UNLOCK TABLES;
