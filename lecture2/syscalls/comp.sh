nasm -f elf32 $1 -o $2.o
ld -m elf_i386 $2.o -o $2.out
