
package com.huhx0015.gityourissues.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */

@Generated("org.jsonschema2pojo")
public class PullRequest {

    @SerializedName("url")
    @Expose private String url;

    @SerializedName("html_url")
    @Expose private String htmlUrl;

    @SerializedName("diff_url")
    @Expose private String diffUrl;

    @SerializedName("patch_url")
    @Expose private String patchUrl;

    /** GET / SET METHODS ______________________________________________________________________ **/

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
     *     The diffUrl
     */
    public String getDiffUrl() {
        return diffUrl;
    }

    /**
     * 
     * @param diffUrl
     *     The diff_url
     */
    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    /**
     * 
     * @return
     *     The patchUrl
     */
    public String getPatchUrl() {
        return patchUrl;
    }

    /**
     * 
     * @param patchUrl
     *     The patch_url
     */
    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

}
