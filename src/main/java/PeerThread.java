import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class PeerThread extends Thread{

	private Blockchain blockchain;
	
	protected Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;

	
	
	public PeerThread(Socket socket, Blockchain blockchain) throws IOException {
		this.blockchain = blockchain;
		this.socket = socket;

	}

	public void run(){

			try {
				byte[] buffer = null;
				inputStream = this.socket.getInputStream();
				outputStream = this.socket.getOutputStream();
				inputStream.read(buffer);
				Block block = SerializationUtils.deserialize(buffer);
				blockchain.getBlocks().add(block);
				System.out.println("bravo 3lik "+ block.getHash());
			} catch (Exception e) {
				e.printStackTrace();

		}
	}
}
