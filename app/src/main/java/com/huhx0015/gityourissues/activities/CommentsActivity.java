package com.huhx0015.gityourissues.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.constants.ActivityConstants;
import com.huhx0015.gityourissues.constants.GitConstants;
import com.huhx0015.gityourissues.databinding.ActivityCommentsBinding;
import com.huhx0015.gityourissues.interfaces.RetrofitInterface;
import com.huhx0015.gityourissues.models.Comment;
import com.huhx0015.gityourissues.ui.CommentsAdapter;
import com.huhx0015.gityourissues.viewmodel.CommentsActivityViewModel;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */
public class CommentsActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // BINDING / VIEWMODEL VARIABLES
    private ActivityCommentsBinding commentsActivityBinding;
    private CommentsActivityViewModel commentsActivityViewModel;

    // LIST VARIABLES
    private List<Comment> commentsListResult;
    private int currentIssue = 0;

    // LOGGING VARIABLES
    private static final String LOG_TAG = CommentsActivity.class.getSimpleName();

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        initToolbar();
        initExtras();
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

    private void initBinding() {
        commentsActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_comments);
        commentsActivityViewModel = new CommentsActivityViewModel();
        commentsActivityBinding.setViewModel(commentsActivityViewModel);
    }

    private void initToolbar() {
        commentsActivityBinding.gitCommentsActivityToolbar.setTitle(getResources().getString(R.string.comments_title));
        setSupportActionBar(commentsActivityBinding.gitCommentsActivityToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initExtras() {
        if (getIntent().getExtras() != null) {
            currentIssue = getIntent().getIntExtra(ActivityConstants.GIT_ISSUE_CONTENT, 0);
        }

        if (currentIssue != 0) {
            commentsActivityViewModel.setProgressBarVisible(true);
            retrieveComments();
        } else {
            commentsActivityViewModel.setErrorTextVisible(true);
        }
    }

    private void updateView(boolean issuesReceived) {

        commentsActivityViewModel.setProgressBarVisible(false);

        if (issuesReceived) {
            initRecyclerView();
            setRecyclerList(commentsListResult);
        }
    }

    /** RECYCLERVIEW METHODS ___________________________________________________________________ **/

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentsActivityBinding.gitCommentsActivityRecyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerList(List<Comment> commentList){
        CommentsAdapter recyclerAdapter = new CommentsAdapter(commentList, this);
        commentsActivityBinding.gitCommentsActivityRecyclerView.setAdapter(recyclerAdapter);
    }

    /** RETROFIT METHODS _______________________________________________________________________ **/

    private void retrieveComments() {

        Retrofit retrofitAdapter = new Retrofit.Builder()
                .baseUrl(GitConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        commentsListResult = new ArrayList<>();

        RetrofitInterface apiRequest = retrofitAdapter.create(RetrofitInterface.class);
        Observable<List<Comment>> call = apiRequest.getComments(GitConstants.GIT_USER, GitConstants.GIT_REPO,
                currentIssue, GitConstants.GIT_SORT_UPDATED);

        Subscription subscription = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, "retrieveComments(): ERROR: " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        commentsListResult = comments;
                        if (comments != null) {
                            updateView(true);
                            Log.d(LOG_TAG, "retrieveComments(): Retrieved issues in " + GitConstants.GIT_REPO + " from GitHub.");
                        } else {
                            Log.e(LOG_TAG, "retrieveComments(): ERROR: The retrieved comments were null.");
                        }
                    }
                });
    }
}