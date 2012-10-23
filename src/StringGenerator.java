import java.util.Random;

/**
 * @author Mark Skuratov, KhNURE.
 * @version 1.0.
 *          Date: 21.10.12.
 */
public class StringGenerator {

    public static String[] generatePlainTexts(int plainTextsNumber) {
        String[] plainTextsArray = new String[plainTextsNumber];
        for (int i = 0; i < plainTextsNumber; i++) {
            plainTextsArray[i] = generateString();
        }
        return plainTextsArray;
    }

    public static String generateKey(){
        return generateString();
    }

    private static String generateString() {
        final int STRING_LENGTH = 16;
        final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_ []{}|;':,./<>?\\\"`~";
        final Random rnd = new Random();
        char[] text = new char[STRING_LENGTH];
        for (int i = 0; i < STRING_LENGTH; i++) {
            text[i] = chars.charAt(rnd.nextInt(chars.length()));
        }
        return new String(text);
    }

    public static byte[][] generate256Texts(){
        Random random = new Random();
        byte[][] plainTexts = new byte[256][];
        plainTexts[0] = new byte[16];
        for(int i = 0; i < 16; i++){
            plainTexts[0][i] = (byte)random.nextInt(256);
        }
        plainTexts[0][13] = 0;
        for(int i = 1; i < 256; i++){
            plainTexts[i] = new byte[16];
            plainTexts[i] = plainTexts[0].clone();
            plainTexts[i][13] = (byte)i;
        }
        return plainTexts;
    }

}
