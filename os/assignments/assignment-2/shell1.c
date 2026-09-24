#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>

#define MAXLINE 4096
#define MAXARGS 128
./countnames mytest1.txt  names1.txt  names_long_redundant1.txt  names_long_redundant.txt mytest2.txt  names2.txt  names_long_redundant2.txt  names_long.txt mytest3.txt  namesB.txt  names_long_redundant3.txt  names.txt
int main(void)
{
    char buf[MAXLINE];
    char *args[MAXARGS];

    printf("%% ");
    while (fgets(buf, MAXLINE, stdin) != NULL) {
        buf[strcspn(buf, "\r\n")] = '\0';

        // split line into argv
        int n = 0;
        for (char *t = strtok(buf, " \t"); t && n < MAXARGS - 1; t = strtok(NULL, " \t"))
            args[n++] = t;
        args[n] = NULL;

        if (n == 0) {          /* empty line */
            printf("%% ");
            continue;
        }

        // One child per argument, minimum 1 arg
        int nkids = (n > 1) ? n - 1 : 1;
        for (int i = 0; i < nkids; i++) {
            pid_t pid = fork();
            if (pid < 0) {
                perror("fork");
            } else if (pid == 0) {
                char *argv[3] = { args[0], (n > 1) ? args[i + 1] : NULL, NULL };
                execvp(argv[0], argv);
                perror(argv[0]);
                return -1;
            }
        }

        while (wait(NULL) > 0) {}  /* wait for every child */
        printf("%% ");
    }
    printf("\n");
    return 0;
}