DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
	`UserName` varchar(45) NOT NULL UNIQUE,
	`Password` varchar(45) NOT NULL,
	`Name` varchar(45) NOT NULL,
	`Access` varchar(7) NOT NULL
);
INSERT INTO `User` VALUES ('mjh4402','Computernerd357','Matthew Hoover','Admin');
INSERT INTO `User` VALUES ('mjh4403','Computernerd358','Matthew','Editor');
INSERT INTO `User` VALUES ('mjh4404','Computernerd359','Matt','General');