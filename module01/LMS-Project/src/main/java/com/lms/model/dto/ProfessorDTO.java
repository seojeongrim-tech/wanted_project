package com.lms.model.dto;

import java.sql.Connection;

public class ProfessorDTO {

    private String ProfessorId;
    private String ProfessorName;
    private String ProfessorNo;
    private String ProfessorAddress;
    private String ProfessorEmail;
    private String ProfessorPhone;
    private String ProfessorPw;

    public ProfessorDTO() {}

    public ProfessorDTO(String professorId, String professorName, String professorNo, String professorAddress, String professorEmail, String professorPhone, String professorPw) {
        ProfessorId = professorId;
        ProfessorName = professorName;
        ProfessorNo = professorNo;
        ProfessorAddress = professorAddress;
        ProfessorEmail = professorEmail;
        ProfessorPhone = professorPhone;
        ProfessorPw = professorPw;
    }

//    public static int insertProfessor(Connection con, ProfessorDTO professorDTO) {
//    }

    public String getProfessorId() {
        return ProfessorId;
    }

    public void setProfessorId(String professorId) {
        ProfessorId = professorId;
    }

    public String getProfessorName() {
        return ProfessorName;
    }

    public void setProfessorName(String professorName) {
        ProfessorName = professorName;
    }

    public String getProfessorNo() {
        return ProfessorNo;
    }

    public void setProfessorNo(String professorNo) {
        ProfessorNo = professorNo;
    }

    public String getProfessorAddress() {
        return ProfessorAddress;
    }

    public void setProfessorAddress(String professorAddress) {
        ProfessorAddress = professorAddress;
    }

    public String getProfessorEmail() {
        return ProfessorEmail;
    }

    public void setProfessorEmail(String professorEmail) {
        ProfessorEmail = professorEmail;
    }

    public String getProfessorPhone() {
        return ProfessorPhone;
    }

    public void setProfessorPhone(String professorPhone) {
        ProfessorPhone = professorPhone;
    }

    public String getProfessorPw() {
        return ProfessorPw;
    }

    public void setProfessorPw(String professorPw) {
        ProfessorPw = professorPw;
    }



    @Override
    public String toString() {
        return "ProfessorDTO{" +
                "ProfessorId='" + ProfessorId + '\'' +
                ", ProfessorName='" + ProfessorName + '\'' +
                ", ProfessorNo='" + ProfessorNo + '\'' +
                ", ProfessorAddress='" + ProfessorAddress + '\'' +
                ", ProfessorEmail='" + ProfessorEmail + '\'' +
                ", ProfessorPhone='" + ProfessorPhone + '\'' +
                ", ProfessorPw='" + ProfessorPw + '\'' +
                '}';
    }
}
