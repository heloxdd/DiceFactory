package spring;

public class Encryptor {
    private static final int cipherNum = 27;

    public static String encrypt(String input) {
         char[] ceaser = input.toCharArray();

        for (int i=0; i<ceaser.length; i++) {
            ceaser[i] = (char) (((ceaser[i]+cipherNum)%65536) + 63);
        }

        String output = "";
        for (int i=ceaser.length-1; i>=0; i--) {
            output = output.concat(String.valueOf(ceaser[i]));
        }
        return output;
    }

    public static String decrypt(String input) {
        char[] ceaser = input.toCharArray();

        for (int i=0; i<ceaser.length; i++) {
            ceaser[i] = (char) (((ceaser[i]-cipherNum)%65536) - 63);
        }

        String output = "";
        for (int i=ceaser.length-1; i>=0; i--) {
            output = output.concat(String.valueOf(ceaser[i]));
        }
        return output;
    }
}
