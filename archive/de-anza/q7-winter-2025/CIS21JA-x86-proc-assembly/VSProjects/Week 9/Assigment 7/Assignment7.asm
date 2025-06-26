


Title Assignment 7

COMMENT !
This program takes up to 40 unsigned dwords from the user, prints them, sorts them in decending order, and prints that.
date:
2025-03-05
!

include irvine32.inc
; ===============================================
.data
  
arr DWORD 40 dup(?)
len DWORD ?
prompt1 BYTE "Enter up to 40 unsigned dword integers. To end the array, enter 0.", 0Ah, 0
prompt2 BYTE "After each element press enter:", 0Ah, 0
init BYTE "Initial Array:", 0Ah, 0
sorted BYTE "Array sorted in descending order:", 0Ah, 0

;=================================================
.code
main proc

	mov edx, offset prompt1
	call writeString
	mov edx, offset prompt2
	call writeString

	push offset arr
	call enter_elem
	pop len

	mov edx, offset init
	call writeString

	push len
	push offset arr
	call print_arr

	push len
	push offset arr
	call sort_arr

	call crlf
	mov edx, offset sorted
	call writeString

	push len
	push offset arr
	call print_arr

	
	exit
main endp

; ================================================
; int enter_elem(arr_addr)
;
; Input:
;   ARR_ADDRESS THROUGH THE STACK
; Output:
;   ARR_LENGTH THROUGH THE STACK
; Operation:
;   Fill the array and count the number of elements
;
enter_elem proc
	
    push ebp
	mov ebp, esp

	mov esi, [ebp + 8]
	mov ecx, 40
	xor ebx, ebx

input_loop:
	call readDec

	cmp eax, 0
	je input_loop_break

	mov [esi + ebx * 4], eax
	inc ebx
	cmp ebx, 40
	je input_loop_break

	jmp input_loop

input_loop_break:
	pop ebp
	mov [esp + 12], ebx
	ret 8

enter_elem endp

; ================================================
; void print_arr(arr_addr,arr_len)
;
; Input:
;   ?
; Output:
;   ?
; Operation:
;  print out the array
;

print_arr proc

  push ebp
  mov ebp, esp

  mov esi, [ebp + 8]
  mov ecx, [ebp + 12]

print_loop:
	mov ebx, [ebp + 12]
	sub ebx, ecx
	mov eax, [esi + ebx * 4]
	call writeDec
	mov al, ' '
	call writeChar
	loop print_loop

	pop ebp
	ret 12

print_arr endp

; ================================================
; void sort_arr(arr_addr,arr_len)
;
; Input:
;   ?
; Output:
;   ?
; Operation:
;   sort the array
;

sort_arr proc

    push ebp
    mov ebp, esp
    pushad

    mov esi, [ebp + 8]  
    mov ecx, [ebp + 12] 
    dec ecx
    mov ebx, ecx

outer_loop:
    mov ecx, ebx
    mov edi, esi

bubble_loop:
    mov eax, [edi]     
    mov edx, [edi + 4]  
    cmp eax, edx
    jge skip_swap     

    ; Swap the elements
    mov [edi], edx
    mov [edi + 4], eax

skip_swap:
    add edi, 4        
    loop bubble_loop

    dec ebx            
    jnz outer_loop

    popad
    pop ebp
    ret 12

sort_arr endp

; ===============================================
; void compare_and_swap(x_addr,y_addr)
;
; Input:
;   ?
; Output:
;   ?
; Operation:
;  compare and call SWAP ONLY IF Y < X 
;

compare_and_swap proc

   push ebp
   mov ebp, esp
   pushad

   mov eax, [ebp + 8]
   mov ebx, [ebp + 12]

   mov edx, [eax]
   cmp edx, [ebx]
   jae skip_swap

   push ecx
   push edx
   call swap

skip_swap:
	popad
	pop ebp
	ret 12

compare_and_swap endp

; =================================================
; void swap(x_addr,y_addr)
;
; Input:
;   ?
; Output:
;   ?
; Operation:
;  swap the two inputs
;

swap proc

   push ebp
   mov ebp, esp
   pushad

   mov eax, [ebp + 8]
   mov ebx, [ebp + 12]

   ; swap
   xor eax, ebx
   xor ebx, eax
   xor eax, ebx

   popad
   pop ebp
   ret 12

swap endp

end main

; SAMPLE RUN
;Enter up to 40 unsigned dword integers. To end the array, enter 0.
;After each element press enter:
;3
;5
;1
;3
;5
;0
;Initial Array:
;3 5 1 3 5
;Array sorted in descending order:
;5 5 3 3 1