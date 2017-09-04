docs: FORCE
	javadoc -d docs *.java
	dot -o docs/flow-diagram.pdf -Tpdf flow-diagram.dot
FORCE:
main:
	javac *.java
run:
	java PersonalityTest
