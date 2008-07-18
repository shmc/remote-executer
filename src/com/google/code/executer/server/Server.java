package com.google.code.executer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static final int DEFAULT_PORT = 8090;

	private static final class Worker implements Runnable {
		private Socket client;

		private Worker(Socket client) {
			this.client = client;
		}

		public void run() {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(this.client.getInputStream()));
				try {
					Command command = new Command(reader.readLine());
					command.execute();
				} finally {
					reader.close();
				}
				this.client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(final String[] args) throws IOException {
		int port = DEFAULT_PORT;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		try {
			ServerSocket socket = new ServerSocket(port);
			try {
				while (true) {
					Socket client = socket.accept();
					new Thread(new Worker(client)).start();
				}
			} finally {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
