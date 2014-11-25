
/*
 * Visitor class to get a count of the positive messages.
 */
public class PositiveVisitor implements ComponentVisitor {

	private int counter = 0;

	@Override
	public void visitUser(User user) {
		for (int i = 0; i < user.getNewsFeed().size(); i++) {
			if (user.getNewsFeed().get(i).contains("good")
					|| user.getNewsFeed().get(i).contains("happy")
					|| user.getNewsFeed().get(i).contains("excelent"))
				counter++;
		}
	}

	@Override
	public void visitUserGroup(Usergroup usergroup) {

	}

	public int getCounter() {
		return counter;
	}

}