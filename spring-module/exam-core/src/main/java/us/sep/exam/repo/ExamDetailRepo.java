package us.sep.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.sep.exam.entity.ExamDetailDO;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamDetailRepo extends JpaRepository<ExamDetailDO,Long> {

    List<ExamDetailDO> findByExamId(String id);

    void deleteAllByExamId(String id);

    void deleteByExamId(String id);

    boolean existsByExamId(String id);
}
