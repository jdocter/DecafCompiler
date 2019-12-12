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
	.string "answer for a:%d\n"
	.align 16
_string_1:
	.string "answer for b:%d\n"
	.align 16
_sp_field_runtime_error_1:
	.string "*** RUNTIME ERROR ***: Array index out of bounds\n"
	.align 16

_sp_field_runtime_error_2:
	.string "*** RUNTIME ERROR ***: Control fell off of a non-void method\n"
	.align 16


	.globl main
main:
	enter $(240), $0
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
	movq $0, -152(%rbp)
	movq $0, -160(%rbp)
	movq $0, -168(%rbp)
	movq $0, -176(%rbp)
	movq $0, -184(%rbp)
	movq $0, -192(%rbp)
	movq $0, -200(%rbp)
	movq $0, -208(%rbp)
	movq $0, -216(%rbp)
	movq $0, -224(%rbp)
	movq $0, -232(%rbp)
	movq $0, -240(%rbp)
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
_block_11:
_block_162:
	movq $1, -40(%rbp) # t18 = 1
	movq $8, -48(%rbp) # t22 = 8
	movq $7, -56(%rbp) # t26 = 7
	movq -56(%rbp), %rax # t25 = -t26
	negq %rax # -t26
	movq %rax, -64(%rbp)
	# t21 = t22 * t25 {canonical: (8) * (MINUS[7])}
	movq -48(%rbp), %rax
	imulq -64(%rbp), %rax
	movq %rax, -72(%rbp)
	# t17 = t18 + t21 {canonical: (1) + ((8) * (MINUS[7]))}
	movq -40(%rbp), %rax
	addq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $6, -88(%rbp) # t48 = 6
	movq $7, -96(%rbp) # t51 = 7
	# t47 = t48 * t51 {canonical: (6) * (7)}
	movq -88(%rbp), %rax
	imulq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $3, -112(%rbp) # t61 = 3
	# t46 = t47 * t61 {canonical: ((6) * (7)) * (3)}
	movq -104(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $200, -128(%rbp) # t71 = 200
	# t45 = t46 - t71 {canonical: (((6) * (7)) * (3)) - (200)}
	movq -120(%rbp), %rax
	subq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t16 = t17 + t45 {canonical: ((1) + ((8) * (MINUS[7]))) + ((((6) * (7)) * (3)) - (200))}
	movq -80(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $9, -152(%rbp) # t88 = 9
	# a_s2 = t16 + t88 {canonical: (((1) + ((8) * (MINUS[7]))) + ((((6) * (7)) * (3)) - (200))) + (9)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -24(%rbp)
	movq -24(%rbp), %rax # t99 = a_s2 {canonical: a_s2}
	movq %rax, -160(%rbp)
	# printf_s0["answer for a:%d\n", t99]
	movq -160(%rbp), %rsi
	leaq _string_0(%rip), %rdi
	call printf
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq $3, -168(%rbp) # t109 = 3
	movq -168(%rbp), %rax # t108 = -t109
	negq %rax # -t109
	movq %rax, -176(%rbp)
	movq $5, -184(%rbp) # t117 = 5
	movq $6, -192(%rbp) # t120 = 6
	# t116 = t117 * t120 {canonical: (5) * (6)}
	movq -184(%rbp), %rax
	imulq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	# t114 = a_s2 / t116 {canonical: (a_s2) / ((5) * (6))}
	movq -24(%rbp), %rax
	cqto
	idivq -200(%rbp)
	movq %rax, -208(%rbp)
	# t107 = t108 + t114 {canonical: (MINUS[3]) + ((a_s2) / ((5) * (6)))}
	movq -176(%rbp), %rax
	addq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	movq $7, -224(%rbp) # t144 = 7
	# t106 = t107 * t144 {canonical: ((MINUS[3]) + ((a_s2) / ((5) * (6)))) * (7)}
	movq -216(%rbp), %rax
	imulq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	movq -232(%rbp), %rax # b_s2 -= t106
	subq %rax, -32(%rbp)
	movq -32(%rbp), %rax
	movq -32(%rbp), %rax # t157 = b_s2 {canonical: b_s2}
	movq %rax, -240(%rbp)
	# printf_s0["answer for b:%d\n", t157]
	movq -240(%rbp), %rsi
	leaq _string_1(%rip), %rdi
	call printf
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	jmp _nop_163
_nop_163:
	jmp _end_of_block_11
_end_of_block_11:
	jmp _return_12
_return_12:
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
