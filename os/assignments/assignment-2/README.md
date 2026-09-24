# Assignment 2
Connor Petri
CS-149
Fall 2026

---

### Compile
`gcc -o shell shell1.c`
`gcc -o countnames countnames.c`

### Run
Start the shell with no arguments:
`./shell`

Then type commands at the `%` prompt. The shell splits the line on spaces/tabs and forks one child per argument (or one child total if there are no arguments), then waits for all of them before printing the next prompt.

`countnames` prepends `test/` to its filename, so to run it on `names.txt`:
`% ./countnames names.txt`

Each countnames child writes its results to `PID.out` in the current directory instead of stdout. Check them with `cat *.out` from a normal shell after exiting (Ctrl-D). `rm *.out` between tests.


### My Test Cases
#### Test 1
This tests for really long and really short names, plus a repeated name
`% ./countnames mytest1.txt`
should print nothing to the terminal and create one `PID.out` containing:
```
Jo: 2
Anna-Marie O'Connor-Smith XYZ: 1
Li: 1
```

#### Test 2
This test accounts for a file full of empty lines
`% ./countnames mytest2.txt`
should output to stderr:
```
Warning - Line 1 is empty.
Warning - Line 2 is empty.
Warning - Line 3 is empty.
```
and create an empty `PID.out`

#### Test 3
This test accounts for a completely empty file
`% ./countnames mytest3.txt`
should output nothing as eof is encountered immediately, and create an empty `PID.out`

#### Test 4
This tests the actual point of the assignment. multiple files on one line, one child per file
`% ./countnames mytest1.txt mytest2.txt mytest3.txt`
should output the three warnings from Test 2 and create three `PID.out` files, one with the Test 1 counts and two empty. The prompt should not come back until all three children have exited.

#### Test 5
This tests that children don't interfere with each other by giving the same file twice
`% ./countnames mytest1.txt mytest1.txt`
should create two `PID.out` files with identical contents (the Test 1 output). Counts should not be doubled.

### Lessons Learned
The fork/exec/wait pattern was new to me. The part that took the longest to click was that `execvp` replaces the whole process and never returns on success, so anything after it only runs if the exec failed, and the child has to return there or it keeps running the parent's loop as a second copy of the shell. I also got bitten by `strcat` on a string literal when building the `test/` path and had to switch to `snprintf` into a buffer.

### References
https://man7.org/linux/man-pages/man2/fork.2.html
https://man7.org/linux/man-pages/man3/exec.3.html
https://man7.org/linux/man-pages/man2/wait.2.html
https://man7.org/linux/man-pages/man3/strtok.3.html
https://stackoverflow.com/questions/8501110/how-to-concatenate-two-strings-in-c

### Acknowledgments
- APUE shell1.c for the starting point
- My time in Spartan Racing taught me almost all of the C I know