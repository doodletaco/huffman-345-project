import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

import java.util.HashMap;
import java.util.Scanner;

public class HuffmanEncode {

	public static void main (String [] args) {
		encode(args[0]);
	}

	/**
	 * Creates a compressed file using huffman codes for a specified file. Outputs
	 * two files, one named [FILENAME]-key.txt and [FILENAME]-huffman.txt in the
	 * source directory.
	 * @param file The file to compress.
	 */
	public static void encode(String file) {
		String fileContent = openFile(file);
		MinPQ chars = analyzeFrequencies(fileContent);
		Node root = createTree(chars);
		HashMap<String, String> codes = generateCodes(root);
		String compressedFile = compressFile(codes, fileContent);
		writeFiles(codes, compressedFile, file);
	}

	/**
	 * Analyzes the frequency of the characters in the file and places them into
	 * a MinPQ ordered based on the frequency of each character.
	 * @param file The file to analyze.
	 * @return A MinPQ of the characters in the file, based on frequency.
	 */
	private static MinPQ analyzeFrequencies(String file) {
		MinPQ chars = new MinPQ();
		HashMap<String, Integer> freq = new HashMap<>();
		for(int i = 0; i < file.length(); i++) {
			String curr = String.valueOf(file.charAt(i));
			if(freq.get(curr) == null) {
				freq.put(curr, 1);
			} else {
				freq.put(curr, freq.get(curr) + 1);
			}
		}
		Object[] keys = freq.keySet().toArray();
		for (Object key : keys) {
			chars.insert((String) key, freq.get(key));
		}
		return chars;
	}

	/**
	 * Creates a tree based on the frequencies of characters in the file.
	 * @param chars A MinPQ with the characters of the file ordered by frequency.
	 * @return The root of the tree.
	 */
	public static Node createTree(MinPQ chars) {
		Node root = new Node(0);
		int numLetters = chars.size();
		if(numLetters == 1) { // If there is only one letter, hardcode to 0.
			root.setVal(0);
			root.left = new Node(chars.delMin());
			return root;
		}
		// Add in first two nodes, this is done differently from the other nodes
		int priority1 = chars.getMinPriority();
		Node lChild = new Node(chars.delMin());
		int priority2 = chars.getMinPriority();
		Node rChild = new Node(chars.delMin());
		root.left = lChild;
		root.right = rChild;
		root.setVal(priority1+priority2);

		while(!chars.isEmpty()) { // Create the tree of characters based on priority.
			int priority = chars.getMinPriority();
			String content = chars.delMin();
			Node newRoot = new Node(0);
			if(root.val instanceof Integer && priority > (Integer)root.val) {
				newRoot.right = new Node(content);
				newRoot.left = root;
				newRoot.setVal((Integer)root.val + priority);
			}
			else if(root.val instanceof Integer) {
				newRoot.left = new Node(content);
				newRoot.right = root;
				newRoot.setVal((Integer)root.val + priority);
			}
			root = newRoot;
		}
		return root;
	}

	/**
	 * Generates the huffman codes for a given tree.
	 * @param root The root of the tree to generate codes for.
	 * @return A hashmap that associates each character with a code.
	 */
	private static HashMap<String, String> generateCodes(Node root) {
		HashMap<String, String> codes = new HashMap<>();
		generateCodesHelper(root, codes, "");
		return codes;
	}

	/**
	 * A recursive helper function to generate the codes for the tree.
	 * @param root The root of the tree.
	 * @param codes The hashmap to add codes to.
	 * @param code The string code up to the point of the node.
	 */
	private static void generateCodesHelper(Node root, HashMap<String, String> codes, String code) {
		if(root == null) { // Base, if the root is null return
			return;
		}
		generateCodesHelper(root.left, codes, code + "0"); // Run left subtree.
		if(root.val instanceof String) { // Only if the roots value is a string, add it to the map.
			codes.put((String)root.val, code);
		}
		generateCodesHelper(root.right, codes, code + "1"); // Run right subtree.
	}

	/**
	 * Compresses a given file with the specified huffman codes.
	 * @param codes A hashmap that associates each character with a code.
	 * @param file The text content of the file to compress.
	 * @return A String that contains the compressed text.
	 */
	private static String compressFile(HashMap<String, String> codes, String file) {
		String compressedFile = "";
		for (int i = 0; i < file.length(); i++) {
			String curr = String.valueOf(file.charAt(i));
			compressedFile += codes.get(curr);
		}
		return compressedFile;
	}

	/**
	 * Writes the compressed text and keys into files named [FILENAME]-huffman.txt and
	 * [FILENAME]-key.txt in the original location of the file.
	 * @param codes A hashmap that associates each character in a file with a huffman code.
	 * @param compressedFileText The text of the compressed file to write.
	 * @param file The file path to the original text.
	 */
	private static void writeFiles(HashMap<String, String> codes, String compressedFileText, String file) {
		Object[] keys = codes.keySet().toArray();
		String keyFileText = "";
		for(Object key : keys) {
			keyFileText += key + "," + codes.get((String)key) + "\n";
		}

		String pathSep = "/"; // Change between back and forward slashes based on path for
		if(file.contains("\\")) {
			pathSep = "\\";
		}

		String filePath = file.substring(0,file.lastIndexOf(pathSep)+1);
		String fileName = file.substring(file.lastIndexOf(pathSep)+1, file.lastIndexOf("."));

		File keyFile = new File(filePath + fileName + "-key.txt");
		File compressedFile = new File(filePath + fileName + "-huffman.txt");
		try {
			FileWriter keyWriter = new FileWriter(keyFile);
			FileWriter compressedWriter = new FileWriter(compressedFile);
			keyWriter.write(keyFileText);
			compressedWriter.write(compressedFileText);
			keyWriter.close();
			compressedWriter.close();
		} catch (IOException e) {
			System.out.println("File already exists!");
			System.exit(-1);
		}
	}

	/**
	 * Prints out a binary tree in in-order order, recursively.
	 * @param root The root of the tree
	 */
	private static void printTree(Node root) {
		if(root == null) {
			return;
		}
		printTree(root.left);
		System.out.println(root.val);
		printTree(root.right);
	}

	/**
	 * Returns a String with the entire contents of a file.
	 * @param fileName The file to open.
	 * @return The entire text of a string.
	 */
	private static String openFile(String fileName) {
		Scanner scan;
		try {
			scan = new Scanner(new File(fileName));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
			return null;
		}
		String content = "";
		while(scan.hasNext()) {
			content += scan.nextLine();
		}
		scan.close();
		return content;
	}

	private static class Node {
		Object val;
		Node left;
		Node right;

		/**
		 * Constructor. Creates a new node.
		 * @param contents  A string. The contents of the node. May be null.
		 */
		public Node(Object contents) {
			val = contents;
			left = null;
			right = null;
		}

		/**
		 * Set the value of the node to another value.
		 * @param value A string. May be null.
		 */
		public void setVal(Object value) {
			val = value;
		}
	}
}