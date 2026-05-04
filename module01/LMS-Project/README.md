# 🎓 LMS Project (Learning Management System)

![Java](https://img.shields.io/badge/Java-17-orange)
![Project](https://img.shields.io/badge/Project-Team%20LMS-blue)
![Status](https://img.shields.io/badge/Status-In%20Progress-green)

> GitHub 협업을 기반으로 진행하는 **LMS (Learning Management System) 개발 프로젝트**

---

# 📌 Project Introduction

LMS (Learning Management System)는  
강의 관리, 학습 자료 제공, 과제 제출 등 교육 과정을 관리하는 **온라인 학습 플랫폼**입니다.

본 프로젝트는 GitHub 협업 기능을 활용하여 다음을 목표로 합니다.

- 팀 프로젝트 개발 경험
- GitHub 협업 프로세스 이해
- 프로젝트 포트폴리오 제작

---

# 🎯 Project Goals

- LMS 핵심 기능 구현
- GitHub 기반 협업 경험
- Issue / PR 중심 개발 프로세스 학습
- 프로젝트 진행 기록 관리

---

# 🧩 Core Features

### 👤 User Management
- 회원가입
- 로그인
- 사용자 정보 관리

### 📚 Course Management
- 강의 목록 조회
- 강의 등록
- 강의 관리

### 📢 Notice Board
- 공지사항 등록
- 공지사항 조회

### 📝 Assignment (Optional)
- 과제 등록
- 과제 제출
- 과제 조회

---

# 🏗 Project Structure

```text
src/main/java
└─ com.lms
   ├─ application
   ├─ common
   ├─ controller
   ├─ model
   │  ├─ dao
   │  ├─ dto
   │  └─ service
   ├─ query
   └─ view

src/main/resources
├─ db.properties
└─ db.properties.example

database
├─ 01_create_database.sql
├─ 02_create_tables.sql
├─ 03_constraints.sql
├─ 04_dummy_data.sql
└─ 05_test_query.sql
```

---

# 👥 Team Collaboration

본 프로젝트는 GitHub 협업 기능을 중심으로 진행됩니다.

### Issue
작업 단위 관리

예시
[FEATURE] 로그인 기능  
[TASK] 2026-03-11 작업 기록  
[BUG] 로그인 오류 수정  

---

### Pull Request

개발 완료 후 PR 생성

feature/login → develop

코드 리뷰 후 merge

---

### Discussion

팀 논의 및 공지

예시

- 프로젝트 Kick-off
- LMS 도메인 조사
- 기능 우선순위 결정

---

# 📅 Project Schedule

Day 1  
LMS 도메인 조사  

Day 2  
유사 서비스 분석  

Day 3  
시스템 설계  

Day 4  
기능 개발  

Day 5  
테스트 및 정리  

---

# 🛠 Tech Stack

- Java
- Git
- GitHub

---

# 📊 GitHub Workflow

Issue 생성  
↓  
Feature Branch 생성  
↓  
개발 진행  
↓  
Pull Request  
↓  
Code Review  
↓  
Merge  

---

# 📝 Daily Task

각 팀원은 작업 기록을 GitHub Issue로 관리합니다.

예시

[TASK] 2026-03-11

기록 내용

- 오늘 할 일
- 진행한 작업
- 문제 사항
- 내일 할 일

---

# 🚀 Future Improvements

- 출석 관리
- 성적 관리
- 파일 업로드
- 강의 자료 관리

---

# 📎 References

- Moodle LMS
- Canvas LMS

---

# 📌 License

This project is created for **educational purposes**.
