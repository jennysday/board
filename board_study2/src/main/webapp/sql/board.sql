CREATE TABLE `board2` (
  `num` int NOT NULL AUTO_INCREMENT COMMENT '게시물 번호',
  `title` varchar(240) NOT NULL COMMENT '제목',
  `content` text NOT NULL COMMENT '내용',
  `writer` varchar(80) NOT NULL COMMENT '작성자',
  `password` varchar(80) NOT NULL COMMENT '비밀번호',
  `file` varchar(80) DEFAULT NULL COMMENT '첨부파일',
  `hit` int DEFAULT '0' COMMENT '조회수',
  `write_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `write_ip` varchar(80) NOT NULL COMMENT '등록 아이피',
  `update_ip` varchar(80) DEFAULT NULL COMMENT '수정 아이피',
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;


CREATE TABLE `board2` (
  `num` int NOT NULL AUTO_INCREMENT COMMENT '게시물 번호',
  `title` varchar(240) NOT NULL COMMENT '제목',
  `content` text NOT NULL COMMENT '내용',
  `writer` varchar(80) NOT NULL COMMENT '작성자',
  `password` varchar(80) NOT NULL COMMENT '비밀번호',
  `file` varchar(80) DEFAULT NULL COMMENT '첨부파일',
  `hit` int DEFAULT '0' COMMENT '조회수',
  `write_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
  `write_ip` varchar(80) NOT NULL COMMENT '등록 아이피',
  `update_ip` varchar(80) DEFAULT NULL COMMENT '수정 아이피',
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `board2` (
  `num` int NOT NULL AUTO_INCREMENT COMMENT '게시물 번호',
  `title` varchar(240) NOT NULL COMMENT '제목',
  `content` text NOT NULL COMMENT '내용',
  `writer` varchar(80) NOT NULL COMMENT '작성자',
  `password` varchar(80) NOT NULL COMMENT '비밀번호',
  `file` varchar(80) DEFAULT NULL COMMENT '첨부파일',
  `hit` int DEFAULT '0' COMMENT '조회수',
  `write_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `write_ip` varchar(80) NOT NULL COMMENT '등록 아이피',
  `update_ip` varchar(80) DEFAULT NULL COMMENT '수정 아이피',
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;


ALTER TABLE board2 AUTO_INCREMENT=1;






















