package com.huhx0015.gityourissues.activities;

import android.graphics.Color;
import android.os.AsyncTask;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private IssuesQueryTask queryTask;
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

    @Override
    public void onStop() {
        super.onStop();

        if (null != queryTask) {
            if (queryTask.getStatus() == AsyncTask.Status.RUNNING) {
                queryTask.cancel(true);
                Log.d(LOG_TAG, "onStop(): AsyncTask has been cancelled.");
            }
        }
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
                queryTask = new IssuesQueryTask();
                queryTask.execute();
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

        RetrofitInterface apiRequest = retrofitAdapter.create(RetrofitInterface.class);

        try {
            issuesListResult = new ArrayList<>();
            issuesListResult = apiRequest.getIssues(GitConstants.GIT_USER, GitConstants.GIT_REPO,
                    currentState, GitConstants.GIT_SORT_UPDATED, GitConstants.GIT_PAGE_ISSUE_LIMIT).execute().body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "retrieveIssues(): Exception occurred while trying to retrieve issues: " + e);
            e.printStackTrace();
        }
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    class IssuesQueryTask extends AsyncTask<Void, Void, Void> {

        boolean isError = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mainProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Log.d(LOG_TAG, "IssuesQueryTask(): Retrieving issues in " + GitConstants.GIT_REPO + " from GitHub...");
                retrieveIssues();
            } catch (Exception e) {
                isError = true;
                Log.e(LOG_TAG, "IssuesQueryTask(): An exception error occurred: " + e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (!isCancelled()) {

                runOnUiThread(new Runnable() {

                    public void run() {

                        if (!isError) {
                            displaySnackbar();
                            updateView(true);
                        }
                    }
                });
            }
        }
    }
}
