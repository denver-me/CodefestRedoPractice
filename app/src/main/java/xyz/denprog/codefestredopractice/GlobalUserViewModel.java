package xyz.denprog.codefestredopractice;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import xyz.denprog.codefestredopractice.database.entity.User;
import xyz.denprog.codefestredopractice.session.SessionUser;
import xyz.denprog.codefestredopractice.session.UserSessionManager;

@HiltViewModel
public class GlobalUserViewModel extends ViewModel {

    private final UserSessionManager userSessionManager;

    @Inject
    public GlobalUserViewModel(UserSessionManager userSessionManager) {
        this.userSessionManager = userSessionManager;
    }

    public LiveData<SessionUser> getCurrentUser() {
        return userSessionManager.getCurrentUser();
    }

    public SessionUser getCurrentUserValue() {
        return userSessionManager.getCurrentUserValue();
    }

    public void setCurrentUser(User user) {
        userSessionManager.setCurrentUser(user);
    }

    public void restoreCurrentUser(Intent intent) {
        userSessionManager.restoreFromIntent(intent);
    }

    public void appendCurrentUser(Intent intent, User user) {
        userSessionManager.appendUserToIntent(intent, user);
    }

    public void clearCurrentUser() {
        userSessionManager.clearCurrentUser();
    }
}
