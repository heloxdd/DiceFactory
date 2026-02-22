import java.util.*;
public class PlayerDice {
    List<Integer> playerDice = new ArrayList<>();
    int points=0;
    int mult=1;
    public void randomizeDice() {
        Random randomNum = new Random();
        for (int i=0;i<6;i++) {
            playerDice.add(randomNum.nextInt(6)+1);
        }
    }
    public List<Integer> getDice() {
        return playerDice;
    }
    public int get(int index) {
        return playerDice.get(index);
    }
    public void setDice(int index, int newnum) {
        playerDice.set(index,newnum);
    }
    public void swap(int i, int j) {
        int placeholder = playerDice.get(i-1);
        playerDice.set(i-1,playerDice.get(j-1));
        playerDice.set(j-1,placeholder);
    }
    public void randomize() {
        Collections.shuffle(playerDice);
    }
    public String setOrder(List<Integer> newOrder) {
        List<Integer> sortedPlayerDice = new ArrayList<>(playerDice);
        Collections.sort(sortedPlayerDice);
        List<Integer> sortedNewOrder = new ArrayList<>(newOrder);
        Collections.sort(sortedNewOrder);
        if (sortedNewOrder.equals(sortedPlayerDice)) {
            playerDice=newOrder;
            return "";
        } else {
            return "You need to type a valid command. Error: Bad Numbers While Setting";
        }
    }
    public static void rules() {
        System.out.println("In Dice Factory, you have to lay out your dice in a profitable way to get the most points.");
        System.out.println("Each die is triggered in the order you placed them in and you take turns with the enemy activating your dice.");
        System.out.println();
        System.out.println("________DICE ABILITIES________________DICE ABILITIES WITH 2X EFFECTS________");
        System.out.println("1: Gives you three points.            Gives you six points.                 ");
        System.out.println();
        System.out.println("2: Adds one to the value of the       Adds two to the value of the.         ");
        System.out.println("   next die (ex. a 3 becomes a 4).    next die (ex. a 3 becomes a 5).       ");
        System.out.println();
        System.out.println("3: Swaps current die with the next    does nothing lol                      ");
        System.out.println("   die the enemy will use.                                                  ");
        System.out.println("   The enemy rerolls the 3.                                                 ");
        System.out.println();
        System.out.println("4: Turns into the previous die        Turns into the previous die           ");
        System.out.println("   and uses itself.                   and uses itself with 2X effects.      ");
        System.out.println();
        System.out.println("5: Gives you +50% points but          Gives you +100% points but.           ");
        System.out.println("   gives enemy 3 points.              gives enemy 8 points.                 ");
        System.out.println();
        System.out.println("6: Next die has 2X effects.           Next die has 4X effects.              ");
    }
    public static void cmdList() {
        System.out.println("set - follow by your dice numbers in a different order to set it to that order. ");
        System.out.println("swap - follow by two numbers being the indexes of two items to be swapped.");
        System.out.println("ready - moves on to the next player's ordering or starts the game.");
        System.out.println("randomize - randomizes the order of the list.");
        System.out.println("rules - prints a list of all the rules of the game and what each die's ability is.");
        System.out.println("cmdlist - prints a list of all commands.");
        System.out.println("ready - moves on to the next player's ordering or starts the game.");
    }
    public void doggify() {
        playerDice=List.of(7,7,7,7,7,7);
    }
}