CREATE TABLE `problem` (
  `code` varchar(45) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `solved_by_count` int(11) DEFAULT NULL,
  `accuracy` double DEFAULT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This contains information about the problems present on www.spoj.com';
