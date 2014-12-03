import java.util.ArrayList;
import java.util.List;

/*
 * Class that holds the group of users.
 */
public class Usergroup implements Component{

	private String name;
	private List<Component> childComponents = new ArrayList<Component>();
	private long creationTime;

	public Usergroup(String string) {
		name = string;
		creationTime = System.currentTimeMillis();
		System.out.println("creation time for " +name +" " +creationTime);
	}
	
	public String getName(){
		return name;
	}
	
	/*
	 * method to add User groups
	 */
	public void add(Component component){
		childComponents.add(component);
	}
	
	/*
	 * getter for the user groups
	 */
	public List<Component> getComponents(){
		return childComponents;
	}
	
	public String toString(){
		return name;
	}

	/* Accepts visitor for the root group and child components
	 * (non-Javadoc)
	 * @see Component#accept(ComponentVisitor)
	 */
	@Override
	public void accept(ComponentVisitor visitor) {
		visitor.visitUserGroup(this);
		for (int i=0; i<childComponents.size(); i++){
			childComponents.get(i).accept(visitor);
		}
	}
	
}
