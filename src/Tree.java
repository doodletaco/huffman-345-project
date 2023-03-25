public class Tree {
    Node root;

    /**
     * Constructor. Creates a root node.
     */
    Tree() {
        root = new Node(null);
    }

    /**
     * Add a node to the tree based on the location, given as a string.
     * A 0 means to go to the left child, while a 1 means right.
     * Create new nodes as needed to get to the assigned location then assign the value there.
     * @param location  A string. The location to place the value
     * @param value     A string. The value to add.
     */
    public void addNode(String location, String value) {
        Node currNode = root;
        for (int i = 0; i < location.length(); i++) {
            if (location.charAt(i) == '0') {
                if (currNode.left == null) {
                    currNode.left = new Node(null);
                }
                currNode = currNode.left;
            }
            else if (location.charAt(i) == '1') {
                if (currNode.right == null) {
                    currNode.right = new Node(null);
                }
                currNode = currNode.right;
            }
        }
        currNode.setVal(value);
    }

    /**
     * Given a location, return the contents of the node located there.
     * A 0 means to go to the left child, while a 1 means right.
     * @param location  A string. The location of the value.
     * @return          A string. The contents of the node found.
     */
    public String getNode(String location) {
        Node currNode = root;
        for (int i = 0; i < location.length(); i++) {
            if (location.charAt(i) == '0') {
                currNode = currNode.left;
            }
            else if (location.charAt(i) == '1') {
                currNode = currNode.right;
            }
        }
        return currNode.val;
    }

    @Override
    public String toString() {
        if (root == null) {
            return "";
        }
        return toString(root.left, 1) + toString(root.right, 1);
    }

    public String toString(Node node, int indent) {
        if (node == null) {
            return "";
        }
        String toPrint = node.val;
        if (toPrint == null) {
            toPrint = " ";
        }
        return "  |".repeat(indent) + toPrint + "\n" + toString(node.left, indent+1) + toString(node.right, indent+1);
    }

    /**
     * A class representing a node of a binary tree.
     */
    class Node {
        String val;
        Node left;
        Node right;

        /**
         * Constructor. Creates a new node.
         * @param contents  A string. The contents of the node. May be null.
         */
        public Node(String contents) {
            val = contents;
            left = null;
            right = null;
        }

        /**
         * Set the value of the node to another value.
         * @param value A string. May be null.
         */
        public void setVal(String value) {
            val = value;
        }

        @Override
        public String toString() {
            return String.format("%s (%s %s)", val, left, right);
        }
    }
}
