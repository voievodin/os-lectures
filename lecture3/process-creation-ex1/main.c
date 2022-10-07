#include "sys/wait.h"
#include "stdio.h"
#include "unistd.h"

int main() {
    int pid = fork();
    if (pid == 0) {
        printf("child process, my pid %d\n", getpid());
    } else {
        printf("parent process, my pid %d\n", getpid());
    }
    return 0;
}
