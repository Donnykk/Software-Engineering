package us.sep.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.sep.exam.entity.ExamTypeDO;

import java.util.Optional;

@Repository
public interface ExamTypeRepo extends JpaRepository<ExamTypeDO,Long> {

    Optional<ExamTypeDO> findByExamId(String examTypeId);

    Optional<ExamTypeDO> findByExamType(String name);

    boolean existsByExamId(String examTypeId);

    boolean existsByExamType(String examType);

    void deleteByExamId(String examTypeId);

    void deleteByExamType(String examType);
}
