package com.huhx0015.gityourissues.interfaces;

import com.huhx0015.gityourissues.models.Comment;
import com.huhx0015.gityourissues.models.Issue;
import java.util.List;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */
public interface RetrofitInterface {

    // GET /repos/:owner/:repo/issues
    @GET("/repos/{owner}/{repo}/issues")
    Call<List<Issue>> getIssues(@Path("owner") String owner, @Path("repo") String repo);

    // GET /repos/:owner/:repo/issues
    @GET("/repos/{owner}/{repo}/issues")
    Call<List<Issue>> getIssues(@Path("owner") String owner, @Path("repo") String repo,
                                @Query("state") String state, @Query("sort") String sort, @Query("per_page") int num);

    // GET /repos/:owner/:repo/issues
    @GET("/repos/{owner}/{repo}/issues/{id}/comments")
    Call<List<Comment>> getComments(@Path("owner") String owner, @Path("repo") String repo,
                                    @Path("id") int id);

    // GET /repos/:owner/:repo/issues
    @GET("/repos/{owner}/{repo}/issues/{id}/comments")
    Call<List<Comment>> getComments(@Path("owner") String owner, @Path("repo") String repo,
                                    @Path("id") int id, @Query("sort") String sort);
}
