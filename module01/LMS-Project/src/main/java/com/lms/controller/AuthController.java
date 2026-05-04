package com.lms.controller;

import com.lms.common.JDBCTemplate;
import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.dto.StudentDTO;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.service.AuthService;
import com.lms.model.service.StudentService;
import com.lms.view.MainView;
import com.lms.view.StudentView;
import java.util.Random;
import java.util.Scanner;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class AuthController {

    private final MainView mainView;
    private final AuthService authService;
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public AuthController(MainView mainView, AuthService authService) {
        this.mainView = mainView;
        this.authService = authService;
        this.mainView.setAuthService(authService);
    }

    // 로그인 기능 로직
    public void login() {

        while (true) {

            LoginRequestDTO request = mainView.inputLoginInfo();

            if (request == null) {
                mainView.displayMessage("로그인 정보 입력이 올바르지 않습니다.‼️");
                return;
            }

            if ("BACK".equalsIgnoreCase(request.getRole())) {
                mainView.displayMessage("메인 화면으로 돌아갑니다.");
                return;
            }

            // 1. 잠금 여부 확인
            if (authService.isDeviceLocked()) {
                long remain = authService.getRemainingDeviceLockSeconds();
                int lockLevel = authService.getCurrentDeviceLockLevel();

                mainView.displayMessage(
                        "현재 기기에서 로그인 시도가 잠겨 있습니다. "
                                + formatSeconds(remain)
                                + " 후 다시 시도해주세요. "
                                + "(잠금 단계: " + lockLevel + "😢)"
                );
                continue;
            }

            // 2. 일정 횟수 이상 실패 시 사람 확인
            if (authService.needHumanCheck(request.getRole())) {
                mainView.displayMessage("로봇확인 🚨 보안숫자를 입력해주세요.🤖");

                boolean passHumanCheck = runHumanCheck();

                if (!passHumanCheck) {
                    mainView.displayMessage("사람 확인에 실패했습니다. 다시 로그인해주세요.⛔");
                    continue;
                }

                mainView.displayMessage("사람 확인에 성공했습니다.✅");
            }

            // 3. 실제 로그인 시도
            LoginUserDTO loginUser = authService.login(request);

            if (loginUser == null) {
                int failCount = authService.recordLoginFailure(request.getRole());

                if (authService.isDeviceLocked()) {
                    long remain = authService.getRemainingDeviceLockSeconds();
                    int lockLevel = authService.getCurrentDeviceLockLevel();

                    mainView.displayMessage(
                            "로그인 실패가 누적되어 현재 기기에서 로그인이 잠겼습니다. ⚠️"
                                    + formatSeconds(remain)
                                    + " 후 다시 시도해주세요. "
                                    + "(잠금 단계: " + lockLevel + ")"
                    );
                } else {
                    printFailureMessage(request, failCount);
                }
                continue;
            }
            authService.recordLoginSuccess();

            // 성공 시 환영 메시지 출력
            if ("STUDENT".equalsIgnoreCase(loginUser.getRole())) {

                printSuccessMessage(loginUser);

                Connection con = JDBCTemplate.getConnection();
                StudentService studentService = new StudentService(new StudentDAO(con));
                StudentController studentController = new StudentController(studentService);
                StudentView studentView = new StudentView(studentController, loginUser);

                studentView.displayStudentMenu();
                break;

            } else if ("PROFESSOR".equalsIgnoreCase(loginUser.getRole())) {
                printSuccessMessage(loginUser);
                new ProfessorController().startProfessorMenu(loginUser.getUserId());
                break;

            } else {
                mainView.displayMessage("알 수 없는 사용자 권한입니다.");
            }
        }
    }

    private String formatSeconds(long seconds) {
        long minutes = seconds / 60;
        long remainSeconds = seconds % 60;

        if (minutes > 0) {
            return minutes + "분 " + remainSeconds + "초";
        }
        return seconds + "초";
    }

    private String getRoleName(String role) {
        return "PROFESSOR".equalsIgnoreCase(role) ? "교수" : "학생";
    }

    private boolean runHumanCheck() {
        int first = random.nextInt(9) + 1;
        int second = random.nextInt(9) + 1;
        boolean plus = random.nextBoolean();

        if (!plus && first < second) {
            int temp = first;
            first = second;
            second = temp;
        }

        int answer = plus ? first + second : first - second;
        String operator = plus ? "+" : "-";

        System.out.println("\n[사람 확인 문제]");
        System.out.println("아래 계산 결과를 입력하세요.");
        System.out.print(first + " " + operator + " " + second + " = ");

        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input) == answer;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void printSuccessMessage(LoginUserDTO loginUser) {
        String roleName = getRoleName(loginUser.getRole());
        mainView.displayMessage("[" + roleName + " 로그인 성공] " + loginUser.getUserName() + "님, 환영합니다.");
    }

    private void printFailureMessage(LoginRequestDTO request, int failCount) {
        int lockThreshold = authService.getLockThreshold(request.getRole());

        StringBuilder sb = new StringBuilder();
        sb.append("로그인 실패: 아이디 또는 비밀번호를 확인해주세요.");
        sb.append(" (전체 실패 누적: ").append(failCount).append("/").append(lockThreshold).append(")");

        if (failCount == 1) {
            sb.append("\nCaps Lock 상태를 확인해주세요.");
        }

        if (failCount % lockThreshold == lockThreshold - 1) {
            sb.append("\n한 번 더 실패하면 현재 기기에서 로그인이 제한됩니다.");
        }

        mainView.displayMessage(sb.toString());
    }


    public void registerStudent() {
        try {
            StudentDTO newStudent = mainView.inputStudentInfo(
                    studentId -> authService.existsStudentId(studentId)
            );

            if (newStudent == null) {
                return; // 메인으로 돌아간 경우
            }

            int result = authService.registerStudent(newStudent);

            if (result > 0) {
                mainView.displayMessage("회원가입에 성공하였습니다!");
            } else {
                mainView.displayMessage("회원가입에 실패하였습니다!");
            }
        } catch (RuntimeException e) {
            mainView.displayMessage("학생 회원가입 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void registerProfessor() {

        String secretKey = "LMS-ADMIN-777";
        String inputId = null;

        while (true) {
            System.out.println("\n 🔐교수 가입 인증 코드를 입력하세요 (취소:0)");
            System.out.println("\n 인증코드 \n");
            String inputKey = new java.util.Scanner(System.in).nextLine().trim();

            if ("0".equalsIgnoreCase(inputKey)) {
                System.out.println("\n🚫 가입 절차를 중단합니다.");
                return;
            }

            if (secretKey.equalsIgnoreCase(inputKey)) {
                System.out.println("\n✅ 인증 성공! 가입 창으로 이동합니다.");
                break;
            } else {
                System.out.println("\n🚨 인증 코드가 일치하지 않습니다. 다시 입력해주세요.");
            }
        }


        while (true) {
            inputId = mainView.inputProfessorId();

            if (inputId == null || "BACK".equals(inputId)) {
                System.out.println("\n🚫 가입 절차를 중단합니다.  메인으로 돌아갑니다.");
                return;
            }

            if (authService.isDuplicateId(inputId)) {
                mainView.displayMessage("🚨 [중복] 이미 가입된 교수 번호입니다. 가입이 불가능합니다.");
                continue;
            }

            ProfessorDTO professorDTO = mainView.inputRestOfProfessorInfo(inputId, authService);


            if (professorDTO == null) {
                System.out.println("\n🔄️ 이전 단계로 돌아갑니다.");
                continue;
            }

            try {
                if (authService.insertProfessor(professorDTO)) {
                    mainView.displayMessage("\n🌟 교수 회원가입 성공");
                }
                break;
            } catch (RuntimeException e) {
                mainView.displayMessage("\n🚨 가입 실패: " + e.getMessage());
                break;
            } catch (SQLException e) {
                mainView.displayMessage("\n🚨 DB 오류 발생: " + e.getMessage());
                break;
            }


        }
    }
}

