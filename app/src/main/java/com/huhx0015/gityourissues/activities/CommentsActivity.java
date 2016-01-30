package com.huhx0015.gityourissues.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.constants.ActivityConstants;
import com.huhx0015.gityourissues.constants.GitConstants;
import com.huhx0015.gityourissues.interfaces.RetrofitInterface;
import com.huhx0015.gityourissues.models.Comment;
import com.huhx0015.gityourissues.ui.CommentsAdapter;
import com.squareup.okhttp.OkHttpClient;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */
public class CommentsActivity extends AppCompatActivity {

    private static final String LOG_TAG = CommentsActivity.class.getSimpleName();

    private List<Comment> commentsListResult;
    private int currentIssue = 0;

    @Bind(R.id.git_comments_activity_progress_indicator) ProgressBar commentsProgressBar;
    @Bind(R.id.git_comments_activity_recycler_view) RecyclerView commentsRecyclerView;
    @Bind(R.id.git_comments_error_text) TextView commentsErrorText;
    @Bind(R.id.git_comments_activity_toolbar) Toolbar commentsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLayout();

        if (getIntent().getExtras() != null) {
            currentIssue = getIntent().getIntExtra(ActivityConstants.GIT_ISSUE_CONTENT, 0);
        }

        if (currentIssue != 0) {
            commentsProgressBar.setVisibility(View.VISIBLE);
            retrieveComments();
        } else {
            commentsErrorText.setVisibility(View.VISIBLE);
        }
    }

    /** ACTIVITY OVERRIDE METHODS ______________________________________________________________ **/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initLayout() {
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);

        commentsToolbar.setTitle(getResources().getString(R.string.comments_title));
        setSupportActionBar(commentsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void updateView(boolean issuesReceived) {

        commentsProgressBar.setVisibility(View.GONE);

        if (issuesReceived) {
            initRecyclerView();
            setRecyclerList(commentsListResult);
        }
    }

    /** RECYCLERVIEW METHODS ___________________________________________________________________ **/

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentsRecyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerList(List<Comment> commentList){
        CommentsAdapter recyclerAdapter = new CommentsAdapter(commentList, this);
        commentsRecyclerView.setAdapter(recyclerAdapter);
    }

    /** RETROFIT METHODS _______________________________________________________________________ **/

    private void retrieveComments() {

        Retrofit retrofitAdapter = new Retrofit.Builder()
                .baseUrl(GitConstants.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        commentsListResult = new ArrayList<>();

        RetrofitInterface apiRequest = retrofitAdapter.create(RetrofitInterface.class);
        Call<List<Comment>> call = apiRequest.getComments(GitConstants.GIT_USER, GitConstants.GIT_REPO,
                currentIssue, GitConstants.GIT_SORT_UPDATED);

        call.enqueue(new Callback<List<Comment>>() {

            @Override
            public void onResponse(Response<List<Comment>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    commentsListResult = response.body();
                    updateView(true);

                    Log.d(LOG_TAG, "retrieveComments(): Retrieved comments in " + GitConstants.GIT_REPO + " from GitHub.");
                } else {
                    Log.e(LOG_TAG, "retrieveComments(): ERROR: " + response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(LOG_TAG, "retrieveComments(): ERROR: " + t.getMessage());
            }
        });
    }
}
