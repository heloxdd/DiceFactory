package spring.repositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.entities.CurGames;

public interface CurGamesRepo extends JpaRepository<CurGames, Integer> {
    CurGames findByP1ID(int p1ID);
    CurGames findByP2ID(int p2ID);
}
