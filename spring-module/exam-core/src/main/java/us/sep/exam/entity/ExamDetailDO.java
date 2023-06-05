package us.sep.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.sep.base.AbstractAuditBase;
import us.sep.exam.builder.ExamDetailBO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam_entry", indexes = {
        @Index(name = "exam_entry_id_index", columnList = "examId")})
public class ExamDetailDO extends AbstractAuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //考试类别id
    @Column(nullable = false, length = 32)
    private String examId;

    //考试内容
    @Column(nullable = false, length = 32)
    private String examTypeName;

    //考试时间
    @Column(nullable = false, length = 32, updatable = false)
    private String examTime;

    //考试总人数
    @Column(nullable = false, length = 32)
    private String examTotalNum;

    //考试已报名人数
    @Column(nullable = false, length = 32)
    private String examPresentNum;

    @Column(nullable = false, length = 32)
    private String examState;


    public ExamDetailBO ToExamDetailBO() {
        return ExamDetailBO.builder().examId(examId).examTypeName(examTypeName).examTime(examTime)
                .examTotalNum(examTotalNum).examPresentNum(examPresentNum).examState(examState).build();
    }

}
