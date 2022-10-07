```
nasm -f elf64 device-management-lib.s -o lib.o
gcc -no-pie main.c lib.o -o main.out
./main.out
```
