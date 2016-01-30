package com.huhx0015.gityourissues.util;

import com.huhx0015.gityourissues.models.Issue;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */
public class IssuesUtil {

    public static List<Issue> filterIssueList(List<Issue> issueList, String state) {

        List<Issue> filteredIssueList = new ArrayList<>();

        for (Issue issue : issueList) {
            if (issue.getState().equals(state)) {
                filteredIssueList.add(issue);
            }
        }

        return filteredIssueList;
    }
}
