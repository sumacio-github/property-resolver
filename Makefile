all: run

build:
	mvn clean package

docker: build
	docker build -t testapp .

run: docker
	docker run -it --rm testapp
