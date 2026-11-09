#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include "namecount.h"

#define MAXLINE 4096
#define MAXARGS 128

// Add one instance of NameCountData to the sum
static void sum_add(NameCountData *sum, const char *name, int count) {
    int i;
    for (i = 0; i < REGION_ENTRIES - 1 && sum[i].name[0] != '\0'; i++) {
        if (strcmp(sum[i].name, name) == 0) {
            sum[i].count += count;
            return;
        }
    }
    // Drop if full nd increase region size before next run
    if (i >= REGION_ENTRIES - 1) {
        fprintf(stderr, "Warning - summation region full, dropping %s.\n", name);
        return;
    }
    memset(&sum[i], 0, sizeof(sum[i]));
    strncpy(sum[i].name, name, sizeof(sum[i].name) - 1);
    sum[i].count = count;
    sum[i + 1].name[0] = '\0';   // new terminator
}

// Walk one child's region until the empty name terminator and add every
//entry into the summation region.
static void aggregate_region(const NameCountData *region, NameCountData *sum) {
    for (int i = 0; i < REGION_ENTRIES && region[i].name[0] != '\0'; i++) {
        char name[sizeof(region[i].name)];
        memcpy(name, region[i].name, sizeof(name));
        name[sizeof(name) - 1] = '\0';
        sum_add(sum, name, region[i].count);
    }
}

// Create the GLOBAL shared segment. Returns void * to region
static void *create_global(const char *path, size_t size) {
    int fd = open(path, O_CREAT | O_EXCL | O_RDWR, 0600);
    if (fd < 0) {
        perror(path);
        return NULL;
    }
    if (ftruncate(fd, size) < 0) {
        perror("ftruncate");
        close(fd);
        unlink(path);
        return NULL;
    }
    void *global = mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    close(fd);
    if (global == MAP_FAILED) {
        perror("mmap");
        unlink(path);
        return NULL;
    }
    return global;
}

int main(void)
{
    char buf[MAXLINE];
    char *args[MAXARGS];
    pid_t pids[MAXARGS];
    char global_path[64];

    snprintf(global_path, sizeof(global_path), ".global_%d", (int) getpid());

    printf("%% ");
    while (fgets(buf, MAXLINE, stdin) != NULL) {
        buf[strcspn(buf, "\r\n")] = '\0'; // Strip /r/n. Add null terminator

        // split line into argv
        int n = 0;
        for (char *t = strtok(buf, " \t"); t && n < MAXARGS - 1; t = strtok(NULL, " \t"))
            args[n++] = t;
        args[n] = NULL;

        if (n == 0) {
            printf("%% ");
            continue;
        }

        // One child per argument, minimum 1 arg.
        int nkids = (n > 1) ? n - 1 : 1;

        // GLOBAL layout: region 0..nkids-1 belong to the children, the last
        // region (index nkids) is the summation region.
        size_t global_size = (size_t) (nkids + 1) * REGION_BYTES;
        void *GLOBAL = create_global(global_path, global_size);
        if (GLOBAL == NULL) {
            printf("%% ");
            continue;
        }
        NameCountData *sum = (NameCountData *) ((char *) GLOBAL + (size_t) nkids * REGION_BYTES);

        // Tell the children where GLOBAL's backing file is. The offset is set per child
        // right before execvp(), after fork() so the parent's copy is untouched.
        char size_s[32];
        snprintf(size_s, sizeof(size_s), "%zu", global_size);
        
        // Set environment variables with context child needs
        setenv(ENV_GLOBAL_NAME, global_path, 1);
        setenv(ENV_GLOBAL_SIZE, size_s, 1);

        for (int i = 0; i < nkids; i++) {
            pids[i] = fork();
            if (pids[i] < 0) {
                perror("fork");
            } else if (pids[i] == 0) {
                // Child: export the byte offset of our region, then exec.
                char off_s[32];
                snprintf(off_s, sizeof(off_s), "%zu", (size_t) i * REGION_BYTES);
                // Set offset env variable for each child spawned
                setenv(ENV_GLOBAL_OFFSET, off_s, 1);

                char *argv[3] = { args[0], (n > 1) ? args[i + 1] : NULL, NULL };
                execvp(argv[0], argv);

                // Only run if execvp returns, indicating err
                perror(argv[0]);
                _exit(-1);
            }
        }

        // Wait for every child, as before. Remember who exited cleanly so
        // only successful regions get aggregated.
        int ok[MAXARGS] = { 0 };
        int status;
        pid_t pid;
        while ((pid = wait(&status)) != -1) {
            for (int i = 0; i < nkids; i++) {
                if (pids[i] == pid) {
                    ok[i] = WIFEXITED(status) && WEXITSTATUS(status) == 0;
                }
            }
        }

        // Aggregate
        for (int i = 0; i < nkids; i++) {
            if (!ok[i]) continue;
            NameCountData *region = (NameCountData *) ((char *) GLOBAL + (size_t) i * REGION_BYTES);
            aggregate_region(region, sum);
        }

        // Print the collected counts in the order they were seen.
        for (int i = 0; i < REGION_ENTRIES && sum[i].name[0] != '\0'; i++) {
            printf("%s: %d\n", sum[i].name, sum[i].count);
        }

        munmap(GLOBAL, global_size);
        unlink(global_path);
        unsetenv(ENV_GLOBAL_NAME);
        unsetenv(ENV_GLOBAL_SIZE);

        printf("%% ");
    }
    printf("\n");
    return 0;
}
