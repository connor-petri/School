#ifndef NAMECOUNT_H
#define NAMECOUNT_H

// Shared between shell.c (parent) and countnames.c (child).
typedef struct {
    char name[30];
    int count;
} NameCountData;

#endif // NAMECOUNT_H