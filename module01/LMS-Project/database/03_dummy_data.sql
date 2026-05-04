-- 교수 더미데이터
INSERT INTO `교수`
(`professor_id`, `professor_name`, `professor_no`, `professor_address`, `professor_email`, `professor_phone`, `professor_pw`)
VALUES
('P001', '김민수', '700101-1234567', '서울시 광진구', 'minsu@univ.ac.kr', '010-1111-1111', 'pw1234'),
('P002', '이서연', '720305-2234567', '서울시 성동구', 'seoyeon@univ.ac.kr', '010-2222-2222', 'pw1234'),
('P003', '박준호', '750912-1234567', '서울시 동대문구', 'junho@univ.ac.kr', '010-3333-3333', 'pw1234');


-- 학생 더미데이터
INSERT INTO `학생`
(`student_id`, `student_name`, `student_no`, `student_address`, `student_email`, `student_phone`, `student_pw`, `professor_id`)
VALUES
('S001', '최지훈', '030101-3123456', '서울시 강남구', 'jhchoi@naver.com', '010-9001-0001', 'pw1234', 'P001'),
('S002', '한유진', '020202-4123456', '서울시 송파구', 'yjhan@naver.com', '010-9002-0002', 'pw1234', 'P001'),
('S003', '정다은', '010303-3123456', '서울시 마포구', 'dejeong@naver.com', '010-9003-0003', 'pw1234', 'P002'),
('S004', '오세훈', '000404-1123456', '서울시 서대문구', 'shoh@naver.com', '010-9004-0004', 'pw1234', 'P003'),
('S005', '윤하늘', '990505-2123456', '서울시 노원구', 'haneul@naver.com', '010-9005-0005', 'pw1234', 'P002');


-- 사용자 더미데이터
INSERT INTO `사용자`
(`user_id`, `user_name`, `student_id`, `professor_id`)
VALUES
('P001', '김민수', NULL, 'P001'),
('P002', '이서연', NULL, 'P002'),
('P003', '박준호', NULL, 'P003'),
('S001', '최지훈', 'S001', NULL),
('S002', '한유진', 'S002', NULL),
('S003', '정다은', 'S003', NULL),
('S004', '오세훈', 'S004', NULL),
('S005', '윤하늘', 'S005', NULL);


-- 강의 더미데이터
INSERT INTO `강의`
(`class_no`, `class_name`, `class_point`, `class_time`, `class_room`, `class_type`, `professor_id`, `class_task`, `class_capacity`)
VALUES
('C001', '자바프로그래밍', 3.0, '월1-2', 'A101', '전필', 'P001', '자바 콘솔 기반 LMS 기능 구현', 30),
('C002', '데이터베이스', 3.0, '화3-4', 'B201', '전필', 'P002', 'ERD 설계 및 SQL 실습 과제', 35),
('C003', '웹프로그래밍', 3.0, '수5-6', 'C301', '전선', 'P001', 'HTML/CSS 화면 구현 과제', 40),
('C004', '인공지능개론', 2.0, '목2-3', 'D401', '전선', 'P003', '머신러닝 모델 조사 보고서', 25),
('C005', '교양영어', 2.0, '금1-2', 'E101', '교양', 'P002', '영어 발표 자료 준비', 50);


-- 수강 더미데이터
INSERT INTO `수강`
(`student_id`, `class_no`, `enroll_date`, `score`, `status`)
VALUES
('S001', 'C001', '2026-03-02 09:00:00', 4.5, 1),
('S001', 'C002', '2026-03-02 09:10:00', 4.5, 1),
('S001', 'C004', '2026-03-02 09:20:00', 4.5, 1);


