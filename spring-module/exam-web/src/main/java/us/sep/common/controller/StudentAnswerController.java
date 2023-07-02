package us.sep.common.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import us.sep.biz.exam.request.StudentAnswerRequest;
import us.sep.exam.builder.StudentAnswerBO;
import us.sep.biz.exam.service.StudentAnswerService;
import us.sep.common.annotion.LoggerName;
import us.sep.util.common.Result;
import us.sep.util.enums.CommonResultCode;
import us.sep.util.log.Log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/studentAnswer")
public class StudentAnswerController {
    @Resource
    StudentAnswerService studentAnswerService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<StudentAnswerBO> getAnswerByExamDetailIdAndUserId(String examDetailId, String userId, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.findAnswer(examDetailId,userId));
    }

    @GetMapping("/detail")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<List<StudentAnswerBO>> getTotalAnswer(String examDetailId, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.findAnswerTotal(examDetailId));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<List<StudentAnswerBO>> getAllAnswer(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.findAll(pageNum, pageSize));
    }

    @GetMapping("/state")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<Integer> judgeComplete(String examDetailId, String userId, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.isComplete(examDetailId,userId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<StudentAnswerBO> updateAnswer(@Valid StudentAnswerRequest request, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.updateAnswer(request));
    }

    @PostMapping("/getScore")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<StudentAnswerBO> updateScore(String examDetailId, String userId, int score, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.updateScore(examDetailId, userId, score));
    }

    @PostMapping("/scoreOut")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<Integer> outputScoreByExamDetailId(String examDetailId, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.outputScore(examDetailId, pageNum, pageSize));
    }

    @PostMapping("/score1")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<Integer> markScore1ByExamDetailIdAndUserId(String examDetailId, String userId, String answer1, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.markingPaper1(examDetailId,userId,answer1));
    }

    @PostMapping("/score2")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<Integer> markScore2ByExamDetailIdAndUserId(String examDetailId, String userId, int score2, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.markingPaper2(examDetailId,userId,score2));
    }

    @PostMapping("/score3")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @Log(loggerName = LoggerName.WEB_DIGEST)
    public Result<Integer> markScore3ByExamDetailIdAndUserId(String examDetailId, String userId, int score3, HttpServletRequest httpServletRequest)
    {
        return new Result<>(true, CommonResultCode.SUCCESS.getCode(), CommonResultCode.SUCCESS.getMessage(),studentAnswerService.markingPaper3(examDetailId,userId,score3));
    }
}
