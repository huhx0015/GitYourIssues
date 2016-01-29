
package com.huhx0015.gityourissues.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */

@Generated("org.jsonschema2pojo")
public class Issue {

    private String url;
    private String labelsUrl;
    private String commentsUrl;
    private String eventsUrl;
    private String htmlUrl;
    private int id;
    private int number;
    private String title;
    private User user;
    private List<Object> labels = new ArrayList<Object>();
    private String state;
    private boolean locked;
    private Object assignee;
    private Object milestone;
    private int comments;
    private String createdAt;
    private String updatedAt;
    private Object closedAt;
    private PullRequest pullRequest;
    private String body;

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The labelsUrl
     */
    public String getLabelsUrl() {
        return labelsUrl;
    }

    /**
     * 
     * @param labelsUrl
     *     The labels_url
     */
    public void setLabelsUrl(String labelsUrl) {
        this.labelsUrl = labelsUrl;
    }

    /**
     * 
     * @return
     *     The commentsUrl
     */
    public String getCommentsUrl() {
        return commentsUrl;
    }

    /**
     * 
     * @param commentsUrl
     *     The comments_url
     */
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    /**
     * 
     * @return
     *     The eventsUrl
     */
    public String getEventsUrl() {
        return eventsUrl;
    }

    /**
     * 
     * @param eventsUrl
     *     The events_url
     */
    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    /**
     * 
     * @return
     *     The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     * 
     * @param htmlUrl
     *     The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The number
     */
    public int getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The labels
     */
    public List<Object> getLabels() {
        return labels;
    }

    /**
     * 
     * @param labels
     *     The labels
     */
    public void setLabels(List<Object> labels) {
        this.labels = labels;
    }

    /**
     * 
     * @return
     *     The state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * 
     * @param locked
     *     The locked
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * 
     * @return
     *     The assignee
     */
    public Object getAssignee() {
        return assignee;
    }

    /**
     * 
     * @param assignee
     *     The assignee
     */
    public void setAssignee(Object assignee) {
        this.assignee = assignee;
    }

    /**
     * 
     * @return
     *     The milestone
     */
    public Object getMilestone() {
        return milestone;
    }

    /**
     * 
     * @param milestone
     *     The milestone
     */
    public void setMilestone(Object milestone) {
        this.milestone = milestone;
    }

    /**
     * 
     * @return
     *     The comments
     */
    public int getComments() {
        return comments;
    }

    /**
     * 
     * @param comments
     *     The comments
     */
    public void setComments(int comments) {
        this.comments = comments;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The closedAt
     */
    public Object getClosedAt() {
        return closedAt;
    }

    /**
     * 
     * @param closedAt
     *     The closed_at
     */
    public void setClosedAt(Object closedAt) {
        this.closedAt = closedAt;
    }

    /**
     * 
     * @return
     *     The pullRequest
     */
    public PullRequest getPullRequest() {
        return pullRequest;
    }

    /**
     * 
     * @param pullRequest
     *     The pull_request
     */
    public void setPullRequest(PullRequest pullRequest) {
        this.pullRequest = pullRequest;
    }

    /**
     * 
     * @return
     *     The body
     */
    public String getBody() {
        return body;
    }

    /**
     * 
     * @param body
     *     The body
     */
    public void setBody(String body) {
        this.body = body;
    }

}
