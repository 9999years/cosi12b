import java.text.Normalizer;

public class AssassinPlayer {
	public final String name;
	protected final String normalizedName;
	public AssassinPlayer killer;

	/**
	 * normalizes and lower-cases a name
	 */
	protected static String comparableName(String name) {
		// "Compatibility decomposition, followed by canonical
		// composition."
		return Normalizer.normalize(name, Normalizer.Form.NFKD)
			.toLowerCase();
	
	}

	AssassinPlayer(String name) {
		this.name = name;
		this.normalizedName = comparableName(name);
	}

	public String isKiller(String s) {
		return killer.equals(s);
	}

	public boolean equals(Object o) {
		if(o instanceof AssassinPlayer) {
			return normalizedName.equals(
				((AssassinPlayer) o).normalizedName);
		} else if(o instanceof String) {
			return normalizedName.equals((String) o);
		} else {
			return false;
		}
	}
}
