import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

public class ServerSlave extends Thread{

	private Socket clientSocket;
	String userName;
	private Server server;
	public ServerSlave(Socket clientSocket, Server server) {
           this.clientSocket= clientSocket;
           this.server= server;
}
	public void run() {
		try {
			handleClient();
		} catch (InterruptedException e) {
			e.printStackTrace();
	}
	}
	private  void handleClient() throws InterruptedException {
		java.io.OutputStream outputStream;
		try {
			outputStream = clientSocket.getOutputStream();
			InputStream in = clientSocket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			outputStream.write("Welcome, Enter password and userID\n\r".getBytes());
			outputStream.write("Enter quit to terminate\n\r".getBytes());
			
			handleLogin(outputStream, reader);
			
			String[] names= new String[server.getList().size()];
			
			String Check;
			for(int i=0; i<names.length; i++) {
				names[i]= server.getList().get(i).userName;
				if(names[i]!=null && names[i]!=userName)
				outputStream.write((names[i] + " is online \n\r").getBytes());
				System.out.println();
			}
				while((Check = reader.readLine()) != null) {
					String[] input= Check.split(" ", 3);
					
					if ("quit".equalsIgnoreCase(input[0])) {
						outputStream.write("aight peace bitch".getBytes());
						System.out.println(" user has logged off");
						server.removeList(this);
						clientSocket.close();
					}
					
					if("message".equalsIgnoreCase(input[0])) {
						handleMessage(input[1], input[2]);
					}
					
				}
	
		} catch (SocketException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();

		}
	}
	public void handleMessage( String name, String message) throws IOException  {
		try {
		for(int i=0; i<server.getList().size(); i++ ) {
			if(server.getList().get(i).userName.equalsIgnoreCase(name)){
				OutputStream out = server.getList().get(i).clientSocket.getOutputStream();
				out.write((userName +" ====> "+ message +" \n\r").getBytes());
                //out.close();
			}
		}
		
		}catch(SocketException e) {
			e.printStackTrace();
		}
		}
	

	
	private void handleLogin(OutputStream outputStream, BufferedReader reader) throws IOException {
		String line= reader.readLine();
		String[] words = line.split(" ", 2);
		
		// while(words[0]!= null ) {
			   if("quit".equalsIgnoreCase(words[0])) {
				   outputStream.write("aight peace bitch".getBytes());
				   System.out.println("user has logged off");
				   clientSocket.close(); 
				   return;
			   }else if(!words[0].equals("ezmoney")){
					outputStream.write("Wrong password, try again \n\r".getBytes());
					
			   }else if(words[1]==null) {
					outputStream.write("Invalid username, try again \n\r".getBytes());
			   }else {
					outputStream.write(("User "+ words[1]+" has logged on \n\r").getBytes());
					userName = words[1];
			   }
						  
	}

	}
