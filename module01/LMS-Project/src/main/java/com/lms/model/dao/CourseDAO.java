package com.lms.model.dao;
import com.lms.common.JDBCTemplate;
import com.lms.common.QueryUtil;
import com.lms.model.dto.EnrollmentCourseDTO;
import com.lms.model.dto.MessageDTO;
import com.lms.model.dto.StudentDTO;
import com.lms.model.dto.UserDTO;
import com.lms.model.dto.UserMessageDTO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDAO {

    private final Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }

    // 1. 강의실 & 시간표 중복 검사
    public boolean checkTimeRoomConflict(Connection con, String time, String room) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        boolean isConflict = false;

        String query = QueryUtil.getQuery("checkTimeRoomConflict");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, time);
            pstmt.setString(2, room);
            rset = pstmt.executeQuery();


            if (rset.next()) {
                int count = rset.getInt(1); //
                if (count > 0) {
                    isConflict = true; //
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }
        return isConflict;
    }

    // 2. 가장 마지막 강의 번호 조회
    public String getLastClassNo(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String lastNo = null;


        String query = QueryUtil.getQuery("getLastClassNo");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                lastNo = rset.getString("class_no"); //
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }
        return lastNo; //
    }

    // 3. 신규 강좌 최종 등록 (INSERT)
    public int insertCourse(Connection con, EnrollmentCourseDTO course) {
        PreparedStatement pstmt = null;
        int result = 0;


        String query = QueryUtil.getQuery("insertCourse");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, course.getClassNo());
            pstmt.setString(2, course.getClassName());
            pstmt.setDouble(3, course.getClassPoint());
            pstmt.setString(4, course.getClassTime());
            pstmt.setString(5, course.getClassRoom());
            pstmt.setString(6, course.getClassType());
            pstmt.setString(7, course.getProfessorId());
            pstmt.setString(8, course.getClassTask());
            pstmt.setFloat(9, course.getClassCapacity());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }

    // ==============================================================
    // [교수 기능 1] 담당 과목 조회
    // ==============================================================
    public List<EnrollmentCourseDTO> selectCoursesByProfId(Connection con, String profId) {
        List<EnrollmentCourseDTO> courseList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = QueryUtil.getQuery("selectCoursesByProfId");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, profId);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentCourseDTO course = new EnrollmentCourseDTO();
                course.setClassNo(rset.getString("class_no"));
                course.setClassName(rset.getString("class_name"));
                course.setClassPoint(rset.getDouble("class_point"));
                course.setClassTime(rset.getString("class_time"));
                course.setClassRoom(rset.getString("class_room"));
                course.setClassType(rset.getString("class_type"));
                course.setClassTask(rset.getString("class_task"));
                course.setProfessorId(rset.getString("professor_id"));
                course.setClassCapacity(rset.getFloat("class_capacity"));
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }
        return courseList;
    }

    // ==============================================================
    // [교수 기능 2] 수강 학생 확인
    // ==============================================================
    public List<EnrollmentCourseDTO> selectStudentsByClassNo(Connection con, String classNo) {
        List<EnrollmentCourseDTO> studentList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = QueryUtil.getQuery("selectStudentsByClassNo");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, classNo);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentCourseDTO course = new EnrollmentCourseDTO();
                course.setStudentId(rset.getString("student_id"));
                course.setStudentName(rset.getString("student_name"));
                course.setScore(rset.getDouble("score"));
                course.setStatus(rset.getBoolean("status"));
                studentList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }
        return studentList;
    }

    // ==============================================================
    // [교수 기능 3] 과제 등록
    // ==============================================================
    public int updateClassTask(Connection con, String classNo, String professorId, String classTask) {
        int result = 0;
        PreparedStatement pstmt = null;
        String query = QueryUtil.getQuery("updateClassTask");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, classTask);
            pstmt.setString(2, classNo);
            pstmt.setString(3, professorId);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }

    // ==============================================================
    // [교수 기능 4] 성적 관리
    // ==============================================================
    public int updateStudentScore(Connection con, String classNo, String studentId, double score) {
        int result = 0;
        PreparedStatement pstmt = null;
        String query = QueryUtil.getQuery("updateStudentScore");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setDouble(1, score);
            pstmt.setString(2, classNo);
            pstmt.setString(3, studentId);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }


    public int updateSingleInfo(Connection con, String profId, String columnName, String newValue) {
        PreparedStatement pstmt = null;
        int result = 0;


        String query = "UPDATE 교수 SET " + columnName + " = ? WHERE professor_id = ?";

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, newValue);
            pstmt.setString(2, profId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }



    // 1. 내 통합 User ID 찾기
    public String findUserIdByProfId(Connection con, String profId) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String userId = null;
        String query = QueryUtil.getQuery("message.findUserId");
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, profId);
            rset = pstmt.executeQuery();
            if (rset.next()) userId = rset.getString("user_id");
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JDBCTemplate.close(rset); JDBCTemplate.close(pstmt); }
        return userId;
    }

    // 2. 나를 제외한 모든 사용자 조회
    public List<UserDTO> getAllUsers(Connection con, String myUserId) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<UserDTO> list = new ArrayList<>();
        String query = QueryUtil.getQuery("message.getAllUsers");
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, myUserId);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rset.getString("user_id"));
                user.setUserName(rset.getString("user_name"));
                user.setProfessorId(rset.getString("professor_id"));
                user.setStudentId(rset.getString("student_id"));
                list.add(user);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JDBCTemplate.close(rset); JDBCTemplate.close(pstmt); }
        return list;
    }

    // 3. 1:1 대화 기록 불러오기
    public List<UserMessageDTO> getChatHistory(Connection con, String myUserId, String targetUserId) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd (E) HH:mm:ss");
        String now = sdf.format(new java.util.Date());


        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<UserMessageDTO> list = new ArrayList<>();
        String query = QueryUtil.getQuery("message.getChatHistory");
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, myUserId); pstmt.setString(2, targetUserId);
            pstmt.setString(3, targetUserId); pstmt.setString(4, myUserId);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                UserMessageDTO msg = new UserMessageDTO();
                msg.setUserId(rset.getString("user_id"));
                msg.setUserName(rset.getString("user_name"));
                msg.setContent(rset.getString("content"));
                list.add(msg);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JDBCTemplate.close(rset); JDBCTemplate.close(pstmt); }
        return list;
    }

    // 4. 메시지 전송하기
    public int sendChatMessage(Connection con, MessageDTO msg) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = QueryUtil.getQuery("message.sendChatMessage");
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, msg.getUserId());
            pstmt.setString(2, msg.getReceiverId());
            pstmt.setString(3, msg.getContent());
            result = pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JDBCTemplate.close(pstmt); }
        return result;
    }


    public List<StudentDTO> getAllMembers(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<StudentDTO> list = new ArrayList<>();


        String query = QueryUtil.getQuery("student.allMemberView");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                StudentDTO person = new StudentDTO();
                person.setStudentId(rset.getString("ID"));

                String name = rset.getString("NAME");
                String type = rset.getString("TYPE");


                if (type.equals("교수")) {
                    person.setStudentName(name + " (교수)");
                } else {
                    person.setStudentName(name + " (학생)");
                }
                list.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }
        return list;
    }

    // 강좌 삭제
    public int deleteCourse(Connection con, String courseId) {
        PreparedStatement pstmt = null;
        int result = 0;

        String query = QueryUtil.getQuery("deleteCourse");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, courseId);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }


    public int updateCourseInfo(Connection con, EnrollmentCourseDTO course) {

        PreparedStatement pstmt = null;
        int result = 0;

        String query = QueryUtil.getQuery("updateCourseInfo");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, course.getClassName());
            pstmt.setDouble(2, course.getClassPoint());
            pstmt.setString(3, course.getClassTime());
            pstmt.setString(4, course.getClassRoom());
            pstmt.setFloat(5, course.getClassCapacity());
            pstmt.setString(6, course.getClassNo());
            pstmt.setString(7, course.getProfessorId());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }

        return result;
    }


}

