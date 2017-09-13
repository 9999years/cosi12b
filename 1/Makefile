javac_opts=-encoding utf8
common_opts=-cp ".;junit-4.12.jar;hamcrest-core-1.3.jar;"

docs: FORCE
	javadoc $(javac_opts) $(common_opts) -d docs PersonalityTest.java
	dot -odocs/flow-diagram.pdf -Tpdf flow-diagram.dot
FORCE:
main:
	javac $(javac_opts) $(common_opts) PersonalityTest.java
run:
	java $(common_opts) PersonalityTest
test:
	javac $(javac_opts) $(common_opts) PersonalityTestTest.java
	java $(common_opts) org.junit.runner.JUnitCore PersonalityTestTest
