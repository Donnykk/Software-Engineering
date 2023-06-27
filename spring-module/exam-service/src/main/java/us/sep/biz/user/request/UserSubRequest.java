package us.sep.biz.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSubRequest {

    @NotBlank(message = "�û�id����Ϊ��")
    private String userId;

    @NotBlank(message = "Ƶ��id����Ϊ��")
    private String channelId;

    private String userChannelId;
}
