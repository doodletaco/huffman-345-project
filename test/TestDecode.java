public class TestDecode {
    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Bad commandline Arguments!");
        }
        Tree tree = HuffmanDecode.makeTree(args[0]);
        String key = HuffmanDecode.getContents(args[1]);
        String text = HuffmanDecode.decode(tree, key);
        System.out.println(text);
    }
}
