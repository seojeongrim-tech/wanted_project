package com.lms.view;

import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.dto.StudentDTO;
import com.lms.model.service.AuthService;
import java.util.function.Predicate;

import java.util.Scanner;

public class MainView {

    private final Scanner sc = new Scanner(System.in);


    private AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
    public int displayMainMenu() {
        while (true) {
            System.out.println("\n========== LMS 메인 ==========");
            System.out.println("1. 🛂로그인");
            System.out.println("2. 🧑‍🎓학생 회원가입");
            System.out.println("3. 🧑‍🏫교수 회원가입");
            System.out.println("0. 💤종료");
            System.out.print("메뉴 선택: ");

            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ 메뉴는 숫자로 입력해주세요.\n");
            }
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public LoginRequestDTO inputLoginInfo() {
        while(true) {
            System.out.println("\n========== 로그인 ==========");
            System.out.println("1.🧑‍🎓학생");
            System.out.println("2. 🧑‍🏫교수");
            System.out.println("0. 🔙뒤로가기");
            System.out.print("로그인 유형 선택: ");

            int roleMenu;

            try {
                roleMenu = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                displayMessage("로그인 유형은 숫자로 입력해주세요.🚨");
                continue;
            }

            if(roleMenu == 0) {
                return new LoginRequestDTO("BACK", "", "");
            }

            String role;
            if (roleMenu == 1) {
                role = "STUDENT";
            } else if (roleMenu == 2) {
                role = "PROFESSOR";
            } else {
                displayMessage("잘못된 로그인 유형입니다.");
                continue;
            }

            System.out.print("아이디 입력: ");
            String userId = sc.nextLine();

            System.out.print("비밀번호 입력: ");
            String password = sc.nextLine();

            return new LoginRequestDTO(role,userId,password);
        }
    }






    public StudentDTO inputStudentInfo(Predicate<String> isDuplicateStudentId) {
        System.out.println("========== 학생 회원가입 ==========\n");

        StudentDTO student = new StudentDTO();
        int step = 0;

        while (true) {
            switch (step) {

                case 0: { // 학번
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 메인으로 돌아가기: 0 *");
                    System.out.print("학번 (* 숫자 8자리를 입력해주세요 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (!input.matches("\\d{8}")) {
                        System.out.println("❌ 학번은 숫자 8자리입니다. 다시 입력해주세요.\n");
                        break;
                    }

                    if (isDuplicateStudentId.test(input)) {
                        System.out.println("❌ 이미 존재하는 아이디입니다. 다시 입력해주세요.\n");
                        break;
                    }

                    student.setStudentId(input);
                    step++;
                }

                case 1: { // 이름
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("이름 : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.matches("^[가-힣a-zA-Z]+$")) {
                        student.setStudentName(input);
                        step++;
                    } else {
                        System.out.println("❌ 이름은 한글 또는 영문만 입력 가능합니다.\n");
                    }
                    break;
                }

                case 2: { // 주민등록번호
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("주민등록번호 (* 123456-1234567 형식으로 입력해주세요 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (!input.matches("\\d{6}-\\d{7}")) {
                        System.out.println("❌ 주민등록번호 형식이 올바르지 않습니다.\n");
                        break;
                    }

                    if (authService.existsStudentNo(input)) {
                        System.out.println("❌ 이미 등록된 주민등록번호입니다.\n");
                        break;
                    }

                    student.setStudentNo(input);
                    step++;
                }

                case 3: { // 주소
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("주소 : ");
                    System.out.println("* 정확한 주소를 모르시는 경우, 시/군/구 까지만 입력해주세요 *");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.matches(".*[가-힣a-zA-Z]+.*")) {
                        student.setStudentAddress(input);
                        step++;
                    } else {
                        System.out.println("❌ 형식에 맞춰 주소를 입력해주세요.\n");
                    }
                    break;
                }

                case 4: { // 이메일
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("이메일 (* 이메일 형식을 준수해 주세요 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (!input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        System.out.println("❌ 올바른 이메일 형식이 아닙니다.\n");
                        break;
                    }

                    if (isDuplicateStudentId != null && authService.existsStudentEmail(input)) {
                        System.out.println("❌ 이미 사용 중인 이메일입니다.\n");
                        break;
                    }

                    student.setStudentEmail(input);
                    step++;
                }

                case 5: { // 전화번호
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("전화번호 (* 번호 중간에 '-'도 입력해주세요 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.matches("^010-\\d{4}-\\d{4}$")) {
                        student.setStudentPhone(input);
                        step++;
                    } else {
                        System.out.println("❌ 전화번호는 010-1234-5678 형식으로 입력해주세요.\n");
                    }
                    break;
                }

                case 6: { // 비밀번호
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("비밀번호 (* 비밀번호는 영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$")) {
                        student.setStudentPw(input);
                        step++;
                    } else {
                        System.out.println("❌ 비밀번호 형식이 올바르지 않습니다.\n");
                    }
                    break;
                }

                case 7: { // 비밀번호 확인
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("확인을 위해 비밀번호를 다시 입력해주세요 : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.equals(student.getStudentPw())) {
                        return student;
                    } else {
                        System.out.println("❌ 비밀번호가 일치하지 않습니다.\n");
                    }
                    break;
                }
            }
        }
    }


    public String inputProfessorId () {
        while (true) {
            System.out.println("\n========== 교수 회원가입 ========== \n(뒤로가기는 '1', 취소는 '0' 입니다.)");
            System.out.print("\n📌 가입하실 교수 번호를 입력해주세요. (P0000)\n");
            String id = sc.nextLine().trim();
            if ("0".equalsIgnoreCase(id)) return null;
            if ("1".equalsIgnoreCase(id)) return "BACK";

            id = id.toUpperCase();

            if (id.matches("^P[0-9]{4}$")) {
                return id;
            } else {
                System.out.println("🚨 [입력 오류] 교수 번호는 'P'와 숫자 4자리 형식이어야 합니다. (예: P1001)");
            }
        }
    }



    public ProfessorDTO inputRestOfProfessorInfo(String inputId, AuthService authService) {
        System.out.println("✅ 번호 확인 완료.");
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setProfessorId(inputId);


        int step = 2;

        while (step <= 7) {
            switch (step) {

                case 2 :
                    System.out.print("\n이름을 입력해주세요.\n");
                    String name = sc.nextLine().trim();
                    if ("0".equalsIgnoreCase(name)) return null;
                    if ("1".equalsIgnoreCase(name)) return null;

                    if (name.isEmpty()) {
                        System.out.println("🚨 이름을 반드시 입력해야 합니다.");
                        continue;
                    }
                    professorDTO.setProfessorName(name);
                    step++;
                    break;

                case 3 :
                    System.out.print("\n주민등록번호를 입력해주세요.\n ");
                    String rawInput = sc.nextLine().trim();
                    String inputNo = rawInput.replaceAll("[^0-9]", "");

                    if ("0".equalsIgnoreCase(inputNo)) return null;
                    if ("1".equalsIgnoreCase(inputNo)) {
                        step--;
                        continue;
                    }

                    if (inputNo.length() == 13) {
                        String formattedNo = inputNo.substring(0,6) + "-" + inputNo.substring(6);
                        professorDTO.setProfessorNo(formattedNo);

                        if (authService.isDuplicateNo(formattedNo)) {
                            System.out.println("🚨 [중복 오류] 이미 등록된 주민번호입니다. 다시 입력해주세요.");
                            continue;
                        }

                        System.out.println("\n➡️ 입력 확인: " + formattedNo);
                        System.out.print("이 정보가 맞습니까? (y/n): ");
                        if (sc.nextLine().equalsIgnoreCase("y")) {
                            System.out.println("✅ 주민번호 형식이 일치합니다.");
                            step++;
                        }
                    } else {
                        System.out.println("🚨 [입력 오류] 숫자 13자리를 정확히 입력해주세요.");
                    }
                    break;

                case 4:
                    System.out.print("\n주소를 다음과 같은 형식으로 입력해주세요." +
                            "\n ----------------------------- "+
                            "\n|   시·도/시·군·구 + 도로명 주소  | " +
                            "\n|  시·도/시·군·구/읍·면·동 + 지번 | " +
                            "\n|    시·도/시·군·구 + 건물명     | " +
                            "\n ------------------------------ \n"
                    );
                    String adress = sc.nextLine().trim();
                    if ("0".equalsIgnoreCase(adress)) return null;
                    if ("1".equalsIgnoreCase(adress)) {
                        step--;
                        continue;
                    }
                    professorDTO.setProfessorAddress(adress);
                    step++;
                    break;

                case 5:
                    System.out.print("\n이메일 주소(example@lms.com)를 입력해주세요. \n ");
                    String email = sc.nextLine().trim();
                    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                    if ("0".equalsIgnoreCase(email)) return null;
                    if ("1".equalsIgnoreCase(email)) {
                        step--;
                        continue;
                    }

                    if (authService.isDuplicateEmail(email)) {
                        System.out.println("🚨 [중복 오류] 이미 등록된 이메일입니다. 다시 입력해주세요.");
                        continue;
                    }

                    if (email.matches(emailRegex)) {
                        professorDTO.setProfessorEmail(email);
                        System.out.println("✅ 이메일 형식이 일치합니다.");
                        step++;
                    } else {
                        System.out.println("🚨 이메일 형식이 올바르지 않습니다. '@'를 포함한 정확한 주소를 입력해주세요.");
                    }
                    break;

                case 6:
                    System.out.print("\n전화번호를 입력해주세요. \n");
                    String inputPhone = sc.nextLine().trim().replaceAll("[^0-9]", "");

                    if ("0".equalsIgnoreCase(inputPhone)) return null;
                    if ("1".equalsIgnoreCase(inputPhone)) {
                        step--;
                        continue;
                    }

                    if (inputPhone.matches("^010\\d{8}$")) {
                        String formattedPhone = "010-" + inputPhone.substring(3, 7) + "-" + inputPhone.substring(7);

                        if (authService.isDuplicatePhone(formattedPhone)) {
                            System.out.println("🚨 [중복 오류] 이미 등록된 전화번호입니다. 다시 입력해주세요.");
                            continue;
                        }

                        professorDTO.setProfessorPhone(formattedPhone);
                        System.out.println("\n➡️ 변환된 형식: " + formattedPhone);
                        System.out.print("이 정보가 맞습니까? (y/n): ");
                        if (sc.nextLine().equalsIgnoreCase("y")) {
                            System.out.println("✅ 전화번호 형식이 일치합니다.");
                            step++;
                        }
                    } else {
                        System.out.println("🚨 [입력 오류] 올바른 전화번호 형식이 아닙니다.");
                    }
                    break;

                case 7:
                    System.out.print("\n비밀번호를 입력해주세요. \n");
                    String pw = sc.nextLine().trim();

                    if ("0".equalsIgnoreCase(pw)) return null;
                    if ("1".equalsIgnoreCase(pw)) {
                        step--;
                        continue;
                    }
                    String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";
                    if (!pw.matches(regex)) {
                        System.out.println("🚨 [입력 오류] 비밀번호는 영문, 숫자, 특수문자 포함 8자 이상이어야 합니다.");
                        continue;
                    }

                    System.out.print("\n비밀번호를 다시 입력해주세요. \n");
                    String pwCheck = sc.nextLine().trim();

                    if ("0".equalsIgnoreCase(pwCheck)) return null;
                    if ("1".equalsIgnoreCase(pwCheck)) {
                        System.out.println("\n🔄 비밀번호 입력부터 다시 시작합니다.");
                        continue;
                    }

                    if (pw.equals(pwCheck)) {
                        professorDTO.setProfessorPw(pw);
                        System.out.println("✅ 비밀번호가 일치합니다.");
                        step++;
                    } else {
                        System.out.println("🚨 비밀번호가 일치하지 않습니다. 처음부터 다시 입력해주세요.");
                    }
                    break;
            }
        }

        return professorDTO;
    }
}