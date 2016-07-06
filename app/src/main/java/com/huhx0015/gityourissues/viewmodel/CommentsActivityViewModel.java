package com.huhx0015.gityourissues.viewmodel;

import android.databinding.BaseObservable;

/**
 * Created by Michael Yoon Huh on 7/5/2016.
 */
public class CommentsActivityViewModel extends BaseObservable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // VISIBILITY VARIABLES
    private boolean errorTextVisible = false;
    private boolean progressBarVisible = false;

    /** VIEW MODEL METHODS _____________________________________________________________________ **/

    public boolean isErrorTextVisible() {
        return errorTextVisible;
    }

    public void setErrorTextVisible(boolean errorTextVisible) {
        this.errorTextVisible = errorTextVisible;
        this.notifyChange();
    }

    public boolean isProgressBarVisible() {
        return progressBarVisible;
    }

    public void setProgressBarVisible(boolean progressBarVisible) {
        this.progressBarVisible = progressBarVisible;
        this.notifyChange();
    }
}
