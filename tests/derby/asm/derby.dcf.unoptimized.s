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
	.string "data/input.ppm"
	.align 16
_string_1:
	.string "output/output.ppm"
	.align 16
.comm _global_image, 18400000, 16
.comm _global_kernel_sum, 8, 16
.comm _global_unsharpKernel, 72, 16
.comm _global_rows, 8, 16
.comm _global_unsharpMask, 6000000, 16
.comm _global_cols, 8, 16
_sp_field_runtime_error_1:
	.string "*** RUNTIME ERROR ***: Array index out of bounds\n"
	.align 16

_sp_field_runtime_error_2:
	.string "*** RUNTIME ERROR ***: Control fell off of a non-void method\n"
	.align 16


_method_sharpenH:
	enter $(192), $0
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
	movq %rdi, -16(%rbp)
	movq %rsi, -32(%rbp)
_block_102:
_block_121:
	movq $0, -48(%rbp) # c_s75 {dead: []} = 0
	jmp _nop_122
_nop_122:
	jmp _end_of_block_102
_end_of_block_102:
	jmp _conditional_115
_conditional_115:
_block_132:
	# t123 {dead: []} = c_s75 < cols_s0 {canonical: (c_s75) < (cols_s0)}
	movq -48(%rbp), %rax # t123 {dead: []} = c_s75 < cols_s0 {canonical: (c_s75) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -56(%rbp)
	jmp _nop_134
_nop_134:
	jmp _end_of_conditional_115
_end_of_conditional_115:
	cmpq $1, -56(%rbp) # true = t123
	jne _return_116 # ifFalse
	jmp _block_106 # ifTrue
_block_106:
_block_139:
	movq $0, -40(%rbp) # r_s75 {dead: []} = 0
	jmp _nop_140
_nop_140:
	jmp _end_of_block_106
_end_of_block_106:
	jmp _conditional_114
_conditional_114:
_block_150:
	# t141 {dead: []} = r_s75 < rows_s0 {canonical: (r_s75) < (rows_s0)}
	movq -40(%rbp), %rax # t141 {dead: []} = r_s75 < rows_s0 {canonical: (r_s75) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -56(%rbp)
	jmp _nop_152
_nop_152:
	jmp _end_of_conditional_114
_end_of_conditional_114:
	cmpq $1, -56(%rbp) # true = t141
	jne _block_103 # ifFalse
	jmp _block_109 # ifTrue
