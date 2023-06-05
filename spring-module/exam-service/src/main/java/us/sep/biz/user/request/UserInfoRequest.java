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

    @NotBlank(message = "院校不能为空")
    //院校
    private String school;

    @NotBlank
    @Size(message = "学号需要九位" ,min = 9 ,max = 9)
    //学号
    private String stuNo;

    @NotBlank(message = "身份证号码不能为空")
    @Size(message = "身份证号需要十八位" ,min = 18 ,max = 18)
    //身份证号码
    private String identificationNumber;

}
