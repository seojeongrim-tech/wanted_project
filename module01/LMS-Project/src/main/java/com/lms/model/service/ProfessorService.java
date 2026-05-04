package com.lms.model.service;
import com.lms.common.JDBCTemplate;
import com.lms.model.dao.CourseDAO;
import com.lms.model.dto.*;


import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ProfessorService {

    // ==============================================================
    // [교수 기능 1] 담당 과목 조회
    // ==============================================================
    public List<EnrollmentCourseDTO> selectCoursesByProfId(String profId) {
        Connection conn = JDBCTemplate.getConnection();


        CourseDAO courseDAO = new CourseDAO(conn);
        List<EnrollmentCourseDTO> courseList = courseDAO.selectCoursesByProfId(conn, profId);

        JDBCTemplate.close(conn);
        return courseList;
    }

    // ==============================================================
    // [교수 기능 2] 수강 학생 확인
    // ==============================================================
    public List<EnrollmentCourseDTO> selectStudentsByClassNo(String classNo) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO courseDAO = new CourseDAO(conn);

        List<EnrollmentCourseDTO> studentList = courseDAO.selectStudentsByClassNo(conn, classNo);

        JDBCTemplate.close(conn);
        return studentList;
    }

    // ==============================================================
    // [교수 기능 3] 과제 등록
    // ==============================================================
    public int updateClassTask(String classNo, String professorId, String classTask) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO courseDAO = new CourseDAO(conn);

        int result = courseDAO.updateClassTask(conn, classNo, professorId, classTask);

        if (result > 0) {
            JDBCTemplate.commit(conn);
        } else {
            JDBCTemplate.rollback(conn);
        }

        JDBCTemplate.close(conn);
        return result;
    }

    public int updateCourseInfo(EnrollmentCourseDTO course) {

        Connection conn = JDBCTemplate.getConnection();

        CourseDAO dao = new CourseDAO(conn);

        int result = dao.updateCourseInfo(conn, course);

        if (result > 0) {
            JDBCTemplate.commit(conn);
        } else {
            JDBCTemplate.rollback(conn);
        }

        JDBCTemplate.close(conn);

        return result;
    }




    // ==============================================================
    // [교수 기능 4] 성적 관리
    // ==============================================================
    public int updateStudentScore(String classNo, String studentId, double score) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO courseDAO = new CourseDAO(conn);

        int result = courseDAO.updateStudentScore(conn, classNo, studentId, score);

        if (result > 0) {
            JDBCTemplate.commit(conn);
        } else {
            JDBCTemplate.rollback(conn);
        }

        JDBCTemplate.close(conn);
        return result;
    }
    public int registerCourse(EnrollmentCourseDTO course) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO courseDAO = new CourseDAO(conn);

        // 1. 방어 로직
        boolean isConflict = courseDAO.checkTimeRoomConflict(conn, course.getClassTime(), course.getClassRoom());
        if(isConflict) {
            JDBCTemplate.close(conn);
            return -1; // -1을 리턴해서 View에게 '중복 에러'임을 알림!
        }

        // 2. PK 자동 채번: 가장 마지막 강의 번호 가져와서 +1 하기
        String lastClassNo = courseDAO.getLastClassNo(conn); // 예: "C104"
        String nextClassNo = generateNextClassNo(lastClassNo); // 예: "C104" -> "C105"

        course.setClassNo(nextClassNo);
        course.setClassTask(null); //

        int result = courseDAO.insertCourse(conn, course);

        if(result > 0) JDBCTemplate.commit(conn);
        else JDBCTemplate.rollback(conn);

        JDBCTemplate.close(conn);
        return result;
    }

    // "C104" 같은 문자를 받아서 "C105"로 만들어주는 마법의 헬퍼 메서드
    private String generateNextClassNo(String lastNo) {
        if(lastNo == null || lastNo.isEmpty()) return "C101"; // 데이터가 하나도 없을 때

        // "C104"에서 'C'를 떼어내고 "104"만 숫자로 바꾼 뒤 +1
        int numPart = Integer.parseInt(lastNo.substring(1)) + 1;
        return "C" + numPart; // 다시 'C'를 붙여서 리턴!
    }

    public int updateSingleInfo(String profId, String columnName, String newValue) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO dao = new CourseDAO(conn);

        int result = dao.updateSingleInfo(conn, profId, columnName, newValue);

        if(result > 0) JDBCTemplate.commit(conn);
        else JDBCTemplate.rollback(conn);

        JDBCTemplate.close(conn);
        return result;
    }



    public String findUserIdByProfId(String profId) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO dao = new CourseDAO(conn);
        String userId = dao.findUserIdByProfId(conn, profId);
        JDBCTemplate.close(conn);
        return userId;
    }

    public List<UserDTO> getAllUsers(String myUserId) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO dao = new CourseDAO(conn);
        List<UserDTO> list = dao.getAllUsers(conn, myUserId);
        JDBCTemplate.close(conn);
        return list;
    }

    public List<UserMessageDTO> getChatHistory(String myUserId, String targetUserId) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO dao = new CourseDAO(conn);
        List<UserMessageDTO> list = dao.getChatHistory(conn, myUserId, targetUserId);
        JDBCTemplate.close(conn);
        return list;
    }

    public int sendChatMessage(MessageDTO msg) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO dao = new CourseDAO(conn);
        int result = dao.sendChatMessage(conn, msg);
        if(result > 0) JDBCTemplate.commit(conn);
        else JDBCTemplate.rollback(conn);
        JDBCTemplate.close(conn);
        return result;
    }

    // 주소록 가져오기
    public List<StudentDTO> getAllMembers() {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO dao = new CourseDAO(conn);

        List<StudentDTO> list = dao.getAllMembers(conn);
        JDBCTemplate.close(conn);
        return list;
    }

    public int deleteCourse(String courseId) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO dao = new CourseDAO(conn);

        int result = dao.deleteCourse(conn, courseId);
        if(result > 0) JDBCTemplate.commit(conn);
        else JDBCTemplate.rollback(conn);

        JDBCTemplate.close(conn);
        return result;
    }




}

