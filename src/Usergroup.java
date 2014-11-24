import java.util.ArrayList;
import java.util.List;

public class Usergroup implements Component{

	private static int groupCount;
	private String name;
	private List<Component> childComponents = new ArrayList<Component>();

	public Usergroup(String string) {
		name = string;
		groupCount++;
	}
	
	public void add(Component component){
		childComponents.add(component);
	}
	
	public List<Component> getComponents(){
		return childComponents;
	}

	public static int getGroupCount() {
		return groupCount;
	}
	
	public String toString(){
		return name;
	}
	
}
