package me.pray.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import me.pray.Main;

@SuppressWarnings("serial")
public class GUI extends JFrame 
{
    private final ConsolePanel console;
    private final Main main;
    
    public GUI(Main main) 
    {
        super();
        this.main = main;
        console = new ConsolePanel();
    }
    
    public void init()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Valorant Rank Guesser");
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Console", console);
        getContentPane().add(tabs);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowListener() 
        {
            @Override public void windowOpened(WindowEvent e) { /* unused */ }
            @Override public void windowClosing(WindowEvent e) 
            {
                try
                {
                    main.shutdown();
                }
                catch(Exception ex)
                {
                    System.exit(0);
                }
            }
            @Override public void windowClosed(WindowEvent e) { /* unused */ }
            @Override public void windowIconified(WindowEvent e) { /* unused */ }
            @Override public void windowDeiconified(WindowEvent e) { /* unused */ }
            @Override public void windowActivated(WindowEvent e) { /* unused */ }
            @Override public void windowDeactivated(WindowEvent e) { /* unused */ }
        });
    }
}