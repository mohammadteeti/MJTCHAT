package MJTCo;
 import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogPanel extends JPanel {


        public LogPanel(int w , int h){
            setSize(w,h);
            setLayout(new GridBagLayout());
            JTextArea txtLog = new JTextArea(5,2);
            txtLog.setWrapStyleWord(true);
            txtLog.setLineWrap(true);
            txtLog.setVisible(true);
            txtLog.setFont(new Font("Serif",1,11));
            setBorder(BorderFactory.createLineBorder(Color.black,2));
            txtLog.setBorder(BorderFactory.createEtchedBorder(Color.black,Color.gray));
            txtLog.repaint();
            JScrollPane scroll = new JScrollPane(txtLog);
            scroll.setSize(this.getSize());
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
            
            GridBagConstraints gc = new GridBagConstraints();
            gc.gridwidth=1;
            gc.gridheight=1;
            gc.weightx=1;
            gc.weighty=1;
            gc.gridx=0;
            gc.gridy=0;

            gc.fill=gc.BOTH;

            add(scroll,gc);
      
            repaint();
            
        }
    
}
