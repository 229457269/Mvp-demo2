package io.jpotts18.android_mvp.domain.ui.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.jpotts18.android_mvp.R;
import io.jpotts18.android_mvp.domain.models.Repo;
import io.jpotts18.android_mvp.domain.presenter.RepoListPresenter;
import io.jpotts18.android_mvp.domain.presenter.adapters.RepoAdapter;
import io.jpotts18.android_mvp.domain.ui.interfas.IRepoListView;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RepoListFragmentActivity extends ActionBarActivity implements IRepoListView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list_fragment);
        ButterKnife.bind(this);
        presenter = new RepoListPresenter(this);
    }

    @Bind(R.id.fragment_repo_list_view)
    ListView listView;

    private RepoListPresenter presenter;

    @Override
    public void onResume() {
        super.onResume();
        //TODO   请求数据
        presenter.loadCommits("229457269");
    }

    @Override
    public void onReposLoadedSuccess(List<Repo> list, Response response) {
        listView.setAdapter(new RepoAdapter(this, list));
    }

    @Override
    public void onReposLoadedFailure(RetrofitError error) {
        Toast.makeText(this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
