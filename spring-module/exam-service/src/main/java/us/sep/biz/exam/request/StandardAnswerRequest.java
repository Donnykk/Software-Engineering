package us.sep.biz.exam.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardAnswerRequest {
    @NotBlank(message = "考试详细id不能为空")
    private String examDetailId;

    @NotBlank(message = "答案1不能为空")
    private String answer1;

    @NotBlank(message = "答案2不能为空")
    private String answer2;

    @NotBlank(message = "答案3不能为空")
    private String answer3;

    private String answerPath;

    private String answerName;
}
