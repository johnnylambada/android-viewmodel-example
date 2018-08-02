package com.johnnylambada.acviewmodel.home;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnnylambada.acviewmodel.R;
import com.johnnylambada.acviewmodel.model.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>{

    private final List<Repo> data = new ArrayList<>();
    private RepoSelectedListener repoSelectedListener;

    public RepoListAdapter(ListViewModel viewModel, LifecycleOwner lifecycleOwner, RepoSelectedListener repoSelectedListener) {
        this.repoSelectedListener = repoSelectedListener;
        viewModel.getRepos().observe(lifecycleOwner, repos -> {
            if (repos==null) {
                data.clear();
                notifyDataSetChanged();
                return;
            }
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new RepoDiffCallback(data,repos));
            data.clear();
            data.addAll(repos);
            diffResult.dispatchUpdatesTo(this);
        });
        setHasStableIds(true);
    }

    @NonNull @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout_list_item, parent, false);
        return new RepoViewHolder(view, repoSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override public int getItemCount() {
        return data.size();
    }

    @Override public long getItemId(int position) {
        return data.get(position).id();
    }

    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repoName) TextView repoName;
        @BindView(R.id.repoDescription) TextView repoDescription;
        @BindView(R.id.repoForks) TextView repoForks;
        @BindView(R.id.repoStars) TextView repoStars;
        private Repo repo;

        RepoViewHolder(View itemView, RepoSelectedListener repoSelectedListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(__->{
                if (repo!=null) {
                    repoSelectedListener.onRepoSelected(repo);
                }
            });
        }

        public void bind(Repo repo){
            this.repo = repo;
            repoName.setText(repo.name());
            repoDescription.setText(repo.description());
            repoForks.setText(String.valueOf(repo.forks()));
            repoStars.setText(String.valueOf(repo.stars()));
        }
    }
}
