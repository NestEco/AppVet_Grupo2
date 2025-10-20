package com.example.appvet_grupo2.navigation

sealed class Screen(val route:String) {
    //El login. Conexiones: Registro, Home
    data object Login : Screen("login_page")

    //Registro de cuenta. Se pide nombre, correo, contraseña, y verificacion de contraseña. Pide validación
    //Conexiones: Home
    data object Registro : Screen("registro_page")

    //La página donde están todas las opciones principales.
    //Conexiones: ReservarHora, Perfil, Mascotas, Agenda, Login
    data object Home : Screen("home_page")

    //Eliges tres opciones entre "Consulta", "Vacuna y Deparacitacion", y "Cirugia y Procesos Especializados"
    //Conexiones: SelectFecha
    data object ReservarHora : Screen("reservarHora_page")

    //Seleccionas tu fecha para agendar hora
    //Conexiones: SelectHora
    data object SelectFecha : Screen("fecha_page")

    //Seleccionas la hora para agendarla
    //Conexiones: Home
    data object SelectHora : Screen("hora_page")

    //El perfil, con foto, nombre, especialidad
    //Conexiones: Home
    data object Perfil : Screen("perfil_page")

    //Se muestran las mascotas del usuario y una opción de agregar
    //Conexiones: Home, RegistrarMascotas
    data object Mascotas : Screen("mascotas_page")

    //Permite registrar una mascota, con nombre, edad, y especie
    //Conexiones: Mascotas
    data object RegistrarMascotas : Screen("registrarMascota_page")

    //Permite revisar las horas agendadas
    //Conexiones: Home
    data object Agenda : Screen("agenda_page")

}