package kz.yeltayev.utility.repository;

import kz.yeltayev.utility.model.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {

    List<Access> findAccessesByUser_Id(Long userId);
}
