package kz.yeltayev.utility.repository;

import kz.yeltayev.utility.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.accountNumber = ?1 or a.accountName = ?1 or a.counterNumber = ?1 or a.address = ?1")
    List<Account> findAccounts(String query);
}
