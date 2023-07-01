package us.sep.biz.exam.service;

import org.springframework.stereotype.Service;
import us.sep.biz.exam.request.StandardAnswerRequest;
import us.sep.exam.builder.StandardAnswerBO;

import java.util.List;

public interface StandardAnswerService {
    StandardAnswerBO createAnswer(StandardAnswerRequest request);

    StandardAnswerBO changeAnswer(StandardAnswerRequest request);

    StandardAnswerBO findAnswer(String examDetailId);

    List<StandardAnswerBO> findAll(int pageNum , int pageSize);

    boolean ifAnswerExist(StandardAnswerRequest request);

    StandardAnswerBO deleteByExamDetailId(String examDetailId);

    StandardAnswerBO realDeleteByExamDetailId(String examDetailId);

    StandardAnswerBO updateAnswerPath(String examDetailId, String answerPath, String answerName);
}
