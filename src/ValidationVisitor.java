import java.util.HashSet;
import java.util.Set;

/*
 * Visitor class to check if the users and Components are valid
 * uses a Set to make sure duplicates are not added to the HashSet, if they are
 * then a counter is triggered.
 */
public class ValidationVisitor implements ComponentVisitor {

	private int counter = 0;
	private Set<String> userNames = new HashSet<String>();
	private Set<String> groupNames = new HashSet<String>();

	@Override
	public void visitUser(User user) {
		
		if (userNames.add(user.getID()) == false) {
			counter++;
		} else if (user.getID().contains(" ") == true) {
			counter++;
		}
	}

	@Override
	public void visitUserGroup(Usergroup usergroup) {
		if (groupNames.add(usergroup.getName()) == false) {
			counter++;
		} else if (usergroup.getName().contains(" ") == true) {
			counter++;
		}
	}

	public int getCounter() {
		return counter;
	}

}
