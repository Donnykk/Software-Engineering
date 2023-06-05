package us.sep.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.sep.base.AbstractAuditBase;
import us.sep.exam.builder.ExamTypeBO;

import javax.persistence.*;

/**
 * @Author kana-cr
 * @Date 2020/10/10 9:58
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam_type", indexes = {
        @Index(name = "exam_type_id_index", columnList = "examId")})
public class ExamTypeDO extends AbstractAuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String examId;

    @Column(nullable = false, length = 32)
    private String examTime;

    @Column(nullable = false, length = 32)
    private String examAddress;

    @Column(nullable = false, length = 32)
    private String examType;

    @Column(nullable = false)
    private int examNum;

    public ExamTypeBO ToExamTypeBO() {
        return ExamTypeBO.builder().examId(examId).examTime(examTime).examAddress(examAddress)
                .examType(examType).examNum(examNum).build();
    }

}
