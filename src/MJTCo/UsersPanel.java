package MJTCo;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
public class UsersPanel extends JPanel{ 
  
    public UsersPanel( int w ,int h){

        
        GridLayout gl = new GridLayout(1000,1); //can contain up to 1000 users
        gl.setVgap(5);
        setSize(w,h);
        setBorder(BorderFactory.createLineBorder(Color.CYAN,2));
        setLayout(gl);
        
        setVisible(true);
      
        
        // /* Test adding users */
        // for (int i = 0 ;i<3;i++){
        //     JLabel lbl = new JLabel(i+"----- User"+i+"----");
        //     lbl.setBorder(BorderFactory.createEtchedBorder(2,Color.red,Color.PINK));
        //     add(lbl);
        // }
        // repaint();

    }

   
    
}
