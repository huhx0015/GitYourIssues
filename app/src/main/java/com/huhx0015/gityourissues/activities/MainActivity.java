package com.huhx0015.gityourissues.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.constants.ActivityConstants;
import com.huhx0015.gityourissues.constants.GitConstants;
import com.huhx0015.gityourissues.interfaces.RetrofitInterface;
import com.huhx0015.gityourissues.models.Issue;
import com.huhx0015.gityourissues.ui.RecyclerAdapter;
import com.squareup.okhttp.OkHttpClient;
import java.io.IOException;
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

    private GitQueryTask queryTask;
    private List<Issue> issuesListResult;

    @Bind(R.id.git_main_activity_fab_button) FloatingActionButton gitFabButton;
    @Bind(R.id.git_main_activity_progress_indicator) ProgressBar gitProgressBar;
    @Bind(R.id.git_main_activity_recycler_view) RecyclerView gitRecyclerView;
    @Bind(R.id.repo_name_text) TextView gitRepoName;
    @Bind(R.id.repo_author_text) TextView gitRepoAuthor;
    @Bind(R.id.repo_open_issues_text) TextView gitOpenIssuesLabelText;
    @Bind(R.id.repo_open_issues_value_text) TextView gitOpenIssuesValueText;
    @Bind(R.id.git_main_activity_toolbar) Toolbar gitToolbar;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLayout();
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

    /** ACTIVITY OVERRIDDEN METHODS ____________________________________________________________ **/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initLayout() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(gitToolbar);
    }

    private void initButtons() {

        // FLOATING ACTION BUTTON:
        gitFabButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                queryTask = new GitQueryTask();
                queryTask.execute();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void updateView(boolean issuesReceived) {

        gitProgressBar.setVisibility(View.GONE);

        if (issuesReceived) {
            initRecyclerView();
            setRecyclerList(issuesListResult);
        }
    }

    /** RECYCLERVIEW METHODS ___________________________________________________________________ **/

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gitRecyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerList(List<Issue> issueList){
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(issueList, this);
        gitRecyclerView.setAdapter(recyclerAdapter);
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
            apiRequest.getIssues(GitConstants.GIT_USER, GitConstants.GIT_REPO).execute().body();
            //issuesListResult = apiRequest.getIssues(GitConstants.GIT_USER, GitConstants.GIT_REPO).execute().body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "retrieveIssues(): Exception occurred while trying to retrieve issues: " + e);
            e.printStackTrace();
        }
    }

    /** INTENT METHODS _________________________________________________________________________ **/

    private void launchIssueActivity(Issue issue) {

        Gson gson = new Gson();

        Intent i = new Intent(this, IssueActivity.class);
        i.putExtra(ActivityConstants.GIT_ISSUE_CONTENT, gson.toJson(issue));
        startActivity(i); // Launches the activity class.
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    class GitQueryTask extends AsyncTask<Void, Void, Void> {

        boolean isError = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            gitProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Log.d(LOG_TAG, "QueryTask(): Retrieving issues in " + GitConstants.GIT_REPO + " from GitHub...");
                retrieveIssues();
            } catch (Exception e) {
                isError = true;
                Log.e(LOG_TAG, "QueryTask(): An exception error occurred: " + e);
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
                            updateView(true);
                        }
                    }
                });
            }
        }
    }
}
