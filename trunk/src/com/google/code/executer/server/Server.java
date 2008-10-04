/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
				BufferedReader reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
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
