/*
 * interface to accept visitors.
 */
public interface Component {

	public void accept(ComponentVisitor visitor);
	
}
