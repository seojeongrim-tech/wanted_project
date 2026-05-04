package com.lms.controller;
import com.lms.model.dto.*;
import com.lms.model.service.ProfessorService;
import com.lms.view.ProfessorView;

import java.util.List;
import java.util.Map;

public class ProfessorController {

    private final ProfessorView view;
    private final ProfessorService service;

    public ProfessorController() {
        this.view = new ProfessorView(this);
        this.service = new ProfessorService();
    }

    // 로그인 담당이 호출할 메소드
    public void startProfessorMenu(String profId) {
        view.displayMainMenu(profId);
    }


    public List<EnrollmentCourseDTO> findCoursesByProfId(String profId) {
        return service.selectCoursesByProfId(profId);
    }
    public List<EnrollmentCourseDTO> findStudentsByCourseId(String courseId) {
        return service.selectStudentsByClassNo(courseId);
    }

    public int createAssignment(String courseId, String profId, String classTask) {
        return service.updateClassTask(courseId, profId, classTask);
    }
    public int updateGrade(String courseId, String studentId, double score) {
        return service.updateStudentScore(courseId, studentId, score);
    }

    // 신규 강좌 등록 요청
    public int registerCourse(EnrollmentCourseDTO course) {
        return service.registerCourse(course);
    }

    public int updateSingleInfo(String profId, String columnName, String newValue) {
        return service.updateSingleInfo(profId, columnName, newValue);
    }



    public String findUserIdByProfId(String profId) { return service.findUserIdByProfId(profId); }
    public List<UserDTO> getAllUsers(String myUserId) { return service.getAllUsers(myUserId); }
    public List<UserMessageDTO> getChatHistory(String myUserId, String targetUserId) { return service.getChatHistory(myUserId, targetUserId); }
    public int sendChatMessage(MessageDTO msg) { return service.sendChatMessage(msg); }



    // 주소록 가져오기
    public List<StudentDTO> getAllMembers() {
        return service.getAllMembers();
    }

    public int deleteCourse(String courseId) {
        return service.deleteCourse(courseId);
    }


    public int updateCourseInfo(EnrollmentCourseDTO course) {
        return service.updateCourseInfo(course);
    }

}
