package com.huhx0015.gityourissues.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.models.Issue;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ListViewHolder> {

    private static final String LOG_TAG = RecyclerAdapter.class.getSimpleName();

    private Context context;
    private List<Issue> issueList;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public RecyclerAdapter(List<Issue> list, Context context){
        this.context = context;
        this.issueList = list;
    }

    /** RECYCLER VIEW METHODS __________________________________________________________________ **/

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_cardview_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        holder.issueNameText.setText(issueList.get(position).getTitle());
        holder.issueDescriptionText.setText(issueList.get(position).getBody());
        holder.issueDateText.setText(issueList.get(position).getCreatedAt());

        Log.d(LOG_TAG, "onBindViewHolder(): Issue Name: " + issueList.get(position).getTitle());
        Log.d(LOG_TAG, "onBindViewHolder(): Issue Description: " + issueList.get(position).getBody());
        Log.d(LOG_TAG, "onBindViewHolder(): Time created at: " + issueList.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        CardView issueCardView;
        TextView issueNameText;
        TextView issueDescriptionText;
        TextView issueDateText;

        ListViewHolder(View itemView) {

            super(itemView);

            issueCardView = (CardView) itemView.findViewById(R.id.recycler_view_cardview_container);
            issueNameText = (TextView) itemView.findViewById(R.id.issue_name_text);
            issueDescriptionText = (TextView) itemView.findViewById(R.id.issue_description_text);
            issueDateText = (TextView) itemView.findViewById(R.id.issue_date_text);
        }
    }
}