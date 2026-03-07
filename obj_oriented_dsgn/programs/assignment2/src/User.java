public class User implements Comparable<User> {
    private final String username;
    private final String password;
    private final boolean admin;

    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() { return username; }
    public boolean isAdmin() { return admin; }

    public boolean equals(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public boolean equals(String username) {
        return this.username.equals(username);
    }

    public int compareTo(User other) {
        return username.compareTo(other.username);
    }

    public String toString() {
        return username + "," +  password + "," + (admin ? 1 : 0);
    }
}
