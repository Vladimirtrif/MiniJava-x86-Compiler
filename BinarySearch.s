	.text
	.globl asm_main
asm_main:
	pushq %rbp
	movq %rsp,%rbp
	pushq %rdi
	pushq %rax
	movq $20,%rax
	pushq %rax
	call BS$BS
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 16(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	movq %rbp,%rsp
	popq %rbp
	ret

	.data
BinarySearch$$: .quad 0

	.text
BS$Start:
	pushq %rbp
	movq %rsp,%rbp
	subq $16,%rsp
	movq 16(%rbp),%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	pushq %rdi
	pushq %rax
	movq 16(%rbp),%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 56(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	movq %rax,-8(%rbp)
	pushq %rdi
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 48(%rax),%rax
	call *(%rax)
	popq %rdi
	movq %rax,-16(%rbp)
	pushq %rdi
	pushq %rax
	movq $8,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 24(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf1
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp doneIf1
	elseIf1:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	doneIf1:
	pushq %rdi
	pushq %rax
	movq $19,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 24(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf2
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp doneIf2
	elseIf2:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	doneIf2:
	pushq %rdi
	pushq %rax
	movq $20,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 24(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf3
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp doneIf3
	elseIf3:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	doneIf3:
	pushq %rdi
	pushq %rax
	movq $21,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 24(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf4
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp doneIf4
	elseIf4:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	doneIf4:
	pushq %rdi
	pushq %rax
	movq $37,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 24(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf5
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp doneIf5
	elseIf5:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	doneIf5:
	pushq %rdi
	pushq %rax
	movq $38,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 24(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf6
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp doneIf6
	elseIf6:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	doneIf6:
	pushq %rdi
	pushq %rax
	movq $39,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 24(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf7
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp doneIf7
	elseIf7:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	doneIf7:
	pushq %rdi
	pushq %rax
	movq $50,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 24(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf8
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp doneIf8
	elseIf8:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	doneIf8:
	movq $999,%rax
	movq %rbp,%rsp
	popq %rbp
	ret
	.text
BS$Search:
	pushq %rbp
	movq %rsp,%rbp
	subq $56,%rsp
	movq $0,%rax
	movq %rax,-48(%rbp)
	movq $0,%rax
	movq %rax,-8(%rbp)
	movq 8(%rdi),%rax
	movq (%rax),%rax
	movq %rax,-16(%rbp)
	movq $1,%rax
	movq %rax,%rdx
	movq -16(%rbp),%rax
	subq %rdx,%rax
	movq %rax,-16(%rbp)
	movq $0,%rax
	movq %rax,-24(%rbp)
	movq $1,%rax
	movq %rax,-32(%rbp)
	jmp testWhile1
	loopWhile1:
	movq -16(%rbp),%rax
	movq %rax,%rdx
	movq -24(%rbp),%rax
	addq %rdx,%rax
	movq %rax,-40(%rbp)
	pushq %rdi
	pushq %rax
	movq -40(%rbp),%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 32(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	movq %rax,-40(%rbp)
	movq 8(%rdi),%rax
	pushq %rax
	movq -40(%rbp),%rax
	popq %rdx
	movq 8(%rdx,%rax,8),%rax
	movq %rax,-48(%rbp)
	movq -48(%rbp),%rax
	movq %rax,%rdx
	movq 16(%rbp),%rax
	cmpq %rdx,%rax
	jl trueLessThan1
	movq $0,%rax
	jmp doneLessThan1
	trueLessThan1:
	movq $1,%rax
	doneLessThan1:
	cmpq $0,%rax
	je elseIf9
	movq $1,%rax
	movq %rax,%rdx
	movq -40(%rbp),%rax
	subq %rdx,%rax
	movq %rax,-16(%rbp)
	jmp doneIf9
	elseIf9:
	movq $1,%rax
	movq %rax,%rdx
	movq -40(%rbp),%rax
	addq %rdx,%rax
	movq %rax,-24(%rbp)
	doneIf9:
	pushq %rdi
	movq -48(%rbp),%rax
	pushq %rax
	movq 16(%rbp),%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 40(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf10
	movq $0,%rax
	movq %rax,-32(%rbp)
	jmp doneIf10
	elseIf10:
	movq $1,%rax
	movq %rax,-32(%rbp)
	doneIf10:
	movq -24(%rbp),%rax
	movq %rax,%rdx
	movq -16(%rbp),%rax
	cmpq %rdx,%rax
	jl trueLessThan2
	movq $0,%rax
	jmp doneLessThan2
	trueLessThan2:
	movq $1,%rax
	doneLessThan2:
	cmpq $0,%rax
	je elseIf11
	movq $0,%rax
	movq %rax,-32(%rbp)
	jmp doneIf11
	elseIf11:
	movq $0,%rax
	movq %rax,-56(%rbp)
	doneIf11:
	testWhile1:
	movq -32(%rbp),%rax
	cmpq $1,%rax
	je loopWhile1
	pushq %rdi
	movq -48(%rbp),%rax
	pushq %rax
	movq 16(%rbp),%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rax
	lea 40(%rax),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je elseIf12
	movq $1,%rax
	movq %rax,-8(%rbp)
	jmp doneIf12
	elseIf12:
	movq $0,%rax
	movq %rax,-8(%rbp)
	doneIf12:
	movq -8(%rbp),%rax
	movq %rbp,%rsp
	popq %rbp
	ret
	.text
BS$Div:
	pushq %rbp
	movq %rsp,%rbp
	subq $24,%rsp
	movq $0,%rax
	movq %rax,-8(%rbp)
	movq $0,%rax
	movq %rax,-16(%rbp)
	movq $1,%rax
	movq %rax,%rdx
	movq 16(%rbp),%rax
	subq %rdx,%rax
	movq %rax,-24(%rbp)
	jmp testWhile2
	loopWhile2:
	movq $1,%rax
	movq %rax,%rdx
	movq -8(%rbp),%rax
	addq %rdx,%rax
	movq %rax,-8(%rbp)
	movq $2,%rax
	movq %rax,%rdx
	movq -16(%rbp),%rax
	addq %rdx,%rax
	movq %rax,-16(%rbp)
	testWhile2:
	movq -24(%rbp),%rax
	movq %rax,%rdx
	movq -16(%rbp),%rax
	cmpq %rdx,%rax
	jl trueLessThan3
	movq $0,%rax
	jmp doneLessThan3
	trueLessThan3:
	movq $1,%rax
	doneLessThan3:
	cmpq $1,%rax
	je loopWhile2
	movq -8(%rbp),%rax
	movq %rbp,%rsp
	popq %rbp
	ret
	.text
BS$Compare:
	pushq %rbp
	movq %rsp,%rbp
	subq $16,%rsp
	movq $0,%rax
	movq %rax,-8(%rbp)
	movq $1,%rax
	movq %rax,%rdx
	movq 16(%rbp),%rax
	addq %rdx,%rax
	movq %rax,-16(%rbp)
	movq 16(%rbp),%rax
	movq %rax,%rdx
	movq 24(%rbp),%rax
	cmpq %rdx,%rax
	jl trueLessThan4
	movq $0,%rax
	jmp doneLessThan4
	trueLessThan4:
	movq $1,%rax
	doneLessThan4:
	cmpq $0,%rax
	je elseIf13
	movq $0,%rax
	movq %rax,-8(%rbp)
	jmp doneIf13
	elseIf13:
	movq -16(%rbp),%rax
	movq %rax,%rdx
	movq 24(%rbp),%rax
	cmpq %rdx,%rax
	jl trueLessThan5
	movq $0,%rax
	jmp doneLessThan5
	trueLessThan5:
	movq $1,%rax
	doneLessThan5:
	cmpq $0,%rax
	movq $0,%rax
	sete %al
	cmpq $0,%rax
	je elseIf14
	movq $0,%rax
	movq %rax,-8(%rbp)
	jmp doneIf14
	elseIf14:
	movq $1,%rax
	movq %rax,-8(%rbp)
	doneIf14:
	doneIf13:
	movq -8(%rbp),%rax
	movq %rbp,%rsp
	popq %rbp
	ret
	.text
BS$Print:
	pushq %rbp
	movq %rsp,%rbp
	subq $8,%rsp
	movq $1,%rax
	movq %rax,-8(%rbp)
	jmp testWhile3
	loopWhile3:
	movq 8(%rdi),%rax
	pushq %rax
	movq -8(%rbp),%rax
	popq %rdx
	movq 8(%rdx,%rax,8),%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	movq $1,%rax
	movq %rax,%rdx
	movq -8(%rbp),%rax
	addq %rdx,%rax
	movq %rax,-8(%rbp)
	testWhile3:
	movq 16(%rdi),%rax
	movq %rax,%rdx
	movq -8(%rbp),%rax
	cmpq %rdx,%rax
	jl trueLessThan6
	movq $0,%rax
	jmp doneLessThan6
	trueLessThan6:
	movq $1,%rax
	doneLessThan6:
	cmpq $1,%rax
	je loopWhile3
	movq $99999,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	movq $0,%rax
	movq %rbp,%rsp
	popq %rbp
	ret
	.text
BS$Init:
	pushq %rbp
	movq %rsp,%rbp
	subq $32,%rsp
	movq 16(%rbp),%rax
	movq %rax,16(%rdi)
	movq 16(%rbp),%rax
	pushq %rax
	incq %rax
	shlq $3,%rax
	pushq %rdi
	movq %rax,%rdi
	pushq %rax
	call mjcalloc
	popq %rdx
	popq %rdi
	popq %rdx
	movq %rdx,(%rax)
	movq $8,%rcx
	pushq %rax
	testNewArray1:
	testq $0,%rdx
	je doneNewArray1
	addq %rcx,%rax
	movq $0,(%rax)
	shlq $2,%rcx
	decq %rdx
	jmp testNewArray1
	doneNewArray1:
	popq %rax
	movq %rax,8(%rdi)
	movq $1,%rax
	movq %rax,-8(%rbp)
	movq $1,%rax
	movq %rax,%rdx
	movq 16(%rdi),%rax
	addq %rdx,%rax
	movq %rax,-16(%rbp)
	jmp testWhile4
	loopWhile4:
	movq -8(%rbp),%rax
	movq %rax,%rdx
	movq $2,%rax
	imulq %rdx,%rax
	movq %rax,-32(%rbp)
	movq $3,%rax
	movq %rax,%rdx
	movq -16(%rbp),%rax
	subq %rdx,%rax
	movq %rax,-24(%rbp)
	movq -8(%rbp),%rax
	pushq %rax
	movq -24(%rbp),%rax
	movq %rax,%rdx
	movq -32(%rbp),%rax
	addq %rdx,%rax
	popq %rdx
	movq 8(%rdi),%rcx
	movq %rax,8(%rcx,%rdx,8)
	movq $1,%rax
	movq %rax,%rdx
	movq -8(%rbp),%rax
	addq %rdx,%rax
	movq %rax,-8(%rbp)
	movq $1,%rax
	movq %rax,%rdx
	movq -16(%rbp),%rax
	subq %rdx,%rax
	movq %rax,-16(%rbp)
	testWhile4:
	movq 16(%rdi),%rax
	movq %rax,%rdx
	movq -8(%rbp),%rax
	cmpq %rdx,%rax
	jl trueLessThan7
	movq $0,%rax
	jmp doneLessThan7
	trueLessThan7:
	movq $1,%rax
	doneLessThan7:
	cmpq $1,%rax
	je loopWhile4
	movq $0,%rax
	movq %rbp,%rsp
	popq %rbp
	ret
BS$BS:
	pushq %rbp
	movq %rsp,%rbp
	pushq %rdi
	movq $24,%rdi
	call mjcalloc
	popq %rdi
	leaq BS$$,%rdx
	movq %rdx,(%rax)
	movq $0,8(%rax)
	movq $0,16(%rax)
	movq %rbp,%rsp
	popq %rbp
	ret

	.data
BS$$: .quad 0
	.quad BS$BS
	.quad BS$Start
	.quad BS$Search
	.quad BS$Div
	.quad BS$Compare
	.quad BS$Print
	.quad BS$Init


