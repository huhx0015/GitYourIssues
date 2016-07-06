package com.huhx0015.gityourissues.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.databinding.AdapterCommentsBinding;
import com.huhx0015.gityourissues.models.Comment;
import com.huhx0015.gityourissues.viewmodel.CommentsRowViewModel;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ADAPTER VARIABLES
    private Context context;

    // LIST VARIABLES
    private List<Comment> commentList;

    // LOGGING VARIABLES
    private static final String LOG_TAG = CommentsAdapter.class.getSimpleName();

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public CommentsAdapter(List<Comment> list, Context context){
        this.context = context;
        this.commentList = list;
    }

    /** RECYCLER VIEW METHODS __________________________________________________________________ **/

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterCommentsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_comments, parent, false);
        return new CommentsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        String commentsName = commentList.get(position).getUser().getLogin();
        String commentsText = commentList.get(position).getBody();
        String commentsTimeText = commentList.get(position).getUpdatedAt();

        holder.bindView(); // Binds the ViewModel.
        CommentsRowViewModel rowViewModel = holder.commentsBinding.getViewModel();

        rowViewModel.setCommentsNameText(commentsName);
        rowViewModel.setCommentsText(commentsText);
        rowViewModel.setCommentsTimeText(commentsTimeText);

        Log.d(LOG_TAG, "onBindViewHolder(): Comment Name: " + commentList.get(position).getUser().getLogin());
        Log.d(LOG_TAG, "onBindViewHolder(): Comment Body: " + commentList.get(position).getBody());
        Log.d(LOG_TAG, "onBindViewHolder(): Comment Updated At: " + commentList.get(position).getUpdatedAt());

        String avatarImageUrl = null;

        try {
            avatarImageUrl = commentList.get(position).getUser().getAvatarUrl();
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "onBindViewHolder(): Null pointer exception encountered while attempting to retrieve the avatar image URL.");
        }

        if (avatarImageUrl != null) {
            Picasso.with(context)
                    .load(avatarImageUrl)
                    .into(holder.commentsBinding.commentsAvatarImage);
        } else {
            Picasso.with(context)
                    .load(R.mipmap.ic_launcher)
                    .into(holder.commentsBinding.commentsAvatarImage);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {

        private AdapterCommentsBinding commentsBinding;

        public CommentsViewHolder(AdapterCommentsBinding binding) {
            super(binding.commentsViewCardviewContainer);
            this.commentsBinding = binding;
        }

        private void bindView() {
            CommentsRowViewModel issuesRowViewModel = new CommentsRowViewModel();
            commentsBinding.setViewModel(issuesRowViewModel);
        }
    }
}