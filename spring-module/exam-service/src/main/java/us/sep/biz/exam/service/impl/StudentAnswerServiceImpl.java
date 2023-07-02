package us.sep.biz.exam.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.sep.biz.exam.request.StudentAnswerRequest;
import us.sep.biz.exam.service.StudentAnswerService;
import us.sep.exam.builder.StudentAnswerBO;
import us.sep.exam.entity.StudentAnswerDO;
import us.sep.exam.repo.StudentAnswerRepo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentAnswerServiceImpl implements StudentAnswerService {
    @Resource
    StudentAnswerRepo studentAnswerRepo;

    @Transactional(rollbackFor = Exception.class)
    public StudentAnswerBO updateAnswer(StudentAnswerRequest request)
    {
        StudentAnswerDO studentAnswerDO = new StudentAnswerDO();
        if(studentAnswerRepo.existsByExamDetailIdAndUserId(request.getExamDetailId(), request.getUserId()))
        {
            studentAnswerDO = studentAnswerRepo.findByExamDetailIdAndUserId(request.getExamDetailId(), request.getUserId());
            studentAnswerDO.setAnswer1(request.getAnswer1());
            studentAnswerDO.setAnswer2(request.getAnswer2());
            studentAnswerDO.setAnswer3(request.getAnswer3());
            studentAnswerDO.setAnswerTranslate(request.getAnswerTranslate());
            studentAnswerDO.setAnswerArticle(request.getAnswerArticle());
        }
        else
        {
            BeanUtils.copyProperties(request, studentAnswerDO);
            studentAnswerDO.setFinish1(false);
            studentAnswerDO.setFinish2(false);
            studentAnswerDO.setFinish3(false);
            studentAnswerDO.setFinishTotal(false);
            studentAnswerDO.setScore(0);
            for(int i = 0;i < 100; i++)
            {
                if(studentAnswerRepo.findById((long)i).isPresent())
                    continue;
                else
                    studentAnswerDO.setId((long)i);
            }
        }
        studentAnswerRepo.save(studentAnswerDO);
        return studentAnswerDO.ToStudentAnswerBO();
    }

    @Transactional(rollbackFor = Exception.class)
    public StudentAnswerBO findAnswer(String examDetailId, String userId)
    {
        StudentAnswerDO studentAnswerDO = studentAnswerRepo.findByExamDetailIdAndUserId(examDetailId, userId);
        return studentAnswerDO.ToStudentAnswerBO();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<StudentAnswerBO> findAnswerTotal(String examDetailId)
    {
        List<StudentAnswerDO> studentAnswerDOS = studentAnswerRepo.findByExamDetailId(examDetailId);
        return studentAnswerDOS.stream().map(StudentAnswerDO::ToStudentAnswerBO).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public StudentAnswerBO updateScore(String examDetailId, String userId, int score)
    {
        StudentAnswerDO studentAnswerDO = studentAnswerRepo.findByExamDetailIdAndUserId(examDetailId, userId);
        studentAnswerDO.setScore(score);
        studentAnswerRepo.save(studentAnswerDO);
        return studentAnswerDO.ToStudentAnswerBO();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<StudentAnswerBO> findAll(int pageNum , int pageSize)
    {
        return studentAnswerRepo.findAll(PageRequest.of(pageNum,pageSize)).stream()
                .map(StudentAnswerDO::ToStudentAnswerBO).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public int markingPaper1(String examDetailId, String userId, String answer1)
    {
        StudentAnswerDO studentAnswerDO = studentAnswerRepo.findByExamDetailIdAndUserId(examDetailId, userId);
        String t_answer1 = studentAnswerDO.getAnswer1();
        for(int i = 0;i < answer1.length(); i++)
        {
            if(t_answer1.charAt(i) == answer1.charAt(i))
            {
                studentAnswerDO.setScore(studentAnswerDO.getScore() + 1);
            }
        }
        studentAnswerDO.setFinish1(true);
        studentAnswerRepo.save(studentAnswerDO);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int markingPaper2(String examDetailId, String userId, int score)
    {
        StudentAnswerDO studentAnswerDO = studentAnswerRepo.findByExamDetailIdAndUserId(examDetailId, userId);
        studentAnswerDO.setScore(studentAnswerDO.getScore() + score);
        studentAnswerDO.setFinish2(true);
        studentAnswerRepo.save(studentAnswerDO);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int markingPaper3(String examDetailId, String userId, int score)
    {
        StudentAnswerDO studentAnswerDO = studentAnswerRepo.findByExamDetailIdAndUserId(examDetailId, userId);
        studentAnswerDO.setScore(studentAnswerDO.getScore() + score);
        studentAnswerDO.setFinish3(true);
        studentAnswerRepo.save(studentAnswerDO);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int outputScore(String examDetailId, int pageNum ,int pageSize)
    {
        ArrayList<StudentAnswerDO> studentAnswerDOArrayList = new ArrayList<>
                (studentAnswerRepo.findByExamDetailId(examDetailId, PageRequest.of(pageNum,pageSize)).getContent());
        boolean flag = true;
        for(StudentAnswerDO tAnswerDO : studentAnswerDOArrayList)
        {
            if(!tAnswerDO.isFinish1() || !tAnswerDO.isFinish2() || !tAnswerDO.isFinish3())
            {
                flag = false;
                break;
            }
        }
        if(flag)
        {
            for(StudentAnswerDO tAnswerDO : studentAnswerDOArrayList)
            {
                tAnswerDO.setFinishTotal(true);
                studentAnswerRepo.save(tAnswerDO);
            }
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int isComplete(String examDetailId, String userId)
    {
        if(studentAnswerRepo.existsByExamDetailIdAndUserId(examDetailId, userId))
        {
            return 2;
        }
        else
        {
            return 1;
        }
    }
}
