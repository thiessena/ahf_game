package elementFactory;

public enum Items {
	FLOOR(0),
	OBSTACLE(1),
	KEY(2),
	BUCKET(3),
	BRUSH(4),
	CALCULATOR(5);
	

	int item;
	
	private static String floor = "Boden";
	private static String obstacle = "Hindernis";
	private static String key = "Schlüssel";
	private static String bucket = "Putzeimer";
	private static String brush = "Pinsel";
	private static String calculator = "Taschenrechner";
	

	Items(int pItem) {
		this.item = pItem;
	}
	
	public int getInt() {
		return item;
	}
	
	public static String toString(Items pItem) {
		switch(pItem) {
		case FLOOR:
			return floor;
		case OBSTACLE:
			return obstacle;
		case KEY:
			return key;
		case BUCKET:
			return bucket;
		case BRUSH:
			return brush;
		case CALCULATOR:
			return calculator;
		default:
			return null;
		}
	}
	
	public static Items fromID(int pID) {
        for (Items item : values()) {
            if (item.getInt() == pID) {
                return item;
            }
        }
        return null;
    }
}