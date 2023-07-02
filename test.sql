DROP TABLE IF EXISTS `a_stuanswer`;
CREATE TABLE `a_stuanswer` (
                                 `user_id` varchar(32) NOT NULL,
                                 `exam_entry_id` varchar(32) NOT NULL,
                                 `Answer_1` varchar(32),
                                 `Answer_2` varchar(32),
                                 `composition` varchar(1024),
                                 PRIMARY KEY (`user_id`,`exam_entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `a_questions`;
CREATE TABLE `a_questions` (
                                 `exam_entry_id` varchar(32) NOT NULL,
                                 `Question_id` int,
                                 `Question_type` int,
                                 `Question_content` varchar(1024),
                                 `choice1` varchar(1024),
                                 `choice2` varchar(1024),
                                 PRIMARY KEY (`exam_entry_id`,`Question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `a_questions` VALUES ('202010181505285583262110032020','1','1','这是什么课','软件工程','青年大学习');
INSERT INTO `a_questions` VALUES ('202010181505285583262110032020','2','1','老师是谁','徐老师','周杰伦');
INSERT INTO `a_questions` VALUES ('202010181505285583262110032020','3','2','课上学到了什么',NULL,NULL);