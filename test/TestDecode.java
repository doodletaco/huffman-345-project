public class TestDecode {
    public static void main(String[] args) {
        Tree tree = HuffmanDecode.makeTree("test/test-files/Huffman_key_test.txt");
        String key = HuffmanDecode.getContents("test/test-files/Huffman_compressed_test.txt");
        String text = HuffmanDecode.decode(tree, key);
        System.out.println(text);
    }
}
