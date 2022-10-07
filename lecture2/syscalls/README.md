To try things out you need to have `nasm`, `ld` and `strace` installed.

Try running privileged instruction
```bash
./comp.sh hlt.s hlt
./hlt.out
```

Try executing simple system call (exit)
```bash
./comp.sh syscall-example.s s1
./s1.out
echo $?
strace ./s1.out
```

Try executing two simple system calls (sys_write + exit)
```bash
./comp.sh syscall-example2.s s2
./s2.out
echo $?
strace ./s2.out
```
