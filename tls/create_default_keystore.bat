@echo off
setlocal

:: Define the keystore folder and create it if not existing
set SCRIPT_DIR=%~dp0
set KEYSTORE_DIR=%SCRIPT_DIR%keystore

if not exist "%KEYSTORE_DIR%" (
    mkdir "%KEYSTORE_DIR%"
)

:: Set default values
set KEYSTORE_NAME=springboot.p12
set ALIAS=api
set VALIDITY=3650
set KEY_SIZE=2048

:: Prompt only for the mandatory keystore password
echo Enter the keystore password (minimum 6 characters):
set /p KEYPASS=

:: Create the keystore in the specified folder
echo Creating the keystore...
keytool -genkeypair -alias %ALIAS% -keyalg RSA -keysize %KEY_SIZE% -storetype PKCS12 -keystore "%KEYSTORE_DIR%\%KEYSTORE_NAME%" -validity %VALIDITY% -storepass %KEYPASS% -keypass %KEYPASS% -dname "CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, S=Unknown, C=Unknown" >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Failed to create the keystore. Please check your inputs and try again.
    goto end
)

echo Keystore %KEYSTORE_NAME% created successfully in %KEYSTORE_DIR%.
goto end

:end
endlocal
