ALTER TABLE acomodacao ADD COLUMN `ativo` BOOLEAN DEFAULT true AFTER preco;
ALTER TABLE acomodacao ADD COLUMN `dt_atualizacao` datetime DEFAULT CURRENT_TIMESTAMP AFTER ativo;

ALTER TABLE hospede ADD COLUMN `ativo` BOOLEAN DEFAULT true AFTER telefone;
ALTER TABLE hospede ADD COLUMN `dt_atualizacao` datetime DEFAULT CURRENT_TIMESTAMP AFTER ativo;

ALTER TABLE acompanhante ADD COLUMN `ativo` BOOLEAN DEFAULT true AFTER hospede_id;
ALTER TABLE acompanhante ADD COLUMN `dt_atualizacao` datetime DEFAULT CURRENT_TIMESTAMP AFTER ativo;

ALTER TABLE hospedagem ADD COLUMN `dt_atualizacao` datetime DEFAULT CURRENT_TIMESTAMP AFTER status;