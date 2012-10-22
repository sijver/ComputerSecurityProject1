import java.util.Arrays;

/**
 * @author Mark Skuratov, KhNURE.
 * @version 1.0.
 *          Date: 21.10.12.
 */
public class Start {

    public static void main(String[] args) {

        /*String[] plainTextsArray = StringGenerator.generatePlainTexts(1);*/
        String key = StringGenerator.generateKey();
        /*for(String s : plainTextsArray){
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
        } */

        byte[][] texts = StringGenerator.generate256Texts();

        byte[][] encryptedTexts = Encryptor.encryptNumberOfPlainTexts(texts, key);

        byte[][] decryptedTexts = Encryptor.decryptNumberOfCipherTexts(encryptedTexts, key);

        /*String plainText = "Hello!!!";
        byte[] plaintTextBytes = plainText.getBytes();

        String key = "78yz889923479122314312";
        byte[] keyBytes = key.getBytes();

        byte[] encryptedTextBytes = AES.encrypt(plaintTextBytes, keyBytes);
        byte[] decryptedTextBytes = AES.decrypt(encryptedTextBytes, keyBytes);


        System.out.println(plainText);
        System.out.println(new String(encryptedTextBytes));
        System.out.println(new String(decryptedTextBytes));*/

    }

}
