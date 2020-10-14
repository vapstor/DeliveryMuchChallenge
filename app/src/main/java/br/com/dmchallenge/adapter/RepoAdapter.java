package br.com.dmchallenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import br.com.dmchallenge.databinding.ListItemBinding;
import br.com.dmchallenge.model.Repo;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    private Context mContext;
    private ArrayList<Repo> mList;
    private ListItemBinding binding;

    public RepoAdapter(Context mContext, ArrayList<Repo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        binding = ListItemBinding.inflate(inflater, parent, false);
        return new RepoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.itemBinding.repoName.setText(mList.get(position).getOwnerName());
        Glide.with(mContext).load(mList.get(position).getOwnerAvatar())
                .into(holder.itemBinding.repoImage);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {
        private ListItemBinding itemBinding;

        public RepoViewHolder(ListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }

    public void updateList(ArrayList<Repo> updatedList) {
        mList = updatedList;
        notifyDataSetChanged();
    }

    public Repo getRepoAt(int position) {
        return mList.get(position);
    }
}