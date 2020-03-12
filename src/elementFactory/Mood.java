package src.elementFactory;

/**
 * Erstellt einen Array mit den {@link elementFactory.avatarMoods}
 * 
 * @author Jonas Schweizer
 * @version 27.01.2020
 */
public class Mood {
	
	/**
	 * Array mit den Moods, die ein Avatar annehmen kann.
	 * @see elementFactory.avatarMoods
	 */
    private avatarMoods[][] moodArray;
    
    /**
     * Instanz dieser Klasse
     */
    private static Mood sInstance = null;

    /**
     * Füllt den {@link moodArray} mit den Moods, die ein Avatar annehmen kann
     * @see elementFactory.avatarMoods
     */
    private Mood() {
        moodArray = new avatarMoods[4][4];

        moodArray[0][0] = avatarMoods.PLAIN_FRONT;
        moodArray[0][1] = avatarMoods.RUNNING_LEFT_FRONT;
        moodArray[0][2] = avatarMoods.WAITING_FRONT;
        moodArray[0][3] = avatarMoods.RUNNING_RIGHT_FRONT;
                    
        moodArray[1][0] = avatarMoods.PLAIN_BACK;
        moodArray[1][1] = avatarMoods.RUNNING_LEFT_BACK;
        moodArray[1][2] = avatarMoods.WAITING_BACK;
        moodArray[1][3] = avatarMoods.RUNNING_RIGHT_BACK;
                    
        moodArray[2][0] = avatarMoods.PLAIN_WEST;
        moodArray[2][1] = avatarMoods.RUNNING_LEFT_WEST;
        moodArray[2][2] = avatarMoods.WAITING_WEST;
        moodArray[2][3] = avatarMoods.RUNNING_RIGHT_WEST;
                    
        moodArray[3][0] = avatarMoods.RUNNING_LEFT_EAST;
        moodArray[3][1] = avatarMoods.PLAIN_EAST;
        moodArray[3][2] = avatarMoods.RUNNING_RIGHT_EAST;
        moodArray[3][3] = avatarMoods.WAITING_EAST;
    }

    /**
     * Gibt die Instanz dieser Klasse zurück.
     * 
     * @return Instanz dieser Klasse
     */
    public static Mood getInstance() {
        if (sInstance == null) {
            sInstance = new Mood();
        }

        return sInstance;
    }
    
    /**
     * Gibt den Mood an einer bestimmten Stelle im Array zurück
     * 
     * @param x <code>x</code>-Koordinate des zurückzugebenen Moods
     * @param y <code>y</code>-Koordinate des zurückzugebenen Moods
     * @return Mood der angegebenen Stelle im Array
     */
    public avatarMoods getMood(int x, int y) {
        return sInstance.moodArray[y][x];
    }
}
