package xyz.denprog.codefestredopractice.admin.users_list;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import xyz.denprog.codefestredopractice.databinding.FragmentUserListWithRecyclerViewBinding;

import java.util.List;

public class MyUserRecyclerViewAdapter extends RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder> {

    public interface OnApprovalActionListener {
        void onAction(PendingUserItem user, boolean isApproved, int position);
    }

    private final List<PendingUserItem> mValues;
    private final OnApprovalActionListener onApprovalActionListener;

    public MyUserRecyclerViewAdapter(List<PendingUserItem> items, OnApprovalActionListener onApprovalActionListener) {
        mValues = items;
        this.onApprovalActionListener = onApprovalActionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentUserListWithRecyclerViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(holder.mItem.id));
        holder.mContentView.setText(holder.mItem.getFullName() + "\n" + holder.mItem.email);
        holder.approveButton.setOnClickListener(v -> onApprovalActionListener.onAction(holder.mItem, true, holder.getBindingAdapterPosition()));
        holder.rejectButton.setOnClickListener(v -> onApprovalActionListener.onAction(holder.mItem, false, holder.getBindingAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final Button approveButton;
        public final Button rejectButton;
        public PendingUserItem mItem;

        public ViewHolder(FragmentUserListWithRecyclerViewBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            approveButton = binding.approveButton;
            rejectButton = binding.rejectButton;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
