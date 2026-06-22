# AgendaSENA

## Ejecución paso a paso

1. Clonar el repositorio:

```bash
git clone 
cd agendasena
```

2. Configurar las credenciales de MySQL en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/agendasena?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Bogota
spring.datasource.username=root
spring.datasource.password=tu_password
```

3. Asegurate de tener un servidor MySQL corriendo localmente (puerto 3306). No es necesario crear la base de datos manualmente: `createDatabaseIfNotExist=true` la crea automáticamente.

Verificar servidor MySQL
Abre **MySQL Workbench** o el gestor de servicios de tu sistema.

    si abre gestor de servicios
        - abre **Servicios** vaya a la barra de busqueda y busque **MySQL80** y verifica que el estado sea **En ejecución**. Si no lo está, clic derecho → **Iniciar**.

4. Compilar y ejecutar el proyecto:

```bash
.\mvnw spring-boot:run
```

5. La API queda disponible en: http://localhost:8080
```
Al arrancar, Hibernate crea las tablas y `data.sql` carga automáticamente los datos de prueba (ambientes, instructores y reservas).
```