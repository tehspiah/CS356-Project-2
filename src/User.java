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

/*
 * Class that is the Users themselves and the GUI
 */
public class User extends Observable implements Observer, Component {

	private String id;
	private List follower, following, tweet;
	JList<String> feed, followingWindow;

	public User(String userID) {
		id = userID;
		follower = new ArrayList();
		following = new ArrayList();
		tweet = new ArrayList();

	}
	//starts up the user interface
	public void generateUI() {
		new UserInterface(this);
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

	/*
	 * converts the following list to a default list model to be show on the GUI
	 */
	public DefaultListModel<String> getFollowingList() {
		DefaultListModel<String> newList = new DefaultListModel<String>();
		for (int i = 0; i < following.size(); i++)
			newList.addElement(following.get(i).toString());
		return newList;
	}

	/*
	 * converts the tweet list to a default list model to be shown on the GUI
	 */
	public DefaultListModel<String> getNewsFeed() {
		DefaultListModel<String> newList = new DefaultListModel<String>();
		for (int i = 0; i < tweet.size(); i++)
			newList.addElement(tweet.get(i).toString());
		return newList;
	}

	/*
	 * return the list of tweets made by the user
	 */
	public List getTweetList() {
		return tweet;
	}

	/*
	 * gets the latest tweet from the user's list of tweets
	 */
	public String getTweet() {
		return tweet.get(tweet.size() - 1).toString();
	}

	/*
	 * adds tweet to the user tweet list
	 */
	public void addTweet(String message) {
		tweet.add(message);
	}

	/*
	 * checks duplicate IDs, currently unimplemented
	 */
	public boolean checkId(String check) {
		if (check.equals(id))
			return true;
		else
			return false;
	}

	/*
	 * attach observer to the user
	 */
	public void attach(Observer o) {
		follower.add(o);
	}

	/*
	 * notify the observer is tweets have been added.
	 */
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

	/*
	 * GUI is now coded within the user class, as this allows it to access the
	 * user object more easily and I had a problem with my previous design of having
	 * a seperate class parsing in the user.
	 */
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

	@Override
	public void accept(ComponentVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visitUser(this);
	}

}
