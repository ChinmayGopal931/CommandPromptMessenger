import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
	public int port;
	ArrayList<ServerSlave> list = new ArrayList<ServerSlave>();

	public Server(int port) {
		this.port = port;
	}

	public void run() {

		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				System.out.println("About to accept client Connection..");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Accepted connection from " + clientSocket);
				ServerSlave slave = new ServerSlave(clientSocket, this);
				list.add(slave);
				slave.start();
			}
		} catch (IOException e) {
			 e.printStackTrace();
		}
	}

	public ArrayList<ServerSlave> getList() {
		return list;
	}

	public void removeList(ServerSlave serverWorker) {
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).equals(serverWorker))
				list.remove(i);
		}
	}
}
