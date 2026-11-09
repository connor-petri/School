#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/mman.h>
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

// Map global segment
static NameCountData *map_region(void **global_out, size_t *size_out) {
    // Environment variables used to get mmap name, size, and this child's offset
    const char *name = getenv(ENV_GLOBAL_NAME);   // backing file the shell created
    const char *size_s = getenv(ENV_GLOBAL_SIZE);
    const char *off_s = getenv(ENV_GLOBAL_OFFSET);
    if (name == NULL || size_s == NULL || off_s == NULL) {
        return NULL;
    }

    size_t size = strtoul(size_s, NULL, 10);
    size_t offset = strtoul(off_s, NULL, 10);
    if (offset + REGION_BYTES > size) {
        fprintf(stderr, "Region offset %zu is outside GLOBAL (%zu bytes)\n", offset, size);
        return NULL;
    }

    int fd = open(name, O_RDWR);
    if (fd < 0) {
        perror(name);
        return NULL;
    }

    void *global = mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    close(fd);   // the mapping stays valid after the fd is closed

    if (global == MAP_FAILED) {
        perror("mmap");
        return NULL;
    }

    *global_out = global;
    *size_out = size;

    // Same pointer arithmetic the parent used to hand out the region.
    return (NameCountData *) ((char *) global + offset);
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

    // If the shell started us it exported where our slice of GLOBAL is.
    // Otherwise (run by hand from bash) just print the counts as text.
    void *global = NULL;
    size_t global_size = 0;
    NameCountData *region = map_region(&global, &global_size);

    int i = 0;
    for (Node *cur = list->head; cur != NULL; cur = cur->next) {
        fprintf(out, "%s: %d\n", cur->name, cur->count);  // Write to file

        if (region == NULL) {
            printf("%s: %d\n", cur->name, cur->count);
            continue;
        }

        // Keep the last slot as the terminator so the parent always stops.
        if (i >= REGION_ENTRIES - 1) {
            fprintf(stderr, "Warning - region full, dropping remaining names.\n");
            break;
        }
        memset(&region[i], 0, sizeof(region[i]));
        strncpy(region[i].name, cur->name, sizeof(region[i].name) - 1);
        region[i].count = cur->count;
        i++;
    }

    if (region != NULL) {
        region[i].name[0] = '\0';   // end-of-data marker (already zero, but be explicit)
        munmap(global, global_size);
    }

    fclose(out);

    return 0;
}
