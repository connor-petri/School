INCLUDE Irvine32.inc

.data
firstint DWORD ?
secondint DWORD ?
thirdint DWORD ?
totalint DWORD ?
prompt1 byte "please enter first integer: ", 0
prompt2 byte "please enter second integer: ", 0
prompt3 byte "please enter third integer: ", 0
result byte "This is the result of your values: ", 0
remainderText byte " R: ", 0

.code
main PROC

	; Prompt for first integer
	mov edx, offset prompt1
	call writestring
	call readint
	mov firstint, eax ; stores first input in firstint
	; Prompt for second integer
	mov edx, offset prompt2
	call writestring
	call readint
	mov secondint, eax ; stores second input in secondint
	; Prompt for third integer
	mov edx, offset prompt3
	call writestring
	call readint
	mov thirdint, eax ; stores third input in thirdint
	; Perform the math
	; Calculate num1^3
	mov eax, firstint
	imul eax, eax
	imul eax, firstint
	; Multiply num1^3 by num2
	mov ebx, secondint
	imul eax, ebx
	; Calculate 5 * num2^2
	mov ebx, secondint
	imul ebx, ebx
	imul ebx, 5
	; Add the results
	add eax, ebx
	; Clear edx to prepare for division
	xor edx, edx
	; Divide by num3
	mov ebx, thirdint
	div ebx
	; Output the results
	mov edx, offset result
	call writestring
	call writeint ; Display the results
	; Output the remainder
	mov edx, offset remainderText
	call writestring
	call writeint ; Displays remainder
	exit
main ENDP
END main