include irvine32.inc

.data
var1 dword 15
var2 dword 5
var3 dword 3

num1 word 2
num2 word 3

a byte 0
b byte 0
d byte 0

.code
main2 proc

; #3
mov eax, 05h
add eax, 0FFFFFFFAh


; #4
mov ebx, var1
mov ecx, var2
mov edx, var3
sub ebx, ecx
sub ebx, 9
add ebx, edx


; #5
mov ax, 4
mov num1, ax
mov num2, ax


; #6
mov al, 'w'
mov d, al
mov a, al
add a, 1
mov b, al
add b, 2

	exit
main2 endp
end main2