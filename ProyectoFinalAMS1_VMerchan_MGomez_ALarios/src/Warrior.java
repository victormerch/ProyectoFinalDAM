import java.util.ArrayList;
//Class general of warrior
abstract public class Warrior {

	protected int warrior_id;
	protected String warrior_name;

	protected int race_id;
	protected String img;
	protected int health;
	protected int force;
	protected int defense;
	protected int agility;
	protected int velocity;

	// Constructor
	public Warrior(int warrior_id, String warrior_name, int race_id, String img, int health, int force, int defense,
			int agility, int velocity) {
		super();
		this.warrior_id = warrior_id;
		this.warrior_name = warrior_name;

		this.race_id = race_id;
		this.img = img;

		this.health = health;
		this.force = force;
		this.defense = defense;
		this.agility = agility;
		this.velocity = velocity;
	}

	public Warrior() {
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
	public int getWarrior_id() {
		return warrior_id;
	}

	public void setWarrior_id(int warrior_id) {
		this.warrior_id = warrior_id;
	}

	public String getWarrior_name() {
		return warrior_name;
	}

	public void setWarrior_name(String warrior_name) {
		this.warrior_name = warrior_name;
	}

	public int getRace_id() {
		return race_id;
	}

	public void setRace_id(int race_id) {
		this.race_id = race_id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

}
//class of Dwarf
class Dwarf extends Warrior {

	public Dwarf(int warrior_id, String warrior_name, int race_id, String img, int health, int force, int defense,
			int agility, int velocity) {
		super();
		this.warrior_id = warrior_id;
		this.warrior_name = warrior_name;

		this.race_id = race_id;
		this.img = img;

		this.health = health;
		this.force = force;
		this.defense = defense;
		this.agility = agility;
		this.velocity = velocity;
	}

	@Override
	public String toString() {
		return "Dwarf [warrior_id=" + warrior_id + ", warrior_name=" + warrior_name + ", race_id=" + race_id + ", img="
				+ img + ", health=" + health + ", force=" + force + ", defense=" + defense + ", agility=" + agility
				+ ", velocity=" + velocity + "]\n";
	}

}
//class of Human
class Human extends Warrior {

	public Human(int warrior_id, String warrior_name, int race_id, String img, int health, int force, int defense,
			int agility, int velocity) {
		super();
		this.warrior_id = warrior_id;
		this.warrior_name = warrior_name;

		this.race_id = race_id;
		this.img = img;

		this.health = health;
		this.force = force;
		this.defense = defense;
		this.agility = agility;
		this.velocity = velocity;
	}

	@Override
	public String toString() {
		return "Human [warrior_id=" + warrior_id + ", warrior_name=" + warrior_name + ", race_id=" + race_id + ", img="
				+ img + ", health=" + health + ", force=" + force + ", defense=" + defense + ", agility=" + agility
				+ ", velocity=" + velocity + "]\n";
	}

}
//class of Elf
class Elf extends Warrior {

	public Elf(int warrior_id, String warrior_name, int race_id, String img, int health, int force, int defense,
			int agility, int velocity) {
		super();
		this.warrior_id = warrior_id;
		this.warrior_name = warrior_name;
		this.race_id = race_id;
		this.img = img;

		this.health = health;
		this.force = force;
		this.defense = defense;
		this.agility = agility;
		this.velocity = velocity;
	}

	@Override
	public String toString() {
		return "Elf [warrior_id=" + warrior_id + ", warrior_name=" + warrior_name + ", race_id=" + race_id + ", img="
				+ img + ", health=" + health + ", force=" + force + ", defense=" + defense + ", agility=" + agility
				+ ", velocity=" + velocity + "]\n";
	}

}
//Class for the arraylist of all characters in the database
class WarriorContainer {
	ArrayList<Warrior> warriors = new ArrayList<Warrior>();

	public WarriorContainer() {

	}

	public void addWarrior(Warrior w) {
		warriors.add(w);
	}

	public Warrior getRandomWarrior() {
		return warriors.get((int) Math.floor(Math.random() * (warriors.size() - 2 + 1) + 1));
	}

	public int getDimension() {
		return warriors.size();
	}

	public Warrior getWarriorIndex(int indice) {
		return warriors.get(indice);
	}

	public ArrayList<Warrior> getWarriors() {
		return warriors;
	}

	public void setWarriors(ArrayList<Warrior> warriors) {
		this.warriors = warriors;
	}

	public ArrayList<Warrior> getHumans() {
		ArrayList<Warrior> Human = new ArrayList<Warrior>();
		for (Warrior warrior : warriors) {
			if (warrior instanceof Human) {
				Human.add(warrior);
			}
		}
		return Human;
	}

	public ArrayList<Warrior> getElf() {
		ArrayList<Warrior> Elf = new ArrayList<Warrior>();
		for (Warrior warrior : warriors) {
			if (warrior instanceof Elf) {
				Elf.add(warrior);
			}
		}
		return Elf;
	}

	public ArrayList<Warrior> getDwarf() {
		ArrayList<Warrior> Dwarf = new ArrayList<Warrior>();
		for (Warrior warrior : warriors) {
			if (warrior instanceof Dwarf) {
				Dwarf.add(warrior);
			}
		}
		return Dwarf;
	}
}