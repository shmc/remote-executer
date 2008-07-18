package pt.discoeuropa.remoteExecuter.server;

import java.io.IOException;

public class Command {

    private String name;

    public Command(String name) {
	if (name.contains("..")) {
	    throw new IllegalArgumentException("Apenas se podem executar comandos na directoria de trabalho");
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
