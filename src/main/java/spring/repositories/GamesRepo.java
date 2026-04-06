package spring.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.entities.Games;
import java.time.LocalDateTime;

public interface GamesRepo extends JpaRepository<Games, Integer> {
    Games findByP1ID(int p1ID);
    Games findByP2ID(int p2ID);
    Games findByWinner(int winner);
    Games findByDate(LocalDateTime date);
}
