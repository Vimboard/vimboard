all: build

build: clean rebuild

clean:
	mvn clean

rebuild:
	mvn package

run:
	java -jar server/target/vimboard-server.jar

run-cli:
	java -jar cli/target/vimboard-cli.jar ${arg0}
