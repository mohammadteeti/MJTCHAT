package MJTCo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class CommandPanel extends JPanel {

    public CommandPanel(int w , int h){
        setLayout(new GridBagLayout());
        setSize(w,h);
        setBorder(BorderFactory.createLineBorder(Color.red,2));
        setVisible(true);
        GridBagConstraints gc = new GridBagConstraints();
        JButton cmd = new JButton("Send");
        JTextField txt = new JTextField(100);
        txt.setVisible(true);
        txt.setFont(new Font("Serif",1,16));
        gc.gridx=0;
        gc.gridy=0;
        gc.weightx=1.0/3.0;
        gc.weighty=1.0;
        gc.gridwidth=1;
        gc.gridheight=2;
        gc.fill=GridBagConstraints.BOTH;
        gc.anchor=gc.NORTH;
        add(cmd,gc);

        gc=new GridBagConstraints();
        gc.gridx=1;
        gc.gridy=0;
        gc.weightx=2.0/3.0;
        gc.weighty=1.0;
        gc.gridwidth=2;
        gc.gridheight=1;
        gc.anchor=gc.NORTH;
        gc.fill=GridBagConstraints.BOTH;
        add(txt,gc);

        repaint();
    }
    
}
