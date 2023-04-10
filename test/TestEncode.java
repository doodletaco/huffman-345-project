public class TestEncode {

	/**
	 * Creates a huffman code and compressed text for a specified file.
	 * @param args The command line arguments. The only argument should be the file path.
	 */
	public static void main(String [] args) {
		if(args.length != 1) {
			System.out.println("Improper command line arguments!");
			System.exit(-1);
		}
		HuffmanEncode.encode(args[0]);
	}
}
