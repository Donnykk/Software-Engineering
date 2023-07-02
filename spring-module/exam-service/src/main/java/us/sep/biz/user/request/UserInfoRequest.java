package us.sep.biz.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "真实姓名不能为空")
    //真实姓名
    private String realName;

    @NotBlank(message = "班级不能为空")
    //班级
    private String className;

    @NotBlank
    @Size(message = "学号需要七位" ,min = 7 ,max = 7)
    //学号
    private String stuNo;

    @NotBlank(message = "专业不能为空")
    //专业
    private String major;

    @NotBlank(message = "身份证号码不能为空")
    @Size(message = "身份证号需要十八位" ,min = 18 ,max = 18)
    //身份证号码
    private String identificationNumber;

    @NotBlank(message = "大学不能为空")
    //大学
    private String mySchool;

    @NotBlank(message = "校区不能为空")
    //校区
    private String myArea;

    @NotBlank(message = "学历不能为空")
    //学历
    private String myEducation;

    @NotBlank(message = "院系不能为空")
    //院系
    private String myDepartment;

    @NotBlank(message = "性别不能为空")
    //性别
    private String mySex;

    @NotBlank(message = "身份证类型不能为空")
    //身份证类型
    private String myIdType;
}
