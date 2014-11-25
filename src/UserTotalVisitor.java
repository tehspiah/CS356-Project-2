/*
 * Visitor class to count the total number of users
 */
public class UserTotalVisitor implements ComponentVisitor {

	private int counter = 0;

	@Override
	public void visitUser(User user) {
		counter++;
	}

	@Override
	public void visitUserGroup(Usergroup usergroup) {

	}

	private void setCounter(int counter) {
		this.counter = counter;
	}

	public int getCounter() {
		return counter;
	}

}
