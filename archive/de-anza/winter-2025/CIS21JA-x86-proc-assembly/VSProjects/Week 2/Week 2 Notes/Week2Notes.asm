include irvine32.inc

.data
msg byte "Hello World!", 0	; null terminator

num1 dword 3				; dword is a 4 byte integer
num2 dword 5				

letter1 byte 'a'			; 1 byte
letter2 byte 'b'			

.code
main proc
	mov edx, offset msg		; edx = &msg
	call writeString		; edx must be used for this subroutine bc irvine said so?

	mov eax, num2			; num2 = num1 requires 2 operations. Move num2 to a register
	mov num1, eax			; then move eax to num1

	mov al, letter2			; move letter2 to the lowest byte of EAX
	mov letter1, al			; move the lowest byte of EAX to letter1

	movzx ax, bl			; movzx requires destination be larger than source
							; this instruction adds zeros to the most significant bits
							; source cannot be immediate (i.e. movzx ebx, 4) source must be register or RAM

	mov bl, 10001111b		; movsx fills the upper half of the destination with a copy of the source operand's sign bit.
	movsx ax, bl			; ax now contains 1111,1111,1000,1111b

	call writeString;

	exit
main endp
end main