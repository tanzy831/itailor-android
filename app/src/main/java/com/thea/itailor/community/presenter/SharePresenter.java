package com.thea.itailor.community.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.thea.itailor.R;
import com.thea.itailor.armoire.view.AnimateFirstDisplayListener;
import com.thea.itailor.community.bean.Image;
import com.thea.itailor.community.bean.ShareItem;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.IShareModel;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.community.model.ShareModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.community.view.IShareView;
import com.thea.itailor.config.Path;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Thea on 2015/8/28 0028.
 */
public class SharePresenter {
    private static final String TAG = "SharePresenter";

    private Activity activity;

    private IShareView mShareView;
    private IUserModel mUserModel;
    private IShareModel mShareModel;
    private IImageModel mImageModel;
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<Image> mShareImages = new ArrayList<>();

    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageLoadingListener imageLoadingListener = new AnimateFirstDisplayListener();

    public SharePresenter(Activity activity, IShareView shareView) {
        this.activity = activity;
        this.mShareView = shareView;
        mShareModel = new ShareModel(activity);
        mUserModel = new UserModel(activity);
        mImageModel = new ImageModel(activity);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.image1)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public void addImage(ArrayList<String> imageNames) {
        for (int i = 0; i < imageNames.size(); i++)
            addImage(imageNames.get(i));
    }

    public void addImage(String imageName) {
        ImageView imageView = (ImageView) LayoutInflater.from(activity)
                .inflate(R.layout.image_view, mShareView.getImageParent(), false);
        String uri = "file://" + Path.BASE_PATH + Path.DIRECTORY_ARMOIRE + "/" + imageName;
        imageLoader.displayImage(uri, imageView, options, imageLoadingListener);
        mShareView.addShareImage(imageView, mShareView.getImageCount());
        mImageNames.add(imageName);
        mShareImages.add(new Image("account" + mUserModel.getAccountID() + "_" + imageName));
    }

    public void addRecommendImage(final String imageID) {
        final ImageView iv = (ImageView) LayoutInflater.from(activity)
                .inflate(R.layout.image_view, mShareView.getImageParent(), false);
        mImageModel.getImage(imageID, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                iv.setImageBitmap(BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length));
                mShareView.addShareImage(iv, mShareView.getImageCount());
                mShareImages.add(new Image(imageID));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
//                if (Check.checkForLogin(activity, statusCode))
                    Toast.makeText(activity, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

   public void send() {
       ShareItem item = new ShareItem();
       item.setDescription(mShareView.getDescription());
       item.setImageJsons(mShareImages);

       mShareModel.addShareItem(item, new AsyncHttpResponseHandler() {
           @Override
           public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               Log.i(TAG, "onSuccess: " + statusCode);
               Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
               activity.onBackPressed();
           }

           @Override
           public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
               Log.i(TAG, "onFailure: " + statusCode);
//               if (Check.checkForLogin(activity, statusCode))
                   Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
           }
       });
   }

    public void sendImages() {
        for (int i = 0; i < mImageNames.size(); i++) {
            mImageModel.sendImage(mImageNames.get(i), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "成功" + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "失败" + statusCode);
                }
            });
        }
    }
}
