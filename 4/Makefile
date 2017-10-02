javac_opts=-encoding utf8
common_opts=-cp ".;junit-4.12.jar;hamcrest-core-1.3.jar;"
junit_ops=org.junit.runner.JUnitCore

bird=Bird

docs: FORCE
	javadoc $(javac_opts) $(common_opts) -d docs *.java

FORCE:

main: FORCE
	javac $(javac_opts) $(common_opts) *.java

run: main
	javac $(javac_opts) $(common_opts) Aviary.java
	java $(common_opts) Aviary

test:
	javac $(javac_opts) $(common_opts) MiscTest.java
	java  $(common_opts)  $(junit_ops) MiscTest
	javac $(javac_opts) $(common_opts) WorldPointTest.java
	java  $(common_opts)  $(junit_ops) WorldPointTest
	javac $(javac_opts) $(common_opts) AbstractBirdTest.java
	java  $(common_opts)  $(junit_ops) AbstractBirdTest
	javac $(javac_opts) $(common_opts) BirdTest.java
	java  $(common_opts)  $(junit_ops) BirdTest
clean:
	rm *.class
