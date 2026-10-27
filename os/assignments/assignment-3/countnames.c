#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "namecount.h"

typedef struct Node {
    char *name;
    int count;
    struct Node *next;
} Node;

typedef struct {
    Node *head;
    Node *tail;
} LinkedList;

void LinkedList_AddName(LinkedList *me, char *name) {
    if(me == NULL || name == NULL) {
        return;
    }

    for (Node *cur = me->head; cur != NULL; cur = cur->next) {
        if (strcmp(cur->name, name) == 0) {
            cur->count++;
            return;
        }
    }

    Node *n = calloc(1, sizeof(*n));
    if (n == NULL) {
        return;
    }
    n->name = strdup(name);
    n->count = 1;


    if (me->head == NULL) {
        me->head = n;
    } else {
        me->tail->next = n;
    }
    me->tail = n;
}

/* write() can return short, so keep going until every byte is out. */
static int write_all(int fd, const void *buf, size_t len) {
    const char *p = buf;
    while (len > 0) {
        ssize_t w = write(fd, p, len);
        if (w < 0) {
            return -1;
        }
        p += w;
        len -= w;
    }
    return 0;
}

/* Send one (name, count) pair to the parent through the pipe. */
void write_struct_namecount(int fd, NameCountData *data) {
    if (write_all(fd, data, sizeof(*data)) < 0) {
        perror("write");
    }
}


int main(int argc, char **argv)
{
    FILE *fp;

    char path[300];

    if (argc >= 2) {
        snprintf(path, sizeof(path), "test/%s", argv[1]);
        fp = fopen(path, "r");
        if (fp == NULL) {
            fprintf(stderr, "Cannot open file %s\n", path);
            return 1;
        }
    } else {
        fp = stdin;
    }

    LinkedList *list = calloc(1, sizeof(*list));
    char line[256];
    int num = 1;

    while (fgets(line, sizeof(line), fp) != NULL) {
        line[strcspn(line, "\r\n")] = '\0';
        if (line[0] == '\0' || line[0] == ' ') {
            fprintf(stderr, "Warning - Line %d is empty.\n", num++);
            continue;
        }
        LinkedList_AddName(list, line);
        num++;
    }
    fclose(fp);

    char out_name[64];
    snprintf(out_name, sizeof(out_name), "%d.out", (int)getpid());
    FILE *out = fopen(out_name, "w");
    if (out == NULL) {
        fprintf(stderr, "Cannot create %s\n", out_name);
        return 1;
    }

    // the shell dup2()'d the write end of the pipe onto our stdout, so
    // fd 1 is the pipe. If stdout is a terminal instead fall back to printing text
    for (Node *cur = list->head; cur != NULL; cur = cur->next) {
        fprintf(out, "%s: %d\n", cur->name, cur->count);  // Write to file

        if (isatty(STDOUT_FILENO)) {
            printf("%s: %d\n", cur->name, cur->count);
        } else {
            NameCountData data;
            memset(&data, 0, sizeof(data));
            strncpy(data.name, cur->name, sizeof(data.name) - 1);
            data.count = cur->count;
            write_struct_namecount(STDOUT_FILENO, &data);
        }
    }

    fclose(out);

    return 0;
}