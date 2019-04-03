package ru.otus.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.User;

/**
 * @author Igor on 14.12.18.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
