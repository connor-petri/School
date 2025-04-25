; Connor Petri
; CIS21JA
; 2025-02-12
; This program takes 2 signed integers from the user, calculates 
; their sum and difference, and displays them to the user. 
; This process repeats 3 times with the text appearing in 
; the middle and the screen clearing between runs.

include irvine32.inc

.data
center_y_cor BYTE 8
center_x_cor BYTE 35

inputMessage BYTE "Please enter an integer: ",0
sumMessage BYTE "The sum of two integers: ",0
diffMessage BYTE "The difference of two integers: ",0
waitMessage BYTE "Press any key...",0

.code
main proc
	mov ecx, 3
loop_start:
	call ClrScr

	call Input
	push eax
	call Input
	push eax

	call DisplaySum
	call DisplayDiff
	call WaitForKey
	loop loop_start
	
	exit

main endp

;;;;;;;;;;;;;;;;;;;;;;;;;;

; No Input
; No Return
Locate proc
	mov dh, center_y_cor
	mov dl, center_x_cor
	inc center_y_cor
	call gotoxy
	ret
Locate endp

;;;;;;;;;;;;;;;;;;;;;;;;;;

; No Input
; Returns signed dword in eax
Input proc 
	call Locate
	mov edx, offset inputMessage
	call WriteString
	call ReadInt
	ret
Input endp

;;;;;;;;;;;;;;;;;;;;;;;;;;

; No Input
; No Return
WaitForKey proc
	call Locate
	mov edx, offset waitMessage
	call WriteString
	call ReadChar
	ret
WaitForKey endp

;;;;;;;;;;;;;;;;;;;;;;;;;;

; Input: Num1, Num2 pushed to the stack in that order
; Returns: Num1, Num2 pushed to the stack in that order 
; so this can be called in sequence
DisplaySum proc
	call Locate

	; unpack input from stack
	pop esi
	pop ebx
	pop eax
	

	mov edx, offset sumMessage
	call WriteString

	mov edx, eax
	add eax, ebx
	call WriteInt
	
	; return values to stack
	push edx
	push ebx
	push esi
	ret
DisplaySum endp

;;;;;;;;;;;;;;;;;;;;;;;;;;

; Input: Num1, Num2 pushed to the stack in that order
; Returns: Num1, Num2 pushed to the stack in that order 
; so this can be called in sequence
DisplayDiff proc
	call Locate

	; unpack inputs from stack
	pop esi
	pop ebx
	pop eax

	mov edx, offset sumMessage
	call WriteString
	
	mov edx, eax
	sub eax, ebx
	call WriteInt
	
	; return inputs to stack
	push edx
	push ebx
	push esi
	ret
DisplayDiff endp

end main

; SAMPLE RUN

; Please enter an integer: 25
; Please enter an integer: -25
; The sum of two integers: +0                                                                                             
; The sum of two integers: +50                                                                                            
; Press any key...     

; Please enter an integer: 100
; Please enter an integer: -50
; The sum of two integers: +50                                                                                            
; The sum of two integers: +150                                                                                            
; Press any key...

; Please enter an integer: +2
; Please enter an integer: -4
; The sum of two integers: -2                                                                                             
; The sum of two integers: +6                                                                                            
; Press any key...