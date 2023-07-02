package us.sep.exam.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import us.sep.exam.entity.StudentAnswerDO;

import java.util.List;

public interface StudentAnswerRepo extends JpaRepository<StudentAnswerDO, Long> {
    List<StudentAnswerDO>findByExamDetailId(String examDetailId);

    Page<StudentAnswerDO> findByExamDetailId(String examDetailId , Pageable request);

    StudentAnswerDO findByExamDetailIdAndUserId(String examDetailId, String userId);

    boolean existsByExamDetailIdAndUserId(String examDetailId , String userId);
}
