#include "sys/wait.h"
#include "stdio.h"
#include "unistd.h"

int main(int argc, char *argv[]) {
    int pid = fork();
    if (pid == 0) {
        execlp("/bin/cat", "cat", "main.c", NULL);
    } else {
        printf("parent process, my pid %d\n", getpid());
    }
    return 0;
}
