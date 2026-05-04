package com.lms.controller;

import com.lms.model.dto.*;
import com.lms.model.service.StudentService;

import java.util.List;

public class StudentController {
    private final StudentService service;


    public StudentController(StudentService service) {
        this.service = service;
    }

    public List<CourseDTO> findClass() {
        return service.findClass();
    }

    public int addClass(String applyClassNo, String studentId) {
        EnrollmentDTO enroll = new EnrollmentDTO();

        //로그인 정보
        enroll.setStudentId(studentId); //사용자에게 입력받은 학번
        enroll.setClassNo(applyClassNo);
        return service.addClass(enroll);
        //addClass()괄호 안에 applyClassNo대신 enroll를 넣는 이유는 누가 수강신청했는지 알기 위함
    }

    public List<EnrollmentDTO> enrollView(String studentId) {

        return service.enrollView(studentId);
    }

    public int deleteClass(String deleteClassNo, String studentId) {
        EnrollmentDTO enroll = new EnrollmentDTO();

        //로그인 정보
        enroll.setStudentId(studentId); //사용자에게 입력받은 학번
        enroll.setClassNo(deleteClassNo);
        return service.deleteClass(enroll);
    }

    public List<CourseDTO> taskView(String taskClassNo, String studentId) {
        EnrollmentDTO enroll = new EnrollmentDTO();

        enroll.setStudentId(studentId); //사용자에게 입력받은 학번
        enroll.setClassNo(taskClassNo);
        return service.taskView(enroll);
    }

    public List<EnrollmentDTO> scoreView(String scoreClassNo, String studentId) {
        EnrollmentDTO enroll = new EnrollmentDTO();

        enroll.setStudentId(studentId); //사용자에게 입력받은 학번
        enroll.setClassNo(scoreClassNo);
        return service.scoreView(enroll);
    }

    public CourseDTO timeEqual(String applyClassNo, String studentId) {
        return service.timeEqual(applyClassNo, studentId);
    }

    public List<EnrollmentDTO> totalScoreView(String studentId) {
        return service.totalScoreView(studentId);
    }

    public List<StudentDTO> messageMember() {
        return service.messageMember();
    }

    public StudentDTO myInfoView(String studentId) {
        return service.myInfoView(studentId);
    }

    public int editMyInfo(StudentDTO myInfo) {
        return service.editMyInfo(myInfo);
    }

    public int messageSend(MessageDTO msg) {
        return service.messageSend(msg);
    }

    public List<MessageDTO> messageCheck(String myId) {
        return service.messageCheck(myId);
    }

    public List<MessageDTO> getChatHistory(String myId, String targetId) {
        return service.getChatHistory(myId, targetId);
    }

}

