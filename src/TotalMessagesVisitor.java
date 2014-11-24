
public class TotalMessagesVisitor implements ComponentVisitor {

	private int counter;
	
	public void visitUser(User user) {
		counter += user.getNewsFeed().size();
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public void visitUserGroup(Usergroup usergroup) {
		// TODO Auto-generated method stub
		
	}
}
