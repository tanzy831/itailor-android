package com.thea.itailor.community.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thea.itailor.R;
import com.thea.itailor.community.bean.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/7.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private List<Account> friends = new ArrayList<>();
    private OnItemSelectedListener onItemSelectedListener;

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        Account friend = friends.get(position);
        holder.icon.setImageResource(R.mipmap.head_icon);
        holder.name.setText(friend.getEmail());
        holder.itemView.setOnLongClickListener(v -> {
            onItemSelectedListener.onItemSelected(v, holder, friend);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void addItem(Account item) {
        friends.add(item);
        notifyItemInserted(friends.size() - 1);
    }

    public void addItem(int position, Account item) {
        friends.add(position, item);
        notifyItemInserted(position);
    }

    public void addItems(List<Account> items) {
        int form = friends.size();
        friends.addAll(items);
        notifyItemRangeInserted(form, items.size());
    }

    public void removeItem(int position) {
        friends.remove(position);
        notifyItemRemoved(position);
    }

    public Account getItem(int position) {
        return friends.get(position);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;

        public FriendViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.civ_friend_icon);
            name = (TextView) itemView.findViewById(R.id.tv_friend_name);
        }
    }

    public interface OnItemSelectedListener {

        void onItemSelected(View v, RecyclerView.ViewHolder holder, Account item);
    }
}
