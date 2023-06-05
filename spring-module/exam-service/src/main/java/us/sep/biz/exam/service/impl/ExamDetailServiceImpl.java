package us.sep.biz.exam.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import us.sep.base.idfactory.BizIdFactory;
import us.sep.biz.common.util.CheckDateUtil;
import us.sep.biz.common.util.ObjectUtil;
import us.sep.biz.common.util.RedisUtil;
import us.sep.biz.exam.request.ExamDetailRequest;
import us.sep.biz.exam.service.ExamDetailService;
import us.sep.exam.builder.ExamDetailBO;
import us.sep.exam.entity.ExamDetailDO;
import us.sep.exam.repo.ExamDetailRepo;
import us.sep.util.enums.CommonResultCode;
import us.sep.util.exceptions.CustomizeException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static us.sep.biz.common.config.RedisConfig.*;

/**
 * @author kana-cr
 * @version 2020/10/15 9:38
 */
@Service
@Slf4j
public class ExamDetailServiceImpl implements ExamDetailService {

    @Resource
    private ExamDetailRepo examDetailRepo;

    @Resource
    private BizIdFactory bizIdFactory;

    @Resource
    private RedisUtil redisUtil;

    private static final long SLEEP_TIME = 200;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ExamDetailBO save(ExamDetailRequest request) {
        ExamDetailDO examDetailDO = new ExamDetailDO();
        BeanUtils.copyProperties(request, examDetailDO);
        examDetailDO.setExamId(bizIdFactory.getExamTypeDetailId());
        examDetailDO = examDetailRepo.save(examDetailDO);
        //写入cache
        redisUtil.hPut(EXAM_DETAIL, EXAM_DETAIL_ID + examDetailDO.getExamId(), JSON.toJSONString(examDetailDO));
        redisUtil.zAdd(EXAM_DETAIL_PAGE, EXAM_DETAIL_ID + examDetailDO.getExamId(), examDetailDO.getId());

        return examDetailDO.ToExamDetailBO();
    }

