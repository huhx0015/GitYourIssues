package com.huhx0015.gityourissues.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.activities.CommentsActivity;
import com.huhx0015.gityourissues.constants.ActivityConstants;
import com.huhx0015.gityourissues.models.Issue;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ListViewHolder> {

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
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_cardview_row, parent, false);

        ListViewHolder viewHolder = new ListViewHolder(view, new ListViewHolder.OnItemViewHolderClick() {

            @Override
            public void onItemClick(View caller, int position) {
                launchCommentsActivity(issueList.get(position));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        String issueTitle = "#" + issueList.get(position).getNumber() + ": " + issueList.get(position).getTitle();
        String issueDate = issueList.get(position).getUpdatedAt();
        String issueBody = issueList.get(position).getBody();

        if (issueBody.length() > 140) {
            issueBody = issueBody.substring(0, 140) + "...";
        }

        holder.issueTitleText.setText(issueTitle);
        holder.issueBodyText.setText(issueBody);
        holder.issueDateText.setText(issueDate);

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

        Gson gson = new Gson();

        Intent i = new Intent(context, CommentsActivity.class);
        i.putExtra(ActivityConstants.GIT_ISSUE_CONTENT, gson.toJson(issue));
        context.startActivity(i);
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView issueCardView;
        TextView issueTitleText;
        TextView issueBodyText;
        TextView issueDateText;

        public OnItemViewHolderClick resultItemListener;

        ListViewHolder(View itemView, OnItemViewHolderClick listener) {

            super(itemView);

            issueCardView = (CardView) itemView.findViewById(R.id.recycler_view_cardview_container);
            issueTitleText = (TextView) itemView.findViewById(R.id.issue_name_text);
            issueBodyText = (TextView) itemView.findViewById(R.id.issue_body_text);
            issueDateText = (TextView) itemView.findViewById(R.id.issue_date_text);

            if (listener != null) {
                resultItemListener = listener;
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            int itemPos = getAdapterPosition();
            resultItemListener.onItemClick(v, itemPos);
        }

        public interface OnItemViewHolderClick {
            void onItemClick(View caller, int position);
        }
    }
}