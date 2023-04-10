# Huffman Encoding
A Huffman code is a method to compress strings into bits. With ASCII, each character takes up 8 bits with 256 total possible characters. However, there is a lot of text which does not require this full range. By accounting for the frequency and using a tree with no duplicate prefixes, more text can be stored in less space. This text compression method can be used in cases where space is a constraint, such as wireless communication.
This method was created by David A. Huffman in 1950 (1). Although other approaches to this encoding problem have been attempted before, he succeeded by using a bottom-up approach instead of working from the top to the bottom.
# How to use these files
# Encoding
Compile the files by typing `javac *.java` in the directory containing the source code and the directory containing the testing code. After it has been compiled, type `java TestEncode <file path>` with the file path leading to the specific file to encode.
After it has run, two text files will be created with the original file name with the prefix:
-huffman.txt and -key.txt.
# Decoding
Compile the files by typing `javac *.java` in the directory containing the source code and the directory containing the testing code. After it has been compiled, type `java TestDecode <key path> <text path>` with the key path leading to the file containing the key, and the text path leading the the encoded text.
After it has run, it will print out the decoded text. Add `> <file name>.txt` to the command to pipe the output to a file.
# Both Encoding and Decoding
This is more useful for testing. With the files compiled, type ‘java TestEncodeDecode <file path>’ with the file path leading to the file you want to encode. The program will then create the key and huffman files, and pass them into the decoder program. The original text will be printed to the console.
# Implementation Analysis
# Encoding
The implementation of Huffman Encoding has 3 separate steps.
First, the file is analyzed for character frequency. This step runs in O(N) time, where N is the number of characters in the file. The extra space used in this process  is proportional to O(M), where M is the number of unique characters in the file. The characters are placed into a minimum PriorityQueue, where the priority is the character's frequency.

Then, using the PriorityQueue of characters, the algorithm builds a binary tree based on the frequencies. The frequency of a node is based on the sum of the current node and its children. So, if two characters of frequency 5 and 6 are added to the tree, their parent node will have a frequency of 11. This helps create the codes to be as space efficient as possible and have no prefixes. The runtime of this process varies, however the best, worst, and average runtimes are proportional to O(NlogN), where N is the number of unique characters in the file. In the best case, the tree will be as balanced and in the average case the tree will be randomly balanced. However, in the worst case the tree will be a straight line, with each interior node having at least one leaf child. The tree takes O(N) space, since the number of nodes is dependent on the number of unique characters in the file.

Finally, the encoding algorithm goes through the entire file and writes the corresponding bit sequence for each character to a new file. For this implementation, the codes are stored in a binary tree. The runtime for this is O(NlogM) where N is the total number of characters in the file and M is the number of unique characters. This portion will take up O(N) extra space, since a whole new file needs to be written to disk.

With both proper implementation and a good candidate file for encoding, the process can be completed in O(NlogM) time. A good candidate file has a small number of unique characters. The best and average case time is O(NlogM). The worst case runtime, where each character in the file is unique, is O(N2).
# Decoding
Decoding requires two different steps. First, the tree is rebuilt from the -key.txt file. The process should take O(N) time, where N is the number of nodes in the tree. This also takes up O(N) extra space.

Then the program processes the encoded file. For each bit, the program goes down the tree right or left, depending on if the bit is a one or a zero, until it reaches a leaf node. Then, the character in the leaf node is recorded in the return string and the current position in the tree is reset to the root. This process takes O(NlogM) time, where N is the number of bits in the file and M is the number of unique characters. It also takes O(N) space because the number of characters in the decoded file is proportional to the number of bits in the encoded file.

Sources
[1] http://www.huffmancoding.com/my-uncle/scientific-american