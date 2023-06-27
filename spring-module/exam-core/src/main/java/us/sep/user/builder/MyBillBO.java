package us.sep.user.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @Author 郑向鹏
 * @Date  2023/6/4 19:52
 **/

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyBillBO {
    private String userId;

    private String examTypeId;

    private String examDetailId;

    private String realName;

    private String examDescription;

    private int myMoney;

    private boolean myState;

    private Instant myTime;
}