_block_109:
_block_279:
	movq $2193, -56(%rbp) # t158 {dead: []} = 2193
	# t156 {dead: []} = r_s75 * t158 {canonical: (r_s75) * (2193)}
	movq -40(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $3, -72(%rbp) # t170 {dead: []} = 3
	# t168 {dead: []} = c_s75 * t170 {canonical: (c_s75) * (3)}
	movq -48(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	# t155 {dead: []} = t156 + t168 {canonical: ((r_s75) * (2193)) + ((c_s75) * (3))}
	movq -64(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	movq $2193, -96(%rbp) # t192 {dead: []} = 2193
	# t190 {dead: []} = r_s75 * t192 {canonical: (r_s75) * (2193)}
	movq -40(%rbp), %rax
	imulq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $3, -112(%rbp) # t204 {dead: []} = 3
	# t202 {dead: []} = c_s75 * t204 {canonical: (c_s75) * (3)}
	movq -48(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	# t189 {dead: []} = t190 + t202 {canonical: ((r_s75) * (2193)) + ((c_s75) * (3))}
	movq -104(%rbp), %rax
	addq -120(%rbp), %rax
	movq %rax, -128(%rbp)
	movq -128(%rbp), %rax # t188 {dead: []} = image_s0[t189]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t188 {dead: []} = image_s0[t189]
	movq %rdx, -136(%rbp)
	movq $731, -144(%rbp) # t231 {dead: []} = 731
	# t229 {dead: []} = r_s75 * t231 {canonical: (r_s75) * (731)}
	movq -40(%rbp), %rax
	imulq -144(%rbp), %rax
	movq %rax, -152(%rbp)
	# t228 {dead: []} = t229 + c_s75 {canonical: ((r_s75) * (731)) + (c_s75)}
	movq -152(%rbp), %rax
	addq -48(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -160(%rbp), %rax # t227 {dead: []} = unsharpMask_s0[t228]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t227 {dead: []} = unsharpMask_s0[t228]
	movq %rdx, -168(%rbp)
	# t225 {dead: []} = amount_s74 * t227 {canonical: (amount_s74) * (unsharpMask_s0[((r_s75) * (731)) + (c_s75)])}
	movq -16(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	# t223 {dead: []} = channelOne_s74 + t225 {canonical: (channelOne_s74) + ((amount_s74) * (unsharpMask_s0[((r_s75) * (731)) + (c_s75)]))}
	movq -32(%rbp), %rax
	addq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	# t187 {dead: []} = t188 * t223 {canonical: (image_s0[((r_s75) * (2193)) + ((c_s75) * (3))]) * ((channelOne_s74) + ((amount_s74) * (unsharpMask_s0[((r_s75) * (731)) + (c_s75)])))}
	movq -136(%rbp), %rax
	imulq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	movq -88(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	# image_s0[t155] {dead: []} = t187 / channelOne_s74 {canonical: ((image_s0[((r_s75) * (2193)) + ((c_s75) * (3))]) * ((channelOne_s74) + ((amount_s74) * (unsharpMask_s0[((r_s75) * (731)) + (c_s75)])))) / (channelOne_s74)}
	movq -192(%rbp), %rax
	cqto
	idivq -32(%rbp)
	movq %rax, (%rdi)
	jmp _nop_280
_nop_280:
	jmp _end_of_block_109
_end_of_block_109:
	jmp _conditional_113
_conditional_113:
_block_324:
	movq $2193, -56(%rbp) # t286 {dead: []} = 2193
	# t284 {dead: []} = r_s75 * t286 {canonical: (r_s75) * (2193)}
	movq -40(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $3, -72(%rbp) # t298 {dead: []} = 3
	# t296 {dead: []} = c_s75 * t298 {canonical: (c_s75) * (3)}
	movq -48(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	# t283 {dead: []} = t284 + t296 {canonical: ((r_s75) * (2193)) + ((c_s75) * (3))}
	movq -64(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	movq -88(%rbp), %rax # t282 {dead: []} = image_s0[t283]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t282 {dead: []} = image_s0[t283]
	movq %rdx, -96(%rbp)
	# t281 {dead: []} = t282 >= channelOne_s74 {canonical: (image_s0[((r_s75) * (2193)) + ((c_s75) * (3))]) >= (channelOne_s74)}
	movq -96(%rbp), %rax # t281 {dead: []} = t282 >= channelOne_s74 {canonical: (image_s0[((r_s75) * (2193)) + ((c_s75) * (3))]) >= (channelOne_s74)}
	cmpq -32(%rbp), %rax
	setge %al
	movzbq %al, %rax
	movq %rax, -104(%rbp)
	jmp _nop_326
_nop_326:
	jmp _end_of_conditional_113
_end_of_conditional_113:
	cmpq $1, -104(%rbp) # true = t281
	jne _block_107 # ifFalse
	jmp _block_112 # ifTrue
_block_112:
_block_371:
	movq $2193, -56(%rbp) # t332 {dead: []} = 2193
	# t330 {dead: []} = r_s75 * t332 {canonical: (r_s75) * (2193)}
	movq -40(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $3, -72(%rbp) # t344 {dead: []} = 3
	# t342 {dead: []} = c_s75 * t344 {canonical: (c_s75) * (3)}
	movq -48(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	# t329 {dead: []} = t330 + t342 {canonical: ((r_s75) * (2193)) + ((c_s75) * (3))}
	movq -64(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	movq $1, -96(%rbp) # t362 {dead: []} = 1
	movq -88(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	# image_s0[t329] {dead: []} = channelOne_s74 - t362 {canonical: (channelOne_s74) - (1)}
	movq -32(%rbp), %rax
	subq -96(%rbp), %rax
	movq %rax, (%rdi)
	jmp _nop_372
_nop_372:
	jmp _end_of_block_112
_end_of_block_112:
	jmp _block_107
_block_107:
_block_380:
	movq $1, -56(%rbp) # t376 {dead: []} = 1
	movq -56(%rbp), %rax # r_s75 {dead: []} += t376
	addq %rax, -40(%rbp)
	jmp _nop_381
_nop_381:
	jmp _end_of_block_107
_end_of_block_107:
	jmp _conditional_114
_block_103:
_block_389:
	movq $1, -56(%rbp) # t385 {dead: []} = 1
	movq -56(%rbp), %rax # c_s75 {dead: []} += t385
	addq %rax, -48(%rbp)
	jmp _nop_390
_nop_390:
	jmp _end_of_block_103
_end_of_block_103:
	jmp _conditional_115
_return_116:
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

_method_createUnsharpMaskV:
	enter $(944), $0
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
	movq $0, -248(%rbp)
	movq $0, -256(%rbp)
	movq $0, -264(%rbp)
	movq $0, -272(%rbp)
	movq $0, -280(%rbp)
	movq $0, -288(%rbp)
	movq $0, -296(%rbp)
	movq $0, -304(%rbp)
	movq $0, -312(%rbp)
	movq $0, -320(%rbp)
	movq $0, -328(%rbp)
	movq $0, -336(%rbp)
	movq $0, -344(%rbp)
	movq $0, -352(%rbp)
	movq $0, -360(%rbp)
	movq $0, -368(%rbp)
	movq $0, -376(%rbp)
	movq $0, -384(%rbp)
	movq $0, -392(%rbp)
	movq $0, -400(%rbp)
	movq $0, -408(%rbp)
	movq $0, -416(%rbp)
	movq $0, -424(%rbp)
	movq $0, -432(%rbp)
	movq $0, -440(%rbp)
	movq $0, -448(%rbp)
	movq $0, -456(%rbp)
	movq $0, -464(%rbp)
	movq $0, -472(%rbp)
	movq $0, -480(%rbp)
	movq $0, -488(%rbp)
	movq $0, -496(%rbp)
	movq $0, -504(%rbp)
	movq $0, -512(%rbp)
	movq $0, -520(%rbp)
	movq $0, -528(%rbp)
	movq $0, -536(%rbp)
	movq $0, -544(%rbp)
	movq $0, -552(%rbp)
	movq $0, -560(%rbp)
	movq $0, -568(%rbp)
	movq $0, -576(%rbp)
	movq $0, -584(%rbp)
	movq $0, -592(%rbp)
	movq $0, -600(%rbp)
	movq $0, -608(%rbp)
	movq $0, -616(%rbp)
	movq $0, -624(%rbp)
	movq $0, -632(%rbp)
	movq $0, -640(%rbp)
	movq $0, -648(%rbp)
	movq $0, -656(%rbp)
	movq $0, -664(%rbp)
	movq $0, -672(%rbp)
	movq $0, -680(%rbp)
	movq $0, -688(%rbp)
	movq $0, -696(%rbp)
	movq $0, -704(%rbp)
	movq $0, -712(%rbp)
	movq $0, -720(%rbp)
	movq $0, -728(%rbp)
	movq $0, -736(%rbp)
	movq $0, -744(%rbp)
	movq $0, -752(%rbp)
	movq $0, -760(%rbp)
	movq $0, -768(%rbp)
	movq $0, -776(%rbp)
	movq $0, -784(%rbp)
	movq $0, -792(%rbp)
	movq $0, -800(%rbp)
	movq $0, -808(%rbp)
	movq $0, -816(%rbp)
	movq $0, -824(%rbp)
	movq $0, -832(%rbp)
	movq $0, -840(%rbp)
	movq $0, -848(%rbp)
	movq $0, -856(%rbp)
	movq $0, -864(%rbp)
	movq $0, -872(%rbp)
	movq $0, -880(%rbp)
	movq $0, -888(%rbp)
	movq $0, -896(%rbp)
	movq $0, -904(%rbp)
	movq $0, -912(%rbp)
	movq $0, -920(%rbp)
	movq $0, -928(%rbp)
	movq $0, -936(%rbp)
	movq $0, -944(%rbp)
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
_block_396:
_block_473:
	movq $3, -48(%rbp) # center_s65 {dead: []} = 3
	movq $0, -32(%rbp) # r_s65 {dead: []} = 0
	jmp _nop_474
_nop_474:
	jmp _end_of_block_396
_end_of_block_396:
	jmp _conditional_425
_conditional_425:
_block_484:
	# t475 {dead: []} = r_s65 < rows_s0 {canonical: (r_s65) < (rows_s0)}
	movq -32(%rbp), %rax # t475 {dead: []} = r_s65 < rows_s0 {canonical: (r_s65) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_486
_nop_486:
	jmp _end_of_conditional_425
_end_of_conditional_425:
	cmpq $1, -88(%rbp) # true = t475
	jne _block_428 # ifFalse
	jmp _block_400 # ifTrue
_block_400:
_block_491:
	movq $0, -40(%rbp) # c_s65 {dead: []} = 0
	jmp _nop_492
_nop_492:
	jmp _end_of_block_400
_end_of_block_400:
	jmp _conditional_404
_conditional_404:
_block_502:
	# t493 {dead: []} = c_s65 < center_s65 {canonical: (c_s65) < (center_s65)}
	movq -40(%rbp), %rax # t493 {dead: []} = c_s65 < center_s65 {canonical: (c_s65) < (center_s65)}
	cmpq -48(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_504
_nop_504:
	jmp _end_of_conditional_404
_end_of_conditional_404:
	cmpq $1, -88(%rbp) # true = t493
	jne _block_406 # ifFalse
	jmp _block_403 # ifTrue
_block_403:
_block_572:
	movq $731, -88(%rbp) # t510 {dead: []} = 731
	# t508 {dead: []} = r_s65 * t510 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t507 {dead: []} = t508 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2193, -112(%rbp) # t532 {dead: []} = 2193
	# t530 {dead: []} = r_s65 * t532 {canonical: (r_s65) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t544 {dead: []} = 3
	# t542 {dead: []} = c_s65 * t544 {canonical: (c_s65) * (3)}
	movq -40(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t529 {dead: []} = t530 + t542 {canonical: ((r_s65) * (2193)) + ((c_s65) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $2, -152(%rbp) # t561 {dead: []} = 2
	# t528 {dead: []} = t529 + t561 {canonical: (((r_s65) * (2193)) + ((c_s65) * (3))) + (2)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -160(%rbp), %rax # unsharpMask_s0[t507] {dead: []} = image_s0[t528]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # unsharpMask_s0[t507] {dead: []} = image_s0[t528]
	movq %rdx, (%rdi)
	jmp _nop_573
_nop_573:
	jmp _end_of_block_403
_end_of_block_403:
	jmp _block_401
_block_401:
_block_581:
	movq $1, -88(%rbp) # t577 {dead: []} = 1
	movq -88(%rbp), %rax # c_s65 {dead: []} += t577
	addq %rax, -40(%rbp)
	jmp _nop_582
_nop_582:
	jmp _end_of_block_401
_end_of_block_401:
	jmp _conditional_404
_block_406:
_block_588:
	movq -48(%rbp), %rax # c_s65 {dead: []} = center_s65 {canonical: center_s65}
	movq %rax, -40(%rbp)
	jmp _nop_589
_nop_589:
	jmp _end_of_block_406
_end_of_block_406:
	jmp _conditional_418
_conditional_418:
_block_608:
	# t592 {dead: []} = cols_s0 - center_s65 {canonical: (cols_s0) - (center_s65)}
	movq _global_cols(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -88(%rbp)
	# t590 {dead: []} = c_s65 < t592 {canonical: (c_s65) < ((cols_s0) - (center_s65))}
	movq -40(%rbp), %rax # t590 {dead: []} = c_s65 < t592 {canonical: (c_s65) < ((cols_s0) - (center_s65))}
	cmpq -88(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -96(%rbp)
	jmp _nop_610
_nop_610:
	jmp _end_of_conditional_418
_end_of_conditional_418:
	cmpq $1, -96(%rbp) # true = t590
	jne _block_420 # ifFalse
	jmp _block_417 # ifTrue
_block_417:
_block_1269:
	movq $731, -88(%rbp) # t616 {dead: []} = 731
	# t614 {dead: []} = r_s65 * t616 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t613 {dead: []} = t614 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # unsharpMask_s0[t613] {dead: []} = 0
	movq $731, -112(%rbp) # t639 {dead: []} = 731
	# t637 {dead: []} = r_s65 * t639 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	# t636 {dead: []} = t637 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -120(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -128(%rbp)
	movq $2193, -136(%rbp) # t663 {dead: []} = 2193
	# t661 {dead: []} = r_s65 * t663 {canonical: (r_s65) * (2193)}
	movq -32(%rbp), %rax
	imulq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $3, -152(%rbp) # t674 {dead: []} = 3
	# t673 {dead: []} = t674 * c_s65 {canonical: (3) * (c_s65)}
	movq -152(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -160(%rbp)
	# t660 {dead: []} = t661 + t673 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	movq -144(%rbp), %rax
	addq -160(%rbp), %rax
	movq %rax, -168(%rbp)
	movq $7, -176(%rbp) # t692 {dead: []} = 7
	# t659 {dead: []} = t660 - t692 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) - (7)}
	movq -168(%rbp), %rax
	subq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	movq -184(%rbp), %rax # t658 {dead: []} = image_s0[t659]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t658 {dead: []} = image_s0[t659]
	movq %rdx, -192(%rbp)
	movq $0, -200(%rbp) # t705 {dead: []} = 0
	movq -200(%rbp), %rax # t704 {dead: []} = unsharpKernel_s0[t705]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t704 {dead: []} = unsharpKernel_s0[t705]
	movq %rdx, -208(%rbp)
	# t657 {dead: []} = t658 * t704 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) - (7)]) * (unsharpKernel_s0[0])}
	movq -192(%rbp), %rax
	imulq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	movq -128(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -216(%rbp), %rax # unsharpMask_s0[t636] {dead: []} += t657
	addq %rax, (%rdi)
	movq $731, -224(%rbp) # t722 {dead: []} = 731
	# t720 {dead: []} = r_s65 * t722 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	# t719 {dead: []} = t720 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -232(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -240(%rbp)
	movq $2193, -248(%rbp) # t746 {dead: []} = 2193
	# t744 {dead: []} = r_s65 * t746 {canonical: (r_s65) * (2193)}
	movq -32(%rbp), %rax
	imulq -248(%rbp), %rax
	movq %rax, -256(%rbp)
	movq $3, -264(%rbp) # t757 {dead: []} = 3
	# t756 {dead: []} = t757 * c_s65 {canonical: (3) * (c_s65)}
	movq -264(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -272(%rbp)
	# t743 {dead: []} = t744 + t756 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	movq -256(%rbp), %rax
	addq -272(%rbp), %rax
	movq %rax, -280(%rbp)
	movq $4, -288(%rbp) # t775 {dead: []} = 4
	# t742 {dead: []} = t743 - t775 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) - (4)}
	movq -280(%rbp), %rax
	subq -288(%rbp), %rax
	movq %rax, -296(%rbp)
	movq -296(%rbp), %rax # t741 {dead: []} = image_s0[t742]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t741 {dead: []} = image_s0[t742]
	movq %rdx, -304(%rbp)
	movq $1, -312(%rbp) # t788 {dead: []} = 1
	movq -312(%rbp), %rax # t787 {dead: []} = unsharpKernel_s0[t788]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t787 {dead: []} = unsharpKernel_s0[t788]
	movq %rdx, -320(%rbp)
	# t740 {dead: []} = t741 * t787 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) - (4)]) * (unsharpKernel_s0[1])}
	movq -304(%rbp), %rax
	imulq -320(%rbp), %rax
	movq %rax, -328(%rbp)
	movq -240(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -328(%rbp), %rax # unsharpMask_s0[t719] {dead: []} += t740
	addq %rax, (%rdi)
	movq $731, -336(%rbp) # t805 {dead: []} = 731
	# t803 {dead: []} = r_s65 * t805 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -336(%rbp), %rax
	movq %rax, -344(%rbp)
	# t802 {dead: []} = t803 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -344(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -352(%rbp)
	movq $2193, -360(%rbp) # t829 {dead: []} = 2193
	# t827 {dead: []} = r_s65 * t829 {canonical: (r_s65) * (2193)}
	movq -32(%rbp), %rax
	imulq -360(%rbp), %rax
	movq %rax, -368(%rbp)
	movq $3, -376(%rbp) # t840 {dead: []} = 3
	# t839 {dead: []} = t840 * c_s65 {canonical: (3) * (c_s65)}
	movq -376(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -384(%rbp)
	# t826 {dead: []} = t827 + t839 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	movq -368(%rbp), %rax
	addq -384(%rbp), %rax
	movq %rax, -392(%rbp)
	movq $1, -400(%rbp) # t858 {dead: []} = 1
	# t825 {dead: []} = t826 - t858 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) - (1)}
	movq -392(%rbp), %rax
	subq -400(%rbp), %rax
	movq %rax, -408(%rbp)
	movq -408(%rbp), %rax # t824 {dead: []} = image_s0[t825]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t824 {dead: []} = image_s0[t825]
	movq %rdx, -416(%rbp)
	movq $2, -424(%rbp) # t871 {dead: []} = 2
	movq -424(%rbp), %rax # t870 {dead: []} = unsharpKernel_s0[t871]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t870 {dead: []} = unsharpKernel_s0[t871]
	movq %rdx, -432(%rbp)
	# t823 {dead: []} = t824 * t870 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) - (1)]) * (unsharpKernel_s0[2])}
	movq -416(%rbp), %rax
	imulq -432(%rbp), %rax
	movq %rax, -440(%rbp)
	movq -352(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -440(%rbp), %rax # unsharpMask_s0[t802] {dead: []} += t823
	addq %rax, (%rdi)
	movq $731, -448(%rbp) # t888 {dead: []} = 731
	# t886 {dead: []} = r_s65 * t888 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -448(%rbp), %rax
	movq %rax, -456(%rbp)
	# t885 {dead: []} = t886 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -456(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -464(%rbp)
	movq $2193, -472(%rbp) # t912 {dead: []} = 2193
	# t910 {dead: []} = r_s65 * t912 {canonical: (r_s65) * (2193)}
	movq -32(%rbp), %rax
	imulq -472(%rbp), %rax
	movq %rax, -480(%rbp)
	movq $3, -488(%rbp) # t923 {dead: []} = 3
	# t922 {dead: []} = t923 * c_s65 {canonical: (3) * (c_s65)}
	movq -488(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -496(%rbp)
	# t909 {dead: []} = t910 + t922 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	movq -480(%rbp), %rax
	addq -496(%rbp), %rax
	movq %rax, -504(%rbp)
	movq $2, -512(%rbp) # t941 {dead: []} = 2
	# t908 {dead: []} = t909 + t941 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) + (2)}
	movq -504(%rbp), %rax
	addq -512(%rbp), %rax
	movq %rax, -520(%rbp)
	movq -520(%rbp), %rax # t907 {dead: []} = image_s0[t908]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t907 {dead: []} = image_s0[t908]
	movq %rdx, -528(%rbp)
	movq $3, -536(%rbp) # t954 {dead: []} = 3
	movq -536(%rbp), %rax # t953 {dead: []} = unsharpKernel_s0[t954]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t953 {dead: []} = unsharpKernel_s0[t954]
	movq %rdx, -544(%rbp)
	# t906 {dead: []} = t907 * t953 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) + (2)]) * (unsharpKernel_s0[3])}
	movq -528(%rbp), %rax
	imulq -544(%rbp), %rax
	movq %rax, -552(%rbp)
	movq -464(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -552(%rbp), %rax # unsharpMask_s0[t885] {dead: []} += t906
	addq %rax, (%rdi)
	movq $731, -560(%rbp) # t971 {dead: []} = 731
	# t969 {dead: []} = r_s65 * t971 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -560(%rbp), %rax
	movq %rax, -568(%rbp)
	# t968 {dead: []} = t969 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -568(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -576(%rbp)
	movq $2193, -584(%rbp) # t995 {dead: []} = 2193
	# t993 {dead: []} = r_s65 * t995 {canonical: (r_s65) * (2193)}
	movq -32(%rbp), %rax
	imulq -584(%rbp), %rax
	movq %rax, -592(%rbp)
	movq $3, -600(%rbp) # t1006 {dead: []} = 3
	# t1005 {dead: []} = t1006 * c_s65 {canonical: (3) * (c_s65)}
	movq -600(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -608(%rbp)
	# t992 {dead: []} = t993 + t1005 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	movq -592(%rbp), %rax
	addq -608(%rbp), %rax
	movq %rax, -616(%rbp)
	movq $5, -624(%rbp) # t1024 {dead: []} = 5
	# t991 {dead: []} = t992 + t1024 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) + (5)}
	movq -616(%rbp), %rax
	addq -624(%rbp), %rax
	movq %rax, -632(%rbp)
	movq -632(%rbp), %rax # t990 {dead: []} = image_s0[t991]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t990 {dead: []} = image_s0[t991]
	movq %rdx, -640(%rbp)
	movq $4, -648(%rbp) # t1037 {dead: []} = 4
	movq -648(%rbp), %rax # t1036 {dead: []} = unsharpKernel_s0[t1037]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1036 {dead: []} = unsharpKernel_s0[t1037]
	movq %rdx, -656(%rbp)
	# t989 {dead: []} = t990 * t1036 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) + (5)]) * (unsharpKernel_s0[4])}
	movq -640(%rbp), %rax
	imulq -656(%rbp), %rax
	movq %rax, -664(%rbp)
	movq -576(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -664(%rbp), %rax # unsharpMask_s0[t968] {dead: []} += t989
	addq %rax, (%rdi)
	movq $731, -672(%rbp) # t1054 {dead: []} = 731
	# t1052 {dead: []} = r_s65 * t1054 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -672(%rbp), %rax
	movq %rax, -680(%rbp)
	# t1051 {dead: []} = t1052 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -680(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -688(%rbp)
	movq $2193, -696(%rbp) # t1078 {dead: []} = 2193
	# t1076 {dead: []} = r_s65 * t1078 {canonical: (r_s65) * (2193)}
	movq -32(%rbp), %rax
	imulq -696(%rbp), %rax
	movq %rax, -704(%rbp)
	movq $3, -712(%rbp) # t1089 {dead: []} = 3
	# t1088 {dead: []} = t1089 * c_s65 {canonical: (3) * (c_s65)}
	movq -712(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -720(%rbp)
	# t1075 {dead: []} = t1076 + t1088 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	movq -704(%rbp), %rax
	addq -720(%rbp), %rax
	movq %rax, -728(%rbp)
	movq $8, -736(%rbp) # t1107 {dead: []} = 8
	# t1074 {dead: []} = t1075 + t1107 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) + (8)}
	movq -728(%rbp), %rax
	addq -736(%rbp), %rax
	movq %rax, -744(%rbp)
	movq -744(%rbp), %rax # t1073 {dead: []} = image_s0[t1074]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1073 {dead: []} = image_s0[t1074]
	movq %rdx, -752(%rbp)
	movq $5, -760(%rbp) # t1120 {dead: []} = 5
	movq -760(%rbp), %rax # t1119 {dead: []} = unsharpKernel_s0[t1120]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1119 {dead: []} = unsharpKernel_s0[t1120]
	movq %rdx, -768(%rbp)
	# t1072 {dead: []} = t1073 * t1119 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) + (8)]) * (unsharpKernel_s0[5])}
	movq -752(%rbp), %rax
	imulq -768(%rbp), %rax
	movq %rax, -776(%rbp)
	movq -688(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -776(%rbp), %rax # unsharpMask_s0[t1051] {dead: []} += t1072
	addq %rax, (%rdi)
	movq $731, -784(%rbp) # t1137 {dead: []} = 731
	# t1135 {dead: []} = r_s65 * t1137 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -784(%rbp), %rax
	movq %rax, -792(%rbp)
	# t1134 {dead: []} = t1135 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -792(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -800(%rbp)
	movq $2193, -808(%rbp) # t1161 {dead: []} = 2193
	# t1159 {dead: []} = r_s65 * t1161 {canonical: (r_s65) * (2193)}
	movq -32(%rbp), %rax
	imulq -808(%rbp), %rax
	movq %rax, -816(%rbp)
	movq $3, -824(%rbp) # t1172 {dead: []} = 3
	# t1171 {dead: []} = t1172 * c_s65 {canonical: (3) * (c_s65)}
	movq -824(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -832(%rbp)
	# t1158 {dead: []} = t1159 + t1171 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	movq -816(%rbp), %rax
	addq -832(%rbp), %rax
	movq %rax, -840(%rbp)
	movq $11, -848(%rbp) # t1190 {dead: []} = 11
	# t1157 {dead: []} = t1158 + t1190 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) + (11)}
	movq -840(%rbp), %rax
	addq -848(%rbp), %rax
	movq %rax, -856(%rbp)
	movq -856(%rbp), %rax # t1156 {dead: []} = image_s0[t1157]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1156 {dead: []} = image_s0[t1157]
	movq %rdx, -864(%rbp)
	movq $6, -872(%rbp) # t1203 {dead: []} = 6
	movq -872(%rbp), %rax # t1202 {dead: []} = unsharpKernel_s0[t1203]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1202 {dead: []} = unsharpKernel_s0[t1203]
	movq %rdx, -880(%rbp)
	# t1155 {dead: []} = t1156 * t1202 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) + (11)]) * (unsharpKernel_s0[6])}
	movq -864(%rbp), %rax
	imulq -880(%rbp), %rax
	movq %rax, -888(%rbp)
	movq -800(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -888(%rbp), %rax # unsharpMask_s0[t1134] {dead: []} += t1155
	addq %rax, (%rdi)
	movq $731, -896(%rbp) # t1220 {dead: []} = 731
	# t1218 {dead: []} = r_s65 * t1220 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -896(%rbp), %rax
	movq %rax, -904(%rbp)
	# t1217 {dead: []} = t1218 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -904(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -912(%rbp)
	movq $731, -920(%rbp) # t1242 {dead: []} = 731
	# t1240 {dead: []} = r_s65 * t1242 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -920(%rbp), %rax
	movq %rax, -928(%rbp)
	# t1239 {dead: []} = t1240 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -928(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -936(%rbp)
	movq -936(%rbp), %rax # t1238 {dead: []} = unsharpMask_s0[t1239]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1238 {dead: []} = unsharpMask_s0[t1239]
	movq %rdx, -944(%rbp)
	movq -912(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	# unsharpMask_s0[t1217] {dead: []} = t1238 / kernel_sum_s0 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) / (kernel_sum_s0)}
	movq -944(%rbp), %rax
	cqto
	idivq _global_kernel_sum(%rip)
	movq %rax, (%rdi)
	jmp _nop_1270
_nop_1270:
	jmp _end_of_block_417
_end_of_block_417:
	jmp _block_407
_block_407:
_block_1278:
	movq $1, -88(%rbp) # t1274 {dead: []} = 1
	movq -88(%rbp), %rax # c_s65 {dead: []} += t1274
	addq %rax, -40(%rbp)
	jmp _nop_1279
_nop_1279:
	jmp _end_of_block_407
_end_of_block_407:
	jmp _conditional_418
_block_420:
_block_1291:
	# c_s65 {dead: []} = cols_s0 - center_s65 {canonical: (cols_s0) - (center_s65)}
	movq _global_cols(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -40(%rbp)
	jmp _nop_1292
_nop_1292:
	jmp _end_of_block_420
_end_of_block_420:
	jmp _conditional_424
_conditional_424:
_block_1302:
	# t1293 {dead: []} = c_s65 < cols_s0 {canonical: (c_s65) < (cols_s0)}
	movq -40(%rbp), %rax # t1293 {dead: []} = c_s65 < cols_s0 {canonical: (c_s65) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_1304
_nop_1304:
	jmp _end_of_conditional_424
_end_of_conditional_424:
	cmpq $1, -88(%rbp) # true = t1293
	jne _block_397 # ifFalse
	jmp _block_423 # ifTrue
_block_423:
_block_1372:
	movq $731, -88(%rbp) # t1310 {dead: []} = 731
	# t1308 {dead: []} = r_s65 * t1310 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t1307 {dead: []} = t1308 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2193, -112(%rbp) # t1332 {dead: []} = 2193
	# t1330 {dead: []} = r_s65 * t1332 {canonical: (r_s65) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t1344 {dead: []} = 3
	# t1342 {dead: []} = c_s65 * t1344 {canonical: (c_s65) * (3)}
	movq -40(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t1329 {dead: []} = t1330 + t1342 {canonical: ((r_s65) * (2193)) + ((c_s65) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $2, -152(%rbp) # t1361 {dead: []} = 2
	# t1328 {dead: []} = t1329 + t1361 {canonical: (((r_s65) * (2193)) + ((c_s65) * (3))) + (2)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -160(%rbp), %rax # unsharpMask_s0[t1307] {dead: []} = image_s0[t1328]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # unsharpMask_s0[t1307] {dead: []} = image_s0[t1328]
	movq %rdx, (%rdi)
	jmp _nop_1373
_nop_1373:
	jmp _end_of_block_423
_end_of_block_423:
	jmp _block_421
_block_421:
_block_1381:
	movq $1, -88(%rbp) # t1377 {dead: []} = 1
	movq -88(%rbp), %rax # c_s65 {dead: []} += t1377
	addq %rax, -40(%rbp)
	jmp _nop_1382
_nop_1382:
	jmp _end_of_block_421
_end_of_block_421:
	jmp _conditional_424
_block_397:
_block_1390:
	movq $1, -88(%rbp) # t1386 {dead: []} = 1
	movq -88(%rbp), %rax # r_s65 {dead: []} += t1386
	addq %rax, -32(%rbp)
	jmp _nop_1391
_nop_1391:
	jmp _end_of_block_397
_end_of_block_397:
	jmp _conditional_425
_block_428:
_block_1415:
	movq -48(%rbp), %rax # t1394 {dead: []} = center_s65 {canonical: center_s65}
	movq %rax, -88(%rbp)
	movq -48(%rbp), %rax # t1399 {dead: []} = center_s65 {canonical: center_s65}
	movq %rax, -96(%rbp)
	movq -96(%rbp), %rax # t1398 {dead: []} = unsharpKernel_s0[t1399]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1398 {dead: []} = unsharpKernel_s0[t1399]
	movq %rdx, -104(%rbp)
	movq -88(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	# unsharpKernel_s0[t1394] {dead: []} = t1398 - kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s65]) - (kernel_sum_s0)}
	movq -104(%rbp), %rax
	subq _global_kernel_sum(%rip), %rax
	movq %rax, (%rdi)
	movq $0, -40(%rbp) # c_s65 {dead: []} = 0
	jmp _nop_1416
_nop_1416:
	jmp _end_of_block_428
_end_of_block_428:
	jmp _conditional_463
_conditional_463:
_block_1426:
	# t1417 {dead: []} = c_s65 < cols_s0 {canonical: (c_s65) < (cols_s0)}
	movq -40(%rbp), %rax # t1417 {dead: []} = c_s65 < cols_s0 {canonical: (c_s65) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_1428
_nop_1428:
	jmp _end_of_conditional_463
_end_of_conditional_463:
	cmpq $1, -88(%rbp) # true = t1417
	jne _block_464 # ifFalse
	jmp _block_435 # ifTrue
_block_435:
_block_1470:
	movq -40(%rbp), %rax # t1432 {dead: []} = c_s65 {canonical: c_s65}
	movq %rax, -88(%rbp)
	movq -88(%rbp), %rax # m1_s70 {dead: []} = unsharpMask_s0[t1432]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m1_s70 {dead: []} = unsharpMask_s0[t1432]
	movq %rdx, -56(%rbp)
	movq $731, -96(%rbp) # t1441 {dead: []} = 731
	# t1439 {dead: []} = c_s65 + t1441 {canonical: (c_s65) + (731)}
	movq -40(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # m2_s70 {dead: []} = unsharpMask_s0[t1439]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m2_s70 {dead: []} = unsharpMask_s0[t1439]
	movq %rdx, -64(%rbp)
	movq $1462, -112(%rbp) # t1456 {dead: []} = 1462
	# t1454 {dead: []} = c_s65 + t1456 {canonical: (c_s65) + (1462)}
	movq -40(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rax # m3_s70 {dead: []} = unsharpMask_s0[t1454]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m3_s70 {dead: []} = unsharpMask_s0[t1454]
	movq %rdx, -72(%rbp)
	movq $0, -32(%rbp) # r_s65 {dead: []} = 0
	jmp _nop_1471
_nop_1471:
	jmp _end_of_block_435
_end_of_block_435:
	jmp _conditional_439
_conditional_439:
_block_1481:
	# t1472 {dead: []} = r_s65 < center_s65 {canonical: (r_s65) < (center_s65)}
	movq -32(%rbp), %rax # t1472 {dead: []} = r_s65 < center_s65 {canonical: (r_s65) < (center_s65)}
	cmpq -48(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_1483
_nop_1483:
	jmp _end_of_conditional_439
_end_of_conditional_439:
	cmpq $1, -88(%rbp) # true = t1472
	jne _block_441 # ifFalse
	jmp _block_438 # ifTrue
_block_438:
_block_1508:
	movq $731, -88(%rbp) # t1489 {dead: []} = 731
	# t1487 {dead: []} = r_s65 * t1489 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t1486 {dead: []} = t1487 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # unsharpMask_s0[t1486] {dead: []} = 0
	jmp _nop_1509
_nop_1509:
	jmp _end_of_block_438
_end_of_block_438:
	jmp _block_436
_block_436:
_block_1517:
	movq $1, -88(%rbp) # t1513 {dead: []} = 1
	movq -88(%rbp), %rax # r_s65 {dead: []} += t1513
	addq %rax, -32(%rbp)
	jmp _nop_1518
_nop_1518:
	jmp _end_of_block_436
_end_of_block_436:
	jmp _conditional_439
_block_441:
_block_1524:
	movq -48(%rbp), %rax # r_s65 {dead: []} = center_s65 {canonical: center_s65}
	movq %rax, -32(%rbp)
	jmp _nop_1525
_nop_1525:
	jmp _end_of_block_441
_end_of_block_441:
	jmp _conditional_456
_conditional_456:
_block_1544:
	# t1528 {dead: []} = rows_s0 - center_s65 {canonical: (rows_s0) - (center_s65)}
	movq _global_rows(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -88(%rbp)
	# t1526 {dead: []} = r_s65 < t1528 {canonical: (r_s65) < ((rows_s0) - (center_s65))}
	movq -32(%rbp), %rax # t1526 {dead: []} = r_s65 < t1528 {canonical: (r_s65) < ((rows_s0) - (center_s65))}
	cmpq -88(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -96(%rbp)
	jmp _nop_1546
_nop_1546:
	jmp _end_of_conditional_456
_end_of_conditional_456:
	cmpq $1, -96(%rbp) # true = t1526
	jne _block_458 # ifFalse
	jmp _block_455 # ifTrue
_block_455:
_block_2088:
	movq $0, -80(%rbp) # dot_s72 {dead: []} = 0
	movq $731, -88(%rbp) # t1558 {dead: []} = 731
	# t1556 {dead: []} = r_s65 * t1558 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t1555 {dead: []} = t1556 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # t1554 {dead: []} = unsharpMask_s0[t1555]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1554 {dead: []} = unsharpMask_s0[t1555]
	movq %rdx, -112(%rbp)
	movq $0, -120(%rbp) # t1581 {dead: []} = 0
	movq -120(%rbp), %rax # t1580 {dead: []} = unsharpKernel_s0[t1581]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1580 {dead: []} = unsharpKernel_s0[t1581]
	movq %rdx, -128(%rbp)
	# t1578 {dead: []} = m1_s70 * t1580 {canonical: (m1_s70) * (unsharpKernel_s0[0])}
	movq -56(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t1553 {dead: []} = t1554 + t1578 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((m1_s70) * (unsharpKernel_s0[0]))}
	movq -112(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq -144(%rbp), %rax # dot_s72 {dead: []} += t1553
	addq %rax, -80(%rbp)
	movq $731, -152(%rbp) # t1608 {dead: []} = 731
	# t1606 {dead: []} = r_s65 * t1608 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	# t1605 {dead: []} = t1606 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -160(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -168(%rbp)
	movq -168(%rbp), %rax # t1604 {dead: []} = unsharpMask_s0[t1605]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1604 {dead: []} = unsharpMask_s0[t1605]
	movq %rdx, -176(%rbp)
	movq $1, -184(%rbp) # t1631 {dead: []} = 1
	movq -184(%rbp), %rax # t1630 {dead: []} = unsharpKernel_s0[t1631]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1630 {dead: []} = unsharpKernel_s0[t1631]
	movq %rdx, -192(%rbp)
	# t1628 {dead: []} = m2_s70 * t1630 {canonical: (m2_s70) * (unsharpKernel_s0[1])}
	movq -64(%rbp), %rax
	imulq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	# t1603 {dead: []} = t1604 + t1628 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((m2_s70) * (unsharpKernel_s0[1]))}
	movq -176(%rbp), %rax
	addq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	movq -208(%rbp), %rax # dot_s72 {dead: []} += t1603
	addq %rax, -80(%rbp)
	movq $731, -216(%rbp) # t1658 {dead: []} = 731
	# t1656 {dead: []} = r_s65 * t1658 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -216(%rbp), %rax
	movq %rax, -224(%rbp)
	# t1655 {dead: []} = t1656 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -224(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -232(%rbp)
	movq -232(%rbp), %rax # t1654 {dead: []} = unsharpMask_s0[t1655]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1654 {dead: []} = unsharpMask_s0[t1655]
	movq %rdx, -240(%rbp)
	movq $2, -248(%rbp) # t1681 {dead: []} = 2
	movq -248(%rbp), %rax # t1680 {dead: []} = unsharpKernel_s0[t1681]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1680 {dead: []} = unsharpKernel_s0[t1681]
	movq %rdx, -256(%rbp)
	# t1678 {dead: []} = m3_s70 * t1680 {canonical: (m3_s70) * (unsharpKernel_s0[2])}
	movq -72(%rbp), %rax
	imulq -256(%rbp), %rax
	movq %rax, -264(%rbp)
	# t1653 {dead: []} = t1654 + t1678 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((m3_s70) * (unsharpKernel_s0[2]))}
	movq -240(%rbp), %rax
	addq -264(%rbp), %rax
	movq %rax, -272(%rbp)
	movq -272(%rbp), %rax # dot_s72 {dead: []} += t1653
	addq %rax, -80(%rbp)
	movq $731, -280(%rbp) # t1708 {dead: []} = 731
	# t1706 {dead: []} = r_s65 * t1708 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -280(%rbp), %rax
	movq %rax, -288(%rbp)
	# t1705 {dead: []} = t1706 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -288(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -296(%rbp)
	movq -296(%rbp), %rax # t1704 {dead: []} = unsharpMask_s0[t1705]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1704 {dead: []} = unsharpMask_s0[t1705]
	movq %rdx, -304(%rbp)
	movq $731, -312(%rbp) # t1732 {dead: []} = 731
	# t1731 {dead: []} = t1732 * r_s65 {canonical: (731) * (r_s65)}
	movq -312(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -320(%rbp)
	# t1730 {dead: []} = t1731 + c_s65 {canonical: ((731) * (r_s65)) + (c_s65)}
	movq -320(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -328(%rbp)
	movq -328(%rbp), %rax # t1729 {dead: []} = unsharpMask_s0[t1730]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1729 {dead: []} = unsharpMask_s0[t1730]
	movq %rdx, -336(%rbp)
	movq $3, -344(%rbp) # t1754 {dead: []} = 3
	movq -344(%rbp), %rax # t1753 {dead: []} = unsharpKernel_s0[t1754]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1753 {dead: []} = unsharpKernel_s0[t1754]
	movq %rdx, -352(%rbp)
	# t1728 {dead: []} = t1729 * t1753 {canonical: (unsharpMask_s0[((731) * (r_s65)) + (c_s65)]) * (unsharpKernel_s0[3])}
	movq -336(%rbp), %rax
	imulq -352(%rbp), %rax
	movq %rax, -360(%rbp)
	# t1703 {dead: []} = t1704 + t1728 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((unsharpMask_s0[((731) * (r_s65)) + (c_s65)]) * (unsharpKernel_s0[3]))}
	movq -304(%rbp), %rax
	addq -360(%rbp), %rax
	movq %rax, -368(%rbp)
	movq -368(%rbp), %rax # dot_s72 {dead: []} += t1703
	addq %rax, -80(%rbp)
	movq $731, -376(%rbp) # t1781 {dead: []} = 731
	# t1779 {dead: []} = r_s65 * t1781 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -376(%rbp), %rax
	movq %rax, -384(%rbp)
	# t1778 {dead: []} = t1779 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -384(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -392(%rbp)
	movq -392(%rbp), %rax # t1777 {dead: []} = unsharpMask_s0[t1778]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1777 {dead: []} = unsharpMask_s0[t1778]
	movq %rdx, -400(%rbp)
	movq $731, -408(%rbp) # t1806 {dead: []} = 731
	# t1805 {dead: []} = t1806 * r_s65 {canonical: (731) * (r_s65)}
	movq -408(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -416(%rbp)
	# t1804 {dead: []} = t1805 + c_s65 {canonical: ((731) * (r_s65)) + (c_s65)}
	movq -416(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -424(%rbp)
	movq $731, -432(%rbp) # t1825 {dead: []} = 731
	# t1803 {dead: []} = t1804 + t1825 {canonical: (((731) * (r_s65)) + (c_s65)) + (731)}
	movq -424(%rbp), %rax
	addq -432(%rbp), %rax
	movq %rax, -440(%rbp)
	movq -440(%rbp), %rax # t1802 {dead: []} = unsharpMask_s0[t1803]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1802 {dead: []} = unsharpMask_s0[t1803]
	movq %rdx, -448(%rbp)
	movq $4, -456(%rbp) # t1838 {dead: []} = 4
	movq -456(%rbp), %rax # t1837 {dead: []} = unsharpKernel_s0[t1838]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1837 {dead: []} = unsharpKernel_s0[t1838]
	movq %rdx, -464(%rbp)
	# t1801 {dead: []} = t1802 * t1837 {canonical: (unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (731)]) * (unsharpKernel_s0[4])}
	movq -448(%rbp), %rax
	imulq -464(%rbp), %rax
	movq %rax, -472(%rbp)
	# t1776 {dead: []} = t1777 + t1801 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (731)]) * (unsharpKernel_s0[4]))}
	movq -400(%rbp), %rax
	addq -472(%rbp), %rax
	movq %rax, -480(%rbp)
	movq -480(%rbp), %rax # dot_s72 {dead: []} += t1776
	addq %rax, -80(%rbp)
	movq $731, -488(%rbp) # t1865 {dead: []} = 731
	# t1863 {dead: []} = r_s65 * t1865 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -488(%rbp), %rax
	movq %rax, -496(%rbp)
	# t1862 {dead: []} = t1863 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -496(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -504(%rbp)
	movq -504(%rbp), %rax # t1861 {dead: []} = unsharpMask_s0[t1862]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1861 {dead: []} = unsharpMask_s0[t1862]
	movq %rdx, -512(%rbp)
	movq $731, -520(%rbp) # t1890 {dead: []} = 731
	# t1889 {dead: []} = t1890 * r_s65 {canonical: (731) * (r_s65)}
	movq -520(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -528(%rbp)
	# t1888 {dead: []} = t1889 + c_s65 {canonical: ((731) * (r_s65)) + (c_s65)}
	movq -528(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -536(%rbp)
	movq $1462, -544(%rbp) # t1909 {dead: []} = 1462
	# t1887 {dead: []} = t1888 + t1909 {canonical: (((731) * (r_s65)) + (c_s65)) + (1462)}
	movq -536(%rbp), %rax
	addq -544(%rbp), %rax
	movq %rax, -552(%rbp)
	movq -552(%rbp), %rax # t1886 {dead: []} = unsharpMask_s0[t1887]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1886 {dead: []} = unsharpMask_s0[t1887]
	movq %rdx, -560(%rbp)
	movq $5, -568(%rbp) # t1922 {dead: []} = 5
	movq -568(%rbp), %rax # t1921 {dead: []} = unsharpKernel_s0[t1922]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1921 {dead: []} = unsharpKernel_s0[t1922]
	movq %rdx, -576(%rbp)
	# t1885 {dead: []} = t1886 * t1921 {canonical: (unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (1462)]) * (unsharpKernel_s0[5])}
	movq -560(%rbp), %rax
	imulq -576(%rbp), %rax
	movq %rax, -584(%rbp)
	# t1860 {dead: []} = t1861 + t1885 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (1462)]) * (unsharpKernel_s0[5]))}
	movq -512(%rbp), %rax
	addq -584(%rbp), %rax
	movq %rax, -592(%rbp)
	movq -592(%rbp), %rax # dot_s72 {dead: []} += t1860
	addq %rax, -80(%rbp)
	movq $731, -600(%rbp) # t1949 {dead: []} = 731
	# t1947 {dead: []} = r_s65 * t1949 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -600(%rbp), %rax
	movq %rax, -608(%rbp)
	# t1946 {dead: []} = t1947 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -608(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -616(%rbp)
	movq -616(%rbp), %rax # t1945 {dead: []} = unsharpMask_s0[t1946]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1945 {dead: []} = unsharpMask_s0[t1946]
	movq %rdx, -624(%rbp)
	movq $731, -632(%rbp) # t1974 {dead: []} = 731
	# t1973 {dead: []} = t1974 * r_s65 {canonical: (731) * (r_s65)}
	movq -632(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -640(%rbp)
	# t1972 {dead: []} = t1973 + c_s65 {canonical: ((731) * (r_s65)) + (c_s65)}
	movq -640(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -648(%rbp)
	movq $2193, -656(%rbp) # t1993 {dead: []} = 2193
	# t1971 {dead: []} = t1972 + t1993 {canonical: (((731) * (r_s65)) + (c_s65)) + (2193)}
	movq -648(%rbp), %rax
	addq -656(%rbp), %rax
	movq %rax, -664(%rbp)
	movq -664(%rbp), %rax # t1970 {dead: []} = unsharpMask_s0[t1971]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t1970 {dead: []} = unsharpMask_s0[t1971]
	movq %rdx, -672(%rbp)
	movq $6, -680(%rbp) # t2006 {dead: []} = 6
	movq -680(%rbp), %rax # t2005 {dead: []} = unsharpKernel_s0[t2006]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2005 {dead: []} = unsharpKernel_s0[t2006]
	movq %rdx, -688(%rbp)
	# t1969 {dead: []} = t1970 * t2005 {canonical: (unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (2193)]) * (unsharpKernel_s0[6])}
	movq -672(%rbp), %rax
	imulq -688(%rbp), %rax
	movq %rax, -696(%rbp)
	# t1944 {dead: []} = t1945 + t1969 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (2193)]) * (unsharpKernel_s0[6]))}
	movq -624(%rbp), %rax
	addq -696(%rbp), %rax
	movq %rax, -704(%rbp)
	movq -704(%rbp), %rax # dot_s72 {dead: []} += t1944
	addq %rax, -80(%rbp)
	movq -64(%rbp), %rax # m1_s70 {dead: []} = m2_s70 {canonical: m2_s70}
	movq %rax, -56(%rbp)
	movq -72(%rbp), %rax # m2_s70 {dead: []} = m3_s70 {canonical: m3_s70}
	movq %rax, -64(%rbp)
	movq $731, -712(%rbp) # t2039 {dead: []} = 731
	# t2037 {dead: []} = r_s65 * t2039 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -712(%rbp), %rax
	movq %rax, -720(%rbp)
	# t2036 {dead: []} = t2037 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -720(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -728(%rbp)
	movq -728(%rbp), %rax # m3_s70 {dead: []} = unsharpMask_s0[t2036]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m3_s70 {dead: []} = unsharpMask_s0[t2036]
	movq %rdx, -72(%rbp)
	movq $731, -736(%rbp) # t2062 {dead: []} = 731
	# t2060 {dead: []} = r_s65 * t2062 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -736(%rbp), %rax
	movq %rax, -744(%rbp)
	# t2059 {dead: []} = t2060 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -744(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -752(%rbp)
	movq -752(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	# unsharpMask_s0[t2059] {dead: []} = dot_s72 / kernel_sum_s0 {canonical: (dot_s72) / (kernel_sum_s0)}
	movq -80(%rbp), %rax
	cqto
	idivq _global_kernel_sum(%rip)
	movq %rax, (%rdi)
	jmp _nop_2089
_nop_2089:
	jmp _end_of_block_455
_end_of_block_455:
	jmp _block_442
_block_442:
_block_2097:
	movq $1, -88(%rbp) # t2093 {dead: []} = 1
	movq -88(%rbp), %rax # r_s65 {dead: []} += t2093
	addq %rax, -32(%rbp)
	jmp _nop_2098
_nop_2098:
	jmp _end_of_block_442
_end_of_block_442:
	jmp _conditional_456
_block_458:
_block_2110:
	# r_s65 {dead: []} = rows_s0 - center_s65 {canonical: (rows_s0) - (center_s65)}
	movq _global_rows(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -32(%rbp)
	jmp _nop_2111
_nop_2111:
	jmp _end_of_block_458
_end_of_block_458:
	jmp _conditional_462
_conditional_462:
_block_2121:
	# t2112 {dead: []} = r_s65 < rows_s0 {canonical: (r_s65) < (rows_s0)}
	movq -32(%rbp), %rax # t2112 {dead: []} = r_s65 < rows_s0 {canonical: (r_s65) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_2123
_nop_2123:
	jmp _end_of_conditional_462
_end_of_conditional_462:
	cmpq $1, -88(%rbp) # true = t2112
	jne _block_429 # ifFalse
	jmp _block_461 # ifTrue
_block_461:
_block_2148:
	movq $731, -88(%rbp) # t2129 {dead: []} = 731
	# t2127 {dead: []} = r_s65 * t2129 {canonical: (r_s65) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t2126 {dead: []} = t2127 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # unsharpMask_s0[t2126] {dead: []} = 0
	jmp _nop_2149
_nop_2149:
	jmp _end_of_block_461
_end_of_block_461:
	jmp _block_459
_block_459:
_block_2157:
	movq $1, -88(%rbp) # t2153 {dead: []} = 1
	movq -88(%rbp), %rax # r_s65 {dead: []} += t2153
	addq %rax, -32(%rbp)
	jmp _nop_2158
_nop_2158:
	jmp _end_of_block_459
_end_of_block_459:
	jmp _conditional_462
_block_429:
_block_2166:
	movq $1, -88(%rbp) # t2162 {dead: []} = 1
	movq -88(%rbp), %rax # c_s65 {dead: []} += t2162
	addq %rax, -40(%rbp)
	jmp _nop_2167
_nop_2167:
	jmp _end_of_block_429
_end_of_block_429:
	jmp _conditional_463
_block_464:
_block_2188:
	movq -48(%rbp), %rax # t2170 {dead: []} = center_s65 {canonical: center_s65}
	movq %rax, -88(%rbp)
	movq -48(%rbp), %rax # t2175 {dead: []} = center_s65 {canonical: center_s65}
	movq %rax, -96(%rbp)
	movq -96(%rbp), %rax # t2174 {dead: []} = unsharpKernel_s0[t2175]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2174 {dead: []} = unsharpKernel_s0[t2175]
	movq %rdx, -104(%rbp)
	movq -88(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	# unsharpKernel_s0[t2170] {dead: []} = t2174 + kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s65]) + (kernel_sum_s0)}
	movq -104(%rbp), %rax
	addq _global_kernel_sum(%rip), %rax
	movq %rax, (%rdi)
	jmp _nop_2189
_nop_2189:
	jmp _end_of_block_464
_end_of_block_464:
	jmp _return_465
_return_465:
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

_method_createKernel:
	enter $(96), $0
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
_block_2203:
_block_2254:
	movq $0, -40(%rbp) # t2211 {dead: []} = 0
	movq -40(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	movq $4433, (%rdi) # unsharpKernel_s0[t2211] {dead: []} = 4433
	movq $1, -48(%rbp) # t2216 {dead: []} = 1
	movq -48(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	movq $54006, (%rdi) # unsharpKernel_s0[t2216] {dead: []} = 54006
	movq $2, -56(%rbp) # t2221 {dead: []} = 2
	movq -56(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	movq $242036, (%rdi) # unsharpKernel_s0[t2221] {dead: []} = 242036
	movq $3, -64(%rbp) # t2226 {dead: []} = 3
	movq -64(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	movq $399050, (%rdi) # unsharpKernel_s0[t2226] {dead: []} = 399050
	movq $4, -72(%rbp) # t2231 {dead: []} = 4
	movq -72(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	movq $242036, (%rdi) # unsharpKernel_s0[t2231] {dead: []} = 242036
	movq $5, -80(%rbp) # t2236 {dead: []} = 5
	movq -80(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	movq $54006, (%rdi) # unsharpKernel_s0[t2236] {dead: []} = 54006
	movq $6, -88(%rbp) # t2241 {dead: []} = 6
	movq -88(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	movq $4433, (%rdi) # unsharpKernel_s0[t2241] {dead: []} = 4433
	movq $3, -24(%rbp) # center_s42 {dead: []} = 3
	movq $0, _global_kernel_sum(%rip) # kernel_sum_s0 {dead: []} = 0
	movq $0, -32(%rbp) # i_s42 {dead: []} = 0
	jmp _nop_2255
_nop_2255:
	jmp _end_of_block_2203
_end_of_block_2203:
	jmp _conditional_2207
_conditional_2207:
_block_2287:
	movq $2, -40(%rbp) # t2261 {dead: []} = 2
	# t2259 {dead: []} = center_s42 * t2261 {canonical: (center_s42) * (2)}
	movq -24(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -48(%rbp)
	movq $1, -56(%rbp) # t2271 {dead: []} = 1
	# t2258 {dead: []} = t2259 + t2271 {canonical: ((center_s42) * (2)) + (1)}
	movq -48(%rbp), %rax
	addq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	# t2256 {dead: []} = i_s42 < t2258 {canonical: (i_s42) < (((center_s42) * (2)) + (1))}
	movq -32(%rbp), %rax # t2256 {dead: []} = i_s42 < t2258 {canonical: (i_s42) < (((center_s42) * (2)) + (1))}
	cmpq -64(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -72(%rbp)
	jmp _nop_2289
_nop_2289:
	jmp _end_of_conditional_2207
_end_of_conditional_2207:
	cmpq $1, -72(%rbp) # true = t2256
	jne _return_2208 # ifFalse
	jmp _block_2206 # ifTrue
_block_2206:
_block_2307:
	movq -32(%rbp), %rax # t2295 {dead: []} = i_s42 {canonical: i_s42}
	movq %rax, -40(%rbp)
	movq -40(%rbp), %rax # t2294 {dead: []} = unsharpKernel_s0[t2295]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2294 {dead: []} = unsharpKernel_s0[t2295]
	movq %rdx, -48(%rbp)
	# kernel_sum_s0 {dead: []} = kernel_sum_s0 + t2294 {canonical: (kernel_sum_s0) + (unsharpKernel_s0[i_s42])}
	movq _global_kernel_sum(%rip), %rax
	addq -48(%rbp), %rax
	movq %rax, _global_kernel_sum(%rip)
	jmp _nop_2308
_nop_2308:
	jmp _end_of_block_2206
_end_of_block_2206:
	jmp _block_2204
_block_2204:
_block_2316:
	movq $1, -40(%rbp) # t2312 {dead: []} = 1
	movq -40(%rbp), %rax # i_s42 {dead: []} += t2312
	addq %rax, -32(%rbp)
	jmp _nop_2317
_nop_2317:
	jmp _end_of_block_2204
_end_of_block_2204:
	jmp _conditional_2207
_return_2208:
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
_block_2334:
_block_2406:
	# read_file_s0[]
	call _method_read_file
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# start_caliper_s0[]
	call start_caliper
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# levels_s0[]
	call _method_levels
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# convert2HSV_s0[]
	call _method_convert2HSV
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# createKernel_s0[]
	call _method_createKernel
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# createUnsharpMaskH_s0[]
	call _method_createUnsharpMaskH
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq $4, -8(%rbp) # t2358 {dead: []} = 4
	movq -8(%rbp), %rax # t2357 {dead: []} = -t2358
	negq %rax # -t2358
	movq %rax, -16(%rbp)
	movq $360, -24(%rbp) # t2363 {dead: []} = 360
	# sharpenH_s0[t2357, t2363]
	movq -24(%rbp), %rsi
	movq -16(%rbp), %rdi
	call _method_sharpenH
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# createUnsharpMaskS_s0[]
	call _method_createUnsharpMaskS
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq $4, -32(%rbp) # t2373 {dead: []} = 4
	movq -32(%rbp), %rax # t2372 {dead: []} = -t2373
	negq %rax # -t2373
	movq %rax, -40(%rbp)
	movq $1024, -48(%rbp) # t2378 {dead: []} = 1024
	# sharpenS_s0[t2372, t2378]
	movq -48(%rbp), %rsi
	movq -40(%rbp), %rdi
	call _method_sharpenS
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# createUnsharpMaskV_s0[]
	call _method_createUnsharpMaskV
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq $4, -56(%rbp) # t2388 {dead: []} = 4
	movq -56(%rbp), %rax # t2387 {dead: []} = -t2388
	negq %rax # -t2388
	movq %rax, -64(%rbp)
	movq $1024, -72(%rbp) # t2393 {dead: []} = 1024
	# sharpenV_s0[t2387, t2393]
	movq -72(%rbp), %rsi
	movq -64(%rbp), %rdi
	call _method_sharpenV
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# convert2RGB_s0[]
	call _method_convert2RGB
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# end_caliper_s0[]
	call end_caliper
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# write_file_s0[]
	call _method_write_file
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	jmp _nop_2407
_nop_2407:
	jmp _end_of_block_2334
_end_of_block_2334:
	jmp _return_2335
_return_2335:
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

_method_createUnsharpMaskS:
	enter $(944), $0
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
	movq $0, -248(%rbp)
	movq $0, -256(%rbp)
	movq $0, -264(%rbp)
	movq $0, -272(%rbp)
	movq $0, -280(%rbp)
	movq $0, -288(%rbp)
	movq $0, -296(%rbp)
	movq $0, -304(%rbp)
	movq $0, -312(%rbp)
	movq $0, -320(%rbp)
	movq $0, -328(%rbp)
	movq $0, -336(%rbp)
	movq $0, -344(%rbp)
	movq $0, -352(%rbp)
	movq $0, -360(%rbp)
	movq $0, -368(%rbp)
	movq $0, -376(%rbp)
	movq $0, -384(%rbp)
	movq $0, -392(%rbp)
	movq $0, -400(%rbp)
	movq $0, -408(%rbp)
	movq $0, -416(%rbp)
	movq $0, -424(%rbp)
	movq $0, -432(%rbp)
	movq $0, -440(%rbp)
	movq $0, -448(%rbp)
	movq $0, -456(%rbp)
	movq $0, -464(%rbp)
	movq $0, -472(%rbp)
	movq $0, -480(%rbp)
	movq $0, -488(%rbp)
	movq $0, -496(%rbp)
	movq $0, -504(%rbp)
	movq $0, -512(%rbp)
	movq $0, -520(%rbp)
	movq $0, -528(%rbp)
	movq $0, -536(%rbp)
	movq $0, -544(%rbp)
	movq $0, -552(%rbp)
	movq $0, -560(%rbp)
	movq $0, -568(%rbp)
	movq $0, -576(%rbp)
	movq $0, -584(%rbp)
	movq $0, -592(%rbp)
	movq $0, -600(%rbp)
	movq $0, -608(%rbp)
	movq $0, -616(%rbp)
	movq $0, -624(%rbp)
	movq $0, -632(%rbp)
	movq $0, -640(%rbp)
	movq $0, -648(%rbp)
	movq $0, -656(%rbp)
	movq $0, -664(%rbp)
	movq $0, -672(%rbp)
	movq $0, -680(%rbp)
	movq $0, -688(%rbp)
	movq $0, -696(%rbp)
	movq $0, -704(%rbp)
	movq $0, -712(%rbp)
	movq $0, -720(%rbp)
	movq $0, -728(%rbp)
	movq $0, -736(%rbp)
	movq $0, -744(%rbp)
	movq $0, -752(%rbp)
	movq $0, -760(%rbp)
	movq $0, -768(%rbp)
	movq $0, -776(%rbp)
	movq $0, -784(%rbp)
	movq $0, -792(%rbp)
	movq $0, -800(%rbp)
	movq $0, -808(%rbp)
	movq $0, -816(%rbp)
	movq $0, -824(%rbp)
	movq $0, -832(%rbp)
	movq $0, -840(%rbp)
	movq $0, -848(%rbp)
	movq $0, -856(%rbp)
	movq $0, -864(%rbp)
	movq $0, -872(%rbp)
	movq $0, -880(%rbp)
	movq $0, -888(%rbp)
	movq $0, -896(%rbp)
	movq $0, -904(%rbp)
	movq $0, -912(%rbp)
	movq $0, -920(%rbp)
	movq $0, -928(%rbp)
	movq $0, -936(%rbp)
	movq $0, -944(%rbp)
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
_block_2413:
_block_2490:
	movq $3, -48(%rbp) # center_s55 {dead: []} = 3
	movq $0, -32(%rbp) # r_s55 {dead: []} = 0
	jmp _nop_2491
_nop_2491:
	jmp _end_of_block_2413
_end_of_block_2413:
	jmp _conditional_2442
_conditional_2442:
_block_2501:
	# t2492 {dead: []} = r_s55 < rows_s0 {canonical: (r_s55) < (rows_s0)}
	movq -32(%rbp), %rax # t2492 {dead: []} = r_s55 < rows_s0 {canonical: (r_s55) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_2503
_nop_2503:
	jmp _end_of_conditional_2442
_end_of_conditional_2442:
	cmpq $1, -88(%rbp) # true = t2492
	jne _block_2445 # ifFalse
	jmp _block_2417 # ifTrue
_block_2417:
_block_2508:
	movq $0, -40(%rbp) # c_s55 {dead: []} = 0
	jmp _nop_2509
_nop_2509:
	jmp _end_of_block_2417
_end_of_block_2417:
	jmp _conditional_2421
_conditional_2421:
_block_2519:
	# t2510 {dead: []} = c_s55 < center_s55 {canonical: (c_s55) < (center_s55)}
	movq -40(%rbp), %rax # t2510 {dead: []} = c_s55 < center_s55 {canonical: (c_s55) < (center_s55)}
	cmpq -48(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_2521
_nop_2521:
	jmp _end_of_conditional_2421
_end_of_conditional_2421:
	cmpq $1, -88(%rbp) # true = t2510
	jne _block_2423 # ifFalse
	jmp _block_2420 # ifTrue
_block_2420:
_block_2589:
	movq $731, -88(%rbp) # t2527 {dead: []} = 731
	# t2525 {dead: []} = r_s55 * t2527 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t2524 {dead: []} = t2525 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2193, -112(%rbp) # t2549 {dead: []} = 2193
	# t2547 {dead: []} = r_s55 * t2549 {canonical: (r_s55) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t2561 {dead: []} = 3
	# t2559 {dead: []} = c_s55 * t2561 {canonical: (c_s55) * (3)}
	movq -40(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t2546 {dead: []} = t2547 + t2559 {canonical: ((r_s55) * (2193)) + ((c_s55) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $1, -152(%rbp) # t2578 {dead: []} = 1
	# t2545 {dead: []} = t2546 + t2578 {canonical: (((r_s55) * (2193)) + ((c_s55) * (3))) + (1)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -160(%rbp), %rax # unsharpMask_s0[t2524] {dead: []} = image_s0[t2545]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # unsharpMask_s0[t2524] {dead: []} = image_s0[t2545]
	movq %rdx, (%rdi)
	jmp _nop_2590
_nop_2590:
	jmp _end_of_block_2420
_end_of_block_2420:
	jmp _block_2418
_block_2418:
_block_2598:
	movq $1, -88(%rbp) # t2594 {dead: []} = 1
	movq -88(%rbp), %rax # c_s55 {dead: []} += t2594
	addq %rax, -40(%rbp)
	jmp _nop_2599
_nop_2599:
	jmp _end_of_block_2418
_end_of_block_2418:
	jmp _conditional_2421
_block_2423:
_block_2605:
	movq -48(%rbp), %rax # c_s55 {dead: []} = center_s55 {canonical: center_s55}
	movq %rax, -40(%rbp)
	jmp _nop_2606
_nop_2606:
	jmp _end_of_block_2423
_end_of_block_2423:
	jmp _conditional_2435
_conditional_2435:
_block_2625:
	# t2609 {dead: []} = cols_s0 - center_s55 {canonical: (cols_s0) - (center_s55)}
	movq _global_cols(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -88(%rbp)
	# t2607 {dead: []} = c_s55 < t2609 {canonical: (c_s55) < ((cols_s0) - (center_s55))}
	movq -40(%rbp), %rax # t2607 {dead: []} = c_s55 < t2609 {canonical: (c_s55) < ((cols_s0) - (center_s55))}
	cmpq -88(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -96(%rbp)
	jmp _nop_2627
_nop_2627:
	jmp _end_of_conditional_2435
_end_of_conditional_2435:
	cmpq $1, -96(%rbp) # true = t2607
	jne _block_2437 # ifFalse
	jmp _block_2434 # ifTrue
_block_2434:
_block_3286:
	movq $731, -88(%rbp) # t2633 {dead: []} = 731
	# t2631 {dead: []} = r_s55 * t2633 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t2630 {dead: []} = t2631 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # unsharpMask_s0[t2630] {dead: []} = 0
	movq $731, -112(%rbp) # t2656 {dead: []} = 731
	# t2654 {dead: []} = r_s55 * t2656 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	# t2653 {dead: []} = t2654 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -120(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -128(%rbp)
	movq $2193, -136(%rbp) # t2680 {dead: []} = 2193
	# t2678 {dead: []} = r_s55 * t2680 {canonical: (r_s55) * (2193)}
	movq -32(%rbp), %rax
	imulq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $3, -152(%rbp) # t2691 {dead: []} = 3
	# t2690 {dead: []} = t2691 * c_s55 {canonical: (3) * (c_s55)}
	movq -152(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -160(%rbp)
	# t2677 {dead: []} = t2678 + t2690 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	movq -144(%rbp), %rax
	addq -160(%rbp), %rax
	movq %rax, -168(%rbp)
	movq $8, -176(%rbp) # t2709 {dead: []} = 8
	# t2676 {dead: []} = t2677 - t2709 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) - (8)}
	movq -168(%rbp), %rax
	subq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	movq -184(%rbp), %rax # t2675 {dead: []} = image_s0[t2676]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2675 {dead: []} = image_s0[t2676]
	movq %rdx, -192(%rbp)
	movq $0, -200(%rbp) # t2722 {dead: []} = 0
	movq -200(%rbp), %rax # t2721 {dead: []} = unsharpKernel_s0[t2722]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2721 {dead: []} = unsharpKernel_s0[t2722]
	movq %rdx, -208(%rbp)
	# t2674 {dead: []} = t2675 * t2721 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) - (8)]) * (unsharpKernel_s0[0])}
	movq -192(%rbp), %rax
	imulq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	movq -128(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -216(%rbp), %rax # unsharpMask_s0[t2653] {dead: []} += t2674
	addq %rax, (%rdi)
	movq $731, -224(%rbp) # t2739 {dead: []} = 731
	# t2737 {dead: []} = r_s55 * t2739 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	# t2736 {dead: []} = t2737 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -232(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -240(%rbp)
	movq $2193, -248(%rbp) # t2763 {dead: []} = 2193
	# t2761 {dead: []} = r_s55 * t2763 {canonical: (r_s55) * (2193)}
	movq -32(%rbp), %rax
	imulq -248(%rbp), %rax
	movq %rax, -256(%rbp)
	movq $3, -264(%rbp) # t2774 {dead: []} = 3
	# t2773 {dead: []} = t2774 * c_s55 {canonical: (3) * (c_s55)}
	movq -264(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -272(%rbp)
	# t2760 {dead: []} = t2761 + t2773 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	movq -256(%rbp), %rax
	addq -272(%rbp), %rax
	movq %rax, -280(%rbp)
	movq $5, -288(%rbp) # t2792 {dead: []} = 5
	# t2759 {dead: []} = t2760 - t2792 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) - (5)}
	movq -280(%rbp), %rax
	subq -288(%rbp), %rax
	movq %rax, -296(%rbp)
	movq -296(%rbp), %rax # t2758 {dead: []} = image_s0[t2759]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2758 {dead: []} = image_s0[t2759]
	movq %rdx, -304(%rbp)
	movq $1, -312(%rbp) # t2805 {dead: []} = 1
	movq -312(%rbp), %rax # t2804 {dead: []} = unsharpKernel_s0[t2805]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2804 {dead: []} = unsharpKernel_s0[t2805]
	movq %rdx, -320(%rbp)
	# t2757 {dead: []} = t2758 * t2804 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) - (5)]) * (unsharpKernel_s0[1])}
	movq -304(%rbp), %rax
	imulq -320(%rbp), %rax
	movq %rax, -328(%rbp)
	movq -240(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -328(%rbp), %rax # unsharpMask_s0[t2736] {dead: []} += t2757
	addq %rax, (%rdi)
	movq $731, -336(%rbp) # t2822 {dead: []} = 731
	# t2820 {dead: []} = r_s55 * t2822 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -336(%rbp), %rax
	movq %rax, -344(%rbp)
	# t2819 {dead: []} = t2820 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -344(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -352(%rbp)
	movq $2193, -360(%rbp) # t2846 {dead: []} = 2193
	# t2844 {dead: []} = r_s55 * t2846 {canonical: (r_s55) * (2193)}
	movq -32(%rbp), %rax
	imulq -360(%rbp), %rax
	movq %rax, -368(%rbp)
	movq $3, -376(%rbp) # t2857 {dead: []} = 3
	# t2856 {dead: []} = t2857 * c_s55 {canonical: (3) * (c_s55)}
	movq -376(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -384(%rbp)
	# t2843 {dead: []} = t2844 + t2856 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	movq -368(%rbp), %rax
	addq -384(%rbp), %rax
	movq %rax, -392(%rbp)
	movq $2, -400(%rbp) # t2875 {dead: []} = 2
	# t2842 {dead: []} = t2843 - t2875 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) - (2)}
	movq -392(%rbp), %rax
	subq -400(%rbp), %rax
	movq %rax, -408(%rbp)
	movq -408(%rbp), %rax # t2841 {dead: []} = image_s0[t2842]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2841 {dead: []} = image_s0[t2842]
	movq %rdx, -416(%rbp)
	movq $2, -424(%rbp) # t2888 {dead: []} = 2
	movq -424(%rbp), %rax # t2887 {dead: []} = unsharpKernel_s0[t2888]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2887 {dead: []} = unsharpKernel_s0[t2888]
	movq %rdx, -432(%rbp)
	# t2840 {dead: []} = t2841 * t2887 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) - (2)]) * (unsharpKernel_s0[2])}
	movq -416(%rbp), %rax
	imulq -432(%rbp), %rax
	movq %rax, -440(%rbp)
	movq -352(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -440(%rbp), %rax # unsharpMask_s0[t2819] {dead: []} += t2840
	addq %rax, (%rdi)
	movq $731, -448(%rbp) # t2905 {dead: []} = 731
	# t2903 {dead: []} = r_s55 * t2905 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -448(%rbp), %rax
	movq %rax, -456(%rbp)
	# t2902 {dead: []} = t2903 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -456(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -464(%rbp)
	movq $2193, -472(%rbp) # t2929 {dead: []} = 2193
	# t2927 {dead: []} = r_s55 * t2929 {canonical: (r_s55) * (2193)}
	movq -32(%rbp), %rax
	imulq -472(%rbp), %rax
	movq %rax, -480(%rbp)
	movq $3, -488(%rbp) # t2940 {dead: []} = 3
	# t2939 {dead: []} = t2940 * c_s55 {canonical: (3) * (c_s55)}
	movq -488(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -496(%rbp)
	# t2926 {dead: []} = t2927 + t2939 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	movq -480(%rbp), %rax
	addq -496(%rbp), %rax
	movq %rax, -504(%rbp)
	movq $1, -512(%rbp) # t2958 {dead: []} = 1
	# t2925 {dead: []} = t2926 + t2958 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) + (1)}
	movq -504(%rbp), %rax
	addq -512(%rbp), %rax
	movq %rax, -520(%rbp)
	movq -520(%rbp), %rax # t2924 {dead: []} = image_s0[t2925]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2924 {dead: []} = image_s0[t2925]
	movq %rdx, -528(%rbp)
	movq $3, -536(%rbp) # t2971 {dead: []} = 3
	movq -536(%rbp), %rax # t2970 {dead: []} = unsharpKernel_s0[t2971]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t2970 {dead: []} = unsharpKernel_s0[t2971]
	movq %rdx, -544(%rbp)
	# t2923 {dead: []} = t2924 * t2970 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) + (1)]) * (unsharpKernel_s0[3])}
	movq -528(%rbp), %rax
	imulq -544(%rbp), %rax
	movq %rax, -552(%rbp)
	movq -464(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -552(%rbp), %rax # unsharpMask_s0[t2902] {dead: []} += t2923
	addq %rax, (%rdi)
	movq $731, -560(%rbp) # t2988 {dead: []} = 731
	# t2986 {dead: []} = r_s55 * t2988 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -560(%rbp), %rax
	movq %rax, -568(%rbp)
	# t2985 {dead: []} = t2986 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -568(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -576(%rbp)
	movq $2193, -584(%rbp) # t3012 {dead: []} = 2193
	# t3010 {dead: []} = r_s55 * t3012 {canonical: (r_s55) * (2193)}
	movq -32(%rbp), %rax
	imulq -584(%rbp), %rax
	movq %rax, -592(%rbp)
	movq $3, -600(%rbp) # t3023 {dead: []} = 3
	# t3022 {dead: []} = t3023 * c_s55 {canonical: (3) * (c_s55)}
	movq -600(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -608(%rbp)
	# t3009 {dead: []} = t3010 + t3022 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	movq -592(%rbp), %rax
	addq -608(%rbp), %rax
	movq %rax, -616(%rbp)
	movq $4, -624(%rbp) # t3041 {dead: []} = 4
	# t3008 {dead: []} = t3009 + t3041 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) + (4)}
	movq -616(%rbp), %rax
	addq -624(%rbp), %rax
	movq %rax, -632(%rbp)
	movq -632(%rbp), %rax # t3007 {dead: []} = image_s0[t3008]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3007 {dead: []} = image_s0[t3008]
	movq %rdx, -640(%rbp)
	movq $4, -648(%rbp) # t3054 {dead: []} = 4
	movq -648(%rbp), %rax # t3053 {dead: []} = unsharpKernel_s0[t3054]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3053 {dead: []} = unsharpKernel_s0[t3054]
	movq %rdx, -656(%rbp)
	# t3006 {dead: []} = t3007 * t3053 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) + (4)]) * (unsharpKernel_s0[4])}
	movq -640(%rbp), %rax
	imulq -656(%rbp), %rax
	movq %rax, -664(%rbp)
	movq -576(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -664(%rbp), %rax # unsharpMask_s0[t2985] {dead: []} += t3006
	addq %rax, (%rdi)
	movq $731, -672(%rbp) # t3071 {dead: []} = 731
	# t3069 {dead: []} = r_s55 * t3071 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -672(%rbp), %rax
	movq %rax, -680(%rbp)
	# t3068 {dead: []} = t3069 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -680(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -688(%rbp)
	movq $2193, -696(%rbp) # t3095 {dead: []} = 2193
	# t3093 {dead: []} = r_s55 * t3095 {canonical: (r_s55) * (2193)}
	movq -32(%rbp), %rax
	imulq -696(%rbp), %rax
	movq %rax, -704(%rbp)
	movq $3, -712(%rbp) # t3106 {dead: []} = 3
	# t3105 {dead: []} = t3106 * c_s55 {canonical: (3) * (c_s55)}
	movq -712(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -720(%rbp)
	# t3092 {dead: []} = t3093 + t3105 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	movq -704(%rbp), %rax
	addq -720(%rbp), %rax
	movq %rax, -728(%rbp)
	movq $7, -736(%rbp) # t3124 {dead: []} = 7
	# t3091 {dead: []} = t3092 + t3124 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) + (7)}
	movq -728(%rbp), %rax
	addq -736(%rbp), %rax
	movq %rax, -744(%rbp)
	movq -744(%rbp), %rax # t3090 {dead: []} = image_s0[t3091]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3090 {dead: []} = image_s0[t3091]
	movq %rdx, -752(%rbp)
	movq $5, -760(%rbp) # t3137 {dead: []} = 5
	movq -760(%rbp), %rax # t3136 {dead: []} = unsharpKernel_s0[t3137]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3136 {dead: []} = unsharpKernel_s0[t3137]
	movq %rdx, -768(%rbp)
	# t3089 {dead: []} = t3090 * t3136 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) + (7)]) * (unsharpKernel_s0[5])}
	movq -752(%rbp), %rax
	imulq -768(%rbp), %rax
	movq %rax, -776(%rbp)
	movq -688(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -776(%rbp), %rax # unsharpMask_s0[t3068] {dead: []} += t3089
	addq %rax, (%rdi)
	movq $731, -784(%rbp) # t3154 {dead: []} = 731
	# t3152 {dead: []} = r_s55 * t3154 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -784(%rbp), %rax
	movq %rax, -792(%rbp)
	# t3151 {dead: []} = t3152 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -792(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -800(%rbp)
	movq $2193, -808(%rbp) # t3178 {dead: []} = 2193
	# t3176 {dead: []} = r_s55 * t3178 {canonical: (r_s55) * (2193)}
	movq -32(%rbp), %rax
	imulq -808(%rbp), %rax
	movq %rax, -816(%rbp)
	movq $3, -824(%rbp) # t3189 {dead: []} = 3
	# t3188 {dead: []} = t3189 * c_s55 {canonical: (3) * (c_s55)}
	movq -824(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -832(%rbp)
	# t3175 {dead: []} = t3176 + t3188 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	movq -816(%rbp), %rax
	addq -832(%rbp), %rax
	movq %rax, -840(%rbp)
	movq $10, -848(%rbp) # t3207 {dead: []} = 10
	# t3174 {dead: []} = t3175 + t3207 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) + (10)}
	movq -840(%rbp), %rax
	addq -848(%rbp), %rax
	movq %rax, -856(%rbp)
	movq -856(%rbp), %rax # t3173 {dead: []} = image_s0[t3174]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3173 {dead: []} = image_s0[t3174]
	movq %rdx, -864(%rbp)
	movq $6, -872(%rbp) # t3220 {dead: []} = 6
	movq -872(%rbp), %rax # t3219 {dead: []} = unsharpKernel_s0[t3220]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3219 {dead: []} = unsharpKernel_s0[t3220]
	movq %rdx, -880(%rbp)
	# t3172 {dead: []} = t3173 * t3219 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) + (10)]) * (unsharpKernel_s0[6])}
	movq -864(%rbp), %rax
	imulq -880(%rbp), %rax
	movq %rax, -888(%rbp)
	movq -800(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -888(%rbp), %rax # unsharpMask_s0[t3151] {dead: []} += t3172
	addq %rax, (%rdi)
	movq $731, -896(%rbp) # t3237 {dead: []} = 731
	# t3235 {dead: []} = r_s55 * t3237 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -896(%rbp), %rax
	movq %rax, -904(%rbp)
	# t3234 {dead: []} = t3235 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -904(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -912(%rbp)
	movq $731, -920(%rbp) # t3259 {dead: []} = 731
	# t3257 {dead: []} = r_s55 * t3259 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -920(%rbp), %rax
	movq %rax, -928(%rbp)
	# t3256 {dead: []} = t3257 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -928(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -936(%rbp)
	movq -936(%rbp), %rax # t3255 {dead: []} = unsharpMask_s0[t3256]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3255 {dead: []} = unsharpMask_s0[t3256]
	movq %rdx, -944(%rbp)
	movq -912(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	# unsharpMask_s0[t3234] {dead: []} = t3255 / kernel_sum_s0 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) / (kernel_sum_s0)}
	movq -944(%rbp), %rax
	cqto
	idivq _global_kernel_sum(%rip)
	movq %rax, (%rdi)
	jmp _nop_3287
_nop_3287:
	jmp _end_of_block_2434
_end_of_block_2434:
	jmp _block_2424
_block_2424:
_block_3295:
	movq $1, -88(%rbp) # t3291 {dead: []} = 1
	movq -88(%rbp), %rax # c_s55 {dead: []} += t3291
	addq %rax, -40(%rbp)
	jmp _nop_3296
_nop_3296:
	jmp _end_of_block_2424
_end_of_block_2424:
	jmp _conditional_2435
_block_2437:
_block_3308:
	# c_s55 {dead: []} = cols_s0 - center_s55 {canonical: (cols_s0) - (center_s55)}
	movq _global_cols(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -40(%rbp)
	jmp _nop_3309
_nop_3309:
	jmp _end_of_block_2437
_end_of_block_2437:
	jmp _conditional_2441
_conditional_2441:
_block_3319:
	# t3310 {dead: []} = c_s55 < cols_s0 {canonical: (c_s55) < (cols_s0)}
	movq -40(%rbp), %rax # t3310 {dead: []} = c_s55 < cols_s0 {canonical: (c_s55) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_3321
_nop_3321:
	jmp _end_of_conditional_2441
_end_of_conditional_2441:
	cmpq $1, -88(%rbp) # true = t3310
	jne _block_2414 # ifFalse
	jmp _block_2440 # ifTrue
_block_2440:
_block_3389:
	movq $731, -88(%rbp) # t3327 {dead: []} = 731
	# t3325 {dead: []} = r_s55 * t3327 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t3324 {dead: []} = t3325 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2193, -112(%rbp) # t3349 {dead: []} = 2193
	# t3347 {dead: []} = r_s55 * t3349 {canonical: (r_s55) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t3361 {dead: []} = 3
	# t3359 {dead: []} = c_s55 * t3361 {canonical: (c_s55) * (3)}
	movq -40(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t3346 {dead: []} = t3347 + t3359 {canonical: ((r_s55) * (2193)) + ((c_s55) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $1, -152(%rbp) # t3378 {dead: []} = 1
	# t3345 {dead: []} = t3346 + t3378 {canonical: (((r_s55) * (2193)) + ((c_s55) * (3))) + (1)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -160(%rbp), %rax # unsharpMask_s0[t3324] {dead: []} = image_s0[t3345]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # unsharpMask_s0[t3324] {dead: []} = image_s0[t3345]
	movq %rdx, (%rdi)
	jmp _nop_3390
_nop_3390:
	jmp _end_of_block_2440
_end_of_block_2440:
	jmp _block_2438
_block_2438:
_block_3398:
	movq $1, -88(%rbp) # t3394 {dead: []} = 1
	movq -88(%rbp), %rax # c_s55 {dead: []} += t3394
	addq %rax, -40(%rbp)
	jmp _nop_3399
_nop_3399:
	jmp _end_of_block_2438
_end_of_block_2438:
	jmp _conditional_2441
_block_2414:
_block_3407:
	movq $1, -88(%rbp) # t3403 {dead: []} = 1
	movq -88(%rbp), %rax # r_s55 {dead: []} += t3403
	addq %rax, -32(%rbp)
	jmp _nop_3408
_nop_3408:
	jmp _end_of_block_2414
_end_of_block_2414:
	jmp _conditional_2442
_block_2445:
_block_3432:
	movq -48(%rbp), %rax # t3411 {dead: []} = center_s55 {canonical: center_s55}
	movq %rax, -88(%rbp)
	movq -48(%rbp), %rax # t3416 {dead: []} = center_s55 {canonical: center_s55}
	movq %rax, -96(%rbp)
	movq -96(%rbp), %rax # t3415 {dead: []} = unsharpKernel_s0[t3416]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3415 {dead: []} = unsharpKernel_s0[t3416]
	movq %rdx, -104(%rbp)
	movq -88(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	# unsharpKernel_s0[t3411] {dead: []} = t3415 - kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s55]) - (kernel_sum_s0)}
	movq -104(%rbp), %rax
	subq _global_kernel_sum(%rip), %rax
	movq %rax, (%rdi)
	movq $0, -40(%rbp) # c_s55 {dead: []} = 0
	jmp _nop_3433
_nop_3433:
	jmp _end_of_block_2445
_end_of_block_2445:
	jmp _conditional_2480
_conditional_2480:
_block_3443:
	# t3434 {dead: []} = c_s55 < cols_s0 {canonical: (c_s55) < (cols_s0)}
	movq -40(%rbp), %rax # t3434 {dead: []} = c_s55 < cols_s0 {canonical: (c_s55) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_3445
_nop_3445:
	jmp _end_of_conditional_2480
_end_of_conditional_2480:
	cmpq $1, -88(%rbp) # true = t3434
	jne _block_2481 # ifFalse
	jmp _block_2452 # ifTrue
_block_2452:
_block_3487:
	movq -40(%rbp), %rax # t3449 {dead: []} = c_s55 {canonical: c_s55}
	movq %rax, -88(%rbp)
	movq -88(%rbp), %rax # m1_s60 {dead: []} = unsharpMask_s0[t3449]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m1_s60 {dead: []} = unsharpMask_s0[t3449]
	movq %rdx, -56(%rbp)
	movq $731, -96(%rbp) # t3458 {dead: []} = 731
	# t3456 {dead: []} = c_s55 + t3458 {canonical: (c_s55) + (731)}
	movq -40(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # m2_s60 {dead: []} = unsharpMask_s0[t3456]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m2_s60 {dead: []} = unsharpMask_s0[t3456]
	movq %rdx, -64(%rbp)
	movq $1462, -112(%rbp) # t3473 {dead: []} = 1462
	# t3471 {dead: []} = c_s55 + t3473 {canonical: (c_s55) + (1462)}
	movq -40(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rax # m3_s60 {dead: []} = unsharpMask_s0[t3471]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m3_s60 {dead: []} = unsharpMask_s0[t3471]
	movq %rdx, -72(%rbp)
	movq $0, -32(%rbp) # r_s55 {dead: []} = 0
	jmp _nop_3488
_nop_3488:
	jmp _end_of_block_2452
_end_of_block_2452:
	jmp _conditional_2456
_conditional_2456:
_block_3498:
	# t3489 {dead: []} = r_s55 < center_s55 {canonical: (r_s55) < (center_s55)}
	movq -32(%rbp), %rax # t3489 {dead: []} = r_s55 < center_s55 {canonical: (r_s55) < (center_s55)}
	cmpq -48(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_3500
_nop_3500:
	jmp _end_of_conditional_2456
_end_of_conditional_2456:
	cmpq $1, -88(%rbp) # true = t3489
	jne _block_2458 # ifFalse
	jmp _block_2455 # ifTrue
_block_2455:
_block_3525:
	movq $731, -88(%rbp) # t3506 {dead: []} = 731
	# t3504 {dead: []} = r_s55 * t3506 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t3503 {dead: []} = t3504 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # unsharpMask_s0[t3503] {dead: []} = 0
	jmp _nop_3526
_nop_3526:
	jmp _end_of_block_2455
_end_of_block_2455:
	jmp _block_2453
_block_2453:
_block_3534:
	movq $1, -88(%rbp) # t3530 {dead: []} = 1
	movq -88(%rbp), %rax # r_s55 {dead: []} += t3530
	addq %rax, -32(%rbp)
	jmp _nop_3535
_nop_3535:
	jmp _end_of_block_2453
_end_of_block_2453:
	jmp _conditional_2456
_block_2458:
_block_3541:
	movq -48(%rbp), %rax # r_s55 {dead: []} = center_s55 {canonical: center_s55}
	movq %rax, -32(%rbp)
	jmp _nop_3542
_nop_3542:
	jmp _end_of_block_2458
_end_of_block_2458:
	jmp _conditional_2473
_conditional_2473:
_block_3561:
	# t3545 {dead: []} = rows_s0 - center_s55 {canonical: (rows_s0) - (center_s55)}
	movq _global_rows(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -88(%rbp)
	# t3543 {dead: []} = r_s55 < t3545 {canonical: (r_s55) < ((rows_s0) - (center_s55))}
	movq -32(%rbp), %rax # t3543 {dead: []} = r_s55 < t3545 {canonical: (r_s55) < ((rows_s0) - (center_s55))}
	cmpq -88(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -96(%rbp)
	jmp _nop_3563
_nop_3563:
	jmp _end_of_conditional_2473
_end_of_conditional_2473:
	cmpq $1, -96(%rbp) # true = t3543
	jne _block_2475 # ifFalse
	jmp _block_2472 # ifTrue
_block_2472:
_block_4105:
	movq $0, -80(%rbp) # dot_s62 {dead: []} = 0
	movq $731, -88(%rbp) # t3575 {dead: []} = 731
	# t3573 {dead: []} = r_s55 * t3575 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t3572 {dead: []} = t3573 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # t3571 {dead: []} = unsharpMask_s0[t3572]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3571 {dead: []} = unsharpMask_s0[t3572]
	movq %rdx, -112(%rbp)
	movq $0, -120(%rbp) # t3598 {dead: []} = 0
	movq -120(%rbp), %rax # t3597 {dead: []} = unsharpKernel_s0[t3598]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3597 {dead: []} = unsharpKernel_s0[t3598]
	movq %rdx, -128(%rbp)
	# t3595 {dead: []} = m1_s60 * t3597 {canonical: (m1_s60) * (unsharpKernel_s0[0])}
	movq -56(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t3570 {dead: []} = t3571 + t3595 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((m1_s60) * (unsharpKernel_s0[0]))}
	movq -112(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq -144(%rbp), %rax # dot_s62 {dead: []} += t3570
	addq %rax, -80(%rbp)
	movq $731, -152(%rbp) # t3625 {dead: []} = 731
	# t3623 {dead: []} = r_s55 * t3625 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	# t3622 {dead: []} = t3623 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -160(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -168(%rbp)
	movq -168(%rbp), %rax # t3621 {dead: []} = unsharpMask_s0[t3622]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3621 {dead: []} = unsharpMask_s0[t3622]
	movq %rdx, -176(%rbp)
	movq $1, -184(%rbp) # t3648 {dead: []} = 1
	movq -184(%rbp), %rax # t3647 {dead: []} = unsharpKernel_s0[t3648]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3647 {dead: []} = unsharpKernel_s0[t3648]
	movq %rdx, -192(%rbp)
	# t3645 {dead: []} = m2_s60 * t3647 {canonical: (m2_s60) * (unsharpKernel_s0[1])}
	movq -64(%rbp), %rax
	imulq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	# t3620 {dead: []} = t3621 + t3645 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((m2_s60) * (unsharpKernel_s0[1]))}
	movq -176(%rbp), %rax
	addq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	movq -208(%rbp), %rax # dot_s62 {dead: []} += t3620
	addq %rax, -80(%rbp)
	movq $731, -216(%rbp) # t3675 {dead: []} = 731
	# t3673 {dead: []} = r_s55 * t3675 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -216(%rbp), %rax
	movq %rax, -224(%rbp)
	# t3672 {dead: []} = t3673 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -224(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -232(%rbp)
	movq -232(%rbp), %rax # t3671 {dead: []} = unsharpMask_s0[t3672]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3671 {dead: []} = unsharpMask_s0[t3672]
	movq %rdx, -240(%rbp)
	movq $2, -248(%rbp) # t3698 {dead: []} = 2
	movq -248(%rbp), %rax # t3697 {dead: []} = unsharpKernel_s0[t3698]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3697 {dead: []} = unsharpKernel_s0[t3698]
	movq %rdx, -256(%rbp)
	# t3695 {dead: []} = m3_s60 * t3697 {canonical: (m3_s60) * (unsharpKernel_s0[2])}
	movq -72(%rbp), %rax
	imulq -256(%rbp), %rax
	movq %rax, -264(%rbp)
	# t3670 {dead: []} = t3671 + t3695 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((m3_s60) * (unsharpKernel_s0[2]))}
	movq -240(%rbp), %rax
	addq -264(%rbp), %rax
	movq %rax, -272(%rbp)
	movq -272(%rbp), %rax # dot_s62 {dead: []} += t3670
	addq %rax, -80(%rbp)
	movq $731, -280(%rbp) # t3725 {dead: []} = 731
	# t3723 {dead: []} = r_s55 * t3725 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -280(%rbp), %rax
	movq %rax, -288(%rbp)
	# t3722 {dead: []} = t3723 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -288(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -296(%rbp)
	movq -296(%rbp), %rax # t3721 {dead: []} = unsharpMask_s0[t3722]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3721 {dead: []} = unsharpMask_s0[t3722]
	movq %rdx, -304(%rbp)
	movq $731, -312(%rbp) # t3749 {dead: []} = 731
	# t3748 {dead: []} = t3749 * r_s55 {canonical: (731) * (r_s55)}
	movq -312(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -320(%rbp)
	# t3747 {dead: []} = t3748 + c_s55 {canonical: ((731) * (r_s55)) + (c_s55)}
	movq -320(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -328(%rbp)
	movq -328(%rbp), %rax # t3746 {dead: []} = unsharpMask_s0[t3747]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3746 {dead: []} = unsharpMask_s0[t3747]
	movq %rdx, -336(%rbp)
	movq $3, -344(%rbp) # t3771 {dead: []} = 3
	movq -344(%rbp), %rax # t3770 {dead: []} = unsharpKernel_s0[t3771]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3770 {dead: []} = unsharpKernel_s0[t3771]
	movq %rdx, -352(%rbp)
	# t3745 {dead: []} = t3746 * t3770 {canonical: (unsharpMask_s0[((731) * (r_s55)) + (c_s55)]) * (unsharpKernel_s0[3])}
	movq -336(%rbp), %rax
	imulq -352(%rbp), %rax
	movq %rax, -360(%rbp)
	# t3720 {dead: []} = t3721 + t3745 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((unsharpMask_s0[((731) * (r_s55)) + (c_s55)]) * (unsharpKernel_s0[3]))}
	movq -304(%rbp), %rax
	addq -360(%rbp), %rax
	movq %rax, -368(%rbp)
	movq -368(%rbp), %rax # dot_s62 {dead: []} += t3720
	addq %rax, -80(%rbp)
	movq $731, -376(%rbp) # t3798 {dead: []} = 731
	# t3796 {dead: []} = r_s55 * t3798 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -376(%rbp), %rax
	movq %rax, -384(%rbp)
	# t3795 {dead: []} = t3796 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -384(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -392(%rbp)
	movq -392(%rbp), %rax # t3794 {dead: []} = unsharpMask_s0[t3795]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3794 {dead: []} = unsharpMask_s0[t3795]
	movq %rdx, -400(%rbp)
	movq $731, -408(%rbp) # t3823 {dead: []} = 731
	# t3822 {dead: []} = t3823 * r_s55 {canonical: (731) * (r_s55)}
	movq -408(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -416(%rbp)
	# t3821 {dead: []} = t3822 + c_s55 {canonical: ((731) * (r_s55)) + (c_s55)}
	movq -416(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -424(%rbp)
	movq $731, -432(%rbp) # t3842 {dead: []} = 731
	# t3820 {dead: []} = t3821 + t3842 {canonical: (((731) * (r_s55)) + (c_s55)) + (731)}
	movq -424(%rbp), %rax
	addq -432(%rbp), %rax
	movq %rax, -440(%rbp)
	movq -440(%rbp), %rax # t3819 {dead: []} = unsharpMask_s0[t3820]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3819 {dead: []} = unsharpMask_s0[t3820]
	movq %rdx, -448(%rbp)
	movq $4, -456(%rbp) # t3855 {dead: []} = 4
	movq -456(%rbp), %rax # t3854 {dead: []} = unsharpKernel_s0[t3855]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3854 {dead: []} = unsharpKernel_s0[t3855]
	movq %rdx, -464(%rbp)
	# t3818 {dead: []} = t3819 * t3854 {canonical: (unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (731)]) * (unsharpKernel_s0[4])}
	movq -448(%rbp), %rax
	imulq -464(%rbp), %rax
	movq %rax, -472(%rbp)
	# t3793 {dead: []} = t3794 + t3818 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (731)]) * (unsharpKernel_s0[4]))}
	movq -400(%rbp), %rax
	addq -472(%rbp), %rax
	movq %rax, -480(%rbp)
	movq -480(%rbp), %rax # dot_s62 {dead: []} += t3793
	addq %rax, -80(%rbp)
	movq $731, -488(%rbp) # t3882 {dead: []} = 731
	# t3880 {dead: []} = r_s55 * t3882 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -488(%rbp), %rax
	movq %rax, -496(%rbp)
	# t3879 {dead: []} = t3880 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -496(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -504(%rbp)
	movq -504(%rbp), %rax # t3878 {dead: []} = unsharpMask_s0[t3879]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3878 {dead: []} = unsharpMask_s0[t3879]
	movq %rdx, -512(%rbp)
	movq $731, -520(%rbp) # t3907 {dead: []} = 731
	# t3906 {dead: []} = t3907 * r_s55 {canonical: (731) * (r_s55)}
	movq -520(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -528(%rbp)
	# t3905 {dead: []} = t3906 + c_s55 {canonical: ((731) * (r_s55)) + (c_s55)}
	movq -528(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -536(%rbp)
	movq $1462, -544(%rbp) # t3926 {dead: []} = 1462
	# t3904 {dead: []} = t3905 + t3926 {canonical: (((731) * (r_s55)) + (c_s55)) + (1462)}
	movq -536(%rbp), %rax
	addq -544(%rbp), %rax
	movq %rax, -552(%rbp)
	movq -552(%rbp), %rax # t3903 {dead: []} = unsharpMask_s0[t3904]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3903 {dead: []} = unsharpMask_s0[t3904]
	movq %rdx, -560(%rbp)
	movq $5, -568(%rbp) # t3939 {dead: []} = 5
	movq -568(%rbp), %rax # t3938 {dead: []} = unsharpKernel_s0[t3939]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3938 {dead: []} = unsharpKernel_s0[t3939]
	movq %rdx, -576(%rbp)
	# t3902 {dead: []} = t3903 * t3938 {canonical: (unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (1462)]) * (unsharpKernel_s0[5])}
	movq -560(%rbp), %rax
	imulq -576(%rbp), %rax
	movq %rax, -584(%rbp)
	# t3877 {dead: []} = t3878 + t3902 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (1462)]) * (unsharpKernel_s0[5]))}
	movq -512(%rbp), %rax
	addq -584(%rbp), %rax
	movq %rax, -592(%rbp)
	movq -592(%rbp), %rax # dot_s62 {dead: []} += t3877
	addq %rax, -80(%rbp)
	movq $731, -600(%rbp) # t3966 {dead: []} = 731
	# t3964 {dead: []} = r_s55 * t3966 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -600(%rbp), %rax
	movq %rax, -608(%rbp)
	# t3963 {dead: []} = t3964 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -608(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -616(%rbp)
	movq -616(%rbp), %rax # t3962 {dead: []} = unsharpMask_s0[t3963]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3962 {dead: []} = unsharpMask_s0[t3963]
	movq %rdx, -624(%rbp)
	movq $731, -632(%rbp) # t3991 {dead: []} = 731
	# t3990 {dead: []} = t3991 * r_s55 {canonical: (731) * (r_s55)}
	movq -632(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -640(%rbp)
	# t3989 {dead: []} = t3990 + c_s55 {canonical: ((731) * (r_s55)) + (c_s55)}
	movq -640(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -648(%rbp)
	movq $2193, -656(%rbp) # t4010 {dead: []} = 2193
	# t3988 {dead: []} = t3989 + t4010 {canonical: (((731) * (r_s55)) + (c_s55)) + (2193)}
	movq -648(%rbp), %rax
	addq -656(%rbp), %rax
	movq %rax, -664(%rbp)
	movq -664(%rbp), %rax # t3987 {dead: []} = unsharpMask_s0[t3988]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t3987 {dead: []} = unsharpMask_s0[t3988]
	movq %rdx, -672(%rbp)
	movq $6, -680(%rbp) # t4023 {dead: []} = 6
	movq -680(%rbp), %rax # t4022 {dead: []} = unsharpKernel_s0[t4023]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4022 {dead: []} = unsharpKernel_s0[t4023]
	movq %rdx, -688(%rbp)
	# t3986 {dead: []} = t3987 * t4022 {canonical: (unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (2193)]) * (unsharpKernel_s0[6])}
	movq -672(%rbp), %rax
	imulq -688(%rbp), %rax
	movq %rax, -696(%rbp)
	# t3961 {dead: []} = t3962 + t3986 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (2193)]) * (unsharpKernel_s0[6]))}
	movq -624(%rbp), %rax
	addq -696(%rbp), %rax
	movq %rax, -704(%rbp)
	movq -704(%rbp), %rax # dot_s62 {dead: []} += t3961
	addq %rax, -80(%rbp)
	movq -64(%rbp), %rax # m1_s60 {dead: []} = m2_s60 {canonical: m2_s60}
	movq %rax, -56(%rbp)
	movq -72(%rbp), %rax # m2_s60 {dead: []} = m3_s60 {canonical: m3_s60}
	movq %rax, -64(%rbp)
	movq $731, -712(%rbp) # t4056 {dead: []} = 731
	# t4054 {dead: []} = r_s55 * t4056 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -712(%rbp), %rax
	movq %rax, -720(%rbp)
	# t4053 {dead: []} = t4054 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -720(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -728(%rbp)
	movq -728(%rbp), %rax # m3_s60 {dead: []} = unsharpMask_s0[t4053]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m3_s60 {dead: []} = unsharpMask_s0[t4053]
	movq %rdx, -72(%rbp)
	movq $731, -736(%rbp) # t4079 {dead: []} = 731
	# t4077 {dead: []} = r_s55 * t4079 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -736(%rbp), %rax
	movq %rax, -744(%rbp)
	# t4076 {dead: []} = t4077 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -744(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -752(%rbp)
	movq -752(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	# unsharpMask_s0[t4076] {dead: []} = dot_s62 / kernel_sum_s0 {canonical: (dot_s62) / (kernel_sum_s0)}
	movq -80(%rbp), %rax
	cqto
	idivq _global_kernel_sum(%rip)
	movq %rax, (%rdi)
	jmp _nop_4106
_nop_4106:
	jmp _end_of_block_2472
_end_of_block_2472:
	jmp _block_2459
_block_2459:
_block_4114:
	movq $1, -88(%rbp) # t4110 {dead: []} = 1
	movq -88(%rbp), %rax # r_s55 {dead: []} += t4110
	addq %rax, -32(%rbp)
	jmp _nop_4115
_nop_4115:
	jmp _end_of_block_2459
_end_of_block_2459:
	jmp _conditional_2473
_block_2475:
_block_4127:
	# r_s55 {dead: []} = rows_s0 - center_s55 {canonical: (rows_s0) - (center_s55)}
	movq _global_rows(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -32(%rbp)
	jmp _nop_4128
_nop_4128:
	jmp _end_of_block_2475
_end_of_block_2475:
	jmp _conditional_2479
_conditional_2479:
_block_4138:
	# t4129 {dead: []} = r_s55 < rows_s0 {canonical: (r_s55) < (rows_s0)}
	movq -32(%rbp), %rax # t4129 {dead: []} = r_s55 < rows_s0 {canonical: (r_s55) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_4140
_nop_4140:
	jmp _end_of_conditional_2479
_end_of_conditional_2479:
	cmpq $1, -88(%rbp) # true = t4129
	jne _block_2446 # ifFalse
	jmp _block_2478 # ifTrue
_block_2478:
_block_4165:
	movq $731, -88(%rbp) # t4146 {dead: []} = 731
	# t4144 {dead: []} = r_s55 * t4146 {canonical: (r_s55) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t4143 {dead: []} = t4144 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # unsharpMask_s0[t4143] {dead: []} = 0
	jmp _nop_4166
_nop_4166:
	jmp _end_of_block_2478
_end_of_block_2478:
	jmp _block_2476
_block_2476:
_block_4174:
	movq $1, -88(%rbp) # t4170 {dead: []} = 1
	movq -88(%rbp), %rax # r_s55 {dead: []} += t4170
	addq %rax, -32(%rbp)
	jmp _nop_4175
_nop_4175:
	jmp _end_of_block_2476
_end_of_block_2476:
	jmp _conditional_2479
_block_2446:
_block_4183:
	movq $1, -88(%rbp) # t4179 {dead: []} = 1
	movq -88(%rbp), %rax # c_s55 {dead: []} += t4179
	addq %rax, -40(%rbp)
	jmp _nop_4184
_nop_4184:
	jmp _end_of_block_2446
_end_of_block_2446:
	jmp _conditional_2480
_block_2481:
_block_4205:
	movq -48(%rbp), %rax # t4187 {dead: []} = center_s55 {canonical: center_s55}
	movq %rax, -88(%rbp)
	movq -48(%rbp), %rax # t4192 {dead: []} = center_s55 {canonical: center_s55}
	movq %rax, -96(%rbp)
	movq -96(%rbp), %rax # t4191 {dead: []} = unsharpKernel_s0[t4192]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4191 {dead: []} = unsharpKernel_s0[t4192]
	movq %rdx, -104(%rbp)
	movq -88(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	# unsharpKernel_s0[t4187] {dead: []} = t4191 + kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s55]) + (kernel_sum_s0)}
	movq -104(%rbp), %rax
	addq _global_kernel_sum(%rip), %rax
	movq %rax, (%rdi)
	jmp _nop_4206
_nop_4206:
	jmp _end_of_block_2481
_end_of_block_2481:
	jmp _return_2482
_return_2482:
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

_method_sharpenS:
	enter $(224), $0
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
	movq %rdi, -16(%rbp)
	movq %rsi, -32(%rbp)
_block_4211:
_block_4230:
	movq $0, -48(%rbp) # c_s80 {dead: []} = 0
	jmp _nop_4231
_nop_4231:
	jmp _end_of_block_4211
_end_of_block_4211:
	jmp _conditional_4224
_conditional_4224:
_block_4241:
	# t4232 {dead: []} = c_s80 < cols_s0 {canonical: (c_s80) < (cols_s0)}
	movq -48(%rbp), %rax # t4232 {dead: []} = c_s80 < cols_s0 {canonical: (c_s80) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -56(%rbp)
	jmp _nop_4243
_nop_4243:
	jmp _end_of_conditional_4224
_end_of_conditional_4224:
	cmpq $1, -56(%rbp) # true = t4232
	jne _return_4225 # ifFalse
	jmp _block_4215 # ifTrue
_block_4215:
_block_4248:
	movq $0, -40(%rbp) # r_s80 {dead: []} = 0
	jmp _nop_4249
_nop_4249:
	jmp _end_of_block_4215
_end_of_block_4215:
	jmp _conditional_4223
_conditional_4223:
_block_4259:
	# t4250 {dead: []} = r_s80 < rows_s0 {canonical: (r_s80) < (rows_s0)}
	movq -40(%rbp), %rax # t4250 {dead: []} = r_s80 < rows_s0 {canonical: (r_s80) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -56(%rbp)
	jmp _nop_4261
_nop_4261:
	jmp _end_of_conditional_4223
_end_of_conditional_4223:
	cmpq $1, -56(%rbp) # true = t4250
	jne _block_4212 # ifFalse
	jmp _block_4218 # ifTrue
_block_4218:
_block_4410:
	movq $2193, -56(%rbp) # t4268 {dead: []} = 2193
	# t4266 {dead: []} = r_s80 * t4268 {canonical: (r_s80) * (2193)}
	movq -40(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $3, -72(%rbp) # t4280 {dead: []} = 3
	# t4278 {dead: []} = c_s80 * t4280 {canonical: (c_s80) * (3)}
	movq -48(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	# t4265 {dead: []} = t4266 + t4278 {canonical: ((r_s80) * (2193)) + ((c_s80) * (3))}
	movq -64(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	movq $1, -96(%rbp) # t4297 {dead: []} = 1
	# t4264 {dead: []} = t4265 + t4297 {canonical: (((r_s80) * (2193)) + ((c_s80) * (3))) + (1)}
	movq -88(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2193, -112(%rbp) # t4313 {dead: []} = 2193
	# t4311 {dead: []} = r_s80 * t4313 {canonical: (r_s80) * (2193)}
	movq -40(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t4325 {dead: []} = 3
	# t4323 {dead: []} = c_s80 * t4325 {canonical: (c_s80) * (3)}
	movq -48(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t4310 {dead: []} = t4311 + t4323 {canonical: ((r_s80) * (2193)) + ((c_s80) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $1, -152(%rbp) # t4342 {dead: []} = 1
	# t4309 {dead: []} = t4310 + t4342 {canonical: (((r_s80) * (2193)) + ((c_s80) * (3))) + (1)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -160(%rbp), %rax # t4308 {dead: []} = image_s0[t4309]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4308 {dead: []} = image_s0[t4309]
	movq %rdx, -168(%rbp)
	movq $731, -176(%rbp) # t4362 {dead: []} = 731
	# t4360 {dead: []} = r_s80 * t4362 {canonical: (r_s80) * (731)}
	movq -40(%rbp), %rax
	imulq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	# t4359 {dead: []} = t4360 + c_s80 {canonical: ((r_s80) * (731)) + (c_s80)}
	movq -184(%rbp), %rax
	addq -48(%rbp), %rax
	movq %rax, -192(%rbp)
	movq -192(%rbp), %rax # t4358 {dead: []} = unsharpMask_s0[t4359]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4358 {dead: []} = unsharpMask_s0[t4359]
	movq %rdx, -200(%rbp)
	# t4356 {dead: []} = amount_s79 * t4358 {canonical: (amount_s79) * (unsharpMask_s0[((r_s80) * (731)) + (c_s80)])}
	movq -16(%rbp), %rax
	imulq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	# t4354 {dead: []} = channelOne_s79 + t4356 {canonical: (channelOne_s79) + ((amount_s79) * (unsharpMask_s0[((r_s80) * (731)) + (c_s80)]))}
	movq -32(%rbp), %rax
	addq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	# t4307 {dead: []} = t4308 * t4354 {canonical: (image_s0[(((r_s80) * (2193)) + ((c_s80) * (3))) + (1)]) * ((channelOne_s79) + ((amount_s79) * (unsharpMask_s0[((r_s80) * (731)) + (c_s80)])))}
	movq -168(%rbp), %rax
	imulq -216(%rbp), %rax
	movq %rax, -224(%rbp)
	movq -104(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	# image_s0[t4264] {dead: []} = t4307 / channelOne_s79 {canonical: ((image_s0[(((r_s80) * (2193)) + ((c_s80) * (3))) + (1)]) * ((channelOne_s79) + ((amount_s79) * (unsharpMask_s0[((r_s80) * (731)) + (c_s80)])))) / (channelOne_s79)}
	movq -224(%rbp), %rax
	cqto
	idivq -32(%rbp)
	movq %rax, (%rdi)
	jmp _nop_4411
_nop_4411:
	jmp _end_of_block_4218
_end_of_block_4218:
	jmp _conditional_4222
_conditional_4222:
_block_4466:
	movq $2193, -56(%rbp) # t4418 {dead: []} = 2193
	# t4416 {dead: []} = r_s80 * t4418 {canonical: (r_s80) * (2193)}
	movq -40(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $3, -72(%rbp) # t4430 {dead: []} = 3
	# t4428 {dead: []} = c_s80 * t4430 {canonical: (c_s80) * (3)}
	movq -48(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	# t4415 {dead: []} = t4416 + t4428 {canonical: ((r_s80) * (2193)) + ((c_s80) * (3))}
	movq -64(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	movq $1, -96(%rbp) # t4447 {dead: []} = 1
	# t4414 {dead: []} = t4415 + t4447 {canonical: (((r_s80) * (2193)) + ((c_s80) * (3))) + (1)}
	movq -88(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # t4413 {dead: []} = image_s0[t4414]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4413 {dead: []} = image_s0[t4414]
	movq %rdx, -112(%rbp)
	# t4412 {dead: []} = t4413 >= channelOne_s79 {canonical: (image_s0[(((r_s80) * (2193)) + ((c_s80) * (3))) + (1)]) >= (channelOne_s79)}
	movq -112(%rbp), %rax # t4412 {dead: []} = t4413 >= channelOne_s79 {canonical: (image_s0[(((r_s80) * (2193)) + ((c_s80) * (3))) + (1)]) >= (channelOne_s79)}
	cmpq -32(%rbp), %rax
	setge %al
	movzbq %al, %rax
	movq %rax, -120(%rbp)
	jmp _nop_4468
_nop_4468:
	jmp _end_of_conditional_4222
_end_of_conditional_4222:
	cmpq $1, -120(%rbp) # true = t4412
	jne _block_4216 # ifFalse
	jmp _block_4221 # ifTrue
_block_4221:
_block_4524:
	movq $2193, -56(%rbp) # t4475 {dead: []} = 2193
	# t4473 {dead: []} = r_s80 * t4475 {canonical: (r_s80) * (2193)}
	movq -40(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $3, -72(%rbp) # t4487 {dead: []} = 3
	# t4485 {dead: []} = c_s80 * t4487 {canonical: (c_s80) * (3)}
	movq -48(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	# t4472 {dead: []} = t4473 + t4485 {canonical: ((r_s80) * (2193)) + ((c_s80) * (3))}
	movq -64(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	movq $1, -96(%rbp) # t4504 {dead: []} = 1
	# t4471 {dead: []} = t4472 + t4504 {canonical: (((r_s80) * (2193)) + ((c_s80) * (3))) + (1)}
	movq -88(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $1, -112(%rbp) # t4515 {dead: []} = 1
	movq -104(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	# image_s0[t4471] {dead: []} = channelOne_s79 - t4515 {canonical: (channelOne_s79) - (1)}
	movq -32(%rbp), %rax
	subq -112(%rbp), %rax
	movq %rax, (%rdi)
	jmp _nop_4525
_nop_4525:
	jmp _end_of_block_4221
_end_of_block_4221:
	jmp _block_4216
_block_4216:
_block_4533:
	movq $1, -56(%rbp) # t4529 {dead: []} = 1
	movq -56(%rbp), %rax # r_s80 {dead: []} += t4529
	addq %rax, -40(%rbp)
	jmp _nop_4534
_nop_4534:
	jmp _end_of_block_4216
_end_of_block_4216:
	jmp _conditional_4223
_block_4212:
_block_4542:
	movq $1, -56(%rbp) # t4538 {dead: []} = 1
	movq -56(%rbp), %rax # c_s80 {dead: []} += t4538
	addq %rax, -48(%rbp)
	jmp _nop_4543
_nop_4543:
	jmp _end_of_block_4212
_end_of_block_4212:
	jmp _conditional_4224
_return_4225:
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

_method_write_file:
	enter $(208), $0
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
_block_4549:
_block_4576:
	movq _global_cols(%rip), %rax # t4564 {dead: []} = cols_s0 {canonical: cols_s0}
	movq %rax, -40(%rbp)
	movq _global_rows(%rip), %rax # t4568 {dead: []} = rows_s0 {canonical: rows_s0}
	movq %rax, -48(%rbp)
	# ppm_open_for_write_s0["output/output.ppm", t4564, t4568]
	movq -48(%rbp), %rdx
	movq -40(%rbp), %rsi
	leaq _string_1(%rip), %rdi
	call ppm_open_for_write
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq $0, -24(%rbp) # r_s6 {dead: []} = 0
	jmp _nop_4577
_nop_4577:
	jmp _end_of_block_4549
_end_of_block_4549:
	jmp _conditional_4558
_conditional_4558:
_block_4587:
	# t4578 {dead: []} = r_s6 < rows_s0 {canonical: (r_s6) < (rows_s0)}
	movq -24(%rbp), %rax # t4578 {dead: []} = r_s6 < rows_s0 {canonical: (r_s6) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -40(%rbp)
	jmp _nop_4589
_nop_4589:
	jmp _end_of_conditional_4558
_end_of_conditional_4558:
	cmpq $1, -40(%rbp) # true = t4578
	jne _block_4559 # ifFalse
	jmp _block_4553 # ifTrue
_block_4553:
_block_4594:
	movq $0, -32(%rbp) # c_s6 {dead: []} = 0
	jmp _nop_4595
_nop_4595:
	jmp _end_of_block_4553
_end_of_block_4553:
	jmp _conditional_4557
_conditional_4557:
_block_4605:
	# t4596 {dead: []} = c_s6 < cols_s0 {canonical: (c_s6) < (cols_s0)}
	movq -32(%rbp), %rax # t4596 {dead: []} = c_s6 < cols_s0 {canonical: (c_s6) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -40(%rbp)
	jmp _nop_4607
_nop_4607:
	jmp _end_of_conditional_4557
_end_of_conditional_4557:
	cmpq $1, -40(%rbp) # true = t4596
	jne _block_4550 # ifFalse
	jmp _block_4556 # ifTrue
_block_4556:
_block_4739:
	movq $2193, -40(%rbp) # t4615 {dead: []} = 2193
	# t4613 {dead: []} = r_s6 * t4615 {canonical: (r_s6) * (2193)}
	movq -24(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -48(%rbp)
	movq $3, -56(%rbp) # t4627 {dead: []} = 3
	# t4625 {dead: []} = c_s6 * t4627 {canonical: (c_s6) * (3)}
	movq -32(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	# t4612 {dead: []} = t4613 + t4625 {canonical: ((r_s6) * (2193)) + ((c_s6) * (3))}
	movq -48(%rbp), %rax
	addq -64(%rbp), %rax
	movq %rax, -72(%rbp)
	movq -72(%rbp), %rax # t4611 {dead: []} = image_s0[t4612]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4611 {dead: []} = image_s0[t4612]
	movq %rdx, -80(%rbp)
	movq $2193, -88(%rbp) # t4651 {dead: []} = 2193
	# t4649 {dead: []} = r_s6 * t4651 {canonical: (r_s6) * (2193)}
	movq -24(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t4663 {dead: []} = 3
	# t4661 {dead: []} = c_s6 * t4663 {canonical: (c_s6) * (3)}
	movq -32(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t4648 {dead: []} = t4649 + t4661 {canonical: ((r_s6) * (2193)) + ((c_s6) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $1, -128(%rbp) # t4680 {dead: []} = 1
	# t4647 {dead: []} = t4648 + t4680 {canonical: (((r_s6) * (2193)) + ((c_s6) * (3))) + (1)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # t4646 {dead: []} = image_s0[t4647]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4646 {dead: []} = image_s0[t4647]
	movq %rdx, -144(%rbp)
	movq $2193, -152(%rbp) # t4697 {dead: []} = 2193
	# t4695 {dead: []} = r_s6 * t4697 {canonical: (r_s6) * (2193)}
	movq -24(%rbp), %rax
	imulq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq $3, -168(%rbp) # t4709 {dead: []} = 3
	# t4707 {dead: []} = c_s6 * t4709 {canonical: (c_s6) * (3)}
	movq -32(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	# t4694 {dead: []} = t4695 + t4707 {canonical: ((r_s6) * (2193)) + ((c_s6) * (3))}
	movq -160(%rbp), %rax
	addq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	movq $2, -192(%rbp) # t4726 {dead: []} = 2
	# t4693 {dead: []} = t4694 + t4726 {canonical: (((r_s6) * (2193)) + ((c_s6) * (3))) + (2)}
	movq -184(%rbp), %rax
	addq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	movq -200(%rbp), %rax # t4692 {dead: []} = image_s0[t4693]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4692 {dead: []} = image_s0[t4693]
	movq %rdx, -208(%rbp)
	# ppm_write_next_pixel_s0[t4611, t4646, t4692]
	movq -208(%rbp), %rdx
	movq -144(%rbp), %rsi
	movq -80(%rbp), %rdi
	call ppm_write_next_pixel
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	jmp _nop_4740
_nop_4740:
	jmp _end_of_block_4556
_end_of_block_4556:
	jmp _block_4554
_block_4554:
_block_4748:
	movq $1, -40(%rbp) # t4744 {dead: []} = 1
	movq -40(%rbp), %rax # c_s6 {dead: []} += t4744
	addq %rax, -32(%rbp)
	jmp _nop_4749
_nop_4749:
	jmp _end_of_block_4554
_end_of_block_4554:
	jmp _conditional_4557
_block_4550:
_block_4757:
	movq $1, -40(%rbp) # t4753 {dead: []} = 1
	movq -40(%rbp), %rax # r_s6 {dead: []} += t4753
	addq %rax, -24(%rbp)
	jmp _nop_4758
_nop_4758:
	jmp _end_of_block_4550
_end_of_block_4550:
	jmp _conditional_4558
_block_4559:
_block_4763:
	# ppm_close_s0[]
	call ppm_close
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	jmp _nop_4764
_nop_4764:
	jmp _end_of_block_4559
_end_of_block_4559:
	jmp _return_4560
_return_4560:
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

_method_sharpenV:
	enter $(224), $0
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
	movq %rdi, -16(%rbp)
	movq %rsi, -32(%rbp)
_block_4769:
_block_4788:
	movq $0, -48(%rbp) # c_s85 {dead: []} = 0
	jmp _nop_4789
_nop_4789:
	jmp _end_of_block_4769
_end_of_block_4769:
	jmp _conditional_4782
_conditional_4782:
_block_4799:
	# t4790 {dead: []} = c_s85 < cols_s0 {canonical: (c_s85) < (cols_s0)}
	movq -48(%rbp), %rax # t4790 {dead: []} = c_s85 < cols_s0 {canonical: (c_s85) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -56(%rbp)
	jmp _nop_4801
_nop_4801:
	jmp _end_of_conditional_4782
_end_of_conditional_4782:
	cmpq $1, -56(%rbp) # true = t4790
	jne _return_4783 # ifFalse
	jmp _block_4773 # ifTrue
_block_4773:
_block_4806:
	movq $0, -40(%rbp) # r_s85 {dead: []} = 0
	jmp _nop_4807
_nop_4807:
	jmp _end_of_block_4773
_end_of_block_4773:
	jmp _conditional_4781
_conditional_4781:
_block_4817:
	# t4808 {dead: []} = r_s85 < rows_s0 {canonical: (r_s85) < (rows_s0)}
	movq -40(%rbp), %rax # t4808 {dead: []} = r_s85 < rows_s0 {canonical: (r_s85) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -56(%rbp)
	jmp _nop_4819
_nop_4819:
	jmp _end_of_conditional_4781
_end_of_conditional_4781:
	cmpq $1, -56(%rbp) # true = t4808
	jne _block_4770 # ifFalse
	jmp _block_4776 # ifTrue
_block_4776:
_block_4968:
	movq $2193, -56(%rbp) # t4826 {dead: []} = 2193
	# t4824 {dead: []} = r_s85 * t4826 {canonical: (r_s85) * (2193)}
	movq -40(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $3, -72(%rbp) # t4838 {dead: []} = 3
	# t4836 {dead: []} = c_s85 * t4838 {canonical: (c_s85) * (3)}
	movq -48(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	# t4823 {dead: []} = t4824 + t4836 {canonical: ((r_s85) * (2193)) + ((c_s85) * (3))}
	movq -64(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	movq $2, -96(%rbp) # t4855 {dead: []} = 2
	# t4822 {dead: []} = t4823 + t4855 {canonical: (((r_s85) * (2193)) + ((c_s85) * (3))) + (2)}
	movq -88(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2193, -112(%rbp) # t4871 {dead: []} = 2193
	# t4869 {dead: []} = r_s85 * t4871 {canonical: (r_s85) * (2193)}
	movq -40(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t4883 {dead: []} = 3
	# t4881 {dead: []} = c_s85 * t4883 {canonical: (c_s85) * (3)}
	movq -48(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t4868 {dead: []} = t4869 + t4881 {canonical: ((r_s85) * (2193)) + ((c_s85) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $2, -152(%rbp) # t4900 {dead: []} = 2
	# t4867 {dead: []} = t4868 + t4900 {canonical: (((r_s85) * (2193)) + ((c_s85) * (3))) + (2)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -160(%rbp), %rax # t4866 {dead: []} = image_s0[t4867]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4866 {dead: []} = image_s0[t4867]
	movq %rdx, -168(%rbp)
	movq $731, -176(%rbp) # t4920 {dead: []} = 731
	# t4918 {dead: []} = r_s85 * t4920 {canonical: (r_s85) * (731)}
	movq -40(%rbp), %rax
	imulq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	# t4917 {dead: []} = t4918 + c_s85 {canonical: ((r_s85) * (731)) + (c_s85)}
	movq -184(%rbp), %rax
	addq -48(%rbp), %rax
	movq %rax, -192(%rbp)
	movq -192(%rbp), %rax # t4916 {dead: []} = unsharpMask_s0[t4917]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4916 {dead: []} = unsharpMask_s0[t4917]
	movq %rdx, -200(%rbp)
	# t4914 {dead: []} = amount_s84 * t4916 {canonical: (amount_s84) * (unsharpMask_s0[((r_s85) * (731)) + (c_s85)])}
	movq -16(%rbp), %rax
	imulq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	# t4912 {dead: []} = channelOne_s84 + t4914 {canonical: (channelOne_s84) + ((amount_s84) * (unsharpMask_s0[((r_s85) * (731)) + (c_s85)]))}
	movq -32(%rbp), %rax
	addq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	# t4865 {dead: []} = t4866 * t4912 {canonical: (image_s0[(((r_s85) * (2193)) + ((c_s85) * (3))) + (2)]) * ((channelOne_s84) + ((amount_s84) * (unsharpMask_s0[((r_s85) * (731)) + (c_s85)])))}
	movq -168(%rbp), %rax
	imulq -216(%rbp), %rax
	movq %rax, -224(%rbp)
	movq -104(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	# image_s0[t4822] {dead: []} = t4865 / channelOne_s84 {canonical: ((image_s0[(((r_s85) * (2193)) + ((c_s85) * (3))) + (2)]) * ((channelOne_s84) + ((amount_s84) * (unsharpMask_s0[((r_s85) * (731)) + (c_s85)])))) / (channelOne_s84)}
	movq -224(%rbp), %rax
	cqto
	idivq -32(%rbp)
	movq %rax, (%rdi)
	jmp _nop_4969
_nop_4969:
	jmp _end_of_block_4776
_end_of_block_4776:
	jmp _conditional_4780
_conditional_4780:
_block_5024:
	movq $2193, -56(%rbp) # t4976 {dead: []} = 2193
	# t4974 {dead: []} = r_s85 * t4976 {canonical: (r_s85) * (2193)}
	movq -40(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $3, -72(%rbp) # t4988 {dead: []} = 3
	# t4986 {dead: []} = c_s85 * t4988 {canonical: (c_s85) * (3)}
	movq -48(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	# t4973 {dead: []} = t4974 + t4986 {canonical: ((r_s85) * (2193)) + ((c_s85) * (3))}
	movq -64(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	movq $2, -96(%rbp) # t5005 {dead: []} = 2
	# t4972 {dead: []} = t4973 + t5005 {canonical: (((r_s85) * (2193)) + ((c_s85) * (3))) + (2)}
	movq -88(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # t4971 {dead: []} = image_s0[t4972]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t4971 {dead: []} = image_s0[t4972]
	movq %rdx, -112(%rbp)
	# t4970 {dead: []} = t4971 >= channelOne_s84 {canonical: (image_s0[(((r_s85) * (2193)) + ((c_s85) * (3))) + (2)]) >= (channelOne_s84)}
	movq -112(%rbp), %rax # t4970 {dead: []} = t4971 >= channelOne_s84 {canonical: (image_s0[(((r_s85) * (2193)) + ((c_s85) * (3))) + (2)]) >= (channelOne_s84)}
	cmpq -32(%rbp), %rax
	setge %al
	movzbq %al, %rax
	movq %rax, -120(%rbp)
	jmp _nop_5026
_nop_5026:
	jmp _end_of_conditional_4780
_end_of_conditional_4780:
	cmpq $1, -120(%rbp) # true = t4970
	jne _block_4774 # ifFalse
	jmp _block_4779 # ifTrue
_block_4779:
_block_5082:
	movq $2193, -56(%rbp) # t5033 {dead: []} = 2193
	# t5031 {dead: []} = r_s85 * t5033 {canonical: (r_s85) * (2193)}
	movq -40(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	movq $3, -72(%rbp) # t5045 {dead: []} = 3
	# t5043 {dead: []} = c_s85 * t5045 {canonical: (c_s85) * (3)}
	movq -48(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	# t5030 {dead: []} = t5031 + t5043 {canonical: ((r_s85) * (2193)) + ((c_s85) * (3))}
	movq -64(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	movq $2, -96(%rbp) # t5062 {dead: []} = 2
	# t5029 {dead: []} = t5030 + t5062 {canonical: (((r_s85) * (2193)) + ((c_s85) * (3))) + (2)}
	movq -88(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $1, -112(%rbp) # t5073 {dead: []} = 1
	movq -104(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	# image_s0[t5029] {dead: []} = channelOne_s84 - t5073 {canonical: (channelOne_s84) - (1)}
	movq -32(%rbp), %rax
	subq -112(%rbp), %rax
	movq %rax, (%rdi)
	jmp _nop_5083
_nop_5083:
	jmp _end_of_block_4779
_end_of_block_4779:
	jmp _block_4774
_block_4774:
_block_5091:
	movq $1, -56(%rbp) # t5087 {dead: []} = 1
	movq -56(%rbp), %rax # r_s85 {dead: []} += t5087
	addq %rax, -40(%rbp)
	jmp _nop_5092
_nop_5092:
	jmp _end_of_block_4774
_end_of_block_4774:
	jmp _conditional_4781
_block_4770:
_block_5100:
	movq $1, -56(%rbp) # t5096 {dead: []} = 1
	movq -56(%rbp), %rax # c_s85 {dead: []} += t5096
	addq %rax, -48(%rbp)
	jmp _nop_5101
_nop_5101:
	jmp _end_of_block_4770
_end_of_block_4770:
	jmp _conditional_4782
_return_4783:
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

_method_convert2RGB:
	enter $(960), $0
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
	movq $0, -248(%rbp)
	movq $0, -256(%rbp)
	movq $0, -264(%rbp)
	movq $0, -272(%rbp)
	movq $0, -280(%rbp)
	movq $0, -288(%rbp)
	movq $0, -296(%rbp)
	movq $0, -304(%rbp)
	movq $0, -312(%rbp)
	movq $0, -320(%rbp)
	movq $0, -328(%rbp)
	movq $0, -336(%rbp)
	movq $0, -344(%rbp)
	movq $0, -352(%rbp)
	movq $0, -360(%rbp)
	movq $0, -368(%rbp)
	movq $0, -376(%rbp)
	movq $0, -384(%rbp)
	movq $0, -392(%rbp)
	movq $0, -400(%rbp)
	movq $0, -408(%rbp)
	movq $0, -416(%rbp)
	movq $0, -424(%rbp)
	movq $0, -432(%rbp)
	movq $0, -440(%rbp)
	movq $0, -448(%rbp)
	movq $0, -456(%rbp)
	movq $0, -464(%rbp)
	movq $0, -472(%rbp)
	movq $0, -480(%rbp)
	movq $0, -488(%rbp)
	movq $0, -496(%rbp)
	movq $0, -504(%rbp)
	movq $0, -512(%rbp)
	movq $0, -520(%rbp)
	movq $0, -528(%rbp)
	movq $0, -536(%rbp)
	movq $0, -544(%rbp)
	movq $0, -552(%rbp)
	movq $0, -560(%rbp)
	movq $0, -568(%rbp)
	movq $0, -576(%rbp)
	movq $0, -584(%rbp)
	movq $0, -592(%rbp)
	movq $0, -600(%rbp)
	movq $0, -608(%rbp)
	movq $0, -616(%rbp)
	movq $0, -624(%rbp)
	movq $0, -632(%rbp)
	movq $0, -640(%rbp)
	movq $0, -648(%rbp)
	movq $0, -656(%rbp)
	movq $0, -664(%rbp)
	movq $0, -672(%rbp)
	movq $0, -680(%rbp)
	movq $0, -688(%rbp)
	movq $0, -696(%rbp)
	movq $0, -704(%rbp)
	movq $0, -712(%rbp)
	movq $0, -720(%rbp)
	movq $0, -728(%rbp)
	movq $0, -736(%rbp)
	movq $0, -744(%rbp)
	movq $0, -752(%rbp)
	movq $0, -760(%rbp)
	movq $0, -768(%rbp)
	movq $0, -776(%rbp)
	movq $0, -784(%rbp)
	movq $0, -792(%rbp)
	movq $0, -800(%rbp)
	movq $0, -808(%rbp)
	movq $0, -816(%rbp)
	movq $0, -824(%rbp)
	movq $0, -832(%rbp)
	movq $0, -840(%rbp)
	movq $0, -848(%rbp)
	movq $0, -856(%rbp)
	movq $0, -864(%rbp)
	movq $0, -872(%rbp)
	movq $0, -880(%rbp)
	movq $0, -888(%rbp)
	movq $0, -896(%rbp)
	movq $0, -904(%rbp)
	movq $0, -912(%rbp)
	movq $0, -920(%rbp)
	movq $0, -928(%rbp)
	movq $0, -936(%rbp)
	movq $0, -944(%rbp)
	movq $0, -952(%rbp)
	movq $0, -960(%rbp)
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
_block_5106:
_block_5187:
	movq $0, -32(%rbp) # row_s29 {dead: []} = 0
	jmp _nop_5188
_nop_5188:
	jmp _end_of_block_5106
_end_of_block_5106:
	jmp _conditional_5181
_conditional_5181:
_block_5198:
	# t5189 {dead: []} = row_s29 < rows_s0 {canonical: (row_s29) < (rows_s0)}
	movq -32(%rbp), %rax # t5189 {dead: []} = row_s29 < rows_s0 {canonical: (row_s29) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -112(%rbp)
	jmp _nop_5200
_nop_5200:
	jmp _end_of_conditional_5181
_end_of_conditional_5181:
	cmpq $1, -112(%rbp) # true = t5189
	jne _return_5182 # ifFalse
	jmp _block_5110 # ifTrue
_block_5110:
_block_5205:
	movq $0, -24(%rbp) # col_s29 {dead: []} = 0
	jmp _nop_5206
_nop_5206:
	jmp _end_of_block_5110
_end_of_block_5110:
	jmp _conditional_5180
_conditional_5180:
_block_5216:
	# t5207 {dead: []} = col_s29 < cols_s0 {canonical: (col_s29) < (cols_s0)}
	movq -24(%rbp), %rax # t5207 {dead: []} = col_s29 < cols_s0 {canonical: (col_s29) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -112(%rbp)
	jmp _nop_5218
_nop_5218:
	jmp _end_of_conditional_5180
_end_of_conditional_5180:
	cmpq $1, -112(%rbp) # true = t5207
	jne _block_5107 # ifFalse
	jmp _block_5121 # ifTrue
_block_5121:
_block_5247:
	movq $0, -56(%rbp) # r_s31 {dead: []} = 0
	movq $0, -96(%rbp) # g_s31 {dead: []} = 0
	movq $0, -64(%rbp) # b_s31 {dead: []} = 0
	movq $0, -80(%rbp) # v_s31 {dead: []} = 0
	movq $0, -104(%rbp) # j_s31 {dead: []} = 0
	movq $0, -88(%rbp) # f_s31 {dead: []} = 0
	movq $0, -40(%rbp) # p_s31 {dead: []} = 0
	movq $0, -48(%rbp) # q_s31 {dead: []} = 0
	movq $0, -72(%rbp) # t_s31 {dead: []} = 0
	jmp _nop_5248
_nop_5248:
	jmp _end_of_block_5121
_end_of_block_5121:
	jmp _conditional_5176
_conditional_5176:
_block_5305:
	movq $2193, -112(%rbp) # t5255 {dead: []} = 2193
	# t5253 {dead: []} = row_s29 * t5255 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t5267 {dead: []} = 3
	# t5265 {dead: []} = col_s29 * t5267 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t5252 {dead: []} = t5253 + t5265 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $1, -152(%rbp) # t5284 {dead: []} = 1
	# t5251 {dead: []} = t5252 + t5284 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -160(%rbp), %rax # t5250 {dead: []} = image_s0[t5251]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5250 {dead: []} = image_s0[t5251]
	movq %rdx, -168(%rbp)
	movq $0, -176(%rbp) # t5296 {dead: []} = 0
	# t5249 {dead: []} = t5250 == t5296 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) == (0)}
	movq -168(%rbp), %rax # t5249 {dead: []} = t5250 == t5296 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) == (0)}
	cmpq -176(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -184(%rbp)
	jmp _nop_5307
_nop_5307:
	jmp _end_of_conditional_5176
_end_of_conditional_5176:
	cmpq $1, -184(%rbp) # true = t5249
	jne _block_5133 # ifFalse
	jmp _block_5126 # ifTrue
_block_5126:
_block_5480:
	movq $2193, -112(%rbp) # t5316 {dead: []} = 2193
	# t5314 {dead: []} = row_s29 * t5316 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t5328 {dead: []} = 3
	# t5326 {dead: []} = col_s29 * t5328 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t5313 {dead: []} = t5314 + t5326 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $2, -152(%rbp) # t5345 {dead: []} = 2
	# t5312 {dead: []} = t5313 + t5345 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -160(%rbp), %rax # t5311 {dead: []} = image_s0[t5312]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5311 {dead: []} = image_s0[t5312]
	movq %rdx, -168(%rbp)
	movq $4, -176(%rbp) # t5357 {dead: []} = 4
	# r_s31 {dead: []} = t5311 / t5357 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) / (4)}
	movq -168(%rbp), %rax
	cqto
	idivq -176(%rbp)
	movq %rax, -56(%rbp)
	movq $2193, -184(%rbp) # t5373 {dead: []} = 2193
	# t5371 {dead: []} = row_s29 * t5373 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	movq $3, -200(%rbp) # t5385 {dead: []} = 3
	# t5383 {dead: []} = col_s29 * t5385 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	# t5370 {dead: []} = t5371 + t5383 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -192(%rbp), %rax
	addq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	movq $2, -224(%rbp) # t5402 {dead: []} = 2
	# t5369 {dead: []} = t5370 + t5402 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	movq -216(%rbp), %rax
	addq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	movq -232(%rbp), %rax # t5368 {dead: []} = image_s0[t5369]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5368 {dead: []} = image_s0[t5369]
	movq %rdx, -240(%rbp)
	movq $4, -248(%rbp) # t5414 {dead: []} = 4
	# g_s31 {dead: []} = t5368 / t5414 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) / (4)}
	movq -240(%rbp), %rax
	cqto
	idivq -248(%rbp)
	movq %rax, -96(%rbp)
	movq $2193, -256(%rbp) # t5430 {dead: []} = 2193
	# t5428 {dead: []} = row_s29 * t5430 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -256(%rbp), %rax
	movq %rax, -264(%rbp)
	movq $3, -272(%rbp) # t5442 {dead: []} = 3
	# t5440 {dead: []} = col_s29 * t5442 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -272(%rbp), %rax
	movq %rax, -280(%rbp)
	# t5427 {dead: []} = t5428 + t5440 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -264(%rbp), %rax
	addq -280(%rbp), %rax
	movq %rax, -288(%rbp)
	movq $2, -296(%rbp) # t5459 {dead: []} = 2
	# t5426 {dead: []} = t5427 + t5459 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	movq -288(%rbp), %rax
	addq -296(%rbp), %rax
	movq %rax, -304(%rbp)
	movq -304(%rbp), %rax # t5425 {dead: []} = image_s0[t5426]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5425 {dead: []} = image_s0[t5426]
	movq %rdx, -312(%rbp)
	movq $4, -320(%rbp) # t5471 {dead: []} = 4
	# b_s31 {dead: []} = t5425 / t5471 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) / (4)}
	movq -312(%rbp), %rax
	cqto
	idivq -320(%rbp)
	movq %rax, -64(%rbp)
	jmp _nop_5481
_nop_5481:
	jmp _end_of_block_5126
_end_of_block_5126:
	jmp _block_5179
_block_5179:
_block_5621:
	movq $2193, -112(%rbp) # t5488 {dead: []} = 2193
	# t5486 {dead: []} = row_s29 * t5488 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t5500 {dead: []} = 3
	# t5498 {dead: []} = col_s29 * t5500 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t5485 {dead: []} = t5486 + t5498 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $0, -152(%rbp) # t5517 {dead: []} = 0
	# t5484 {dead: []} = t5485 + t5517 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (0)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -160(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq -56(%rbp), %rax # image_s0[t5484] {dead: []} = r_s31 {canonical: r_s31}
	movq %rax, (%rdi)
	movq $2193, -168(%rbp) # t5534 {dead: []} = 2193
	# t5532 {dead: []} = row_s29 * t5534 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	movq $3, -184(%rbp) # t5546 {dead: []} = 3
	# t5544 {dead: []} = col_s29 * t5546 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	# t5531 {dead: []} = t5532 + t5544 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -176(%rbp), %rax
	addq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	movq $1, -208(%rbp) # t5563 {dead: []} = 1
	# t5530 {dead: []} = t5531 + t5563 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	movq -200(%rbp), %rax
	addq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	movq -216(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq -96(%rbp), %rax # image_s0[t5530] {dead: []} = g_s31 {canonical: g_s31}
	movq %rax, (%rdi)
	movq $2193, -224(%rbp) # t5580 {dead: []} = 2193
	# t5578 {dead: []} = row_s29 * t5580 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	movq $3, -240(%rbp) # t5592 {dead: []} = 3
	# t5590 {dead: []} = col_s29 * t5592 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -240(%rbp), %rax
	movq %rax, -248(%rbp)
	# t5577 {dead: []} = t5578 + t5590 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -232(%rbp), %rax
	addq -248(%rbp), %rax
	movq %rax, -256(%rbp)
	movq $2, -264(%rbp) # t5609 {dead: []} = 2
	# t5576 {dead: []} = t5577 + t5609 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	movq -256(%rbp), %rax
	addq -264(%rbp), %rax
	movq %rax, -272(%rbp)
	movq -272(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq -64(%rbp), %rax # image_s0[t5576] {dead: []} = b_s31 {canonical: b_s31}
	movq %rax, (%rdi)
	jmp _nop_5622
_nop_5622:
	jmp _end_of_block_5179
_end_of_block_5179:
	jmp _block_5111
_block_5111:
_block_5630:
	movq $1, -112(%rbp) # t5626 {dead: []} = 1
	movq -112(%rbp), %rax # col_s29 {dead: []} += t5626
	addq %rax, -24(%rbp)
	jmp _nop_5631
_nop_5631:
	jmp _end_of_block_5111
_end_of_block_5111:
	jmp _conditional_5180
_block_5133:
_block_6287:
	movq $2193, -112(%rbp) # t5641 {dead: []} = 2193
	# t5639 {dead: []} = row_s29 * t5641 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t5653 {dead: []} = 3
	# t5651 {dead: []} = col_s29 * t5653 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t5638 {dead: []} = t5639 + t5651 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $0, -152(%rbp) # t5670 {dead: []} = 0
	# t5637 {dead: []} = t5638 + t5670 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (0)}
	movq -144(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq -160(%rbp), %rax # t5636 {dead: []} = image_s0[t5637]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5636 {dead: []} = image_s0[t5637]
	movq %rdx, -168(%rbp)
	movq $60, -176(%rbp) # t5682 {dead: []} = 60
	# t5635 {dead: []} = t5636 / t5682 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (0)]) / (60)}
	movq -168(%rbp), %rax
	cqto
	idivq -176(%rbp)
	movq %rax, -184(%rbp)
	movq $6, -192(%rbp) # t5692 {dead: []} = 6
	# j_s31 {dead: []} = t5635 % t5692 {canonical: ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (0)]) / (60)) % (6)}
	movq -184(%rbp), %rax
	cqto
	idivq -192(%rbp)
	movq %rdx, -104(%rbp)
	movq $2193, -200(%rbp) # t5708 {dead: []} = 2193
	# t5706 {dead: []} = row_s29 * t5708 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	movq $3, -216(%rbp) # t5720 {dead: []} = 3
	# t5718 {dead: []} = col_s29 * t5720 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -216(%rbp), %rax
	movq %rax, -224(%rbp)
	# t5705 {dead: []} = t5706 + t5718 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -208(%rbp), %rax
	addq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	movq $0, -240(%rbp) # t5737 {dead: []} = 0
	# t5704 {dead: []} = t5705 + t5737 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (0)}
	movq -232(%rbp), %rax
	addq -240(%rbp), %rax
	movq %rax, -248(%rbp)
	movq -248(%rbp), %rax # t5703 {dead: []} = image_s0[t5704]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5703 {dead: []} = image_s0[t5704]
	movq %rdx, -256(%rbp)
	movq $60, -264(%rbp) # t5749 {dead: []} = 60
	# f_s31 {dead: []} = t5703 % t5749 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (0)]) % (60)}
	movq -256(%rbp), %rax
	cqto
	idivq -264(%rbp)
	movq %rdx, -88(%rbp)
	movq $2193, -272(%rbp) # t5766 {dead: []} = 2193
	# t5764 {dead: []} = row_s29 * t5766 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -272(%rbp), %rax
	movq %rax, -280(%rbp)
	movq $3, -288(%rbp) # t5778 {dead: []} = 3
	# t5776 {dead: []} = col_s29 * t5778 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -288(%rbp), %rax
	movq %rax, -296(%rbp)
	# t5763 {dead: []} = t5764 + t5776 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -280(%rbp), %rax
	addq -296(%rbp), %rax
	movq %rax, -304(%rbp)
	movq $2, -312(%rbp) # t5795 {dead: []} = 2
	# t5762 {dead: []} = t5763 + t5795 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	movq -304(%rbp), %rax
	addq -312(%rbp), %rax
	movq %rax, -320(%rbp)
	movq -320(%rbp), %rax # t5761 {dead: []} = image_s0[t5762]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5761 {dead: []} = image_s0[t5762]
	movq %rdx, -328(%rbp)
	movq $1024, -336(%rbp) # t5808 {dead: []} = 1024
	movq $2193, -344(%rbp) # t5816 {dead: []} = 2193
	# t5814 {dead: []} = row_s29 * t5816 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -344(%rbp), %rax
	movq %rax, -352(%rbp)
	movq $3, -360(%rbp) # t5828 {dead: []} = 3
	# t5826 {dead: []} = col_s29 * t5828 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -360(%rbp), %rax
	movq %rax, -368(%rbp)
	# t5813 {dead: []} = t5814 + t5826 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -352(%rbp), %rax
	addq -368(%rbp), %rax
	movq %rax, -376(%rbp)
	movq $1, -384(%rbp) # t5845 {dead: []} = 1
	# t5812 {dead: []} = t5813 + t5845 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	movq -376(%rbp), %rax
	addq -384(%rbp), %rax
	movq %rax, -392(%rbp)
	movq -392(%rbp), %rax # t5811 {dead: []} = image_s0[t5812]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5811 {dead: []} = image_s0[t5812]
	movq %rdx, -400(%rbp)
	# t5807 {dead: []} = t5808 - t5811 {canonical: (1024) - (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)])}
	movq -336(%rbp), %rax
	subq -400(%rbp), %rax
	movq %rax, -408(%rbp)
	# t5760 {dead: []} = t5761 * t5807 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * ((1024) - (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]))}
	movq -328(%rbp), %rax
	imulq -408(%rbp), %rax
	movq %rax, -416(%rbp)
	movq $1024, -424(%rbp) # t5872 {dead: []} = 1024
	movq $4, -432(%rbp) # t5875 {dead: []} = 4
	# t5871 {dead: []} = t5872 * t5875 {canonical: (1024) * (4)}
	movq -424(%rbp), %rax
	imulq -432(%rbp), %rax
	movq %rax, -440(%rbp)
	# p_s31 {dead: []} = t5760 / t5871 {canonical: ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * ((1024) - (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]))) / ((1024) * (4))}
	movq -416(%rbp), %rax
	cqto
	idivq -440(%rbp)
	movq %rax, -40(%rbp)
	movq $2193, -448(%rbp) # t5899 {dead: []} = 2193
	# t5897 {dead: []} = row_s29 * t5899 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -448(%rbp), %rax
	movq %rax, -456(%rbp)
	movq $3, -464(%rbp) # t5911 {dead: []} = 3
	# t5909 {dead: []} = col_s29 * t5911 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -464(%rbp), %rax
	movq %rax, -472(%rbp)
	# t5896 {dead: []} = t5897 + t5909 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -456(%rbp), %rax
	addq -472(%rbp), %rax
	movq %rax, -480(%rbp)
	movq $2, -488(%rbp) # t5928 {dead: []} = 2
	# t5895 {dead: []} = t5896 + t5928 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	movq -480(%rbp), %rax
	addq -488(%rbp), %rax
	movq %rax, -496(%rbp)
	movq -496(%rbp), %rax # t5894 {dead: []} = image_s0[t5895]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5894 {dead: []} = image_s0[t5895]
	movq %rdx, -504(%rbp)
	movq $1024, -512(%rbp) # t5942 {dead: []} = 1024
	movq $60, -520(%rbp) # t5945 {dead: []} = 60
	# t5941 {dead: []} = t5942 * t5945 {canonical: (1024) * (60)}
	movq -512(%rbp), %rax
	imulq -520(%rbp), %rax
	movq %rax, -528(%rbp)
	movq $2193, -536(%rbp) # t5961 {dead: []} = 2193
	# t5959 {dead: []} = row_s29 * t5961 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -536(%rbp), %rax
	movq %rax, -544(%rbp)
	movq $3, -552(%rbp) # t5973 {dead: []} = 3
	# t5971 {dead: []} = col_s29 * t5973 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -552(%rbp), %rax
	movq %rax, -560(%rbp)
	# t5958 {dead: []} = t5959 + t5971 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -544(%rbp), %rax
	addq -560(%rbp), %rax
	movq %rax, -568(%rbp)
	movq $1, -576(%rbp) # t5990 {dead: []} = 1
	# t5957 {dead: []} = t5958 + t5990 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	movq -568(%rbp), %rax
	addq -576(%rbp), %rax
	movq %rax, -584(%rbp)
	movq -584(%rbp), %rax # t5956 {dead: []} = image_s0[t5957]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t5956 {dead: []} = image_s0[t5957]
	movq %rdx, -592(%rbp)
	# t5955 {dead: []} = t5956 * f_s31 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * (f_s31)}
	movq -592(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -600(%rbp)
	# t5940 {dead: []} = t5941 - t5955 {canonical: ((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * (f_s31))}
	movq -528(%rbp), %rax
	subq -600(%rbp), %rax
	movq %rax, -608(%rbp)
	# t5893 {dead: []} = t5894 * t5940 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * (((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * (f_s31)))}
	movq -504(%rbp), %rax
	imulq -608(%rbp), %rax
	movq %rax, -616(%rbp)
	movq $1024, -624(%rbp) # t6026 {dead: []} = 1024
	movq $60, -632(%rbp) # t6029 {dead: []} = 60
	# t6025 {dead: []} = t6026 * t6029 {canonical: (1024) * (60)}
	movq -624(%rbp), %rax
	imulq -632(%rbp), %rax
	movq %rax, -640(%rbp)
	movq $4, -648(%rbp) # t6039 {dead: []} = 4
	# t6024 {dead: []} = t6025 * t6039 {canonical: ((1024) * (60)) * (4)}
	movq -640(%rbp), %rax
	imulq -648(%rbp), %rax
	movq %rax, -656(%rbp)
	# q_s31 {dead: []} = t5893 / t6024 {canonical: ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * (((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * (f_s31)))) / (((1024) * (60)) * (4))}
	movq -616(%rbp), %rax
	cqto
	idivq -656(%rbp)
	movq %rax, -48(%rbp)
	movq $2193, -664(%rbp) # t6063 {dead: []} = 2193
	# t6061 {dead: []} = row_s29 * t6063 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -664(%rbp), %rax
	movq %rax, -672(%rbp)
	movq $3, -680(%rbp) # t6075 {dead: []} = 3
	# t6073 {dead: []} = col_s29 * t6075 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -680(%rbp), %rax
	movq %rax, -688(%rbp)
	# t6060 {dead: []} = t6061 + t6073 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -672(%rbp), %rax
	addq -688(%rbp), %rax
	movq %rax, -696(%rbp)
	movq $2, -704(%rbp) # t6092 {dead: []} = 2
	# t6059 {dead: []} = t6060 + t6092 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	movq -696(%rbp), %rax
	addq -704(%rbp), %rax
	movq %rax, -712(%rbp)
	movq -712(%rbp), %rax # t6058 {dead: []} = image_s0[t6059]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t6058 {dead: []} = image_s0[t6059]
	movq %rdx, -720(%rbp)
	movq $1024, -728(%rbp) # t6106 {dead: []} = 1024
	movq $60, -736(%rbp) # t6109 {dead: []} = 60
	# t6105 {dead: []} = t6106 * t6109 {canonical: (1024) * (60)}
	movq -728(%rbp), %rax
	imulq -736(%rbp), %rax
	movq %rax, -744(%rbp)
	movq $2193, -752(%rbp) # t6125 {dead: []} = 2193
	# t6123 {dead: []} = row_s29 * t6125 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -752(%rbp), %rax
	movq %rax, -760(%rbp)
	movq $3, -768(%rbp) # t6137 {dead: []} = 3
	# t6135 {dead: []} = col_s29 * t6137 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -768(%rbp), %rax
	movq %rax, -776(%rbp)
	# t6122 {dead: []} = t6123 + t6135 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -760(%rbp), %rax
	addq -776(%rbp), %rax
	movq %rax, -784(%rbp)
	movq $1, -792(%rbp) # t6154 {dead: []} = 1
	# t6121 {dead: []} = t6122 + t6154 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	movq -784(%rbp), %rax
	addq -792(%rbp), %rax
	movq %rax, -800(%rbp)
	movq -800(%rbp), %rax # t6120 {dead: []} = image_s0[t6121]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t6120 {dead: []} = image_s0[t6121]
	movq %rdx, -808(%rbp)
	movq $60, -816(%rbp) # t6167 {dead: []} = 60
	# t6166 {dead: []} = t6167 - f_s31 {canonical: (60) - (f_s31)}
	movq -816(%rbp), %rax
	subq -88(%rbp), %rax
	movq %rax, -824(%rbp)
	# t6119 {dead: []} = t6120 * t6166 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * ((60) - (f_s31))}
	movq -808(%rbp), %rax
	imulq -824(%rbp), %rax
	movq %rax, -832(%rbp)
	# t6104 {dead: []} = t6105 - t6119 {canonical: ((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * ((60) - (f_s31)))}
	movq -744(%rbp), %rax
	subq -832(%rbp), %rax
	movq %rax, -840(%rbp)
	# t6057 {dead: []} = t6058 * t6104 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * (((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * ((60) - (f_s31))))}
	movq -720(%rbp), %rax
	imulq -840(%rbp), %rax
	movq %rax, -848(%rbp)
	movq $1024, -856(%rbp) # t6201 {dead: []} = 1024
	movq $60, -864(%rbp) # t6204 {dead: []} = 60
	# t6200 {dead: []} = t6201 * t6204 {canonical: (1024) * (60)}
	movq -856(%rbp), %rax
	imulq -864(%rbp), %rax
	movq %rax, -872(%rbp)
	movq $4, -880(%rbp) # t6214 {dead: []} = 4
	# t6199 {dead: []} = t6200 * t6214 {canonical: ((1024) * (60)) * (4)}
	movq -872(%rbp), %rax
	imulq -880(%rbp), %rax
	movq %rax, -888(%rbp)
	# t_s31 {dead: []} = t6057 / t6199 {canonical: ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * (((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * ((60) - (f_s31))))) / (((1024) * (60)) * (4))}
	movq -848(%rbp), %rax
	cqto
	idivq -888(%rbp)
	movq %rax, -72(%rbp)
	movq $2193, -896(%rbp) # t6237 {dead: []} = 2193
	# t6235 {dead: []} = row_s29 * t6237 {canonical: (row_s29) * (2193)}
	movq -32(%rbp), %rax
	imulq -896(%rbp), %rax
	movq %rax, -904(%rbp)
	movq $3, -912(%rbp) # t6249 {dead: []} = 3
	# t6247 {dead: []} = col_s29 * t6249 {canonical: (col_s29) * (3)}
	movq -24(%rbp), %rax
	imulq -912(%rbp), %rax
	movq %rax, -920(%rbp)
	# t6234 {dead: []} = t6235 + t6247 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	movq -904(%rbp), %rax
	addq -920(%rbp), %rax
	movq %rax, -928(%rbp)
	movq $2, -936(%rbp) # t6266 {dead: []} = 2
	# t6233 {dead: []} = t6234 + t6266 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	movq -928(%rbp), %rax
	addq -936(%rbp), %rax
	movq %rax, -944(%rbp)
	movq -944(%rbp), %rax # t6232 {dead: []} = image_s0[t6233]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t6232 {dead: []} = image_s0[t6233]
	movq %rdx, -952(%rbp)
	movq $4, -960(%rbp) # t6278 {dead: []} = 4
	# v_s31 {dead: []} = t6232 / t6278 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) / (4)}
	movq -952(%rbp), %rax
	cqto
	idivq -960(%rbp)
	movq %rax, -80(%rbp)
	jmp _nop_6288
_nop_6288:
	jmp _end_of_block_5133
_end_of_block_5133:
	jmp _conditional_5139
_conditional_5139:
_block_6300:
	movq $0, -112(%rbp) # t6291 {dead: []} = 0
	# t6289 {dead: []} = j_s31 == t6291 {canonical: (j_s31) == (0)}
	movq -104(%rbp), %rax # t6289 {dead: []} = j_s31 == t6291 {canonical: (j_s31) == (0)}
	cmpq -112(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -120(%rbp)
	jmp _nop_6302
_nop_6302:
	jmp _end_of_conditional_5139
_end_of_conditional_5139:
	cmpq $1, -120(%rbp) # true = t6289
	jne _conditional_5145 # ifFalse
	jmp _block_5138 # ifTrue
_block_5138:
_block_6316:
	movq -80(%rbp), %rax # r_s31 {dead: []} = v_s31 {canonical: v_s31}
	movq %rax, -56(%rbp)
	movq -72(%rbp), %rax # g_s31 {dead: []} = t_s31 {canonical: t_s31}
	movq %rax, -96(%rbp)
	movq -40(%rbp), %rax # b_s31 {dead: []} = p_s31 {canonical: p_s31}
	movq %rax, -64(%rbp)
	jmp _nop_6317
_nop_6317:
	jmp _end_of_block_5138
_end_of_block_5138:
	jmp _conditional_5145
_conditional_5145:
_block_6329:
	movq $1, -112(%rbp) # t6320 {dead: []} = 1
	# t6318 {dead: []} = j_s31 == t6320 {canonical: (j_s31) == (1)}
	movq -104(%rbp), %rax # t6318 {dead: []} = j_s31 == t6320 {canonical: (j_s31) == (1)}
	cmpq -112(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -120(%rbp)
	jmp _nop_6331
_nop_6331:
	jmp _end_of_conditional_5145
_end_of_conditional_5145:
	cmpq $1, -120(%rbp) # true = t6318
	jne _conditional_5151 # ifFalse
	jmp _block_5144 # ifTrue
_block_5144:
_block_6345:
	movq -48(%rbp), %rax # r_s31 {dead: []} = q_s31 {canonical: q_s31}
	movq %rax, -56(%rbp)
	movq -80(%rbp), %rax # g_s31 {dead: []} = v_s31 {canonical: v_s31}
	movq %rax, -96(%rbp)
	movq -40(%rbp), %rax # b_s31 {dead: []} = p_s31 {canonical: p_s31}
	movq %rax, -64(%rbp)
	jmp _nop_6346
_nop_6346:
	jmp _end_of_block_5144
_end_of_block_5144:
	jmp _conditional_5151
_conditional_5151:
_block_6358:
	movq $2, -112(%rbp) # t6349 {dead: []} = 2
	# t6347 {dead: []} = j_s31 == t6349 {canonical: (j_s31) == (2)}
	movq -104(%rbp), %rax # t6347 {dead: []} = j_s31 == t6349 {canonical: (j_s31) == (2)}
	cmpq -112(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -120(%rbp)
	jmp _nop_6360
_nop_6360:
	jmp _end_of_conditional_5151
_end_of_conditional_5151:
	cmpq $1, -120(%rbp) # true = t6347
	jne _conditional_5157 # ifFalse
	jmp _block_5150 # ifTrue
_block_5150:
_block_6374:
	movq -40(%rbp), %rax # r_s31 {dead: []} = p_s31 {canonical: p_s31}
	movq %rax, -56(%rbp)
	movq -80(%rbp), %rax # g_s31 {dead: []} = v_s31 {canonical: v_s31}
	movq %rax, -96(%rbp)
	movq -72(%rbp), %rax # b_s31 {dead: []} = t_s31 {canonical: t_s31}
	movq %rax, -64(%rbp)
	jmp _nop_6375
_nop_6375:
	jmp _end_of_block_5150
_end_of_block_5150:
	jmp _conditional_5157
_conditional_5157:
_block_6387:
	movq $3, -112(%rbp) # t6378 {dead: []} = 3
	# t6376 {dead: []} = j_s31 == t6378 {canonical: (j_s31) == (3)}
	movq -104(%rbp), %rax # t6376 {dead: []} = j_s31 == t6378 {canonical: (j_s31) == (3)}
	cmpq -112(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -120(%rbp)
	jmp _nop_6389
_nop_6389:
	jmp _end_of_conditional_5157
_end_of_conditional_5157:
	cmpq $1, -120(%rbp) # true = t6376
	jne _conditional_5163 # ifFalse
	jmp _block_5156 # ifTrue
_block_5156:
_block_6403:
	movq -40(%rbp), %rax # r_s31 {dead: []} = p_s31 {canonical: p_s31}
	movq %rax, -56(%rbp)
	movq -48(%rbp), %rax # g_s31 {dead: []} = q_s31 {canonical: q_s31}
	movq %rax, -96(%rbp)
	movq -80(%rbp), %rax # b_s31 {dead: []} = v_s31 {canonical: v_s31}
	movq %rax, -64(%rbp)
	jmp _nop_6404
_nop_6404:
	jmp _end_of_block_5156
_end_of_block_5156:
	jmp _conditional_5163
_conditional_5163:
_block_6416:
	movq $4, -112(%rbp) # t6407 {dead: []} = 4
	# t6405 {dead: []} = j_s31 == t6407 {canonical: (j_s31) == (4)}
	movq -104(%rbp), %rax # t6405 {dead: []} = j_s31 == t6407 {canonical: (j_s31) == (4)}
	cmpq -112(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -120(%rbp)
	jmp _nop_6418
_nop_6418:
	jmp _end_of_conditional_5163
_end_of_conditional_5163:
	cmpq $1, -120(%rbp) # true = t6405
	jne _conditional_5169 # ifFalse
	jmp _block_5162 # ifTrue
_block_5162:
_block_6432:
	movq -72(%rbp), %rax # r_s31 {dead: []} = t_s31 {canonical: t_s31}
	movq %rax, -56(%rbp)
	movq -40(%rbp), %rax # g_s31 {dead: []} = p_s31 {canonical: p_s31}
	movq %rax, -96(%rbp)
	movq -80(%rbp), %rax # b_s31 {dead: []} = v_s31 {canonical: v_s31}
	movq %rax, -64(%rbp)
	jmp _nop_6433
_nop_6433:
	jmp _end_of_block_5162
_end_of_block_5162:
	jmp _conditional_5169
_conditional_5169:
_block_6445:
	movq $5, -112(%rbp) # t6436 {dead: []} = 5
	# t6434 {dead: []} = j_s31 == t6436 {canonical: (j_s31) == (5)}
	movq -104(%rbp), %rax # t6434 {dead: []} = j_s31 == t6436 {canonical: (j_s31) == (5)}
	cmpq -112(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -120(%rbp)
	jmp _nop_6447
_nop_6447:
	jmp _end_of_conditional_5169
_end_of_conditional_5169:
	cmpq $1, -120(%rbp) # true = t6434
	jne _conditional_5175 # ifFalse
	jmp _block_5168 # ifTrue
_block_5168:
_block_6461:
	movq -80(%rbp), %rax # r_s31 {dead: []} = v_s31 {canonical: v_s31}
	movq %rax, -56(%rbp)
	movq -40(%rbp), %rax # g_s31 {dead: []} = p_s31 {canonical: p_s31}
	movq %rax, -96(%rbp)
	movq -48(%rbp), %rax # b_s31 {dead: []} = q_s31 {canonical: q_s31}
	movq %rax, -64(%rbp)
	jmp _nop_6462
_nop_6462:
	jmp _end_of_block_5168
_end_of_block_5168:
	jmp _conditional_5175
_conditional_5175:
_block_6474:
	movq $0, -112(%rbp) # t6465 {dead: []} = 0
	# t6463 {dead: []} = j_s31 < t6465 {canonical: (j_s31) < (0)}
	movq -104(%rbp), %rax # t6463 {dead: []} = j_s31 < t6465 {canonical: (j_s31) < (0)}
	cmpq -112(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -120(%rbp)
	jmp _nop_6476
_nop_6476:
	jmp _end_of_conditional_5175
_end_of_conditional_5175:
	cmpq $1, -120(%rbp) # true = t6463
	jne _block_5179 # ifFalse
	jmp _block_5174 # ifTrue
_block_5174:
_block_6487:
	movq $0, -56(%rbp) # r_s31 {dead: []} = 0
	movq $0, -96(%rbp) # g_s31 {dead: []} = 0
	movq $0, -64(%rbp) # b_s31 {dead: []} = 0
	jmp _nop_6488
_nop_6488:
	jmp _end_of_block_5174
_end_of_block_5174:
	jmp _block_5179
_block_5107:
_block_6496:
	movq $1, -112(%rbp) # t6492 {dead: []} = 1
	movq -112(%rbp), %rax # row_s29 {dead: []} += t6492
	addq %rax, -32(%rbp)
	jmp _nop_6497
_nop_6497:
	jmp _end_of_block_5107
_end_of_block_5107:
	jmp _conditional_5181
_return_5182:
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

_method_createUnsharpMaskH:
	enter $(928), $0
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
	movq $0, -248(%rbp)
	movq $0, -256(%rbp)
	movq $0, -264(%rbp)
	movq $0, -272(%rbp)
	movq $0, -280(%rbp)
	movq $0, -288(%rbp)
	movq $0, -296(%rbp)
	movq $0, -304(%rbp)
	movq $0, -312(%rbp)
	movq $0, -320(%rbp)
	movq $0, -328(%rbp)
	movq $0, -336(%rbp)
	movq $0, -344(%rbp)
	movq $0, -352(%rbp)
	movq $0, -360(%rbp)
	movq $0, -368(%rbp)
	movq $0, -376(%rbp)
	movq $0, -384(%rbp)
	movq $0, -392(%rbp)
	movq $0, -400(%rbp)
	movq $0, -408(%rbp)
	movq $0, -416(%rbp)
	movq $0, -424(%rbp)
	movq $0, -432(%rbp)
	movq $0, -440(%rbp)
	movq $0, -448(%rbp)
	movq $0, -456(%rbp)
	movq $0, -464(%rbp)
	movq $0, -472(%rbp)
	movq $0, -480(%rbp)
	movq $0, -488(%rbp)
	movq $0, -496(%rbp)
	movq $0, -504(%rbp)
	movq $0, -512(%rbp)
	movq $0, -520(%rbp)
	movq $0, -528(%rbp)
	movq $0, -536(%rbp)
	movq $0, -544(%rbp)
	movq $0, -552(%rbp)
	movq $0, -560(%rbp)
	movq $0, -568(%rbp)
	movq $0, -576(%rbp)
	movq $0, -584(%rbp)
	movq $0, -592(%rbp)
	movq $0, -600(%rbp)
	movq $0, -608(%rbp)
	movq $0, -616(%rbp)
	movq $0, -624(%rbp)
	movq $0, -632(%rbp)
	movq $0, -640(%rbp)
	movq $0, -648(%rbp)
	movq $0, -656(%rbp)
	movq $0, -664(%rbp)
	movq $0, -672(%rbp)
	movq $0, -680(%rbp)
	movq $0, -688(%rbp)
	movq $0, -696(%rbp)
	movq $0, -704(%rbp)
	movq $0, -712(%rbp)
	movq $0, -720(%rbp)
	movq $0, -728(%rbp)
	movq $0, -736(%rbp)
	movq $0, -744(%rbp)
	movq $0, -752(%rbp)
	movq $0, -760(%rbp)
	movq $0, -768(%rbp)
	movq $0, -776(%rbp)
	movq $0, -784(%rbp)
	movq $0, -792(%rbp)
	movq $0, -800(%rbp)
	movq $0, -808(%rbp)
	movq $0, -816(%rbp)
	movq $0, -824(%rbp)
	movq $0, -832(%rbp)
	movq $0, -840(%rbp)
	movq $0, -848(%rbp)
	movq $0, -856(%rbp)
	movq $0, -864(%rbp)
	movq $0, -872(%rbp)
	movq $0, -880(%rbp)
	movq $0, -888(%rbp)
	movq $0, -896(%rbp)
	movq $0, -904(%rbp)
	movq $0, -912(%rbp)
	movq $0, -920(%rbp)
	movq $0, -928(%rbp)
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
_block_6503:
_block_6580:
	movq $3, -48(%rbp) # center_s45 {dead: []} = 3
	movq $0, -32(%rbp) # r_s45 {dead: []} = 0
	jmp _nop_6581
_nop_6581:
	jmp _end_of_block_6503
_end_of_block_6503:
	jmp _conditional_6532
_conditional_6532:
_block_6591:
	# t6582 {dead: []} = r_s45 < rows_s0 {canonical: (r_s45) < (rows_s0)}
	movq -32(%rbp), %rax # t6582 {dead: []} = r_s45 < rows_s0 {canonical: (r_s45) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_6593
_nop_6593:
	jmp _end_of_conditional_6532
_end_of_conditional_6532:
	cmpq $1, -88(%rbp) # true = t6582
	jne _block_6535 # ifFalse
	jmp _block_6507 # ifTrue
_block_6507:
_block_6598:
	movq $0, -40(%rbp) # c_s45 {dead: []} = 0
	jmp _nop_6599
_nop_6599:
	jmp _end_of_block_6507
_end_of_block_6507:
	jmp _conditional_6511
_conditional_6511:
_block_6609:
	# t6600 {dead: []} = c_s45 < center_s45 {canonical: (c_s45) < (center_s45)}
	movq -40(%rbp), %rax # t6600 {dead: []} = c_s45 < center_s45 {canonical: (c_s45) < (center_s45)}
	cmpq -48(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_6611
_nop_6611:
	jmp _end_of_conditional_6511
_end_of_conditional_6511:
	cmpq $1, -88(%rbp) # true = t6600
	jne _block_6513 # ifFalse
	jmp _block_6510 # ifTrue
_block_6510:
_block_6668:
	movq $731, -88(%rbp) # t6617 {dead: []} = 731
	# t6615 {dead: []} = r_s45 * t6617 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t6614 {dead: []} = t6615 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2193, -112(%rbp) # t6638 {dead: []} = 2193
	# t6636 {dead: []} = r_s45 * t6638 {canonical: (r_s45) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t6650 {dead: []} = 3
	# t6648 {dead: []} = c_s45 * t6650 {canonical: (c_s45) * (3)}
	movq -40(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t6635 {dead: []} = t6636 + t6648 {canonical: ((r_s45) * (2193)) + ((c_s45) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -144(%rbp), %rax # unsharpMask_s0[t6614] {dead: []} = image_s0[t6635]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # unsharpMask_s0[t6614] {dead: []} = image_s0[t6635]
	movq %rdx, (%rdi)
	jmp _nop_6669
_nop_6669:
	jmp _end_of_block_6510
_end_of_block_6510:
	jmp _block_6508
_block_6508:
_block_6677:
	movq $1, -88(%rbp) # t6673 {dead: []} = 1
	movq -88(%rbp), %rax # c_s45 {dead: []} += t6673
	addq %rax, -40(%rbp)
	jmp _nop_6678
_nop_6678:
	jmp _end_of_block_6508
_end_of_block_6508:
	jmp _conditional_6511
_block_6513:
_block_6684:
	movq -48(%rbp), %rax # c_s45 {dead: []} = center_s45 {canonical: center_s45}
	movq %rax, -40(%rbp)
	jmp _nop_6685
_nop_6685:
	jmp _end_of_block_6513
_end_of_block_6513:
	jmp _conditional_6525
_conditional_6525:
_block_6704:
	# t6688 {dead: []} = cols_s0 - center_s45 {canonical: (cols_s0) - (center_s45)}
	movq _global_cols(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -88(%rbp)
	# t6686 {dead: []} = c_s45 < t6688 {canonical: (c_s45) < ((cols_s0) - (center_s45))}
	movq -40(%rbp), %rax # t6686 {dead: []} = c_s45 < t6688 {canonical: (c_s45) < ((cols_s0) - (center_s45))}
	cmpq -88(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -96(%rbp)
	jmp _nop_6706
_nop_6706:
	jmp _end_of_conditional_6525
_end_of_conditional_6525:
	cmpq $1, -96(%rbp) # true = t6686
	jne _block_6527 # ifFalse
	jmp _block_6524 # ifTrue
_block_6524:
_block_7354:
	movq $731, -88(%rbp) # t6712 {dead: []} = 731
	# t6710 {dead: []} = r_s45 * t6712 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t6709 {dead: []} = t6710 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # unsharpMask_s0[t6709] {dead: []} = 0
	movq $731, -112(%rbp) # t6735 {dead: []} = 731
	# t6733 {dead: []} = r_s45 * t6735 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	# t6732 {dead: []} = t6733 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -120(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -128(%rbp)
	movq $2193, -136(%rbp) # t6759 {dead: []} = 2193
	# t6757 {dead: []} = r_s45 * t6759 {canonical: (r_s45) * (2193)}
	movq -32(%rbp), %rax
	imulq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq $3, -152(%rbp) # t6770 {dead: []} = 3
	# t6769 {dead: []} = t6770 * c_s45 {canonical: (3) * (c_s45)}
	movq -152(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -160(%rbp)
	# t6756 {dead: []} = t6757 + t6769 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	movq -144(%rbp), %rax
	addq -160(%rbp), %rax
	movq %rax, -168(%rbp)
	movq $9, -176(%rbp) # t6788 {dead: []} = 9
	# t6755 {dead: []} = t6756 - t6788 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) - (9)}
	movq -168(%rbp), %rax
	subq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	movq -184(%rbp), %rax # t6754 {dead: []} = image_s0[t6755]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t6754 {dead: []} = image_s0[t6755]
	movq %rdx, -192(%rbp)
	movq $0, -200(%rbp) # t6801 {dead: []} = 0
	movq -200(%rbp), %rax # t6800 {dead: []} = unsharpKernel_s0[t6801]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t6800 {dead: []} = unsharpKernel_s0[t6801]
	movq %rdx, -208(%rbp)
	# t6753 {dead: []} = t6754 * t6800 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) - (9)]) * (unsharpKernel_s0[0])}
	movq -192(%rbp), %rax
	imulq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	movq -128(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -216(%rbp), %rax # unsharpMask_s0[t6732] {dead: []} += t6753
	addq %rax, (%rdi)
	movq $731, -224(%rbp) # t6818 {dead: []} = 731
	# t6816 {dead: []} = r_s45 * t6818 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	# t6815 {dead: []} = t6816 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -232(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -240(%rbp)
	movq $2193, -248(%rbp) # t6842 {dead: []} = 2193
	# t6840 {dead: []} = r_s45 * t6842 {canonical: (r_s45) * (2193)}
	movq -32(%rbp), %rax
	imulq -248(%rbp), %rax
	movq %rax, -256(%rbp)
	movq $3, -264(%rbp) # t6853 {dead: []} = 3
	# t6852 {dead: []} = t6853 * c_s45 {canonical: (3) * (c_s45)}
	movq -264(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -272(%rbp)
	# t6839 {dead: []} = t6840 + t6852 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	movq -256(%rbp), %rax
	addq -272(%rbp), %rax
	movq %rax, -280(%rbp)
	movq $6, -288(%rbp) # t6871 {dead: []} = 6
	# t6838 {dead: []} = t6839 - t6871 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) - (6)}
	movq -280(%rbp), %rax
	subq -288(%rbp), %rax
	movq %rax, -296(%rbp)
	movq -296(%rbp), %rax # t6837 {dead: []} = image_s0[t6838]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t6837 {dead: []} = image_s0[t6838]
	movq %rdx, -304(%rbp)
	movq $1, -312(%rbp) # t6884 {dead: []} = 1
	movq -312(%rbp), %rax # t6883 {dead: []} = unsharpKernel_s0[t6884]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t6883 {dead: []} = unsharpKernel_s0[t6884]
	movq %rdx, -320(%rbp)
	# t6836 {dead: []} = t6837 * t6883 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) - (6)]) * (unsharpKernel_s0[1])}
	movq -304(%rbp), %rax
	imulq -320(%rbp), %rax
	movq %rax, -328(%rbp)
	movq -240(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -328(%rbp), %rax # unsharpMask_s0[t6815] {dead: []} += t6836
	addq %rax, (%rdi)
	movq $731, -336(%rbp) # t6901 {dead: []} = 731
	# t6899 {dead: []} = r_s45 * t6901 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -336(%rbp), %rax
	movq %rax, -344(%rbp)
	# t6898 {dead: []} = t6899 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -344(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -352(%rbp)
	movq $2193, -360(%rbp) # t6925 {dead: []} = 2193
	# t6923 {dead: []} = r_s45 * t6925 {canonical: (r_s45) * (2193)}
	movq -32(%rbp), %rax
	imulq -360(%rbp), %rax
	movq %rax, -368(%rbp)
	movq $3, -376(%rbp) # t6936 {dead: []} = 3
	# t6935 {dead: []} = t6936 * c_s45 {canonical: (3) * (c_s45)}
	movq -376(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -384(%rbp)
	# t6922 {dead: []} = t6923 + t6935 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	movq -368(%rbp), %rax
	addq -384(%rbp), %rax
	movq %rax, -392(%rbp)
	movq $3, -400(%rbp) # t6954 {dead: []} = 3
	# t6921 {dead: []} = t6922 - t6954 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) - (3)}
	movq -392(%rbp), %rax
	subq -400(%rbp), %rax
	movq %rax, -408(%rbp)
	movq -408(%rbp), %rax # t6920 {dead: []} = image_s0[t6921]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t6920 {dead: []} = image_s0[t6921]
	movq %rdx, -416(%rbp)
	movq $2, -424(%rbp) # t6967 {dead: []} = 2
	movq -424(%rbp), %rax # t6966 {dead: []} = unsharpKernel_s0[t6967]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t6966 {dead: []} = unsharpKernel_s0[t6967]
	movq %rdx, -432(%rbp)
	# t6919 {dead: []} = t6920 * t6966 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) - (3)]) * (unsharpKernel_s0[2])}
	movq -416(%rbp), %rax
	imulq -432(%rbp), %rax
	movq %rax, -440(%rbp)
	movq -352(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -440(%rbp), %rax # unsharpMask_s0[t6898] {dead: []} += t6919
	addq %rax, (%rdi)
	movq $731, -448(%rbp) # t6984 {dead: []} = 731
	# t6982 {dead: []} = r_s45 * t6984 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -448(%rbp), %rax
	movq %rax, -456(%rbp)
	# t6981 {dead: []} = t6982 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -456(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -464(%rbp)
	movq $2193, -472(%rbp) # t7007 {dead: []} = 2193
	# t7005 {dead: []} = r_s45 * t7007 {canonical: (r_s45) * (2193)}
	movq -32(%rbp), %rax
	imulq -472(%rbp), %rax
	movq %rax, -480(%rbp)
	movq $3, -488(%rbp) # t7018 {dead: []} = 3
	# t7017 {dead: []} = t7018 * c_s45 {canonical: (3) * (c_s45)}
	movq -488(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -496(%rbp)
	# t7004 {dead: []} = t7005 + t7017 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	movq -480(%rbp), %rax
	addq -496(%rbp), %rax
	movq %rax, -504(%rbp)
	movq -504(%rbp), %rax # t7003 {dead: []} = image_s0[t7004]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7003 {dead: []} = image_s0[t7004]
	movq %rdx, -512(%rbp)
	movq $3, -520(%rbp) # t7039 {dead: []} = 3
	movq -520(%rbp), %rax # t7038 {dead: []} = unsharpKernel_s0[t7039]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7038 {dead: []} = unsharpKernel_s0[t7039]
	movq %rdx, -528(%rbp)
	# t7002 {dead: []} = t7003 * t7038 {canonical: (image_s0[((r_s45) * (2193)) + ((3) * (c_s45))]) * (unsharpKernel_s0[3])}
	movq -512(%rbp), %rax
	imulq -528(%rbp), %rax
	movq %rax, -536(%rbp)
	movq -464(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -536(%rbp), %rax # unsharpMask_s0[t6981] {dead: []} += t7002
	addq %rax, (%rdi)
	movq $731, -544(%rbp) # t7056 {dead: []} = 731
	# t7054 {dead: []} = r_s45 * t7056 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -544(%rbp), %rax
	movq %rax, -552(%rbp)
	# t7053 {dead: []} = t7054 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -552(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -560(%rbp)
	movq $2193, -568(%rbp) # t7080 {dead: []} = 2193
	# t7078 {dead: []} = r_s45 * t7080 {canonical: (r_s45) * (2193)}
	movq -32(%rbp), %rax
	imulq -568(%rbp), %rax
	movq %rax, -576(%rbp)
	movq $3, -584(%rbp) # t7091 {dead: []} = 3
	# t7090 {dead: []} = t7091 * c_s45 {canonical: (3) * (c_s45)}
	movq -584(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -592(%rbp)
	# t7077 {dead: []} = t7078 + t7090 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	movq -576(%rbp), %rax
	addq -592(%rbp), %rax
	movq %rax, -600(%rbp)
	movq $3, -608(%rbp) # t7109 {dead: []} = 3
	# t7076 {dead: []} = t7077 + t7109 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) + (3)}
	movq -600(%rbp), %rax
	addq -608(%rbp), %rax
	movq %rax, -616(%rbp)
	movq -616(%rbp), %rax # t7075 {dead: []} = image_s0[t7076]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7075 {dead: []} = image_s0[t7076]
	movq %rdx, -624(%rbp)
	movq $4, -632(%rbp) # t7122 {dead: []} = 4
	movq -632(%rbp), %rax # t7121 {dead: []} = unsharpKernel_s0[t7122]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7121 {dead: []} = unsharpKernel_s0[t7122]
	movq %rdx, -640(%rbp)
	# t7074 {dead: []} = t7075 * t7121 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) + (3)]) * (unsharpKernel_s0[4])}
	movq -624(%rbp), %rax
	imulq -640(%rbp), %rax
	movq %rax, -648(%rbp)
	movq -560(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -648(%rbp), %rax # unsharpMask_s0[t7053] {dead: []} += t7074
	addq %rax, (%rdi)
	movq $731, -656(%rbp) # t7139 {dead: []} = 731
	# t7137 {dead: []} = r_s45 * t7139 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -656(%rbp), %rax
	movq %rax, -664(%rbp)
	# t7136 {dead: []} = t7137 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -664(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -672(%rbp)
	movq $2193, -680(%rbp) # t7163 {dead: []} = 2193
	# t7161 {dead: []} = r_s45 * t7163 {canonical: (r_s45) * (2193)}
	movq -32(%rbp), %rax
	imulq -680(%rbp), %rax
	movq %rax, -688(%rbp)
	movq $3, -696(%rbp) # t7174 {dead: []} = 3
	# t7173 {dead: []} = t7174 * c_s45 {canonical: (3) * (c_s45)}
	movq -696(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -704(%rbp)
	# t7160 {dead: []} = t7161 + t7173 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	movq -688(%rbp), %rax
	addq -704(%rbp), %rax
	movq %rax, -712(%rbp)
	movq $6, -720(%rbp) # t7192 {dead: []} = 6
	# t7159 {dead: []} = t7160 + t7192 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) + (6)}
	movq -712(%rbp), %rax
	addq -720(%rbp), %rax
	movq %rax, -728(%rbp)
	movq -728(%rbp), %rax # t7158 {dead: []} = image_s0[t7159]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7158 {dead: []} = image_s0[t7159]
	movq %rdx, -736(%rbp)
	movq $5, -744(%rbp) # t7205 {dead: []} = 5
	movq -744(%rbp), %rax # t7204 {dead: []} = unsharpKernel_s0[t7205]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7204 {dead: []} = unsharpKernel_s0[t7205]
	movq %rdx, -752(%rbp)
	# t7157 {dead: []} = t7158 * t7204 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) + (6)]) * (unsharpKernel_s0[5])}
	movq -736(%rbp), %rax
	imulq -752(%rbp), %rax
	movq %rax, -760(%rbp)
	movq -672(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -760(%rbp), %rax # unsharpMask_s0[t7136] {dead: []} += t7157
	addq %rax, (%rdi)
	movq $731, -768(%rbp) # t7222 {dead: []} = 731
	# t7220 {dead: []} = r_s45 * t7222 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -768(%rbp), %rax
	movq %rax, -776(%rbp)
	# t7219 {dead: []} = t7220 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -776(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -784(%rbp)
	movq $2193, -792(%rbp) # t7246 {dead: []} = 2193
	# t7244 {dead: []} = r_s45 * t7246 {canonical: (r_s45) * (2193)}
	movq -32(%rbp), %rax
	imulq -792(%rbp), %rax
	movq %rax, -800(%rbp)
	movq $3, -808(%rbp) # t7257 {dead: []} = 3
	# t7256 {dead: []} = t7257 * c_s45 {canonical: (3) * (c_s45)}
	movq -808(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -816(%rbp)
	# t7243 {dead: []} = t7244 + t7256 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	movq -800(%rbp), %rax
	addq -816(%rbp), %rax
	movq %rax, -824(%rbp)
	movq $9, -832(%rbp) # t7275 {dead: []} = 9
	# t7242 {dead: []} = t7243 + t7275 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) + (9)}
	movq -824(%rbp), %rax
	addq -832(%rbp), %rax
	movq %rax, -840(%rbp)
	movq -840(%rbp), %rax # t7241 {dead: []} = image_s0[t7242]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7241 {dead: []} = image_s0[t7242]
	movq %rdx, -848(%rbp)
	movq $6, -856(%rbp) # t7288 {dead: []} = 6
	movq -856(%rbp), %rax # t7287 {dead: []} = unsharpKernel_s0[t7288]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7287 {dead: []} = unsharpKernel_s0[t7288]
	movq %rdx, -864(%rbp)
	# t7240 {dead: []} = t7241 * t7287 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) + (9)]) * (unsharpKernel_s0[6])}
	movq -848(%rbp), %rax
	imulq -864(%rbp), %rax
	movq %rax, -872(%rbp)
	movq -784(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -872(%rbp), %rax # unsharpMask_s0[t7219] {dead: []} += t7240
	addq %rax, (%rdi)
	movq $731, -880(%rbp) # t7305 {dead: []} = 731
	# t7303 {dead: []} = r_s45 * t7305 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -880(%rbp), %rax
	movq %rax, -888(%rbp)
	# t7302 {dead: []} = t7303 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -888(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -896(%rbp)
	movq $731, -904(%rbp) # t7327 {dead: []} = 731
	# t7325 {dead: []} = r_s45 * t7327 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -904(%rbp), %rax
	movq %rax, -912(%rbp)
	# t7324 {dead: []} = t7325 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -912(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -920(%rbp)
	movq -920(%rbp), %rax # t7323 {dead: []} = unsharpMask_s0[t7324]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7323 {dead: []} = unsharpMask_s0[t7324]
	movq %rdx, -928(%rbp)
	movq -896(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	# unsharpMask_s0[t7302] {dead: []} = t7323 / kernel_sum_s0 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) / (kernel_sum_s0)}
	movq -928(%rbp), %rax
	cqto
	idivq _global_kernel_sum(%rip)
	movq %rax, (%rdi)
	jmp _nop_7355
_nop_7355:
	jmp _end_of_block_6524
_end_of_block_6524:
	jmp _block_6514
_block_6514:
_block_7363:
	movq $1, -88(%rbp) # t7359 {dead: []} = 1
	movq -88(%rbp), %rax # c_s45 {dead: []} += t7359
	addq %rax, -40(%rbp)
	jmp _nop_7364
_nop_7364:
	jmp _end_of_block_6514
_end_of_block_6514:
	jmp _conditional_6525
_block_6527:
_block_7376:
	# c_s45 {dead: []} = cols_s0 - center_s45 {canonical: (cols_s0) - (center_s45)}
	movq _global_cols(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -40(%rbp)
	jmp _nop_7377
_nop_7377:
	jmp _end_of_block_6527
_end_of_block_6527:
	jmp _conditional_6531
_conditional_6531:
_block_7387:
	# t7378 {dead: []} = c_s45 < cols_s0 {canonical: (c_s45) < (cols_s0)}
	movq -40(%rbp), %rax # t7378 {dead: []} = c_s45 < cols_s0 {canonical: (c_s45) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_7389
_nop_7389:
	jmp _end_of_conditional_6531
_end_of_conditional_6531:
	cmpq $1, -88(%rbp) # true = t7378
	jne _block_6504 # ifFalse
	jmp _block_6530 # ifTrue
_block_6530:
_block_7446:
	movq $731, -88(%rbp) # t7395 {dead: []} = 731
	# t7393 {dead: []} = r_s45 * t7395 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t7392 {dead: []} = t7393 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2193, -112(%rbp) # t7416 {dead: []} = 2193
	# t7414 {dead: []} = r_s45 * t7416 {canonical: (r_s45) * (2193)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t7428 {dead: []} = 3
	# t7426 {dead: []} = c_s45 * t7428 {canonical: (c_s45) * (3)}
	movq -40(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t7413 {dead: []} = t7414 + t7426 {canonical: ((r_s45) * (2193)) + ((c_s45) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq -144(%rbp), %rax # unsharpMask_s0[t7392] {dead: []} = image_s0[t7413]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # unsharpMask_s0[t7392] {dead: []} = image_s0[t7413]
	movq %rdx, (%rdi)
	jmp _nop_7447
_nop_7447:
	jmp _end_of_block_6530
_end_of_block_6530:
	jmp _block_6528
_block_6528:
_block_7455:
	movq $1, -88(%rbp) # t7451 {dead: []} = 1
	movq -88(%rbp), %rax # c_s45 {dead: []} += t7451
	addq %rax, -40(%rbp)
	jmp _nop_7456
_nop_7456:
	jmp _end_of_block_6528
_end_of_block_6528:
	jmp _conditional_6531
_block_6504:
_block_7464:
	movq $1, -88(%rbp) # t7460 {dead: []} = 1
	movq -88(%rbp), %rax # r_s45 {dead: []} += t7460
	addq %rax, -32(%rbp)
	jmp _nop_7465
_nop_7465:
	jmp _end_of_block_6504
_end_of_block_6504:
	jmp _conditional_6532
_block_6535:
_block_7489:
	movq -48(%rbp), %rax # t7468 {dead: []} = center_s45 {canonical: center_s45}
	movq %rax, -88(%rbp)
	movq -48(%rbp), %rax # t7473 {dead: []} = center_s45 {canonical: center_s45}
	movq %rax, -96(%rbp)
	movq -96(%rbp), %rax # t7472 {dead: []} = unsharpKernel_s0[t7473]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7472 {dead: []} = unsharpKernel_s0[t7473]
	movq %rdx, -104(%rbp)
	movq -88(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	# unsharpKernel_s0[t7468] {dead: []} = t7472 - kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s45]) - (kernel_sum_s0)}
	movq -104(%rbp), %rax
	subq _global_kernel_sum(%rip), %rax
	movq %rax, (%rdi)
	movq $0, -40(%rbp) # c_s45 {dead: []} = 0
	jmp _nop_7490
_nop_7490:
	jmp _end_of_block_6535
_end_of_block_6535:
	jmp _conditional_6570
_conditional_6570:
_block_7500:
	# t7491 {dead: []} = c_s45 < cols_s0 {canonical: (c_s45) < (cols_s0)}
	movq -40(%rbp), %rax # t7491 {dead: []} = c_s45 < cols_s0 {canonical: (c_s45) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_7502
_nop_7502:
	jmp _end_of_conditional_6570
_end_of_conditional_6570:
	cmpq $1, -88(%rbp) # true = t7491
	jne _block_6571 # ifFalse
	jmp _block_6542 # ifTrue
_block_6542:
_block_7544:
	movq -40(%rbp), %rax # t7506 {dead: []} = c_s45 {canonical: c_s45}
	movq %rax, -88(%rbp)
	movq -88(%rbp), %rax # m1_s50 {dead: []} = unsharpMask_s0[t7506]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m1_s50 {dead: []} = unsharpMask_s0[t7506]
	movq %rdx, -56(%rbp)
	movq $731, -96(%rbp) # t7515 {dead: []} = 731
	# t7513 {dead: []} = c_s45 + t7515 {canonical: (c_s45) + (731)}
	movq -40(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # m2_s50 {dead: []} = unsharpMask_s0[t7513]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m2_s50 {dead: []} = unsharpMask_s0[t7513]
	movq %rdx, -64(%rbp)
	movq $1462, -112(%rbp) # t7530 {dead: []} = 1462
	# t7528 {dead: []} = c_s45 + t7530 {canonical: (c_s45) + (1462)}
	movq -40(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rax # m3_s50 {dead: []} = unsharpMask_s0[t7528]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m3_s50 {dead: []} = unsharpMask_s0[t7528]
	movq %rdx, -72(%rbp)
	movq $0, -32(%rbp) # r_s45 {dead: []} = 0
	jmp _nop_7545
_nop_7545:
	jmp _end_of_block_6542
_end_of_block_6542:
	jmp _conditional_6546
_conditional_6546:
_block_7555:
	# t7546 {dead: []} = r_s45 < center_s45 {canonical: (r_s45) < (center_s45)}
	movq -32(%rbp), %rax # t7546 {dead: []} = r_s45 < center_s45 {canonical: (r_s45) < (center_s45)}
	cmpq -48(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_7557
_nop_7557:
	jmp _end_of_conditional_6546
_end_of_conditional_6546:
	cmpq $1, -88(%rbp) # true = t7546
	jne _block_6548 # ifFalse
	jmp _block_6545 # ifTrue
_block_6545:
_block_7582:
	movq $731, -88(%rbp) # t7563 {dead: []} = 731
	# t7561 {dead: []} = r_s45 * t7563 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t7560 {dead: []} = t7561 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # unsharpMask_s0[t7560] {dead: []} = 0
	jmp _nop_7583
_nop_7583:
	jmp _end_of_block_6545
_end_of_block_6545:
	jmp _block_6543
_block_6543:
_block_7591:
	movq $1, -88(%rbp) # t7587 {dead: []} = 1
	movq -88(%rbp), %rax # r_s45 {dead: []} += t7587
	addq %rax, -32(%rbp)
	jmp _nop_7592
_nop_7592:
	jmp _end_of_block_6543
_end_of_block_6543:
	jmp _conditional_6546
_block_6548:
_block_7598:
	movq -48(%rbp), %rax # r_s45 {dead: []} = center_s45 {canonical: center_s45}
	movq %rax, -32(%rbp)
	jmp _nop_7599
_nop_7599:
	jmp _end_of_block_6548
_end_of_block_6548:
	jmp _conditional_6563
_conditional_6563:
_block_7618:
	# t7602 {dead: []} = rows_s0 - center_s45 {canonical: (rows_s0) - (center_s45)}
	movq _global_rows(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -88(%rbp)
	# t7600 {dead: []} = r_s45 < t7602 {canonical: (r_s45) < ((rows_s0) - (center_s45))}
	movq -32(%rbp), %rax # t7600 {dead: []} = r_s45 < t7602 {canonical: (r_s45) < ((rows_s0) - (center_s45))}
	cmpq -88(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -96(%rbp)
	jmp _nop_7620
_nop_7620:
	jmp _end_of_conditional_6563
_end_of_conditional_6563:
	cmpq $1, -96(%rbp) # true = t7600
	jne _block_6565 # ifFalse
	jmp _block_6562 # ifTrue
_block_6562:
_block_8162:
	movq $0, -80(%rbp) # dot_s52 {dead: []} = 0
	movq $731, -88(%rbp) # t7632 {dead: []} = 731
	# t7630 {dead: []} = r_s45 * t7632 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t7629 {dead: []} = t7630 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # t7628 {dead: []} = unsharpMask_s0[t7629]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7628 {dead: []} = unsharpMask_s0[t7629]
	movq %rdx, -112(%rbp)
	movq $0, -120(%rbp) # t7655 {dead: []} = 0
	movq -120(%rbp), %rax # t7654 {dead: []} = unsharpKernel_s0[t7655]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7654 {dead: []} = unsharpKernel_s0[t7655]
	movq %rdx, -128(%rbp)
	# t7652 {dead: []} = m1_s50 * t7654 {canonical: (m1_s50) * (unsharpKernel_s0[0])}
	movq -56(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t7627 {dead: []} = t7628 + t7652 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((m1_s50) * (unsharpKernel_s0[0]))}
	movq -112(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq -144(%rbp), %rax # dot_s52 {dead: []} += t7627
	addq %rax, -80(%rbp)
	movq $731, -152(%rbp) # t7682 {dead: []} = 731
	# t7680 {dead: []} = r_s45 * t7682 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	# t7679 {dead: []} = t7680 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -160(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -168(%rbp)
	movq -168(%rbp), %rax # t7678 {dead: []} = unsharpMask_s0[t7679]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7678 {dead: []} = unsharpMask_s0[t7679]
	movq %rdx, -176(%rbp)
	movq $1, -184(%rbp) # t7705 {dead: []} = 1
	movq -184(%rbp), %rax # t7704 {dead: []} = unsharpKernel_s0[t7705]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7704 {dead: []} = unsharpKernel_s0[t7705]
	movq %rdx, -192(%rbp)
	# t7702 {dead: []} = m2_s50 * t7704 {canonical: (m2_s50) * (unsharpKernel_s0[1])}
	movq -64(%rbp), %rax
	imulq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	# t7677 {dead: []} = t7678 + t7702 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((m2_s50) * (unsharpKernel_s0[1]))}
	movq -176(%rbp), %rax
	addq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	movq -208(%rbp), %rax # dot_s52 {dead: []} += t7677
	addq %rax, -80(%rbp)
	movq $731, -216(%rbp) # t7732 {dead: []} = 731
	# t7730 {dead: []} = r_s45 * t7732 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -216(%rbp), %rax
	movq %rax, -224(%rbp)
	# t7729 {dead: []} = t7730 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -224(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -232(%rbp)
	movq -232(%rbp), %rax # t7728 {dead: []} = unsharpMask_s0[t7729]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7728 {dead: []} = unsharpMask_s0[t7729]
	movq %rdx, -240(%rbp)
	movq $2, -248(%rbp) # t7755 {dead: []} = 2
	movq -248(%rbp), %rax # t7754 {dead: []} = unsharpKernel_s0[t7755]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7754 {dead: []} = unsharpKernel_s0[t7755]
	movq %rdx, -256(%rbp)
	# t7752 {dead: []} = m3_s50 * t7754 {canonical: (m3_s50) * (unsharpKernel_s0[2])}
	movq -72(%rbp), %rax
	imulq -256(%rbp), %rax
	movq %rax, -264(%rbp)
	# t7727 {dead: []} = t7728 + t7752 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((m3_s50) * (unsharpKernel_s0[2]))}
	movq -240(%rbp), %rax
	addq -264(%rbp), %rax
	movq %rax, -272(%rbp)
	movq -272(%rbp), %rax # dot_s52 {dead: []} += t7727
	addq %rax, -80(%rbp)
	movq $731, -280(%rbp) # t7782 {dead: []} = 731
	# t7780 {dead: []} = r_s45 * t7782 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -280(%rbp), %rax
	movq %rax, -288(%rbp)
	# t7779 {dead: []} = t7780 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -288(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -296(%rbp)
	movq -296(%rbp), %rax # t7778 {dead: []} = unsharpMask_s0[t7779]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7778 {dead: []} = unsharpMask_s0[t7779]
	movq %rdx, -304(%rbp)
	movq $731, -312(%rbp) # t7806 {dead: []} = 731
	# t7805 {dead: []} = t7806 * r_s45 {canonical: (731) * (r_s45)}
	movq -312(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -320(%rbp)
	# t7804 {dead: []} = t7805 + c_s45 {canonical: ((731) * (r_s45)) + (c_s45)}
	movq -320(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -328(%rbp)
	movq -328(%rbp), %rax # t7803 {dead: []} = unsharpMask_s0[t7804]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7803 {dead: []} = unsharpMask_s0[t7804]
	movq %rdx, -336(%rbp)
	movq $3, -344(%rbp) # t7828 {dead: []} = 3
	movq -344(%rbp), %rax # t7827 {dead: []} = unsharpKernel_s0[t7828]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7827 {dead: []} = unsharpKernel_s0[t7828]
	movq %rdx, -352(%rbp)
	# t7802 {dead: []} = t7803 * t7827 {canonical: (unsharpMask_s0[((731) * (r_s45)) + (c_s45)]) * (unsharpKernel_s0[3])}
	movq -336(%rbp), %rax
	imulq -352(%rbp), %rax
	movq %rax, -360(%rbp)
	# t7777 {dead: []} = t7778 + t7802 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((unsharpMask_s0[((731) * (r_s45)) + (c_s45)]) * (unsharpKernel_s0[3]))}
	movq -304(%rbp), %rax
	addq -360(%rbp), %rax
	movq %rax, -368(%rbp)
	movq -368(%rbp), %rax # dot_s52 {dead: []} += t7777
	addq %rax, -80(%rbp)
	movq $731, -376(%rbp) # t7855 {dead: []} = 731
	# t7853 {dead: []} = r_s45 * t7855 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -376(%rbp), %rax
	movq %rax, -384(%rbp)
	# t7852 {dead: []} = t7853 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -384(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -392(%rbp)
	movq -392(%rbp), %rax # t7851 {dead: []} = unsharpMask_s0[t7852]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7851 {dead: []} = unsharpMask_s0[t7852]
	movq %rdx, -400(%rbp)
	movq $731, -408(%rbp) # t7880 {dead: []} = 731
	# t7879 {dead: []} = t7880 * r_s45 {canonical: (731) * (r_s45)}
	movq -408(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -416(%rbp)
	# t7878 {dead: []} = t7879 + c_s45 {canonical: ((731) * (r_s45)) + (c_s45)}
	movq -416(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -424(%rbp)
	movq $731, -432(%rbp) # t7899 {dead: []} = 731
	# t7877 {dead: []} = t7878 + t7899 {canonical: (((731) * (r_s45)) + (c_s45)) + (731)}
	movq -424(%rbp), %rax
	addq -432(%rbp), %rax
	movq %rax, -440(%rbp)
	movq -440(%rbp), %rax # t7876 {dead: []} = unsharpMask_s0[t7877]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7876 {dead: []} = unsharpMask_s0[t7877]
	movq %rdx, -448(%rbp)
	movq $4, -456(%rbp) # t7912 {dead: []} = 4
	movq -456(%rbp), %rax # t7911 {dead: []} = unsharpKernel_s0[t7912]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7911 {dead: []} = unsharpKernel_s0[t7912]
	movq %rdx, -464(%rbp)
	# t7875 {dead: []} = t7876 * t7911 {canonical: (unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (731)]) * (unsharpKernel_s0[4])}
	movq -448(%rbp), %rax
	imulq -464(%rbp), %rax
	movq %rax, -472(%rbp)
	# t7850 {dead: []} = t7851 + t7875 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (731)]) * (unsharpKernel_s0[4]))}
	movq -400(%rbp), %rax
	addq -472(%rbp), %rax
	movq %rax, -480(%rbp)
	movq -480(%rbp), %rax # dot_s52 {dead: []} += t7850
	addq %rax, -80(%rbp)
	movq $731, -488(%rbp) # t7939 {dead: []} = 731
	# t7937 {dead: []} = r_s45 * t7939 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -488(%rbp), %rax
	movq %rax, -496(%rbp)
	# t7936 {dead: []} = t7937 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -496(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -504(%rbp)
	movq -504(%rbp), %rax # t7935 {dead: []} = unsharpMask_s0[t7936]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7935 {dead: []} = unsharpMask_s0[t7936]
	movq %rdx, -512(%rbp)
	movq $731, -520(%rbp) # t7964 {dead: []} = 731
	# t7963 {dead: []} = t7964 * r_s45 {canonical: (731) * (r_s45)}
	movq -520(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -528(%rbp)
	# t7962 {dead: []} = t7963 + c_s45 {canonical: ((731) * (r_s45)) + (c_s45)}
	movq -528(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -536(%rbp)
	movq $1462, -544(%rbp) # t7983 {dead: []} = 1462
	# t7961 {dead: []} = t7962 + t7983 {canonical: (((731) * (r_s45)) + (c_s45)) + (1462)}
	movq -536(%rbp), %rax
	addq -544(%rbp), %rax
	movq %rax, -552(%rbp)
	movq -552(%rbp), %rax # t7960 {dead: []} = unsharpMask_s0[t7961]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7960 {dead: []} = unsharpMask_s0[t7961]
	movq %rdx, -560(%rbp)
	movq $5, -568(%rbp) # t7996 {dead: []} = 5
	movq -568(%rbp), %rax # t7995 {dead: []} = unsharpKernel_s0[t7996]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t7995 {dead: []} = unsharpKernel_s0[t7996]
	movq %rdx, -576(%rbp)
	# t7959 {dead: []} = t7960 * t7995 {canonical: (unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (1462)]) * (unsharpKernel_s0[5])}
	movq -560(%rbp), %rax
	imulq -576(%rbp), %rax
	movq %rax, -584(%rbp)
	# t7934 {dead: []} = t7935 + t7959 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (1462)]) * (unsharpKernel_s0[5]))}
	movq -512(%rbp), %rax
	addq -584(%rbp), %rax
	movq %rax, -592(%rbp)
	movq -592(%rbp), %rax # dot_s52 {dead: []} += t7934
	addq %rax, -80(%rbp)
	movq $731, -600(%rbp) # t8023 {dead: []} = 731
	# t8021 {dead: []} = r_s45 * t8023 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -600(%rbp), %rax
	movq %rax, -608(%rbp)
	# t8020 {dead: []} = t8021 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -608(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -616(%rbp)
	movq -616(%rbp), %rax # t8019 {dead: []} = unsharpMask_s0[t8020]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t8019 {dead: []} = unsharpMask_s0[t8020]
	movq %rdx, -624(%rbp)
	movq $731, -632(%rbp) # t8048 {dead: []} = 731
	# t8047 {dead: []} = t8048 * r_s45 {canonical: (731) * (r_s45)}
	movq -632(%rbp), %rax
	imulq -32(%rbp), %rax
	movq %rax, -640(%rbp)
	# t8046 {dead: []} = t8047 + c_s45 {canonical: ((731) * (r_s45)) + (c_s45)}
	movq -640(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -648(%rbp)
	movq $2193, -656(%rbp) # t8067 {dead: []} = 2193
	# t8045 {dead: []} = t8046 + t8067 {canonical: (((731) * (r_s45)) + (c_s45)) + (2193)}
	movq -648(%rbp), %rax
	addq -656(%rbp), %rax
	movq %rax, -664(%rbp)
	movq -664(%rbp), %rax # t8044 {dead: []} = unsharpMask_s0[t8045]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t8044 {dead: []} = unsharpMask_s0[t8045]
	movq %rdx, -672(%rbp)
	movq $6, -680(%rbp) # t8080 {dead: []} = 6
	movq -680(%rbp), %rax # t8079 {dead: []} = unsharpKernel_s0[t8080]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t8079 {dead: []} = unsharpKernel_s0[t8080]
	movq %rdx, -688(%rbp)
	# t8043 {dead: []} = t8044 * t8079 {canonical: (unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (2193)]) * (unsharpKernel_s0[6])}
	movq -672(%rbp), %rax
	imulq -688(%rbp), %rax
	movq %rax, -696(%rbp)
	# t8018 {dead: []} = t8019 + t8043 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (2193)]) * (unsharpKernel_s0[6]))}
	movq -624(%rbp), %rax
	addq -696(%rbp), %rax
	movq %rax, -704(%rbp)
	movq -704(%rbp), %rax # dot_s52 {dead: []} += t8018
	addq %rax, -80(%rbp)
	movq -64(%rbp), %rax # m1_s50 {dead: []} = m2_s50 {canonical: m2_s50}
	movq %rax, -56(%rbp)
	movq -72(%rbp), %rax # m2_s50 {dead: []} = m3_s50 {canonical: m3_s50}
	movq %rax, -64(%rbp)
	movq $731, -712(%rbp) # t8113 {dead: []} = 731
	# t8111 {dead: []} = r_s45 * t8113 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -712(%rbp), %rax
	movq %rax, -720(%rbp)
	# t8110 {dead: []} = t8111 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -720(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -728(%rbp)
	movq -728(%rbp), %rax # m3_s50 {dead: []} = unsharpMask_s0[t8110]
	cmpq $750000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpMask(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # m3_s50 {dead: []} = unsharpMask_s0[t8110]
	movq %rdx, -72(%rbp)
	movq $731, -736(%rbp) # t8136 {dead: []} = 731
	# t8134 {dead: []} = r_s45 * t8136 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -736(%rbp), %rax
	movq %rax, -744(%rbp)
	# t8133 {dead: []} = t8134 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -744(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -752(%rbp)
	movq -752(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	# unsharpMask_s0[t8133] {dead: []} = dot_s52 / kernel_sum_s0 {canonical: (dot_s52) / (kernel_sum_s0)}
	movq -80(%rbp), %rax
	cqto
	idivq _global_kernel_sum(%rip)
	movq %rax, (%rdi)
	jmp _nop_8163
_nop_8163:
	jmp _end_of_block_6562
_end_of_block_6562:
	jmp _block_6549
_block_6549:
_block_8171:
	movq $1, -88(%rbp) # t8167 {dead: []} = 1
	movq -88(%rbp), %rax # r_s45 {dead: []} += t8167
	addq %rax, -32(%rbp)
	jmp _nop_8172
_nop_8172:
	jmp _end_of_block_6549
_end_of_block_6549:
	jmp _conditional_6563
_block_6565:
_block_8184:
	# r_s45 {dead: []} = rows_s0 - center_s45 {canonical: (rows_s0) - (center_s45)}
	movq _global_rows(%rip), %rax
	subq -48(%rbp), %rax
	movq %rax, -32(%rbp)
	jmp _nop_8185
_nop_8185:
	jmp _end_of_block_6565
_end_of_block_6565:
	jmp _conditional_6569
_conditional_6569:
_block_8195:
	# t8186 {dead: []} = r_s45 < rows_s0 {canonical: (r_s45) < (rows_s0)}
	movq -32(%rbp), %rax # t8186 {dead: []} = r_s45 < rows_s0 {canonical: (r_s45) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_8197
_nop_8197:
	jmp _end_of_conditional_6569
_end_of_conditional_6569:
	cmpq $1, -88(%rbp) # true = t8186
	jne _block_6536 # ifFalse
	jmp _block_6568 # ifTrue
_block_6568:
_block_8222:
	movq $731, -88(%rbp) # t8203 {dead: []} = 731
	# t8201 {dead: []} = r_s45 * t8203 {canonical: (r_s45) * (731)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t8200 {dead: []} = t8201 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	movq -96(%rbp), %rax
	addq -40(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $750000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpMask, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # unsharpMask_s0[t8200] {dead: []} = 0
	jmp _nop_8223
_nop_8223:
	jmp _end_of_block_6568
_end_of_block_6568:
	jmp _block_6566
_block_6566:
_block_8231:
	movq $1, -88(%rbp) # t8227 {dead: []} = 1
	movq -88(%rbp), %rax # r_s45 {dead: []} += t8227
	addq %rax, -32(%rbp)
	jmp _nop_8232
_nop_8232:
	jmp _end_of_block_6566
_end_of_block_6566:
	jmp _conditional_6569
_block_6536:
_block_8240:
	movq $1, -88(%rbp) # t8236 {dead: []} = 1
	movq -88(%rbp), %rax # c_s45 {dead: []} += t8236
	addq %rax, -40(%rbp)
	jmp _nop_8241
_nop_8241:
	jmp _end_of_block_6536
_end_of_block_6536:
	jmp _conditional_6570
_block_6571:
_block_8262:
	movq -48(%rbp), %rax # t8244 {dead: []} = center_s45 {canonical: center_s45}
	movq %rax, -88(%rbp)
	movq -48(%rbp), %rax # t8249 {dead: []} = center_s45 {canonical: center_s45}
	movq %rax, -96(%rbp)
	movq -96(%rbp), %rax # t8248 {dead: []} = unsharpKernel_s0[t8249]
	cmpq $9, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_unsharpKernel(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t8248 {dead: []} = unsharpKernel_s0[t8249]
	movq %rdx, -104(%rbp)
	movq -88(%rbp), %rdi
	cmpq $9, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_unsharpKernel, %rdi
	add %rcx, %rdi
	# unsharpKernel_s0[t8244] {dead: []} = t8248 + kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s45]) + (kernel_sum_s0)}
	movq -104(%rbp), %rax
	addq _global_kernel_sum(%rip), %rax
	movq %rax, (%rdi)
	jmp _nop_8263
_nop_8263:
	jmp _end_of_block_6571
_end_of_block_6571:
	jmp _return_6572
_return_6572:
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

_method_read_file:
	enter $(208), $0
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
_block_8271:
_block_8304:
	# ppm_open_for_read_s0["data/input.ppm"]
	leaq _string_0(%rip), %rdi
	call ppm_open_for_read
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	# ppm_get_cols_s0[]
	call ppm_get_cols
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	
	movq %rax, _global_cols(%rip) # cols_s0 {dead: []} = load %rax
	
	# ppm_get_rows_s0[]
	call ppm_get_rows
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	
	movq %rax, _global_rows(%rip) # rows_s0 {dead: []} = load %rax
	
	movq $0, -24(%rbp) # r_s2 {dead: []} = 0
	jmp _nop_8305
_nop_8305:
	jmp _end_of_block_8271
_end_of_block_8271:
	jmp _conditional_8282
_conditional_8282:
_block_8315:
	# t8306 {dead: []} = r_s2 < rows_s0 {canonical: (r_s2) < (rows_s0)}
	movq -24(%rbp), %rax # t8306 {dead: []} = r_s2 < rows_s0 {canonical: (r_s2) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -40(%rbp)
	jmp _nop_8317
_nop_8317:
	jmp _end_of_conditional_8282
_end_of_conditional_8282:
	cmpq $1, -40(%rbp) # true = t8306
	jne _block_8283 # ifFalse
	jmp _block_8275 # ifTrue
_block_8275:
_block_8322:
	movq $0, -32(%rbp) # c_s2 {dead: []} = 0
	jmp _nop_8323
_nop_8323:
	jmp _end_of_block_8275
_end_of_block_8275:
	jmp _conditional_8281
_conditional_8281:
_block_8333:
	# t8324 {dead: []} = c_s2 < cols_s0 {canonical: (c_s2) < (cols_s0)}
	movq -32(%rbp), %rax # t8324 {dead: []} = c_s2 < cols_s0 {canonical: (c_s2) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -40(%rbp)
	jmp _nop_8335
_nop_8335:
	jmp _end_of_conditional_8281
_end_of_conditional_8281:
	cmpq $1, -40(%rbp) # true = t8324
	jne _block_8272 # ifFalse
	jmp _block_8280 # ifTrue
_block_8280:
_block_8481:
	movq $2193, -40(%rbp) # t8342 {dead: []} = 2193
	# t8340 {dead: []} = r_s2 * t8342 {canonical: (r_s2) * (2193)}
	movq -24(%rbp), %rax
	imulq -40(%rbp), %rax
	movq %rax, -48(%rbp)
	movq $3, -56(%rbp) # t8354 {dead: []} = 3
	# t8352 {dead: []} = c_s2 * t8354 {canonical: (c_s2) * (3)}
	movq -32(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	# t8339 {dead: []} = t8340 + t8352 {canonical: ((r_s2) * (2193)) + ((c_s2) * (3))}
	movq -48(%rbp), %rax
	addq -64(%rbp), %rax
	movq %rax, -72(%rbp)
	movq $0, -80(%rbp) # t8371 {dead: []} = 0
	# t8338 {dead: []} = t8339 + t8371 {canonical: (((r_s2) * (2193)) + ((c_s2) * (3))) + (0)}
	movq -72(%rbp), %rax
	addq -80(%rbp), %rax
	movq %rax, -88(%rbp)
	# ppm_get_next_pixel_color_s0[]
	call ppm_get_next_pixel_color
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq -88(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	
	movq %rax, (%rdi) # image_s0[t8338] {dead: []} = load %rax
	
	movq $2193, -96(%rbp) # t8390 {dead: []} = 2193
	# t8388 {dead: []} = r_s2 * t8390 {canonical: (r_s2) * (2193)}
	movq -24(%rbp), %rax
	imulq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $3, -112(%rbp) # t8402 {dead: []} = 3
	# t8400 {dead: []} = c_s2 * t8402 {canonical: (c_s2) * (3)}
	movq -32(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	# t8387 {dead: []} = t8388 + t8400 {canonical: ((r_s2) * (2193)) + ((c_s2) * (3))}
	movq -104(%rbp), %rax
	addq -120(%rbp), %rax
	movq %rax, -128(%rbp)
	movq $1, -136(%rbp) # t8419 {dead: []} = 1
	# t8386 {dead: []} = t8387 + t8419 {canonical: (((r_s2) * (2193)) + ((c_s2) * (3))) + (1)}
	movq -128(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	# ppm_get_next_pixel_color_s0[]
	call ppm_get_next_pixel_color
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq -144(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	
	movq %rax, (%rdi) # image_s0[t8386] {dead: []} = load %rax
	
	movq $2193, -152(%rbp) # t8438 {dead: []} = 2193
	# t8436 {dead: []} = r_s2 * t8438 {canonical: (r_s2) * (2193)}
	movq -24(%rbp), %rax
	imulq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq $3, -168(%rbp) # t8450 {dead: []} = 3
	# t8448 {dead: []} = c_s2 * t8450 {canonical: (c_s2) * (3)}
	movq -32(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	# t8435 {dead: []} = t8436 + t8448 {canonical: ((r_s2) * (2193)) + ((c_s2) * (3))}
	movq -160(%rbp), %rax
	addq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	movq $2, -192(%rbp) # t8467 {dead: []} = 2
	# t8434 {dead: []} = t8435 + t8467 {canonical: (((r_s2) * (2193)) + ((c_s2) * (3))) + (2)}
	movq -184(%rbp), %rax
	addq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	# ppm_get_next_pixel_color_s0[]
	call ppm_get_next_pixel_color
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	movq -200(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	
	movq %rax, (%rdi) # image_s0[t8434] {dead: []} = load %rax
	
	jmp _nop_8482
_nop_8482:
	jmp _end_of_block_8280
_end_of_block_8280:
	jmp _block_8276
_block_8276:
_block_8490:
	movq $1, -40(%rbp) # t8486 {dead: []} = 1
	movq -40(%rbp), %rax # c_s2 {dead: []} += t8486
	addq %rax, -32(%rbp)
	jmp _nop_8491
_nop_8491:
	jmp _end_of_block_8276
_end_of_block_8276:
	jmp _conditional_8281
_block_8272:
_block_8499:
	movq $1, -40(%rbp) # t8495 {dead: []} = 1
	movq -40(%rbp), %rax # r_s2 {dead: []} += t8495
	addq %rax, -24(%rbp)
	jmp _nop_8500
_nop_8500:
	jmp _end_of_block_8272
_end_of_block_8272:
	jmp _conditional_8282
_block_8283:
_block_8505:
	# ppm_close_s0[]
	call ppm_close
	cmpq $0, _sp_exit_code
	jne _sp_method_premature_return # premature exit if the call resulted in runtime exception
	jmp _nop_8506
_nop_8506:
	jmp _end_of_block_8283
_end_of_block_8283:
	jmp _return_8284
_return_8284:
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

_method_levels:
	enter $(224), $0
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
_block_8513:
_block_8563:
	movq $10, -48(%rbp) # b_s90 {dead: []} = 10
	movq $243, -64(%rbp) # w_s90 {dead: []} = 243
	movq $0, -40(%rbp) # r_s90 {dead: []} = 0
	jmp _nop_8564
_nop_8564:
	jmp _end_of_block_8513
_end_of_block_8513:
	jmp _conditional_8551
_conditional_8551:
_block_8574:
	# t8565 {dead: []} = r_s90 < rows_s0 {canonical: (r_s90) < (rows_s0)}
	movq -40(%rbp), %rax # t8565 {dead: []} = r_s90 < rows_s0 {canonical: (r_s90) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -72(%rbp)
	jmp _nop_8576
_nop_8576:
	jmp _end_of_conditional_8551
_end_of_conditional_8551:
	cmpq $1, -72(%rbp) # true = t8565
	jne _return_8552 # ifFalse
	jmp _block_8517 # ifTrue
_block_8517:
_block_8581:
	movq $0, -56(%rbp) # c_s90 {dead: []} = 0
	jmp _nop_8582
_nop_8582:
	jmp _end_of_block_8517
_end_of_block_8517:
	jmp _conditional_8550
_conditional_8550:
_block_8592:
	# t8583 {dead: []} = c_s90 < cols_s0 {canonical: (c_s90) < (cols_s0)}
	movq -56(%rbp), %rax # t8583 {dead: []} = c_s90 < cols_s0 {canonical: (c_s90) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -72(%rbp)
	jmp _nop_8594
_nop_8594:
	jmp _end_of_conditional_8550
_end_of_conditional_8550:
	cmpq $1, -72(%rbp) # true = t8583
	jne _block_8514 # ifFalse
	jmp _block_8520 # ifTrue
_block_8520:
_block_8700:
	movq $2193, -72(%rbp) # t8600 {dead: []} = 2193
	# t8598 {dead: []} = r_s90 * t8600 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t8612 {dead: []} = 3
	# t8610 {dead: []} = c_s90 * t8612 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t8597 {dead: []} = t8598 + t8610 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2193, -112(%rbp) # t8635 {dead: []} = 2193
	# t8633 {dead: []} = r_s90 * t8635 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $3, -128(%rbp) # t8647 {dead: []} = 3
	# t8645 {dead: []} = c_s90 * t8647 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	# t8632 {dead: []} = t8633 + t8645 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -120(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq -144(%rbp), %rax # t8631 {dead: []} = image_s0[t8632]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t8631 {dead: []} = image_s0[t8632]
	movq %rdx, -152(%rbp)
	# t8630 {dead: []} = t8631 - b_s90 {canonical: (image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) - (b_s90)}
	movq -152(%rbp), %rax
	subq -48(%rbp), %rax
	movq %rax, -160(%rbp)
	movq $255, -168(%rbp) # t8674 {dead: []} = 255
	# t8629 {dead: []} = t8630 * t8674 {canonical: ((image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) - (b_s90)) * (255)}
	movq -160(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	# t8684 {dead: []} = w_s90 - b_s90 {canonical: (w_s90) - (b_s90)}
	movq -64(%rbp), %rax
	subq -48(%rbp), %rax
	movq %rax, -184(%rbp)
	movq -104(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	# image_s0[t8597] {dead: []} = t8629 / t8684 {canonical: (((image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) - (b_s90)) * (255)) / ((w_s90) - (b_s90))}
	movq -176(%rbp), %rax
	cqto
	idivq -184(%rbp)
	movq %rax, (%rdi)
	jmp _nop_8701
_nop_8701:
	jmp _end_of_block_8520
_end_of_block_8520:
	jmp _conditional_8529
_conditional_8529:
_block_8747:
	movq $2193, -72(%rbp) # t8707 {dead: []} = 2193
	# t8705 {dead: []} = r_s90 * t8707 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t8719 {dead: []} = 3
	# t8717 {dead: []} = c_s90 * t8719 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t8704 {dead: []} = t8705 + t8717 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # t8703 {dead: []} = image_s0[t8704]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t8703 {dead: []} = image_s0[t8704]
	movq %rdx, -112(%rbp)
	movq $0, -120(%rbp) # t8738 {dead: []} = 0
	# t8702 {dead: []} = t8703 < t8738 {canonical: (image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) < (0)}
	movq -112(%rbp), %rax # t8702 {dead: []} = t8703 < t8738 {canonical: (image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) < (0)}
	cmpq -120(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -128(%rbp)
	jmp _nop_8749
_nop_8749:
	jmp _end_of_conditional_8529
_end_of_conditional_8529:
	cmpq $1, -128(%rbp) # true = t8702
	jne _conditional_8528 # ifFalse
	jmp _block_8523 # ifTrue
_block_8523:
_block_8785:
	movq $2193, -72(%rbp) # t8755 {dead: []} = 2193
	# t8753 {dead: []} = r_s90 * t8755 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t8767 {dead: []} = 3
	# t8765 {dead: []} = c_s90 * t8767 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t8752 {dead: []} = t8753 + t8765 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # image_s0[t8752] {dead: []} = 0
	jmp _nop_8786
_nop_8786:
	jmp _end_of_block_8523
_end_of_block_8523:
	jmp _block_8530
_block_8530:
_block_8914:
	movq $2193, -72(%rbp) # t8793 {dead: []} = 2193
	# t8791 {dead: []} = r_s90 * t8793 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t8805 {dead: []} = 3
	# t8803 {dead: []} = c_s90 * t8805 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t8790 {dead: []} = t8791 + t8803 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $1, -112(%rbp) # t8822 {dead: []} = 1
	# t8789 {dead: []} = t8790 + t8822 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $2193, -128(%rbp) # t8839 {dead: []} = 2193
	# t8837 {dead: []} = r_s90 * t8839 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq $3, -144(%rbp) # t8851 {dead: []} = 3
	# t8849 {dead: []} = c_s90 * t8851 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -144(%rbp), %rax
	movq %rax, -152(%rbp)
	# t8836 {dead: []} = t8837 + t8849 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -136(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq $1, -168(%rbp) # t8868 {dead: []} = 1
	# t8835 {dead: []} = t8836 + t8868 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	movq -160(%rbp), %rax
	addq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	movq -176(%rbp), %rax # t8834 {dead: []} = image_s0[t8835]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t8834 {dead: []} = image_s0[t8835]
	movq %rdx, -184(%rbp)
	# t8833 {dead: []} = t8834 - b_s90 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) - (b_s90)}
	movq -184(%rbp), %rax
	subq -48(%rbp), %rax
	movq %rax, -192(%rbp)
	movq $255, -200(%rbp) # t8888 {dead: []} = 255
	# t8832 {dead: []} = t8833 * t8888 {canonical: ((image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) - (b_s90)) * (255)}
	movq -192(%rbp), %rax
	imulq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	# t8898 {dead: []} = w_s90 - b_s90 {canonical: (w_s90) - (b_s90)}
	movq -64(%rbp), %rax
	subq -48(%rbp), %rax
	movq %rax, -216(%rbp)
	movq -120(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	# image_s0[t8789] {dead: []} = t8832 / t8898 {canonical: (((image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) - (b_s90)) * (255)) / ((w_s90) - (b_s90))}
	movq -208(%rbp), %rax
	cqto
	idivq -216(%rbp)
	movq %rax, (%rdi)
	jmp _nop_8915
_nop_8915:
	jmp _end_of_block_8530
_end_of_block_8530:
	jmp _conditional_8539
_conditional_8539:
_block_8972:
	movq $2193, -72(%rbp) # t8922 {dead: []} = 2193
	# t8920 {dead: []} = r_s90 * t8922 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t8934 {dead: []} = 3
	# t8932 {dead: []} = c_s90 * t8934 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t8919 {dead: []} = t8920 + t8932 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $1, -112(%rbp) # t8951 {dead: []} = 1
	# t8918 {dead: []} = t8919 + t8951 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rax # t8917 {dead: []} = image_s0[t8918]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t8917 {dead: []} = image_s0[t8918]
	movq %rdx, -128(%rbp)
	movq $0, -136(%rbp) # t8963 {dead: []} = 0
	# t8916 {dead: []} = t8917 < t8963 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) < (0)}
	movq -128(%rbp), %rax # t8916 {dead: []} = t8917 < t8963 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) < (0)}
	cmpq -136(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -144(%rbp)
	jmp _nop_8974
_nop_8974:
	jmp _end_of_conditional_8539
_end_of_conditional_8539:
	cmpq $1, -144(%rbp) # true = t8916
	jne _conditional_8538 # ifFalse
	jmp _block_8533 # ifTrue
_block_8533:
_block_9021:
	movq $2193, -72(%rbp) # t8981 {dead: []} = 2193
	# t8979 {dead: []} = r_s90 * t8981 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t8993 {dead: []} = 3
	# t8991 {dead: []} = c_s90 * t8993 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t8978 {dead: []} = t8979 + t8991 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $1, -112(%rbp) # t9010 {dead: []} = 1
	# t8977 {dead: []} = t8978 + t9010 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # image_s0[t8977] {dead: []} = 0
	jmp _nop_9022
_nop_9022:
	jmp _end_of_block_8533
_end_of_block_8533:
	jmp _block_8540
_block_8540:
_block_9150:
	movq $2193, -72(%rbp) # t9029 {dead: []} = 2193
	# t9027 {dead: []} = r_s90 * t9029 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t9041 {dead: []} = 3
	# t9039 {dead: []} = c_s90 * t9041 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t9026 {dead: []} = t9027 + t9039 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2, -112(%rbp) # t9058 {dead: []} = 2
	# t9025 {dead: []} = t9026 + t9058 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $2193, -128(%rbp) # t9075 {dead: []} = 2193
	# t9073 {dead: []} = r_s90 * t9075 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq $3, -144(%rbp) # t9087 {dead: []} = 3
	# t9085 {dead: []} = c_s90 * t9087 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -144(%rbp), %rax
	movq %rax, -152(%rbp)
	# t9072 {dead: []} = t9073 + t9085 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -136(%rbp), %rax
	addq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq $2, -168(%rbp) # t9104 {dead: []} = 2
	# t9071 {dead: []} = t9072 + t9104 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	movq -160(%rbp), %rax
	addq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	movq -176(%rbp), %rax # t9070 {dead: []} = image_s0[t9071]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t9070 {dead: []} = image_s0[t9071]
	movq %rdx, -184(%rbp)
	# t9069 {dead: []} = t9070 - b_s90 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) - (b_s90)}
	movq -184(%rbp), %rax
	subq -48(%rbp), %rax
	movq %rax, -192(%rbp)
	movq $255, -200(%rbp) # t9124 {dead: []} = 255
	# t9068 {dead: []} = t9069 * t9124 {canonical: ((image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) - (b_s90)) * (255)}
	movq -192(%rbp), %rax
	imulq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	# t9134 {dead: []} = w_s90 - b_s90 {canonical: (w_s90) - (b_s90)}
	movq -64(%rbp), %rax
	subq -48(%rbp), %rax
	movq %rax, -216(%rbp)
	movq -120(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	# image_s0[t9025] {dead: []} = t9068 / t9134 {canonical: (((image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) - (b_s90)) * (255)) / ((w_s90) - (b_s90))}
	movq -208(%rbp), %rax
	cqto
	idivq -216(%rbp)
	movq %rax, (%rdi)
	jmp _nop_9151
_nop_9151:
	jmp _end_of_block_8540
_end_of_block_8540:
	jmp _conditional_8549
_conditional_8549:
_block_9208:
	movq $2193, -72(%rbp) # t9158 {dead: []} = 2193
	# t9156 {dead: []} = r_s90 * t9158 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t9170 {dead: []} = 3
	# t9168 {dead: []} = c_s90 * t9170 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t9155 {dead: []} = t9156 + t9168 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2, -112(%rbp) # t9187 {dead: []} = 2
	# t9154 {dead: []} = t9155 + t9187 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rax # t9153 {dead: []} = image_s0[t9154]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t9153 {dead: []} = image_s0[t9154]
	movq %rdx, -128(%rbp)
	movq $0, -136(%rbp) # t9199 {dead: []} = 0
	# t9152 {dead: []} = t9153 < t9199 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) < (0)}
	movq -128(%rbp), %rax # t9152 {dead: []} = t9153 < t9199 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) < (0)}
	cmpq -136(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -144(%rbp)
	jmp _nop_9210
_nop_9210:
	jmp _end_of_conditional_8549
_end_of_conditional_8549:
	cmpq $1, -144(%rbp) # true = t9152
	jne _conditional_8548 # ifFalse
	jmp _block_8543 # ifTrue
_block_8543:
_block_9257:
	movq $2193, -72(%rbp) # t9217 {dead: []} = 2193
	# t9215 {dead: []} = r_s90 * t9217 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t9229 {dead: []} = 3
	# t9227 {dead: []} = c_s90 * t9229 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t9214 {dead: []} = t9215 + t9227 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2, -112(%rbp) # t9246 {dead: []} = 2
	# t9213 {dead: []} = t9214 + t9246 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq $0, (%rdi) # image_s0[t9213] {dead: []} = 0
	jmp _nop_9258
_nop_9258:
	jmp _end_of_block_8543
_end_of_block_8543:
	jmp _block_8518
_block_8518:
_block_9266:
	movq $1, -72(%rbp) # t9262 {dead: []} = 1
	movq -72(%rbp), %rax # c_s90 {dead: []} += t9262
	addq %rax, -56(%rbp)
	jmp _nop_9267
_nop_9267:
	jmp _end_of_block_8518
_end_of_block_8518:
	jmp _conditional_8550
_conditional_8548:
_block_9324:
	movq $2193, -72(%rbp) # t9274 {dead: []} = 2193
	# t9272 {dead: []} = r_s90 * t9274 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t9286 {dead: []} = 3
	# t9284 {dead: []} = c_s90 * t9286 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t9271 {dead: []} = t9272 + t9284 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2, -112(%rbp) # t9303 {dead: []} = 2
	# t9270 {dead: []} = t9271 + t9303 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rax # t9269 {dead: []} = image_s0[t9270]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t9269 {dead: []} = image_s0[t9270]
	movq %rdx, -128(%rbp)
	movq $255, -136(%rbp) # t9315 {dead: []} = 255
	# t9268 {dead: []} = t9269 > t9315 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) > (255)}
	movq -128(%rbp), %rax # t9268 {dead: []} = t9269 > t9315 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) > (255)}
	cmpq -136(%rbp), %rax
	setg %al
	movzbq %al, %rax
	movq %rax, -144(%rbp)
	jmp _nop_9326
_nop_9326:
	jmp _end_of_conditional_8548
_end_of_conditional_8548:
	cmpq $1, -144(%rbp) # true = t9268
	jne _block_8518 # ifFalse
	jmp _block_8547 # ifTrue
_block_8547:
_block_9373:
	movq $2193, -72(%rbp) # t9333 {dead: []} = 2193
	# t9331 {dead: []} = r_s90 * t9333 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t9345 {dead: []} = 3
	# t9343 {dead: []} = c_s90 * t9345 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t9330 {dead: []} = t9331 + t9343 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $2, -112(%rbp) # t9362 {dead: []} = 2
	# t9329 {dead: []} = t9330 + t9362 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq $255, (%rdi) # image_s0[t9329] {dead: []} = 255
	jmp _nop_9374
_nop_9374:
	jmp _end_of_block_8547
_end_of_block_8547:
	jmp _block_8518
_conditional_8538:
_block_9431:
	movq $2193, -72(%rbp) # t9381 {dead: []} = 2193
	# t9379 {dead: []} = r_s90 * t9381 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t9393 {dead: []} = 3
	# t9391 {dead: []} = c_s90 * t9393 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t9378 {dead: []} = t9379 + t9391 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $1, -112(%rbp) # t9410 {dead: []} = 1
	# t9377 {dead: []} = t9378 + t9410 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rax # t9376 {dead: []} = image_s0[t9377]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t9376 {dead: []} = image_s0[t9377]
	movq %rdx, -128(%rbp)
	movq $255, -136(%rbp) # t9422 {dead: []} = 255
	# t9375 {dead: []} = t9376 > t9422 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) > (255)}
	movq -128(%rbp), %rax # t9375 {dead: []} = t9376 > t9422 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) > (255)}
	cmpq -136(%rbp), %rax
	setg %al
	movzbq %al, %rax
	movq %rax, -144(%rbp)
	jmp _nop_9433
_nop_9433:
	jmp _end_of_conditional_8538
_end_of_conditional_8538:
	cmpq $1, -144(%rbp) # true = t9375
	jne _block_8540 # ifFalse
	jmp _block_8537 # ifTrue
_block_8537:
_block_9480:
	movq $2193, -72(%rbp) # t9440 {dead: []} = 2193
	# t9438 {dead: []} = r_s90 * t9440 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t9452 {dead: []} = 3
	# t9450 {dead: []} = c_s90 * t9452 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t9437 {dead: []} = t9438 + t9450 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $1, -112(%rbp) # t9469 {dead: []} = 1
	# t9436 {dead: []} = t9437 + t9469 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	movq -104(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq -120(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq $255, (%rdi) # image_s0[t9436] {dead: []} = 255
	jmp _nop_9481
_nop_9481:
	jmp _end_of_block_8537
_end_of_block_8537:
	jmp _block_8540
_conditional_8528:
_block_9527:
	movq $2193, -72(%rbp) # t9487 {dead: []} = 2193
	# t9485 {dead: []} = r_s90 * t9487 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t9499 {dead: []} = 3
	# t9497 {dead: []} = c_s90 * t9499 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t9484 {dead: []} = t9485 + t9497 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rax # t9483 {dead: []} = image_s0[t9484]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t9483 {dead: []} = image_s0[t9484]
	movq %rdx, -112(%rbp)
	movq $255, -120(%rbp) # t9518 {dead: []} = 255
	# t9482 {dead: []} = t9483 > t9518 {canonical: (image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) > (255)}
	movq -112(%rbp), %rax # t9482 {dead: []} = t9483 > t9518 {canonical: (image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) > (255)}
	cmpq -120(%rbp), %rax
	setg %al
	movzbq %al, %rax
	movq %rax, -128(%rbp)
	jmp _nop_9529
_nop_9529:
	jmp _end_of_conditional_8528
_end_of_conditional_8528:
	cmpq $1, -128(%rbp) # true = t9482
	jne _block_8530 # ifFalse
	jmp _block_8527 # ifTrue
_block_8527:
_block_9565:
	movq $2193, -72(%rbp) # t9535 {dead: []} = 2193
	# t9533 {dead: []} = r_s90 * t9535 {canonical: (r_s90) * (2193)}
	movq -40(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -80(%rbp)
	movq $3, -88(%rbp) # t9547 {dead: []} = 3
	# t9545 {dead: []} = c_s90 * t9547 {canonical: (c_s90) * (3)}
	movq -56(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	# t9532 {dead: []} = t9533 + t9545 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	movq -80(%rbp), %rax
	addq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq -104(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq $255, (%rdi) # image_s0[t9532] {dead: []} = 255
	jmp _nop_9566
_nop_9566:
	jmp _end_of_block_8527
_end_of_block_8527:
	jmp _block_8530
_block_8514:
_block_9574:
	movq $1, -72(%rbp) # t9570 {dead: []} = 1
	movq -72(%rbp), %rax # r_s90 {dead: []} += t9570
	addq %rax, -40(%rbp)
	jmp _nop_9575
_nop_9575:
	jmp _end_of_block_8514
_end_of_block_8514:
	jmp _conditional_8551
_return_8552:
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

_method_convert2HSV:
	enter $(256), $0
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
	movq $0, -248(%rbp)
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
_block_9580:
_block_9651:
	movq $0, -32(%rbp) # row_s10 {dead: []} = 0
	jmp _nop_9652
_nop_9652:
	jmp _end_of_block_9580
_end_of_block_9580:
	jmp _conditional_9645
_conditional_9645:
_block_9662:
	# t9653 {dead: []} = row_s10 < rows_s0 {canonical: (row_s10) < (rows_s0)}
	movq -32(%rbp), %rax # t9653 {dead: []} = row_s10 < rows_s0 {canonical: (row_s10) < (rows_s0)}
	cmpq _global_rows(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_9664
_nop_9664:
	jmp _end_of_conditional_9645
_end_of_conditional_9645:
	cmpq $1, -88(%rbp) # true = t9653
	jne _return_9646 # ifFalse
	jmp _block_9584 # ifTrue
_block_9584:
_block_9669:
	movq $0, -24(%rbp) # col_s10 {dead: []} = 0
	jmp _nop_9670
_nop_9670:
	jmp _end_of_block_9584
_end_of_block_9584:
	jmp _conditional_9644
_conditional_9644:
_block_9680:
	# t9671 {dead: []} = col_s10 < cols_s0 {canonical: (col_s10) < (cols_s0)}
	movq -24(%rbp), %rax # t9671 {dead: []} = col_s10 < cols_s0 {canonical: (col_s10) < (cols_s0)}
	cmpq _global_cols(%rip), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -88(%rbp)
	jmp _nop_9682
_nop_9682:
	jmp _end_of_conditional_9644
_end_of_conditional_9644:
	cmpq $1, -88(%rbp) # true = t9671
	jne _block_9581 # ifFalse
	jmp _block_9592 # ifTrue
_block_9592:
_block_9702:
	movq $0, -40(%rbp) # min_s12 {dead: []} = 0
	movq $0, -56(%rbp) # max_s12 {dead: []} = 0
	movq $0, -72(%rbp) # delta_s12 {dead: []} = 0
	movq $0, -80(%rbp) # h_s12 {dead: []} = 0
	movq $0, -48(%rbp) # s_s12 {dead: []} = 0
	movq $0, -64(%rbp) # v_s12 {dead: []} = 0
	jmp _nop_9703
_nop_9703:
	jmp _end_of_block_9592
_end_of_block_9592:
	jmp _conditional_9600
_conditional_9600:
_block_9803:
	movq $2193, -88(%rbp) # t9710 {dead: []} = 2193
	# t9708 {dead: []} = row_s10 * t9710 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t9722 {dead: []} = 3
	# t9720 {dead: []} = col_s10 * t9722 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t9707 {dead: []} = t9708 + t9720 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $0, -128(%rbp) # t9739 {dead: []} = 0
	# t9706 {dead: []} = t9707 + t9739 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # t9705 {dead: []} = image_s0[t9706]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t9705 {dead: []} = image_s0[t9706]
	movq %rdx, -144(%rbp)
	movq $2193, -152(%rbp) # t9756 {dead: []} = 2193
	# t9754 {dead: []} = row_s10 * t9756 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq $3, -168(%rbp) # t9768 {dead: []} = 3
	# t9766 {dead: []} = col_s10 * t9768 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	# t9753 {dead: []} = t9754 + t9766 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -160(%rbp), %rax
	addq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	movq $1, -192(%rbp) # t9785 {dead: []} = 1
	# t9752 {dead: []} = t9753 + t9785 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -184(%rbp), %rax
	addq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	movq -200(%rbp), %rax # t9751 {dead: []} = image_s0[t9752]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t9751 {dead: []} = image_s0[t9752]
	movq %rdx, -208(%rbp)
	# t9704 {dead: []} = t9705 > t9751 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) > (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)])}
	movq -144(%rbp), %rax # t9704 {dead: []} = t9705 > t9751 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) > (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)])}
	cmpq -208(%rbp), %rax
	setg %al
	movzbq %al, %rax
	movq %rax, -216(%rbp)
	jmp _nop_9805
_nop_9805:
	jmp _end_of_conditional_9600
_end_of_conditional_9600:
	cmpq $1, -216(%rbp) # true = t9704
	jne _block_9599 # ifFalse
	jmp _block_9596 # ifTrue
_block_9596:
_block_9899:
	movq $2193, -88(%rbp) # t9813 {dead: []} = 2193
	# t9811 {dead: []} = row_s10 * t9813 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t9825 {dead: []} = 3
	# t9823 {dead: []} = col_s10 * t9825 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t9810 {dead: []} = t9811 + t9823 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $0, -128(%rbp) # t9842 {dead: []} = 0
	# t9809 {dead: []} = t9810 + t9842 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # max_s12 {dead: []} = image_s0[t9809]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # max_s12 {dead: []} = image_s0[t9809]
	movq %rdx, -56(%rbp)
	movq $2193, -144(%rbp) # t9859 {dead: []} = 2193
	# t9857 {dead: []} = row_s10 * t9859 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -144(%rbp), %rax
	movq %rax, -152(%rbp)
	movq $3, -160(%rbp) # t9871 {dead: []} = 3
	# t9869 {dead: []} = col_s10 * t9871 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -160(%rbp), %rax
	movq %rax, -168(%rbp)
	# t9856 {dead: []} = t9857 + t9869 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -152(%rbp), %rax
	addq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	movq $1, -184(%rbp) # t9888 {dead: []} = 1
	# t9855 {dead: []} = t9856 + t9888 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -176(%rbp), %rax
	addq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	movq -192(%rbp), %rax # min_s12 {dead: []} = image_s0[t9855]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # min_s12 {dead: []} = image_s0[t9855]
	movq %rdx, -40(%rbp)
	jmp _nop_9900
_nop_9900:
	jmp _end_of_block_9596
_end_of_block_9596:
	jmp _conditional_9609
_conditional_9609:
_block_9955:
	movq $2193, -88(%rbp) # t9908 {dead: []} = 2193
	# t9906 {dead: []} = row_s10 * t9908 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t9920 {dead: []} = 3
	# t9918 {dead: []} = col_s10 * t9920 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t9905 {dead: []} = t9906 + t9918 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $2, -128(%rbp) # t9937 {dead: []} = 2
	# t9904 {dead: []} = t9905 + t9937 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # t9903 {dead: []} = image_s0[t9904]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t9903 {dead: []} = image_s0[t9904]
	movq %rdx, -144(%rbp)
	# t9901 {dead: []} = max_s12 < t9903 {canonical: (max_s12) < (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	movq -56(%rbp), %rax # t9901 {dead: []} = max_s12 < t9903 {canonical: (max_s12) < (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	cmpq -144(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -152(%rbp)
	jmp _nop_9957
_nop_9957:
	jmp _end_of_conditional_9609
_end_of_conditional_9609:
	cmpq $1, -152(%rbp) # true = t9901
	jne _conditional_9608 # ifFalse
	jmp _block_9603 # ifTrue
_block_9603:
_block_10005:
	movq $2193, -88(%rbp) # t9965 {dead: []} = 2193
	# t9963 {dead: []} = row_s10 * t9965 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t9977 {dead: []} = 3
	# t9975 {dead: []} = col_s10 * t9977 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t9962 {dead: []} = t9963 + t9975 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $2, -128(%rbp) # t9994 {dead: []} = 2
	# t9961 {dead: []} = t9962 + t9994 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # max_s12 {dead: []} = image_s0[t9961]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # max_s12 {dead: []} = image_s0[t9961]
	movq %rdx, -56(%rbp)
	jmp _nop_10006
_nop_10006:
	jmp _end_of_block_9603
_end_of_block_9603:
	jmp _block_9611
_block_9611:
_block_10030:
	# delta_s12 {dead: []} = max_s12 - min_s12 {canonical: (max_s12) - (min_s12)}
	movq -56(%rbp), %rax
	subq -40(%rbp), %rax
	movq %rax, -72(%rbp)
	movq $4, -88(%rbp) # t10020 {dead: []} = 4
	# v_s12 {dead: []} = t10020 * max_s12 {canonical: (4) * (max_s12)}
	movq -88(%rbp), %rax
	imulq -56(%rbp), %rax
	movq %rax, -64(%rbp)
	jmp _nop_10031
_nop_10031:
	jmp _end_of_block_9611
_end_of_block_9611:
	jmp _conditional_9617
_conditional_9617:
_block_10043:
	movq $0, -88(%rbp) # t10034 {dead: []} = 0
	# t10032 {dead: []} = max_s12 == t10034 {canonical: (max_s12) == (0)}
	movq -56(%rbp), %rax # t10032 {dead: []} = max_s12 == t10034 {canonical: (max_s12) == (0)}
	cmpq -88(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -96(%rbp)
	jmp _nop_10045
_nop_10045:
	jmp _end_of_conditional_9617
_end_of_conditional_9617:
	cmpq $1, -96(%rbp) # true = t10032
	jne _block_9616 # ifFalse
	jmp _block_9614 # ifTrue
_block_9614:
_block_10050:
	movq $0, -48(%rbp) # s_s12 {dead: []} = 0
	jmp _nop_10051
_nop_10051:
	jmp _end_of_block_9614
_end_of_block_9614:
	jmp _conditional_9640
_conditional_9640:
_block_10063:
	movq $0, -88(%rbp) # t10054 {dead: []} = 0
	# t10052 {dead: []} = delta_s12 == t10054 {canonical: (delta_s12) == (0)}
	movq -72(%rbp), %rax # t10052 {dead: []} = delta_s12 == t10054 {canonical: (delta_s12) == (0)}
	cmpq -88(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -96(%rbp)
	jmp _nop_10065
_nop_10065:
	jmp _end_of_conditional_9640
_end_of_conditional_9640:
	cmpq $1, -96(%rbp) # true = t10052
	jne _conditional_9639 # ifFalse
	jmp _block_9620 # ifTrue
_block_9620:
_block_10073:
	movq $1, -88(%rbp) # t10069 {dead: []} = 1
	movq -88(%rbp), %rax # h_s12 {dead: []} = -t10069
	negq %rax # -t10069
	movq %rax, -80(%rbp)
	jmp _nop_10074
_nop_10074:
	jmp _end_of_block_9620
_end_of_block_9620:
	jmp _block_9643
_block_9643:
_block_10214:
	movq $2193, -88(%rbp) # t10081 {dead: []} = 2193
	# t10079 {dead: []} = row_s10 * t10081 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t10093 {dead: []} = 3
	# t10091 {dead: []} = col_s10 * t10093 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t10078 {dead: []} = t10079 + t10091 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $0, -128(%rbp) # t10110 {dead: []} = 0
	# t10077 {dead: []} = t10078 + t10110 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq -80(%rbp), %rax # image_s0[t10077] {dead: []} = h_s12 {canonical: h_s12}
	movq %rax, (%rdi)
	movq $2193, -144(%rbp) # t10127 {dead: []} = 2193
	# t10125 {dead: []} = row_s10 * t10127 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -144(%rbp), %rax
	movq %rax, -152(%rbp)
	movq $3, -160(%rbp) # t10139 {dead: []} = 3
	# t10137 {dead: []} = col_s10 * t10139 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -160(%rbp), %rax
	movq %rax, -168(%rbp)
	# t10124 {dead: []} = t10125 + t10137 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -152(%rbp), %rax
	addq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	movq $1, -184(%rbp) # t10156 {dead: []} = 1
	# t10123 {dead: []} = t10124 + t10156 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -176(%rbp), %rax
	addq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	movq -192(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq -48(%rbp), %rax # image_s0[t10123] {dead: []} = s_s12 {canonical: s_s12}
	movq %rax, (%rdi)
	movq $2193, -200(%rbp) # t10173 {dead: []} = 2193
	# t10171 {dead: []} = row_s10 * t10173 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	movq $3, -216(%rbp) # t10185 {dead: []} = 3
	# t10183 {dead: []} = col_s10 * t10185 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -216(%rbp), %rax
	movq %rax, -224(%rbp)
	# t10170 {dead: []} = t10171 + t10183 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -208(%rbp), %rax
	addq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	movq $2, -240(%rbp) # t10202 {dead: []} = 2
	# t10169 {dead: []} = t10170 + t10202 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -232(%rbp), %rax
	addq -240(%rbp), %rax
	movq %rax, -248(%rbp)
	movq -248(%rbp), %rdi
	cmpq $2300000, %rdi
	jge _sp_method_exit_with_status_1
	cmpq $0, %rdi
	jl _sp_method_exit_with_status_1
	leaq 0(,%rdi,8), %rcx
	leaq _global_image, %rdi
	add %rcx, %rdi
	movq -64(%rbp), %rax # image_s0[t10169] {dead: []} = v_s12 {canonical: v_s12}
	movq %rax, (%rdi)
	jmp _nop_10215
_nop_10215:
	jmp _end_of_block_9643
_end_of_block_9643:
	jmp _block_9585
_block_9585:
_block_10223:
	movq $1, -88(%rbp) # t10219 {dead: []} = 1
	movq -88(%rbp), %rax # col_s10 {dead: []} += t10219
	addq %rax, -24(%rbp)
	jmp _nop_10224
_nop_10224:
	jmp _end_of_block_9585
_end_of_block_9585:
	jmp _conditional_9644
_conditional_9639:
_block_10279:
	movq $2193, -88(%rbp) # t10232 {dead: []} = 2193
	# t10230 {dead: []} = row_s10 * t10232 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t10244 {dead: []} = 3
	# t10242 {dead: []} = col_s10 * t10244 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t10229 {dead: []} = t10230 + t10242 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $0, -128(%rbp) # t10261 {dead: []} = 0
	# t10228 {dead: []} = t10229 + t10261 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # t10227 {dead: []} = image_s0[t10228]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10227 {dead: []} = image_s0[t10228]
	movq %rdx, -144(%rbp)
	# t10225 {dead: []} = max_s12 == t10227 {canonical: (max_s12) == (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)])}
	movq -56(%rbp), %rax # t10225 {dead: []} = max_s12 == t10227 {canonical: (max_s12) == (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)])}
	cmpq -144(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -152(%rbp)
	jmp _nop_10281
_nop_10281:
	jmp _end_of_conditional_9639
_end_of_conditional_9639:
	cmpq $1, -152(%rbp) # true = t10225
	jne _conditional_9637 # ifFalse
	jmp _conditional_9638 # ifTrue
_conditional_9638:
_block_10381:
	movq $2193, -88(%rbp) # t10288 {dead: []} = 2193
	# t10286 {dead: []} = row_s10 * t10288 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t10300 {dead: []} = 3
	# t10298 {dead: []} = col_s10 * t10300 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t10285 {dead: []} = t10286 + t10298 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $1, -128(%rbp) # t10317 {dead: []} = 1
	# t10284 {dead: []} = t10285 + t10317 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # t10283 {dead: []} = image_s0[t10284]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10283 {dead: []} = image_s0[t10284]
	movq %rdx, -144(%rbp)
	movq $2193, -152(%rbp) # t10334 {dead: []} = 2193
	# t10332 {dead: []} = row_s10 * t10334 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq $3, -168(%rbp) # t10346 {dead: []} = 3
	# t10344 {dead: []} = col_s10 * t10346 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	# t10331 {dead: []} = t10332 + t10344 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -160(%rbp), %rax
	addq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	movq $2, -192(%rbp) # t10363 {dead: []} = 2
	# t10330 {dead: []} = t10331 + t10363 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -184(%rbp), %rax
	addq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	movq -200(%rbp), %rax # t10329 {dead: []} = image_s0[t10330]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10329 {dead: []} = image_s0[t10330]
	movq %rdx, -208(%rbp)
	# t10282 {dead: []} = t10283 >= t10329 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) >= (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	movq -144(%rbp), %rax # t10282 {dead: []} = t10283 >= t10329 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) >= (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	cmpq -208(%rbp), %rax
	setge %al
	movzbq %al, %rax
	movq %rax, -216(%rbp)
	jmp _nop_10383
_nop_10383:
	jmp _end_of_conditional_9638
_end_of_conditional_9638:
	cmpq $1, -216(%rbp) # true = t10282
	jne _conditional_9637 # ifFalse
	jmp _block_9624 # ifTrue
_block_9624:
_block_10505:
	movq $60, -88(%rbp) # t10388 {dead: []} = 60
	movq $2193, -96(%rbp) # t10397 {dead: []} = 2193
	# t10395 {dead: []} = row_s10 * t10397 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -96(%rbp), %rax
	movq %rax, -104(%rbp)
	movq $3, -112(%rbp) # t10409 {dead: []} = 3
	# t10407 {dead: []} = col_s10 * t10409 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	# t10394 {dead: []} = t10395 + t10407 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -104(%rbp), %rax
	addq -120(%rbp), %rax
	movq %rax, -128(%rbp)
	movq $1, -136(%rbp) # t10426 {dead: []} = 1
	# t10393 {dead: []} = t10394 + t10426 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -128(%rbp), %rax
	addq -136(%rbp), %rax
	movq %rax, -144(%rbp)
	movq -144(%rbp), %rax # t10392 {dead: []} = image_s0[t10393]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10392 {dead: []} = image_s0[t10393]
	movq %rdx, -152(%rbp)
	movq $2193, -160(%rbp) # t10443 {dead: []} = 2193
	# t10441 {dead: []} = row_s10 * t10443 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -160(%rbp), %rax
	movq %rax, -168(%rbp)
	movq $3, -176(%rbp) # t10455 {dead: []} = 3
	# t10453 {dead: []} = col_s10 * t10455 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	# t10440 {dead: []} = t10441 + t10453 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -168(%rbp), %rax
	addq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	movq $2, -200(%rbp) # t10472 {dead: []} = 2
	# t10439 {dead: []} = t10440 + t10472 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -192(%rbp), %rax
	addq -200(%rbp), %rax
	movq %rax, -208(%rbp)
	movq -208(%rbp), %rax # t10438 {dead: []} = image_s0[t10439]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10438 {dead: []} = image_s0[t10439]
	movq %rdx, -216(%rbp)
	# t10391 {dead: []} = t10392 - t10438 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	movq -152(%rbp), %rax
	subq -216(%rbp), %rax
	movq %rax, -224(%rbp)
	# t10387 {dead: []} = t10388 * t10391 {canonical: (60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))}
	movq -88(%rbp), %rax
	imulq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	# h_s12 {dead: []} = t10387 / delta_s12 {canonical: ((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))) / (delta_s12)}
	movq -232(%rbp), %rax
	cqto
	idivq -72(%rbp)
	movq %rax, -80(%rbp)
	jmp _nop_10506
_nop_10506:
	jmp _end_of_block_9624
_end_of_block_9624:
	jmp _block_9643
_conditional_9637:
_block_10561:
	movq $2193, -88(%rbp) # t10514 {dead: []} = 2193
	# t10512 {dead: []} = row_s10 * t10514 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t10526 {dead: []} = 3
	# t10524 {dead: []} = col_s10 * t10526 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t10511 {dead: []} = t10512 + t10524 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $0, -128(%rbp) # t10543 {dead: []} = 0
	# t10510 {dead: []} = t10511 + t10543 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # t10509 {dead: []} = image_s0[t10510]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10509 {dead: []} = image_s0[t10510]
	movq %rdx, -144(%rbp)
	# t10507 {dead: []} = max_s12 == t10509 {canonical: (max_s12) == (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)])}
	movq -56(%rbp), %rax # t10507 {dead: []} = max_s12 == t10509 {canonical: (max_s12) == (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)])}
	cmpq -144(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -152(%rbp)
	jmp _nop_10563
_nop_10563:
	jmp _end_of_conditional_9637
_end_of_conditional_9637:
	cmpq $1, -152(%rbp) # true = t10507
	jne _conditional_9635 # ifFalse
	jmp _conditional_9636 # ifTrue
_conditional_9636:
_block_10663:
	movq $2193, -88(%rbp) # t10570 {dead: []} = 2193
	# t10568 {dead: []} = row_s10 * t10570 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t10582 {dead: []} = 3
	# t10580 {dead: []} = col_s10 * t10582 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t10567 {dead: []} = t10568 + t10580 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $1, -128(%rbp) # t10599 {dead: []} = 1
	# t10566 {dead: []} = t10567 + t10599 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # t10565 {dead: []} = image_s0[t10566]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10565 {dead: []} = image_s0[t10566]
	movq %rdx, -144(%rbp)
	movq $2193, -152(%rbp) # t10616 {dead: []} = 2193
	# t10614 {dead: []} = row_s10 * t10616 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -152(%rbp), %rax
	movq %rax, -160(%rbp)
	movq $3, -168(%rbp) # t10628 {dead: []} = 3
	# t10626 {dead: []} = col_s10 * t10628 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	# t10613 {dead: []} = t10614 + t10626 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -160(%rbp), %rax
	addq -176(%rbp), %rax
	movq %rax, -184(%rbp)
	movq $2, -192(%rbp) # t10645 {dead: []} = 2
	# t10612 {dead: []} = t10613 + t10645 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -184(%rbp), %rax
	addq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	movq -200(%rbp), %rax # t10611 {dead: []} = image_s0[t10612]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10611 {dead: []} = image_s0[t10612]
	movq %rdx, -208(%rbp)
	# t10564 {dead: []} = t10565 < t10611 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) < (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	movq -144(%rbp), %rax # t10564 {dead: []} = t10565 < t10611 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) < (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	cmpq -208(%rbp), %rax
	setl %al
	movzbq %al, %rax
	movq %rax, -216(%rbp)
	jmp _nop_10665
_nop_10665:
	jmp _end_of_conditional_9636
_end_of_conditional_9636:
	cmpq $1, -216(%rbp) # true = t10564
	jne _conditional_9635 # ifFalse
	jmp _block_9628 # ifTrue
_block_9628:
_block_10798:
	movq $360, -88(%rbp) # t10669 {dead: []} = 360
	movq $60, -96(%rbp) # t10674 {dead: []} = 60
	movq $2193, -104(%rbp) # t10683 {dead: []} = 2193
	# t10681 {dead: []} = row_s10 * t10683 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	movq $3, -120(%rbp) # t10695 {dead: []} = 3
	# t10693 {dead: []} = col_s10 * t10695 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -120(%rbp), %rax
	movq %rax, -128(%rbp)
	# t10680 {dead: []} = t10681 + t10693 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -112(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq $1, -144(%rbp) # t10712 {dead: []} = 1
	# t10679 {dead: []} = t10680 + t10712 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -136(%rbp), %rax
	addq -144(%rbp), %rax
	movq %rax, -152(%rbp)
	movq -152(%rbp), %rax # t10678 {dead: []} = image_s0[t10679]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10678 {dead: []} = image_s0[t10679]
	movq %rdx, -160(%rbp)
	movq $2193, -168(%rbp) # t10729 {dead: []} = 2193
	# t10727 {dead: []} = row_s10 * t10729 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	movq $3, -184(%rbp) # t10741 {dead: []} = 3
	# t10739 {dead: []} = col_s10 * t10741 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	# t10726 {dead: []} = t10727 + t10739 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -176(%rbp), %rax
	addq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	movq $2, -208(%rbp) # t10758 {dead: []} = 2
	# t10725 {dead: []} = t10726 + t10758 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -200(%rbp), %rax
	addq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	movq -216(%rbp), %rax # t10724 {dead: []} = image_s0[t10725]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10724 {dead: []} = image_s0[t10725]
	movq %rdx, -224(%rbp)
	# t10677 {dead: []} = t10678 - t10724 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	movq -160(%rbp), %rax
	subq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	# t10673 {dead: []} = t10674 * t10677 {canonical: (60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))}
	movq -96(%rbp), %rax
	imulq -232(%rbp), %rax
	movq %rax, -240(%rbp)
	# t10672 {dead: []} = t10673 / delta_s12 {canonical: ((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))) / (delta_s12)}
	movq -240(%rbp), %rax
	cqto
	idivq -72(%rbp)
	movq %rax, -248(%rbp)
	# h_s12 {dead: []} = t10669 + t10672 {canonical: (360) + (((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))) / (delta_s12))}
	movq -88(%rbp), %rax
	addq -248(%rbp), %rax
	movq %rax, -80(%rbp)
	jmp _nop_10799
_nop_10799:
	jmp _end_of_block_9628
_end_of_block_9628:
	jmp _block_9643
_conditional_9635:
_block_10854:
	movq $2193, -88(%rbp) # t10807 {dead: []} = 2193
	# t10805 {dead: []} = row_s10 * t10807 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t10819 {dead: []} = 3
	# t10817 {dead: []} = col_s10 * t10819 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t10804 {dead: []} = t10805 + t10817 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $1, -128(%rbp) # t10836 {dead: []} = 1
	# t10803 {dead: []} = t10804 + t10836 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # t10802 {dead: []} = image_s0[t10803]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10802 {dead: []} = image_s0[t10803]
	movq %rdx, -144(%rbp)
	# t10800 {dead: []} = max_s12 == t10802 {canonical: (max_s12) == (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)])}
	movq -56(%rbp), %rax # t10800 {dead: []} = max_s12 == t10802 {canonical: (max_s12) == (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)])}
	cmpq -144(%rbp), %rax
	sete %al
	movzbq %al, %rax
	movq %rax, -152(%rbp)
	jmp _nop_10856
_nop_10856:
	jmp _end_of_conditional_9635
_end_of_conditional_9635:
	cmpq $1, -152(%rbp) # true = t10800
	jne _block_9634 # ifFalse
	jmp _block_9632 # ifTrue
_block_9632:
_block_10989:
	movq $120, -88(%rbp) # t10860 {dead: []} = 120
	movq $60, -96(%rbp) # t10865 {dead: []} = 60
	movq $2193, -104(%rbp) # t10874 {dead: []} = 2193
	# t10872 {dead: []} = row_s10 * t10874 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	movq $3, -120(%rbp) # t10886 {dead: []} = 3
	# t10884 {dead: []} = col_s10 * t10886 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -120(%rbp), %rax
	movq %rax, -128(%rbp)
	# t10871 {dead: []} = t10872 + t10884 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -112(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq $2, -144(%rbp) # t10903 {dead: []} = 2
	# t10870 {dead: []} = t10871 + t10903 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -136(%rbp), %rax
	addq -144(%rbp), %rax
	movq %rax, -152(%rbp)
	movq -152(%rbp), %rax # t10869 {dead: []} = image_s0[t10870]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10869 {dead: []} = image_s0[t10870]
	movq %rdx, -160(%rbp)
	movq $2193, -168(%rbp) # t10920 {dead: []} = 2193
	# t10918 {dead: []} = row_s10 * t10920 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	movq $3, -184(%rbp) # t10932 {dead: []} = 3
	# t10930 {dead: []} = col_s10 * t10932 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	# t10917 {dead: []} = t10918 + t10930 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -176(%rbp), %rax
	addq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	movq $0, -208(%rbp) # t10949 {dead: []} = 0
	# t10916 {dead: []} = t10917 + t10949 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	movq -200(%rbp), %rax
	addq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	movq -216(%rbp), %rax # t10915 {dead: []} = image_s0[t10916]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t10915 {dead: []} = image_s0[t10916]
	movq %rdx, -224(%rbp)
	# t10868 {dead: []} = t10869 - t10915 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)])}
	movq -160(%rbp), %rax
	subq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	# t10864 {dead: []} = t10865 * t10868 {canonical: (60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]))}
	movq -96(%rbp), %rax
	imulq -232(%rbp), %rax
	movq %rax, -240(%rbp)
	# t10863 {dead: []} = t10864 / delta_s12 {canonical: ((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]))) / (delta_s12)}
	movq -240(%rbp), %rax
	cqto
	idivq -72(%rbp)
	movq %rax, -248(%rbp)
	# h_s12 {dead: []} = t10860 + t10863 {canonical: (120) + (((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]))) / (delta_s12))}
	movq -88(%rbp), %rax
	addq -248(%rbp), %rax
	movq %rax, -80(%rbp)
	jmp _nop_10990
_nop_10990:
	jmp _end_of_block_9632
_end_of_block_9632:
	jmp _block_9643
_block_9634:
_block_11123:
	movq $240, -88(%rbp) # t10994 {dead: []} = 240
	movq $60, -96(%rbp) # t10999 {dead: []} = 60
	movq $2193, -104(%rbp) # t11008 {dead: []} = 2193
	# t11006 {dead: []} = row_s10 * t11008 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	movq $3, -120(%rbp) # t11020 {dead: []} = 3
	# t11018 {dead: []} = col_s10 * t11020 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -120(%rbp), %rax
	movq %rax, -128(%rbp)
	# t11005 {dead: []} = t11006 + t11018 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -112(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq $0, -144(%rbp) # t11037 {dead: []} = 0
	# t11004 {dead: []} = t11005 + t11037 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	movq -136(%rbp), %rax
	addq -144(%rbp), %rax
	movq %rax, -152(%rbp)
	movq -152(%rbp), %rax # t11003 {dead: []} = image_s0[t11004]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t11003 {dead: []} = image_s0[t11004]
	movq %rdx, -160(%rbp)
	movq $2193, -168(%rbp) # t11054 {dead: []} = 2193
	# t11052 {dead: []} = row_s10 * t11054 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	movq $3, -184(%rbp) # t11066 {dead: []} = 3
	# t11064 {dead: []} = col_s10 * t11066 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	# t11051 {dead: []} = t11052 + t11064 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -176(%rbp), %rax
	addq -192(%rbp), %rax
	movq %rax, -200(%rbp)
	movq $1, -208(%rbp) # t11083 {dead: []} = 1
	# t11050 {dead: []} = t11051 + t11083 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -200(%rbp), %rax
	addq -208(%rbp), %rax
	movq %rax, -216(%rbp)
	movq -216(%rbp), %rax # t11049 {dead: []} = image_s0[t11050]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t11049 {dead: []} = image_s0[t11050]
	movq %rdx, -224(%rbp)
	# t11002 {dead: []} = t11003 - t11049 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)])}
	movq -160(%rbp), %rax
	subq -224(%rbp), %rax
	movq %rax, -232(%rbp)
	# t10998 {dead: []} = t10999 * t11002 {canonical: (60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]))}
	movq -96(%rbp), %rax
	imulq -232(%rbp), %rax
	movq %rax, -240(%rbp)
	# t10997 {dead: []} = t10998 / delta_s12 {canonical: ((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]))) / (delta_s12)}
	movq -240(%rbp), %rax
	cqto
	idivq -72(%rbp)
	movq %rax, -248(%rbp)
	# h_s12 {dead: []} = t10994 + t10997 {canonical: (240) + (((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]))) / (delta_s12))}
	movq -88(%rbp), %rax
	addq -248(%rbp), %rax
	movq %rax, -80(%rbp)
	jmp _nop_11124
_nop_11124:
	jmp _end_of_block_9634
_end_of_block_9634:
	jmp _block_9643
_block_9616:
_block_11147:
	movq $1024, -88(%rbp) # t11129 {dead: []} = 1024
	# t11128 {dead: []} = t11129 * delta_s12 {canonical: (1024) * (delta_s12)}
	movq -88(%rbp), %rax
	imulq -72(%rbp), %rax
	movq %rax, -96(%rbp)
	# s_s12 {dead: []} = t11128 / max_s12 {canonical: ((1024) * (delta_s12)) / (max_s12)}
	movq -96(%rbp), %rax
	cqto
	idivq -56(%rbp)
	movq %rax, -48(%rbp)
	jmp _nop_11148
_nop_11148:
	jmp _end_of_block_9616
_end_of_block_9616:
	jmp _conditional_9640
_conditional_9608:
_block_11203:
	movq $2193, -88(%rbp) # t11156 {dead: []} = 2193
	# t11154 {dead: []} = row_s10 * t11156 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t11168 {dead: []} = 3
	# t11166 {dead: []} = col_s10 * t11168 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t11153 {dead: []} = t11154 + t11166 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $2, -128(%rbp) # t11185 {dead: []} = 2
	# t11152 {dead: []} = t11153 + t11185 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # t11151 {dead: []} = image_s0[t11152]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # t11151 {dead: []} = image_s0[t11152]
	movq %rdx, -144(%rbp)
	# t11149 {dead: []} = min_s12 > t11151 {canonical: (min_s12) > (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	movq -40(%rbp), %rax # t11149 {dead: []} = min_s12 > t11151 {canonical: (min_s12) > (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	cmpq -144(%rbp), %rax
	setg %al
	movzbq %al, %rax
	movq %rax, -152(%rbp)
	jmp _nop_11205
_nop_11205:
	jmp _end_of_conditional_9608
_end_of_conditional_9608:
	cmpq $1, -152(%rbp) # true = t11149
	jne _block_9611 # ifFalse
	jmp _block_9607 # ifTrue
_block_9607:
_block_11253:
	movq $2193, -88(%rbp) # t11213 {dead: []} = 2193
	# t11211 {dead: []} = row_s10 * t11213 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t11225 {dead: []} = 3
	# t11223 {dead: []} = col_s10 * t11225 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t11210 {dead: []} = t11211 + t11223 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $2, -128(%rbp) # t11242 {dead: []} = 2
	# t11209 {dead: []} = t11210 + t11242 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # min_s12 {dead: []} = image_s0[t11209]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # min_s12 {dead: []} = image_s0[t11209]
	movq %rdx, -40(%rbp)
	jmp _nop_11254
_nop_11254:
	jmp _end_of_block_9607
_end_of_block_9607:
	jmp _block_9611
_block_9599:
_block_11348:
	movq $2193, -88(%rbp) # t11262 {dead: []} = 2193
	# t11260 {dead: []} = row_s10 * t11262 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -88(%rbp), %rax
	movq %rax, -96(%rbp)
	movq $3, -104(%rbp) # t11274 {dead: []} = 3
	# t11272 {dead: []} = col_s10 * t11274 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -104(%rbp), %rax
	movq %rax, -112(%rbp)
	# t11259 {dead: []} = t11260 + t11272 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -96(%rbp), %rax
	addq -112(%rbp), %rax
	movq %rax, -120(%rbp)
	movq $1, -128(%rbp) # t11291 {dead: []} = 1
	# t11258 {dead: []} = t11259 + t11291 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	movq -120(%rbp), %rax
	addq -128(%rbp), %rax
	movq %rax, -136(%rbp)
	movq -136(%rbp), %rax # max_s12 {dead: []} = image_s0[t11258]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # max_s12 {dead: []} = image_s0[t11258]
	movq %rdx, -56(%rbp)
	movq $2193, -144(%rbp) # t11308 {dead: []} = 2193
	# t11306 {dead: []} = row_s10 * t11308 {canonical: (row_s10) * (2193)}
	movq -32(%rbp), %rax
	imulq -144(%rbp), %rax
	movq %rax, -152(%rbp)
	movq $3, -160(%rbp) # t11320 {dead: []} = 3
	# t11318 {dead: []} = col_s10 * t11320 {canonical: (col_s10) * (3)}
	movq -24(%rbp), %rax
	imulq -160(%rbp), %rax
	movq %rax, -168(%rbp)
	# t11305 {dead: []} = t11306 + t11318 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	movq -152(%rbp), %rax
	addq -168(%rbp), %rax
	movq %rax, -176(%rbp)
	movq $0, -184(%rbp) # t11337 {dead: []} = 0
	# t11304 {dead: []} = t11305 + t11337 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	movq -176(%rbp), %rax
	addq -184(%rbp), %rax
	movq %rax, -192(%rbp)
	movq -192(%rbp), %rax # min_s12 {dead: []} = image_s0[t11304]
	cmpq $2300000, %rax
	jge _sp_method_exit_with_status_1
	cmpq $0, %rax
	jl _sp_method_exit_with_status_1
	leaq _global_image(%rip), %rdx
	movq (%rdx,%rax,8), %rdx # min_s12 {dead: []} = image_s0[t11304]
	movq %rdx, -40(%rbp)
	jmp _nop_11349
_nop_11349:
	jmp _end_of_block_9599
_end_of_block_9599:
	jmp _conditional_9609
_block_9581:
_block_11357:
	movq $1, -88(%rbp) # t11353 {dead: []} = 1
	movq -88(%rbp), %rax # row_s10 {dead: []} += t11353
	addq %rax, -32(%rbp)
	jmp _nop_11358
_nop_11358:
	jmp _end_of_block_9581
_end_of_block_9581:
	jmp _conditional_9645
_return_9646:
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
