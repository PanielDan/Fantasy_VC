package game_logic;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import messages.BetPlacedMessage;
import messages.BuzzMessage;
import messages.ClientQuitGameMessage;
import messages.ClientQuitInitMessage;
import messages.CorrectAnswerMessage;
import messages.FinalAnswerSubmittedMessage;
import messages.IncorrectAnswerMessage;
import messages.JeopardyMessage;
import messages.NoRightAnswerMessage;
import messages.QuestionEnteredMessage;
import messages.RevealFinalQuestionMessage;
import messages.SecondChanceMessage;
import messages.VoteMessage;

public class ServerClientCommunicator extends Thread {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JeopardyServer js;
	private String teamName;
	private User user;

	public ServerClientCommunicator(Socket s, JeopardyServer js, User user) {
		try {
			this.js = js;
			this.user = user;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendMessage(JeopardyMessage message) {
		try {
			oos.writeObject(message);
			oos.flush();
//			oos.reset();
		} catch (IOException ioe) {
			System.out.println("IOException in JeopardyServerThread::sendMessage(): " + ioe.getMessage());
			ioe.printStackTrace();
		}
	}

	public void run() {
		while (true) { 
			try {
				//				JeopardyMessage message = (JeopardyMessage) ois.readObject();
				Object o = ois.readObject();
				if (o != null) { 
					JeopardyMessage message = (JeopardyMessage) o;
					if (message != null) { 
						System.out.println("ServerThread Receiving: " + message.getType() + " | " + message.getMessage());

						if (message.getType() == 1) {
							this.setTeamName(message.getMessage());
							js.sendTeamName(message.getMessage());
						} else if (message.getType() == 2) {
							ClientQuitInitMessage _message = (ClientQuitInitMessage) message;
							js.removePlayerFromLobby(_message.getTeamName(), this);
						} else if (message.getType() == 4) {
							/* Someone exit. */
							ClientQuitGameMessage _message = (ClientQuitGameMessage) message;
							js.sendExitMessage(_message);
						} else if (message.getType() == 199) { 
							js.resetServer();
							js.sendMessageToAllClients(message);
						} else if (message.getType() == 200) {
							/* Entered a question. */
							QuestionEnteredMessage _message = (QuestionEnteredMessage) message;
							js.setCurrentQuestionState(_message.currentQuestionState);
							js.sendMessageToAllClients(message);
						} else if (message.getType() == 210) { 
							/* Correct answer. */
							CorrectAnswerMessage _message = (CorrectAnswerMessage) message;
							System.out.println(_message.getMessage());
							js.sendMessageToAllClients(message);
						} else if (message.getType() == 211) {
							/* Incorrect answer. */
							IncorrectAnswerMessage _message = (IncorrectAnswerMessage) message;
							System.out.println(_message.getMessage());
							js.sendMessageToAllClients(message);
						} else if (message.getType() == 212) { 
							/* Second chance. */
							SecondChanceMessage _message = (SecondChanceMessage) message;
							System.out.println(_message.getMessage());
							js.sendMessageToAllClients(message);
						} else if (message.getType() == 280) { 
							/* Buzzed in.*/
							BuzzMessage _message = (BuzzMessage) message;
							System.out.println(_message.getMessage());
							js.sendMessageToAllClients(message);
						} else if (message.getType() == 290) {
							/* Nobody got it right. */
							/* DEPRECATED? */
							NoRightAnswerMessage _message = (NoRightAnswerMessage) message;
							System.out.println("Answer " + _message.getAnswer());
							System.out.println("Message " + _message.getMessage());
							js.sendMessageToAllClients(_message);
						} else if (message.getType() == 291) {
							/* NoRightAnswerMessage version 2. */
							/* AllWrongMessage. */
							js.sendMessageToAllClients(message);
						} else if (message.getType() == 351) {
							/* Bid placed. */
							BetPlacedMessage _message = (BetPlacedMessage) message;
							System.out.println(_message.getMessage());
							js.sendMessageToAllClients(_message);
						} else if (message.getType() == 352) {
							/* Final Jeopardy answer submitted. */
							FinalAnswerSubmittedMessage _message = (FinalAnswerSubmittedMessage) message;
							System.out.println(_message.getMessage());
							js.sendMessageToAllClients(_message);
						} else if (message.getType() == 380) {
							/* Reveal Final Jeopardy question to all players. **/
							RevealFinalQuestionMessage _message = (RevealFinalQuestionMessage) message;
							System.out.println(_message.getMessage());
							js.sendMessageToAllClients(_message);
						} else if (message.getType() == 450) {
							/* Someone voted. */
							/* Named after EE 450. */
							VoteMessage _message = (VoteMessage) message;
							System.out.println(_message.getMessage());
							js.sendMessageToAllClients(_message);
						} else if (message.getType() == 455) {
							/* Update. */
							System.out.println(message.getMessage());
							js.sendMessageToAllClients(message);
						} else {
							System.out.println("Slow down there cowboy, that code doesn't exist.");
						}
					}
				}
			} catch (EOFException eofe) { 
				System.out.println("HELLO THIS IS EOFEXCEPTION: " + eofe.getLocalizedMessage());
				return;
			} catch (ClassNotFoundException cnfe) {
				System.out.println("ClassNotFoundException in JeopardyServerThread::run(): " + cnfe.getLocalizedMessage());
				cnfe.printStackTrace();
			} catch (IOException ioe) {
				System.out.println("IOException in JeopardyServerThread::run(): " + ioe.getLocalizedMessage());
				ioe.printStackTrace();
				return;
			}
		}
	}

	public void kill() {
		try { 
			if (ois != null) { ois.close(); }
			if (oos != null) { oos.close(); }
		} catch (IOException ioe) {
			return;
		} 
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String name) {
		this.teamName = name;
	}
}

