package ordburbuja;

import java.net.*;
import java.io.*;

public class MiSocketDatagrama extends DatagramSocket {

    static final int MAX_LON = 1024;

    MiSocketDatagrama(int numPuerto) throws SocketException {
        super(numPuerto);
    }

    // Método para enviar el arreglo bidimensional
    public void enviaMensaje(InetAddress maquinaReceptora, int puertoReceptor, String arregloTemp[][], int longX, int longY) throws IOException {
        String mensaje = convertirArregloAString(arregloTemp, longX, longY);
        byte[] almacenEnvio = mensaje.getBytes();
        DatagramPacket datagrama = new DatagramPacket(almacenEnvio, almacenEnvio.length, maquinaReceptora, puertoReceptor);
        this.send(datagrama);
    }

    public void enviaMensajeVuelta(InetAddress maquinaReceptora, int puertoReceptor, int arregloRecibido[][]) throws IOException {
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
        byte[] almacenEnvio = mensajeOrdenado.getBytes();
        DatagramPacket datagrama = new DatagramPacket(almacenEnvio, almacenEnvio.length, maquinaReceptora, puertoReceptor);
        this.send(datagrama);
    }

    // Método para recibir un mensaje
    public String recibeMensaje() throws IOException {
        byte[] almacenRecepcion = new byte[MAX_LON];
        DatagramPacket datagrama = new DatagramPacket(almacenRecepcion, MAX_LON);
        this.receive(datagrama);
        String mensaje = new String(almacenRecepcion, 0, datagrama.getLength());
        return mensaje;
    }

    // Convertir el arreglo bidimensional a String para enviarlo
    public static String convertirArregloAString(String[][] arreglo, int longX, int longY) {
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
}
