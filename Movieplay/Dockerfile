# Usamos una imagen base de Maven para compilar la aplicación
FROM maven:3.8.4-openjdk-17-slim AS build

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo pom.xml y descargamos las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# Compilamos la aplicación usando Maven (omitiendo las pruebas)
RUN mvn clean package -DskipTests

# Usamos una imagen base de OpenJDK para la aplicación en tiempo de ejecución
FROM openjdk:17-jdk-slim AS runtime

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el JAR generado desde la imagen de compilación a la imagen de tiempo de ejecución
COPY --from=build /app/target/*.jar ./app.jar

# Exponemos el puerto 8080 para que la aplicación escuche las solicitudes HTTP
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot al iniciar el contenedor
CMD ["java", "-jar", "./app.jar"]
