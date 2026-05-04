package com.lms.model.dto;

//LoginRequestDTO 작성
//로그인 요청값을 담는 DTO입니다.
public class LoginRequestDTO {

    private String role;
    private String userId;
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String role, String userId,String password){
        this.role = role;
        this.userId = userId;
        this.password = password;
    }
    public String getRole(){
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
