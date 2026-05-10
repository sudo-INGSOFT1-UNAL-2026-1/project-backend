@echo off

echo =========================================
echo  PREPARACION DEL PROYECTO ERP
echo =========================================
echo.

REM -----------------------------------------------------
REM BLOQUE 1: CARGAR VARIABLES DEL .ENV
REM -----------------------------------------------------

echo [1/8] Cargando variables del archivo .env...

for /f "tokens=1,2 delims==" %%a in (.env) do (
    set %%a=%%b
)

echo.

REM -----------------------------------------------------
REM BLOQUE 2: VERIFICAR JAVA
REM -----------------------------------------------------

echo [2/8] Verificando Java...

java -version
IF %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java no esta instalado.
    exit /b
)

echo.

REM -----------------------------------------------------
REM BLOQUE 3: VERIFICAR DOCKER
REM -----------------------------------------------------

echo [3/8] Verificando Docker...

docker --version
IF %ERRORLEVEL% NEQ 0 (
    echo ERROR: Docker no esta instalado.
    exit /b
)

echo.

REM -----------------------------------------------------
REM BLOQUE 4: LEVANTAR CONTENEDORES
REM -----------------------------------------------------

echo [4/8] Levantando contenedores Docker...

docker compose up -d

echo.

REM -----------------------------------------------------
REM BLOQUE 5: ESPERAR MYSQL
REM -----------------------------------------------------

echo [5/8] Esperando inicio de MySQL...

timeout /t 15 > nul

echo.

REM -----------------------------------------------------
REM BLOQUE 6: EJECUTAR SCRIPTS SQL
REM -----------------------------------------------------

echo [6/8] Ejecutando scripts SQL...

docker exec -i sistema_gestion_db mysql -u root -p%MYSQL_ROOT_PASSWORD% %MYSQL_DATABASE% < sql/init.sql

docker exec -i sistema_gestion_db mysql -u root -p%MYSQL_ROOT_PASSWORD% %MYSQL_DATABASE% < sql/seed.sql

echo.

REM -----------------------------------------------------
REM BLOQUE 7: INSTALAR DEPENDENCIAS Y TESTS
REM -----------------------------------------------------

echo [7/8] Instalando dependencias y ejecutando pruebas...

IF EXIST mvnw.cmd (
    call mvnw.cmd clean install
    call mvnw.cmd test
) ELSE (
    mvn clean install
    mvn test
)

echo.

REM -----------------------------------------------------
REM BLOQUE 8: INICIAR SPRING BOOT
REM -----------------------------------------------------

echo [8/8] Iniciando aplicación Spring Boot...

IF EXIST mvnw.cmd (
    call mvnw.cmd spring-boot:run
) ELSE (
    mvn spring-boot:run
)

echo.
echo =========================================
echo  PROYECTO INICIADO CORRECTAMENTE
echo =========================================