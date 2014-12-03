public class UpdateVisitor implements ComponentVisitor {

	private String userID;
	private long updateTime;

	@Override
	public void visitUser(User user) {
		if(user.getUpdateTime()>updateTime){
			updateTime = user.getUpdateTime();
			userID = user.getID();
		}
	}

	@Override
	public void visitUserGroup(Usergroup usergroup) {

	}

	public String getID() {
		return userID;
	}

}
