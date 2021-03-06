package io.jpotts18.android_mvp.domain.presenter.tasks;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.jpotts18.android_mvp.domain.GithubService;
import io.jpotts18.android_mvp.domain.models.Repo;
import io.jpotts18.android_mvp.domain.presenter.interfas.OnRepoTaskFinishedListener;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jpotts18 on 5/11/15.
 */
public class RepoListTask implements Callback<List<Repo>> {

    private OnRepoTaskFinishedListener listener;

    public RepoListTask(OnRepoTaskFinishedListener listener) {
        this.listener = listener;
    }

    private RestAdapter initRestAdapter(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("https://api.github.com")
                .build();
        return restAdapter;
    }

    public void loadRecentCommits(String username) {
        //TODO  请求  数据
        RestAdapter adapter = initRestAdapter();
        adapter.create(GithubService.class).
                listRepos(username, this);  //传入参数和回调监听器
    }

    @Override
    public void success(List<Repo> list, Response response) {
        Collections.sort(list, new Comparator<Repo>() {
            @Override
            public int compare(Repo left, Repo right) {
                return (left.stars > right.stars) ? -1 : 1;
            }
        });

        listener.onNetworkSuccess(list, response);
    }

    @Override
    public void failure(RetrofitError error) {
        listener.onNetworkFailure(error);
    }
}
