import java.util.Arrays;

/**
 * @author Mark Skuratov, KhNURE.
 * @version 1.0.
 *          Date: 21.10.12.
 */
public class Start {

    public static void main(String[] args) {

        String key = StringGenerator.generateKey();

        byte[][] texts = StringGenerator.generate256Texts();

        byte[][] encryptedTexts = Encryptor.encryptNumberOfPlainTexts(texts, key);

        System.out.println(Arrays.deepToString(AES.getLastKey()));

        byte[] result = new byte[256];
        for(int i = 0; i < 256; i++){
            result[i] = AES.InvSubByte((byte)(encryptedTexts[0][13]^(byte)i));
            for(int j = 1; j < 256; j++){
                result[i] = (byte)(AES.InvSubByte((byte)(encryptedTexts[j][13]^(byte)i))^result[i]);
            }
            if(result[i] == (byte)0){
                System.out.println((byte)i);
            }
        }

        System.out.println(Arrays.toString(result));

        /*
        FUCKING CODE!

        System.out.println(Arrays.toString(key.getBytes()));

        for(String s : plainTextsArray){
            System.out.println(s);
        }

        byte[][] encryptedTexts = Encryptor.encryptNumberOfPlainTexts(plainTextsArray, key);

        System.out.println();
        for(byte[] b : encryptedTexts){
            System.out.println(new String(b));
        }
        byte[][] decryptedTexts = Encryptor.decryptNumberOfCipherTexts(encryptedTexts, key);

        System.out.println();
        for(byte[] b : decryptedTexts){
            System.out.println(new String(b));
        }

        String plainText = "Hello!!!лрлрлр";

        byte[] plaintTextBytes = plainText.getBytes();

        String key = "78yz889923479122314312";
        byte[] keyBytes = key.getBytes();

        byte[] encryptedTextBytes = AES.encrypt(plaintTextBytes, keyBytes);
        byte[] decryptedTextBytes = AES.decrypt(encryptedTextBytes, keyBytes);


        System.out.println(plainText);
        System.out.println(new String(encryptedTextBytes));
        System.out.println(new String(decryptedTextBytes));

        for (int i = 1; i < encryptedTexts.length; i++) {
            for (int j = 0; j < encryptedTexts[0].length; j++) {
                encryptedTexts[i][j] = (byte)(encryptedTexts[i][j] ^ encryptedTexts[i-1][j]);
            }
            //System.out.println(Arrays.toString(encryptedTexts[i]));
        } */

        //byte[][] decryptedTexts = Encryptor.decryptNumberOfCipherTexts(encryptedTexts, key);

    }

}
