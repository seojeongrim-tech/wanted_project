DROP TABLE IF EXISTS 메시지;
DROP TABLE IF EXISTS 수강;
DROP TABLE IF EXISTS 강의;
DROP TABLE IF EXISTS 사용자;
DROP TABLE IF EXISTS 학생;
DROP TABLE IF EXISTS 교수;



CREATE TABLE `교수`
(
    `professor_id`      VARCHAR(10) NOT NULL COMMENT '교수번호',
    `professor_name`    VARCHAR(20) NOT NULL COMMENT '이름',
    `professor_no`      VARCHAR(15) NOT NULL COMMENT '주민등록번호',
    `professor_address` VARCHAR(30) NOT NULL COMMENT '주소',
    `professor_email`   VARCHAR(30) NOT NULL COMMENT '이메일',
    `professor_phone`   VARCHAR(15) NOT NULL COMMENT '전화번호',
    `professor_pw`      VARCHAR(255) NOT NULL COMMENT '비밀번호',
    PRIMARY KEY (`professor_id`)
) COMMENT='교수';

CREATE TABLE `강의`
(
    `class_no`    VARCHAR(10) NOT NULL COMMENT '강의번호',
    `class_name`    VARCHAR(20) NOT NULL COMMENT '강의명',
    `class_point`    FLOAT NOT NULL COMMENT '학점',
    `class_time`    VARCHAR(15) COMMENT '시간표',
    `class_room`    VARCHAR(10) NOT NULL COMMENT '강의실',
    `class_type`    VARCHAR(5) NOT NULL COMMENT '분류',
    `professor_id`    VARCHAR(10) NOT NULL COMMENT '교수번호',
    `class_task`    VARCHAR(6000) COMMENT '과제',
    `class_capacity`    FLOAT NOT NULL COMMENT '수강인원',
    PRIMARY KEY ( `class_no` ),
    CONSTRAINT `fk_class_professor`
        FOREIGN KEY (`professor_id`) REFERENCES `교수` (`professor_id`)
)
 COMMENT = '강의';

CREATE TABLE `학생`
(
    `student_id`        VARCHAR(10) NOT NULL COMMENT '학번',
    `student_name`      VARCHAR(20) NOT NULL COMMENT '이름',
    `student_no`        VARCHAR(15) NOT NULL COMMENT '주민등록번호',
    `student_address`   VARCHAR(50) NOT NULL COMMENT '주소',
    `student_email`     VARCHAR(30) NOT NULL COMMENT '이메일',
    `student_phone`     VARCHAR(15) NOT NULL COMMENT '전화번호',
    `student_pw`        VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `professor_id`      VARCHAR(10)  COMMENT '지도교수',
    PRIMARY KEY (`student_id`),
    CONSTRAINT `fk_student_professor`
        FOREIGN KEY (`professor_id`) REFERENCES `교수` (`professor_id`)
) COMMENT='학생';

CREATE TABLE `사용자`
(
    `user_id` VARCHAR(10) NOT NULL COMMENT '사용자',
    `user_name` VARCHAR(20) NOT NULL COMMENT '사용자이름',
    `student_id`    VARCHAR(10) COMMENT '학번',
    `professor_id`    VARCHAR(10) COMMENT '교수번호',
 PRIMARY KEY ( `user_id` ),
    CONSTRAINT `fk_student`
        FOREIGN KEY (`student_id`) REFERENCES `학생` (`student_id`),
    CONSTRAINT `fk_professor`
        FOREIGN KEY (`professor_id`) REFERENCES `교수` (`professor_id`)
) COMMENT = '사용자';

CREATE TABLE `수강`
(
    `student_id`    VARCHAR(10) NOT NULL COMMENT '학번',
    `class_no`      VARCHAR(10) NOT NULL COMMENT '강의번호',
    `enroll_date`   DATETIME COMMENT '수강신청일',
    `score`         DECIMAL(5,2) COMMENT '성적',
    `status`        Boolean COMMENT '수강상태',
    PRIMARY KEY (`student_id`, `class_no`),
    CONSTRAINT `fk_enroll_student`
        FOREIGN KEY (`student_id`) REFERENCES `학생` (`student_id`),
    CONSTRAINT `fk_enroll_class`
        FOREIGN KEY (`class_no`) REFERENCES `강의` (`class_no`)
) COMMENT='수강';


CREATE TABLE `메시지`
(
    `user_id` VARCHAR(10) NOT NULL COMMENT '사용자',
    `content`    VARCHAR(10000) COMMENT '메시지내용',
    `receiver_id`    VARCHAR(10) NOT NULL COMMENT '받는사람',
    `id` BIGINT NOT NULL COMMENT '메시지번호',
 PRIMARY KEY ( `id` ),
 CONSTRAINT `fk_user_id`
 FOREIGN KEY(`user_id`) REFERENCES `사용자`(`user_id`),
 CONSTRAINT `fk_receiver_id`
 FOREIGN KEY(`receiver_id`) REFERENCES `사용자`(`user_id`)
 ) COMMENT = '메시지';
