public class User {
    private String email;
    private String UTORid;
    private String password;
    private Boolean isTeacher;

    public User(String email, String UTORid, String password) {
        this.email = email;
        this.UTORid = UTORid;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object user2) {
        return user2 instanceof User && ((User) user2).email.equals(this.email);
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n", email, password);
    }
}
