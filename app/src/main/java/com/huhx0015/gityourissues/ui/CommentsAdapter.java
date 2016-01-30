package com.huhx0015.gityourissues.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.huhx0015.gityourissues.R;
import com.huhx0015.gityourissues.models.Comment;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ListViewHolder> {

    private static final String LOG_TAG = CommentsAdapter.class.getSimpleName();

    private Context context;
    private List<Comment> commentList;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public CommentsAdapter(List<Comment> list, Context context){
        this.context = context;
        this.commentList = list;
    }

    /** RECYCLER VIEW METHODS __________________________________________________________________ **/

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_view_cardview_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        holder.commentsNameText.setText(commentList.get(position).getUser().getLogin());
        holder.commentText.setText(commentList.get(position).getBody());
        holder.commentsTimeText.setText(commentList.get(position).getUpdatedAt());

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
                    .into(holder.commentsAvatarImage);
        } else {
            Picasso.with(context)
                    .load(R.mipmap.ic_launcher)
                    .into(holder.commentsAvatarImage);
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

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        CardView commentsCardView;
        ImageView commentsAvatarImage;
        TextView commentsNameText;
        TextView commentText;
        TextView commentsTimeText;

        ListViewHolder(View itemView) {

            super(itemView);

            commentsCardView = (CardView) itemView.findViewById(R.id.comments_view_cardview_container);
            commentsAvatarImage = (ImageView) itemView.findViewById(R.id.comments_avatar_image);
            commentsNameText = (TextView) itemView.findViewById(R.id.comments_name_text);
            commentText = (TextView) itemView.findViewById(R.id.comments_text);
            commentsTimeText = (TextView) itemView.findViewById(R.id.comments_time_text);
        }
    }
}