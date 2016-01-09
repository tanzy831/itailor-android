package com.thea.itailor.personal.app;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.app.LoginActivity;
import com.thea.itailor.community.app.MyFriendsActivity;
import com.thea.itailor.community.app.PursuersActivity;
import com.thea.itailor.community.app.ShareInfoActivity;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.recommend.app.BodyDataActivity;
import com.thea.itailor.recommend.app.FavouritesActivity;
import com.thea.itailor.recommend.app.PreferenceActivity;
import com.thea.itailor.util.HttpUtil;
import com.thea.itailor.util.ImageHelper;

import org.apache.http.Header;
import org.apache.http.entity.FileEntity;
import org.apache.http.message.BasicHeader;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;


public class PersonalFragment extends Fragment {
    private static final String TAG = "PersonalFragment";

    private IUserModel mUserModel;

    private View mView;
    private CircleImageView headIcon;
    private TextView username;

    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PersonalFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_personal, container, false);

        mUserModel = new UserModel(getActivity());

        initViews(mView);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        headIcon = (CircleImageView) mView.findViewById(R.id.civ_user_icon);
        username = (TextView) mView.findViewById(R.id.tv_user_name);
        TextView friendsCount = (TextView) mView.findViewById(R.id.tv_friends_count);
        Button editNick = (Button) mView.findViewById(R.id.btn_edit);
        if (mUserModel.getLoginState()) {
            headIcon.setImageBitmap(ImageHelper.getImageFromStore(Constant.DIRECTORY_USER,
                    "/" + mUserModel.getCurPortrait()));
//            headIcon.setImageResource(R.mipmap.head_icon);
            username.setText(mUserModel.getCurUserName());
            friendsCount.setText("");
            editNick.setVisibility(View.VISIBLE);
        }
        else {
            headIcon.setImageResource(R.mipmap.head_icon);
            username.setText(R.string.before_login);
            friendsCount.setText("");
            editNick.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
//            headIcon.setImageBitmap(ImageHelper.getImageFromStore(Constant.DIRECTORY_USER,
//                    "/" + mUserModel.getCurPortrait()));
            mUserModel.setCurPortrait(Constant.CUR_PORTRAIT);
            Header[] headers = new Header[1];
            headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
            String path = Path.BASE_PATH + Constant.DIRECTORY_USER + "/" + Constant.CUR_PORTRAIT;
            FileEntity entity = new FileEntity(new File(path), ContentType.APPLICATION_OCTET_STREAM);
            HttpUtil.post(getActivity(), mUserModel.getBaseUrl() + Path.PORTRAIT + "?accountID=" +
                    mUserModel.getAccountID() + "&imageID=account" + mUserModel.getAccountID()
                    + "_portrait.jpg", headers, null, ContentType.APPLICATION_JSON,
                    new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess:" + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure:" + statusCode);
                }
            });
            HttpUtil.post(getActivity(), mUserModel.getBaseUrl() + Path.IMAGE_SERVER + "?accountID=" +
                    mUserModel.getAccountID() + "&imageID=account" + mUserModel.getAccountID()
                    + "_portrait.jpg", headers, entity, ContentType.APPLICATION_OCTET_STREAM,
                    new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess:" + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure:" + statusCode);
                }
            });
        }
    }

    public void initViews(View view) {
        view.findViewById(R.id.rl_user_control).setOnClickListener(clickListener);
        view.findViewById(R.id.btn_edit).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_my_friends).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_pursuers).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_body_data).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_test_preferences).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_shared).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_my_favourites).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_realsense_manage).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_settings).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_feedback).setOnClickListener(clickListener);
        view.findViewById(R.id.rl_about_us).setOnClickListener(clickListener);
        view.findViewById(R.id.btn_exit).setOnClickListener(clickListener);

        view.findViewById(R.id.civ_user_icon).setOnLongClickListener(v -> {
            if (!mUserModel.getLoginState())
                return false;
            Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = ImageHelper.createNewFile(Constant.DIRECTORY_USER, "/" + Constant.CUR_PORTRAIT);
            Uri uri = Uri.fromFile(file);
            imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(imageCaptureIntent, Constant.REQUEST_CAMERA);
            return true;
        });
    }

    public void skipToUserInfo() {
        startActivity(new Intent(getActivity(), UserInfoActivity.class));
    }

    public void editNick() {
        final EditText name = new EditText(getActivity());
        name.setText(username.getText());
        name.setEnabled(true);
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        name.setSelection(name.getText().length());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("昵称").setView(name).setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.done, (dialog, which) -> {
                mUserModel.setCurUserName(name.getText().toString());
                username.setText(name.getText().toString());
            }).show();
    }

    public void skipToLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    public void skipToMyFriends() {
        startActivity(new Intent(getActivity(), MyFriendsActivity.class));
    }

    public void skipToPursuers() {
        startActivity(new Intent(getActivity(), PursuersActivity.class));
    }

    public void skipToBodyData() {
        startActivity(new Intent(getActivity(), BodyDataActivity.class));
    }

    public void skipToPreference() {
        startActivity(new Intent(getActivity(), PreferenceActivity.class));
    }

    public void skipToFavourites() {
        startActivity(new Intent(getActivity(), FavouritesActivity.class));
    }

    public void skipToShare() {
        startActivity(new Intent(getActivity(), ShareInfoActivity.class));
    }

    public void skipToSettings() {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }

    public void skipToFeedback() {
        startActivity(new Intent(getActivity(), FeedbackActivity.class));
    }

    public void skipToAboutUs() {
        startActivity(new Intent(getActivity(), AboutUsActivity.class));
    }

    public void exit() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.rl_user_control:
                if (!mUserModel.getLoginState())
                    skipToLogin();
                break;
            case R.id.btn_edit:
                if (mUserModel.getLoginState())
                    editNick();
                break;
            case R.id.rl_my_friends:
                if (mUserModel.getLoginState())
                    skipToMyFriends();
                break;
            case R.id.rl_pursuers:
                if (mUserModel.getLoginState())
                    skipToPursuers();
                break;
            case R.id.rl_body_data:
                skipToBodyData();
                break;
            case R.id.rl_test_preferences:
                skipToPreference();
                break;
            case R.id.rl_my_favourites:
                skipToFavourites();
                break;
            case R.id.rl_shared:
                skipToShare();
                break;
            case R.id.rl_realsense_manage:
                Toast.makeText(getActivity(), "无可用连接设备", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_settings:
                skipToSettings();
                break;
            case R.id.rl_feedback:
                skipToFeedback();
                break;
            case R.id.rl_about_us:
                skipToAboutUs();
                break;
            case R.id.btn_exit:
                exit();
                break;
        }
    };
}
