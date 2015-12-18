CREATE TABLE `user_problem_mapping` (
  `username` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  PRIMARY KEY (`username`,`code`),
  KEY `FK_code_problem_idx` (`code`),
  CONSTRAINT `FK_code_problem` FOREIGN KEY (`code`) REFERENCES `problem` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_username_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
