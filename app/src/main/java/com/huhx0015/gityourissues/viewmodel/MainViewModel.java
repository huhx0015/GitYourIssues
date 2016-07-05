package com.huhx0015.gityourissues.viewmodel;

import android.databinding.BaseObservable;
import android.view.View;

/**
 * Created by Michael Yoon Huh on 7/4/2016.
 */
public class MainViewModel extends BaseObservable {

    /** CLASS VARIABLES _____________________________________________________ **/

    private boolean mainProgressBarVisible = false;
    private boolean openIssueContainerVisible = false;
    private MainViewModelListener mainViewModelListener;
    private String openIssuesValueText;

    public interface MainViewModelListener {
        void onFabButtonClicked();
    }

    public void onClickFabButton(View view) {
        if (mainViewModelListener != null) {
            mainViewModelListener.onFabButtonClicked();
        }
    }

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
}
