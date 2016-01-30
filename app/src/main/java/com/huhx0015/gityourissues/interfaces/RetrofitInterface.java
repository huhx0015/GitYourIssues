package com.huhx0015.gityourissues.interfaces;

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
    Call<List<Issue>> getIssues(@Path("owner") String owner, @Path("repo") String repo,
                                @Query("state") String state, @Query("sort") String sort);
}
