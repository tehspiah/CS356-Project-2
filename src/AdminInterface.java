import java.awt.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import java.util.*;

/*
 * Admin interface to control launching of user view, see users + groups
 * and to view statistics.
 */
public class AdminInterface implements ActionListener, TreeSelectionListener {

	JFrame frame;
	JButton addUser, addGroup, userView, showUserTotal, showGroupTotal,
			showMessagesTotal, showPositiveTotal, validateID, lastUpdated;
	JTextArea UserID, GroupID;
	JTree tree;
	Hashtable<String, User> userTable;
	JPanel panel;

	DefaultMutableTreeNode selectedLabel;

	private User bob, alice, jeff, cindy; // hardcoded groups and users
	private DefaultMutableTreeNode bobNode, aliceNode, jeffNode, cindyNode,
			rootNode, class1Node;
	private Usergroup root, class1;

	private static AdminInterface instance = null; // singleton instance

	private AdminInterface() {
		frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setTitle("Admin Panel");
		frame.setSize(350, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1,2));
		
		panel = new JPanel();

		userTable = new Hashtable<String, User>();
		addUsers();
		tree = new JTree(rootNode);
		frame.add(tree);

		tree.getSelectionModel().addTreeSelectionListener(this);

		addUser = new JButton("Add User");
		addGroup = new JButton("Add Group");
		userView = new JButton("User View");
		showUserTotal = new JButton("Show User Total");
		showGroupTotal = new JButton("Show Group Total");
		showMessagesTotal = new JButton("Show Messages Total");
		showPositiveTotal = new JButton("Show Positive Total");
		validateID = new JButton("Validate IDs");
		lastUpdated = new JButton("Last Updated ID");

		addUser.addActionListener(this);
		addGroup.addActionListener(this);
		userView.addActionListener(this);
		showUserTotal.addActionListener(this);
		showGroupTotal.addActionListener(this);
		showMessagesTotal.addActionListener(this);
		showPositiveTotal.addActionListener(this);
		validateID.addActionListener(this);
		lastUpdated.addActionListener(this);

		panel.add(userView);
		panel.add(showUserTotal);
		panel.add(showGroupTotal);
		panel.add(showMessagesTotal);
		panel.add(showPositiveTotal);
		panel.add(validateID);
		panel.add(lastUpdated);
		frame.add(panel);
		frame.setVisible(true);
	}

	// action events for JButtons
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addUser) {

		}
		if (e.getSource() == addGroup) { // currently not added to frame

		}
		if (e.getSource() == userView) { // launches userview
			((User) selectedLabel.getUserObject()).generateUI();
		}
		if (e.getSource() == showUserTotal) { // shows total users
			UserTotalVisitor UTvisitor = new UserTotalVisitor();
			root.accept(UTvisitor);
			JOptionPane.showMessageDialog(frame, UTvisitor.getCounter(),
					"Total Users", 1);
		}
		if (e.getSource() == showGroupTotal) { // shows # of groups
			GroupTotalVisitor total = new GroupTotalVisitor();
			root.accept(total);
			JOptionPane.showMessageDialog(frame, total.getCounter(),
					"Total Groups", 1);
		}
		if (e.getSource() == showMessagesTotal) { // shows total # of messages
			TotalMessagesVisitor total = new TotalMessagesVisitor();
			root.accept(total);
			JOptionPane.showMessageDialog(frame, total.getCounter(),
					"Total number of messages", 1);
		}
		if (e.getSource() == showPositiveTotal) { // shows number of positive
													// tweets
			PositiveVisitor PV = new PositiveVisitor();
			root.accept(PV);
			JOptionPane.showMessageDialog(frame, PV.getCounter(),
					"Positive Tweets", 1);
		}
		if (e.getSource() == validateID) {
			ValidationVisitor VV = new ValidationVisitor();
			root.accept(VV);
			JOptionPane.showMessageDialog(frame, VV.getCounter(),
					"Number of invalid IDs", 1);
		}
		if (e.getSource() == lastUpdated) {
			UpdateVisitor UV = new UpdateVisitor();
			root.accept(UV);
			JOptionPane.showMessageDialog(frame, UV.getID(),
					"Last updated user", 1);
		}

	}

	/*
	 * Method to create users and groups and to add them to the respective
	 * groups Creates a user, then puts them into the user into a hash table to
	 * return strings as a User. Then it creates nodes to show in the admin
	 * interface hierarchy.
	 */
	public void addUsers() {
		bob = new User("Bob");
		alice = new User("Alice");
		jeff = new User("Jeff");
		cindy = new User("Cindy");

		userTable.put("Bob", bob);
		userTable.put("Alice", alice);
		userTable.put("Jeff", jeff);
		userTable.put("Cindy", cindy);

		root = new Usergroup("root");
		root.add(bob);
		root.add(alice);
		class1 = new Usergroup("Class1");
		class1.add(jeff);
		class1.add(cindy);

		root.add(class1);

		bobNode = new DefaultMutableTreeNode(bob);
		aliceNode = new DefaultMutableTreeNode(alice);
		jeffNode = new DefaultMutableTreeNode(jeff);
		cindyNode = new DefaultMutableTreeNode(cindy);
		
		rootNode = new DefaultMutableTreeNode(root);
		rootNode.add(bobNode);
		rootNode.add(aliceNode);
		class1Node = new DefaultMutableTreeNode(class1);
		class1Node.add(jeffNode);
		class1Node.add(cindyNode);

		rootNode.add(class1Node);
	}

	// getter for hashtable
	public Hashtable<String, User> getUserTable() {
		return userTable;
	}

	// tree selection listener
	public void valueChanged(TreeSelectionEvent e) {
		selectedLabel = (DefaultMutableTreeNode) (e.getPath()
				.getLastPathComponent());
	}

	// singleton
	public static AdminInterface getInstance() {
		if (instance == null) {
			instance = new AdminInterface();
		}
		return instance;
	}
}
