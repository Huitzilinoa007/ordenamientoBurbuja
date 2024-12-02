/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ordburbuja;

import java.net.*;
import static ordburbuja.EmisorReceptor.mandarMensaje;

/**
 *
 * @author Usuario
 */
public class Main {

    public static void main(String[] args) {
       if (args.length < 1) {
            System.out.println("Se requiere el rol como argumento (servidor o cliente).");
            return;
        }

        String rol = args[0]; // 'servidor' o 'cliente'

        // Determinar el rol y ejecutar la función correspondiente
        if (rol.equalsIgnoreCase("servidor")) {
            // Si es servidor, ejecutar la función del servidor
            if (args.length != 4) {
                System.out.println("Este programa requiere 4 argumentos para el servidor.");
                return;
            }
            ReceptorEmisor.ordenar(args);
        } else if (rol.equalsIgnoreCase("cliente")) {
            // Si es cliente, ejecutar la función del cliente
            if (args.length != 5) {
                System.out.println("Este programa requiere 5 argumentos para el cliente.");
                return;
            }
            EmisorReceptor.mandarMensaje(args);
        } else {
            System.out.println("Rol no válido. Use 'servidor' o 'cliente'.");
        }
    }
}
