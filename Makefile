ANTLRJAR = /usr/local/lib/antlr-4.7.2-complete.jar

export CLASSPATH := .:$(ANTLRJAR):${CLASSPATH}
antlr4 = java -jar $(ANTLRJAR)

SRCFILES = main.java AST.java
GENERATED = cocoListener.java cocoBaseListener.java cocoParser.java cocoBaseVisitor.java cocoVisitor.java cocoLexer.java

all:
	make main.class

main.class:	$(SRCFILES) $(GENERATED) coco.g4
	javac  -Xlint:unchecked  $(SRCFILES) $(GENERATED)

cocoListener.java:	coco.g4
	$(antlr4) -visitor coco.g4

test:	main.class
	java main coco_input.txt > coco_output.java
	cat coco_output.java

bigtest:	main.class
	java main example/interpreter.coco > example/interpreter.java
	cd example ; make test

clean:
	rm -f *.class
	rm -f $(GENERATED)
	rm -f *.tokens
	cd example ; make clean
