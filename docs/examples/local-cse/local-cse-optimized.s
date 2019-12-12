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
	.string "%d\n"
	.align 16
_string_1:
	.string "%d\n"
	.align 16
_sp_field_runtime_error_1:
	.string "*** RUNTIME ERROR ***: Array index out of bounds\n"
	.align 16

_sp_field_runtime_error_2:
	.string "*** RUNTIME ERROR ***: Control fell off of a non-void method\n"
	.align 16


	.globl main
main:
	enter $(144), $0
	movq $0, -8(%rbp)
	movq $0, -16(%rbp)
	movq $0, -24(%rbp)
	movq $0, -32(%rbp)
	movq $0, -40(%rbp)
	movq $0, -48(%rbp)
	movq $0, -56(%rbp)
	movq $0, -64(%rbp)
	movq $0, -72(%rbp)
	movq $0, -80(%rbp)
	movq $0, -88(%rbp)
	movq $0, -96(%rbp)
	movq $0, -104(%rbp)
	movq $0, -112(%rbp)
	movq $0, -120(%rbp)
	movq $0, -128(%rbp)
	movq $0, -136(%rbp)
	movq $0, -144(%rbp)
	# save callee-save registers
	pushq %rbx
	movq $0, %rbx
	pushq %r12
	movq $0, %r12
	pushq %r13
	movq $0, %r13
	pushq %r14
	movq $0, %r14
	pushq %r15
	movq $0, %r15
	pushq %r15 # Dummy push to maintain 16-alignment
_block_20:
_block_117:
	movq $2, -72(%rbp) # t26 = 2
	# get_int_s0[t26]
	movq -72(%rbp), %rdi
	call _method_get_int
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	
	movq %rax, -40(%rbp) # a_s4 = load %rax
	
	movq $3, -80(%rbp) # t35 = 3
	# get_int_s0[t35]
	movq -80(%rbp), %rdi
	call _method_get_int
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	
	movq %rax, -48(%rbp) # b_s4 = load %rax
	
	movq $0, -56(%rbp) # c_s4 = 0
	movq $0, -64(%rbp) # d_s4 = 0
	# t129, t49 = a_s4 + b_s4 {canonical: (a_s4) + (b_s4)}
	movq -40(%rbp), %rax
	addq -48(%rbp), %rax
	movq %rax, -88(%rbp)
	movq %rax, -96(%rbp) # t129 = (a_s4) + (b_s4)
	movq -96(%rbp), %rax # t129, t59 = t129 {canonical: (a_s4) + (b_s4)}
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax
	movq %rax, -96(%rbp) # t129 = (a_s4) + (b_s4)
	# t130, c_s4 = t49 * t59 {canonical: ((a_s4) + (b_s4)) * ((a_s4) + (b_s4))}
	movq -88(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -56(%rbp)
	movq %rax, -112(%rbp) # t130 = ((a_s4) + (b_s4)) * ((a_s4) + (b_s4))
	movq -96(%rbp), %rax # t129, t77 = t129 {canonical: (a_s4) + (b_s4)}
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rax
	movq %rax, -96(%rbp) # t129 = (a_s4) + (b_s4)
	movq -96(%rbp), %rax # t87 = t129 {canonical: (a_s4) + (b_s4)}
	movq %rax, -128(%rbp)
	movq -128(%rbp), %rax
	movq -112(%rbp), %rax # d_s4 = t130 {canonical: ((a_s4) + (b_s4)) * ((a_s4) + (b_s4))}
	movq %rax, -64(%rbp)
	movq -64(%rbp), %rax
	movq -56(%rbp), %rax # t105 = c_s4 {canonical: c_s4}
	movq %rax, -136(%rbp)
	# printf_s0["%d\n", t105]
	movq -136(%rbp), %rsi
	leaq _string_0(%rip), %rdi
	call printf
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq -64(%rbp), %rax # t112 = d_s4 {canonical: d_s4}
	movq %rax, -144(%rbp)
	# printf_s0["%d\n", t112]
	movq -144(%rbp), %rsi
	leaq _string_1(%rip), %rdi
	call printf
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	jmp _nop_118
_nop_118:
	jmp _end_of_block_20
_end_of_block_20:
	jmp _return_21
_return_21:
	# return void
	movq $0, %rax
	
	# restore callee-saved registers
	popq %r15 # dummy pop to maintain 16-alignment
	popq %r15
	popq %r14
	popq %r13
	popq %r12
	popq %rbx
	leave
	ret

_method_get_int:
	enter $(16), $0
	movq $0, -8(%rbp)
	movq $0, -16(%rbp)
	# save callee-save registers
	pushq %rbx
	movq $0, %rbx
	pushq %r12
	movq $0, %r12
	pushq %r13
	movq $0, %r13
	pushq %r14
	movq $0, %r14
	pushq %r15
	movq $0, %r15
	pushq %r15 # Dummy push to maintain 16-alignment
	movq %rdi, -8(%rbp)
_return_122:
	# calculating return Expr
_block_126:
	movq -8(%rbp), %rax # t123 = x_s1 {canonical: x_s1}
	movq %rax, -16(%rbp)
	jmp _nop_128
_nop_128:
	jmp _end_of_return_122
_end_of_return_122:
	
	movq -16(%rbp), %rax
	
	# restore callee-saved registers
	popq %r15 # dummy pop to maintain 16-alignment
	popq %r15
	popq %r14
	popq %r13
	popq %r12
	popq %rbx
	leave
	ret
