package ordburbuja;

import java.io.*;
import java.net.*;

public class EmisorReceptor {

    public static void mandarMensaje(String cadena[]) {
        if (cadena.length != 5) {
            System.out.println("Este programa requiere 5 argumentos de línea de mandato");
        } else {
            try {
                InetAddress maquinaReceptora = InetAddress.getByName(cadena[1]); // IP
                int puertoReceptor = Integer.parseInt(cadena[2]); // Puerto receptor
                int miPuerto = Integer.parseInt(cadena[3]); // Mi puerto
                int longX = Integer.parseInt(cadena[4]); // Longitud en x
                int longY = Integer.parseInt(cadena[5]); // Longitud en y

                MiSocketDatagrama miSocket = new MiSocketDatagrama(miPuerto);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                // Crear un arreglo bidimensional de enteros
                String[][] arregloTemp = new String[longX][longY];

                // Rellenar el arreglo con valores proporcionados por el usuario
                for (int i = 0; i < longX; i++) {
                    for (int j = 0; j < longY; j++) {
                        System.out.print("Número en la posición: [" + i + "] [" + j + "]: ");
                        arregloTemp[i][j] = reader.readLine();
                    }
                }

                // Enviar el arreglo bidimensional
                miSocket.enviaMensaje(maquinaReceptora, puertoReceptor, arregloTemp, longX, longY);

                // Esperar el arreglo ordenado
                String mensajeRecibido = miSocket.recibeMensaje();
                System.out.println("[Recibido]: " + mensajeRecibido);

                // Mostrar el arreglo ordenado
                String[][] arregloOrdenado = convertirStringAArreglo(mensajeRecibido, longX, longY);
                System.out.println("Arreglo ordenado recibido:");
                for (int i = 0; i < longX; i++) {
                    for (int j = 0; j < longY; j++) {
                        System.out.print(arregloOrdenado[i][j] + " ");
                    }
                    System.out.println();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Convertir el arreglo bidimensional a String para enviarlo
    public static String convertirArregloAString(int[][] arreglo, int longX, int longY) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longX; i++) {
            for (int j = 0; j < longY; j++) {
                sb.append(arreglo[i][j]);
                if (j < longY - 1) {
                    sb.append(",");
                }
            }
            if (i < longX - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    // Convertir el String recibido a un arreglo bidimensional
    public static String[][] convertirStringAArreglo(String mensaje, int longX, int longY) {
        String[][] arreglo = new String[longX][longY];
        String[] filas = mensaje.split(";");
        for (int i = 0; i < longX; i++) {
            String[] valores = filas[i].split(",");
            for (int j = 0; j < longY; j++) {
                arreglo[i][j] = valores[j];
            }
        }
        return arreglo;
    }
}
