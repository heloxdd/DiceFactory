package spring;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GameController {

    @PostMapping("/play")
    public List<String> play(@RequestBody GameRequest request) {
        PlayerDice player1 = new PlayerDice(request.getPlayer1());
        PlayerDice player2 = new PlayerDice(request.getPlayer2());

        GameFactory factory = new GameFactory(player1, player2, player1.diceAmount);
        return factory.run();
    }

    @GetMapping("/test")
    public String test() {
        return "hello";
    }
}
