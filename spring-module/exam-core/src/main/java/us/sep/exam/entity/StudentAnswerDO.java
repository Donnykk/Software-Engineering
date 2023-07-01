package us.sep.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.sep.exam.builder.StudentAnswerBO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_answer", indexes = {
        @Index(name = "userId_index", columnList = "userId")})
public class StudentAnswerDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 32, updatable = false)
    private String examDetailId;

    @Column(nullable = false,length = 32, updatable = false)
    private String userId;

    @Column(nullable = false,length = 32, updatable = false)
    private String answer1;

    @Column(nullable = false,length = 32, updatable = false)
    private String answer2;

    @Column(nullable = false,length = 32, updatable = false)
    private String answer3;

    @Column(nullable = false,length = 1024, updatable = false)
    private String answerTranslate;

    @Column(nullable = false,length = 1024, updatable = false)
    private String answerArticle;

    @Column()
    private boolean finish1;

    @Column()
    private boolean finish2;

    @Column()
    private boolean finish3;

    @Column()
    private boolean finishTotal;

    @Column(nullable = false)
    private int score;

    public StudentAnswerBO ToStudentAnswerBO(){
        return StudentAnswerBO.builder().examDetailId(examDetailId).answer1(answer1)
                .answer2(answer2).answer3(answer3).answerTranslate(answerTranslate)
                .answerArticle(answerArticle).finish1(finish1).finish2(finish2)
                .finish3(finish3).finishTotal(finishTotal).userId(userId).score(score).build();
    }
}
