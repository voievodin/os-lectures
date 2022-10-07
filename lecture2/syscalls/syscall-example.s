global _start
_start:
    mov eax, 1 ;sys_exit
    mov ebx, 42 ;exit code
    int 0x80
