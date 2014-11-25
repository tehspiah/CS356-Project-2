/*
 * Visitor class to get the total number of groups
 */
public class GroupTotalVisitor implements ComponentVisitor{

	private int counter = 0;
	
	@Override
	public void visitUser(User user) {
		
	}

	@Override
	public void visitUserGroup(Usergroup usergroup) {
		counter++;
	}

	private void setCounter(int counter) {
		this.counter = counter;
	}

	public int getCounter() {
		return counter;
	}

}
