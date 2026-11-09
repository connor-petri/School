#ifndef NAMECOUNT_H
#define NAMECOUNT_H

#include <stddef.h>

// Shared between shell.c (parent) and countnames.c (child).
typedef struct {
    char name[30];
    int count;
} NameCountData;

// Every region of GLOBAL holds this many NameCountData entries. The last
// entry is always left as the all-zero terminator (name[0] == '\0').
#define REGION_ENTRIES 1024
#define REGION_BYTES (REGION_ENTRIES * sizeof(NameCountData))

// The shell tells each child where GLOBAL lives and which slice is its own
// through these environment variables (an anonymous mmap does not survive
// execvp(), so the child has to open and map the same backing file itself).
#define ENV_GLOBAL_NAME "CN_GLOBAL_NAME"    // path of the backing file, e.g. .global_1234
#define ENV_GLOBAL_SIZE "CN_GLOBAL_SIZE"    // total bytes of GLOBAL
#define ENV_GLOBAL_OFFSET "CN_GLOBAL_OFFSET"  // byte offset of this child's region

#endif // NAMECOUNT_H
