
public abstract class Event {
	static int ID_Count;
	private int ID;
	private int OwnerID;
	private int ResponsibleID;

	public Event(int ownerID, int responsibleID) {
		this.ID = ID_Count;
		ID_Count++;
		this.OwnerID = ownerID;
		this.ResponsibleID = responsibleID;
	}

	public Event(int ownerID, int responsibleID, int ID) {
		this.ID = ID;
		this.OwnerID = ownerID;
		this.ResponsibleID = responsibleID;

	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getOwnerID() {
		return OwnerID;
	}

	public void setOwnerID(int ownerID) {
		OwnerID = ownerID;
	}

	public int getResponsibleID() {
		return ResponsibleID;
	}

	public void setResponsibleID(int responsibleID) {
		ResponsibleID = responsibleID;
	}

}
