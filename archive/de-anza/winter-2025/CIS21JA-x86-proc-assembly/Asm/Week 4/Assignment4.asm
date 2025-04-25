; Connor Petri
; CIS21JA
; 2025-02-05
; This program takes 10 inputs from the user, stores them, returns basic statistics on the set, and rotates the array one element at a time until it has been reversed.

include irvine32.inc

.data
arr DWORD 10 dup(?)

enterPrompt BYTE "Please enter a number: ", 0
sumString BYTE "The sum is: ", 0
meanString BYTE "The mean is: ", 0
arrayString BYTE "The original array: ", 0
rotationString BYTE "After a rotation: ", 0

slash BYTE "/", 0
space BYTE " ", 0
newline BYTE 0Ah, 0

swapStart DWORD 0

.code
main proc

mov edi, OFFSET arr
xor ebx, ebx			; Stores the sum

mov ecx, LENGTHOF arr	; Set up ecx for loop counting

INPUT:
	mov edx, OFFSET enterPrompt		; prompt the user
	call writeString				;
	call readInt					; read input
	mov [edi], eax					; add input to arr
	add ebx, eax					; update sum
	add edi, TYPE arr				; inc arr pointer
	loop INPUT
	
	; Display sum
	mov edx, OFFSET sumString
	call writeString
	mov eax, ebx
	call writeDec
	mov edx, OFFSET newline
	call writeString

	; Display meanString
	mov edx, OFFSET meanString
	call writeString

	; Calculate mean
	xor edx, edx
	mov ebx, LENGTHOF arr
	div ebx
	mov ebx, edx	; move remainder out of edx

	; Format mean to specification
	call writeDec
	mov edx, OFFSET space
	call writeString
	mov eax, ebx
	call writeDec
	mov edx, OFFSET slash
	call writeString
	mov eax, LENGTHOF arr
	call writeDec
	mov edx, OFFSET newline
	call writeString

	; Display original array
	mov edx, OFFSET arrayString
	call writeString

	; loop prep
	mov edi, OFFSET arr
	mov ecx, LENGTHOF arr
	mov edx, OFFSET space

OGARRAY:
	mov eax, [edi]
	call writeDec
	call writeString
	add edi, TYPE arr
	loop OGARRAY

	mov edx, OFFSET newline
	call writeString


	; Everything past this point is horribly confusing
	; I have left a slightly less confusing c++ equivilent at the bottom.
	; I am so sorry

	; Rotated Arrays
	mov ecx, LENGTHOF arr
	sub ecx, 1

ROTATE:
	mov edi, OFFSET arr
	mov edx, [edi + (LENGTHOF arr - 1) * TYPE arr]	; store the last element
	mov ebx, ecx									; store current iterator
	mov ecx, LENGTHOF arr - 1
	sub ecx, swapStart
	
SWAP:
	; Swap each element
	mov eax, swapStart
	add eax, ecx
	sub eax, 1
	mov esi, [edi + eax * TYPE arr]
	add eax, 1
	mov [edi + eax * TYPE arr], esi
	loop SWAP

	; Move the last element to index startSwap
	mov eax, swapStart
	mov [edi + eax * TYPE arr], edx

	mov ecx, LENGTHOF arr
	mov edx, OFFSET rotationString
	call writeString

PRINT:
	mov eax, [edi]
	call writeDec
	mov edx, OFFSET space
	call writeString
	add edi, TYPE arr
	loop PRINT

	mov edx, OFFSET newline
	call writeString

	mov ecx, ebx	; return original iterator

	mov ebx, swapStart	; incriment swapStart
	inc ebx
	mov swapStart, ebx

	loop ROTATE
	

main endp
end main

COMMENT @
THIS IS THE C++ EQUIVILENT TO THE ROTATION ALGORITHM
IM SO SORRY

int swapStart = 0
int temp

for (int i = arr.size() - 1; i > 0; i--) {
	temp = arr[arr.size() - 1]
	for (int j = arr.size() - swapStart - 1; i > 0; i--) {
		arr[j+swapStart] = arr[j-1 + swapStart]
	}
	arr[swapStart] = temp
}
@

COMMENT @
TEST RUN

Please enter a number: 2
Please enter a number: 3
Please enter a number: 4
Please enter a number: 5
Please enter a number: 6
Please enter a number: 7
Please enter a number: 8
Please enter a number: 9
Please enter a number: 0
Please enter a number: 10
The sum is: 54
The mean is: 5 4/10
The original array: 2 3 4 5 6 7 8 9 0 10
After a rotation: 10 2 3 4 5 6 7 8 9 0
After a rotation: 10 0 2 3 4 5 6 7 8 9
After a rotation: 10 0 9 2 3 4 5 6 7 8
After a rotation: 10 0 9 8 2 3 4 5 6 7
After a rotation: 10 0 9 8 7 2 3 4 5 6
After a rotation: 10 0 9 8 7 6 2 3 4 5
After a rotation: 10 0 9 8 7 6 5 2 3 4
After a rotation: 10 0 9 8 7 6 5 4 2 3
After a rotation: 10 0 9 8 7 6 5 4 3 2

@