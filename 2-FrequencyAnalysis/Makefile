javac_opts=-encoding utf8
common_opts=-cp ".;junit-4.12.jar;hamcrest-core-1.3.jar;"

ifeq ($(OS),Windows_NT)
	rm=del
else
	rm=rm
endif

LetterInventory=LetterInventory.java
LetterInventoryTest=LetterInventoryTest.java
FrequencyAnalysis=FrequencyAnalysis.java

docs: FORCE $(LetterInventory)
	javadoc $(javac_opts) $(common_opts) -d docs $(LetterInventory)
FORCE:
main: $(LetterInventory)
	javac $(javac_opts) $(common_opts) $(LetterInventory)
run: main $(FrequencyAnalysis)
	javac $(javac_opts) $(common_opts) $(FrequencyAnalysis)
	java $(common_opts) $(FrequencyAnalysis)
test: mytests
	javac $(javac_opts) $(common_opts) Test1.java
	java  $(common_opts)               Test1
	javac $(javac_opts) $(common_opts) Test2.java
	java  $(common_opts)               Test2
	javac $(javac_opts) $(common_opts) Test3.java
	java  $(common_opts)               Test3
mytests:
	javac $(javac_opts) $(common_opts) $(LetterInventoryTest)
	java  $(common_opts) org.junit.runner.JUnitCore LetterInventoryTest
clean:
	$(rm) *.class
