package com.thea.itailor.recommend.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.Check;
import com.thea.itailor.R;
import com.thea.itailor.community.app.ShareActivity;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.ExtraName;
import com.thea.itailor.config.Path;
import com.thea.itailor.recommend.adapters.RecommendAdapter;
import com.thea.itailor.recommend.app.ColorImageActivity;
import com.thea.itailor.recommend.bean.ClothingImage;
import com.thea.itailor.recommend.bean.Feedback;
import com.thea.itailor.recommend.bean.FeedbackJson;
import com.thea.itailor.recommend.bean.RecommendItem;
import com.thea.itailor.recommend.bean.Suit;
import com.thea.itailor.recommend.model.FavouritesModel;
import com.thea.itailor.recommend.model.IFavouritesModel;
import com.thea.itailor.recommend.view.IRecommendView;
import com.thea.itailor.util.HttpUtil;
import com.thea.itailor.util.ImageHelper;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.thea.itailor.R.id.tv_cloth_info;

/**
 * Created by Thea on 2015/8/18 0018.
 */
public class RecommendPresenter {
    private static final String TAG = "RecommendPresenter";

    private Activity context;

    private IRecommendView mRecommendView;
    private IFavouritesModel mFavouritesModel;
    private IUserModel mUserModel;
    private IImageModel mImageModel;

    private RecommendAdapter mAdapter;

    private int grade = 0;
    private List<Feedback> feedbacks = new ArrayList<>();

    public RecommendPresenter(Activity context, IRecommendView mRecommendView) {
        this.context = context;
        this.mRecommendView = mRecommendView;
        mFavouritesModel = new FavouritesModel(context);
        mUserModel = new UserModel(context);
        mImageModel = new ImageModel(context);
        mAdapter = new RecommendAdapter(context);
    }

    public void loadRecommends() {
//        mAdapter = new RecommendAdapter(context, new ArrayList<>());
        mRecommendView.setPagerAdapter(mAdapter);
    }

    public void loadData() {
        if (Check.checkForLogin(context)) {
//            mRecommendView.showLoadingView();
            Header[] headers = new Header[1];
            headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
            HttpUtil.get(context,  mUserModel.getBaseUrl() + Path.RECOMMEND + "?accountID=" +
                    mUserModel.getAccountID(), headers, null, httpResponseHandler);
        }
    }

    public void sendFeedback() {
        if (feedbacks.size() <= 0)
            return;
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());

        try {
            StringEntity entity = new StringEntity(
                    new Gson().toJson(new FeedbackJson(feedbacks), FeedbackJson.class), HttpUtil.ENCODING);
            HttpUtil.post(context, mUserModel.getBaseUrl() + Path.RECOMMEND + "?accountID=" +
                    mUserModel.getAccountID(), headers, entity, ContentType.APPLICATION_JSON, httpResponseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private JsonHttpResponseHandler httpResponseHandler = new JsonHttpResponseHandler(HttpUtil.ENCODING) {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            Log.i(TAG, "onSuccess: " + statusCode + response.toString());
            mAdapter.setItems(getViewsFormJson(response));
//            mRecommendView.hideLoadingView();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Log.i(TAG, "onFailure: " + statusCode);
//            if (Check.checkForLogin(context, statusCode))
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
        }
    };

    public ArrayList<View> getViewsFormJson(JSONArray jsonArray) {
        ArrayList<View> views = new ArrayList<>();

        Type listType = new TypeToken<ArrayList<RecommendItem>>(){}.getType();
        ArrayList<RecommendItem> items = new Gson().fromJson(jsonArray.toString(), listType);
        ArrayList<Suit> suits = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Suit suit = new Suit();
            if (items.get(i).isDress())
                suit.addItem(items.get(i));
            else {
                suit.addItem(items.get(i));
                i++;
                Log.i(TAG, i + "");
                suit.addItem(items.get(i));
            }
            suits.add(suit);
        }

        for (int i = 0; i < suits.size(); i++)
            views.add(generateView(i, suits.get(i)));

        return views;
    }

