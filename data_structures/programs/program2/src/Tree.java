import java.util.*;

class Tree {

  private static class Node {
    private Integer k1, k2;
    private Node left, mid, right;

    Node(int key) {
      k1 = key;
    }

    boolean isLeaf() { return left == null; }
    boolean isTwo() { return k2 == null; }
  }

  private Node root;

  public Tree() {}

  public Tree(Node root) {
    this.root = root;
  }

    private int count(Node start) {
      if (start == null) {
        return 0;
      }

      int total = start.isTwo() ? 1 : 2;

      total += count(start.left);
      total += count(start.mid);
      total += count(start.right);

      return total;
    }

  private Node find(Node node, int val) {
    if (node == null) { return null; }
    if (node.k1 == val || node.k2 == val) {
      return node;
    }
    if (node.isLeaf()) {
      return null;
    }
    Node found = find(node.left, val);
    if (found != null) {
      return found;
    }
    found = find(node.mid, val);
    if (found != null) {
      return found;
    }
    found = find(node.right, val);
    return found;
  }

  public int size() { return count(root); }

  public int size(int val) {
    if (root == null) { return 0; }
    Node target = find(root, val);
    return (target == null) ? 0 : count(target);
  }

  public boolean insert(int val) {
    if (root == null) {
      root = new Node(val);
      return true;
    } else {
      Node newRoot = insert(root, val);
      if (newRoot != null) {
        root = newRoot;
        return true;
      }
      return false;
    }
  }

  private Node insert(Node node, int val) {
    if (node.isLeaf()) {
      return addValToNode(node, val, null, null);
    }

    // Check for duplicates. Return null if found
    if (node.k1 == val || node.k2 == val) {
      return null;
    }

    // Pick which child to recur down
    Node child;
    if (val < node.k1) {
      child = node.left;
    } else if (node.isTwo() || (node.k2 != null && val < node.k2)) {
      child = node.mid;
    } else {
      child = node.right;
    }

    // Insert the value down the selected child node
    Node split = insert(child, val);

    if (split != null) {
      return addValToNode(node, split.k1, split.left, split.right);
    }

    return null; // Insertion failure
  }

  // Adds the key in order (smaller key in k1) and handles splitting in the case of a full node
  private Node addValToNode(Node node, int val, Node leftChild, Node rightChild) {
    // If node is a 2 node, simply add the value and adjust the children refs
    if (node.isTwo()) {
      // Ensure k1 is the smallest key
      if (val < node.k1) {
        node.k2 = node.k1;
        node.k1 = val;

        node.right = node.mid;
        node.left = leftChild;
        node.right = rightChild;
      } else {
        node.k2 = val;
        node.mid = leftChild;
        node.right = rightChild;
      }

      return null; // Insertion complete without node splitting
    }

    // else split the node
    int[] keys = new int[3];
    keys[0] = val;
    keys[1] = node.k1;
    keys[2] = node.k2;
    Arrays.sort(keys); // keys[1] will be our promoted value

    // 0,1 are the new left node's left and right
    // 2,3 are the new right node's left and right
    Node[] children = new Node[4];
    if (val < node.k1) {
      children[0] = leftChild;
      children[1] = rightChild;
      children[2] = node.left;
      children[3] = node.right;
    } else if (val < node.k2) {
      children[0] = node.left;
      children[1] = leftChild;
      children[2] = rightChild;
      children[3] = node.right;
    } else {
      children[0] = node.left;
      children[1] = node.mid;
      children[2] = leftChild;
      children[3] = rightChild;
    }

    // New left and right child of promoted node
    Node leftNode = new Node(keys[0]);
    Node rightNode = new Node(keys[2]);

    leftNode.left = children[0];
    leftNode.right = children[1];
    rightNode.left = children[2];
    rightNode.right = children[3];

    // Promote middle key and assign it's left and right nodes to the previously created nodes
    Node promoted = new Node(keys[1]);
    promoted.left = leftNode;
    promoted.right = rightNode;

    return promoted; // Return promoted middle value/new parent node
  }
}