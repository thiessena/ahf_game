package mainPackage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class GUIServer extends JFrame {

	private JPanel contentPane;
	private JTextField fieldPort;
	private JTextField fieldHost;
	private JTextField fieldIP;
	private JTextField fieldPortConnect;
	private JTextField fieldMessage;
	private JTextField fieldEmpfang;
	private JButton btnConnect;
	private JButton btnSenden;

	private EchoServer mEchoServer;
	private EchoClient mEchoClient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new GUIServer();
	}

	/**
	 * Create the frame.
	 */
	public GUIServer() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 500, 218);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (screenSize.getWidth() / 2 - this.getWidth() / 2),
				(int) (screenSize.getHeight() * 0.2 - this.getHeight() / 2));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel serverPane = new JPanel();
		serverPane.setBorder(
				new TitledBorder(null, "Serververbindung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(serverPane, BorderLayout.NORTH);
		serverPane.setLayout(new BoxLayout(serverPane, BoxLayout.X_AXIS));

		JButton btnStartServer = new JButton("Server starten");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startServer(e);
			}
		});
		serverPane.add(btnStartServer);

		Component horizontalStrut = Box.createHorizontalStrut(40);
		serverPane.add(horizontalStrut);

		JLabel lblPort = new JLabel("Port:");
		serverPane.add(lblPort);

		Component horizontalStrut_2 = Box.createHorizontalStrut(10);
		serverPane.add(horizontalStrut_2);

		fieldPort = new JTextField();
		fieldPort.setText("1000");
		serverPane.add(fieldPort);
		fieldPort.setColumns(10);

		Component horizontalStrut_1 = Box.createHorizontalStrut(40);
		serverPane.add(horizontalStrut_1);

		JLabel lblHost = new JLabel("Host:");
		serverPane.add(lblHost);

		Component horizontalStrut_3 = Box.createHorizontalStrut(10);
		serverPane.add(horizontalStrut_3);

		fieldHost = new JTextField();
		try {
			fieldHost.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			// System.err.println("Host nicht gefunden!");
			// System.exit(1);
			e.printStackTrace();
		}
		serverPane.add(fieldHost);
		fieldHost.setColumns(10);

		JPanel clientPane = new JPanel();
		clientPane.setBorder(
				new TitledBorder(null, "Clientverbindung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(clientPane, BorderLayout.CENTER);
		clientPane.setLayout(new BorderLayout(0, 0));

		JPanel connectionPane = new JPanel();
		clientPane.add(connectionPane);
		connectionPane.setLayout(new BoxLayout(connectionPane, BoxLayout.X_AXIS));

		btnConnect = new JButton("mit Server verbinden");
		btnConnect.setEnabled(false);

		// if(mState == serverState.disconnected)
		// btnConnect.setEnabled(false);
		// else
		// btnConnect.setEnabled(true);

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectServer(e);
			}
		});
		connectionPane.add(btnConnect);
		btnConnect.setVerticalAlignment(SwingConstants.TOP);

		Component horizontalStrut_4 = Box.createHorizontalStrut(40);
		connectionPane.add(horizontalStrut_4);

		JLabel lblIP = new JLabel("IP:");
		connectionPane.add(lblIP);

		Component horizontalStrut_6 = Box.createHorizontalStrut(10);
		connectionPane.add(horizontalStrut_6);

		fieldIP = new JTextField();
		fieldIP.setText("127.0.0.1");
		connectionPane.add(fieldIP);
		fieldIP.setColumns(10);

		Component horizontalStrut_7 = Box.createHorizontalStrut(40);
		connectionPane.add(horizontalStrut_7);

		JLabel lblPortConnect = new JLabel("Port:");
		connectionPane.add(lblPortConnect);

		Component horizontalStrut_5 = Box.createHorizontalStrut(10);
		connectionPane.add(horizontalStrut_5);

		fieldPortConnect = new JTextField();
		fieldPortConnect.setText("1000");
		connectionPane.add(fieldPortConnect);
		fieldPortConnect.setColumns(10);

		JPanel messagePane = new JPanel();
		messagePane.setBorder(new EmptyBorder(20, 0, 0, 0));
		clientPane.add(messagePane, BorderLayout.SOUTH);
		messagePane.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		messagePane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout(0, 0));

		btnSenden = new JButton("Senden");
		btnSenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendToClient(e);
			}
		});
		panel_1.add(btnSenden, BorderLayout.NORTH);

		JLabel lblEmpfang = new JLabel("Empfang:");
		lblEmpfang.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblEmpfang, BorderLayout.SOUTH);

		Component verticalStrut = Box.createVerticalStrut(10);
		panel_1.add(verticalStrut, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 20, 0, 0));
		messagePane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		fieldMessage = new JTextField();
		panel.add(fieldMessage, BorderLayout.NORTH);
		fieldMessage.setColumns(10);

		fieldEmpfang = new JTextField();
		panel.add(fieldEmpfang, BorderLayout.SOUTH);
		fieldEmpfang.setColumns(10);

		setVisible(true);
	}

	public void startServer(ActionEvent e) {
		mEchoServer = new EchoServer(Integer.parseInt(fieldPort.getText()));
		connectServer(e);
	}

	public void connectServer(ActionEvent e) {
		mEchoClient = new EchoClient(fieldIP.getText(), Integer.parseInt(fieldPortConnect.getText()), fieldEmpfang);
		btnConnect.setEnabled(false);
		btnSenden.setEnabled(true);
	}

	public void sendToClient(ActionEvent e) {
		mEchoClient.send(fieldMessage.getText());
	}

}
