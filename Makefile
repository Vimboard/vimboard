all: build

build: clean rebuild

clean:
	mvn clean

rebuild:
	mvn package

run:
	java -jar target/vimboard.jar
