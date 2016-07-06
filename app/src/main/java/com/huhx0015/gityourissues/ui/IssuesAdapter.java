package com.huhx0015.gityourissues.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.activities.CommentsActivity;
import com.huhx0015.gityourissues.constants.ActivityConstants;
import com.huhx0015.gityourissues.databinding.AdapterIssuesBinding;
import com.huhx0015.gityourissues.models.Issue;
import com.huhx0015.gityourissues.viewmodel.IssuesRowViewModel;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssuesViewHolder> {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private static final String LOG_TAG = IssuesAdapter.class.getSimpleName();

    private Context context;
    private List<Issue> issueList;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public IssuesAdapter(List<Issue> list, Context context){
        this.context = context;
        this.issueList = list;
    }

    /** RECYCLER VIEW METHODS __________________________________________________________________ **/

    @Override
    public IssuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_issues, parent, false);
        return new IssuesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IssuesViewHolder holder, int position) {

        String issueTitle = "#" + issueList.get(holder.getAdapterPosition()).getNumber() + ": " + issueList.get(position).getTitle();
        String issueDate = issueList.get(holder.getAdapterPosition()).getUpdatedAt();
        String issueBody = issueList.get(holder.getAdapterPosition()).getBody();

        if (issueBody.length() > 140) {
            issueBody = issueBody.substring(0, 140) + "...";
        }

        IssuesRowViewModel rowViewModel = holder.getIssuesViewModel();

        rowViewModel.setIssueTitleText(issueTitle);
        rowViewModel.setIssueDateText(issueDate);
        rowViewModel.setIssueBodyText(issueBody);

        rowViewModel.setIssuesRowViewModelListener(new IssuesRowViewModel.IssuesRowViewModelListener() {
            @Override
            public void onIssueRowClicked() {
                launchCommentsActivity(issueList.get(holder.getAdapterPosition()));
            }
        });

        holder.getIssuesBinding().setViewModel(rowViewModel);

        //rowViewModel.notifyChange();

        Log.d(LOG_TAG, "onBindViewHolder(): Issue Name: " + issueTitle);
        Log.d(LOG_TAG, "onBindViewHolder(): Issue Body: " + issueBody);
        Log.d(LOG_TAG, "onBindViewHolder(): Issue Updated At: " + issueDate);
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /** INTENT METHODS _________________________________________________________________________ **/

    private void launchCommentsActivity(Issue issue) {

        Intent i = new Intent(context, CommentsActivity.class);

        if (issue.getComments() != 0) {
            i.putExtra(ActivityConstants.GIT_ISSUE_CONTENT, issue.getNumber());
        }

        context.startActivity(i);
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public static class IssuesViewHolder extends RecyclerView.ViewHolder {

        private AdapterIssuesBinding issuesBinding;
        private IssuesRowViewModel issuesViewModel;

        public IssuesViewHolder(View rowView) {
            super(rowView);
            issuesBinding = DataBindingUtil.bind(rowView);
            issuesViewModel = new IssuesRowViewModel();
            issuesBinding.setViewModel(issuesViewModel);
        }

        public AdapterIssuesBinding getIssuesBinding() {
            return this.issuesBinding;
        }

        public IssuesRowViewModel getIssuesViewModel() {
            return this.issuesViewModel;
        }
    }
}