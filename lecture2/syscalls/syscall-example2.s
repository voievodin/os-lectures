global _start

section .data
    msg db "Hey!"
    len equ $- msg
    
section .text
_start:
    mov eax, 4 ; sys_write system call
    mov ebx, 1 ; stdout
    mov ecx, msg
    mov edx, len
    int 0x80
    
    mov eax, 1 ; sys_exit
    mov ebx, 0
    int 0x80
