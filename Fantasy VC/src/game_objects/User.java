package game_objects;

/**
 * The {@code User} class stores information about the logged
 * in user, using data queried from the MySQL database.
 *
 * @author alancoon
 * 
 */
public class User {

	private int id;
	private String password;
	private String username;

	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return The user's ID from the MySQL data table.
	 */
	public synchronized int getId() {
		return id;
	}

	/**
	 * Well that's dangerous! Let's make that private.
	 * Or better yet let's delete it entirely.
	 * @return The user's unhashed password.
	 */
	private synchronized String getPassword() {
		return password;
	}

	/**
	 * @return The user's username.
	 */
	public synchronized String getUsername() {
		return username;
	}
}
