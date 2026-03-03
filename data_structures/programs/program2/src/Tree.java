import java.util.*;

class Tree {

  private class Node {
    private Integer[] keys = new Integer[] { null, null, null };

    // The wall I ran into was that I tried to make this size 3 instead of 4
    // Once I increased the size to 4, everything fell into place in the insertion algorithm
    private Node[] children = new Node[] { null, null, null, null };
    int size = 0;

    Node(int key) {
      keys[0] = key;
      size++;
    }

    boolean isLeaf() {
      return children[0] == null;
    }
    boolean isTwo() {
      return keys[1] == null;
    }
    boolean contains(int x) {
      return keys[0] == x || (!isTwo() && keys[1] == x);
    }
    int numKeys() {
      return isTwo() ? 1 : 2;
    }
    int getSize() {
      return size;
    }

    public Node find(int val) {
      // Return cases
      if (contains(val)) {
        return this;
      }

      if (isLeaf()) {
        return null;
      }

      int i = 0;
      while (i < numKeys() && keys[i] < val) {
        i++;
      }

      return children[i].find(val);
    }

    public Node insert(int val) {
      if (isLeaf()) {
        return addVal(val);
      }

      int i = 0;
      while (i < numKeys() && keys[i] < val) {
        i++;
      }

      Node promoted = children[i].insert(val);

      if (promoted == null) {
        size++; // Propagate size increment up
        return null; // No promotion occurs
      }

      // Promotion occurs if this point is reached
      int position = 0; // Position from which promotion occurs
      while (position < numKeys() &&  keys[position] < promoted.keys[0]) {
        position++;
      }

      // Shift children to make room for promoted children in the index from which it was promoted
      for (i = 3; i > position + 1; i--) {
        children[i] = children[i - 1];
      }
      // Insert children into space left by previous loop
      // This process overwrites the child the promotion comes from
      children[position] = promoted.children[0];
      children[position + 1] = promoted.children[1];

      // Add the value to the current node
      promoted = addVal(promoted.keys[0]);
      if (promoted == null) {
        return null;
      } // This node does not need to split

      // Set children of left and right child of promoted node
      promoted.children[0].children = new Node[] { children[0], children[1], null, null };
      promoted.children[1].children = new Node[] { children[2], children[3], null, null };
      promoted.children[0].size = children[0].size + children[1].size + 1;
      promoted.children[1].size = children[2].size + children[3].size + 1;
      promoted.size = promoted.children[0].size + promoted.children[1].size + 1;

      return promoted;
    }

    public Node addVal(int val) {
      if (isTwo()) {
        if (val > keys[0]) {
          keys[1] = val;
        } else {
          keys[1] = keys[0];
          keys[0] = val;
        }
        size++;
        return null; // No split needed
      }

      Integer[] k = new Integer[]{keys[0], keys[1], val};

      Arrays.sort(k);

      Node leftNode = new Node(k[0]);
      Node promoted = new Node(k[1]);
      Node rightNode = new Node(k[2]);

      // The word "size" doesn't look real anymore

      leftNode.size = 1;
      rightNode.size = 1;
      promoted.size = 3;

      promoted.children[0] = leftNode;
      promoted.children[1] = rightNode;

      return promoted; // After this, the current node gets collected as the promoted node replaces all references to it
    }

    public Integer get(int index) {
      if (isLeaf()) {
        return keys[index];
      }

      int i = 0;
      int total = 0;
      while (total + children[i].size <= index) {
        total += children[i].size;
        if (total == index) {
          return keys[i]; // If index match found in keys of this node
        }
        i++;
        total++;
      }

      return children[i].get(index - total);
    }
  }

  private Node root;

  public Tree() {}


  public int size() {
    if (root == null) {
      return 0;
    }

    return root.getSize();
  }

  public int size(int val) {
    if (root == null) {
      return 0;
    }
    Node target = root.find(val);
    return (target == null) ? 0 : target.getSize();
  }

  public boolean insert(int val) {
    if (root == null) {
      root = new Node(val);
      return true;
    }
    if (root.find(val) != null) {
      return false;
    }

    Node newRoot = root.insert(val);
    if (newRoot != null) {
      root = newRoot;
    }
    return true;
  }

  public int get(int i) throws ArrayIndexOutOfBoundsException {
    if (root == null) {
      throw new ArrayIndexOutOfBoundsException("Root is null");
    }

    Integer val = root.get(i);
    if (val == null) {
      throw new ArrayIndexOutOfBoundsException("Index " + i + " is out of bounds.");
    }
    return val;
  }
}