public class AssassinPlayer {
	public final String name;
	protected final String normalizedName;
	protected String killer;

	AssassinPlayer(String name) {
		this.name = name;
		this.normalizedName = name.toLowerCase();
	}

	public String getKiller() {
		return killer;
	}
}
