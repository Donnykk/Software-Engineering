package us.sep.biz.exam.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDetailRequest {

    private String examId;

    private String examTypeName;

    private String examTime;

    private String examTotalNum;

    private String examPresentNum;

    private String examState;
}
