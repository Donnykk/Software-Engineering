package us.sep.exam.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author kana-cr
 * @Date  2020/10/10 9:15
 **/

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamTypeBO {

    private String examId;

    private String examTime;

    private String examType;

    private String examAddress;

    private int examNum;
}
