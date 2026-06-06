#!/bin/bash

echo "========================================="
echo " PREPARACION DEL PROYECTO ERP"
echo "========================================="
echo ""

# -----------------------------------------------------
# BLOQUE 1: CARGAR VARIABLES DE ENTORNO
# -----------------------------------------------------

echo "[1/8] Cargando variables del archivo .env..."

if [ ! -f .env ]; then
    echo "ERROR: No existe el archivo .env"
    exit 1
fi

export $(grep -v '^#' .env | xargs)

echo "Variables cargadas correctamente."
echo ""

# -----------------------------------------------------
# BLOQUE 2: VERIFICAR JAVA
# -----------------------------------------------------

echo "[2/8] Verificando Java..."

if ! command -v java &> /dev/null; then
    echo "ERROR: Java no está instalado."
    exit 1
fi

java -version

echo ""

# -----------------------------------------------------
# BLOQUE 3: VERIFICAR DOCKER
# -----------------------------------------------------

echo "[3/8] Verificando Docker..."

if ! command -v docker &> /dev/null; then
    echo "ERROR: Docker no está instalado."
    exit 1
fi

docker --version

if ! docker info >/dev/null 2>&1; then
    echo "ERROR: No se puede acceder al daemon de Docker."
    echo "       Esto suele ocurrir por falta de permisos en /var/run/docker.sock."
    echo "       Ejecuta el script con sudo o agrega tu usuario al grupo docker:" 
    echo "         sudo usermod -aG docker \$USER"
    echo "       Después cierra sesión y vuelve a iniciar sesión." 
    exit 1
fi

echo ""

# -----------------------------------------------------
# BLOQUE 4: LEVANTAR CONTENEDORES
# -----------------------------------------------------

echo "[4/8] Levantando contenedores Docker..."

# Source the .env file to ensure all variables are available
source .env

COMPOSE_CMD=""
if docker compose version >/dev/null 2>&1; then
    COMPOSE_CMD="docker compose"
elif docker-compose version >/dev/null 2>&1; then
    COMPOSE_CMD="docker-compose"
else
    echo "ERROR: No se encontró docker compose ni docker-compose."
    exit 1
fi

${COMPOSE_CMD} up -d

if [ $? -ne 0 ]; then
    echo "ERROR: No se pudieron iniciar los contenedores."
    exit 1
fi

echo ""

# -----------------------------------------------------
# BLOQUE 5: ESPERAR MYSQL
# -----------------------------------------------------

echo "[5/8] Esperando inicio de MySQL..."

sleep 15

echo "MySQL debería estar listo."
echo ""

# -----------------------------------------------------
# BLOQUE 6: EJECUTAR SCRIPTS SQL
# -----------------------------------------------------

echo "[6/8] Ejecutando scripts SQL..."

CONTAINER_NAME="erp_mysql"

# Verificar existencia del contenedor
if ! docker ps --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
    echo "ERROR: El contenedor ${CONTAINER_NAME} no está corriendo."
    docker ps
    exit 1
fi

# Verificar archivos SQL
if [ ! -f data/init.sql ]; then
    echo "ERROR: No existe data/init.sql"
    exit 1
fi

if [ ! -f data/seed.sql ]; then
    echo "ADVERTENCIA: No existe data/seed.sql"
fi

echo "Importando init.sql..."

docker exec -i ${CONTAINER_NAME} \
mysql -u root -p${MYSQL_ROOT_PASSWORD} ${MYSQL_DATABASE} \
< data/init.sql

if [ $? -ne 0 ]; then
    echo "ERROR: Falló la ejecución de init.sql"
    exit 1
fi

if [ -f data/seed.sql ]; then

    echo "Importando seed.sql..."

    docker exec -i ${CONTAINER_NAME} \
    mysql -u root -p${MYSQL_ROOT_PASSWORD} ${MYSQL_DATABASE} \
    < data/seed.sql

    if [ $? -ne 0 ]; then
        echo "ERROR: Falló la ejecución de seed.sql"
        exit 1
    fi
fi

echo "Scripts SQL ejecutados correctamente."
echo ""

# -----------------------------------------------------
# BLOQUE 7: INSTALAR DEPENDENCIAS Y TESTS
# -----------------------------------------------------

echo "[7/8] Instalando dependencias y ejecutando pruebas..."

if [ -f "./mvnw" ]; then
    chmod +x mvnw

    ./mvnw clean install

    if [ $? -ne 0 ]; then
        echo "ERROR: Falló Maven clean install"
        exit 1
    fi

    ./mvnw test

    if [ $? -ne 0 ]; then
        echo "ERROR: Fallaron los tests"
        exit 1
    fi
else
    mvn clean install

    if [ $? -ne 0 ]; then
        echo "ERROR: Falló Maven clean install"
        exit 1
    fi

    mvn test

    if [ $? -ne 0 ]; then
        echo "ERROR: Fallaron los tests"
        exit 1
    fi
fi

echo ""

# -----------------------------------------------------
# BLOQUE 8: INICIAR SPRING BOOT
# -----------------------------------------------------

echo "[8/8] Iniciando aplicación Spring Boot..."

if [ -f "./mvnw" ]; then
    ./mvnw spring-boot:run
else
    mvn spring-boot:run
fi

echo ""
echo "========================================="
echo " PROYECTO INICIADO CORRECTAMENTE"
echo "========================================="