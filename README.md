# Dynamic Email Generator

## Description
The Dynamic Email Generator is a service designed to create customized email addresses based on user-defined rules. It utilizes Spring Boot framework to serve dynamically generated email addresses through a RESTful API.

## Technologies Used
- Java 21
- Spring Boot 3
- Spring Security (TODO)
- Nginx (TODO)
- Docker (TODO)

## Setup Instructions
Note: The following instructions were tested written on a _Windows 10_ system

### Certificates
To create the self-signed certificates needed for the Nginx proxy configuration, you must have [OpenSSL](https://github.com/openssl/openssl) installed on your system.
After installing OpenSSL:
1. Open a CMD window
2. Navigate to the **nginx** folder in project root directory (TODO images)
3. Create a "**/certs**" directory, run ```mkdir certs```
4. To generate a self-signed certificate run ```openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout certs/nginx.key -out certs/nginx.crt -subj "/C=US/ST=Denial/L=Springfield/O=Dis/CN=www.localhost.com"```

This will create a **.crt** and **.key** file in the **\nginx\certs** directory. Note that it is referenced in [docker-compose.yml]() and must be updated in both places in the case of a change.

### Running the docker-compose bundle locally
To run the configuration described inside the [docker.compose.yml](), you must have [Docker](https://docs.docker.com/engine/install/) installed on your system.
After installing Docker:
1. Open a CMD window
2. Navigate to the project root directory
3. Run ```docker compose up```

This will build the new service Docker image described in the [Dockerfile]() and pull and run the Nginx image

(TODO)
## Custom Expression Language
(TODO language rules)