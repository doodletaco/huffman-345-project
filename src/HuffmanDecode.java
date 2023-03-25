import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A decoder for a Huffman code. Contains methods to decode from a tree and text, in addition to methods
 * that will create the tree and get the text from files.
 */
public class HuffmanDecode {

    /**
     * With the key from the tree and a string of compressed text, decode the string.
     * @param key   A tree. The key for the Huffman code.
     * @param text  A string. Text compressed by the rules of the key.
     * @return      A string. The decoded text.
     */
    public static String decode(Tree key, String text) {
        String decodedText = "";
        String currText = "";
        for (int i = 0; i < text.length(); i++) {
            currText += text.charAt(i);
            String charAtLoc = key.getNode(currText);
            if (charAtLoc != null) {
                decodedText += charAtLoc;
                currText = "";
            }
        }
        return decodedText;
    }

    /**
     * With the filename containing the key, create a tree representing the code.
     * @param fName The file name.
     * @return      A tree containing the key, or null if the file does not exist.
     */
    public static Tree makeTree(String fName) {
        Tree tree = new Tree();

        File file = new File(fName);
        Scanner text;
        try {
            text = new Scanner(file);
        }
        catch(FileNotFoundException e) {
            System.out.printf("File %s not found.", fName);
            return null;
        }
        while (text.hasNextLine()) {
            String[] nodeString = text.nextLine().split(",");
            String content = nodeString[0];
            String location = nodeString[1];
            tree.addNode(location, content);
        }
        return tree;
    }

    /**
     * Get the contents of the coded file as a String.
     * @param fName The file name.
     * @return      A string containing the file contents.
     */
    public static String getContents(String fName) {
        String contents = "";

        File file = new File(fName);
        Scanner text;
        try {
            text = new Scanner(file);
        }
        catch(FileNotFoundException e) {
            System.out.printf("File %s not found.", fName);
            return null;
        }
        while (text.hasNextLine()) {
            contents += text.nextLine();
        }
        return contents;
    }
}
