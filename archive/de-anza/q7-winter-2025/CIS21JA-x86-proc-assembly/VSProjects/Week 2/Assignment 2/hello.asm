; This program diplays hello world!

INCLUDE Irvine32.inc

.data
string byte "hello world!", 0

.code
main PROC
	mov edx, offset string
	call writeString

	mov ebx, -20
	add ebx, 1
	exit
main ENDP
END main