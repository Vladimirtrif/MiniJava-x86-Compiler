	.text
	.globl asm_main
asm_main:
	pushq %rbp
	movq %rsp,%rbp
	pushq %rdi
	call BasicTests$BasicTests
	movq %rax,%rdi
	lea 8(%rdi),%rax
	call *(%rax)
	popq %rdi
	pushq %rdi
	movq %rax,%rdi
	call put
	popq %rdi
	movq %rbp,%rsp
	popq %rbp
	ret

	.data
TestPrint$$: .quad 0

	.text
	BasicTests$runTests:
	pushq %rbp
	movq %rsp,%rbp
	movq $0,%rax
	movq %rbp,%rsp
	popq %rbp
	ret
	BasicTests$BasicTests:
	pushq %rbp
	movq %rsp,%rbp
	pushq %rdi
	movq $8,%rdi
	call mjcalloc
	popq %rdi
	leaq BasicTests$$,%rdx
	movq %rdx,(%rax)
	movq %rbp,%rsp
	popq %rbp
	ret

	.data
BasicTests$$: .quad 0
	.quad BasicTests$BasicTests
	.quad BasicTests$runTests
	.text


