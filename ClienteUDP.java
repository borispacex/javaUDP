import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteUDP {
	// Iniciamos el Main
	public static void main(String[] args) throws IOException {
		// Creamos e inicializamos el objeto cliente con el puerto 9990.
		ClienteUDP Cliente = new ClienteUDP("127.0.0.1", 9990);
		Cliente.iniciar();
	}

	// Declaramos las variables de nuestro Socket UDP
	int puerto;
	InetAddress servidorDestino;
	DatagramSocket socketUDP;
	DatagramPacket paqueteRecibido;
	DatagramPacket paqueteAEnviar;

	// Constructor para ingresar nuestra direccion y puerto
	public ClienteUDP(String d, int p) throws UnknownHostException {
		servidorDestino = InetAddress.getByName(d);
		puerto = p;
	}

	// Funcion para iniciar la consulta al servidor
	public void iniciar() throws IOException {
		Scanner lectura = new Scanner(System.in);

		// Creamos un socket bajo UDP
		socketUDP = new DatagramSocket();

		// -- ENVIANDO EL PAQUETE
		// Leemos el mensaje que vamos a enviar al servidor
		System.out.print("\nCual es tu nombre: ");
		String cadenaEnviar = lectura.nextLine();
		// Convertimos el mensajes a Bytes
		byte[] mensajeEnviar = cadenaEnviar.getBytes();
		// Declaramos nuestro paquete el cual enviaremos
		paqueteAEnviar = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, servidorDestino, puerto);
		// Enviamos el Datagrama
		socketUDP.send(paqueteAEnviar);

		// -- RECIBIENDO PAQUETE
		// Se recibe el paquete
		paqueteRecibido = new DatagramPacket(new byte[1024], 1024);
		// Esperamos a que nos llegue respuesta desde el servidor
		socketUDP.receive(paqueteRecibido);
		// Ha llegado un datagrama, para ver los datos se utiliza getData()
		String mensaje = new String(paqueteRecibido.getData());
		System.out.println("Respuesta del servidor : " + mensaje);
		finalizar();
	}

	// Funcion para finalizar la conexion
	public void finalizar() {
		try {
			socketUDP.close();
			System.out.println("Conexion Finalizada");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
