package xyz.denprog.codefestredopractice.admin.users_list;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.denprog.codefestredopractice.R;

@AndroidEntryPoint
public class UserListWithRecyclerView extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private final List<PendingUserItem> pendingUsers = new ArrayList<>();
    private MyUserRecyclerViewAdapter adapter;
    private UserApprovalViewModel userApprovalViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserListWithRecyclerView() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UserListWithRecyclerView newInstance(int columnCount) {
        UserListWithRecyclerView fragment = new UserListWithRecyclerView();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        userApprovalViewModel = new ViewModelProvider(this).get(UserApprovalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list_with_recycler_view_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            pendingUsers.clear();
            pendingUsers.addAll(userApprovalViewModel.getPendingUserList());
            adapter = new MyUserRecyclerViewAdapter(pendingUsers, (user, isApproved, position) -> {
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                userApprovalViewModel.setApproval(user.requestId, isApproved);
                pendingUsers.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(
                        requireContext(),
                        isApproved ? "User approved." : "User rejected.",
                        Toast.LENGTH_SHORT
                ).show();
            });
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}
