package com.fydp.fundusapp.Objects;

import java.util.List;

public class Exam {

    private String examId;
    private String patientId;
    private String examinerId;
    private String examDate;
    private List<ExamImage> examImages;

    public Exam(String examId, String patientId, String examinerId, String examDate, List<ExamImage> examImages){
        this.examId = examId;
        this.patientId = patientId;
        this.examinerId = examinerId;
        this.examDate = examDate;
        this.examImages = examImages;
    }

    public String getExamId() {
        return examId;
    }

    public String getPatientId(){
        return patientId;
    }

    public String getExaminerId(){
        return examinerId;
    }


    public String getExamDate() {
        return examDate;
    }

    public List<ExamImage> getExamImages() {
        return examImages;
    }
}
