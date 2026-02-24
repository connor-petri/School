# Week 6

---

## Binary Search Trees
A binary search tree (BST) is a binary tree data structure that maintains the property that for any given node, the value of all nodes in its left subtree is less than the node's value, and the value of all nodes in its right subtree is greater than the node's value. This property allows for efficient searching, insertion, and deletion operations.

### Operations on BSTs
#### Insertion
To insert a value into a BST, we start at the root and compare the value to be inserted with the current node's value. If the value is less than the current node's value, we move to the left child; if it is greater, we move to the right child. We continue this process until we find an empty spot where the new value can be inserted.

#### Deletion
To delete a value from a BST, we first search for the node containing the value. Once we find the node, we have three cases to consider:
1. If the node has no children, we simply remove it from the tree.
2. If the node has one child, we replace the node with its child.
3. If the node has two children, we find the in-order successor (the smallest value in the right subtree) or the in-order predecessor (the largest value in the left subtree) to replace the node, and then delete the successor or predecessor node.

## 2-3 Trees
2-3 trees are a type of self-balancing binary search tree where each node can have either two or three children. In a 2-3 tree, all leaves are at the same level, and the tree maintains a balanced structure to ensure efficient operations. Each node in a 2-3 tree can contain one or two keys, and the children of a node are organized based on the keys they contain. This structure allows for efficient searching, insertion, and deletion operations while maintaining a balanced tree.

### Insertion in 2-3 Trees
To insert a value into a 2-3 tree, we start at the root and compare the value to be inserted with the keys in the current node. If the value is less than the first key, we move to the left child; if it is between the first and second keys, we move to the middle child; if it is greater than the second key, we move to the right child. If we reach a leaf node, we insert the value there. If the node already contains two keys, we split the node and promote the middle key to the parent node, ensuring that the tree remains balanced. 