package us.sep.exam.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import us.sep.exam.entity.StandardAnswerDO;

import java.util.List;

public interface StandardAnswerRepo extends JpaRepository<StandardAnswerDO, Long>{
    StandardAnswerDO findByExamDetailId(String examDetailId);

    boolean existsByExamDetailId(String examDetailId);

    void deleteByExamDetailId(String examDetailId);
}
