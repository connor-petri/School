# Assignment 1
Connor Petri
CS-149
Fall 2026

---

### Compile
`gcc -o countnames countnames.c`

### Run
To run a test case, for example `names.txt`:
`./countnames test/names.txt`


### My Test Cases
#### Test 1
This tests for really long and really short names
`./countnames test/mytest1.txt`
should output:
```
Jo: 2
Anna-Marie O'Connor-Smith XYZ: 1
Li: 1
```

#### Test 2
This test accounts for a file full of empty lines
`./countnames test/mytest2.txt`
should output:
```
Warning - Line 1 is empty.
Warning - Line 2 is empty.
Warning - Line 3 is empty.
```

#### Test 3
This test accounts for a completely empty file
`./countnames test/mytest2.txt`
should output nothing as eof is encountered immediately:
```
 
```

### Lessons Learned
I write a lot of C during my work with Spartan Racing, so most of what I learned has to do with file IO and stderr/stdout, neither of which I have to deal with when writing vehicle control software. I learned how to print to stderr and how to use stdout as a "file".

### References
https://stackoverflow.com/questions/39002052/how-can-i-print-to-standard-error-in-c-with-printf
https://stackoverflow.com/questions/69337102/compare-strings-c

### Acknowledgments
- My time in Spartan Racing taught me almost all of the C I know
- My C++ professors for teaching me about c strings 