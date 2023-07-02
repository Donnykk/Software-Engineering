package us.sep.biz.exam.service;

import us.sep.biz.exam.request.StudentAnswerRequest;
import us.sep.exam.builder.StudentAnswerBO;

import java.util.List;

public interface StudentAnswerService {
    StudentAnswerBO updateAnswer(StudentAnswerRequest request);

    StudentAnswerBO findAnswer(String examDetailId, String userId);

    int markingPaper1(String examDetailId, String userId, String answer1);

    int markingPaper2(String examDetailId, String userId, int score);

    int markingPaper3(String examDetailId, String userId, int score);

    int outputScore(String examDetailId,int pageNum ,int pageSize);

    List<StudentAnswerBO> findAll(int pageNum , int pageSize);

    int isComplete(String examDetailId, String userId);
}
