
import java.io.*;
import java.net.Socket;
import java.util.List;


public class PeerThread extends Thread{

	private MyLocalPeer myLocalPeer;
	protected Socket socket;

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	private int requestNum;
	private int longestValidBlockChain = myLocalPeer.getBlockchain().getBlockChainSize();

	public PeerThread(Socket socket, MyLocalPeer myLocalPeer) throws IOException {
		this.myLocalPeer = myLocalPeer;
		this.socket = socket;
	}

	public void run(){
		try {
			objectInputStream  = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

			requestNum = objectInputStream.readInt();

			if(requestNum == 1){ // receive block
				myLocalPeer.addBlock((Block) objectInputStream.readObject());
			}
			else if(requestNum == 2){ // want longest valid block chain size
				objectOutputStream.writeInt(3);
				objectOutputStream.writeInt(myLocalPeer.getBlockchain().getBlockChainSize());
			}
			else if(requestNum == 3){ // send longest valid block chain size
				int validBlockChainSizeTemp = objectInputStream.readInt();

				if(validBlockChainSizeTemp > longestValidBlockChain)
					longestValidBlockChain = validBlockChainSizeTemp;

				myLocalPeer.setLongestValidBlockChain(longestValidBlockChain);
			}
			else if(requestNum == 4){ // send block chain request
				objectOutputStream.writeInt(6);
				myLocalPeer.sendBlockChain(objectOutputStream);
			}
			else if(requestNum == 5){ // send IP Addresses
				myLocalPeer.setNetworkPeers((List<String>) objectInputStream.readObject());
			}
			else if(requestNum == 6){ // i receive a BlockChain
				myLocalPeer.setBlockchain((Blockchain) objectInputStream.readObject());
			}
			else if(requestNum == 7){ //i receive transactions
				myLocalPeer.appendTransactions((List<Transaction>) objectInputStream.readObject());
			}


		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
