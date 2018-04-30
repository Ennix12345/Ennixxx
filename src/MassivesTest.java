import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MassivesTest extends JFrame implements Runnable{
    static private Socket connection;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;

    public MassivesTest(String name){
        super(name);
        setSize(300,300);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        final JTextField text = new JTextField(10);
        final JButton b1 = new JButton("Send");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==b1){
                    sendData(text.getText());
                }
            }
        });
        add(text);
        add(b1);
    }

    public static void main(String[] args) {
       new Thread(new MassivesTest("Testing")).start();
       new Thread(new Worker()).start();
    }
    @Override
    public void run() {
        try {
            while (true) {
                connection = new Socket(InetAddress.getByName("127.0.0.1"), 5678);
                output = new ObjectOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());
                JOptionPane.showMessageDialog(null, (String) input.readObject());
            }
        } catch (IOException e) {
        } catch (HeadlessException e) {
        } catch (ClassNotFoundException e) {
        }
    }
      private static void sendData(Object obj){
          try {
              output.flush();
              output.writeObject(obj);
          } catch (IOException e) {

          }
      }
    }


/*Pattern pt = Pattern.compile("(([a-z]){1,}[\\-]{0,1}([a-z]){1,}[\\.]{0,1}([a-z]){1,})+"+"@([a-z]){1,}[\\-]{0,1}([a-z]){1,}[\\.]{0,1}([a-z]){1,}"+"(([\\.]){1}([a-z]){2,4})+");
        Matcher m = pt.matcher("name-email.one@gmail.com.ua");
        boolean ft = m.matches();
        System.out.println(ft);*/



//("\\W+([\\.-]?\\w+)*@\\W+([\\.-]?\\W+)*\\.\\W{2,4}")