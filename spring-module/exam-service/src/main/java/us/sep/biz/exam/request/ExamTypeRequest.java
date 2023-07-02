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
    private String examTypeName;

    @NotBlank(message = "���Ա������Ʋ���Ϊ��")
    private String examLimit;

    @NotBlank(message = "��������������Ϣ����Ϊ��")
    private String examTypeDescription;

    private String examTypeId;
}
