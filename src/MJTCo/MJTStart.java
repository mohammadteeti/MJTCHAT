package MJTCo;

import java.awt.GridLayout;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.w3c.dom.css.RGBColor;

public class MJTStart extends JFrame {

    private LogPanel log;
    private UsersPanel users;
    private CommandPanel command;
    private JScrollPane usersScroll;
    private static String myAddress;
    private static JButton refresh ;
    public MJTStart() {

        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        getContentPane().setBackground(Color.black);
        setTitle("MJT Server");
        setSize(new Dimension(800, 600));
        getContentPane().setSize(this.getSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        log = new LogPanel(this.getWidth() - 300, this.getHeight() - 100);
        users = new UsersPanel(300, 500);
        command = new CommandPanel(800, 100);
        /* add log panel to the right */
        log.setVisible(true);
        gc.gridx = 1;
        gc.gridy = 0;
        gc.weightx = (5.0 / 8.0);
        gc.weighty = (5.0 / 6.0);
        gc.gridwidth = 1;
        gc.gridheight = 1;

        gc.fill = gc.BOTH;

        getContentPane().add(log, gc);

        /* create scroll panel and add user panel */
        JPanel usersRootPane = new JPanel(new GridLayout(1, 1));
        usersRootPane.setSize(300, 500);
        usersScroll = new JScrollPane(users);
        usersScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        gc = new GridBagConstraints();
        users.setVisible(true);
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = (3.0 / 8.0);
        gc.weighty = (5.0 / 6.0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.fill = gc.BOTH;

        getContentPane().add(usersScroll, gc);

        /* add Command Panel down the screen */
        gc = new GridBagConstraints();
        command.setVisible(true);
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = (1.0);
        gc.weighty = (1.0 / 6.0);
        gc.gridwidth = 2;
        gc.gridheight = 1;
        gc.fill = gc.BOTH;

        getContentPane().add(command, gc);
        getContentPane().repaint();

        /* Set Refresh Button ,but don't add to scroll panel yet */
            refresh = new JButton("Refresh!");
            refresh.setSize(usersScroll.getWidth(), 80);
            refresh.setForeground(Color.BLUE);
            refresh.setBackground(Color.decode("#00ffee"));

        /* Storing the IP Address of the Server */
        try {
            /*
             * A socket is used to get local ip address
             * getLocalAddress() returns 127.0.0.1 in general
             * but as sockets connects to the gateway it retrieves the
             * DHCP ip address
             */
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10000);
            myAddress = socket.getLocalAddress().getHostAddress();
            socket.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error retriveing Local host ", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) throws Exception {

        MJTStart mjt = new MJTStart();
        mjt.setVisible(true);

        String IpRegex = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
                "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
                "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";

        Pattern p = Pattern.compile(IpRegex);

        System.out.println("This machine IP: " + myAddress);

        Matcher m = p.matcher(myAddress);//retrievs xxx.xxx.xxx 

        String subnet = "";
        if (m.find())
            subnet = m.group(); //must be used inside If statment
        getConnectedDevices(subnet, mjt.getUsers(), mjt.getUsersScroll());

        // ServerSocket ss = new ServerSocket(5050);
        // Socket s = ss.accept();

    }

    public static void getConnectedDevices(String subnet, UsersPanel users, JScrollPane scrollpane)
            throws UnknownHostException, IOException {
        
            
        // int timeout = 1000;
        // for (int i = 1; i <= 138; i++) {
        //     String host = subnet + "." + i;
        //     System.out.println(host);
        //     if (host.equals(myAddress)) // skip my own address 
        //         continue;

        //     if (InetAddress.getByName(host).isReachable(timeout)) {
        //         JLabel lbl = new JLabel("----- User " + host + " ----");
        //         lbl.setBorder(BorderFactory.createEtchedBorder(2, Color.red, Color.PINK));
        //         users.add(lbl);

        //     }

        //     users.revalidate();
        //     scrollpane.revalidate();

        // }
        Thread t1 = new Thread(new ChkClients(myAddress, subnet, 1, 20,users,scrollpane));
        Thread t2 = new Thread(new ChkClients(myAddress, subnet, 21, 40,users,scrollpane));
        Thread t3 = new Thread(new ChkClients(myAddress, subnet, 41, 60,users,scrollpane));
        Thread t4 = new Thread(new ChkClients(myAddress, subnet, 61, 80,users,scrollpane));
        Thread t5 = new Thread(new ChkClients(myAddress, subnet, 81, 100,users,scrollpane));
        Thread t6 = new Thread(new ChkClients(myAddress, subnet, 101, 120,users,scrollpane));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();  
        
        
        users.add(refresh);
        users.revalidate();
        users.repaint();


        

    }

    public LogPanel getLog() {
        return log;
    }

    public void setLog(LogPanel log) {
        this.log = log;
    }

    public UsersPanel getUsers() {
        return users;
    }

    public void setUsers(UsersPanel users) {
        this.users = users;
    }

    public CommandPanel getCommand() {
        return command;
    }

    public void setCommand(CommandPanel command) {
        this.command = command;
    }

    public JScrollPane getUsersScroll() {
        return usersScroll;
    }

    public void setUsersScroll(JScrollPane usersScroll) {
        this.usersScroll = usersScroll;
    }

}

class ChkClients implements Runnable{

     int begin , end;
     String subnet="",myAddress="";
     UsersPanel users;
    JScrollPane scrollpane;     
     public ChkClients(String myAddress ,String subnet ,int beg , int end
     ,UsersPanel users, JScrollPane scrollpane ){
       
        this.begin=beg;
        this.end = end;
        this.subnet=subnet;
        this.myAddress=myAddress;
        this.users=users;
        this.scrollpane=scrollpane;

     }

    @Override
    public void run () {
        try {
        /* code for chekcing connected devices */
        int timeout = 1000;
        for (int i = begin; i <= end; i++) {
            String host = subnet + "." + i;
            System.out.println(host);
            if (host.equals(myAddress)) // skip my own address 
                continue;

            if (InetAddress.getByName(host).isReachable(timeout)) {
                JLabel lbl = new JLabel("----- User " + host + " ----");
                lbl.setBorder(BorderFactory.createEtchedBorder(2, Color.red, Color.PINK));
                users.add(lbl);

            }

            users.revalidate();
            scrollpane.revalidate();

        }
    }
    catch(Exception e ){
        e.printStackTrace();;
    }
}
}
