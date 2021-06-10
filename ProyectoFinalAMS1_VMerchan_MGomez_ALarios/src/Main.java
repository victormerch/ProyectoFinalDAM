import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main extends JFrame {
	private static Scanner input = new Scanner(System.in);
	// Redimension panel4
	private boolean redi4 = false;
	// Containers
	private WarriorContainer warriorContainer;
	// Boolean for fight
	private static boolean inFight = false;
	private static boolean fightBool = true;
	private static boolean fightfinal = false;
	// Panels
	private Initial_Panel panel1;
	private ChooseWarrior_Panel panel2;
	private ChooseWeapon_Panel panel3;
	private Battle_Panel panel4;

	// Players
	private static Warrior warriorPlayer;
	private static Weapon weaponPlayer;
	private static Warrior warriorCPU;
	private static Weapon weaponCPU;

	// ConnexionDB and username
	private ConnectionDB conn;

	private static boolean cpu_player_bool;

	private int contRounds = 0;
	private int contTourn = 0;

	private Player player1 = null, player2 = null;

	// TotalPoints
	private int totalPoints = 0;
	private boolean newRecord = false;

	// ==Class instantiation
	public static void main(String[] args) {
		new Main();

	}

	public Main() {
		// We load the initial Frame
		this.setTitle("Welcome");
		String sCarpAct = System.getProperty("user.dir");
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miicono = mipantalla.getImage(sCarpAct + File.separator + "img" + File.separator + "iconJava.jpg");
		setIconImage(miicono);
		setSize(400, 450);
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// First Panel
		panel1 = new Initial_Panel();
		JButton boton1 = panel1.getButton();

		boton1.addActionListener(new activeBotons());
		add(panel1);

		//

		setVisible(true);
	}

	// Battle Algorithm
	public void algoritmBattle(Player user, Player bot) {
		if (fightBool) {
			inFight = true;

			if (user.getVel() > bot.getVel()) {
				player1 = user;
				player2 = bot;
			}

			else if (bot.getVel() > user.getVel()) {
				player1 = bot;
				player2 = user;

			}

			else if (bot.getVel() == user.getVel()) {
				if (user.getAgil() > bot.getAgil()) {
					player1 = user;
					player2 = bot;

				}

				else if (bot.getAgil() > user.getAgil()) {
					player1 = bot;
					player2 = user;

				}

				else if (bot.getAgil() == user.getAgil()) {
					int randomAt = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
					if (randomAt == 1) {
						player1 = user;
						player2 = bot;
					}
					if (randomAt == 2) {
						player1 = bot;
						player2 = user;
					}
				}
			}
			panel4.getTpoint().setText(Integer.toString(totalPoints));
			System.out.println("Beggins: " + player1.getName());
			fightBool = false;
		}
		// TURNS
		if (player1.getLife() > 0 && player2.getLife() > 0) {
			contRounds++;
			System.out.println("~~~~~~ Turn " + contRounds + " ~~~~~~");

			if (player1.getUsername() == "CPU") {
				int atackPlayer1 = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1);
				int dodgePlayer2 = (int) Math.floor(Math.random() * (50 - 1 + 1) + 1);
				System.out.println("--------CPU atack--------");
				if (player1.getAgil() * 10 > atackPlayer1) {
					if (player2.getAgil() < dodgePlayer2) {
						player2.setLife(player2.getLife() - player1.getAtack());
						System.out.println("Successful attack: -" + player1.getAtack());
						System.out
								.println(user.Username + ": HP of " + player2.getName() + " has " + player2.getLife());
						// CPU life color
						panel4.getPanelPlayer().setVida(player2.getLife());
						if (player2.getLife() <= (player2.getTotalHP() * 0.5)
								&& player2.getLife() > (player2.getTotalHP() * 0.25)) {
							panel4.getPanelPlayer().setColor(Color.yellow);
						}
						if (player2.getLife() <= (player2.getTotalHP() * 0.25)) {
							panel4.getPanelPlayer().setColor(Color.red);
						}
					} else {
						System.out.println(player2.getUsername() + " dodged the attack!");
					}
				} else {
					System.out.println("Failed attack!");
				}
				if (player2.getLife() > 0) {
					System.out.println("--------" + player2.getUsername() + " atack--------");
					int atackPlayer2 = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1);
					int dodgePlayer1 = (int) Math.floor(Math.random() * (50 - 1 + 1) + 1);
					if (player2.getAgil() * 10 > atackPlayer2) {
						if (player1.getAgil() < dodgePlayer1) {

							player1.setLife(player1.getLife() - player2.getAtack());
							System.out.println("Successful attack: -" + player2.getAtack());
							System.out.println(
									bot.Username + ": HP of " + player1.getName() + " has " + player1.getLife());
							// Player life color
							panel4.getPanelCPU().setVida(player1.getLife());
							if (player1.getLife() <= (player1.getTotalHP() * 0.50)
									&& player1.getLife() > (player1.getTotalHP() * 0.25)) {
								panel4.getPanelCPU().setColor(Color.yellow);
							}
							if (player1.getLife() <= (player1.getTotalHP() * 0.25)) {
								panel4.getPanelCPU().setColor(Color.red);
							}
						} else {
							System.out.println(player1.getUsername() + " dodged the attack!");
						}
					} else {
						System.out.println("Failed attack!");
					}
				}

				if (player2.getLife() < 1) {
					System.out.println(player2.getName() + " has been defeated.");
					panel4.getTerminal().setBackground(Color.RED);
					fightfinal = true;
				}
				if (player1.getLife() < 1) {
					System.out.println(player1.getName() + " has been defeated.");
					panel4.getTerminal().setBackground(Color.GREEN);
					fightfinal = true;
				}
			} else {
				int atackPlayer1 = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1);
				int dodgePlayer2 = (int) Math.floor(Math.random() * (50 - 1 + 1) + 1);
				System.out.println("--------" + player1.getUsername() + " atack--------");
				if (player1.getAgil() * 10 > atackPlayer1) {
					if (player2.getAgil() < dodgePlayer2) {
						player2.setLife(player2.getLife() - player1.getAtack());
						System.out.println("Successful attack: -" + player1.getAtack());
						System.out.println(bot.Username + ": HP of " + player2.getName() + " has " + player2.getLife());
						// CPU life color
						panel4.getPanelCPU().setVida(player2.getLife());
						if (player2.getLife() <= (player2.getTotalHP() * 0.5)
								&& player2.getLife() > (player2.getTotalHP() * 0.25)) {
							panel4.getPanelCPU().setColor(Color.yellow);
						}
						if (player2.getLife() <= (player2.getTotalHP() * 0.25)) {
							panel4.getPanelCPU().setColor(Color.red);
						}
					} else {
						System.out.println(player2.getUsername() + " dodged the attack!");
					}
				} else {
					System.out.println("Failed attack!");
				}
				if (player2.getLife() > 0) {
					int atackPlayer2 = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1);
					int dodgePlayer1 = (int) Math.floor(Math.random() * (50 - 1 + 1) + 1);
					System.out.println("--------CPU atack--------");
					if (player2.getAgil() * 10 > atackPlayer2) {
						if (player1.getAgil() < dodgePlayer1) {
							player1.setLife(player1.getLife() - player2.getAtack());
							//
							System.out.println("Successful attack: -" + player2.getAtack());
							System.out.println(
									user.Username + ": HP of " + player1.getName() + " has " + player1.getLife());

							// Player life color
							panel4.getPanelPlayer().setVida(player1.getLife());
							if (player1.getLife() <= (player1.getTotalHP() * 0.50)
									&& player1.getLife() > (player1.getTotalHP() * 0.25)) {
								panel4.getPanelPlayer().setColor(Color.yellow);
							}
							if (player1.getLife() <= (player1.getTotalHP() * 0.25)) {
								panel4.getPanelPlayer().setColor(Color.red);
							}
						} else {
							System.out.println(player1.getUsername() + " dodged the attack!");
						}
					} else {
						System.out.println("Failed attack!");
					}
				}
				if (player2.getLife() < 1) {
					System.out.println(player2.getName() + " has been defeated.");
					panel4.getTerminal().setBackground(Color.GREEN);
					fightfinal = true;
				}
				if (player1.getLife() < 1) {
					System.out.println(player1.getName() + " has been defeated.");
					panel4.getTerminal().setBackground(Color.RED);
					fightfinal = true;
				}

			}
		}
		// END THE GAME
		if (fightfinal) {
			// ===When a game wins, it does the following
			// When you lose:
			if (player1.getUsername() == "CPU") {
				if (player1.getLife() > player2.getLife()) {
					if (contTourn == 0) {
						ConnectionDB.insertPlayer(user.getUsername());
						contTourn++;
					}
					ConnectionDB.insertBattle(warriorPlayer.getWarrior_name(), weaponPlayer.getWeapon_id(),
							warriorCPU.getWarrior_name(), weaponCPU.getWeapon_id(), player1.getLife(),
							player2.getLife());
					if (totalPoints > 0) {
						ConnectionDB.insertRanking(player2.getName(), totalPoints);
					}
					int confirmado = JOptionPane
							.showConfirmDialog(
									null, "GAMEOVER" + "\nYou've got: " + totalPoints + " points\nDo you want to play with a different character again?"
											+ "You've got: " + totalPoints + " points",
									"Round result", JOptionPane.YES_NO_OPTION);
					if (JOptionPane.OK_OPTION == confirmado) {

						remove(panel4);
						setTitle("Choose Warrior");
						setSize(900, 600);
						setLocationRelativeTo(null);
						setDefaultCloseOperation(EXIT_ON_CLOSE);
						panel2 = new ChooseWarrior_Panel(warriorContainer);
						for (JButton boton : panel2.getButtonArray()) {
							boton.addActionListener(new activeBotons());
						}
						add(panel2);
						newRecord = false;
					} else {
						new Tabla(conn);
						dispose();
					}
				}
				// When you win:
				else {
					totalPoints = totalPoints + player2.getLife();
					panel4.getTpoint().setText(Integer.toString(totalPoints));
					if (contTourn == 0) {
						ConnectionDB.insertPlayer(user.getUsername());
						contTourn++;
					}
					ConnectionDB.insertBattle(warriorPlayer.getWarrior_name(), weaponPlayer.getWeapon_id(),
							warriorCPU.getWarrior_name(), weaponCPU.getWeapon_id(), player1.getLife(),
							player2.getLife());
					if (newRecord == false) {
						if (totalPoints > Integer.parseInt(panel4.getRpoint().getText())) {
							panel4.getRpoint().setText(Integer.toString(totalPoints));
							JOptionPane.showMessageDialog(null, "YOU HAVE REACHED A NEW RECORD", "CONGRATULATIONS",
									JOptionPane.DEFAULT_OPTION);
							newRecord = true;
						}

					}
					int confirmado = JOptionPane.showConfirmDialog(null,
							"You have won.\nDo you want to play another round?", "Round result",
							JOptionPane.YES_NO_OPTION);
					if (JOptionPane.OK_OPTION == confirmado) {
						contTourn = contTourn + 1;
						warriorContainer = conn.getWarriorContainer();
						Main.warriorCPU = warriorContainer.getRandomWarrior();
						ArrayList<Weapon> weapons2 = conn.getWeaponContainer(warriorCPU);
						Main.weaponCPU = weapons2.get((int) Math.floor(Math.random() * (weapons2.size() - 2 + 1) + 1));
						Main.cpu_player_bool = true;

						choosePanel4();
					}

					else {
						if (totalPoints > 0) {
							ConnectionDB.insertRanking(player2.getName(), totalPoints);
						}
						new Tabla(conn);
						dispose();
					}

				}
			} else {
				if (player2.getLife() > player1.getLife()) {
					if (contTourn == 0) {
						ConnectionDB.insertPlayer(user.getUsername());
						contTourn++;
					}

					ConnectionDB.insertBattle(warriorPlayer.getWarrior_name(), weaponPlayer.getWeapon_id(),
							warriorCPU.getWarrior_name(), weaponCPU.getWeapon_id(), player2.getLife(),
							player1.getLife());

					if (totalPoints > 0) {
						ConnectionDB.insertRanking(player1.getName(), totalPoints);
					}
					int confirmado = JOptionPane
							.showConfirmDialog(
									null, "GAMEOVER" + "You've got: " + totalPoints + " points\nDo you want to play with a different character again?"
											+ "You've got: " + totalPoints + " points",
									"Round result", JOptionPane.YES_NO_OPTION);
					if (JOptionPane.OK_OPTION == confirmado) {
						remove(panel4);
						setTitle("Choose Warrior");
						setSize(900, 601);
						setLocationRelativeTo(null);
						setDefaultCloseOperation(EXIT_ON_CLOSE);
						panel2 = new ChooseWarrior_Panel(warriorContainer);
						for (JButton boton : panel2.getButtonArray()) {
							boton.addActionListener(new activeBotons());
						}
						add(panel2);
						newRecord = false;
					} else {
						if (totalPoints > 0) {
							ConnectionDB.insertRanking(player1.getName(), totalPoints);
						}
						new Tabla(conn);
						dispose();
					}

				}
				// When you win:
				else {
					totalPoints = totalPoints + player1.getLife();
					panel4.getTpoint().setText(Integer.toString(totalPoints));
					if (contTourn == 0) {
						ConnectionDB.insertPlayer(user.getUsername());
						contTourn++;
					}
					ConnectionDB.insertBattle(warriorPlayer.getWarrior_name(), weaponPlayer.getWeapon_id(),
							warriorCPU.getWarrior_name(), weaponCPU.getWeapon_id(), player2.getLife(),
							player1.getLife());
					if (newRecord == false) {
						if (totalPoints > Integer.parseInt(panel4.getRpoint().getText())) {
							panel4.getRpoint().setText(Integer.toString(totalPoints));
							JOptionPane.showMessageDialog(null, "YOU HAVE REACHED A NEW RECORD", "CONGRATULATIONS",
									JOptionPane.DEFAULT_OPTION);
							newRecord = true;
						}

					}
					int confirmado = JOptionPane.showConfirmDialog(null,
							"You have won.\nDo you want to play another round?", "Round result",
							JOptionPane.YES_NO_OPTION);
					if (JOptionPane.OK_OPTION == confirmado) {
						contTourn = contTourn + 1;
						warriorContainer = conn.getWarriorContainer();
						Main.warriorCPU = warriorContainer.getRandomWarrior();
						ArrayList<Weapon> weapons2 = conn.getWeaponContainer(warriorCPU);
						Main.weaponCPU = weapons2.get((int) Math.floor(Math.random() * (weapons2.size() - 2 + 1) + 1));
						Main.cpu_player_bool = true;

						choosePanel4();
					}

					else {
						if (totalPoints > 0) {
							ConnectionDB.insertRanking(player1.getName(), totalPoints);
						}
						new Tabla(conn);
						dispose();
					}

				}

			}
			contRounds = 0;
			fightBool = true;
			fightfinal = false;
			inFight = false;
		}

	}

	public void choosePanel4() {

		remove(panel4);

		setTitle("Battle");
		if (redi4) {
			setSize(950, 801);
			redi4 = false;
		} else {
			setSize(951, 802);
			redi4 = true;
		}

		setLocationRelativeTo(null);
		panel4 = new Battle_Panel(panel1.getUsername().getText(), Main.warriorPlayer, Main.weaponPlayer,
				Main.warriorCPU, Main.weaponCPU, totalPoints);
		JButton JCWarrior = panel4.getJCWarrior();
		JButton JCWeapon = panel4.getJCWeapon();
		JButton JRanking = panel4.getJRanking();
		JButton botonFight = panel4.getBotonFight();
		JButton botonClearConsole = panel4.getBotonClearConsole();

		JCWarrior.addActionListener(new activeBotons());
		JCWeapon.addActionListener(new activeBotons());
		JRanking.addActionListener(new activeBotons());
		botonFight.addActionListener(new activeBotons());
		botonClearConsole.addActionListener(new activeBotons());
		panel4.getTpoint().setText(Integer.toString(totalPoints));
		add(panel4);
	}

	// Events
	class activeBotons implements ActionListener {
		private boolean bool = true;

		public void actionPerformed(ActionEvent e) {
			// Choose characters

			if ((e.getActionCommand().equals("Play") || e.getActionCommand().equals("Choose Warrior"))
					&& !Main.inFight) {
				if (totalPoints > 0) {
					if (player1.getUsername() == "CPU") {
						ConnectionDB.insertRanking(player2.getName(), totalPoints);
					} else {
						ConnectionDB.insertRanking(player1.getName(), totalPoints);
					}
				}
				conn = new ConnectionDB();
				bool = conn.conection(panel1.getTx1().getText(), panel1.getTx2().getText());
				if (panel1.getUsername().getText().length() > 15 || panel1.getUsername().getText().length() < 1) {
					JOptionPane.showMessageDialog(null, "Invalid username (Min: 1, Max: 15)", "Error",
							JOptionPane.WARNING_MESSAGE);
					bool = false;
				}
				if (bool) {

					// Take the second warrior for CPU one time
					if (!Main.cpu_player_bool) {

						warriorContainer = conn.getWarriorContainer();
						Main.warriorCPU = warriorContainer.getRandomWarrior();

						ArrayList<Weapon> weapons2 = conn.getWeaponContainer(warriorCPU);
						Main.weaponCPU = weapons2.get((int) Math.floor(Math.random() * (weapons2.size() - 2 + 1) + 1));
						Main.cpu_player_bool = true;

					}

					// ============
					if (panel4 != null) {
						remove(panel4);
					} else if (panel1 != null) {
						remove(panel1);
					}
					totalPoints = 0;
					setTitle("Choose Warrior");
					setSize(900, 599);
					setLocationRelativeTo(null);
					setDefaultCloseOperation(EXIT_ON_CLOSE);
					panel2 = new ChooseWarrior_Panel(warriorContainer);
					for (JButton boton : panel2.getButtonArray()) {
						boton.addActionListener(new activeBotons());
					}
					add(panel2);

				}

			}
			// Take Weapon if followed by panel 4
			if (e.getActionCommand().contentEquals("Choose Weapon") && !Main.inFight) {

				if (panel4 != null) {
					remove(panel4);
				}
				setTitle("Choose Weapon");
				setSize(900, 650);
				setLocationRelativeTo(null);
				createPanel3();

			}
			if (inFight) {
			}
			// Take weapon if followed by choose warrior
			if (bool) {
				for (Warrior warrior : warriorContainer.getWarriors()) {

					if (warrior.getWarrior_name().equals(e.getActionCommand())) {

						if (panel2 != null) {

							Main.warriorPlayer = warrior;

							remove(panel2);
						}

						setTitle("Choose Weapon");
						setSize(900, 649);
						setLocationRelativeTo(null);
						createPanel3();
						break;
					}
				}
			}

			// Fight
			if (e.getActionCommand().contentEquals("Fight")) {
				Player user = new Player(panel1.getUsername().getText(), warriorPlayer, weaponPlayer);
				Player bot = new Player("CPU", warriorCPU, weaponCPU);
				setBackground(new java.awt.Color(0, 152, 70));
				algoritmBattle(user, bot);
			}
			// Clear Console
			if (e.getActionCommand().contentEquals("Clear Console")) {

				panel4.getTerminal().setText("");

			}
			// Ranking
			if (e.getActionCommand().equals("Ranking")) {
				new Tabla(conn);
			}
			// If you change weapon or warrior in the middle of fighting
			if ((e.getActionCommand().equals("Choose Warrior") || e.getActionCommand().contentEquals("Choose Weapon"))
					&& Main.inFight) {
				if (e.getActionCommand().equals("Choose Warrior")) {
					JOptionPane.showMessageDialog(null, "You cannot pick a warrior if you are fighting", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
				if (e.getActionCommand().equals("Choose Weapon")) {
					JOptionPane.showMessageDialog(null, "You cannot pick a weapon if you are fighting", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}

		// It works for when you go to put the panel3
		public void createPanel3() {
			panel3 = new ChooseWeapon_Panel(Main.warriorPlayer, conn);
			for (JButton boton : panel3.getButtonArray()) {

				boton.addActionListener(new activeBotons() {

					public void actionPerformed(ActionEvent e) {
						for (Weapon weapon : conn.getWeaponContainer(Main.warriorPlayer)) {
							if (weapon.getWeapon_name().equals(e.getActionCommand())) {
								Main.weaponPlayer = weapon;
							}
						}

						// Battle panel

						remove(panel3);

						setTitle("Battle");
						if (redi4) {
							setSize(950, 801);
							redi4 = false;
						} else {
							setSize(951, 802);
							redi4 = true;
						}

						setLocationRelativeTo(null);
						panel4 = new Battle_Panel(panel1.getUsername().getText(), Main.warriorPlayer, Main.weaponPlayer,
								Main.warriorCPU, Main.weaponCPU, totalPoints);
						JButton JCWarrior = panel4.getJCWarrior();
						JButton JCWeapon = panel4.getJCWeapon();
						JButton JRanking = panel4.getJRanking();
						JButton botonFight = panel4.getBotonFight();
						JButton botonClearConsole = panel4.getBotonClearConsole();
						totalPoints = 0;
						JCWarrior.addActionListener(new activeBotons());
						JCWeapon.addActionListener(new activeBotons());
						JRanking.addActionListener(new activeBotons());
						botonFight.addActionListener(new activeBotons());
						botonClearConsole.addActionListener(new activeBotons());
						panel4.getTpoint().setText(Integer.toString(totalPoints));
						add(panel4);

					}
				});
			}
			add(panel3);

		}

	}

//Class for the Ranking window
	class Tabla extends JFrame {

		public Tabla(ConnectionDB conn) {
			// ==Confg general
			Container cn = this.getContentPane();
			setTitle("Ranking");
			String sCarpAct = System.getProperty("user.dir");
			Toolkit mipantalla = Toolkit.getDefaultToolkit();
			Image miicono = mipantalla.getImage(sCarpAct + File.separator + "img" + File.separator + "iconJava.jpg");
			setIconImage(miicono);
			setSize(480, 440);
			setLocationRelativeTo(null);
			setResizable(false);
			JPanel panel1 = new JPanel();

			String[] nomCol = { "POSITION", "PLAYER", "WARRIOR", "POINTS" };

			String[][] datos = conn.buscar(conn.countrows("select * from RANKING limit 10;"));
			JTable taula = new JTable(datos, nomCol);
			taula.setRowHeight(34);
			taula.setRowMargin(WIDTH);
			taula.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			JScrollPane jsp = new JScrollPane(taula);
			panel1.add(jsp);
			cn.setLayout(new BoxLayout(cn, BoxLayout.Y_AXIS));

			JLabel titulo = new JLabel("Top 10 Players");
			titulo.setForeground(Color.red);
			titulo.setFont(new Font("Arial", Font.BOLD, 24));
			titulo.setAlignmentX(CENTER_ALIGNMENT);

			add(titulo);

			add(panel1);

			setVisible(true);
		}

	}
}