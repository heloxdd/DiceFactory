package spring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.sql.*;
import java.util.Properties;
import javax.sql.DataSource;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GameController {

    private final DataSource dataSource;

    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired
    public GameController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/play")
    public List<String> play(@RequestBody GameRequest request) {
        PlayerDice player1 = new PlayerDice(request.getPlayer1());
        PlayerDice player2 = new PlayerDice(request.getPlayer2());
        GameFactory factory = new GameFactory(player1, player2, player1.diceAmount);

//        String url = "jdbc:postgresql://localhost:5050,localhost:5050/postgres?targetServerType=primary";
//        final Properties props = new Properties();
//        props.setProperty("user", "admin");
//        props.setProperty("password", "qwertyuiop");
//
//        try (Connection conn = DriverManager.getConnection(url, props)) {
//            System.out.println(conn.getMetaData().getDatabaseProductVersion());
//            Statement st = conn.createStatement();
//
//            st.execute("INSERT INTO games VALUES (? ? ? ? ? ? ? ? ?)");
//
//        } catch (SQLException e) {
//            log.error("e: ", e);
//            throw new RuntimeException(e);
//        }
        return factory.run();
    }

    @GetMapping("/new-user")
    public ResponseEntity<String> newPlayer(/*@RequestBody UserRequest request*/) {
        DataSource dataSource = this.dataSource;

        try (Connection conn = dataSource.getConnection()) {
            String insertc = "INSERT INTO users VALUES (?, ?)";
            PreparedStatement psmt = conn.prepareStatement(insertc);
            System.out.println(conn.getMetaData().getDatabaseProductVersion());
            psmt.executeUpdate();
            return new ResponseEntity<>("Creation Successful. ", HttpStatus.OK);

        } catch (SQLException e) {
            log.error("e: ", e);
            return new ResponseEntity<>("Database Connection Failed. ", HttpStatus.BAD_REQUEST);
        }

    }
}
