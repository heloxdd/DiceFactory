package spring.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.entities.User;

public interface UsersRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
