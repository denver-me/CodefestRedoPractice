package xyz.denprog.codefestredopractice;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import xyz.denprog.codefestredopractice.database.dao.UserDao;
import xyz.denprog.codefestredopractice.database.entity.User;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {

    UserDao userDao;
    @Inject
    public MainActivityViewModel(UserDao userDao) {
        this.userDao = userDao;
    }

    boolean isAdminExist() {
        return userDao.isAdminUserExist() != null;
    }

    public void insertAdminAccount() {
        User adminUser = new User();
        adminUser.id = 1L;
        adminUser.firstName = "Default";
        adminUser.middleName = "";
        adminUser.lastName = "Admin";
        adminUser.email = "admin@codefest.local";
        adminUser.password = "admin123";
        adminUser.isAdmin = true;

        userDao.insertUser(adminUser);
    }
}
