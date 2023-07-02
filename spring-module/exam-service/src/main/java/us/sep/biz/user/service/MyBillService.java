package us.sep.biz.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.sep.biz.user.request.MyBillRequest;
import us.sep.user.builder.MyBillBO;
import us.sep.user.entity.MyBillDO;
import us.sep.user.repo.MyBillRepo;
import us.sep.util.enums.CommonResultCode;
import us.sep.util.exceptions.CustomizeException;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyBillService {
    @Resource
    MyBillRepo myBillRepo;

    @Transactional(rollbackFor = Exception.class)
    public MyBillBO createMyBill(MyBillRequest request){
        MyBillDO myBillDO = new MyBillDO();
        BeanUtils.copyProperties(request,myBillDO);
        myBillDO.setMyState(false);
        for(int i = 0;i < 100; i++)
        {
            if(myBillRepo.findById((long)i).isPresent())
                continue;
            else
                myBillDO.setId((long)i);
        }
        myBillRepo.save(myBillDO);
        return myBillDO.ToMyBillBO();
    }

    @Transactional(rollbackFor = Exception.class)
    public MyBillBO finishMyBill(MyBillRequest request,int pageNum ,int pageSize){
        ArrayList<MyBillDO> myBillDOList = new ArrayList<>(myBillRepo.findByUserId(request.getUserId(), PageRequest.of(pageNum, pageSize)).getContent());
        String t_detailId = request.getExamDetailId();
        MyBillDO myBillDO = new MyBillDO();
        int flag = 0;
        for (MyBillDO billDO : myBillDOList)
        {
            if (t_detailId.equals(billDO.getExamDetailId()))
            {
                myBillDO = billDO;
                flag = 1;
                break;
            }
        }
        if(flag == 1)
        {
            myBillDO.setMyState(true);
            LocalDateTime now = LocalDateTime.now();
            ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            myBillDO.setMyTime(instant);
            myBillRepo.save(myBillDO);
            return myBillDO.ToMyBillBO();
        }
        else
        {
            throw new CustomizeException(CommonResultCode.UNFOUNDED,"不存在该账单");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public MyBillBO findMyBillOnly(String userId, String examDetailId, int pageNum , int pageSize){
        ArrayList<MyBillDO> myBillDOList = new ArrayList<>(myBillRepo.findByUserId(userId, PageRequest.of(pageNum, pageSize)).getContent());
        MyBillDO myBillDO = new MyBillDO();
        int flag = 0;
        for (MyBillDO billDO : myBillDOList)
        {
            if (examDetailId.equals(billDO.getExamDetailId()))
            {
                myBillDO = billDO;
                flag = 1;
                break;
            }
        }
        if(flag == 1)
        {
            return myBillDO.ToMyBillBO();
        }
        else
        {
            throw new CustomizeException(CommonResultCode.UNFOUNDED,"不存在该账单");
        }
    }

    public List<MyBillBO> findAll(int pageNum , int pageSize){
        return myBillRepo.findAll(PageRequest.of(pageNum,pageSize)).stream()
                .map(MyBillDO::ToMyBillBO).collect(Collectors.toList());
    }

    public List<MyBillBO> findByUserId(String userId,int pageNum ,int pageSize){
        return myBillRepo.findByUserId(userId,PageRequest.of(pageNum,pageSize)).getContent().stream()
                .map(MyBillDO::ToMyBillBO).collect(Collectors.toList());
    }

    public boolean ifMyBillExist(MyBillRequest request){
        return myBillRepo.existsByUserIdAndExamDetailId(request.getUserId(), request.getExamDetailId());
    }

    private List<String> findByUserId(String userId){
        return myBillRepo.findByUserId(userId).stream().map(MyBillDO::getExamDetailId).collect(Collectors.toList());
    }
}
