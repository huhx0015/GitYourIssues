package com.huhx0015.gityourissues.viewmodel;

import android.databinding.BaseObservable;

/**
 * Created by Michael Yoon Huh on 7/6/2016.
 */
public class CommentsRowViewModel extends BaseObservable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // TEXT VARIABLES
    private String commentsNameText;
    private String commentsText;
    private String commentsTimeText;

    /** VIEW MODEL METHODS _____________________________________________________________________ **/

    public String getCommentsNameText() {
        return commentsNameText;
    }

    public String getCommentsText() {
        return commentsText;
    }

    public String getCommentsTimeText() {
        return commentsTimeText;
    }

    public void setCommentsNameText(String commentsNameText) {
        this.commentsNameText = commentsNameText;
    }

    public void setCommentsText(String commentsText) {
        this.commentsText = commentsText;
    }

    public void setCommentsTimeText(String commentsTimeText) {
        this.commentsTimeText = commentsTimeText;
    }
}