section .data
    jobDone db 'Device has been disabled!', 0x0a
    jobDoneL equ $ - jobDone

section .text
    global disable_device

disable_device:
    ; actually disabling device
    mov eax, 4 ; sys_write
    mov ebx, 1 ; stdout
    mov ecx, jobDone
    mov edx, jobDoneL
    int 0x80
    ret
