package us.sep.biz.exam.enums;

public enum ExamTypeEnum {

    FOUR("大学生英语四级考试"),
    SIX("大学生英语六级考试"),
    ;
    String name;

    ExamTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
