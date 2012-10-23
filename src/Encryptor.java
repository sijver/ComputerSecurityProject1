/**
 * @author Mark Skuratov, KhNURE.
 * @version 1.0.
 *          Date: 21.10.12.
 */
public class Encryptor {

    public static byte[] encryptPlainText(String plainText, String key){
        return AES.encrypt(plainText.getBytes(), key.getBytes());
    }

    public static byte[] encryptPlainText(byte[] plainText, String key){
        return AES.encrypt(plainText, key.getBytes());
    }

    public static byte[] decryptCipherText (byte[] cipherText, String key){
        return AES.decrypt(cipherText, key.getBytes());
    }

    public static byte[][] encryptNumberOfPlainTexts(String[] plainTextsArray, String key){
        byte[][] cipherTextsArray = new byte[plainTextsArray.length][];
        for(int i = 0; i < cipherTextsArray.length; i++){
            cipherTextsArray[i] = encryptPlainText(plainTextsArray[i], key);
        }
        return cipherTextsArray;
    }

    public static byte[][] encryptNumberOfPlainTexts(byte[][] plainTextsArray, String key){
        byte[][] cipherTextsArray = new byte[plainTextsArray.length][];
        for(int i = 0; i < cipherTextsArray.length; i++){
            cipherTextsArray[i] = encryptPlainText(plainTextsArray[i], key);
        }
        return cipherTextsArray;
    }

    public static byte[][] decryptNumberOfCipherTexts(byte[][] cipherTextsArray, String key){
        byte[][] decryptedTextsArray = new byte[cipherTextsArray.length][];
        for(int i = 0; i < cipherTextsArray.length; i++){
            decryptedTextsArray[i] = decryptCipherText(cipherTextsArray[i], key);
        }
        return decryptedTextsArray;
    }

}
