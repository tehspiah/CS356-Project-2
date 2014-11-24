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

public class AdminInterface implements ActionListener, TreeSelectionListener {

	JFrame frame;
	JButton addUser, addGroup, userView, showUserTotal, showGroupTotal,
			showMessagesTotal, showPositiveTotal;
	JTextArea UserID, GroupID;
	JTree tree;
	Hashtable<String, User> userTable;

	DefaultMutableTreeNode selectedLabel;

	private User bob, alice, jeff, cindy;
	private DefaultMutableTreeNode bobNode, aliceNode, jeffNode, cindyNode,
			rootNode, class1Node;
	private Usergroup root, class1;

	private static AdminInterface instance = null;

	private AdminInterface() {
		frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setTitle("Admin Panel");
		frame.setSize(240, 280);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		addUser.addActionListener(this);
		addGroup.addActionListener(this);
		userView.addActionListener(this);
		showUserTotal.addActionListener(this);
		showGroupTotal.addActionListener(this);
		showMessagesTotal.addActionListener(this);
		showPositiveTotal.addActionListener(this);

		frame.add(userView);
		frame.add(showUserTotal);
		frame.add(showGroupTotal);
		frame.add(showMessagesTotal);
		frame.add(showPositiveTotal);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addUser) {

		}
		if (e.getSource() == addGroup) {

		}
		if (e.getSource() == userView) {
			((User) selectedLabel.getUserObject()).generateUI();
		}
		if (e.getSource() == showUserTotal) {
			JOptionPane.showMessageDialog(frame, User.getUserCount(),
					"Total Users", 1);
		}
		if (e.getSource() == showGroupTotal) {
			JOptionPane.showMessageDialog(frame, Usergroup.getGroupCount(),
					"Total Groups", 1);
		}
		if (e.getSource() == showMessagesTotal) {
			JOptionPane.showMessageDialog(frame, User.getGlobalTweet(),
					"Total number of messages", 1);
		}
		if (e.getSource() == showPositiveTotal) {
			JOptionPane.showMessageDialog(frame, User.getPositiveTweet(),
					"Positive Tweets", 1);
		}

	}

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

	public Hashtable<String, User> getUserTable() {
		return userTable;
	}

	public void valueChanged(TreeSelectionEvent e) {
		selectedLabel = (DefaultMutableTreeNode) (e.getPath().getLastPathComponent());
	}

	public static AdminInterface getInstance() {
		if (instance == null) {
			instance = new AdminInterface();
		}
		return instance;
	}
}
