package com.lms.model.dao;

import com.google.protobuf.Message;
import com.lms.common.JDBCTemplate;
import com.lms.common.QueryUtil;
import com.lms.model.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lms.model.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;


public class StudentDAO {

    public LoginUserDTO loginStudent(LoginRequestDTO request) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        LoginUserDTO loginUser = null;

        String query = QueryUtil.getQuery("loginStudent");

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, request.getUserId());
            pstmt.setString(2, request.getPassword());

            rset = pstmt.executeQuery();

            if (rset.next()) {
                loginUser = new LoginUserDTO();
                loginUser.setRole("STUDENT");
                loginUser.setUserId(rset.getString("student_id"));
                loginUser.setUserName(rset.getString("student_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("학생 로그인 조회 실패", e);
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }

        return loginUser;
    }

    public int save(StudentDTO student) throws SQLException {
        String query = QueryUtil.getQuery("students.save");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getStudentPw());
            pstmt.setString(3, student.getStudentNo());
            pstmt.setString(4, student.getStudentName());
            pstmt.setString(5, student.getStudentAddress());
            pstmt.setString(6, student.getStudentEmail());
            pstmt.setString(7, student.getStudentPhone());

            return pstmt.executeUpdate();
        }
    }

    public boolean existsByStudentId(String studentId) throws SQLException {
        String query = QueryUtil.getQuery("students.existsByStudentId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean existsByEmail(String email) throws SQLException {
        String query = QueryUtil.getQuery("students.existsByEmail");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean existsByStudentNo(String studentNo) throws SQLException {
        String query = QueryUtil.getQuery("students.existsByStudentNo");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentNo);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private final Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<CourseDTO> findClass() throws SQLException {
        List<CourseDTO> courseList = new ArrayList<>();
        String query = QueryUtil.getQuery("course.findClass");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                CourseDTO course = new CourseDTO();
                course.setClassNo(rset.getString("class_no"));
                course.setClassName(rset.getString("class_name"));
                course.setClassTime(rset.getString("class_time"));
                course.setClassType(rset.getString("class_type"));
                course.setClassRoom(rset.getString("class_room"));
                course.setClassPoint(rset.getString("class_point"));
                course.setProfessorId(rset.getString("professor_name"));
                course.setClassCapacity(rset.getFloat("class_capacity"));
                course.setClassTask(rset.getString("current_count"));
                courseList.add(course);
            }
            return courseList;
        }
    }

    public int addClass(EnrollmentDTO enroll) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.addClass");
        int result = 0;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, enroll.getStudentId());
            pstmt.setString(2, enroll.getClassNo());

            result = pstmt.executeUpdate();
            //insert, update, delete는 db의 상태를 바꾸기 때문에 executeUpdate 사용
            //insert에 성공하면 1이 반환됨(1행이 추가되므로)

            if (result > 0) {
                JDBCTemplate.commit(connection);
                System.out.println("DB에 영구 반영되었습니다.");
            } else {
                // 삭제할 데이터가 없거나 실패했다면 롤백 (안전장치)
                JDBCTemplate.rollback(connection);
            }
        }
        return result;
    }

    public List<EnrollmentDTO> enrollView(String studentId) throws SQLException {
        List<EnrollmentDTO> enrollList = new ArrayList<>();
        String query = QueryUtil.getQuery("enrollment.myEnrollView");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentDTO enroll = new EnrollmentDTO();

                enroll.setStudentId(rset.getString("student_id"));
                enroll.setClassNo(rset.getString("class_no"));

                String detailInfo = (("학생명: " + rset.getString("student_name") +
                        ("\n강의명: " + rset.getString("class_name") + " (" + rset.getString("class_type") + ")")+
                        ("\n강의실: " + rset.getString("class_room") + " (" + rset.getString("class_time") + ")") +
                        ("\n학점: " + rset.getInt("class_point")) +
                        ("\n교수명: " + rset.getString("professor_name")) +
                        ("\n신청일: " + rset.getString("enroll_date")) +
                        ("\n수강신청 인원: " + rset.getString("current_count")) + "/" + (int) rset.getFloat("class_capacity")));

                enroll.setEnrollDate(detailInfo);

                enrollList.add(enroll);
            }
            return enrollList;
        }

    }

    public int deleteClass(EnrollmentDTO enroll) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.deleteClass");
        int result = 0;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, enroll.getStudentId());
            pstmt.setString(2, enroll.getClassNo());

            result =  pstmt.executeUpdate();

            if (result > 0) {
                JDBCTemplate.commit(connection);
                System.out.println("DB에 영구 반영되었습니다.");
            } else {
                // 삭제할 데이터가 없거나 실패했다면 롤백 (안전장치)
                JDBCTemplate.rollback(connection);
            }

        }
        return result;
    }

    public List<CourseDTO> taskView(EnrollmentDTO enroll) throws SQLException {
        String query = QueryUtil.getQuery("course.taskCheck");
        List<CourseDTO> taskList = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, enroll.getStudentId());
            pstmt.setString(2, enroll.getClassNo());

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                CourseDTO course = new CourseDTO();

                course.setClassName(rset.getString("class_name"));
                String taskContent = rset.getString("class_task"); // DTO에 이 필드가 있어야 함!
                course.setClassNo(enroll.getClassNo()); // 이미 알고 있는 번호 세팅

                if (taskContent == null || taskContent.isEmpty()) {
                    course.setClassTask("현재 등록된 과제가 없습니다.");
                } else {
                    course.setClassTask(taskContent);
                }
                taskList.add(course);
            }
        }
        return taskList;
    }

    public List<EnrollmentDTO> scoreView(EnrollmentDTO enroll) throws SQLException {
        String query = QueryUtil.getQuery("enroll.scoreCheck");
        List<EnrollmentDTO> scoreList = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, enroll.getStudentId());
            pstmt.setString(2, enroll.getClassNo());

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentDTO score = new EnrollmentDTO();

                enroll.setScore(rset.getString("score"));
                enroll.setClassNo(rset.getString("class_name"));

                scoreList.add(enroll);
            }

        }
        return scoreList;
    }


    public CourseDTO timeEqual(String applyClassNo, String userId) throws SQLException {
        String query = QueryUtil.getQuery("course.timeCheck");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userId); //로그인된 학번
            pstmt.setString(2, applyClassNo); //신청할 강의번호(요일 추출용)
            pstmt.setString(3, applyClassNo); //신청할 강의번호(종료시간 비교용)
            pstmt.setString(4, applyClassNo); //신청할 강의번호(시작시간 비교용)

            ResultSet rset = pstmt.executeQuery();
            if(rset.next()) { //겹치는 강의가 있다면
                CourseDTO timeEqual = new CourseDTO();
                timeEqual.setClassName(rset.getString("class_name"));
                timeEqual.setClassTime(rset.getString("class_time"));
                return timeEqual;
            } //결과가 존재(true)하면 시간이 겹친다.
        }
        return null;
    }

    public List<EnrollmentDTO> totalScoreView(String studentId) throws SQLException {
        List<EnrollmentDTO> list = new ArrayList<>();
        String query = QueryUtil.getQuery("enroll.totalScoreView");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentDTO enroll = new EnrollmentDTO();
                enroll.setClassNo(rset.getString("class_name"));
                String scorePoint = ("학점: " + rset.getString("score") + " (" + rset.getString("class_point") + ")");
                enroll.setScore(scorePoint);
                list.add(enroll);
            }
        }
        return list;
    }

    public List<StudentDTO> messageMember() throws SQLException {
        List<StudentDTO> memberList = new ArrayList<>();
        String query = QueryUtil.getQuery("student.allMemberView");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                String name = rset.getString("NAME");
                String id = rset.getString("ID");
                String type = rset.getString("TYPE");

                StudentDTO person = new StudentDTO();
                person.setStudentId(id);

                if (type.equals("교수")) {
                    person.setStudentName(name + " (교수)");
                } else {
                    person.setStudentName(name + " (학생)");
                }

                memberList.add(person);
            }
        }
        return memberList;
    }

    public StudentDTO myInfoView(String studentId) throws SQLException {
        String query = QueryUtil.getQuery("student.myInfoView");
        StudentDTO student = null;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                student = new StudentDTO();

                student.setStudentId(rset.getString("student_id"));
                student.setStudentName(rset.getString("student_name"));
                student.setStudentNo(rset.getString("student_no"));
                student.setStudentAddress(rset.getString("student_address"));
                student.setStudentEmail(rset.getString("student_email"));
                student.setStudentPhone(rset.getString("student_phone"));
                student.setStudentPw(rset.getString("student_pw"));
                student.setProfessorId(rset.getString("professor_id"));
            }
        }
        return student;
    }

    public int editMyInfo(StudentDTO myInfo) throws SQLException {
        String query = QueryUtil.getQuery("student.editMyInfo");
        int result = 0; //수정된 행의 개수(성공 시 1 리턴)

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, myInfo.getStudentName());
            pstmt.setString(2, myInfo.getStudentAddress());
            pstmt.setString(3, myInfo.getStudentEmail());
            pstmt.setString(4, myInfo.getStudentPhone());
            pstmt.setString(5, myInfo.getStudentPw());

            pstmt.setString(6, myInfo.getStudentId());

            result = pstmt.executeUpdate();

            if (result > 0) {
                JDBCTemplate.commit(connection);
                System.out.println("DB에 반영되었습니다.");
            } else {
                JDBCTemplate.rollback(connection);
            }
        }
        return result;
    }

