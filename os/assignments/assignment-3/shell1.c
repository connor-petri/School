#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include "namecount.h"

#define MAXLINE 4096
#define MAXARGS 128

// Same list as countnames.c, but it holds the totals across children.
typedef struct Node {
    char *name;
    int count;
    struct Node *next;
} Node;

typedef struct {
    Node *head;
    Node *tail;
} LinkedList;

static void LinkedList_AddCount(LinkedList *me, const char *name, int count) {
    if (me == NULL || name == NULL) {
        return;
    }

    for (Node *cur = me->head; cur != NULL; cur = cur->next) {
        if (strcmp(cur->name, name) == 0) {
            cur->count += count;
            return;
        }
    }

    Node *n = calloc(1, sizeof(*n));
    if (n == NULL) {
        return;
    }
    n->name = strdup(name);
    n->count = count;

    if (me->head == NULL) {
        me->head = n;
    } else {
        me->tail->next = n;
    }
    me->tail = n;
}

static void LinkedList_Free(LinkedList *me) {
    Node *cur = me->head;
    while (cur != NULL) {
        Node *next = cur->next;
        free(cur->name);
        free(cur);
        cur = next;
    }
    me->head = me->tail = NULL;
}

// read() may return fewer bytes than asked for, so loop 
static int read_all(int fd, void *buf, size_t len) {
    char *p = buf;
    size_t got = 0;
    while (got < len) {
        ssize_t r = read(fd, p + got, len - got);
        if (r < 0) {
            return -1;
        }
        if (r == 0) {
            return (got == 0) ? 0 : -1;
        }
        got += r;
    }
    return 1;
}

// Drain one child's pipe, folding every NameCountData into the totals.
static void read_from_pipe(int fd, LinkedList *totals) {
    NameCountData data;
    int rc;
    while ((rc = read_all(fd, &data, sizeof(data))) == 1) {
        data.name[sizeof(data.name) - 1] = '\0';   
        LinkedList_AddCount(totals, data.name, data.count);
    }
    if (rc < 0) {
        fprintf(stderr, "Warning - truncated or failed read from child pipe.\n");
    }
}

int main(void)
{
    char buf[MAXLINE];
    char *args[MAXARGS];
    int pipes[MAXARGS][2];
    LinkedList totals = { NULL, NULL };

    printf("%% ");
    while (fgets(buf, MAXLINE, stdin) != NULL) {
        buf[strcspn(buf, "\r\n")] = '\0';

        // split line into argv
        int n = 0;
        for (char *t = strtok(buf, " \t"); t && n < MAXARGS - 1; t = strtok(NULL, " \t"))
            args[n++] = t;
        args[n] = NULL;

        if (n == 0) {          
            printf("%% ");
            continue;
        }

        // One child per argument, minimum 1 arg, one pipe per child
        int nkids = (n > 1) ? n - 1 : 1;
        for (int i = 0; i < nkids; i++) {
            if (pipe(pipes[i]) < 0) {
                perror("pipe");
                pipes[i][0] = pipes[i][1] = -1;
                continue;
            }

            pid_t pid = fork();
            if (pid < 0) {
                perror("fork");
                close(pipes[i][0]);
                close(pipes[i][1]);
                pipes[i][0] = pipes[i][1] = -1;
            } else if (pid == 0) {
                //Child: stdout becomes the write end of our pipe, so
                // countnames just writes structs to fd 1. Close every
                // pipe fd we inherited so the parent sees EOF properly. 
                dup2(pipes[i][1], STDOUT_FILENO);
                for (int j = 0; j <= i; j++) {
                    if (pipes[j][0] >= 0) close(pipes[j][0]);
                    if (pipes[j][1] >= 0) close(pipes[j][1]);
                }

                char *argv[3] = { args[0], (n > 1) ? args[i + 1] : NULL, NULL };
                execvp(argv[0], argv);
                perror(argv[0]);
                _exit(-1);
            } else {
                // Parent keeps only the read end.
                close(pipes[i][1]);
                pipes[i][1] = -1;
            }
        }

        //Collect every child's results. Reading to EOF on each pipe also
         // means that child has finished writing. 
        for (int i = 0; i < nkids; i++) {
            if (pipes[i][0] < 0) continue;
            read_from_pipe(pipes[i][0], &totals);
            close(pipes[i][0]);
        }

        while (wait(NULL) != -1) {}  //wait for every child

        //Print the collected counts in the order they were seen.
        for (Node *cur = totals.head; cur != NULL; cur = cur->next) {
            printf("%s: %d\n", cur->name, cur->count);
        }
        LinkedList_Free(&totals);

        printf("%% ");
    }
    printf("\n");
    return 0;
}

//./countnames mytest1.txt names1.txt names_long_redundant1.txt names_long_redundant.txt mytest2.txt names2.txt names_long_redundant2.txt names_long.txt mytest3.txt namesB.txt names_long_redundant3.txt names.txt