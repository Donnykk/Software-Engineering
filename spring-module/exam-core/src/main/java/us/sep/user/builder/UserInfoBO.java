package us.sep.user.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoBO {

    //真实姓名
    private String realName;

    //班级
    private String className;

    //学号
    private String stuNo;

    //专业
    private String major;

    //身份证号码
    private String identificationNumber;

    //用户id
    private String userId;

    //大学
    private String mySchool;

    //校区
    private String myArea;

    //学历
    private String myEducation;

    //院系
    private String myDepartment;

    //性别
    private String mySex;

    //证件类型
    private String myIdType;
}
