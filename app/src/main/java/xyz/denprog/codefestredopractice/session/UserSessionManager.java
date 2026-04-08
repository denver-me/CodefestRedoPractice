package xyz.denprog.codefestredopractice.session;

import android.content.Intent;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import xyz.denprog.codefestredopractice.database.LoggedInUserDao;
import xyz.denprog.codefestredopractice.database.entity.LoggedInUser;
import xyz.denprog.codefestredopractice.database.entity.User;

@Singleton
public class UserSessionManager {

    public static final String EXTRA_USER_ID = "xyz.denprog.codefestredopractice.extra.USER_ID";
    public static final String EXTRA_USER_DISPLAY_NAME = "xyz.denprog.codefestredopractice.extra.USER_DISPLAY_NAME";
    public static final String EXTRA_USER_EMAIL = "xyz.denprog.codefestredopractice.extra.USER_EMAIL";
    public static final String EXTRA_USER_IS_ADMIN = "xyz.denprog.codefestredopractice.extra.USER_IS_ADMIN";

    private final MutableLiveData<SessionUser> currentUser = new MutableLiveData<>();
    private final LoggedInUserDao loggedInUserDao;

    @Inject
    public UserSessionManager(LoggedInUserDao loggedInUserDao) {
        this.loggedInUserDao = loggedInUserDao;
    }

    public LiveData<SessionUser> getCurrentUser() {
        return currentUser;
    }

    public SessionUser getCurrentUserValue() {
        return currentUser.getValue();
    }

    public void setCurrentUser(User user) {
        if (user == null) {
            loggedInUserDao.clearLoggedInUser();
            currentUser.setValue(null);
            return;
        }

        SessionUser sessionUser = new SessionUser(
                user.id,
                buildDisplayName(user.firstName, user.lastName, user.email),
                user.email,
                user.isAdmin
        );
        persistSessionUser(sessionUser);
        currentUser.setValue(sessionUser);
    }

    public void restoreFromIntent(Intent intent) {
        if (currentUser.getValue() != null) {
            return;
        }

        if (intent != null) {
            long userId = intent.getLongExtra(EXTRA_USER_ID, -1L);
            String displayName = intent.getStringExtra(EXTRA_USER_DISPLAY_NAME);
            String email = intent.getStringExtra(EXTRA_USER_EMAIL);
            boolean isAdmin = intent.getBooleanExtra(EXTRA_USER_IS_ADMIN, false);

            if (userId > 0L && !TextUtils.isEmpty(displayName)) {
                SessionUser sessionUser = new SessionUser(userId, displayName, email, isAdmin);
                persistSessionUser(sessionUser);
                currentUser.setValue(sessionUser);
                return;
            }
        }

        LoggedInUser loggedInUser = loggedInUserDao.getLoggedInUser();
        if (loggedInUser == null) {
            return;
        }

        currentUser.setValue(new SessionUser(
                loggedInUser.userId,
                loggedInUser.displayName,
                loggedInUser.email,
                loggedInUser.isAdmin
        ));
    }

    public void appendUserToIntent(Intent intent, User user) {
        if (intent == null || user == null) {
            return;
        }

        intent.putExtra(EXTRA_USER_ID, user.id);
        intent.putExtra(EXTRA_USER_DISPLAY_NAME, buildDisplayName(user.firstName, user.lastName, user.email));
        intent.putExtra(EXTRA_USER_EMAIL, user.email);
        intent.putExtra(EXTRA_USER_IS_ADMIN, user.isAdmin);
    }

    public void clearCurrentUser() {
        loggedInUserDao.clearLoggedInUser();
        currentUser.setValue(null);
    }

    private String buildDisplayName(String firstName, String lastName, String email) {
        String safeFirstName = firstName == null ? "" : firstName.trim();
        String safeLastName = lastName == null ? "" : lastName.trim();
        String fullName = (safeFirstName + " " + safeLastName).trim();

        if (!TextUtils.isEmpty(fullName)) {
            return fullName;
        }
        if (!TextUtils.isEmpty(email)) {
            return email;
        }
        return "User";
    }

    private void persistSessionUser(SessionUser sessionUser) {
        LoggedInUser loggedInUser = new LoggedInUser();
        loggedInUser.sessionId = 1;
        loggedInUser.userId = sessionUser.id;
        loggedInUser.displayName = sessionUser.displayName;
        loggedInUser.email = sessionUser.email;
        loggedInUser.isAdmin = sessionUser.isAdmin;
        loggedInUserDao.saveLoggedInUser(loggedInUser);
    }
}
