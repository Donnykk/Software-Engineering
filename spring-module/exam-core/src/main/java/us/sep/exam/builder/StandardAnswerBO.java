package us.sep.exam.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardAnswerBO {
    private String examDetailId;

    private String answer1;

    private String answer2;

    private String answer3;

    private String answerPath;

    private String answerName;
}
