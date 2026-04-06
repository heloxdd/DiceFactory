package spring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import spring.repositories.*;
import spring.entities.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GameController {

    private Map<Integer, Boolean> p1Ready = new HashMap<>();
    private Map<Integer, Boolean> p2Ready = new HashMap<>();

    @PostMapping("/play")
    public List<String> play(@RequestBody GameRequest request) {
        PlayerDice player1 = new PlayerDice(request.getPlayer1());
        PlayerDice player2 = new PlayerDice(request.getPlayer2());

        GameFactory factory = new GameFactory(player1, player2, player1.diceAmount);
        return factory.run();
    }

    @Autowired
    private CurGamesRepo curGamesRepo;

    @Autowired
    private UsersRepo usersRepo;

    @PostMapping("/online/new")
    public Integer createGame() {
        CurGames newGame = new CurGames();
        curGamesRepo.save(newGame);
        p1Ready.put(newGame.getID(), false);
        p2Ready.put(newGame.getID(), false);
        return newGame.getID();
    }
    @PostMapping("/online/ready")
    public List<String> onlineReady(@RequestBody OnlineReadyRequest request) {
        CurGames game = curGamesRepo.findById(request.getGameId()).orElseThrow();
        User player = usersRepo.findById(request.getPlayerId()).orElseThrow();

        if (request.getPlayerId() == game.getP1ID()) {
            game.setP1Dice(String.join(",", request.getPlayerDice().stream().map(String::valueOf).toList()));
            p1Ready.replace(game.getID(), true);
        } else {
            game.setP2Dice(String.join(",", request.getPlayerDice().stream().map(String::valueOf).toList()));
            p2Ready.replace(game.getID(), true);
        }

        curGamesRepo.save(game);

        if (p1Ready.get(game.getID()) == true && p2Ready.get(game.getID())) {
            PlayerDice player1 = new PlayerDice(Stream.of(game.getP1Dice().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList()));
            PlayerDice player2 = new PlayerDice(Stream.of(game.getP2Dice().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));
            GameFactory factory = new GameFactory(player1, player2, player1.diceAmount);
            return factory.run();
        } else {
            return new ArrayList<>();
        }
    }
}
