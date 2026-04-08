package xyz.denprog.codefestredopractice.viewmodel;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import xyz.denprog.codefestredopractice.database.dao.UserDao;
import xyz.denprog.codefestredopractice.database.entity.User;
import xyz.denprog.codefestredopractice.database.entity.UserRequests;

@HiltViewModel
public class RegisterViewModel extends ViewModel {

    private final UserDao userDao;

    @Inject
    public RegisterViewModel(
            UserDao userDao
    ) {
        this.userDao = userDao;
    }

    public void registerUser(User user) {
        UserRequests userRequest = new UserRequests();
        userRequest.id = user.id;
        userRequest.isApproved = null;
        userDao.insertUserRequest(userRequest);
        userDao.insertUser(user);
    }

}
