#!/bin/bash


echo "========================================="
echo " PREPARACION DEL PROYECTO ERP"
echo "========================================="
echo ""

# -----------------------------------------------------
# BLOQUE 1: CARGAR VARIABLES DE ENTORNO
# -----------------------------------------------------

echo "[1/8] Cargando variables del archivo .env..."

export $(grep -v '^#' .env | xargs)

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

echo ""

# -----------------------------------------------------
# BLOQUE 4: LEVANTAR CONTENEDORES
# -----------------------------------------------------

echo "[4/8] Levantando contenedores Docker..."

docker compose up -d

echo ""

# -----------------------------------------------------
# BLOQUE 5: ESPERAR MYSQL
# -----------------------------------------------------

echo "[5/8] Esperando inicio de MySQL..."

sleep 15

echo ""

# -----------------------------------------------------
# BLOQUE 6: EJECUTAR SCRIPTS SQL
# -----------------------------------------------------

echo "[6/8] Ejecutando scripts SQL..."

docker exec -i sistema_gestion_db \
mysql -u root -p${MYSQL_ROOT_PASSWORD} ${MYSQL_DATABASE} \
< sql/init.sql

docker exec -i sistema_gestion_db \
mysql -u root -p${MYSQL_ROOT_PASSWORD} ${MYSQL_DATABASE} \
< sql/seed.sql

echo ""

# -----------------------------------------------------
# BLOQUE 7: INSTALAR DEPENDENCIAS Y TESTS
# -----------------------------------------------------

echo "[7/8] Instalando dependencias y ejecutando pruebas..."

if [ -f "./mvnw" ]; then
    ./mvnw clean install
    ./mvnw test
else
    mvn clean install
    mvn test
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