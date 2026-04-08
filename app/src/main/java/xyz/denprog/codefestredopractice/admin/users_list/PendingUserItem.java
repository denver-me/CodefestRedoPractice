package xyz.denprog.codefestredopractice.admin.users_list;

public class PendingUserItem {
    public long requestId;
    public long id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String email;
    public String password;
    public boolean isAdmin;

    public String getFullName() {
        StringBuilder builder = new StringBuilder();
        if (firstName != null && !firstName.isEmpty()) {
            builder.append(firstName);
        }
        if (middleName != null && !middleName.isEmpty()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(middleName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(lastName);
        }
        return builder.toString().trim();
    }
}
