package us.sep.user.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import us.sep.user.entity.MyBillDO;

import java.util.List;

public interface MyBillRepo extends JpaRepository<MyBillDO,Long> {
    List<MyBillDO> findByUserId(String userId);

    Page<MyBillDO> findByUserId(String userId , Pageable request);

    boolean existsByUserIdAndExamDetailId(String userId , String examDetailId);
}
