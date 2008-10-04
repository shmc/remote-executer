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

package com.google.code.executer.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

	private static final int DEFAULT_PORT = 8090;

	public static void main(String[] args) throws IOException {
		String command = args[0];
		String host = args[1];
		int port = DEFAULT_PORT;
		if (args.length > 2) {
			port = Integer.parseInt(args[2]);
		}

		Socket server = new Socket(host, port);
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			try {
				writer.write(command + "\n");
				writer.flush();
			} finally {
				writer.close();
			}
		} finally {
			server.close();
		}

		System.exit(0);
	}
}
