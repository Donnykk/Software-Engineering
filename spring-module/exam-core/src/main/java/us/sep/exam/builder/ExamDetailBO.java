package us.sep.exam.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDetailBO  {

    private String examId;

    private String examTypeName;

    private String examTime;

    private String examTotalNum;

    private String examPresentNum;

    private String examState;

}
