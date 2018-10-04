import java.io.IOException;
import exceptions.*;

/**
 * @version 1.0 2018.03.10
 * @author Anton Shchesnovich
 * contains logic of cipher/decipher text using Vegenere cipher
 */
public class VigenereCipher {
	/**
	 * text and codeqord for cipher/decipher using Vegenere cipher
	 */
	private String text;
	private String codeword;
    
	/**
     * @return ciphered text with using Vegenere cipher
     * @throws look method checkFields();
     */
    public String cipher() throws NullTextException, NullCodewordException, IllegalTextException, IllegalCodewordException{
    	checkFields();
    	char[] textArray = text.toLowerCase().toCharArray();
    	char[] key = lengthenCodeword(codeword, text.length()).toLowerCase().toCharArray();
        char[] result = new char[textArray.length];
        int k;
        int t;
        char[][] table = genVegenereTable();
 
        for (int i = 0; i < textArray.length; i++) {
            k = (int)key[i] - 97;
            t = (int)textArray[i] - 97;
            result[i] = table[k][t];
            
        }
        return new String(result);
    }
    
    /**
     * @return deciphered text with using Vegenere cipher
     * @throws look method checkFields();
     */
    public String decipher() throws NullTextException, NullCodewordException, IllegalTextException, IllegalCodewordException{
    	checkFields();
    	char[] key = lengthenCodeword(codeword, text.length()).toLowerCase().toCharArray();
    	char[] textArray = text.toLowerCase().toCharArray();
        char[] result = new char[textArray.length];
        int k;
        int t;
        char[][] table = genVegenereTable();
 
        for (int i = 0; i < textArray.length; i++) {
            k = (int)key[i] - 97;
            t = (int)textArray[i] - 97;
            if (k > t) {
                result[i] = table[26 + (t - k)][0];
            } else {
                result[i] = table[t - k][0];
            }
        }
        return new String(result);
    }
    
    /**
     * @return two-dimensional array which contains Vegenere table
     */
    public char[][] genVegenereTable() {
        char[][] vegenereTable = new char[26][26];
 
        for (int i = 0; i < 26; i++) {
            int off = i;
 
            for (int j = 0; j < 26; j++) {
 
                if(off == 26) {
                    off = 0;
                }
                vegenereTable[i][j] = (char)(97 + off);
                off++;
            }
        }
        return vegenereTable;
    }
    
    /**
     * @param codeword
     * @param requiredLenght
     * @return repeated codeword with >= requiredLenght
     * for example: codeword = "word", requiredLenght = 10; method will returned "wordwordword"; 
     */
    private String lengthenCodeword(String codeword, int requiredLenght) {
    	StringBuilder result = new StringBuilder(codeword);
    	while(result.length() < requiredLenght) {
    		result.append(codeword);
    	}
    	return result.toString();
    }
    
    /**
     * @throws NullTextException
     * @throws NullCodewordException
     * @throws IllegalTextException
     * @throws IllegalCodewordException
     * check fields text and codeword if fields empty or contains illegal symbols throws appropriate exceptions
     */
    private void checkFields() throws NullTextException, NullCodewordException, IllegalTextException, IllegalCodewordException {
    	if(text == null || text.length() == 0) throw new NullTextException();
    	if(codeword == null || codeword.length() == 0) throw new NullCodewordException();
    	if(!text.matches("^[a-zA-Z]+$")) throw new IllegalTextException();
    	if(!codeword.matches("^[a-zA-Z]+$")) throw new IllegalCodewordException();
    }
    
    public void setText(String text) {
    	this.text = text;
    }
    
    public void setCodeword(String codeword) {
    	this.codeword = codeword;
    }

}
