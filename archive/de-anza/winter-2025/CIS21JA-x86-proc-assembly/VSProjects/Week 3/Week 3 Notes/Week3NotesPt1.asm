; AddTwo.asm - adds two 32-bit integers.
; Chapter 3 example

include irvine32.inc

.data
message byte "enter a number: ", 0Ah, 0 ; 0Ah and 0Dh are newline characters
var1 dword ?
var2 dword ?
res dword ?

; byte array examples
arr1	byte 10, 20, 30, 40			; initializer examples
arr2	byte 10, 20, 30, 40
		byte 50, 60, 70, 80

arr3 byte 20 dup(20)				; 20 bytes all initialized to 0
arr4 byte 20 dup(?)					; 20 bytes uninitialized
arr5 byte 4 dup("STACK")			; 20 bytes: STACKSTACKSTACKSTACK
arr6 byte 10, 3 dup(0), 20			; 10, 0, 0, 0, 20 => 5 bytes

COUNTER = 10000						; #define COUNTER 100000
list1 dword COUNTER dup(5)			; COUNTER elements of 5

list2 byte 01, 02, 03, 04, 05, 06
list2Size = $ - list2				; $ is the current location counter, which is the address of the next byte to be assembled
									; thus $ - list2 gives us the size of the array, as list2 is the address of the first element in list2
									; THIS MUST COME DIRECTLY AFTER ASSEMBLING THE ARRAY OR $ WILL NOT REPRESENT THE CORRECT BYTE FOR THIS TO WORK

list3 dword 1, 2, 3, 4, 5
list3Size = ($ - list3) / 4			; RESULT MUST BE DIVIDED BY THE SIZE IN BYTES OF THE TYPE BEING COLLECTED

.code
main proc
	mov edx, offset message
	call writeString

	call readDec					; into eax
	mov var1, eax
	neg var1						; var1 *= -1
	mov eax, var1
	call writeInt					; writeInt is for signed ints writeDec is for unsigned ints


	mov eax, COUNTER


	; MUL (register/variable)

	mul bl							; ax = bl * al => al is used when mul is called on 1 byte and is stored in ax
	mul bx							; dx:ax = bx * ax => ax is used when mul is called on 2 bytes and is stored in dx:ax with the least significant digits in ax
	mul ebx							; edx:eax = ebx * eax => eax is used when mul is called on 4 bytes and is stored in edx:eax with the least significant digits in eax

	; DIV (register/variable)

	div bl							; al = ax / bl && ah = ax % bl => quotient in al and remainder in ah when divisor is 1 byte
	div bx							; ax = dx:ax / bx && dx = dx:ax % bx => quotient in ax and remainder in dx when divisor is 2 bytes. dx:ax is used as the other divisor for compatibility with mul
	div ebx							; eax = edx:eax / ebx && edx = edx:edx % ebx => quotient in eax and remainder in dx when divisor is 4 bytes. edx:eax is used as the other divisor.

	exit
main endp
end main