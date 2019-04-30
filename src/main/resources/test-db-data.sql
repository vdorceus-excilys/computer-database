INSERT INTO `computer-database-db`.`company`
(`id`,`name`) VALUES (1,"APPLE"), (2,"DELL"), (3,"HP");
INSERT INTO `computer-database-db`.`computer`
(`id`,`name`,`introduced`,`discontinued`,`company_id`)
VALUES
(1,"MACBOOK",null,null,1),
(2,"iMAC",null,null,1),
(3,"MACBOOK PRO",null,null,1),
(4,"LATITUDE",null,null,2),
(5,"XPS",null,null,2),
(6,"ENVY",null,null,3);
