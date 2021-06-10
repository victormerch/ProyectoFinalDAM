import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ConnectionDB {
	public static Connection conn;

	public ConnectionDB() {
		
	}

	// Conection
	public boolean conection(String user, String pass) {
		
		String urlDatos = "jdbc:mysql://localhost/FINAL_PROYECT?serverTimezone=UTC";

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");

			
			conn = DriverManager.getConnection(urlDatos, user, pass);
			return true;

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Driver loaded incorrectly", "Error", JOptionPane.WARNING_MESSAGE);
			return false;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Incorrect UserDB or PasswordDB", "Error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

	// Take Array with all characters
	public WarriorContainer getWarriorContainer() {
		WarriorContainer warriors = new WarriorContainer();
		try {
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from WARRIORS");

			while (rs.next()) {
				int race_id = rs.getInt(3);
				ResultSet rs2 = conn.createStatement().executeQuery("select * from RACE where RACE_ID=" + race_id);
				rs2.next();

				if (race_id == 1) {

					warriors.addWarrior(new Dwarf(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
							rs2.getInt(3), rs2.getInt(4), rs2.getInt(5), rs2.getInt(6), rs2.getInt(7)));
				} else if (race_id == 2) {

					warriors.addWarrior(new Human(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
							rs2.getInt(3), rs2.getInt(4), rs2.getInt(5), rs2.getInt(6), rs2.getInt(7)));
				} else if (race_id == 3) {

					warriors.addWarrior(new Elf(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
							rs2.getInt(3), rs2.getInt(4), rs2.getInt(5), rs2.getInt(6), rs2.getInt(7)));
				}

			}
			return warriors;
		} catch (SQLException e) {
			
			System.out.println("Error in ArrayWarriors");
			return null;
		}

	}

	// Take an arraylist with the weapons that the chosen warrior can take
	public ArrayList<Weapon> getWeaponContainer(Warrior warrior) {

		try {
			ArrayList<Weapon> array = new ArrayList<Weapon>();
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from WEAPONS");

			ResultSet rs2 = conn.createStatement()
					.executeQuery("select * from RACE where RACE_ID=" + warrior.getRace_id());
			rs2.next();

			while (rs.next()) {

				if (rs.getString(5).contains(rs2.getString(2))) {
					array.add(new Weapon(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
							rs.getString(6)));

				}
			}
			return array;
		} catch (SQLException e) {
			
			System.out.println("Error en wapon container");
		}
		return null;
	}

	// Ranking record
	public static void insertRanking(String warriorName, int puntos_totales) {
		int warrior_id;
		int player_id;
		try {
			Statement stmnt = conn.createStatement();

			ResultSet searchWarriorId = stmnt
					.executeQuery("select WARRIOR_ID from WARRIORS where WARRIOR_NAME = '" + warriorName + "'");
			searchWarriorId.next();
			warrior_id = searchWarriorId.getInt(1);

			ResultSet searchPlayerId = stmnt.executeQuery("select MAX(PLAYER_ID) from PLAYERS");
			searchPlayerId.next();
			player_id = searchPlayerId.getInt(1);

			stmnt.executeUpdate(
					"insert into RANKING values(" + player_id + ", " + puntos_totales + ", " + warrior_id + ")");
			stmnt.close();
		} catch (SQLException e) {
			
			System.out.println("");
		}
	}
	
	//Insert the player in the database
	public static void insertPlayer(String player_name) {
		try {
			Statement stmnt = conn.createStatement();
			stmnt.executeUpdate("insert  into PLAYERS(PLAYER_NAME) values('" + player_name + "')");
			stmnt.close();
		} catch (SQLException e) {
			
			System.out.println("");
		}
	}
	//Insertion of each battle
	public static void insertBattle(String warriorName, int weaponId, String oponentName, int oponentWeaponId,
			int oponent_life, int rest_life) {
		try {
			int warrior_id, oponentWarrior_id, player_id, battle_points, warriorRaceId, oponentRaceId, opponentHP,
					warriorHP;
			int suffered;
			Statement stmnt = conn.createStatement();

			ResultSet searchPlayerId = conn.createStatement().executeQuery("select MAX(PLAYER_ID) from PLAYERS");
			searchPlayerId.next();
			player_id = searchPlayerId.getInt(1);
			searchPlayerId.close();

			ResultSet searchWarriorId = conn.createStatement()
					.executeQuery("select * from WARRIORS where WARRIOR_NAME = '" + warriorName + "'");
			searchWarriorId.next();
			warrior_id = searchWarriorId.getInt(1);
			warriorRaceId = searchWarriorId.getInt(3);
			searchWarriorId.close();

			ResultSet searchWarriorOponentId = conn.createStatement()
					.executeQuery("select * from WARRIORS where WARRIOR_NAME = '" + oponentName + "'");
			searchWarriorOponentId.next();
			oponentWarrior_id = searchWarriorOponentId.getInt(1);
			oponentRaceId = searchWarriorOponentId.getInt(3);
			searchWarriorOponentId.close();

			ResultSet searchWarriorRaceHP = conn.createStatement()
					.executeQuery("select * from RACE where RACE_ID = '" + warriorRaceId + "'");
			searchWarriorRaceHP.next();
			warriorHP = searchWarriorRaceHP.getInt(3);
			searchWarriorRaceHP.close();

			ResultSet searchOpponentRaceHP = conn.createStatement()
					.executeQuery("select * from RACE where RACE_ID = '" + oponentRaceId + "'");
			searchOpponentRaceHP.next();
			opponentHP = searchOpponentRaceHP.getInt(3);
			searchOpponentRaceHP.close();

			battle_points = opponentHP - oponent_life;
			suffered = warriorHP - rest_life;

			stmnt.executeUpdate(
					"insert  into BATTLE(PLAYER_ID, WARRIOR_ID, WARRIOR_WEAPON_ID, OPPONENT_ID, OPPONENT_WEAPON_ID, INJURIES_CAUSED, INJURIES_SUFFERED, BATTLE_POINTS) values("
							+ player_id + ", " + warrior_id + ", " + weaponId + ", " + oponentWarrior_id + ", "
							+ oponentWeaponId + ", " + battle_points + ", " + suffered + ", " + battle_points + ")");
			stmnt.close();
		} catch (SQLException e) {
			System.out.println(e + "Error en insert de BATTLE");
		}
	}

	// This function is used to count the rows in a query
	public static int countrows(String query) {
		try {
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery(query);

			int contador = 0;
			while (rs.next()) {
				contador++;

			}
			return contador;

		} catch (SQLException e) {
			
			System.out.println("Query not found");
			return 0;
		}
	}
	//Function created to search and store the top 10 players in the ranking
	public String[][] buscar(int rows) {
		try {
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery(
					"select p.PLAYER_NAME, w.WARRIOR_NAME, r.TOTAL_POINTS from RANKING r inner join PLAYERS p on p.PLAYER_ID = r.PLAYER_ID\n"
							+ "inner join WARRIORS w on w.WARRIOR_ID = r.WARRIOR_ID order by r.TOTAL_POINTS desc limit 10;");
			String[][] datos = new String[rows][4];
			int contador = 0;
			int position = 1;
			while (rs.next()) {
				if (contador < rows) {
					datos[contador][0] = String.valueOf(position);
					datos[contador][1] = rs.getString(1);
					datos[contador][2] = rs.getString(2);
					datos[contador][3] = String.valueOf(rs.getInt(3));

				} else {
					contador = 0;
					datos[contador][0] = String.valueOf(position);
					datos[contador][1] = rs.getString(1);
					datos[contador][2] = rs.getString(2);
					datos[contador][3] = String.valueOf(rs.getInt(3));

				}
				contador++;
				position++;

			}
			return datos;

		} catch (SQLException e) {
			
			System.out.println("No ha funcionado la consulta");

		}
		return null;
	}
	
	public static int recordRanking() {
		Statement stmnt;
		int top1 = 0;
		try {
			stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery(
					"select MAX(TOTAL_POINTS) from RANKING");
			while(rs.next()) {
				top1 = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in Ranking");
		}
		return top1;
		
	}
}
