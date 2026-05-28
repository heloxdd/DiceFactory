package spring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
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

        int winner;
        PlayerDice player1 = new PlayerDice(request.getP1Dice());
        PlayerDice player2 = new PlayerDice(request.getP2Dice());
        GameFactory factory = new GameFactory(player1, player2, player1.diceAmount);
        List<String> result = factory.run();

        winner = switch (factory.getWinner()) {

            case "p1" -> request.getP1ID();
            case "p2" -> request.getP2ID();
            default -> 7629157;

        };

        PreparedStatement psmt;
        try (Connection conn = dataSource.getConnection()) {

            psmt = conn.prepareStatement("SELECT MAX(id) FROM games");
            ResultSet rs = psmt.executeQuery();
            rs.next();
            int newId = rs.getInt(1)+1;

            psmt = conn.prepareStatement("INSERT INTO games VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            psmt.setInt(1, newId);
            psmt.setInt(2, request.getP1ID());
            psmt.setInt(3, request.getP2ID());
            psmt.setString(4, request.getP1Dice().toString());
            psmt.setString(5, request.getP2Dice().toString());
            psmt.setInt(6, factory.getP1Points());
            psmt.setInt(7, factory.getP2Points());
            psmt.setTimestamp(8, Timestamp.from(Instant.now()));
            psmt.setInt(9, winner);
            psmt.executeUpdate();

        }
        catch (SQLException e) {

            log.error("error: ", e);

        }

        return result;

    }

    @PostMapping("/new-user")
    public ResponseEntity<String> newPlayer(@RequestBody UserRequest request) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement psmt;
            ResultSet resultSet;

            psmt = conn.prepareStatement("SELECT MAX(id) FROM users");
            ResultSet rs = psmt.executeQuery();
            rs.next();
            int newId = rs.getInt(1)+1;

            DataSource dataSource = this.dataSource;
            log.info("{}, id", newId);
            log.info("{}, username", request.getUsername());
            log.info("{}, encrypted password", request.getPassword());
            log.info("{}, email", request.getEmail());
            log.info("{}, id", newId);
            log.info("{}, is admin", false);
            log.info("");
            List<Integer> results = new ArrayList<>();
            List<String> otherResults = new ArrayList<>();

            //Makes sure there is no duplicate id

            psmt = conn.prepareStatement("SELECT id FROM users WHERE id=(?)");
            psmt.setInt(1, newId);
            resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                results.add(resultSet.getInt("id"));
            }
            if (!results.isEmpty() | newId == 7629157) {
                return new ResponseEntity<>("ID Already Exists. ", HttpStatus.BAD_REQUEST);
            }



            //Makes sure there is no duplicate email

            psmt = conn.prepareStatement("SELECT email FROM contacts WHERE email=(?)");
            psmt.setString(1, request.getEmail());
            resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                otherResults.add(resultSet.getString("email"));
            }
            if (!otherResults.isEmpty()) {
                return new ResponseEntity<>("Email Already Exists. ", HttpStatus.BAD_REQUEST);
            }



            //Add to Users table

            String insertc = "INSERT INTO users VALUES (?, ? )";
            psmt = conn.prepareStatement(insertc);
            psmt.setInt(1, newId);
            psmt.setString(2, request.getUsername());

            System.out.println(conn.getMetaData().getDatabaseProductVersion());
            psmt.executeUpdate();



            //Add to Contacts table

            insertc = "INSERT INTO contacts VALUES (?, ?, ?, ?)";
            psmt = conn.prepareStatement(insertc);
            psmt.setInt(1, newId);
            psmt.setString(2, request.getPassword());
            psmt.setString(3, request.getEmail());
            psmt.setBoolean(4, false);

            System.out.println(conn.getMetaData().getDatabaseProductVersion());
            psmt.executeUpdate();



            return new ResponseEntity<>("Creation Successful. ", HttpStatus.OK);

        } catch (SQLException e) {
            log.error("error: ", e);
            return new ResponseEntity<>("Database Connection Failed. ", HttpStatus.BAD_REQUEST);
        }
    }
}
