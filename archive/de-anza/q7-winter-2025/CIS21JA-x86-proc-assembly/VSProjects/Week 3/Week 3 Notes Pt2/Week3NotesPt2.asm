include irvine32.inc

.data
message byte "Enter a number: ", 0
mult byte "num1 * num2 = ", 0
q byte "Q: ", 0
r byte "R: ", 0

num1 dword ?
num2 dword ?
ans dword ?


.code
main proc

	mov edx, offset message
	call writeString

	call readDec
	mov num1, eax
	
	call writeString

	call readDec
	mov num2, eax

	mov eax, num1
	mov ebx, num2
	mul ebx

	mov edx, offset mult
	call writeString
	call writeDec


	; div
	sub edx, edx
	mov eax, num1
	div num2
	mov ebx, edx

	mov edx, offset q
	call writeString	; edx
	call writeDec		; eax

	mov eax, ebx
	mov edx, offset r
	call writeString
	call writeDec
	

	; example: ans = num1 / (num2 - 2) ; 4 bytes

	mov ebx, num2
	sub ebx, 2
	mov eax, num1

	sub edx, edx
	div ebx
	call writeDec
	mov eax, edx
	call writeDec

	exit
main endp
end main