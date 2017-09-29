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
	javac $(javac_opts) $(common_opts) $(udate)Test.java
	java  $(common_opts) $(junit_ops) $(udate)Test
	javac $(javac_opts) $(common_opts) $(date)Test.java
	java  $(common_opts) $(junit_ops) $(date)Test
clean:
	$(rm) *.class
