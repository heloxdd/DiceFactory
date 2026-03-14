import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int diceAmount = 6;
        boolean p1ready=false;
        boolean p2ready=false;
        
        System.out.print("How many dice per player will there be? ");
        diceAmount = scanner.nextInt();
        System.out.println();

        PlayerDice playerOneDice = new PlayerDice(diceAmount);
        PlayerDice playerTwoDice = new PlayerDice(diceAmount);
        PlayerDice fakePlayerDice = new PlayerDice(diceAmount);
        playerOneDice.randomizeDice();
        playerTwoDice.randomizeDice();
        fakePlayerDice.playerDice=playerOneDice.playerDice;

        System.out.print("Player 2, look away! Press enter to continue. ");
        scanner.nextLine();
        System.out.println();
        System.out.println("Your dice are: "+playerOneDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]","")+".");
        System.out.println("Player 2's dice are: "+playerTwoDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]","")+".");
        System.out.println("Type 'cmdlist' to see a list of commands. ");
        
        while (!p1ready) { //does the turn thingy
            List<Integer> inputWords = new ArrayList<>();
            inputWords.removeAll(inputWords);
            String input = scanner.nextLine();
            String firstInputWord = input.split("\\s+")[0];
            for (int i = 1; i<=diceAmount; i++) {
                if (i < input.split("\\s+").length && input.split("\\s+")[i] != null) {
                    inputWords.add(Integer.parseInt(input.split("\\s+")[i]));
                }
            }
            System.out.println();
            try {
                switch (firstInputWord) {
                    case "set":
                        String correctness = playerOneDice.setOrder(inputWords);
                        if (correctness!="") {
                            System.out.println(correctness);
                        }
                        break;
                    case "swap":
                        playerOneDice.swap(inputWords.get(0), inputWords.get(1));
                        break;
                    case "randomize":
                        playerOneDice.randomize();
                        break;
                    case "sort":
                        playerOneDice.sort();
                        break;
                    case "rules":
                        PlayerDice.rules();
                        break;
                    case "cmdlist":
                        PlayerDice.cmdList();
                        break;
                    case "ready":
                        p1ready=true;
                        break;
                    default:
                        System.out.println("You need to type a valid command. Error: Inexistent Command");
                }
                System.out.println("Current layout of dice: "+playerOneDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]",""));
            } 
            catch (Exception ex) {
                System.out.println("You need to type a valid command. Error: "+ex);
            }
        }
        for (int i=0;i<100;i++) {
            System.out.println("DO NOT SCROLL UP, YOU WILL SEE CONFIDENTIAL INFO");
        }
        
        System.out.println();
        System.out.print("Player 1, look away! It is now player 2's turn to re-arrange their dice. Press enter to continue. ");
        scanner.nextLine();
        System.out.println();
        System.out.println("Your dice are: "+playerTwoDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]","")+".");
        System.out.println("Player 1's dice (in randomized order) are: "+fakePlayerDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]","")+".");
        System.out.println("Type 'cmdlist' to see a list of commands. ");
        
        while (!p2ready) {
            List<Integer> inputWords = new ArrayList<>();
            inputWords.removeAll(inputWords);
            String input = scanner.nextLine();
            String firstInputWord = input.split("\\s+")[0];
            for (int i = 1; i<=diceAmount; i++) {
                if (i < input.split("\\s+").length && input.split("\\s+")[i] != null) {
                    inputWords.add(Integer.parseInt(input.split("\\s+")[i]));
                }
            }
            System.out.println();
            try {
                switch (firstInputWord) {
                    case "swap":
                        int secondInputWord = Integer.parseInt(input.split("\\s+")[1]);
                        int thirdInputWord = Integer.parseInt(input.split("\\s+")[2]);
                        playerTwoDice.swap(secondInputWord, thirdInputWord);
                        break;
                    case "set":
                        String correctness = playerTwoDice.setOrder(inputWords);
                        if (correctness!="") {
                            System.out.println(correctness);
                        }
                        break;
                    case "randomize":
                        playerTwoDice.randomize();
                        break;
                    case "rules":
                        PlayerDice.rules();
                        break;
                    case "cmdlist":
                        PlayerDice.cmdList();
                        break;
                    case "ready":
                        p2ready=true;
                        break;
                    default:
                        System.out.println("You need to type a valid command. Error: Inexistent Command");
                }
                System.out.println("Current layout of dice: "+playerTwoDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]",""));
            } 
            catch (Exception ex) {
                System.out.println("You need to type a valid command. Error: "+ex);
            }
        }
        for (int i=0;i<100;i++) {
            System.out.println("DO NOT SCROLL UP, YOU WILL SEE CONFIDENTIAL INFO");
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Ok, battle time!");
        System.out.println("Player 1's dice: "+playerOneDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]",""));
        System.out.println("Player 2's dice: "+playerTwoDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]",""));
        scanner.close();
        System.out.println();

        Factory game = new Factory(playerOneDice, playerTwoDice, diceAmount);
        game.run();
    }
}
