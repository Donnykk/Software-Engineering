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

    private String examDetailId;

    @NotBlank(message = "��������id����Ϊ��")
    @Size(min = 30 , max = 30 ,message = "��������id����Ϊ32λ")
    private String examTypeId;

    @NotBlank(message = "����˵������Ϊ��")
    private String examDescription;

    @NotBlank(message = "���Կ�ʼʱ�䲻��Ϊ��")
    private String examStartTime;

    @NotBlank(message = "���Խ���ʱ�䲻��Ϊ��")
    private String examEndTime;

    @NotBlank(message = "����λ�ò���Ϊ��")
    private String examLocation;

    @NotBlank(message = "�ɼ�����ʱ�乫��")
    private String examAnnounce;
}
