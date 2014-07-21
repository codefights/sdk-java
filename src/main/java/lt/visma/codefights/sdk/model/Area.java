package lt.visma.codefights.sdk.model;

public enum Area{

    NOSE (10), 
    JAW (8),
    BELLY (6), 
    GROIN (4), 
    LEGS (3);
	
    private final int value;

    private Area(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
	
}