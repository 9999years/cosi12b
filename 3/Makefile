javac_opts=-encoding utf8
common_opts=-cp ".;junit-4.12.jar;hamcrest-core-1.3.jar;"
junit_ops=org.junit.runner.JUnitCore

date=Date
udate=UnvalidatedDate
datetest=DateTest
bday=Birthday

docs: FORCE
	javadoc $(javac_opts) $(common_opts) -d docs *.java

FORCE:

main $(date).class $(udate).class $(bday).class: $(bday).java $(date).java $(udate).java
	javac $(javac_opts) $(common_opts) $^

run: main
	java $(common_opts) $(bday)

test:
	javac $(javac_opts) $(common_opts) $(datetest).java
	java  $(junit_ops)  $(common_opts) $(datetest)
