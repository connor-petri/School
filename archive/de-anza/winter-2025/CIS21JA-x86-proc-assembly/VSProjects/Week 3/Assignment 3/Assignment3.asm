; Connor Petri
; Assignment 3
; CIS21JA
; 2025-01-28

; goal: collect num1, num2, and num3 from the user, compute ((num1 ^ 3) * num2 + 5 * (num2 ^ 2)) / num3, and display it back to the user

; assumptions: The user will only enter positive integers and the result will be no larger then 4 bytes

include irvine32.inc

.data
	num1 dword ?				; using dword so that all numbers are 4 bytes. This accounts for the possibility of the user
	num2 dword ?				; entering an integer greater than 2 bytes for any of the variables.
	num3 dword ?				; the most significant 4 bytes stored in edx when using mul will be disregarded due to the assumption
								; that the calculation will not exceed 4 bytes.

	str1 byte "Num 1: ", 0
	str2 byte "Num 2: ", 0
	str3 byte "Num 3: ", 0
	r byte " R: ", 0


.code
main proc

	; Input gathering
	mov edx, offset str1
	call writeString
	call readDec
	mov num1, eax

	call crlf

	mov edx, offset str2
	call writeString
	call readDec
	mov num2, eax

	call crlf

	mov edx, offset str3
	call writeString
	call readDec
	mov num3, eax

	call crlf

	; Calculation
	mov eax, num1				; eax = num1
	mul num1					; edx:eax = num1 ^ 2, but we disregard edx due to our maximum size assumption
	mul num1					; eax = num1 ^ 3
	mul num2					; eax = (num1 ^ 3) * num2
	mov ecx, eax				; ecx = (num1 ^ 3) * num2 (moved out of eax to facilitate order of operations)
	
	mov eax, num2				; eax = num2
	mul num2					; eax = num2 ^ 2, once again disregarding the most significant 4 bytes due to our assumption
	mov ebx, 5
	mul ebx						; eax = 5 * (num2 ^ 2)

	add eax, ecx				; eax = (num1 ^ 3) * num2 + 5 * (num2 ^ 2)
	sub edx, edx				; zero edx in preperation for the remainder
	div num3					; eax = ((num1 ^ 3) * num2 + 5 * (num2 ^ 2)) / num3
								; edx = ((num1 ^ 3) * num2 + 5 * (num2 ^ 2)) % num3
	mov ebx, edx				; move the remainder out of edx in preperation for writeString

	; Output
	call writeDec
	mov edx, offset r
	call writeString
	mov eax, ebx
	call writeDec

	exit
main endp
end main

; INPUT: 1, 2, 3
; OUTPUT: 7 R: 1

; INPUT 4, 6, 9
; OUTPUT 62 R: 6