CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `name` varchar(500) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `institution` varchar(500) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `classical_solved_count` int(11) DEFAULT NULL,
  `classical_score` double DEFAULT NULL,
  `challenge_solved_count` int(11) DEFAULT NULL,
  `challenge_score` double DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table contains the user detail from www.spoj.com';
