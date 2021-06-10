import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//Panel that takes the information from the database and the username
public class Initial_Panel extends JPanel {
	private JTextField username;
	private JTextField tx1;
	private JPasswordField tx2;
	private JButton button;
	private ConnectionDB conn;

	public Initial_Panel() {
		setBackground(Color.white);
		JPanel panel1 = new JPanel();
		JPanel gridPanel = new JPanel();

		add(Box.createVerticalGlue());
		JLabel title = new JLabel();
		String sCarpAct = System.getProperty("user.dir");
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miicono = mipantalla
				.getImage(sCarpAct + File.separator + "img" + File.separator + "icon2.png");

		Image dimg = miicono.getScaledInstance(300, 180, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		title.setIcon(imageIcon);
		title.setAlignmentX(CENTER_ALIGNMENT);
		add(title);

		gridPanel.add(new JLabel("Username:", JLabel.RIGHT));
		username = new JTextField("Group5", 10);
		gridPanel.add(username);
		gridPanel.add(new JLabel("UserDB:", JLabel.RIGHT));
		tx1 = new JTextField("root", 10);//default user
		gridPanel.add(tx1);
		gridPanel.add(new JLabel("PassDB:", JLabel.RIGHT));
		tx2 = new JPasswordField("metajets", 10);//default password
		gridPanel.add(tx2);
		gridPanel.setLayout(new GridLayout(3, 2, 5, 5));
		panel1.add(gridPanel);
		panel1.add(Box.createRigidArea(new Dimension(20, 0)));
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		gridPanel.setBackground(Color.white);
		panel1.setBackground(Color.white);
		add(panel1);

		button = new JButton("Play");

		button.setAlignmentX(CENTER_ALIGNMENT);
		add(button);
		add(Box.createVerticalGlue());

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	}

	public JTextField getUsername() {
		return username;
	}

	public void setUsername(JTextField username) {
		this.username = username;
	}

	public JTextField getTx1() {
		return tx1;
	}

	public void setTx1(JTextField tx1) {
		this.tx1 = tx1;
	}

	public JPasswordField getTx2() {
		return tx2;
	}

	public void setTx2(JPasswordField tx2) {
		this.tx2 = tx2;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public ConnectionDB getConn() {
		return conn;
	}

	public void setConn(ConnectionDB conn) {
		this.conn = conn;
	}

}
