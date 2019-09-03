package kz.yeltayev.utility.repository;

import kz.yeltayev.utility.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByLogin(String login);

    List<User> findUsersByOwner(String owner);
}
