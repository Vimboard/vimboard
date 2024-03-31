all: clean release

build: clean package

clean:
	mvn clean

package:
	mvn package

release:
	mvn -P release package

run:
	cd target; java -jar vimboard-server.jar

run-cli:
	cd target; java -jar vimboard-cli.jar ${arg0} ${arg1} ${arg2} ${arg3}

run-cli-createdb:
	cd target; java -jar vimboard-cli.jar drop-schema
	cd target; java -jar vimboard-cli.jar create-schema
