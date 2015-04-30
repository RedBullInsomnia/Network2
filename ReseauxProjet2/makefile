JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Worker.java \
	WebServer.java \
	HTTPReply.java \
	HTTPRequest.java \
	Sessions.java \
	Cookies.java \
	Messages.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class