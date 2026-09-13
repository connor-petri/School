#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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


int main(int argc, char **argv)
{
    FILE *fp;

    if (argc >= 2) {
        fp = fopen(argv[1], "r");
        if (fp == NULL) {
            fprintf(stderr, "Cannor open file %s\n", argv[1]);
            return 1;
        }
    } else {
        fp = stdin;
    }

    char *file_name = argv[1];

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

    for (Node *cur = list->head; cur != NULL; cur = cur->next) {
        printf("%s: %d\n", cur->name, cur->count);
    }

    return 0;
}