package us.sep.exam.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAnswerBO {
    private String examDetailId;

    private String userId;

    private String answer1;

    private String answer2;

    private String answer3;

    private String answerTranslate;

    private String answerArticle;

    private boolean finish1;

    private boolean finish2;

    private boolean finish3;

    private boolean finishTotal;

    private int score;
}
