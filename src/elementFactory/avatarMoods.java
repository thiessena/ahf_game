package elementFactory;

/**
 * Moods und Bewegungen, die ein Player annehmen kann.
 * 
 * @author Jonas Schweizer
 * @version 27.01.2020
 *
 */
public enum avatarMoods {
	PLAIN_FRONT(0), 
	RUNNING_LEFT_FRONT(1), 
	WAITING_FRONT(2), 
	RUNNING_RIGHT_FRONT(3),

	PLAIN_BACK(4), 
	RUNNING_LEFT_BACK(5), 
	WAITING_BACK(6), 
	RUNNING_RIGHT_BACK(7),

	PLAIN_WEST(8), 
	RUNNING_LEFT_WEST(9), 
	WAITING_WEST(10), 
	RUNNING_RIGHT_WEST(11),

	PLAIN_EAST(12), 
	RUNNING_LEFT_EAST(13), 
	WAITING_EAST(14), 
	RUNNING_RIGHT_EAST(15);

	int mood;

	/**
	 * Weist der Enumvariable <code>mood</code> seinen spezifischen Integer-Wert zu.
	 * 
	 * @param pMood Integer-Wert
	 */
	avatarMoods(int pMood) {
		this.mood = pMood;
	}
	
	/**
	 * Gibt den Integer-Wert zurück.
	 * 
	 * @return Wert der Enumvariable
	 */
	public int getInt() {
		return mood;
	}
}