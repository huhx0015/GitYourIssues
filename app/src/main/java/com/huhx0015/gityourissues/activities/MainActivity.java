package com.huhx0015.gityourissues.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.constants.GitConstants;
import com.huhx0015.gityourissues.interfaces.RetrofitInterface;
import com.huhx0015.gityourissues.models.Issue;
import com.huhx0015.gityourissues.ui.IssuesAdapter;
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

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private List<Issue> issuesListResult;
    private String currentState = GitConstants.GIT_STATE_OPEN;

    @Bind(R.id.git_main_activity_layout) CoordinatorLayout mainLayout;
    @Bind(R.id.git_main_activity_fab_button) FloatingActionButton mainFabButton;
    @Bind(R.id.git_main_activity_open_issue_container) LinearLayout mainOpenIssuesContainer;
    @Bind(R.id.git_main_activity_progress_indicator) ProgressBar mainProgressBar;
    @Bind(R.id.git_main_activity_recycler_view) RecyclerView mainRecyclerView;
    @Bind(R.id.repo_name_text) TextView mainRepoName;
    @Bind(R.id.repo_author_text) TextView mainRepoAuthor;
    @Bind(R.id.repo_open_issues_value_text) TextView mainOpenIssuesValueText;
    @Bind(R.id.git_main_activity_toolbar) Toolbar mainToolbar;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLayout();
        initText();
        initButtons();
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initLayout() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mainToolbar);
    }

    private void initButtons() {

        // FLOATING ACTION BUTTON:
        mainFabButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mainProgressBar.setVisibility(View.VISIBLE);
                retrieveIssues();
            }
        });
    }

    private void initText() {
        mainRepoName.setText(GitConstants.GIT_REPO);
        mainRepoName.setShadowLayer(4, 2, 2, Color.BLACK);
        mainRepoAuthor.setShadowLayer(4, 2, 2, Color.BLACK);
        mainRepoAuthor.setText(GitConstants.GIT_USER);
    }

    private void displaySnackbar() {
        Snackbar.make(mainLayout, String.format(getResources().getString(R.string.open_issues_snackbar_message), issuesListResult.size()), Snackbar.LENGTH_LONG).show();
    }

    private void updateView(boolean issuesReceived) {

        mainProgressBar.setVisibility(View.GONE);

        if (issuesReceived) {
            initRecyclerView();
            setRecyclerList(issuesListResult);
            mainOpenIssuesValueText.setText(" " + issuesListResult.size());
            mainOpenIssuesContainer.setVisibility(View.VISIBLE);
            displaySnackbar();
        }
    }

    /** RECYCLERVIEW METHODS ___________________________________________________________________ **/

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerList(List<Issue> issueList){
        IssuesAdapter recyclerAdapter = new IssuesAdapter(issueList, this);
        mainRecyclerView.setAdapter(recyclerAdapter);
    }

    /** RETROFIT METHODS _______________________________________________________________________ **/

    private void retrieveIssues() {

        Retrofit retrofitAdapter = new Retrofit.Builder()
                .baseUrl(GitConstants.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        issuesListResult = new ArrayList<>();

        RetrofitInterface apiRequest = retrofitAdapter.create(RetrofitInterface.class);
        Call<List<Issue>> call = apiRequest.getIssues(GitConstants.GIT_USER, GitConstants.GIT_REPO,
                GitConstants.GIT_SORT_UPDATED, currentState, GitConstants.GIT_PAGE_ISSUE_LIMIT);

        call.enqueue(new Callback<List<Issue>>() {

            @Override
            public void onResponse(Response<List<Issue>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    issuesListResult = response.body();
                    updateView(true);

                    Log.d(LOG_TAG, "retrieveIssues(): Retrieved issues in " + GitConstants.GIT_REPO + " from GitHub.");
                } else {
                    Log.e(LOG_TAG, "retrieveIssues(): ERROR: " + response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(LOG_TAG, "retrieveIssues(): ERROR: " + t.getMessage());
            }
        });
    }
}
