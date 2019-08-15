package kz.yeltayev.utility.repository;

import kz.yeltayev.utility.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {

    @Query("select a from AccountDetail a where a.account.accountNumber = ?1")
    List<AccountDetail> fetchDetailsForAccount(Long accountNumber);
}