//    public int sendMessage(UserDTO msg) throws SQLException {
//        String query = QueryUtil.getQuery("message.newMessage");
//        int result= 0;
//        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setString(1, msg.getUserId());
//            pstmt.setString(2, msg.getStudentId());
//            pstmt.setString(3, msg.getReceiverId());
//            pstmt.setString(4, msg.getContent());
//            pstmt.setString(5, msg.getUserName());
//
//            pstmt.setString(6, msg.getStudentId());
//
//            result = pstmt.executeUpdate();
//            if (result > 0) {
//                JDBCTemplate.commit(connection);
//                System.out.println("DB에 반영되었습니다.");
//            } else {
//                JDBCTemplate.rollback(connection);
//            }
//
//        }
//        return result;
//    }
//
//    public List<UserDTO> messageCheck(String myId) throws SQLException {
//        List<UserDTO> list = new ArrayList<>();
//        String query = QueryUtil.getQuery("message.contentView");
//        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setString(1, myId);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                UserDTO m = new UserDTO();
//                m.setUserId(rset.getString("user_id"));
//                m.setUserName(rset.getString("user_name"));
//                m.setContent(rset.getString("content"));
//                list.add(m);
//            }
//        }
//        return list;
//    }


    public int sendMessage(MessageDTO msg) throws SQLException {
        String ensureUserQuery = QueryUtil.getQuery("message.ensureUser");
        String messageQuery = QueryUtil.getQuery("message.newMessage");
        int result= 0;
        try (PreparedStatement pstmtUser = connection.prepareStatement(ensureUserQuery);
             PreparedStatement pstmtMsg = connection.prepareStatement(messageQuery)) {
            pstmtUser.setString(1, msg.getUserId());
            pstmtUser.setString(2, msg.getUserId());
            pstmtUser.setString(3, msg.getUserId());
            pstmtUser.executeUpdate();

            pstmtUser.setString(1, msg.getReceiverId());
            pstmtUser.setString(2, msg.getReceiverId());
            pstmtUser.setString(3, msg.getReceiverId());
            pstmtUser.executeUpdate();

            pstmtMsg.setString(1, msg.getUserId());
            pstmtMsg.setString(2, msg.getReceiverId());
            pstmtMsg.setString(3, msg.getContent());

            result = pstmtMsg.executeUpdate();
            if (result > 0) {
                JDBCTemplate.commit(connection);
            } else {
                JDBCTemplate.rollback(connection);
            }

        }
        return result;
    }

    public List<MessageDTO> messageCheck(String myId) throws SQLException {
        List<MessageDTO> list = new ArrayList<>();
        String query = QueryUtil.getQuery("message.contentView");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, myId);
            pstmt.setString(2, myId);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                MessageDTO m = new MessageDTO();
                m.setUserId(rset.getString("user_name") +
                        " (" + rset.getString("user_id") + ")");
                m.setContent(rset.getString("content"));
                m.setReceiverId((rset.getString("receiver_id")));
                list.add(m);
            }
        }
        return list;
    }

    public List<MessageDTO> getChatHistory(String myId, String targetId) throws SQLException {
        List<MessageDTO> list = new ArrayList<>();
        String query = QueryUtil.getQuery("message.chatHistory");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, myId);
            pstmt.setString(2, targetId);
            pstmt.setString(3, targetId);
            pstmt.setString(4, myId);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                MessageDTO m = new MessageDTO();
                m.setUserId(rset.getString("user_name") +
                        " (" + rset.getString("user_id") + ")");
                m.setContent(rset.getString("content"));

                list.add(m);
            }
        }
        return list;
    }
}

