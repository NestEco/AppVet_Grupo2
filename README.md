AppVet - Gestión Veterinaria

Aplicación móvil desarrollada Por Lucia Gomez y Rodrigo Carcamo en Kotlin, que permite a los usuarios registrar, agendar y gestionar citas veterinarias de sus mascotas.

---

## Características

- Registro e inicio de sesión de usuarios
- Visualización de perfil
- Gestión de mascotas
- Agenda de horas veterinarias
- Multiples interface

---

## EndPoints

Microservicio de Usuarios (Puerto 8080)
Base URL: http://34.205.86.178:8080/api/usuarios
- Health Check (Verificar estado del microservicio)
  GET /api/usuarios/health
- Obtener todos los usuarios
  GET /api/usuarios
- Obtener usuario por ID
  GET /api/usuarios/{id}
  Parámetros de ruta: id (string, requerido)
- Obtener usuario por email
  GET /api/usuarios/email/{email}
  Parámetros de ruta: email (string, requerido)
- Registrar nuevo usuario
  POST /api/usuarios/registro
  Parámetros requeridos: nombre, email, password, rol
- Login
  POST /api/usuarios/login
  Parámetros requeridos: email, password
- Actualizar usuario
  PUT /api/usuarios/{id}
  Parámetros de ruta: id (string, requerido)
- Eliminar usuario
  DELETE /api/usuarios/{id}
  Parámetros de ruta: id (string, requerido)

Microservicio de Mascotas (Puerto 8081)
Base URL: http://34.205.86.178:8081/api/mascotas
- Health Check (Verificar estado del microservicio)
  GET /api/mascotas/health
- Obtener todas las mascotas
  GET /api/mascotas
- Obtener mascota por ID
  GET /api/mascotas/{id}
  Parámetros de ruta: id (string, requerido)
- Obtener mascotas por usuario
  GET /api/mascotas/usuario/{usuarioId}
  Parámetros de ruta: usuarioId (string, requerido)
- Obtener mascotas por especie
  GET /api/mascotas/especie/{especie}
  Parámetros de ruta: especie (string, requerido)
- Contar mascotas por usuario
  GET /api/mascotas/usuario/{usuarioId}/count
  Parámetros de ruta: usuarioId (string, requerido)
- Crear nueva mascota
  POST /api/mascotas
  Parámetros requeridos: nombre, edad, especie, usuarioId
- Actualizar mascota
  PUT /api/mascotas/{id}
  Parámetros de ruta: id (string, requerido)
- Eliminar mascota
  DELETE /api/mascotas/{id}
  Parámetros de ruta: id (string, requerido)

Microservicio de Horas Agendadas (Puerto 8082)
Base URL: http://34.205.86.178:8082/api/horas-agendadas
- Health Check
  GET /api/horas-agendadas/health
- Obtener todas las horas agendadas
  GET /api/horas-agendadas
- Obtener hora agendada por ID
  GET /api/horas-agendadas/{id}
  Parámetros de ruta: id (string, requerido)
- Obtener horas agendadas por usuario
  GET /api/horas-agendadas/usuario/{usuarioId}
  Parámetros de ruta: usuarioId (string, requerido)
- Obtener horas agendadas por mascota
  GET /api/horas-agendadas/mascota/{mascotaId}
  Parámetros de ruta: mascotaId (string, requerido)
- Obtener horas agendadas por estado
  GET /api/horas-agendadas/estado/{estado}
  Parámetros de ruta: estado (string, requerido)
- Obtener horas agendadas por tipo
  GET /api/horas-agendadas/tipo/{tipo}
  Parámetros de ruta: tipo (string, requerido)
- Crear nueva hora agendada
  POST /api/horas-agendadas
  Parámetros requeridos: hora, minuto, tipo, usuarioId, mascotaId, fecha
- Actualizar hora agendada
  PUT /api/horas-agendadas/{id}
  Parámetros de ruta: id (string, requerido)
- Cambiar estado de hora agendada
  PATCH /api/horas-agendadas/{id}/estado
  Parámetros de ruta: id (string, requerido)
- Eliminar hora agendada
  DELETE /api/horas-agendadas/{id}
  Parámetros de ruta: id (string, requerido)

---

## Pasos para ejecutar:
1.- Asegurarse que la base de datos en AWS esté corriendo
2.- Asegurarse que los microservicios en AWS estén corriendo
3.- Instalar la APK
4.- Ejecutar normalmente
