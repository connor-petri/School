# Week 11

---

## Sorting Arrays of Numbers
- Not done very often in real life
- But a great way to think about tasks that are often done in programming projects
- Also a simple way to approach analysis of algorithm performance time complexity
- The easy/obvious algorithms are slow
    - **Selection Sort:** many visits, few moves
    - **Insertion Sort:** many moves, few visits
- The smart algorithms are fast and recursive
    - **Merge Sort**
    - **Quick Sort**

### Selection Sort
- Swap members until the array is sorted
    - Find smallest member, put it in ```a[0]```
    - Find next smallest member, put it in ```a[1]```
    - etc.

## Complexity Analysis
- Need to know how long it will take to sort $n$ items in a way that is hardware-agnostic
- Expressed in "big-O" notation (i.e. $O(n^2)$)
- Count # of array visits