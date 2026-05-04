-- =========================================
-- 05_test_query.sql
-- =========================================

-- 1. 학생별 수강 목록 조회
SELECT
    s.student_id AS 학번,
    s.student_name AS 학생명,
    c.class_no AS 강의번호,
    c.class_name AS 강의명,
    e.enroll_date AS 수강신청일,
    e.score AS 성적,
    CASE
        WHEN e.status = 1 THEN '수강중'
        WHEN e.status = 0 THEN '중도포기'
        ELSE '상태없음'
    END AS 수강상태
FROM `수강` e
JOIN `학생` s
    ON e.student_id = s.student_id
JOIN `강의` c
    ON e.class_no = c.class_no
ORDER BY s.student_id, c.class_no;


-- 2. 교수별 강의 목록 조회
SELECT
    p.professor_id AS 교수번호,
    p.professor_name AS 교수명,
    c.class_no AS 강의번호,
    c.class_name AS 강의명,
    c.class_time AS 강의시간,
    c.class_room AS 강의실,
    c.class_type AS 강의구분
FROM `강의` c
JOIN `교수` p
    ON c.professor_id = p.professor_id
ORDER BY p.professor_id, c.class_no;


-- 3. 학생 + 지도교수 조회
-- 지도교수가 아직 배정되지 않은 학생도 조회되도록 LEFT JOIN 사용
SELECT
    s.student_id AS 학번,
    s.student_name AS 학생명,
    p.professor_id AS 지도교수번호,
    p.professor_name AS 지도교수명
FROM `학생` s
LEFT JOIN `교수` p
    ON s.professor_id = p.professor_id
ORDER BY s.student_id;


-- 4. 사용자 목록 조회
-- 학생 사용자 / 교수 사용자 구분 확인용
SELECT
    u.user_id AS 사용자ID,
    u.user_name AS 사용자명,
    CASE
        WHEN u.student_id IS NOT NULL THEN '학생'
        WHEN u.professor_id IS NOT NULL THEN '교수'
        ELSE '미분류'
    END AS 사용자구분,
    u.student_id AS 학번,
    u.professor_id AS 교수번호
FROM `사용자` u
ORDER BY u.user_id;


-- 5. 메시지 송신/수신 조회
SELECT
    m.id AS 메시지번호,
    sender.user_name AS 보낸사람,
    receiver.user_name AS 받는사람,
    m.content AS 메시지내용
FROM `메시지` m
JOIN `사용자` sender
    ON m.user_id = sender.user_id
JOIN `사용자` receiver
    ON m.receiver_id = receiver.user_id
ORDER BY m.id;


-- 6. 특정 학생의 수강 과목 수 조회
SELECT
    s.student_id AS 학번,
    s.student_name AS 학생명,
    COUNT(e.class_no) AS 수강과목수
FROM `학생` s
LEFT JOIN `수강` e
    ON s.student_id = e.student_id
GROUP BY s.student_id, s.student_name
ORDER BY s.student_id;


-- 7. 특정 강의의 수강생 목록 조회
SELECT
    c.class_no AS 강의번호,
    c.class_name AS 강의명,
    s.student_id AS 학번,
    s.student_name AS 학생명,
    e.enroll_date AS 수강신청일,
    e.score AS 성적
FROM `수강` e
JOIN `학생` s
    ON e.student_id = s.student_id
JOIN `강의` c
    ON e.class_no = c.class_no
ORDER BY c.class_no, s.student_id;


-- 8. 교수별 담당 강의 수 조회
SELECT
    p.professor_id AS 교수번호,
    p.professor_name AS 교수명,
    COUNT(c.class_no) AS 담당강의수
FROM `교수` p
LEFT JOIN `강의` c
    ON p.professor_id = c.professor_id
GROUP BY p.professor_id, p.professor_name
ORDER BY p.professor_id;