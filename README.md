# password-validation

Clojure REST Service to validate password with its hashed password of algorithm [Scrypt](https://github.com/weavejester/crypto-password).

## Installation

### Using [Leiningen](https://leiningen.org/)
First you need to install clojure using Leiningen then follow the following instruction.
```sh
1. git clone <this repo> <folder name>
2. cd <folder name>
3. lein run
```
*Running webserver at http:/127.0.0.1:3000/*

### Using [Docker](https://www.docker.com/)
```sh
1. git clone <this repo> <folder name>
2. docker build -t password-validation-service .
3. docker run -it --rm -p 3000:3000 password-validation-service
```
*Running webserver at http:/127.0.0.1:3000/*

## Usage

API: http://localhost:3000/api/v0/match-password

`Must needed` Headers: 

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |
| password     | *Your password*  |
| hashPassword | *Your hash*      |



## License

Copyright © 2020 FIXME

