package us.sep.biz.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyBillRequest {
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @NotBlank(message = "考试类型id不能为空")
    private String examTypeId;

    @NotBlank(message = "考试详细id不能为空")
    private String examDetailId;

    @NotBlank(message = "真名不能为空")
    private String realName;

    @NotBlank(message = "考试描述不能为空")
    private String examDescription;

    private int myMoney;

    private boolean myState;

    private Instant myTime;
}
