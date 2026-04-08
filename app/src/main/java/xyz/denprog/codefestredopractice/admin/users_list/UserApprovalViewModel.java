package xyz.denprog.codefestredopractice.admin.users_list;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import xyz.denprog.codefestredopractice.database.dao.UserDao;

@HiltViewModel
public class UserApprovalViewModel extends ViewModel {

    private final UserDao userDao;

    @Inject
    public UserApprovalViewModel(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<PendingUserItem> getPendingUserList() {
        return userDao.getAllPendingUserList();
    }

    public void setApproval(long requestId, boolean isApproved) {
        userDao.updateUserRequestApproval(requestId, isApproved);
    }
}
