@echo off
setlocal enabledelayedexpansion

echo =========================================
echo  PREPARACION DEL PROYECTO ERP
echo =========================================
echo.

REM -----------------------------------------------------
REM BLOQUE 1: CARGAR VARIABLES DEL .ENV
REM -----------------------------------------------------

echo [1/8] Cargando variables del archivo .env...

IF NOT EXIST .env (
    echo ERROR: No existe el archivo .env
    exit /b 1
)

for /f "usebackq tokens=1,* delims==" %%a in (`findstr /v "^#" .env`) do (
    set %%a=%%b
)

echo Variables cargadas correctamente.
echo.

REM -----------------------------------------------------
REM BLOQUE 2: VERIFICAR JAVA
REM -----------------------------------------------------

echo [2/8] Verificando Java...

java -version > nul 2>&1

IF %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java no esta instalado.
    exit /b 1
)

java -version

echo.

REM -----------------------------------------------------
REM BLOQUE 3: VERIFICAR DOCKER
REM -----------------------------------------------------

echo [3/8] Verificando Docker...

docker --version > nul 2>&1

IF %ERRORLEVEL% NEQ 0 (
    echo ERROR: Docker no esta instalado.
    exit /b 1
)

docker --version

echo.

REM -----------------------------------------------------
REM BLOQUE 4: LEVANTAR CONTENEDORES
REM -----------------------------------------------------

echo [4/8] Levantando contenedores Docker...

docker compose up -d

IF %ERRORLEVEL% NEQ 0 (
    echo ERROR: No se pudieron iniciar los contenedores.
    exit /b 1
)

echo.

REM -----------------------------------------------------
REM BLOQUE 5: ESPERAR MYSQL
REM -----------------------------------------------------

echo [5/8] Esperando inicio de MySQL...

timeout /t 15 > nul

echo MySQL deberia estar listo.
echo.

REM -----------------------------------------------------
REM BLOQUE 6: EJECUTAR SCRIPTS SQL
REM -----------------------------------------------------

echo [6/8] Ejecutando scripts SQL...

set CONTAINER_NAME=erp_mysql

REM Verificar contenedor
docker ps --format "{{.Names}}" | findstr /x "%CONTAINER_NAME%" > nul

IF %ERRORLEVEL% NEQ 0 (
    echo ERROR: El contenedor %CONTAINER_NAME% no esta corriendo.
    docker ps
    exit /b 1
)

REM Verificar init.sql
IF NOT EXIST data\init.sql (
    echo ERROR: No existe data\init.sql
    exit /b 1
)

echo Importando init.sql...

docker exec -i %CONTAINER_NAME% mysql -u root -p%MYSQL_ROOT_PASSWORD% %MYSQL_DATABASE% < data\init.sql

IF %ERRORLEVEL% NEQ 0 (
    echo ERROR: Fallo la ejecucion de init.sql
    exit /b 1
)

REM Verificar seed.sql
IF EXIST data\seed.sql (

    echo Importando seed.sql...

    docker exec -i %CONTAINER_NAME% mysql -u root -p%MYSQL_ROOT_PASSWORD% %MYSQL_DATABASE% < data\seed.sql

    IF !ERRORLEVEL! NEQ 0 (
        echo ERROR: Fallo la ejecucion de seed.sql
        exit /b 1
    )

) ELSE (
    echo ADVERTENCIA: No existe data\seed.sql
)

echo Scripts SQL ejecutados correctamente.
echo.

REM -----------------------------------------------------
REM BLOQUE 7: INSTALAR DEPENDENCIAS Y TESTS
REM -----------------------------------------------------

echo [7/8] Instalando dependencias y ejecutando pruebas...

IF EXIST mvnw.cmd (

    call mvnw.cmd clean install

    IF %ERRORLEVEL% NEQ 0 (
        echo ERROR: Fallo Maven clean install
        exit /b 1
    )

    call mvnw.cmd test

    IF %ERRORLEVEL% NEQ 0 (
        echo ERROR: Fallaron los tests
        exit /b 1
    )

) ELSE (

    mvn clean install

    IF %ERRORLEVEL% NEQ 0 (
        echo ERROR: Fallo Maven clean install
        exit /b 1
    )

    mvn test

    IF %ERRORLEVEL% NEQ 0 (
        echo ERROR: Fallaron los tests
        exit /b 1
    )
)

echo.

REM -----------------------------------------------------
REM BLOQUE 8: INICIAR SPRING BOOT
REM -----------------------------------------------------

echo [8/8] Iniciando aplicacion Spring Boot...

IF EXIST mvnw.cmd (
    call mvnw.cmd spring-boot:run
) ELSE (
    mvn spring-boot:run
)

echo.
echo =========================================
echo  PROYECTO INICIADO CORRECTAMENTE
echo =========================================