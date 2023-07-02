package us.sep.biz.exam.enums;

public enum ExamTypeEnum {

    CET("CET","英语四六级考试"),
    NCRE("NCRE","计算机等级考试"),
;
    String name;
    String description;

     ExamTypeEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
