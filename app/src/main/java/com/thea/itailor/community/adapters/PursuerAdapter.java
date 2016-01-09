package com.thea.itailor.community.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.bean.Account;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.util.ImageHelper;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/9/4 0004.
 */
public class PursuerAdapter extends RecyclerView.Adapter<PursuerAdapter.PursuerViewHolder> {
    private static final String TAG = "PursuerAdapter";

    private List<Account> pursuers = new ArrayList<>();
    private OnResponsePursueListener onResponsePursueListener;

    private IImageModel mImageModel;


    @Override
    public PursuerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mImageModel = new ImageModel(parent.getContext());
        return new PursuerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pursuer, parent, false));
    }

    @Override
    public void onBindViewHolder(PursuerViewHolder holder, int position) {
        Account pursuer = pursuers.get(position);
        holder.icon.setImageResource(R.mipmap.head_icon);
        mImageModel.getImage(pursuer.getPortrait(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
//                Bitmap bitmap = ImageHelper.getImageFromByte(responseBody);
                holder.icon.setImageBitmap(ImageHelper.getImageFromByte(responseBody));
//                bitmap.recycle();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
            }
        });

        holder.name.setText(pursuer.getEmail());
        holder.acceptBtn.setOnClickListener(v ->
                onResponsePursueListener.onAccept(v, holder, pursuer.getEmail()));
        holder.rejectBtn.setOnClickListener(v ->
                onResponsePursueListener.onReject(v, holder, pursuer.getEmail()));
    }

    @Override
    public int getItemCount() {
        return pursuers.size();
    }

    public void addItem(Account item) {
        pursuers.add(item);
        notifyItemInserted(pursuers.size() - 1);
    }

    public void addItem(int position, Account item) {
        pursuers.add(position, item);
        notifyItemInserted(position);
    }

    public void addItems(List<Account> items) {
        int form = pursuers.size();
        pursuers.addAll(items);
        notifyItemRangeInserted(form, items.size());
    }

    public void removeItem(int position) {
        pursuers.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnResponsePursueListener(OnResponsePursueListener onResponsePursueListener) {
        this.onResponsePursueListener = onResponsePursueListener;
    }

    public class PursuerViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;
        public View acceptBtn;
        public View rejectBtn;

        public PursuerViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.civ_pursuer_icon);
            name = (TextView) itemView.findViewById(R.id.tv_pursuer_name);
            acceptBtn = itemView.findViewById(R.id.btn_accept_pursue);
            rejectBtn = itemView.findViewById(R.id.btn_reject_pursue);
        }
    }

    public interface OnResponsePursueListener {
        void onAccept(View v, RecyclerView.ViewHolder holder, String email);

        void onReject(View v, RecyclerView.ViewHolder holder, String email);
    }
}
