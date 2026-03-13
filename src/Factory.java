import java.util.*;
public class Factory {
    PlayerDice p1;
    PlayerDice p2;
    Random random = new Random();
    public Factory(PlayerDice p1, PlayerDice p2) {
        this.p1=p1;
        this.p2=p2;
    }
    public void run() {
        int p1index=0;
        int p2index=0;
        for (int i=0;i<12;i++) {
            if (i%2==0) {
                System.out.print("On Player 1's turn (turn "+(p1index+1)+"), ");
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
                System.out.println("Player 1's dice: "+diceString(p1.getDice(), p1index));
                System.out.println("Player 2's dice: "+diceString(p2.getDice(), 7));
                p1index+=1;
                System.out.println("P1 points: "+p1.points);
                System.out.println("P2 points: "+p2.points);
                System.out.println("P1 mult: "+p1.mult);
                System.out.println("P2 mult: "+p2.mult);
                System.out.println();
            } else {
                System.out.print("On Player 2's turn (turn "+(p2index+1)+"), ");
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
                System.out.println("Player 1's dice: "+diceString(p1.getDice(), 7));
                System.out.println("Player 2's dice: "+diceString(p2.getDice(), p2index));
                p2index+=1;
                System.out.println("P2 points: "+p2.points);
                System.out.println("P1 points: "+p1.points);
                System.out.println("P2 mult: "+p2.mult);
                System.out.println("P1 mult: "+p1.mult);
                System.out.println();
            }
        }
    }
    public String diceString(List<Integer> dice, int index) {
        String retval = "";
        for (int i=0;i<6;i++){
            if (i!=index) {
                retval+=dice.get(i).toString();
            } else {
                retval+="-> "+dice.get(index).toString();
            }
            if (i<5) {
                retval += ", ";
            }
        }
        return retval;
    }
    public void one(PlayerDice player) {
        player.points+=3*player.mult;
        System.out.println("player got 3 * "+player.mult+" points. ");
        player.mult=1;
    }
    public void two(PlayerDice player, int index) {
        if (index<5) {
            int curvalue = player.get(index+1);
            int newvalue = (curvalue+player.mult)%6;
            if (newvalue == 0) {
                newvalue=6;
            }
            player.setDice(index+1, newvalue);
            System.out.println("player's next die got +"+1*player.mult+" value. ");
            player.mult=1;
        } else {
            System.out.println("nothing happened, two was the last die.");
        }
    }
    public void three(PlayerDice player, PlayerDice enemy, int enemyIndex, int index) {
        if (player.mult==1 && enemyIndex!=6) {
            player.setDice(index, enemy.get(enemyIndex));
            enemy.setDice(enemyIndex, random.nextInt(6)+1);
            System.out.println("player swapped dice with other player. Other player re-rolled swapped die. The obtained die will now be used.");
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
            System.out.println("nothing happened because three was multed. ");
            player.mult=1;
        } else {
            System.out.println("nothing happened because this three was the last die.");
        }
    }
    public void four(PlayerDice player, PlayerDice enemy, int index, int enemyIndex) {
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
            System.out.println("previous die was copied. ");
        } else {
            System.out.println("nothing happened, this 4 was the first die. ");
        }
    }
    public void five(PlayerDice player, PlayerDice enemy) {
        if (player.points%2==0) {
            player.points*=(1+(player.mult*0.5));
        }
        else {
            player.points*=((int)1+(player.mult*0.5));
            player.points+=1;
        }
        enemy.points+=3*player.mult;
        System.out.println("player got +"+50*player.mult+"% more points. Enemy got +"+3*player.mult+" points. ");
        player.mult=1;
    }
    public void six(PlayerDice player)  {
        player.mult*=2;
        System.out.println("mult was doubled.");
    } 
}