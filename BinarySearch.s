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
	movq (%rdi),%rcx
	lea 16(%rcx),%rax
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
	pushq %rdi
	pushq %rax
	movq 8(%rbp),%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 56(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	movq %rax,-8(%rbp)
	pushq %rdi
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 48(%rcx),%rax
	call *(%rax)
	popq %rdi
	movq %rax,-16(%rbp)
	pushq %rdi
	pushq %rax
	movq $8,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 24(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else0
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp done0
	else0:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	done0:
	pushq %rdi
	pushq %rax
	movq $19,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 24(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else1
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp done1
	else1:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	done1:
	pushq %rdi
	pushq %rax
	movq $20,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 24(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else2
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp done2
	else2:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	done2:
	pushq %rdi
	pushq %rax
	movq $21,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 24(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else3
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp done3
	else3:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	done3:
	pushq %rdi
	pushq %rax
	movq $37,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 24(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else4
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp done4
	else4:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	done4:
	pushq %rdi
	pushq %rax
	movq $38,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 24(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else5
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp done5
	else5:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	done5:
	pushq %rdi
	pushq %rax
	movq $39,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 24(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else6
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp done6
	else6:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	done6:
	pushq %rdi
	pushq %rax
	movq $50,%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 24(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else7
	movq $1,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	jmp done7
	else7:
	movq $0,%rax
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	done7:
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
	jmp test0
	loop0:
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
	movq (%rdi),%rcx
	lea 32(%rcx),%rax
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
	movq 8(%rbp),%rax
	cmpq %rdx,%rax
	jl lessThan0
	movq $0,%rax
	jmp finish0
	lessThan0:
	movq $1,%rax
	finish0:
	cmpq $0,%rax
	je else8
	movq $1,%rax
	movq %rax,%rdx
	movq -40(%rbp),%rax
	subq %rdx,%rax
	movq %rax,-16(%rbp)
	jmp done8
	else8:
	movq $1,%rax
	movq %rax,%rdx
	movq -40(%rbp),%rax
	addq %rdx,%rax
	movq %rax,-24(%rbp)
	done8:
	pushq %rdi
	movq -48(%rbp),%rax
	pushq %rax
	movq 8(%rbp),%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 40(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else9
	movq $0,%rax
	movq %rax,-32(%rbp)
	jmp done9
	else9:
	movq $1,%rax
	movq %rax,-32(%rbp)
	done9:
	movq -24(%rbp),%rax
	movq %rax,%rdx
	movq -16(%rbp),%rax
	cmpq %rdx,%rax
	jl lessThan1
	movq $0,%rax
	jmp finish1
	lessThan1:
	movq $1,%rax
	finish1:
	cmpq $0,%rax
	je else10
	movq $0,%rax
	movq %rax,-32(%rbp)
	jmp done10
	else10:
	movq $0,%rax
	movq %rax,-56(%rbp)
	done10:
	test0:
	movq -32(%rbp),%rax
	cmpq $1,%rax
	je loop0
	pushq %rdi
	movq -48(%rbp),%rax
	pushq %rax
	movq 8(%rbp),%rax
	pushq %rax
	movq %rdi,%rax
	movq %rax,%rdi
	movq (%rdi),%rcx
	lea 40(%rcx),%rax
	call *(%rax)
	popq %rdx
	popq %rdx
	popq %rdi
	cmpq $0,%rax
	je else11
	movq $1,%rax
	movq %rax,-8(%rbp)
	jmp done11
	else11:
	movq $0,%rax
	movq %rax,-8(%rbp)
	done11:
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
	movq 8(%rbp),%rax
	subq %rdx,%rax
	movq %rax,-24(%rbp)
	jmp test1
	loop1:
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
	test1:
	movq -24(%rbp),%rax
	movq %rax,%rdx
	movq -16(%rbp),%rax
	cmpq %rdx,%rax
	jl lessThan2
	movq $0,%rax
	jmp finish2
	lessThan2:
	movq $1,%rax
	finish2:
	cmpq $1,%rax
	je loop1
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
	movq 8(%rbp),%rax
	cmpq %rdx,%rax
	jl lessThan3
	movq $0,%rax
	jmp finish3
	lessThan3:
	movq $1,%rax
	finish3:
	cmpq $0,%rax
	je else12
	movq $0,%rax
	movq %rax,-8(%rbp)
	jmp done12
	else12:
	movq -16(%rbp),%rax
	movq %rax,%rdx
	movq 8(%rbp),%rax
	cmpq %rdx,%rax
	jl lessThan4
	movq $0,%rax
	jmp finish4
	lessThan4:
	movq $1,%rax
	finish4:
	notq %rax
	cmpq $0,%rax
	je else13
	movq $0,%rax
	movq %rax,-8(%rbp)
	jmp done13
	else13:
	movq $1,%rax
	movq %rax,-8(%rbp)
	done13:
	done12:
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
	jmp test2
	loop2:
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
	test2:
	movq 16(%rdi),%rax
	movq %rax,%rdx
	movq -8(%rbp),%rax
	cmpq %rdx,%rax
	jl lessThan5
	movq $0,%rax
	jmp finish5
	lessThan5:
	movq $1,%rax
	finish5:
	cmpq $1,%rax
	je loop2
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
	movq 8(%rbp),%rax
	movq %rax,16(%rdi)
	movq 8(%rbp),%rax
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
	test_NewArray1:
	testq $0,%rdx
	je done_NewArray1
	addq %rcx,%rax
	movq $0,(%rax)
	shlq $2,%rcx
	decq %rdx
	jmp test_NewArray1
	done_NewArray1:
	popq %rax
	movq %rax,8(%rdi)
	movq $1,%rax
	movq %rax,-8(%rbp)
	movq $1,%rax
	movq %rax,%rdx
	movq 16(%rdi),%rax
	addq %rdx,%rax
	movq %rax,-16(%rbp)
	jmp test3
	loop3:
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
	test3:
	movq 16(%rdi),%rax
	movq %rax,%rdx
	movq -8(%rbp),%rax
	cmpq %rdx,%rax
	jl lessThan6
	movq $0,%rax
	jmp finish6
	lessThan6:
	movq $1,%rax
	finish6:
	cmpq $1,%rax
	je loop3
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


