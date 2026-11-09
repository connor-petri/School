# Assignment 4
Connor Petri
CS-149
Fall 2026

---

### Compile
`gcc -o shell shell.c -Wall -Wextra`
`gcc -o countnames countnames.c -Wall -Wextra`

### Run
Start the shell with no arguments:
`./shell`

Then type commands at the `%` prompt. The shell splits the line on spaces/tabs and forks one child per argument (or one child total if there are no arguments), then waits for all of them before printing the next prompt.

`countnames` prepends `test/` to its filename, so to run it on `names.txt`:
`% ./countnames names.txt`

Each countnames child still writes its results to `PID.out` in the current directory. `rm *.out` between tests.

### How the shared memory works
An anonymous `mmap` does not survive `execvp()`, so the shell creates `GLOBAL` as a memory-mapped regular file.

`GLOBAL` is `(nkids + 1) * REGION_BYTES` long. Regions `0...nkids-1` belong to the children and region `nkids` is the summation region, where name totals are recorded. 

The shell exports three environment variables before `execvp()`: the backing file's path, the total size of `GLOBAL`, and that child's byte offset (`i * REGION_BYTES`). The child opens the same object, maps it, and computes its region with `(NameCountData *)((char *)GLOBAL + offset)`. It writes one struct per unique name and leaves the next entry zeroed as the terminator.

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
Same file twice. Each child gets its own region of `GLOBAL`, so the parent sums the two regions and every count doubles
`% ./countnames mytest1.txt mytest1.txt`
should print:
```
Jo: 4
Anna-Marie O'Connor-Smith XYZ: 2
Li: 2
```
 
#### Test 3
Children that write nothing (all empty lines / completely empty file) leave their region as all terminators, which doesn't break the aggregation
`% ./countnames mytest1.txt mytest2.txt mytest3.txt`
should print the three "Line N is empty" warnings from `mytest2.txt` to stderr and then only the `mytest1.txt` counts:
```
Jo: 2
Anna-Marie O'Connor-Smith XYZ: 1
Li: 1
```
 
#### Test 4
A child that fails to open its file exits 1; the shell checks the exit status from `wait()` and skips that region, the rest still get summed
`% ./countnames nope.txt names.txt`
should print `Cannot open file test/nope.txt` to stderr and then the `names.txt` counts.
 
#### Test 5
Five children writing into five different regions of `GLOBAL` at the same time; checks nobody stomps on a neighbour's region
`% ./countnames names_long.txt names_long_redundant.txt names_long_redundant1.txt names_long_redundant2.txt names_long_redundant3.txt`
should print the combined list: `MARY SMITH: 4` and `Avery Taylor: 2`, which match `cat test/names_long*.txt | grep -c ...`.

### Lessons Learned
The thing that caught me up was that `MAP_SHARED | MAP_ANONYMOUS` memory is only shared with children that stay in the forked image. The moment the child calls `execvp()` it gets a brand-new address space and the mapping is gone, so the first version "worked" (no crash) but the parent always read an empty region. The fix was backing the mapping with a real file so the exec'd child can `open()` and `mmap()` the same file itself, exactly like the memory-mapped file I/O from the IPC slides.

The `mmap` offset argument also has to be page aligned, so the child maps all of `GLOBAL` and does the pointer arithmetic to its region instead of trying to map just its slice.

### References
https://man7.org/linux/man-pages/man2/fork.2.html
https://man7.org/linux/man-pages/man3/exec.3.html
https://man7.org/linux/man-pages/man2/wait.2.html
https://man7.org/linux/man-pages/man3/strtok.3.html
https://man7.org/linux/man-pages/man2/mmap.2.html
https://man7.org/linux/man-pages/man2/ftruncate.2.html
https://stackoverflow.com/questions/8501110/how-to-concatenate-two-strings-in-c

### Acknowledgments
- My time in Spartan Racing taught me almost all of the C I know