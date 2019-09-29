import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.omg.CORBA.portable.OutputStream;

public class ServerMain {
public static void main(String[] args) {
	int port = 7718;
	Server server = new Server(port);
	server.start();
	}
	
}

