package com.huhx0015.gityourissues.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.constants.GitConstants;
import com.huhx0015.gityourissues.databinding.ActivityMainBinding;
import com.huhx0015.gityourissues.interfaces.RetrofitInterface;
import com.huhx0015.gityourissues.models.Issue;
import com.huhx0015.gityourissues.ui.IssuesAdapter;
import com.huhx0015.gityourissues.viewmodel.MainViewModel;
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

public class MainActivity extends AppCompatActivity implements MainViewModel.MainViewModelListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // BINDING / VIEWMODEL VARIABLES
    private ActivityMainBinding mainActivityBinding;
    private MainViewModel mainActivityViewModel;

    // LIST VARIABLES
    private List<Issue> issuesListResult;

    // LOGGING VARIABLES
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        initToolbar();
        initText();
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initBinding() {
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = new MainViewModel();
        mainActivityViewModel.setMainViewModelListener(this);
        mainActivityBinding.setViewModel(mainActivityViewModel);
    }

    private void initToolbar() {
        setSupportActionBar(mainActivityBinding.gitMainActivityToolbar);
    }

    private void initText() {
        mainActivityBinding.repoNameText.setShadowLayer(4, 2, 2, Color.BLACK);
        mainActivityBinding.repoAuthorText.setShadowLayer(4, 2, 2, Color.BLACK);
    }

    private void displaySnackbar() {
        Snackbar.make(mainActivityBinding.gitMainActivityLayout, String.format(getResources().getString(R.string.open_issues_snackbar_message), issuesListResult.size()), Snackbar.LENGTH_LONG).show();
    }

    private void updateView(boolean issuesReceived) {

        mainActivityViewModel.setMainProgressBarVisibility(false);

        if (issuesReceived) {
            initRecyclerView();
            setRecyclerList(issuesListResult);
            mainActivityViewModel.setOpenIssuesValueText(" " + issuesListResult.size());
            mainActivityViewModel.setOpenIssueContainerVisibility(true);
            displaySnackbar();
        }
    }

    /** RECYCLERVIEW METHODS ___________________________________________________________________ **/

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainActivityBinding.gitMainActivityRecyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerList(List<Issue> issueList){
        IssuesAdapter recyclerAdapter = new IssuesAdapter(issueList, this);
        recyclerAdapter.setHasStableIds(true);
        mainActivityBinding.gitMainActivityRecyclerView.setAdapter(recyclerAdapter);
        mainActivityBinding.gitMainActivityRecyclerView.setHasFixedSize(true);
        mainActivityBinding.gitMainActivityRecyclerView.setItemViewCacheSize(30);
        mainActivityBinding.gitMainActivityRecyclerView.setDrawingCacheEnabled(true);
        mainActivityBinding.gitMainActivityRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    /** RETROFIT METHODS _______________________________________________________________________ **/

    private void retrieveIssues() {

        Retrofit retrofitAdapter = new Retrofit.Builder()
                .baseUrl(GitConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        issuesListResult = new ArrayList<>();

        RetrofitInterface apiRequest = retrofitAdapter.create(RetrofitInterface.class);
        Observable<List<Issue>> call = apiRequest.getIssues(GitConstants.GIT_USER, GitConstants.GIT_REPO,
                GitConstants.GIT_SORT_UPDATED, GitConstants.GIT_STATE_OPEN, GitConstants.GIT_PAGE_ISSUE_LIMIT);

        Subscription subscription = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Issue>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, "retrieveIssues(): ERROR: " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Issue> issues) {
                        issuesListResult = issues;
                        if (issues != null) {
                            updateView(true);
                            Log.d(LOG_TAG, "retrieveIssues(): Retrieved issues in " + GitConstants.GIT_REPO + " from GitHub.");
                        } else {
                            Log.e(LOG_TAG, "retrieveIssues(): ERROR: The retrieved issues were null.");
                        }
                    }
                });
    }

    /** LISTENER METHODS _______________________________________________________________________ **/

    @Override
    public void onFabButtonClicked() {
        mainActivityViewModel.setMainProgressBarVisibility(true);
        retrieveIssues();
    }
}