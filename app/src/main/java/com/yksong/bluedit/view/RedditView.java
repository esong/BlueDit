package com.yksong.bluedit.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yksong.bluedit.R;
import com.yksong.bluedit.app.BlueDitApp;
import com.yksong.bluedit.model.RedditApiResult;
import com.yksong.bluedit.model.RedditPost;
import com.yksong.bluedit.presenter.RedditPresenter;
import com.yksong.bluedit.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by esong on 2015-11-01.
 */
public class RedditView extends RelativeLayout {
    @Bind(R.id.list) RecyclerView mList;
    @Bind(R.id.refresh) SwipeRefreshLayout mRefreshLayout;
    @BindDimen(R.dimen.divider_padding) float mDividerPadding;
    @Inject RedditPresenter mPresenter;

    private RedditAdapter mAdapter;

    public RedditView(Context context, AttributeSet attrs) {
        super(context, attrs);

        ((BlueDitApp)((Activity) context).getApplication()).getAppComponent().inject(this);
        mPresenter.takeView(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST,
                        mDividerPadding, false));

        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mList.setAdapter(new RedditAdapter(new ArrayList<RedditPost>()));
        request();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });
    }

    private void request() {
        mPresenter.request();
    }

    public void onData(List<RedditPost> posts, int changedPos) {
        mRefreshLayout.setRefreshing(false);
        if (mAdapter == null) {
            mAdapter = new RedditAdapter(posts);
            mList.setAdapter(mAdapter);
        } else {
            if (changedPos == 0) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyItemInserted(changedPos);
            }
        }
    }

    public class RedditAdapter extends RecyclerView.Adapter<RedditAdapter.ViewHolder> {
        List<RedditPost> mPosts;

        public RedditAdapter(List<RedditPost> posts) {
            mPosts = posts;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.title) TextView mTitleView;
            @Bind(R.id.time) TextView mCreatedTimeView;
            @Bind(R.id.author) TextView mAuthorView;
            @Bind(R.id.score) TextView mScore;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RedditPost.Data postData = mPosts.get(getAdapterPosition()).getData();
                        if (postData != null) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(postData.getUrl()));
                            getContext().startActivity(browserIntent);
                        }
                    }
                });

                ButterKnife.bind(this, itemView);
            }

            public void bind (RedditPost post) {
                RedditPost.Data postData = post.getData();
                if (postData != null) {
                    mTitleView.setText(postData.getTitle());
                    mCreatedTimeView.setText(
                            DateUtils.getRelativeTimeSpanString(postData.getCreatedTime() * 1000
                            ));
                    mAuthorView.setText(postData.getAuthor());
                    mScore.setText(postData.getScore());
                }
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.reddit_post_layout, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(mPosts.get(position));

            if (position >= getItemCount() / 2) {
                mPresenter.requestAfter();
            }
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }
    }

}
