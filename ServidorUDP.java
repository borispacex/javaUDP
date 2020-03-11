import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
	// Iniciamos
	public static void main(String[] args) {
		// Creamos e inicializamos el objeto servidor con el puerto 9990.
		ServidorUDP Servidor = new ServidorUDP(9990);
		Servidor.iniciar();
	}

	// Declaramos variables de tipo Socket que vamos a usar
	DatagramSocket socketUDP;
	DatagramPacket paqueteRecibido;
	DatagramPacket paqueteAEnviar;
	int puerto;

	// Constructor para enviar el puerto
	public ServidorUDP(int p) {
		puerto = p;
	}

	// Funcion para iniciar el Servidor
	public void iniciar() {
		try {
			// Creamos un socket bajo UDP
			socketUDP = new DatagramSocket(puerto);
			System.out.println(" - SERVIDOR UDP INICIADO - ");
			System.out.println("- Esperando Cliente -");
			while (true) {

				// -- RECIBIENDO EL PAQUETE
				// Declaramos nuestro paquete el cual recibiremos
				paqueteRecibido = new DatagramPacket(new byte[1024], 1024);
				// Recibe el paquete
				socketUDP.receive(paqueteRecibido);
				System.out.println("Llego un paquete...");
				// Recibiendo vector e bytes y convirtiendo a cadena
				String cadenaRecibido = new String(paqueteRecibido.getData());
				System.out.println("Solicitud Recibida: " + cadenaRecibido);

				// -- ENVIANDO EL PAQUETE
				String cadenaEnviar = "Bienvenido " + cadenaRecibido;
				byte mensajeEnviar[] = new byte[1024];
				// Convierte cadena a Vector de Bytes
				mensajeEnviar = cadenaEnviar.getBytes();
				// Se crea el datagrama que contendra el mensaje
				paqueteAEnviar = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, paqueteRecibido.getAddress(),
						paqueteRecibido.getPort());
				// Luego se Envia el paquete al cliente
				socketUDP.send(paqueteAEnviar);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcion para finalizar la conexion
	public void finalizar() {
		try {
			socketUDP.close();
			System.out.println("Conexion finalizada");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
