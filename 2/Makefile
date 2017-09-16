javac_opts=-encoding utf8
common_opts=-cp ".;junit-4.12.jar;hamcrest-core-1.3.jar;"

docs: FORCE
	javadoc $(javac_opts) $(common_opts) -d docs LetterInventory.java
FORCE:
main:
	javac $(javac_opts) $(common_opts) LetterInventory.java
run: main
	javac $(javac_opts) $(common_opts)  FrequencyAnalysis.java
	java $(common_opts) FrequencyAnalysis
test: mytests
	javac $(javac_opts) $(common_opts) Test1.java
	java  $(common_opts)               Test1
	javac $(javac_opts) $(common_opts) Test2.java
	java  $(common_opts)               Test2
	javac $(javac_opts) $(common_opts) Test3.java
	java  $(common_opts)               Test3
mytests:
	javac $(javac_opts) $(common_opts) LetterInventoryTest.java
	java  $(common_opts) org.junit.runner.JUnitCore LetterInventoryTest
clean:
ifeq ($(OS),Windows_NT)
	del *.class
else
	rm *.class
endif
