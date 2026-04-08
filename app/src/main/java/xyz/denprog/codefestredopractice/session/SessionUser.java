package xyz.denprog.codefestredopractice.session;

public class SessionUser {
    public final long id;
    public final String displayName;
    public final String email;
    public final boolean isAdmin;

    public SessionUser(long id, String displayName, String email, boolean isAdmin) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.isAdmin = isAdmin;
    }
}
