import java.util.List;


public class PositiveVisitor implements ComponentVisitor{

	private int counter = 0;
	@Override
	public void visitUser(User user) {
		for (int i = 0; i < user.getNewsFeed().size(); i++) {
			if ( user.getNewsFeed().get(i).toString().contains("good") ||  user.getNewsFeed().get(i).toString().contains("happy")
					|| user.getNewsFeed().get(i).toString().contains("excelent"))
				counter++;
		}
		
	}

	@Override
	public void visitUserGroup(Usergroup usergroup) {
		// TODO Auto-generated method stub
		
	}
	
	private void setCounter(int counter) {
		this.counter = counter;
	}

	public int getCounter() {
		return counter;
	}

}