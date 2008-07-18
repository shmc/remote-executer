package com.google.code.executer.server;

import java.io.IOException;

public class Command {

	private String name;

	public Command(String name) {
		if (name.contains("..")) {
			throw new IllegalArgumentException(
					"Only scripts in the current directory can be executed");
		}
		this.name = name;
	}

	public void execute() {
		new Thread(new Runnable() {

			public void run() {
				try {
					Process p = Runtime.getRuntime().exec(Command.this.name);
					p.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
