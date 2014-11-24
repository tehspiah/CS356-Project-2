import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class User extends Observable implements Observer, Component {

	private static int userCount, globalTweet, positiveTweet;
	private String id;
	private List follower, following, tweet;
	JList<String> feed, followingWindow;

	public User(String userID) {
		id = userID;
		userCount++;
		follower = new ArrayList();
		following = new ArrayList();
		tweet = new ArrayList();

	}

	public void generateUI() {
		new UserInterface(this);
	}

	public static int getUserCount() {
		return userCount;
	}

	public static int getGlobalTweet() {
		return globalTweet;
	}

	public static int getPositiveTweet() {
		return positiveTweet;
	}

	public List getFollower() {
		return follower;
	}

	public void addFollower(User u) {
		follower.add(u);
	}

	public List getFollowing() {
		return following;
	}

	public void addFollowing(String person) {
		following.add(person);
	}

	public DefaultListModel<String> getFollowingList() {
		DefaultListModel<String> newList = new DefaultListModel<String>();
		for (int i = 0; i < following.size(); i++)
			newList.addElement(following.get(i).toString());
		return newList;
	}

	public DefaultListModel<String> getNewsFeed() {
		DefaultListModel<String> newList = new DefaultListModel<String>();
		for (int i = 0; i < tweet.size(); i++)
			newList.addElement(tweet.get(i).toString());
		return newList;
	}

	public String getTweet() {
		return tweet.get(tweet.size() - 1).toString();
	}

	public void addTweet(String message) {
		tweet.add(message);
		globalTweet++;
		if (message.contains("good") || message.contains("happy")
				|| message.contains("excelent")) {
			positiveTweet++;
		}
	}

	/*
	 * checks duplicate IDs
	 */
	public boolean checkId(String check) {
		if (check.equals(id))
			return true;
		else
			return false;
	}

	public String toString() {
		return id;
	}

	public void attach(Observer o) {
		follower.add(o);
	}

	public void notifyObserver() {
		for (int i = 0; i < follower.size(); i++) {
			((User) follower.get(i)).update(this, this.getTweet());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		addTweet(arg.toString());
		feed.setModel(getNewsFeed());
	}

	public class UserInterface implements ActionListener {

		JFrame frame;
		JPanel panel1, panel2, panel3, panel4;
		JButton followUser, postTweet;

		JTextArea id, message;
		User u;

		UserInterface(User user) {
			u = user;
			frame = new JFrame();
			frame.getContentPane().setLayout(new FlowLayout());
			frame.setTitle("User Panel");
			frame.setSize(240, 280);
			frame.setLocationRelativeTo(null);
			followUser = new JButton("Follow User");
			postTweet = new JButton("Post Tweet");

			panel1 = new JPanel(new GridLayout(1, 2));
			panel2 = new JPanel(new GridLayout(1, 1));
			panel3 = new JPanel(new GridLayout(1, 2));
			panel4 = new JPanel(new GridLayout(1, 1));

			id = new JTextArea();
			message = new JTextArea();
			followingWindow = new JList<String>(u.getFollowingList());
			feed = new JList<String>(u.getNewsFeed());

			JScrollPane scroll = new JScrollPane(feed);
			JScrollPane scroll2 = new JScrollPane(followingWindow);

			followUser.addActionListener(this);
			postTweet.addActionListener(this);

			panel1.add(id);
			panel1.add(followUser);
			frame.add(panel1);

			panel2.add(scroll2);
			panel2.setPreferredSize(new Dimension(240, 80));
			frame.add(panel2);

			panel3.add(message);
			panel3.add(postTweet);
			frame.add(panel3);

			panel4.add(scroll);
			panel4.setPreferredSize(new Dimension(240, 80));
			frame.add(panel4);

			frame.setVisible(true);

		}

		public void actionPerformed(ActionEvent e) {
			String temp;
			if (postTweet == e.getSource()) {
				temp = message.getText();
				u.addTweet(temp);
				message.setText("");
				u.notifyObserver();
				feed.setModel(u.getNewsFeed());
			}

			if (followUser == e.getSource()) {
				u.addFollowing(id.getText());
				followingWindow.setModel(u.getFollowingList());
				AdminInterface.getInstance().getUserTable().get(id.getText())
						.addFollower(u);
				id.setText("");
			}

		}
	}

}
