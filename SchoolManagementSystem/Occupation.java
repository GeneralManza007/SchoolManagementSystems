public enum Occupation {

    FINANCIALADVISOR("FinancialAdvisor"), HEADTEACHER("Headteacher"), DEUPTYHEADTEACHER("DeuptyHeadTeacher"), HEADSOFDEPARTMENTS("HeadsOfDepartments"), RECEPTIONISTS("Receptionists"), TEACHERS("Teachers"), TEACHINGASSISTANTS("`TeachingAssistants"), STUDENTS("Students");


    private String occupation;

    private Occupation(String occ) {
        this.occupation = occ;
    }   

    public String getOccupation() {

        return occupation;

    }
}
