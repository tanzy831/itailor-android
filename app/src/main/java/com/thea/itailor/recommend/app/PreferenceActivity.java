package com.thea.itailor.recommend.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.recommend.bean.Game;
import com.thea.itailor.recommend.adapters.LumpAdapter;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferenceActivity extends AppCompatActivity {
    private static final String TAG = "PreferenceActivity";

    private static Context context;
    private static FragmentManager fragmentManager;

    private static Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        context = this;
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(
                PlaceholderFragment.SECTION_COLOR_LUMP)).commit();
    }

    public static void send() {
        IUserModel mUserModel = new UserModel(context);
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());

        Type type = new TypeToken<Game>(){}.getType();

        try {
            Log.i(TAG, new Gson().toJson(game, type));
            StringEntity entity = new StringEntity(new Gson().toJson(game, type), HttpUtil.ENCODING);
            HttpUtil.post(context, mUserModel.getBaseUrl() + Path.PREFERENCE_GAME + "?accountID=" +
                    mUserModel.getAccountID(), headers, entity,
                    ContentType.APPLICATION_JSON, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess: " + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure: " + statusCode);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static final int SECTION_COLOR_LUMP = 1;
        public static final int SECTION_ABSTRACT_IMAGE = 2;

        private List<String> selectedItems = new ArrayList<>();

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_preference, container, false);
            RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_color_lump);
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            LumpAdapter adapter = new LumpAdapter(sectionNumber);
            adapter.setOnItemSelectedListener((v, sign, viewHolder) -> {
                String[] names = sectionNumber == SECTION_COLOR_LUMP ? lumpString : abstractString;
                boolean selected = sign.getVisibility() != View.VISIBLE;
                if (selected) {
                    if (selectedItems.size() >= 3) {
                        Toast.makeText(getActivity(), "最多只能选三个", Toast.LENGTH_SHORT).show();
                    } else {
                        selectedItems.add(names[viewHolder.getAdapterPosition()]);
                        sign.setVisibility(View.VISIBLE);
                    }
                } else {
                    sign.setVisibility(View.GONE);
                    selectedItems.remove(names[viewHolder.getAdapterPosition()]);
                }
            });
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mRecyclerView.setAdapter(adapter);

            Button btn = (Button) rootView.findViewById(R.id.btn_next);
            btn.setText(sectionNumber == SECTION_COLOR_LUMP ? "什么鬼" : "完成");
            btn.setOnClickListener(v -> {
                game.addFeedback(selectedItems);
                if (getArguments().getInt(ARG_SECTION_NUMBER) == SECTION_COLOR_LUMP) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, PlaceholderFragment.newInstance(
                                    SECTION_ABSTRACT_IMAGE)).commit();
                } else if (getArguments().getInt(ARG_SECTION_NUMBER) == SECTION_ABSTRACT_IMAGE) {
                    send();
                    getActivity().onBackPressed();
                }
            });

            return rootView;
        }
    }

    static String[] lumpString = {"1_1", "1_8", "1_3", "1_5", "1_6", "1_2", "1_7", "1_4"};

    static String[] abstractString = {"2_1", "2_2", "2_6", "2_5", "2_3", "2_4", "2_8", "2_7"};
}
