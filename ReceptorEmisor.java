package ordburbuja;

import java.net.*;
import java.io.*;

public class ReceptorEmisor {

    public static void ordenar(String cadena[]) {
        if (cadena.length != 4) {
            System.out.println("Este programa requiere 4 argumentos de línea de mandato");
        } else {
            try {
                InetAddress maquinaReceptora = InetAddress.getByName(cadena[1]);
                int puertoReceptor = Integer.parseInt(cadena[2]);
                int miPuerto = Integer.parseInt(cadena[3]);
                MiSocketDatagrama miSocket = new MiSocketDatagrama(miPuerto);

                while (true) {
                    System.out.println("\nEsperando mensaje del otro usuario...");
                    String mensajeRecibido = miSocket.recibeMensaje();
                    System.out.println("[Recibido]: " + mensajeRecibido);

                    if (mensajeRecibido.equalsIgnoreCase("salir")) {
                        break;
                    }

                    // Convertir el mensaje recibido (cadena) a un arreglo bidimensional
                    int[][] arregloRecibido = convertirStringAArreglo(mensajeRecibido);

                    int n = arregloRecibido.length;
                    int m = arregloRecibido[0].length;
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m - 1; j++) {
                            for (int k = 0; k < m - 1 - j; k++) {
                                if (arregloRecibido[i][k] > arregloRecibido[i][k + 1]) {
                                    int temp = arregloRecibido[i][k];
                                    arregloRecibido[i][k] = arregloRecibido[i][k + 1];
                                    arregloRecibido[i][k + 1] = temp;
                                }
                            }
                        }
                    }

                    String[] filas = mensajeRecibido.split(";");
                    int[][] arreglo = new int[filas.length][];
                    for (int i = 0; i < filas.length; i++) {
                        String[] valores = filas[i].split(",");
                        arreglo[i] = new int[valores.length];
                        for (int j = 0; j < valores.length; j++) {
                            arreglo[i][j] = Integer.parseInt(valores[j]);
                        }
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < arregloRecibido.length; i++) {
                        for (int j = 0; j < arregloRecibido[i].length; j++) {
                            sb.append(arregloRecibido[i][j]);
                            if (j < arregloRecibido[i].length - 1) {
                                sb.append(",");
                            }
                        }
                        if (i < arregloRecibido.length - 1) {
                            sb.append(";");
                        }
                    }

                    String mensajeOrdenado = sb.toString();

                    // Enviar el arreglo ordenado de vuelta
                    miSocket.enviaMensajeVuelta(maquinaReceptora, puertoReceptor, arregloRecibido);
                    System.out.println("[Sistema]: Arreglo ordenado enviado.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Método de burbuja para ordenar el arreglo bidimensional
    public static int[][] ordenarBurbuja(int[][] arreglo) {
        int n = arreglo.length;
        int m = arreglo[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m - 1; j++) {
                for (int k = 0; k < m - 1 - j; k++) {
                    if (arreglo[i][k] > arreglo[i][k + 1]) {
                        int temp = arreglo[i][k];
                        arreglo[i][k] = arreglo[i][k + 1];
                        arreglo[i][k + 1] = temp;
                    }
                }
            }
        }
        return arreglo;
    }

    // Convertir el String recibido a un arreglo bidimensional
    public static int[][] convertirStringAArreglo(String mensaje) {
        String[] filas = mensaje.split(";");
        int[][] arreglo = new int[filas.length][];
        for (int i = 0; i < filas.length; i++) {
            String[] valores = filas[i].split(",");
            arreglo[i] = new int[valores.length];
            for (int j = 0; j < valores.length; j++) {
                arreglo[i][j] = Integer.parseInt(valores[j]);
            }
        }
        return arreglo;
    }

    // Convertir el arreglo bidimensional a String para enviarlo
    public static String convertirArregloAString(int[][] arreglo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < arreglo[i].length; j++) {
                sb.append(arreglo[i][j]);
                if (j < arreglo[i].length - 1) {
                    sb.append(",");
                }
            }
            if (i < arreglo.length - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }
}
