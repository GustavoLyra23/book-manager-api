@echo off

REM Nome da imagem Docker
set IMAGE_NAME=java

REM Build da imagem Docker
echo Building Docker image...
docker build -t %IMAGE_NAME% .

REM Verificação se o build foi bem-sucedido
if %ERRORLEVEL% equ 0 (
    echo Docker image built successfully.
) else (
    echo Failed to build Docker image.
    exit /b 1
)

REM Executar o container
echo Running Docker container...
docker run -p 8080:8080 %IMAGE_NAME%
