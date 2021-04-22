package tech.raidtheweb.java.rogue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import asciiPanel.AsciiPanel;
import asciiPanel.AsciiFont;
import tech.raidtheweb.java.rogue.screens.Screen;
import tech.raidtheweb.java.rogue.screens.StartScreen;

public class Main extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7533219487400227477L;
	
	private AsciiPanel terminal;
	private Screen screen;
	private static JTextArea console;
	private static JFrame logOutput;
	
	public Main() {
		URL url = ClassLoader.getSystemResource("tech/raidtheweb/java/rogue/resources/icon.png");
		
		Main.logOutput = new JFrame("Game Logs");
		Main.console = new JTextArea(5, 60);
		Main.console.setEditable(false);
		logOutput.add(new JScrollPane(console));
		logOutput.pack();
		logOutput.setVisible(true);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		logOutput.setIconImage(img);
		this.setIconImage(img);
		
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().setBackground(Color.BLACK);
		terminal = new AsciiPanel(120, 24, AsciiFont.NEXUSSPELUNKER_9x16);
		this.add(terminal);
		this.pack();
		screen = new StartScreen(this);
		this.addKeyListener(this);
		repaint();
	}
	
	public static void log(String text) {
		console.append(text + "\n");
	}
	
	public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        Main app = new Main();
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setTitle("Nexus SpelunkerRL");
        app.setVisible(true);
        app.setResizable(false);
        log("[GUIHandler]: Initialised JFrame");
        logOutput.toFront();
    }
    
    public static void restartApplication() {
    	final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
	    File currentJar = null;
	    try {
	    	currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	
	    if(!currentJar.getName().endsWith(".jar"))
	    	return;
	
	    final ArrayList<String> command = new ArrayList<String>();
	    command.add(javaBin);
	    command.add("-jar");
	    command.add(currentJar.getPath());
	
	    final ProcessBuilder builder = new ProcessBuilder(command);
	    try {
	    	builder.start();
	    } catch (IOException e) {
			e.printStackTrace();
	    }
	    System.exit(0);
    }
	
}
