package com.huhx0015.gityourissues.viewmodel;

import android.databinding.BaseObservable;

/**
 * Created by Michael Yoon Huh on 7/5/2016.
 */
public class IssuesRowViewModel extends BaseObservable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // TEXT VARIABLES
    private String issueTitleText;
    private String issueBodyText;
    private String issueDateText;

    /** VIEW MODEL METHODS _____________________________________________________________________ **/

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
}
