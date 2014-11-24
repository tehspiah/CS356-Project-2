import java.util.ArrayList;
import java.util.List;

public class Usergroup implements Component{

	private String name;
	private List<Component> childComponents = new ArrayList<Component>();

	public Usergroup(String string) {
		name = string;
	}
	
	public void add(Component component){
		childComponents.add(component);
	}
	
	public List<Component> getComponents(){
		return childComponents;
	}
	
	public String toString(){
		return name;
	}

	@Override
	public void accept(ComponentVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visitUserGroup(this);
		for (int i=0; i<childComponents.size(); i++){
			childComponents.get(i).accept(visitor);
		}
	}
	
}
