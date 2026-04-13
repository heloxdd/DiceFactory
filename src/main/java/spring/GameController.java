package spring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import spring.repositories.*;
import spring.entities.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GameController {

    private final Map<Integer, Boolean> p1Ready = new HashMap<>();
    private final Map<Integer, Boolean> p2Ready = new HashMap<>();
    private final Map<Integer, List<String>> results = new HashMap<>();

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

    @PostMapping("/online/new/{playerId}")
    public Integer createGame(@PathVariable int playerId) {
        CurGames newGame = new CurGames();
        newGame.setP1ID(playerId);
        curGamesRepo.save(newGame);
        p1Ready.put(newGame.getID(), false);
        p2Ready.put(newGame.getID(), false);
        return newGame.getID();
    }

    @PostMapping("/online/ready")
    public void onlineReady(@RequestBody OnlineReadyRequest request) {
        System.out.println("game ID: " + request.getGameId());
        System.out.println("player ID: " + request.getPlayerId());
        CurGames game = curGamesRepo.findById(request.getGameId()).orElseThrow();
        User player = usersRepo.findById(request.getPlayerId()).orElseThrow();

        if (request.getPlayerId() == game.getP1ID()) {
            game.setP1Dice(request.getPlayerDice());
            p1Ready.replace(game.getID(), true);
        } else {
            game.setP2Dice(request.getPlayerDice());
            p2Ready.replace(game.getID(), true);
        }

        curGamesRepo.save(game);

        if (p1Ready.get(game.getID()) == true && p2Ready.get(game.getID())) {
            PlayerDice player1 = new PlayerDice(Arrays.stream(game.getP1Dice().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));
            PlayerDice player2 = new PlayerDice(Arrays.stream(game.getP2Dice().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));
            System.out.println("Player 1 listified dice: "+player1.getDice());
            System.out.println("Player 2 listified dice: "+player2.getDice());
            GameFactory factory = new GameFactory(player1, player2, player1.diceAmount);
            results.put(game.getID(), factory.run());
        }
    }

    @GetMapping("/online/getresults/{gameId}")
    public List<String> getResults(@PathVariable int gameId) {
        return results.getOrDefault(gameId, new ArrayList<>());
    }
}
