package lt.visma.codefights.sdk.boilerplate.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import lt.visma.codefights.sdk.model.IFighter;
import lt.visma.codefights.sdk.model.Move;

public class ServerMode {

	private BufferedReader inStream = new BufferedReader(new InputStreamReader(System.in));
	private PrintStream outStream = System.out;
	private boolean cancelFlag = false;

	public void run(IFighter fighter) throws IOException {
		Protocol protocol = new Protocol(inStream, outStream);
		protocol.handshake();

		Protocol.ServerResponse resp = new Protocol.ServerResponse();

		while (!cancelFlag) {
			Move move = fighter.makeNextMove(resp.move, resp.score1, resp.score2);
			protocol.sendRequest(move);
			resp = protocol.readResponse();
		}
	}
	
	public void setInputStream(InputStream istream){
		this.inStream=new BufferedReader(new InputStreamReader(istream));
	}
	
	public void setOutputStream(OutputStream ostream){
		this.outStream = new PrintStream(ostream, false);
	}
	
	public void cancel(){
		this.cancelFlag = true;
	}

}
