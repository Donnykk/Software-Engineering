package us.sep.biz.message.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelRequest {


    private String channelId;

    @NotBlank(message = "Ƶ��������Ϊ��")
    private String channel;

    @NotBlank(message = "��������id����Ϊ��")
    private String examTypeId;
}
