package us.sep.biz.exam.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamTypeRequest {

    @NotBlank(message = "��������������Ϊ��")
    private String examType;

    @NotBlank(message = "����ʱ�䲻��Ϊ��")
    private String examTime;

    @NotBlank(message = "���Ե�ַ����Ϊ��")
    private String examAddress;

    private String examTypeId;
}
