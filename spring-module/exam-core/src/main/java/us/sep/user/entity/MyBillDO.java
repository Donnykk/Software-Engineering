package us.sep.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.sep.user.builder.MyBillBO;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "my_bill", indexes = {
        @Index(name = "state_index", columnList = "myState")})
public class MyBillDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 32, updatable = false)
    private String userId;

    @Column(nullable = false,length = 32)
    private String examTypeId;

    @Column(nullable = false,length = 32)
    private String examDetailId;

    @Column(nullable = false,length = 32)
    private String realName;

    @Column(nullable = false,length = 32)
    private String examDescription;

    @Column(nullable = false)
    private int myMoney;

    @Column(nullable = false)
    private boolean myState;

    @Column()
    private Instant myTime;

    public MyBillBO ToMyBillBO(){
        return MyBillBO.builder().userId(userId).examTypeId(examTypeId).examDetailId(examDetailId)
                .realName(realName).examDescription(examDescription).myMoney(myMoney).myState(myState)
                .myTime(myTime).build();
    }
}
