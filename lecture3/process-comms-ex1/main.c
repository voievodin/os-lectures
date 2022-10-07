#include "stdio.h"
#include "stdlib.h"
#include "sys/mman.h"
#include "unistd.h"

const int SHARED_SLOTS = 3;

void print_slot_values(int *mem) {
    printf("Shared memory:\n");
    for (int i = 0; i < SHARED_SLOTS; i++) {
        printf("  mem[%d] = %d\n", i, mem[i]);
    }
    printf("\n");    
}

void do_heavy_computation(int slot, int *mem) {
    printf("[CHILD %d] Doing heavy computation\n", getpid());
    sleep(slot + 10);
    mem[slot] = (slot + 1) * 2; 
}


int main() {
    // TODO: modify to use malloc and check mem values in each process
    int *mem = (int *) (
        mmap(
            NULL, 
            sizeof(int) * SHARED_SLOTS, 
            PROT_READ | PROT_WRITE, 
            MAP_SHARED | MAP_ANONYMOUS, 
            -1, 
            0
        )
    );

    print_slot_values(mem);

    int pids[3];

    // child 1
    if ((pids[0] = fork()) == 0) {
        do_heavy_computation(0, mem);
        return 0;
    }
    
    // child 2
    if ((pids[1] = fork()) == 0) {
        do_heavy_computation(1, mem);
        return 0;
    }
    
    // child 3
    if ((pids[2] = fork()) == 0) {
        do_heavy_computation(2, mem);
        return 0;
    }
    
    printf("[PARENT] Waiting for heavy jobs to complete\n");
    for (int i = 0; i < SHARED_SLOTS; i++) {
        waitpid(pids[i], "", NULL);
    }
    printf("[PARENT] Heavy jobs DONE!\n");
    print_slot_values(mem);
    
    munmap(mem, sizeof(int) * SHARED_SLOTS);
}
