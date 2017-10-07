package de.mannheim.wifo2.fesas.ui;

import java.io.PrintStream;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

public class FesasGUI extends javax.swing.JFrame{

	// just for serialization purposes
	private static final long serialVersionUID = -2617900350041964600L;
	
	private JPanel jRepositoryPanel;
	private final int width = 1200;
	private final int height = 700;
	
	private JTextArea jConsoleTA;
	private JLabel jConsoleLB;
	private JScrollPane jConsoleSP;	


	public FesasGUI(String title){
		initComponents(title);
	}
	
	public void startUI() {
//		intended to be empty - could be used to enable buttons
	}

	private void initComponents(String title) {

		jRepositoryPanel = new javax.swing.JPanel(new SpringLayout());
		
		//console output
		jConsoleTA = new JTextArea();
		TextAreaOutputStream taos = new TextAreaOutputStream( jConsoleTA, 60 );
	    PrintStream ps = new PrintStream( taos );
		
//		DefaultCaret caretConsole = (DefaultCaret)jConsoleTA.getCaret();
//		caretConsole.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//		jConsoleTA.setEnabled(true);
//		jConsoleTA.setEditable(true);
		System.setOut(ps);
//		System.setIn(new InputStream(new JTextAreaOutputStream(jConsoleTA)));
		System.setErr(ps);
		
		jConsoleLB = new JLabel();
		jConsoleLB.setText("Console output:");
		jConsoleLB.setLabelFor(jConsoleTA);
		jConsoleSP = new JScrollPane(jConsoleTA, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
	    // titel of the window and close button
	    setTitle(title);
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      public void windowClosing(java.awt.event.WindowEvent evt) {
	        exitForm(evt);
	      }
	    });
	
	    
	    // set layout
	    GroupLayout layout = new GroupLayout(jRepositoryPanel);
	    jRepositoryPanel.setLayout(layout);
	    
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	    layout.setHorizontalGroup(layout.createSequentialGroup()
	    		   	  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	    		           .addComponent(jConsoleLB)
	    		       )
	    		       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	    		           .addComponent(jConsoleSP)
	    		       )
	    		);
	    layout.setVerticalGroup(
	    		   layout.createSequentialGroup()
	    		       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	    		           .addComponent(jConsoleLB)
	    		           .addComponent(jConsoleSP))
	    		);
	    
	    getContentPane().add(jRepositoryPanel);
	     
	    // refresh for layout and set window sizes and location
	    pack();
	    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    setSize(new java.awt.Dimension(width, height));
	    setLocation((screenSize.width-width)/2,(screenSize.height-548)/2);
		
	}

	private void exitForm(java.awt.event.WindowEvent evt) { 
		System.exit(0);
	}
	
	//inner class for the console output
//	private class JTextAreaOutputStream extends OutputStream {
//
//		JTextArea ta;
//
//		public JTextAreaOutputStream(JTextArea t){
//			super();
//			ta = t;
//		}
//
//		public void write(int i){
//			ta.append(Character.toString((char)i));
//		}
//
//		@SuppressWarnings("unused")
//		public void write(char[] buf, int off, int len) {
//			String s = new String(buf, off, len);
//			ta.append(s);
//		}
//	}
	

}
