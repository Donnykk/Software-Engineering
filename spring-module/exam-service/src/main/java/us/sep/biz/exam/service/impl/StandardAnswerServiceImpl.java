package us.sep.biz.exam.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.sep.biz.exam.request.StandardAnswerRequest;
import us.sep.biz.exam.service.StandardAnswerService;
import us.sep.exam.builder.StandardAnswerBO;
import us.sep.exam.entity.StandardAnswerDO;
import us.sep.exam.repo.StandardAnswerRepo;
import us.sep.util.enums.CommonResultCode;
import us.sep.util.exceptions.CustomizeException;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StandardAnswerServiceImpl implements StandardAnswerService {
    @Resource
    StandardAnswerRepo standardAnswerRepo;

    @Transactional(rollbackFor = Exception.class)
    public StandardAnswerBO createAnswer(StandardAnswerRequest request)
    {
        StandardAnswerDO standardAnswerDO = new StandardAnswerDO();
        BeanUtils.copyProperties(request, standardAnswerDO);
        for(int i = 0;i < 100; i++)
        {
            if(standardAnswerRepo.findById((long)i).isPresent())
                continue;
            else
                standardAnswerDO.setId((long)i);
        }
        standardAnswerRepo.save(standardAnswerDO);
        return standardAnswerDO.ToStandardAnswerBO();
    }

    @Transactional(rollbackFor = Exception.class)
    public StandardAnswerBO changeAnswer(StandardAnswerRequest request)
    {
        StandardAnswerDO standardAnswerDO = standardAnswerRepo.findByExamDetailId(request.getExamDetailId());
        standardAnswerRepo.deleteByExamDetailId(request.getExamDetailId());
        StandardAnswerDO t_standardAnswerDO = new StandardAnswerDO();
        t_standardAnswerDO.setExamDetailId(standardAnswerDO.getExamDetailId());
        t_standardAnswerDO.setAnswer1(request.getAnswer1());
        t_standardAnswerDO.setAnswer2(request.getAnswer2());
        t_standardAnswerDO.setAnswer3(request.getAnswer3());
        for(int i = 0;i < 100; i++)
        {
            if(standardAnswerRepo.findById((long)i).isPresent())
                continue;
            else
                t_standardAnswerDO.setId((long)i);
        }
        standardAnswerRepo.save(t_standardAnswerDO);
        return t_standardAnswerDO.ToStandardAnswerBO();
    }

    @Transactional(rollbackFor = Exception.class)
    public StandardAnswerBO findAnswer(String examDetailId)
    {
        StandardAnswerDO standardAnswerDO = standardAnswerRepo.findByExamDetailId(examDetailId);
        return standardAnswerDO.ToStandardAnswerBO();
    }

    public List<StandardAnswerBO> findAll(int pageNum , int pageSize)
    {
        return standardAnswerRepo.findAll(PageRequest.of(pageNum,pageSize)).stream()
                .map(StandardAnswerDO::ToStandardAnswerBO).collect(Collectors.toList());
    }

    public boolean ifAnswerExist(StandardAnswerRequest request)
    {
        return standardAnswerRepo.existsByExamDetailId(request.getExamDetailId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StandardAnswerBO deleteByExamDetailId(String examDetailId)
    {
        if(!standardAnswerRepo.existsByExamDetailId(examDetailId))
            throw new CustomizeException(CommonResultCode.UNFOUNDED , "不存在该试卷！");

        StandardAnswerBO standardAnswerBO = standardAnswerRepo.findByExamDetailId(examDetailId).ToStandardAnswerBO();
        standardAnswerRepo.deleteByExamDetailId(examDetailId);
        StandardAnswerDO t_standardAnswerDO = new StandardAnswerDO();
        t_standardAnswerDO.setExamDetailId(examDetailId);
        t_standardAnswerDO.setAnswer1("$$$$$$$$$$$$$$$$$$$$$$$$$");
        t_standardAnswerDO.setAnswer2("$$");
        t_standardAnswerDO.setAnswer3("$$");
        for(int i = 0;i < 100; i++)
        {
            if(standardAnswerRepo.findById((long)i).isPresent())
                continue;
            else
                t_standardAnswerDO.setId((long)i);
        }
        standardAnswerRepo.save(t_standardAnswerDO);
        return standardAnswerBO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StandardAnswerBO realDeleteByExamDetailId(String examDetailId)
    {
        if(!standardAnswerRepo.existsByExamDetailId(examDetailId))
            throw new CustomizeException(CommonResultCode.UNFOUNDED , "不存在该试卷！");

        StandardAnswerBO standardAnswerBO = standardAnswerRepo.findByExamDetailId(examDetailId).ToStandardAnswerBO();
        standardAnswerRepo.deleteByExamDetailId(examDetailId);
        return standardAnswerBO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StandardAnswerBO updateAnswerPath(String examDetailId, String answerPath, String answerName)
    {
        StandardAnswerDO standardAnswerDO = standardAnswerRepo.findByExamDetailId(examDetailId);
        standardAnswerDO.setAnswerPath(answerPath);
        standardAnswerDO.setAnswerName(answerName);
        standardAnswerRepo.save(standardAnswerDO);
        return standardAnswerDO.ToStandardAnswerBO();
    }
}
