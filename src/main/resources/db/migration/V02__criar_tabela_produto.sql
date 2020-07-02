CREATE TABLE `produto` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`nome` VARCHAR(100) NOT NULL,
	`descricao` VARCHAR(255) NOT NULL,
	`preco` DECIMAL(10,2) NOT NULL,
	`status` BOOLEAN NOT NULL,
	`origem` VARCHAR(20) NOT NULL,
	`codigo_categoria` BIGINT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`codigo_categoria`) REFERENCES `categoria` (`id`)
)
COLLATE='utf8_general_ci'
;
