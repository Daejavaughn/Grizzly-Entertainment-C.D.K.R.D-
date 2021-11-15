package main;
//worked on alongside kevaughn
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ParentWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menu = new JMenuBar();
	private JMenu file = new JMenu("File");
	private JMenuItem exit = new JMenuItem("Exit");
	private JDesktopPane mainWindow = new JDesktopPane();
			
	public ParentWindow()
	{
		super("Grizzly");
		this.setJMenuBar(menu);
		menu.add(file);
		file.setMnemonic('F');
		file.add(exit);
		exit.setActionCommand("Exit");
		exit.addActionListener(this);
		(this.getContentPane()).add(mainWindow);
		
		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		AbstractAction exitAction = new AbstractAction("file") {

			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				 System.out.println("Exiting...");
				
			}
			
		};
		//exitAction.putValue(Action., KeyEvent.VK_S);
		exit.setAction(exitAction);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	//	
		
	}

	public static void main(String[] args) {
		new ParentWindow();
		
	}
		
}
