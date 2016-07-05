package com.huhx0015.gityourissues.viewmodel;

import android.databinding.BaseObservable;
import android.view.View;

/**
 * Created by Michael Yoon Huh on 7/5/2016.
 */
public class IssuesRowViewModel extends BaseObservable {

    /** CLASS VARIABLES _____________________________________________________ **/

    private String issueTitleText;
    private String issueBodyText;
    private String issueDateText;

    private IssuesRowViewModelListener issuesRowViewModelListener;

    public interface IssuesRowViewModelListener {
        void onIssueRowClicked();
    }

    public void onClickIssueRow(View view) {
        if (issuesRowViewModelListener != null) {
            issuesRowViewModelListener.onIssueRowClicked();
        }
    }

    public String getIssueTitleText() {
        return issueTitleText;
    }

    public String getIssueBodyText() {
        return issueBodyText;
    }

    public String getIssueDateText() {
        return issueDateText;
    }

    public void setIssueTitleText(String issueTitleText) {
        this.issueTitleText = issueTitleText;
    }

    public void setIssueBodyText(String issueBodyText) {
        this.issueBodyText = issueBodyText;
    }

    public void setIssueDateText(String issueDateText) {
        this.issueDateText = issueDateText;
    }

    public void setIssuesRowViewModelListener(IssuesRowViewModelListener listener) {
        if (issuesRowViewModelListener != null) {
            this.issuesRowViewModelListener = listener;
        }
    }
}
