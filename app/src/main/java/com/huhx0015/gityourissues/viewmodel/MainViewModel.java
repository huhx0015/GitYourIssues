package com.huhx0015.gityourissues.viewmodel;

import android.databinding.BaseObservable;
import android.view.View;

/**
 * Created by Michael Yoon Huh on 7/4/2016.
 */
public class MainViewModel extends BaseObservable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LISTENER VARIABLES
    private MainViewModelListener mainViewModelListener;

    // TEXT VARIABLES
    private String openIssuesValueText;

    // VISIBILITY VARIABLES
    private boolean mainProgressBarVisible = false;
    private boolean openIssueContainerVisible = false;

    /** VIEW MODEL METHODS _____________________________________________________________________ **/

    public String getOpenIssuesValueText() {
        return openIssuesValueText;
    }

    public void setOpenIssuesValueText(String text) {
        this.openIssuesValueText = text;
        notifyChange();
    }

    public boolean isMainProgressBarVisible() {
        return mainProgressBarVisible;
    }

    public boolean isOpenIssueContainerVisible() {
        return openIssueContainerVisible;
    }

    public void setMainProgressBarVisibility(boolean isVisible) {
        this.mainProgressBarVisible = isVisible;
        notifyChange();
    }

    public void setOpenIssueContainerVisibility(boolean isVisible) {
        this.openIssueContainerVisible = isVisible;
        notifyChange();
    }

    public void setMainViewModelListener(MainViewModelListener listener) {
        this.mainViewModelListener = listener;
    }

    /** CLICK METHODS __________________________________________________________________________ **/

    public void onClickFabButton(View view) {
        if (mainViewModelListener != null) {
            mainViewModelListener.onFabButtonClicked();
        }
    }

    /** INTERFACE ______________________________________________________________________________ **/

    public interface MainViewModelListener {
        void onFabButtonClicked();
    }
}
