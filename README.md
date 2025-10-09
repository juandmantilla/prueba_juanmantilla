# 🧱 Prueba Técnica – Juan Mantilla
### Implementación de Arquitectura Limpia (Clean Architecture Scaffold)

Este repositorio contiene el desarrollo de la **prueba técnica** orientada a la implementación de una **arquitectura limpia (Clean Architecture)**.

---

## 🚀 Tecnologías principales

- **Java 21**
- **Spring Boot (WebFlux + R2DBC)**
- **Gradle** como gestor de dependencias
- **PostgreSQL / Neon DB** (Base de datos relacional & Gestor de base de datos pública)
- **Reactor (Mono / Flux)** para programación reactiva

---

## ⚙️ Instalación y ejecución local

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/juandmantilla/prueba_juanmantilla.git
   cd prueba_juanmantilla
   git checkout develop
   git pull origin develop
   ```

2. **Configurar variables de conexión**
- Editar el archivo `application.yml` o las propiedades de conexión en `PostgresqlConnectionProperties.java`.
- Verificar los parámetros del host, usuario y contraseña.

3. **Iniciar la aplicación**
   ```bash
   ./gradlew bootRun
   ```

4. **Verificar la ejecución**
- La aplicación estará disponible en:
  ```
  http://localhost:8080
  ```

---

## 🧪 Pruebas con Postman

Se incluye una colección de **Postman** para probar los endpoints expuestos.

📁 Ruta de la colección:
```
/postman/Collection_Prueba_Tecnica.json
```
---


## 🧑‍💻 Autor

**Juan David Mantilla López**  
Desarrollador Backend | Java | AWS | Arquitecturas Limpias  
📧 [juandmantilla@outlook.com](mailto:juandmantilla@outlook.com)
