# 🧱 Prueba Técnica – Juan Mantilla

### Implementación de Arquitectura Limpia (Clean Architecture Scaffold)

Este repositorio contiene el desarrollo de la **prueba técnica** orientada a la implementación de una **arquitectura
limpia (Clean Architecture)**.

---

## 🚀 Tecnologías principales

- **Java 21**
- **Spring Boot (WebFlux + R2DBC)**
- **Gradle** como gestor de dependencias
- **PostgreSQL / Neon Platform** (Base de datos relacional & Gestor de base de datos pública)
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

## 💾 Modelo de Datos
El modelo de datos está compuesto de tres tablas configuradas de la siguiente manera:

* La entidad Franquicia puede tener una o muchas Sucursales.
* Una Sucursal solo se relaciona con una Franquicia.
* Una Sucursal puede operar con uno muchos Productos.
* Un Producto solo se relaciona con una Sucursal.

 ```
    FRANQUICIA {
        int id PK
        varchar nombre UK
    }
    
    SUCURSAL {
        int id PK
        varchar nombre
        int franquicia_id FK
    }
    
    PRODUCTO {
        int id PK
        varchar nombre
        int stock
        int sucursal_id FK
    }
```


## 🧪 Pruebas con Postman

Se incluye una colección de **Postman** para probar los endpoints expuestos.

📁 Ruta de la colección:

```
/postman/Coleccion_Prueba_Tecnica.json
```

---

## 🧑‍💻 Autor

**Juan David Mantilla López**  
📧 [juandmantilla@outlook.com](mailto:juandmantilla@outlook.com)
