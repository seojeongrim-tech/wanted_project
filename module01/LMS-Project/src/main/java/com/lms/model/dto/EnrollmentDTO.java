package com.lms.model.dto;

public class EnrollmentDTO {
        private String studentId;
        private String classNo;
        private String enrollDate;
        private String score;
        private String status;

    public EnrollmentDTO(String studentId, String classNo, String enrollDate,
                         String score, String status) {
        this.studentId = studentId;
        this.classNo = classNo;
        this.enrollDate = enrollDate;
        this.score = score;
        this.status = status;
    }

    public EnrollmentDTO() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "EnrollmentDTO{" +
                "studentId='" + studentId + '\'' +
                ", classNo='" + classNo + '\'' +
                ", enrollDate='" + enrollDate + '\'' +
                ", score='" + score + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
