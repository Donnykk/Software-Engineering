package us.sep.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.sep.exam.builder.StandardAnswerBO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "standard_answer", indexes = {
        @Index(name = "examId_index", columnList = "examDetailId")})
public class StandardAnswerDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 32, updatable = false)
    private String examDetailId;

    @Column(nullable = false,length = 32, updatable = false)
    private String answer1;

    @Column(nullable = false,length = 32, updatable = false)
    private String answer2;

    @Column(nullable = false,length = 32, updatable = false)
    private String answer3;

    @Column()
    private String answerPath;

    public StandardAnswerBO ToStandardAnswerBO(){
        return StandardAnswerBO.builder().examDetailId(examDetailId).answer1(answer1)
                .answer2(answer2).answer3(answer3).answerPath(answerPath).build();
    }
}
