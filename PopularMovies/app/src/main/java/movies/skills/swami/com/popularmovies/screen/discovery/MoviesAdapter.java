/**
 * Copyright 2017 Ameya Kadam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package movies.skills.swami.com.popularmovies.screen.discovery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movies.skills.swami.com.popularmovies.R;
import movies.skills.swami.com.popularmovies.model.MovieResult;
import movies.skills.swami.com.popularmovies.screen.details.DetailsActivity;
import movies.skills.swami.com.popularmovies.util.Constants;

/**
 * Class for creating a custom adapter for the recycler view.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<MovieResult> mResults;
    private Context mContext;
    private LayoutInflater mInflater;

    public MoviesAdapter(Context context, List<MovieResult> results) {
        mContext = context;
        mResults = results;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_movie, parent, false);
        MovieViewHolder holder = new MovieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
        final int pos = position;
        holder.mPosterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra(Constants.PARCELABLE_KEY, mResults.get(pos));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView mPosterView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mPosterView = (ImageView) itemView.findViewById(R.id.movie_item_image);
        }

        void bind(int position) {
            Picasso.with(mContext).load(Constants.BASE_IMAGE_URL + Constants.IMAGE_SIZE + mResults.get(position).getPosterPath()).into(mPosterView);
        }


    }
}
