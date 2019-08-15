package kz.yeltayev.utility.repository;

import kz.yeltayev.utility.entity.AuthRequest;
import kz.yeltayev.utility.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
}
