.comm _sp_exit_code, 8, 16
_sp_method_premature_return:
	leave
	ret

_sp_method_exit_with_status_1:
	# arraybounds check fail
	leaq _sp_field_runtime_error_1(%rip), %rdi
	call printf
	
	movq $1, _sp_exit_code(%rip)
	movq $1, %rax
	leave
	ret
	
_sp_method_exit_with_status_2:
	# returning void in a function supposed to return a value
	leaq _sp_field_runtime_error_2(%rip), %rdi
	call printf
	
	movq $2, _sp_exit_code(%rip)
	movq $2, %rax
	leave
	ret
	
_string_0:
	.string "%d + %d = %d (15)\n"
	.align 16
.comm _global_a, 8, 16
.comm _global_b, 8, 16
.comm _global_c, 8, 16
_sp_field_runtime_error_1:
	.string "*** RUNTIME ERROR ***: Array index out of bounds\n"
	.align 16

_sp_field_runtime_error_2:
	.string "*** RUNTIME ERROR ***: Control fell off of a non-void method\n"
	.align 16


	.globl main
main:
	enter $(64), $0
	movq $0, -8(%rbp)
	movq $0, -16(%rbp)
	movq $0, -24(%rbp)
	movq $0, -32(%rbp)
	movq $0, -40(%rbp)
	movq $0, -48(%rbp)
	movq $0, -56(%rbp)
	movq $0, -64(%rbp)
_nop_5:
	jmp _block_9
_block_9:
_nop_11:
	jmp _block_58
_block_58:
	movq $10, -8(%rbp) # t14 = 10
	movq -8(%rbp), %rax
	movq %rax, _global_a(%rip)
	movq $5, -16(%rbp) # t20 = 5
	movq -16(%rbp), %rax
	movq %rax, _global_b(%rip)
	movq _global_a(%rip), rax
	movq %rax, -24(%rbp)
	movq _global_b(%rip), rax
	movq %rax, -32(%rbp)
	# t26 = t27 + t28
	movq -24(%rbp), %rax
	addq -32(%rbp), %rax
	movq %rax, -40(%rbp)
	movq -40(%rbp), %rax
	movq %rax, _global_c(%rip)
	movq _global_a(%rip), rax
	movq %rax, -48(%rbp)
	movq _global_b(%rip), rax
	movq %rax, -56(%rbp)
	movq _global_c(%rip), rax
	movq %rax, -64(%rbp)
	# printf["%d + %d = %d (15)\n", t45, t49, t53]
	movq -64(%rbp), %rcx
	movq -56(%rbp), %rdx
	movq -48(%rbp), %rsi
	leaq _string_0(%rip), %rdi
	call printf
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	jmp _nop_59
_nop_59:
	jmp _end_of_block_9
_end_of_block_9:
	jmp _return_10
_return_10:
	# return void
	movq $0, %rax
	
	leave
	ret
