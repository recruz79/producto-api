# Producto API

Una API REST construida con Java 16 y Spring Boot 3 para la gestión de productos. Permite crear y buscar productos, con validaciones, manejo global de errores y registro de auditoría.

## 🚀 Características

- Crear y consultar productos
- Validación de datos
- Auditoría básica (creación y modificación)
- Manejo global de errores
- Logging en consola
- Pruebas unitarias con JUnit

## 🧰 Tecnologías

- Java 16
- Spring Boot 3
- Maven 3.8.1
- Lombok 1.18.30
- MySQL 8.0.32

## 🗃️ Modelo de la Tabla `producto`

| Campo         | Tipo         | Descripción                |
|---------------|--------------|----------------------------|
| `id`          | `Long`       | Identificador único        |
| `description` | `String`     | Descripción del producto   |
| `quantity`    | `Integer`    | Cantidad disponible        |
| `price`       | `BigDecimal` | Precio del producto        |

## 📦 Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/producto-api.git
   cd producto-api
   ```

2. Configura la base de datos en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/productos_db
   spring.datasource.username=usuario
   spring.datasource.password=contraseña
   ```

3. Compila y ejecuta la aplicación:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## 📬 Endpoints principales

### Crear producto

- **POST** `/productos`
- **Request JSON:**
  ```json
  {
    "description": "Teclado mecánico",
    "quantity": 10,
    "price": 129.99
  }
  ```

- **Response (201 Created):**
  ```json
  {
    "id": 1,
    "description": "Teclado mecánico",
    "quantity": 10,
    "price": 129.99
  }
  ```

### Listar productos

- **GET** `/productos`
- **Response:**
  ```json
  [
    {
      "id": 1,
      "description": "Teclado mecánico",
      "quantity": 10,
      "price": 129.99
    },
    {
      "id": 2,
      "description": "Mouse inalámbrico",
      "quantity": 25,
      "price": 49.99
    }
  ]
  ```

### Buscar producto por ID

- **GET** `/productos/{id}`
- **Response (200 OK):**
  ```json
  {
    "id": 1,
    "description": "Teclado mecánico",
    "quantity": 10,
    "price": 129.99
  }
  ```

## ✅ Pruebas

Ejecuta las pruebas unitarias con:

```bash
mvn test
```