#include "sys/wait.h"
#include "stdio.h"
#include "unistd.h"

int main(int argc, char *argv[]) {
    int pid = fork();
    if (pid == 0) {
        sleep(5);
        printf("child done!\n");
    } else {
        waitpid(pid, NULL, 0);
        printf("parent process, my pid %d\n", getpid());
    }
    return 0;
}
