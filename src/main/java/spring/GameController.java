package spring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        PlayerDice player1 = new PlayerDice(request.getP1Dice());
        PlayerDice player2 = new PlayerDice(request.getP2Dice());
        GameFactory factory = new GameFactory(player1, player2, player1.diceAmount);

//        PreparedStatement psmt;
//        try (Connection conn = dataSource.getConnection()) {
//
//            psmt = conn.prepareStatement("INSERT INTO games VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
//            psmt.setInt(1, request.getID());
//            psmt.setInt(2, request.getP1ID());
//            psmt.setInt(3, request.getP2ID());
//            psmt.setString(4, request.getPreGameP1Dice().toString());
//            psmt.setString(5, request.getPreGameP2Dice().toString());
//            psmt.setInt(6, request.getP1Points());
//            psmt.setInt(7, request.getP2Points());
//            psmt.setTimestamp(8, request.getDate());
//            psmt.setInt(9, request.getWinnerID());
//            psmt.executeUpdate();
//
//        }
//        catch (SQLException e) {
//
//            log.error("error: ", e);
//
//        }

        return factory.run();

    }

    @PostMapping("/new-user")
    public ResponseEntity<String> newPlayer(@RequestBody UserRequest request) {
        DataSource dataSource = this.dataSource;
        log.info("{}, id", request.getPlayerId());
        log.info("{}, username", request.getUsername());
        log.info("{}, encrypted password", request.getPassword());
        log.info("{}, email", request.getEmail());
        log.info("{}, id", request.getPlayerId());
        log.info("{}, is admin", request.getIsAdmin());
        PreparedStatement psmt;
        List<Integer> results = new ArrayList<>();
        List<String> otherResults = new ArrayList<>();



        try (Connection conn = dataSource.getConnection()) {

            //Makes sure there is no duplicate id

            psmt = conn.prepareStatement("SELECT id FROM users WHERE id=(?)");
            psmt.setInt(1, request.getPlayerId());
            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                results.add(resultSet.getInt("id"));
            }
            if (!results.isEmpty()) {
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
            psmt.setInt(1, request.getPlayerId());
            psmt.setString(2, request.getUsername());

            System.out.println(conn.getMetaData().getDatabaseProductVersion());
            psmt.executeUpdate();



            //Add to Contacts table

            insertc = "INSERT INTO contacts VALUES (?, ?, ?, ?)";
            psmt = conn.prepareStatement(insertc);
            psmt.setInt(1, request.getPlayerId());
            psmt.setString(2, request.getEmail());
            psmt.setString(3, request.getPassword());
            psmt.setBoolean(4, request.getIsAdmin());

            System.out.println(conn.getMetaData().getDatabaseProductVersion());
            psmt.executeUpdate();



            return new ResponseEntity<>("Creation Successful. ", HttpStatus.OK);

        } catch (SQLException e) {
            log.error("error: ", e);
            return new ResponseEntity<>("Database Connection Failed. ", HttpStatus.BAD_REQUEST);
        }
    }
}
