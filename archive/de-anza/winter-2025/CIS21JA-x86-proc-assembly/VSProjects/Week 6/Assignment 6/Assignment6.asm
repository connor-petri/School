; Connor Petri
; CIS21JA
; 2025 02 19
; This program takes an unsigned integer from the user and displays every calculated prime number between 0 and the entered number.

include irvine32.inc

.data
userNum dword ?
prompt byte "Please enter a number: ", 0
primesFound byte "Primes found until the given number: ", 0


.code
main proc
	mov edx, offset prompt
	call writeString
	call readDec
	mov userNum, eax

	mov edx, offset primesFound
	call writeString

	mov ecx, eax
	dec ecx

; for(int i = 0; i < userNum; i++) {
;	if (isPrime(i)) {
;		std::cout << i << " ";
;	}
; }
mainLoop:
	mov ebx, userNum
	sub ebx, ecx
	push ecx
	call isPrime
	pop ecx
	cmp edx, 0
	je mainLoopOut

	mov eax, ebx
	call writeDec

	mov al, 20h
	call writeChar
	
	jmp mainLoopOut

mainLoopOut:
	loop mainLoop

	exit
main endp

; Takes the number being compared as input in ebx register
; Returns 0 or 1 for true or false in edx
isPrime proc
	; if (ebx <= 1) { return false; }
	cmp ebx, 1
	jbe notPrime

	; if (ebx == 2) { return true; }
	cmp ebx, 2
	je prime

	mov ecx, ebx
	dec ecx

; for(int j = 3; j < i; j++) {
;	if (ebx % j == 0) { return false; }
; }
; return true;
isPrimeLoop:
	xor edx, edx
	mov eax, ebx
	div ecx
	cmp edx, 0
	je notPrime
	
	dec ecx
	cmp ecx, 1
	jg isPrimeLoop

prime:
	mov edx, 1		; if this instruction is reached then the number is prime and 1 is pushed to the stack
	jmp isPrimeOut

notPrime:
	xor edx, edx	; sets edx to 0 if not prime
	jmp isPrimeOut

isPrimeOut:		
	ret

isPrime endp

end main

; TEST RUN
; Please enter a number: 50
; Primes found until the given number: 2 3 5 7 11 13 17 19 23 29 31 37 41 43 47