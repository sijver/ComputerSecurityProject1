package aes;

import java.util.Random;

/**
 * Class that implements square attack on 4-rounds AES algorithm
 */
public class SquareAttack {

    private static byte[] key;  //128-bit key represented in form of 16-byte array

    /**
     * @return Original AK0 key
     */
    public static byte[] getKey() {
        return key.clone();
    }

    /**
     * Sets key for AES algorithm
     *
     * @param key Specified 16-byte key
     */
    public static void setKey(byte[] key) {
        SquareAttack.key = key.clone();
    }

    public static void generateKey() {
        final int bitsInByte = 256;
        final int lengthOfKey = 16;
        Random random = new Random();

        byte[] generatedKey = new byte[lengthOfKey];

        for (int i = 0; i < lengthOfKey; i++) {
            generatedKey[i] = (byte) random.nextInt(bitsInByte);
        }

        setKey(generatedKey);
    }

    /**
     * Generates 256 16-byte plain texts each of which has 15 same bytes with the others
     *
     * @param activePosition Number of byte that changes from -128 to 127
     *
     * @return Array of 256 texts that are represented in form of 16-byte arrays
     */
    public static byte[][] generate256Texts(int activePosition) {
        final int numOfTexts = 256;
        final int lengthOfText = 16;
        Random random = new Random();

        byte[][] plainTexts = new byte[numOfTexts][];

        plainTexts[0] = new byte[lengthOfText];

        //Generating of the 1st plain text and setting 0 to active byte
        for (int i = 0; i < lengthOfText; i++) {
            plainTexts[0][i] = (byte) random.nextInt(numOfTexts);
        }
        plainTexts[0][activePosition] = 0;

        //Copying of bytes of the 1st plain text to other 255 texts and changing of active byte of each
        for (int i = 1; i < numOfTexts; i++) {
            plainTexts[i] = new byte[lengthOfText];
            plainTexts[i] = plainTexts[0].clone();
            plainTexts[i][activePosition] = (byte) i;
        }

        return plainTexts;
    }

    /**
     * Encrypts number of plain texts using the same key
     *
     * @param plainTexts Array of 256 texts that are represented in form of 16-byte arrays
     *
     * @return Array of 256 cipher texts that are represented in form of 16-byte arrays
     */
    public static byte[][] encryptTexts(byte[][] plainTexts) {
        byte[][] cipherTexts = new byte[plainTexts.length][];

        for (int i = 0; i < cipherTexts.length; i++) {
            cipherTexts[i] = AES.encrypt(plainTexts[i], key);
        }

        return cipherTexts;
    }

    /**
     * Decrypts number of cipher texts using the same key
     *
     * @param cipherTexts Array of 256 cipher texts that are represented in form of 16-byte arrays
     *
     * @return Array of 256 decrypted texts that are represented in form of 16-byte arrays
     */
    public static byte[][] decryptTexts(byte[][] cipherTexts) {
        byte[][] decryptedTexts = new byte[cipherTexts.length][];

        for (int i = 0; i < cipherTexts.length; i++) {
            decryptedTexts[i] = AES.decrypt(cipherTexts[i], key);
        }

        return decryptedTexts;
    }

    /**
     * Attacks an AES 4-round cipher with square-attack and recovers a byte of key AK4
     *
     * @param cipherTexts  Array of 256 cipher texts that are represented in form of 16-byte arrays
     * @param bytePosition Number of byte of key which attacked
     *
     * @return Recovered byte of key
     */
    public static byte attackKeyByte(byte[][] cipherTexts, int bytePosition) {
        final int numOfBytes = 256;
        byte keyByte = 0;
        int keyByteCandidatesNum = 0;

        byte[] bytesArray = new byte[numOfBytes];

        //For each 256 possible key values calculating xor of recovered 3rd round cipher texts
        for (int i = 0; i < 256; i++) {
            bytesArray[i] = AES.invSubByte((byte) (cipherTexts[0][bytePosition] ^ (byte) i));
            //Calculating xor for assumed byte of key (i)
            for (int j = 1; j < 256; j++) {
                bytesArray[i] = (byte) (AES.invSubByte((byte) (cipherTexts[j][bytePosition] ^ (byte) i)) ^ bytesArray[i]);
            }

            if (bytesArray[i] == (byte) 0) {
                keyByte = (byte) i;
                keyByteCandidatesNum++;
            }
        }

        //If more than 1 candidate for a key byte than attacking byte on new set of plain texts
        if (keyByteCandidatesNum == 1) {
            return keyByte;
        } else {
            return attackKeyByte(encryptTexts(generate256Texts(bytePosition)), bytePosition);
        }
    }

}
