package com.huhx0015.gityourissues.interfaces;

import com.huhx0015.gityourissues.models.Comment;
import com.huhx0015.gityourissues.models.Issue;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Michael Yoon Huh on 1/29/2016.
 */
public interface RetrofitInterface {

    @GET("/repos/{owner}/{repo}/issues")
    Observable<List<Issue>> getIssues(@Path("owner") String owner, @Path("repo") String repo,
                                @Query("sort") String sort, @Query("state") String state, @Query("per_page") int num);

    // GET /repos/:owner/:repo/issues
    @GET("/repos/{owner}/{repo}/issues/{id}/comments")
    Observable<List<Comment>> getComments(@Path("owner") String owner, @Path("repo") String repo,
                                    @Path("id") int id, @Query("sort") String sort);
}