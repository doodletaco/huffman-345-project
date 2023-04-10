public class TestEncodeDecode {

	public static void main(String [] args) {
		String file;
		if (args.length == 0) { // Runs the example file if there is no file provided.
			file = "test/test-files/Huffman_text_test.txt";
		} else {
			file = args[0];
		}

		String pathSep = "/"; // Change between back and forward slashes based on path to support both
		if(file.contains("\\")) { // types of file separators.
			pathSep = "\\";
		}

		String filePath = file.substring(0,file.lastIndexOf(pathSep)+1);
		String fileName = file.substring(file.lastIndexOf(pathSep)+1, file.lastIndexOf("."));
		String keyFile = filePath + fileName + "-key.txt";
		String textFile = filePath + fileName + "-huffman.txt";

		HuffmanEncode.encode(file);
		System.out.println(HuffmanDecode.decode(keyFile, textFile));
	}
}
