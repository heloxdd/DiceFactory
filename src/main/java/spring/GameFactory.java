package spring;

import java.util.*;
public class GameFactory {
    PlayerDice p1;
    PlayerDice p2;
    int diceAmount;
    Random random = new Random();
    List<String> results = new ArrayList<>();
    List<String> resultPile = new ArrayList<>();
    
    public GameFactory(PlayerDice p1, PlayerDice p2, int diceAmount) {
        this.p1=p1;
        this.p2=p2;
        this.diceAmount=diceAmount;
    }
    public List<String> run() {
        int p1index=0;
        int p2index=0;
        for (int i=0;i<diceAmount*2;i++) {
            if (i%2==0) {
                results.add("On Player 1's turn (turn "+(p1index+1)+"), ");
                switch (p1.get(p1index)) {
                    case 1:
                        one(p1);
                        break;
                    case 2:
                        two(p1, p1index);
                        break;
                    case 3:
                        three(p1, p2, p2index, p1index);
                        break;
                    case 4:
                        four(p1, p2, p1index, p2index);
                        break;
                    case 5:
                        five(p1, p2);
                        break;
                    case 6:
                        six(p1);
                        break;
                }
                results.add(resultPile.toString().replace("[", "").replace("]", ""));
                results.add("Player 1's dice: "+diceString(p1.getDice()));
                results.add("Player 2's dice: "+diceString(p2.getDice()));
                p1index+=1;
                results.add("Player 1's points: "+p1.points);
                results.add("Player 2's points: "+p2.points);
                results.add("Player 1's mult: "+p1.mult);
                results.add("Player 2's mult: "+p2.mult);
                resultPile.clear();
            } else {
                results.add("On Player 2's turn (turn "+(p2index+1)+"), ");
                switch (p2.get(p2index)) {
                    case 1:
                        one(p2);
                        break;
                    case 2:
                        two(p2, p2index);
                        break;
                    case 3:
                        three(p2, p1, p1index, p2index);
                        break;
                    case 4:
                        four(p2, p1, p2index, p1index);
                        break;
                    case 5:
                        five(p2, p1);
                        break;
                    case 6:
                        six(p2);
                        break;
                }
                results.add(resultPile.toString().replace("[", "").replace("]", ""));
                results.add("Player 1's dice: "+diceString(p1.getDice()));
                results.add("Player 2's dice: "+diceString(p2.getDice()));
                p2index+=1;
                results.add("Player 2's points: "+p2.points);
                results.add("Player 1's points: "+p1.points);
                results.add("Player 2's mult: "+p2.mult);
                results.add("Player 1's mult: "+p1.mult);
                resultPile.clear();
            }
        }
        if (p1.points>p2.points) {
            results.add("Player 1 won!");
        } else if (p1.points==p2.points) {
            results.add("The game was a tie!");
        } else {
            results.add("Player 2 won!");
        }
        results.add("Player 1's points: "+p1.points);
        results.add("Player 2's points: "+p2.points);
        return results;
    }
    private String diceString(List<Integer> dice) {
        StringBuilder retval = new StringBuilder();
        for (int i=0;i<diceAmount;i++){
            retval.append(dice.get(i).toString());
            if (i<diceAmount-1) {
                retval.append(", ");
            }
        }
        return retval.toString();
    }
    private void one(PlayerDice player) {
        player.points+= 3L *player.mult;
        resultPile.add("Player got 3 * "+player.mult+" points. ");
        player.mult=1;
    }
    private void two(PlayerDice player, int index) {
        if (index<diceAmount-1) {
            int curValue = player.get(index+1);
            int newValue = (curValue+player.mult)%6;
            if (newValue == 0) {
                newValue=6;
            }
            player.setDice(index+1, newValue);
            resultPile.add("Player's next die got +"+ player.mult +" value. ");
            player.mult=1;
        } else {
            resultPile.add("Nothing happened, two was the last die. ");
        }
    }
    private void three(PlayerDice player, PlayerDice enemy, int enemyIndex, int index) {
        if (player.mult==1 && enemyIndex!=diceAmount) {
            player.setDice(index, enemy.get(enemyIndex));
            enemy.setDice(enemyIndex, random.nextInt(6)+1);
            resultPile.add("Player swapped dice with other player. Other player re-rolled swapped die. The obtained die will now be used. ");
            switch (player.get(index)) {
                case 1:
                    one(player);
                    break;
                case 2:
                    two(player, index);
                    break;
                case 3:
                    three(player, enemy, enemyIndex, index);
                    break;
                case 4:
                    four(player, enemy, index, enemyIndex);
                    break;
                case 5:
                    five(player, enemy);
                    break;
                case 6:
                    six(player);
                    break;
            }
        } else if (player.mult!=1) {
            resultPile.add("Nothing happened because three was multed. ");
            player.mult=1;
        } else {
            resultPile.add("Nothing happened because this three was the last die. ");
        }
    }
    private void four(PlayerDice player, PlayerDice enemy, int index, int enemyIndex) {
        if (index>0) {
            switch (player.get(index-1)) {
                case 1:
                    one(player);
                    break;
                case 2:
                    two(player, index);
                    break;
                case 3:
                    three(player, enemy, enemyIndex, index);
                    break;
                case 4:
                    four(player, enemy, index-1, enemyIndex);
                    break;
                case 5:
                    five(player, enemy);
                    break;
                case 6:
                    six(player);
                    break;
            }
            player.setDice(index, player.get(index-1));
            resultPile.add("Previous die was copied. ");
        } else {
            resultPile.add("Nothing happened, this 4 was the first die. ");
        }
    }
    private void five(PlayerDice player, PlayerDice enemy) {
        player.points += (long) (player.mult*0.5*player.points);

        enemy.points+= 3L *player.mult;
        resultPile.add("Player got +"+50*player.mult+"% more points. Enemy got +"+3*player.mult+" points. ");
        player.mult=1;
    }
    private void six(PlayerDice player)  {
        player.mult*=2;
        resultPile.add("Mult was doubled. ");
    }

    public PlayerDice getP1Dice() {
        return p1;
    }
    public PlayerDice getP2Dice() {
        return p2;
    }
    public long getP1Points() {
        return p1.points;
    }
    public long getP2Points() {
        return p2.points;
    }

}