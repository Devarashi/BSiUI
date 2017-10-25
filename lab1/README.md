### System requirements
* Java 7
* Maven 4.0.0

### Run instructions
1. Enter terminal
2. To build project run: `mvn clean install`
3. To start server on specific port run: 
`mvn exec:java -Dexec.mainClass="Server" --Dexec.args="<port_number>"` 
4. To start client and make it connect to server on specific host, port and use encryption run:
`mvn exec:java -Dexec.mainClass="Client" --Dexec.args="<host> <port_number> <encryption>"`

### How to use it
1. Start server on specific port.
2. Start client, specify servers host and port. Optionally you can specify encryption, 
by default there will be no encryption.
3. Type message on client side, hit enter and check if it was correctly delivered to server.

### Objectives of this project
* Implement simple server client communicator
* Implement Diffie-Hellman protocol 