    public View generateView(int position, Suit suit) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, null);

        ImageView seal = (ImageView) view.findViewById(R.id.iv_itailor_seal);
        seal.setImageResource(gradeSeals[position]);

        List<RecommendItem> recommendItems = suit.getItems();
        List<ClothingImage> images = recommendItems.get(0).getCloth().getItem().getClothingImages();
        final ImageView iv = (ImageView) view.findViewById(R.id.iv_image);
        iv.setOnClickListener(v1 -> seeColorImages(v1, images));
        mImageModel.getImage(images.get(0).getImageID(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "图片onSuccess: " + statusCode);
                iv.setImageBitmap(ImageHelper.getImageFromByte(responseBody, 1));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "图片onFailure: " + statusCode);
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.ib_collect).setOnClickListener(v -> onCollect(v, recommendItems.get(0)));
        view.findViewById(R.id.ib_share).setOnClickListener(v -> onShare(
                recommendItems.get(0).getRecommendID(), images.get(0).getImageID()));
        view.findViewById(R.id.ib_shop).setOnClickListener(v -> onShopGuide(v, recommendItems.get(0)));

        if (recommendItems.size() > 1) {
            List<ClothingImage> images2 = recommendItems.get(0).getCloth().getItem().getClothingImages();
            final ImageView iv2 = (ImageView) view.findViewById(R.id.iv_image_pant);
            iv2.setOnClickListener(v2 -> seeColorImages(v2, images));
            mImageModel.getImage(images2.get(0).getImageID(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess: " + statusCode);
                    iv2.setImageBitmap(ImageHelper.getImageFromByte(responseBody, 1));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure: " + statusCode);
                    Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
                }
            });
            view.findViewById(R.id.ib_collect_pant).setOnClickListener(
                    v -> onCollect(v, recommendItems.get(1)));
            view.findViewById(R.id.ib_share_pant).setOnClickListener(
                    v -> onShare(recommendItems.get(1).getRecommendID(), images.get(1).getImageID()));
        } else {
            view.findViewById(R.id.rl_suit_pant).setVisibility(View.GONE);
        }

        TextView reason = (TextView) view.findViewById(R.id.tv_suit_description);
        reason.setText(recommendItems.get(0).getReason());
        if (recommendItems.size() > 1)
            reason.append("/n" + recommendItems.get(1).getReason());

        /*LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_labels);
        for (RecommendItem item : recommendItems) {
            String[] labels = item.getReason().split("[ ]+");
            for (int i = 0; i < labels.length; i++) {
                Log.i(TAG, labels[i]);
                View labelView = LayoutInflater.from(context).inflate(R.layout.item_label, ll, false);
                ImageView bg = (ImageView) labelView.findViewById(R.id.iv_label_background);
                TextView tv = (TextView) labelView.findViewById(R.id.tv_label);
                bg.setImageResource(label_backgrounds[new Random().nextInt(3)]);
                tv.setText(labels[i]);
                ll.addView(labelView);
            }
        }*/

        return view;
    }

    public void onCollect(final View v, final RecommendItem item) {
        int tag = Integer.valueOf((String) v.getTag());
        final ImageButton imageButton = (ImageButton) v;
        if (tag == 0) {
            mFavouritesModel.addItem(item.getRecommendID(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess: " + statusCode);
                    imageButton.setImageResource(R.mipmap.ic_favorite_black_24dp);
                    v.setTag(1 + "");
                    onGrade(item);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure: " + statusCode);
//                    if (Check.checkForLogin(context, statusCode))
                        Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            mFavouritesModel.deleteItem(item.getRecommendID(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess: " + statusCode);
                    imageButton.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
                    v.setTag(0 + "");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure: " + statusCode);
//                    if (Check.checkForLogin(context, statusCode))
                        Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onShare(int recommendID, String imageID) {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra(ExtraName.RECOMMEND_ID, recommendID);
        intent.putExtra(ExtraName.IMAGE_NAME, imageID);
        context.startActivity(intent);
    }

    public void onGrade(RecommendItem item) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_grade, null);

        ImageView iv1 = (ImageView) view.findViewById(R.id.iv_love1);
        ImageView iv2 = (ImageView) view.findViewById(R.id.iv_love2);
        ImageView iv3 = (ImageView) view.findViewById(R.id.iv_love3);
        ImageView iv4 = (ImageView) view.findViewById(R.id.iv_love4);
        ImageView iv5 = (ImageView) view.findViewById(R.id.iv_love5);

        View.OnClickListener clickListener = v1 -> {
            switch (v1.getId()) {
                case R.id.iv_love1:
                    grade = setGrade(1, iv1, iv2, iv3, iv4, iv5);
                    break;
                case R.id.iv_love2:
                    grade = setGrade(2, iv1, iv2, iv3, iv4, iv5);
                    break;
                case R.id.iv_love3:
                    grade = setGrade(3, iv1, iv2, iv3, iv4, iv5);
                    break;
                case R.id.iv_love4:
                    grade = setGrade(4, iv1, iv2, iv3, iv4, iv5);
                    break;
                case R.id.iv_love5:
                    grade = setGrade(5, iv1, iv2, iv3, iv4, iv5);
                    break;
            }
        };

        iv1.setOnClickListener(clickListener);
        iv2.setOnClickListener(clickListener);
        iv3.setOnClickListener(clickListener);
        iv4.setOnClickListener(clickListener);
        iv5.setOnClickListener(clickListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("赏个评分吧").setView(view).setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.affirm, (dialog, which) -> {
                feedbacks.add(new Feedback(item.getCloth().getClothID(), grade));
                grade = 0;
            }).show();
    }

    public int setGrade(int grade, ImageView iv1, ImageView iv2, ImageView iv3,ImageView iv4, ImageView iv5) {
        iv1.setImageResource(grade >= 1 ? R.mipmap.ic_favorite_black_24dp :
                R.mipmap.ic_favorite_border_black_24dp);
        iv2.setImageResource(grade >= 2 ? R.mipmap.ic_favorite_black_24dp :
                R.mipmap.ic_favorite_border_black_24dp);
        iv3.setImageResource(grade >= 3 ? R.mipmap.ic_favorite_black_24dp :
                R.mipmap.ic_favorite_border_black_24dp);
        iv4.setImageResource(grade >= 4 ? R.mipmap.ic_favorite_black_24dp :
                R.mipmap.ic_favorite_border_black_24dp);
        iv5.setImageResource(grade >= 5 ? R.mipmap.ic_favorite_black_24dp :
                R.mipmap.ic_favorite_border_black_24dp);
        return grade;
    }

    public void onShopGuide(View v, RecommendItem item) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_cloth_info, null);
        TextView info = (TextView) view.findViewById(tv_cloth_info);
        info.setText(item.getReason());
        AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.MyBottomViewStyle);
        dialog.show();
    }

    public void seeColorImages(View v, List<ClothingImage> images) {
        ArrayList<String> list = new ArrayList<>();
        for (ClothingImage image : images)
            list.add(image.getImageID());

        Intent intent = new Intent(context, ColorImageActivity.class);
        intent.putExtra(ExtraName.IMAGE_NAMES, list);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.grow_fade_in_from_top, R.anim.shrink_fade_out_from_bottom);
    }

    int[] gradeSeals = {R.mipmap.grade1, R.mipmap.grade2, R.mipmap.grade3, R.mipmap.grade3,
            R.mipmap.grade3, R.mipmap.grade3};

    int[] label_backgrounds = {R.mipmap.label_background1, R.mipmap.label_background2, R.mipmap.label_background3};
}
