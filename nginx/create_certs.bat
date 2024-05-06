@echo off

:: Set the directory to the location of the script
cd /d %~dp0

:: Check if openssl is available
openssl version >nul 2>&1
if errorlevel 1 (
    echo OpenSSL is not installed or not in the PATH.
    exit /b 1
)

:: Create certs directory if it does not exist
if not exist "certs" (
    mkdir certs
)

:: Generate a new self-signed certificate and key
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout certs/nginx.key -out certs/nginx.crt -subj "/C=US/ST=Denial/L=Springfield/O=Dis/CN=www.localhost.com"

echo Certificate and key have been generated.
