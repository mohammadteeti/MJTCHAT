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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MJTStart extends JFrame {

    private LogPanel log;
    private UsersPanel users;
    private CommandPanel command;
    private JScrollPane usersScroll;
    private InetAddress myAddress;

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
            myAddress = socket.getLocalAddress();
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

        System.out.println("This machine IP: " + mjt.getMyAddress().getHostAddress());

        Matcher m = p.matcher(mjt.getMyAddress().getHostAddress());

        String subnet = "";
        if (m.find())
            subnet = m.group(); //must be used inside If statment
        getConnectedDevices(subnet, mjt.getUsers(), mjt.getUsersScroll());

        // ServerSocket ss = new ServerSocket(5050);
        // Socket s = ss.accept();

    }

    public static void getConnectedDevices(String subnet, UsersPanel users, JScrollPane scrollpane)
            throws UnknownHostException, IOException {
        /* code for chekcing connected devices */
        int timeout = 1000;
        for (int i = 1; i <= 138; i++) {
            String host = subnet + "." + i;
            System.out.println(host);

            if (InetAddress.getByName(host).isReachable(timeout)) {
                JLabel lbl = new JLabel("----- User " + host + " ----");
                lbl.setBorder(BorderFactory.createEtchedBorder(2, Color.red, Color.PINK));
                users.add(lbl);

            }

            users.revalidate();
            scrollpane.revalidate();

        }

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

    public InetAddress getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(InetAddress myAddress) {
        this.myAddress = myAddress;
    }
}
