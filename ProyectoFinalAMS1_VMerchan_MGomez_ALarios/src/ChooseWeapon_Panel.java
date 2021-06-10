import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChooseWeapon_Panel extends JPanel {
	private ArrayList<JButton> ButtonArray = new ArrayList<JButton>();
	private ArrayList<Weapon> WeaponContainer = new ArrayList<Weapon>();

	public ChooseWeapon_Panel(Warrior warrior, ConnectionDB conn) {
		JPanel gridPanel = new JPanel();
		JLabel title = new JLabel("Choose " + warrior.getWarrior_name() +"'s weapon");
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		title.setAlignmentX(CENTER_ALIGNMENT);
		ArrayList<Weapon> array = conn.getWeaponContainer(warrior);
		add(title);

		for (Weapon weapon : array) {

			String sCarpAct = System.getProperty("user.dir");
			Toolkit mipantalla = Toolkit.getDefaultToolkit();
			Image miicono = mipantalla
					.getImage(sCarpAct + File.separator + "img" + File.separator + weapon.getWeapon_url());
			Image dimg = miicono.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(dimg);
			JButton boton = new JButton(weapon.getWeapon_name(), imageIcon);
			boton.setToolTipText("<html><body style=\"background-color:white;\"><p color=black>Speed: "
					+ weapon.getPlus_velocity() + " <br>Damage: " + weapon.getPlus_force() + " </p></body></html>");
			boton.setOpaque(true);
			boton.setBackground(Color.white);
			this.ButtonArray.add(boton);
			gridPanel.add(boton);
		}
		int[] a = Grid(array);
		gridPanel.setLayout(new GridLayout(a[0], a[1]));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(gridPanel);

	}

	public ArrayList<JButton> getButtonArray() {
		return ButtonArray;
	}

	public ArrayList<Weapon> getWeaponContainer() {
		return WeaponContainer;
	}
	
	public int[] Grid(ArrayList<Weapon> Weapons) {
		int [] valors = new int[2];
		for(int i = 1; i < Math.sqrt(Weapons.size()) + 1; i++) {
			for(int j = 1; j <= i; j++)
			{
				if((j*i) >= Weapons.size()) {
					valors[0] = i;
					valors[1] = j;
					return valors;
				}
			}
		}
		return valors;
	}
}