    @Override
    public List<ExamDetailBO> find(ExamDetailRequest request, int pageNum, int pageSize) {

        //只需分页查询
        if (ObjectUtil.checkObjAllFieldsIsNull(request)) {
            if (redisUtil.hasKey(EXAM_DETAIL) && redisUtil.hasKey(EXAM_DETAIL_PAGE)) {
                //取自增主键id分页区间
                Set<String> ExamIds = redisUtil.zRange(EXAM_DETAIL_PAGE, ((long) pageNum * pageSize), (long) (pageNum + 1) * pageSize);
                List<Object> serializeList = redisUtil.hMultiGet(EXAM_DETAIL, new ArrayList<>(ExamIds));
                List<ExamDetailDO> examDetails = new ArrayList<>();
                for (Object obj : serializeList) {
                    examDetails.add(JSON.parseObject((String) obj, ExamDetailDO.class));
                }
                //help gc
                ExamIds = null;
                serializeList = null;
                return examDetails.stream().map(ExamDetailDO::ToExamDetailBO).collect(Collectors.toList());
            } else {
                List<ExamDetailDO> examDetails = examDetailRepo.findAll();
                for (ExamDetailDO examDetailDO : examDetails) {
                    //写入cache
                    redisUtil.hPut(EXAM_DETAIL, EXAM_DETAIL_ID + examDetailDO.getExamId(), JSON.toJSONString(examDetailDO));
                    redisUtil.zAdd(EXAM_DETAIL_PAGE, EXAM_DETAIL_ID + examDetailDO.getExamId(), examDetailDO.getId());
                }
                return examDetails.subList(pageNum * pageSize, ((pageNum + 1) * pageSize) - 1).
                        stream().map(ExamDetailDO::ToExamDetailBO).collect(Collectors.toList());
            }
        }

        ExamDetailDO examDetailDO = new ExamDetailDO();

        if (!StringUtils.isEmpty(request.getExamId())) {
            List<ExamDetailDO> list = examDetailRepo.findByExamId(request.getExamId());
            List<ExamDetailBO> new_list = new ArrayList<>();
            for (ExamDetailDO ed : list) {
                new_list.add(ed.ToExamDetailBO());
            }
            return new_list;
        }

        if (!StringUtils.isEmpty(request.getExamTypeName()))
            examDetailDO.setExamTypeName(request.getExamTypeName());

        if (!StringUtils.isEmpty(request.getExamTime()))
            examDetailDO.setExamTime(request.getExamTime());

        if (!StringUtils.isEmpty(request.getExamTotalNum()))
            examDetailDO.setExamTotalNum(request.getExamTotalNum());

        if (!StringUtils.isEmpty(request.getExamPresentNum()))
            examDetailDO.setExamPresentNum(request.getExamPresentNum());

        if (!StringUtils.isEmpty(request.getExamState()))
            examDetailDO.setExamState(request.getExamState());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("ExamId")
                .withMatcher("examTypeName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("examExamTime", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("examTotalNum", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("examPresentNum", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("examState", ExampleMatcher.GenericPropertyMatchers.contains());


        Example<ExamDetailDO> example = Example.of(examDetailDO, matcher);

        return examDetailRepo.findAll(example, PageRequest.of(pageNum, pageSize)).getContent().stream()
                .map(ExamDetailDO::ToExamDetailBO).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(ExamDetailRequest request) throws InterruptedException {

        if (!examDetailRepo.existsByExamId(request.getExamId()))
            throw new CustomizeException(CommonResultCode.ILLEGAL_PARAMETERS, "该考试信息不存在");

        ExamDetailDO examDetailDO = examDetailRepo.findByExamId(request.getExamId()).get(0);

        if (!StringUtils.isEmpty(request.getExamTypeName()))
            examDetailDO.setExamTypeName(request.getExamTypeName());

        if (!StringUtils.isEmpty(request.getExamTime()))
            examDetailDO.setExamTime(request.getExamTime());


        if (!StringUtils.isEmpty(request.getExamTotalNum()))
            examDetailDO.setExamTotalNum(request.getExamTotalNum());

        if (!StringUtils.isEmpty(request.getExamPresentNum()) && !StringUtils.isEmpty(request.getExamPresentNum()))
            CheckDateUtil.checkDateFormat(request.getExamPresentNum(), request.getExamPresentNum());


        redisUtil.hDelete(EXAM_DETAIL, EXAM_DETAIL_ID + request.getExamId());
        redisUtil.zRemove(EXAM_DETAIL_PAGE, EXAM_DETAIL_ID + request.getExamId());
        examDetailRepo.save(examDetailDO);

        //写入cache
        redisUtil.hPut(EXAM_DETAIL, EXAM_DETAIL_ID + examDetailDO.getExamId(), JSON.toJSONString(examDetailDO));
        redisUtil.zAdd(EXAM_DETAIL_PAGE, EXAM_DETAIL_ID + examDetailDO.getExamId(), examDetailDO.getId());

          /*Thread.sleep(SLEEP_TIME);
          redisUtil.hDelete(EXAM_DETAIL,EXAM_DETAIL_ID + request.getExamId());
          redisUtil.zRemove(EXAM_DETAIL_PAGE,EXAM_DETAIL_ID + request.getExamId());*/


    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByExamTypeId(String examTypeId) {

        if (examDetailRepo.existsByExamId(examTypeId)) {
            examDetailRepo.deleteAllByExamId(examTypeId);
            List<ExamDetailDO> list = examDetailRepo.findByExamId(examTypeId);
            for (ExamDetailDO examDetail : list) {
                redisUtil.hDelete(EXAM_DETAIL, EXAM_DETAIL_ID + examDetail.getExamId());
                redisUtil.zRemove(EXAM_DETAIL_PAGE, EXAM_DETAIL_ID + examDetail.getExamId());
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByExamId(String ExamId) {
        if (examDetailRepo.existsByExamId(ExamId)) {
            examDetailRepo.deleteByExamId(ExamId);
            redisUtil.hDelete(EXAM_DETAIL, EXAM_DETAIL_ID + ExamId);
            redisUtil.zRemove(EXAM_DETAIL_PAGE, EXAM_DETAIL_ID + ExamId);
        }
    }


}
