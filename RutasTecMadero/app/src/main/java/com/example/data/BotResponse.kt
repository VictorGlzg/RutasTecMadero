package com.example.data

object BotResponse {
    fun basicResponses(_message:String) : String {
        val message = _message.lowercase()

        return when {
            //Hello
            message.contains("hola") -> {
                "Hola, es un gusto atenderlo."
            }
            message.contains("ayuda") -> {
                "Si necesita ayuda el número gobernamental para la atención es: 01 800 007 3705, por favor espere la ayuda. "
            }
            message.contains("información") || message.contains("informacion")-> {
                "Esta aplicación consiste de tres menús, el primero es el chatbot de ayuda, el segundo es el mapa " +
                        "donde se puede observar las rutas hacía cada salón del campus 2, y el último menú es para configurar los colores y el mapa."
            }
            message.contains("adiós") || message.contains("adios")-> {
                "Gracias por utilizar nuestra aplicación, espero tenga un ¡lindo día!"
            }
            message.contains("direcciones") -> {
                "En el mapa se pueden observar unos marcadores, al presionarlos se puede observar el edificio que representan."
            }
            else -> {
                "No entiendo la instrucción, intente escribir otra opción."
            }
        }
    }
}