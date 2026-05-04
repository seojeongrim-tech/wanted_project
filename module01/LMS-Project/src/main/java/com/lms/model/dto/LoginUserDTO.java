package com.lms.model.dto;

//LoginUserDTO 코드 작성
//로그인 성공 후 반환할 사용자 정보 DTO입니다.
//역할: 로그인 성공 시 "누가 로그린했는지"를 담음

public class LoginUserDTO {

    private String role;
    private String userId;
    private String userName;

    public LoginUserDTO(){}

    public LoginUserDTO(String role, String userId, String userName) {
        this.role = role;
        this.userId = userId;
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}
