import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlayerDice playerOneDice = new PlayerDice();
        PlayerDice playerTwoDice = new PlayerDice();
        playerOneDice.randomizeDice();
        playerTwoDice.randomizeDice();
        boolean p1ready=false;
        boolean p2ready=false;
        PlayerDice fakePlayerOneDice = new PlayerDice();
        fakePlayerOneDice.playerDice=playerOneDice.playerDice;
        

        System.out.print("Player 2, look away! Press enter to continue. ");
        scanner.nextLine();
        System.out.println();
        System.out.println("Your dice are: "+playerOneDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]","")+".");
        System.out.println("Player 2's dice are: "+playerTwoDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]","")+".");
        System.out.println("Type 'cmdlist' to see a list of commands. ");
        while (!p1ready) {
            String input = scanner.nextLine();
            String firstInputWord = input.split("\\s+")[0];
            System.out.println();
            try {
                switch (firstInputWord) {
                    case "set":
                        int secondInputWord = Integer.parseInt(input.split("\\s+")[1]);
                        int thirdInputWord = Integer.parseInt(input.split("\\s+")[2]);
                        int fourthInputWord = Integer.parseInt(input.split("\\s+")[3]);
                        int fifthInputWord = Integer.parseInt(input.split("\\s+")[4]);
                        int sixthInputWord = Integer.parseInt(input.split("\\s+")[5]);
                        int seventhInputWord = Integer.parseInt(input.split("\\s+")[6]);
                        List<Integer> inputWords = new ArrayList<>();
                        inputWords.add(secondInputWord);
                        inputWords.add(thirdInputWord);
                        inputWords.add(fourthInputWord);
                        inputWords.add(fifthInputWord);
                        inputWords.add(sixthInputWord);
                        inputWords.add(seventhInputWord);
                        String correctness = playerOneDice.setOrder(inputWords);
                        if (correctness!="") {
                            System.out.println(correctness);
                        }
                        break;
                    case "swap":
                        secondInputWord = Integer.parseInt(input.split("\\s+")[1]);
                        thirdInputWord = Integer.parseInt(input.split("\\s+")[2]);
                        playerOneDice.swap(secondInputWord, thirdInputWord);
                        break;
                    case "randomize":
                        playerOneDice.randomize();
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
                    case "ATHENABOBINADOG":
                        playerOneDice.doggify();
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
        System.out.println("Player 1's dice (in randomized order) are: "+fakePlayerOneDice.getDice().toString().replaceAll("\\[", "").replaceAll("\\]","")+".");
        System.out.println("Type 'cmdlist' to see a list of commands. ");
        while (!p2ready) {
            String input = scanner.nextLine();
            String firstInputWord = input.split("\\s+")[0];
            System.out.println();
            try {
                switch (firstInputWord) {
                    case "swap":
                        int secondInputWord = Integer.parseInt(input.split("\\s+")[1]);
                        int thirdInputWord = Integer.parseInt(input.split("\\s+")[2]);
                        playerTwoDice.swap(secondInputWord, thirdInputWord);
                        break;
                    case "set":
                        secondInputWord = Integer.parseInt(input.split("\\s+")[1]);
                        thirdInputWord = Integer.parseInt(input.split("\\s+")[2]);
                        int fourthInputWord = Integer.parseInt(input.split("\\s+")[3]);
                        int fifthInputWord = Integer.parseInt(input.split("\\s+")[4]);
                        int sixthInputWord = Integer.parseInt(input.split("\\s+")[5]);
                        int seventhInputWord = Integer.parseInt(input.split("\\s+")[6]);
                        List<Integer> inputWords = new ArrayList<>();
                        inputWords.add(secondInputWord);
                        inputWords.add(thirdInputWord);
                        inputWords.add(fourthInputWord);
                        inputWords.add(fifthInputWord);
                        inputWords.add(sixthInputWord);
                        inputWords.add(seventhInputWord);
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
                    case "ATHENABOBINADOG":
                        playerTwoDice.doggify();
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

        Factory game = new Factory(playerOneDice, playerTwoDice);
        game.run();
    }
}
