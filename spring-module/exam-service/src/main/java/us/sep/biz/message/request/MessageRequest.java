package us.sep.biz.message.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    @NotBlank(message = "�����˲���Ϊ��")
    private String publisher;

    @NotBlank(message = "��Ϣ���岻��Ϊ��")
    private String content;

    @NotBlank(message = "Ƶ��������Ϊ��")
    private String channel;

    @NotBlank(message = "�������Ͳ���Ϊ��")
    private String examType;

    @NotBlank(message = "����˵������Ϊ��")
    private String examDescription;

    @NotBlank(message = "�Ƿ񷢲���Ϣ��������Ϊ��")
    //0���ݲ����� 1�Ƿ���
    private boolean ifPublish;
}
