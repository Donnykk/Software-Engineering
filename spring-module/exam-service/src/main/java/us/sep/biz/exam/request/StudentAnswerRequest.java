package us.sep.biz.exam.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAnswerRequest {
    @NotBlank(message = "考试详细id不能为空")
    private String examDetailId;

    @NotBlank(message = "用户id不能为空")
    private String userId;

    @NotBlank(message = "答案1不能为空")
    private String answer1;

    @NotBlank(message = "答案2不能为空")
    private String answer2;

    @NotBlank(message = "答案3不能为空")
    private String answer3;

    @NotBlank(message = "翻译不能为空")
    private String answerTranslate;

    @NotBlank(message = "写作不能为空")
    private String answerArticle;

    private boolean finish1;

    private boolean finish2;

    private boolean finish3;

    private boolean finishTotal;

    private int score;
}
