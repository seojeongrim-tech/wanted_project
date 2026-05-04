package com.lms.model.dao;


import com.lms.common.QueryUtil;
import com.lms.model.dto.ProfessorDTO;
import com.lms.common.JDBCTemplate;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDAO {
  
    private final Connection connection;

    public ProfessorDAO(Connection connection) {
        this.connection = connection;
    }

  
    // 교수 회원가입 정보 저장 
    public String save(Connection connection, ProfessorDTO professorDTO) throws SQLException {
        String query = QueryUtil.getQuery("professor.save");

        try (PreparedStatement pstmt = connection.prepareStatement(query)){

//            ResultSet rset = pstmt.executeQuery();

            pstmt.setString(1,professorDTO.getProfessorId());
            pstmt.setString(2,professorDTO.getProfessorName());
            pstmt.setString(3,professorDTO.getProfessorNo());
            pstmt.setString(4,professorDTO.getProfessorAddress());
            pstmt.setString(5,professorDTO.getProfessorEmail());
            pstmt.setString(6,professorDTO.getProfessorPhone());
            pstmt.setString(7,professorDTO.getProfessorPw());

            int affectedRows = pstmt.executeUpdate();

            if(affectedRows > 0) {
                return "SUCCESS";

            }




        }

        return "FAIL";
    }

    public boolean existById(Connection con, String professorId) throws SQLException {
        boolean exists = false;
        String query = QueryUtil.getQuery("professor.existById");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1,professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    exists = rset.getInt(1) > 0;
                }
            }
        }

        return exists;

    }


    public boolean existByNo(Connection con, String professorNo) throws SQLException {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM `교수` WHERE `professor_no` = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, professorNo);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    exists = rset.getInt(1) > 0;
                }
            }
        }
        return exists;
    }


    public boolean existByEmail(Connection con, String professorEmail) throws SQLException {
        String query = "SELECT COUNT(*) FROM `교수` WHERE `professor_email` = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, professorEmail);
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean existByPhone(Connection con, String professorPhone) throws SQLException {
        String query = "SELECT COUNT(*) FROM `교수` WHERE `professor_phone` = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, professorPhone);
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public static int insertMessage(Connection con, String id, String name) throws SQLException{
        String sql = "INSERT INTO `사용자` (USER_ID, PROFESSOR_ID, USER_NAME) VALUES (?, ?, ?)";

        try ( PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, id);
            pstmt.setString(3, name);

            return pstmt.executeUpdate();
        }

    }
        
       

    //교수 로그인 메소드
    public LoginUserDTO loginProfessor(Connection con, LoginRequestDTO request) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        LoginUserDTO loginUser = null;

        String qeury = QueryUtil.getQuery("loginProfessor");

        try {
            pstmt = con.prepareStatement(qeury);
            pstmt.setString(1, request.getUserId());
            pstmt.setString(2, request.getPassword());

            rset = pstmt.executeQuery();

            if (rset.next()) {
                loginUser = new LoginUserDTO();
                loginUser.setRole("PROFESSOR");
                loginUser.setUserId(rset.getString("professor_id"));
                loginUser.setUserName(rset.getString("professor_name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("교수 로그인 조회 실패", e);
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }

        return loginUser;
    }

}
