package com.lms.model.dto;

public class UserDTO {
    private String userId;
    private String userName;
    private String studentId;
    private String professorId;

    public UserDTO() {
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public UserDTO(String userId, String userName, String studentId, String professorId) {
        this.userId = userId;
        this.userName = userName;
        this.studentId = studentId;
        this.professorId = professorId;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", studentId='" + studentId + '\'' +
                ", professorId='" + professorId + '\'' +
                '}';
    }
}
