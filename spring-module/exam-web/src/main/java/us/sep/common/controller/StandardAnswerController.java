package us.sep.common.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import us.sep.biz.exam.request.StandardAnswerRequest;
import us.sep.biz.exam.service.StandardAnswerService;
import us.sep.common.annotion.AvoidRepeatableCommit;
import us.sep.common.annotion.LoggerName;
import us.sep.exam.builder.StandardAnswerBO;
import us.sep.util.common.Result;
import us.sep.util.enums.CommonResultCode;
import us.sep.util.log.Log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/standardAnswer")
public class StandardAnswerController {
    @Resource
    StandardAnswerService standardAnswerService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<StandardAnswerBO> getStandardAnswerBOByExamDetailId(String examDetailId , HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),standardAnswerService.findAnswer(examDetailId));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<List<StandardAnswerBO>> getAllStandardAnswerBO(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),standardAnswerService.findAll(pageNum, pageSize));
    }

    @AvoidRepeatableCommit
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<StandardAnswerBO> createStandardAnswer(@Valid StandardAnswerRequest standardAnswerRequest, HttpServletRequest httpServletRequest)
    {
        if(!standardAnswerService.ifAnswerExist(standardAnswerRequest))
            return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),standardAnswerService.createAnswer(standardAnswerRequest));

        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), "该答案已经生成过，请前往修改界面");
    }

    @PostMapping("/change")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<StandardAnswerBO> changeStandardAnswer(@Valid StandardAnswerRequest standardAnswerRequest, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),standardAnswerService.changeAnswer(standardAnswerRequest));
    }

    @AvoidRepeatableCommit
    @PostMapping("/photo")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<StandardAnswerBO> uploadImg(@RequestParam("file") MultipartFile multipartFile, @RequestParam("examDetailId") String examDetailId, HttpServletRequest request) throws IOException {
        System.out.println(multipartFile);

        /*
         * 编码为字符串*/
        String s = Base64Utils.encodeToString(multipartFile.getBytes());
        System.out.println("s:"+s);

        /* *
         *2.解码成字节数组
         */
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(s);

        /*
         * 3.字节流转文件
         */
        String d = System.getProperty("user.dir");
        File fileMkdir = new File(d+"\\..\\vue-module\\src\\assets\\answers");
        if (!fileMkdir.exists()){
            fileMkdir.mkdir();
        }
        String pathName = fileMkdir.getPath() + "\\" + multipartFile.getOriginalFilename();

        String t_path = "../../../assets/answers/" + multipartFile.getOriginalFilename();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathName);
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(), standardAnswerService.updateAnswerPath(examDetailId, t_path, multipartFile.getOriginalFilename()));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<StandardAnswerBO> deleteByExamDetailId(String examDetailId , HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),standardAnswerService.deleteByExamDetailId(examDetailId));
    }

    @DeleteMapping("/real")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<StandardAnswerBO> realDeleteByExamDetailId(String examDetailId , HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),standardAnswerService.realDeleteByExamDetailId(examDetailId));
    }
}
