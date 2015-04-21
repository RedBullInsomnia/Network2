JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Worker.java \
	Request.java \
	EchoServer.java \
	EchoClient.java \
	Reply.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class