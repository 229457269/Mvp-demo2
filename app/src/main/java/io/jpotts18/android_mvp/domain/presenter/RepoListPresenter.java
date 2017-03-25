package io.jpotts18.android_mvp.domain.presenter;

import java.util.List;

import io.jpotts18.android_mvp.domain.models.Repo;
import io.jpotts18.android_mvp.domain.presenter.interfas.IRepoListPresenter;
import io.jpotts18.android_mvp.domain.ui.interfas.IRepoListView;
import io.jpotts18.android_mvp.domain.presenter.interfas.OnRepoTaskFinishedListener;
import io.jpotts18.android_mvp.domain.presenter.tasks.RepoListTask;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jpotts18 on 5/11/15.
 */
public class RepoListPresenter implements IRepoListPresenter, OnRepoTaskFinishedListener {

    private IRepoListView view;
    private RepoListTask interactor;

    public RepoListPresenter(IRepoListView view) {
        this.view = view;
        this.interactor = new RepoListTask(this); // pass in the InteractorListener
    }

    @Override
    public void loadCommits(String username) {
        interactor.loadRecentCommits(username);
    }

    @Override
    public void onNetworkSuccess(List<Repo> list, Response response) {
        view.onReposLoadedSuccess(list, response);
    }

    @Override
    public void onNetworkFailure(RetrofitError error) {
        view.onReposLoadedFailure(error);
    }
}
