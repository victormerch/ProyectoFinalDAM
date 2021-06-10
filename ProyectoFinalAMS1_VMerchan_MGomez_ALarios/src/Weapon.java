import java.util.ArrayList;

public class Weapon {
	private int weapon_id;
	private String weapon_name;
	private int plus_force;
	private int plus_velocity;
	private String weapon_race;
	private String weapon_url;
	public Weapon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Weapon(int weapon_id, String weapon_name, int plus_force, int plus_velocity, String weapon_race,
			String weapon_url) {
		super();
		this.weapon_id = weapon_id;
		this.weapon_name = weapon_name;
		this.plus_force = plus_force;
		this.plus_velocity = plus_velocity;
		this.weapon_race = weapon_race;
		this.weapon_url = weapon_url;
	}
	public int getWeapon_id() {
		return weapon_id;
	}
	public void setWeapon_id(int weapon_id) {
		this.weapon_id = weapon_id;
	}
	public String getWeapon_name() {
		return weapon_name;
	}
	public void setWeapon_name(String weapon_name) {
		this.weapon_name = weapon_name;
	}
	public int getPlus_force() {
		return plus_force;
	}
	public void setPlus_force(int plus_force) {
		this.plus_force = plus_force;
	}
	public int getPlus_velocity() {
		return plus_velocity;
	}
	public void setPlus_velocity(int plus_velocity) {
		this.plus_velocity = plus_velocity;
	}
	public String getWeapon_race() {
		return weapon_race;
	}
	public void setWeapon_race(String weapon_race) {
		this.weapon_race = weapon_race;
	}
	public String getWeapon_url() {
		return weapon_url;
	}
	public void setWeapon_url(String weapon_url) {
		this.weapon_url = weapon_url;
	}
	@Override
	public String toString() {
		return "Weapon [weapon_id=" + weapon_id + ", weapon_name=" + weapon_name + ", plus_force=" + plus_force
				+ ", plus_velocity=" + plus_velocity + ", weapon_race=" + weapon_race + ", weapon_url=" + weapon_url
				+ "]\n";
	}
	
	
	class WeaponContainer{
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	    public WeaponContainer(){
	        
	    }

	    public void addWeapons(Weapon w) {
	        weapons.add(w);
	    }

	    public Weapon getRandomWeapon() {
	    	return weapons.get((int) Math.floor(Math.random() * (weapons.size() - 2 + 1) + 1));
	    }
	    
	    public int getDimension() {
	        return weapons.size();
	    }

	    public Weapon getWeponIndex(int indice) {
	        return weapons.get(indice);
	    }

		public ArrayList<Weapon> getWeapons() {
			return weapons;
		}

		public void setWeapons(ArrayList<Weapon> weapons) {
			this.weapons = weapons;
		}
	}
	
	
	
	
	 
}
