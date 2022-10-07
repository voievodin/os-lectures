#include "stdio.h"
#include "signal.h"
#include "unistd.h"

void sig_handler(int sig) {
    printf("I WON'T STOP!!!!!\n");
}

int main(){
    signal(SIGINT, sig_handler);
    for (int i = 1; ; i++) {
        printf("Main loop: %d\n", i);
        sleep(1);
    }
    return 0;
}
