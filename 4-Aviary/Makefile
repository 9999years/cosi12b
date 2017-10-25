javac_opts=-encoding utf8
common_opts=-cp ".;junit-4.12.jar;hamcrest-core-1.3.jar;"
junit_ops=org.junit.runner.JUnitCore

bird=Bird

docs: FORCE
	javadoc $(javac_opts) $(common_opts) -d docs *.java

FORCE:

zip:
	7z a -wsrc/main/java -mx=5 -tzip pa4.zip @zipfiles.txt
