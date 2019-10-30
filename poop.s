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
	
.comm _global_A, 800, 16
.comm _global_y, 8, 16
_sp_field_runtime_error_1:
	.string "*** RUNTIME ERROR ***: Array index out of bounds"
	.align 16

_sp_field_runtime_error_2:
	.string "*** RUNTIME ERROR ***: Control fell off of a non-void method"
	.align 16


_method_clobbery:
	enter $(32), $0
	movq $0, -8(%rbp)
	movq $0, -16(%rbp)
	movq $0, -24(%rbp)
_nop_7:
	jmp _block_8
_block_8:
_nop_10:
	jmp _block_24
_block_24:
	movq _global_y(%rip), %rax # t14 = y
	movq %rax, -8(%rbp)
	movq $1, -16(%rbp) # t15 = 1
	# t13 = t14 + t15
	movq -8(%rbp), %rax
	addq -16(%rbp), %rax
	movq %rax, -24(%rbp)
	movq -24(%rbp), %rdx # y = t13
	movq %rdx, _global_y(%rip)
	jmp _nop_25
_nop_25:
	jmp _end_of_block_8
_end_of_block_8:
	jmp _return_9
_return_9:
	# return void
	movq $0, %rax
	
	leave
	ret

	.globl main
main:
	enter $(80), $0
	movq $0, -8(%rbp)
	movq $0, -16(%rbp)
	movq $0, -24(%rbp)
	movq $0, -32(%rbp)
	movq $0, -40(%rbp)
	movq $0, -48(%rbp)
	movq $0, -56(%rbp)
	movq $0, -64(%rbp)
	movq $0, -72(%rbp)
_nop_28:
	jmp _block_30
_block_30:
_nop_42:
	jmp _block_48
_block_48:
	movq $0, -32(%rbp) # t45 = 0
	movq -32(%rbp), %rdx # i = t45
	movq %rdx, -16(%rbp)
	jmp _nop_49
_nop_49:
	jmp _end_of_block_30
_end_of_block_30:
	jmp _conditional_40
_conditional_40:
_block_54:
	movq -16(%rbp), %rax # t51 = i
	movq %rax, -32(%rbp)
	jmp _block_59
_block_59:
	movq -16(%rbp), %rax # t51 = i
	movq %rax, -32(%rbp)
	movq $30, -40(%rbp) # t52 = 30
	# t50 = t51 < t52
	movq -32(%rbp), %rax
	cmpq -40(%rbp), %rax
	setl %cl
	movzbq %cl, %rcx
	movq %rcx, -48(%rbp)
	jmp _nop_61
_nop_61:
	jmp _end_of_conditional_40
_end_of_conditional_40:
	cmpq $1, -48(%rbp) # true = t50
	jne _return_41 # ifFalse
	jmp _block_34 # ifTrue
_block_34:
_nop_62:
	jmp _block_68
_block_68:
	movq $0, -32(%rbp) # t65 = 0
	movq -32(%rbp), %rdx # j = t65
	movq %rdx, -24(%rbp)
	jmp _nop_69
_nop_69:
	jmp _end_of_block_34
_end_of_block_34:
	jmp _conditional_39
_conditional_39:
_block_74:
	movq -24(%rbp), %rax # t71 = j
	movq %rax, -32(%rbp)
	jmp _block_87
_block_87:
	movq -24(%rbp), %rax # t71 = j
	movq %rax, -32(%rbp)
	movq _global_y(%rip), %rax # t75 = y
	movq %rax, -40(%rbp)
	movq $1, -48(%rbp) # t76 = 1
	# t72 = t75 + t76
	movq -40(%rbp), %rax
	addq -48(%rbp), %rax
	movq %rax, -56(%rbp)
	# t70 = t71 < t72
	movq -32(%rbp), %rax
	cmpq -56(%rbp), %rax
	setl %cl
	movzbq %cl, %rcx
	movq %rcx, -64(%rbp)
	jmp _nop_89
_nop_89:
	jmp _end_of_conditional_39
_end_of_conditional_39:
	cmpq $1, -64(%rbp) # true = t70
	jne _block_31 # ifFalse
	jmp _block_38 # ifTrue
_block_38:
_nop_90:
	jmp _block_116
_block_116:
	movq $2, -32(%rbp) # t95 = 2
	movq -16(%rbp), %rax # t96 = i
	movq %rax, -40(%rbp)
	# t93 = t95 * t96
	movq -32(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -48(%rbp)
	movq -24(%rbp), %rax # t94 = j
	movq %rax, -56(%rbp)
	# t92 = t93 + t94
	movq -48(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $1, -72(%rbp) # t110 = 1
	movq -72(%rbp), %rdx # A[t92] = t110
	cmpq $100, %rax
	jle _sp_method_exit_with_status_1
	cmpq $0, %rax
	jg _sp_method_exit_with_status_1
	movq -64(%rbp), %rax
	leaq 0(,%rax,8), %rcx
	leaq _global_A, %rax
	movq %rdx, (%rcx,%rax)
	# clobbery[]
	call _method_clobbery
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	jmp _nop_117
_nop_117:
	jmp _end_of_block_38
_end_of_block_38:
	jmp _block_35
_block_35:
_nop_118:
	jmp _block_124
_block_124:
	movq $1, -32(%rbp) # t121 = 1
	movq -32(%rbp), %rdx # j += t121
	addq %rdx, -24(%rbp)
	jmp _nop_125
_nop_125:
	jmp _end_of_block_35
_end_of_block_35:
	jmp _conditional_39
_block_31:
_nop_126:
	jmp _block_132
_block_132:
	movq $1, -32(%rbp) # t129 = 1
	movq -32(%rbp), %rdx # i += t129
	addq %rdx, -16(%rbp)
	jmp _nop_133
_nop_133:
	jmp _end_of_block_31
_end_of_block_31:
	jmp _conditional_40
_return_41:
	# return void
	movq $0, %rax
	
	leave
	ret
