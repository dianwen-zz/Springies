package links;

public class Muscle extends SuperLink {

	private static final int MASS_COLLISION_ID = 3;
	private float amplitude; 
	
	public Muscle(String massA, String massB, float restlength, float constant, float amplitude) {
		// TODO Auto-generated constructor stub
		super(massA, massB, restlength, constant);
		this.amplitude = amplitude; 
	}

}
