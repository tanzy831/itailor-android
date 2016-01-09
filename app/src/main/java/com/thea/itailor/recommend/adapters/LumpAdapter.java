package com.thea.itailor.recommend.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thea.itailor.R;

import static com.thea.itailor.recommend.app.PreferenceActivity.PlaceholderFragment.SECTION_COLOR_LUMP;

/**
 * Created by Thea on 2015/9/7 0007.
 */
public class LumpAdapter extends RecyclerView.Adapter<LumpAdapter.MyViewHolder> {
    private int sectionNumber;
    private OnItemSelectedListener onItemSelectedListener;

    public LumpAdapter(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_gorgeous, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.iv.setImageResource(sectionNumber == SECTION_COLOR_LUMP ?
                colorLump[position] : abstractImage[position]);
        holder.iv.setOnClickListener(v -> {
            onItemSelectedListener.onItemSelected(v, holder.sign, holder);
        });
    }

    @Override
    public int getItemCount() {
        return colorLump.length;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        View sign;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_gorgeous);
            sign = itemView.findViewById(R.id.iv_selected_sign);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(View v, View sign, RecyclerView.ViewHolder viewHolder);
    }

    int[] colorLump = {R.mipmap.c1_1, R.mipmap.c1_8, R.mipmap.c1_3, R.mipmap.c1_5, R.mipmap.c1_6,
            R.mipmap.c1_2, R.mipmap.c1_7, R.mipmap.c1_4};

    int[] abstractImage = {R.mipmap.c2_1, R.mipmap.c2_2, R.mipmap.c2_6, R.mipmap.c2_5, R.mipmap.c2_3,
            R.mipmap.c2_4, R.mipmap.c2_8, R.mipmap.c2_7};
}
