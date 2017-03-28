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
import com.huhx0015.gityourissues.interfaces.IssuesViewHolderListener;
import com.huhx0015.gityourissues.models.Issue;
import com.huhx0015.gityourissues.viewmodel.IssuesRowViewModel;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssuesViewHolder> {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ADAPTER VARIABLES
    private Context context;

    // LIST VARIABLES
    private List<Issue> issueList;

    // LOGGING VARIABLES
    private static final String LOG_TAG = IssuesAdapter.class.getSimpleName();

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public IssuesAdapter(List<Issue> list, Context context){
        this.context = context;
        this.issueList = list;
    }

    /** RECYCLER VIEW METHODS __________________________________________________________________ **/

    @Override
    public IssuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterIssuesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_issues, parent, false);
        return new IssuesViewHolder(binding, new IssuesViewHolderListener() {
            @Override
            public void onIssueClick(View view, int position) {
                launchCommentsActivity(issueList.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(IssuesViewHolder holder, final int position) {
        String issueTitle = "#" + issueList.get(holder.getAdapterPosition()).getNumber() + ": " + issueList.get(position).getTitle();
        String issueDate = issueList.get(holder.getAdapterPosition()).getUpdatedAt();
        String issueBody = issueList.get(holder.getAdapterPosition()).getBody();

        if (issueBody.length() > 140) {
            issueBody = issueBody.substring(0, 140) + "...";
        }

        holder.bindView(); // Binds the ViewModel.
        IssuesRowViewModel rowViewModel = holder.issuesBinding.getViewModel();

        rowViewModel.setIssueTitleText(issueTitle);
        rowViewModel.setIssueDateText(issueDate);
        rowViewModel.setIssueBodyText(issueBody);

        Log.d(LOG_TAG, "onBindViewHolder(): Issue Name: " + issueTitle);
        Log.d(LOG_TAG, "onBindViewHolder(): Issue Body: " + issueBody);
        Log.d(LOG_TAG, "onBindViewHolder(): Issue Updated At: " + issueDate);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (issueList != null) {
            return issueList.size();
        } else {
            return 0;
        }
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

    public class IssuesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterIssuesBinding issuesBinding;
        private IssuesViewHolderListener issuesViewHolderListener;

        public IssuesViewHolder(AdapterIssuesBinding binding, IssuesViewHolderListener listener) {
            super(binding.issuesViewCardviewContainer);
            this.issuesBinding = binding;
            this.issuesViewHolderListener = listener;

            binding.issuesViewCardviewContainer.setOnClickListener(this);
        }

        private void bindView() {
            IssuesRowViewModel issuesRowViewModel = new IssuesRowViewModel();
            issuesBinding.setViewModel(issuesRowViewModel);
        }

        @Override
        public void onClick(View view) {
            int itemPos = getAdapterPosition();
            issuesViewHolderListener.onIssueClick(view, itemPos);
        }
    }
}