package us.sep.biz.exam.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamTypeRequest {

    @NotBlank(message = "考试类型名不能为空")
    private String examType;

    @NotBlank(message = "考试时间不能为空")
    private String examTime;

    @NotBlank(message = "考试地址不能为空")
    private String examAddress;

    private String examTypeId;
}
