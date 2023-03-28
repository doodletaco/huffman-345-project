public class TestEncodeDecode {

	public static void main(String [] args) {
		String file = args[0];

		String pathSep = "/"; // Change between back and forward slashes based on path for
		if(file.contains("\\")) {
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
