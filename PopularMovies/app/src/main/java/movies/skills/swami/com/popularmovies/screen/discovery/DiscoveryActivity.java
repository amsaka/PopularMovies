/**
 * Copyright 2017 Ameya Kadam
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package movies.skills.swami.com.popularmovies.screen.discovery;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import movies.skills.swami.com.popularmovies.R;
import movies.skills.swami.com.popularmovies.application.PopularMoviesApplication;
import movies.skills.swami.com.popularmovies.model.MovieResult;
import movies.skills.swami.com.popularmovies.util.Constants;
import movies.skills.swami.com.popularmovies.util.NetworkClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Class for the discovery screen.
 */

public class DiscoveryActivity extends AppCompatActivity {

    private Spinner mSortSpinner;
    private RecyclerView mMoviesListView;
    private ProgressBar mProgessBar;
    private ArrayList<MovieResult> mMoviesList;
    private MoviesAdapter mAdapter;
    private int mSortBy = -1;
    private MoviesAsyncTask mMoviesAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        initUiWidgets();
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.PARCELABLE_KEY) && savedInstanceState.containsKey(Constants.SORT_BY_KEY)) {
            mSortBy = savedInstanceState.getInt(Constants.SORT_BY_KEY);
            mMoviesList = savedInstanceState.getParcelableArrayList(Constants.PARCELABLE_KEY);
        } else {
            mMoviesList = new ArrayList<>();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.PARCELABLE_KEY, mMoviesList);
        outState.putInt(Constants.SORT_BY_KEY, mSortBy);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        if (mMoviesAsyncTask != null && !mMoviesAsyncTask.isCancelled()) {
            mMoviesAsyncTask.cancel(true);
            mMoviesAsyncTask = null;
        }
        super.onPause();
    }

    private void initUiWidgets() {
        mSortSpinner = (Spinner) findViewById(R.id.sort_order_spinner);
        mMoviesListView = (RecyclerView) findViewById(R.id.movies_list);
        mProgessBar = (ProgressBar) findViewById(R.id.progress_bar);
        mSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mSortSpinner.setSelection(position);
                if (mSortBy == -1 || mSortBy != position) {
                    mSortBy = position;
                    mProgessBar.setVisibility(View.VISIBLE);
                    mMoviesAsyncTask = new MoviesAsyncTask();
                    mMoviesAsyncTask.execute(position);
                } else {
                    mAdapter = new MoviesAdapter(DiscoveryActivity.this, mMoviesList);
                    mMoviesListView.setLayoutManager(new GridLayoutManager(DiscoveryActivity.this, Constants.NO_OF_COLUMNS));
                    mMoviesListView.setAdapter(mAdapter);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSortSpinner.setSelection(mSortBy);

    }

    private String constructUrl(int pos) {
        String sortBy = pos == 0 ? Constants.BY_POPULAR : Constants.BY_TOP_RATED;
        return Constants.BASE_API_URL + sortBy + Constants.QUERY_STRING + Constants.API_KEY + Constants.API_KEY_VALUE + Constants.OTHER_PARAMETERS;
    }

    /**
     * AsyncTask to make API request to fetch movies.
     */
    class MoviesAsyncTask extends AsyncTask<Integer, Void, ArrayList<MovieResult>> {

        @Override
        protected ArrayList<MovieResult> doInBackground(Integer... integers) {
            int position = integers[0];
            String url = constructUrl(position);
            OkHttpClient client = ((PopularMoviesApplication) getApplication()).getOkHttpClient();
            Request request = NetworkClient.buildNetworkRequest(url);
            String response = Constants.EMPTY_STRING;
            try {
                response = NetworkClient.getApiResponse(request, client);
                mMoviesList = NetworkClient.extractMovies(response);
            } catch (IOException e) {
                mMoviesList = null;
            }
            return mMoviesList;
        }

        
        @Override
        protected void onPostExecute(ArrayList<MovieResult> movieResults) {
            super.onPostExecute(movieResults);
            if (!isCancelled()) {
                if (movieResults == null) {
                    Toast.makeText(DiscoveryActivity.this, Constants.SOMETHING_WRONG, Toast.LENGTH_LONG).show();
                    mMoviesList = new ArrayList<>();
                }
                mAdapter = new MoviesAdapter(DiscoveryActivity.this, mMoviesList);
                mMoviesListView.setLayoutManager(new GridLayoutManager(DiscoveryActivity.this, Constants.NO_OF_COLUMNS));
                mMoviesListView.setAdapter(mAdapter);
                mProgessBar.setVisibility(View.GONE);
            }

        }
    }
}

