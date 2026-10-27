# Assignment 3
Connor Petri
CS-149
Fall 2026

---

### Compile
`gcc -o shell shell1.c -Wall -Wextra`
`gcc -o countnames countnames.c -Wall -Wextra`

### Run
Start the shell with no arguments:
`./shell`

Then type commands at the `%` prompt. The shell splits the line on spaces/tabs and forks one child per argument (or one child total if there are no arguments), then waits for all of them before printing the next prompt.

`countnames` prepends `test/` to its filename, so to run it on `names.txt`:
`% ./countnames names.txt`

Each countnames child writes its results to `PID.out` in the current directory instead of stdout. Check them with `cat *.out` from a normal shell after exiting (Ctrl-D). `rm *.out` between tests.


### My Test Cases
#### Test 1
Two different files, overlapping names get summed
`% ./countnames names.txt names2.txt`
should print the empty-line warnings on stderr and then:
```
Nicky: 1
Dave Joe: 2
Yuan Cheng Chang: 3
John Smith: 1
Jenn Xu: 2
Tom Wu: 1
```
 
#### Test 2
Same file twice. In A2 this produced two identical `.out` files; in A3 the parent sums them so every count doubles
`% ./countnames mytest1.txt mytest1.txt`
should print:
```
Jo: 4
Anna-Marie O'Connor-Smith XYZ: 2
Li: 2
```
 
#### Test 3
Children that send nothing (all empty lines / completely empty file) don't break the aggregation
`% ./countnames mytest1.txt mytest2.txt mytest3.txt`
should print the three "Line N is empty" warnings from `mytest2.txt` to stderr and then only the `mytest1.txt` counts:
```
Jo: 2
Anna-Marie O'Connor-Smith XYZ: 1
Li: 1
```
 
#### Test 4
A child that fails to open its file exits 1 and is skipped, the rest still get summed
`% ./countnames nope.txt names.txt`
should print `Cannot open file test/nope.txt` to stderr and then the `names.txt` counts.
 
#### Test 5
Lots of data through several pipes at once (bigger than one `read()` typically returns)
`% ./countnames names_long.txt names_long_redundant.txt names_long_redundant1.txt names_long_redundant2.txt names_long_redundant3.txt`
should print the combined list: `MARY SMITH: 4` and `Avery Taylor: 2`, which match `cat test/names_long*.txt | grep -c ...`.

### Lessons Learned
The thing that caught me up was forgetting to close the write end of the pipe in the parent: the children would finish but the shell hung on `read()` forever.

### References
https://man7.org/linux/man-pages/man2/fork.2.html
https://man7.org/linux/man-pages/man3/exec.3.html
https://man7.org/linux/man-pages/man2/wait.2.html
https://man7.org/linux/man-pages/man3/strtok.3.html
https://stackoverflow.com/questions/8501110/how-to-concatenate-two-strings-in-c

### Acknowledgments
- My time in Spartan Racing taught me almost all of the C I know