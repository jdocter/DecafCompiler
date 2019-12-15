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
	enter $(112), $0
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
_block_19:
_block_70:
	movq $2, -88(%rbp) # t46 = 2
	# get_int_s0[t46]
	movq -88(%rbp), %rdi
	call _method_get_int
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	
	movq %rax, -48(%rbp) # a_s4 = load %rax
	
	movq $3, -96(%rbp) # t55 = 3
	# get_int_s0[t55]
	movq -96(%rbp), %rdi
	call _method_get_int
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	
	movq %rax, -56(%rbp) # b_s4 = load %rax
	
	movq $0, -64(%rbp) # c_s4 = 0
	movq $0, -72(%rbp) # d_s4 = 0
	movq $0, -80(%rbp) # e_s4 = 0
	jmp _nop_71
_nop_71:
	jmp _end_of_block_19
_end_of_block_19:
	jmp _conditional_26
_conditional_26:
_block_74:
	movq $1, -88(%rbp) # t72 = true
	jmp _nop_76
_nop_76:
	jmp _end_of_conditional_26
_end_of_conditional_26:
	cmpq $1, -88(%rbp) # true = t72
	jne _block_25 # ifFalse
	jmp _block_22 # ifTrue
_block_22:
_block_106:
	# t80 = a_s4 + b_s4 {canonical: (a_s4) + (b_s4)}
	movq -48(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -88(%rbp)
	# t90 = a_s4 + b_s4 {canonical: (a_s4) + (b_s4)}
	movq -48(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -96(%rbp)
	# c_s4 = t80 * t90 {canonical: ((a_s4) + (b_s4)) * ((a_s4) + (b_s4))}
	movq -88(%rbp), %rax
	imulq -96(%rbp), %rax
	movq %rax, -64(%rbp)
	jmp _nop_107
_nop_107:
	jmp _end_of_block_22
_end_of_block_22:
	jmp _block_29
_block_29:
_block_140:
	# t111 = a_s4 + b_s4 {canonical: (a_s4) + (b_s4)}
	movq -48(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -88(%rbp)
	# t121 = a_s4 + b_s4 {canonical: (a_s4) + (b_s4)}
	movq -48(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -96(%rbp)
	# d_s4 = t111 * t121 {canonical: ((a_s4) + (b_s4)) * ((a_s4) + (b_s4))}
	movq -88(%rbp), %rax
	imulq -96(%rbp), %rax
	movq %rax, -72(%rbp)
	movq $0, -48(%rbp) # a_s4 = 0
	jmp _nop_141
_nop_141:
	jmp _end_of_block_29
_end_of_block_29:
	jmp _conditional_38
_conditional_38:
_block_151:
	# t142 = a_s4 < b_s4 {canonical: (a_s4) < (b_s4)}
	movq -48(%rbp), %rax # t142 = a_s4 < b_s4 {canonical: (a_s4) < (b_s4)}
	cmpq -56(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_153
_nop_153:
	jmp _end_of_conditional_38
_end_of_conditional_38:
	cmpq $1, -88(%rbp) # true = t142
	jne _block_40 # ifFalse
	jmp _block_33 # ifTrue
_block_33:
_block_189:
	# t157 = c_s4 + b_s4 {canonical: (c_s4) + (b_s4)}
	movq -64(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -88(%rbp)
	# t167 = c_s4 + b_s4 {canonical: (c_s4) + (b_s4)}
	movq -64(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -96(%rbp)
	# e_s4 = t157 * t167 {canonical: ((c_s4) + (b_s4)) * ((c_s4) + (b_s4))}
	movq -88(%rbp), %rax
	imulq -96(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $1, -104(%rbp) # t185 = 1
	movq -104(%rbp), %rax # c_s4 += t185
	addq %rax, -64(%rbp)
	movq -64(%rbp), %rax
	jmp _nop_190
_nop_190:
	jmp _end_of_block_33
_end_of_block_33:
	jmp _conditional_37
_conditional_37:
_block_202:
	movq $3, -88(%rbp) # t193 = 3
	# t191 = e_s4 == t193 {canonical: (e_s4) == (3)}
	movq -80(%rbp), %rax # t191 = e_s4 == t193 {canonical: (e_s4) == (3)}
	cmpq -88(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -96(%rbp)
	jmp _nop_204
_nop_204:
	jmp _end_of_conditional_37
_end_of_conditional_37:
	cmpq $1, -96(%rbp) # true = t191
	jne _block_30 # ifFalse
	jmp _return_36 # ifTrue
_return_36:
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
_block_30:
_block_210:
	incq -48(%rbp) # a_s4 ++
	movq -48(%rbp), %rax
	jmp _nop_211
_nop_211:
	jmp _end_of_block_30
_end_of_block_30:
	jmp _conditional_38
_block_40:
_block_227:
	movq -64(%rbp), %rax # t215 = c_s4 {canonical: c_s4}
	movq %rax, -88(%rbp)
	# printf_s0["%d\n", t215]
	movq -88(%rbp), %rsi
	leaq _string_0(%rip), %rdi
	call printf
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq -72(%rbp), %rax # t222 = d_s4 {canonical: d_s4}
	movq %rax, -96(%rbp)
	# printf_s0["%d\n", t222]
	movq -96(%rbp), %rsi
	leaq _string_1(%rip), %rdi
	call printf
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	jmp _nop_228
_nop_228:
	jmp _end_of_block_40
_end_of_block_40:
	jmp _return_41
_return_41:
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
_block_25:
_block_261:
	# t232 = a_s4 + b_s4 {canonical: (a_s4) + (b_s4)}
	movq -48(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -88(%rbp)
	# t242 = a_s4 + b_s4 {canonical: (a_s4) + (b_s4)}
	movq -48(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -96(%rbp)
	# e_s4 = t232 * t242 {canonical: ((a_s4) + (b_s4)) * ((a_s4) + (b_s4))}
	movq -88(%rbp), %rax
	imulq -96(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $1, -48(%rbp) # a_s4 = 1
	jmp _nop_262
_nop_262:
	jmp _end_of_block_25
_end_of_block_25:
	jmp _block_29

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
_return_266:
	# calculating return Expr
_block_270:
	movq -8(%rbp), %rax # t267 = x_s1 {canonical: x_s1}
	movq %rax, -16(%rbp)
	jmp _nop_272
_nop_272:
	jmp _end_of_return_266
_end_of_return_266:
	
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
