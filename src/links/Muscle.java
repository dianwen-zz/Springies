package links;

public class Muscle extends SuperLink {

	private static final int MASS_COLLISION_ID = 3;
	private float phaseAngle; 
	
	public Muscle(String massA, String massB, float restlength, float constant, float phaseAngle) {
		// TODO Auto-generated constructor stub
		super(massA, massB, restlength, constant);
		this.phaseAngle = phaseAngle; 
	}
	
	

}
