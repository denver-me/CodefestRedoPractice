package xyz.denprog.codefestredopractice.viewmodel;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import xyz.denprog.codefestredopractice.database.dao.UserDao;
import xyz.denprog.codefestredopractice.database.entity.User;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final UserDao userDao;

    @Inject
    public LoginViewModel(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String email, String password) {
        return userDao.login(email, password);
    }
}
