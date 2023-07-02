package us.sep.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.sep.base.AbstractAuditBase;
import us.sep.user.builder.UserInfoBO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info", indexes = {
        @Index(name = "user_id_index", columnList = "userId")})
public class UserInfoDO extends AbstractAuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //真实姓名
    @Column(nullable = false,length = 32)
    private String realName;

    //班级
    @Column(nullable = false,length = 32)
    private String className;

    //学号
    @Column(nullable = false,length = 32)
    private String stuNo;

    //专业
    @Column(nullable = false,length = 32)
    private String major;

    //身份证号码
    @Column(nullable = false,length = 32)
    private String identificationNumber;

    //用户id
    @Column(nullable = false,length = 32, updatable = false)
    private String userId;

    //大学
    @Column(nullable = false)
    private String mySchool;

    //校区
    @Column(nullable = false)
    private String myArea;

    //学历
    @Column(nullable = false)
    private String myEducation;

    //院系
    @Column(nullable = false)
    private String myDepartment;

    //性别
    @Column(nullable = false)
    private String mySex;

    //证件类型
    @Column(nullable = false)
    private String myIdType;

    public UserInfoBO ToUserInfoBO(){
        return UserInfoBO.builder().userId(userId).stuNo(stuNo).realName(realName).major(major)
                .identificationNumber(identificationNumber).className(className).mySchool(mySchool)
                .myArea(myArea).myDepartment(myDepartment).myEducation(myEducation).mySex(mySex)
                .myIdType(myIdType).build();
    }


}
