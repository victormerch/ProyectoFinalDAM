

public class Player {
	String name;
	String Username;
	int Vel;
	int Agil;
	int Life;
	int Atack;
	int Def;
	int TotalHP;
	public Player(String username, Warrior warrior,Weapon weapon) {
		super();
		this.name = warrior.getWarrior_name();
		Vel = warrior.getVelocity()+weapon.getPlus_velocity();
		Agil = warrior.getAgility();
		Life = warrior.getHealth();
		Atack = warrior.force+weapon.getPlus_force();
		Def = warrior.getDefense();
		TotalHP = warrior.getHealth();
		Username = username;
	}
	
	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public int getTotalHP() {
		return TotalHP;
	}

	public void setTotalHP(int totalHP) {
		TotalHP = totalHP;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVel() {
		return Vel;
	}

	public void setVel(int vel) {
		Vel = vel;
	}

	public int getAgil() {
		return Agil;
	}

	public void setAgil(int agil) {
		Agil = agil;
	}

	public int getLife() {
		return Life;
	}

	public void setLife(int life) {
		Life = life;
	}

	public int getAtack() {
		return Atack;
	}

	public void setAtack(int atack) {
		Atack = atack;
	}

	public int getDef() {
		return Def;
	}

	public void setDef(int def) {
		Def = def;
	}

	

}