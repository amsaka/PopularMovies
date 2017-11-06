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

package movies.skills.swami.com.popularmovies.screen.details;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import movies.skills.swami.com.popularmovies.R;
import movies.skills.swami.com.popularmovies.model.MovieResult;
import movies.skills.swami.com.popularmovies.util.Constants;

/**
 * Class for the details screen.
 */

public class DetailsActivity extends AppCompatActivity{

    private TextView mTitleView;
    private TextView mReleaseDateView;
    private TextView mVoteAverageView;
    private TextView mSynopsisView;
    private ImageView mPosterView;
    private MovieResult mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initUiWidgets();
        mResult = getIntent().getParcelableExtra(Constants.PARCELABLE_KEY);
        if(savedInstanceState!=null && savedInstanceState.containsKey(Constants.PARCELABLE_KEY) || mResult!=null){
            displayValuesUi();
        }
    }

    private void displayValuesUi() {
        Picasso.with(this).load(Constants.BASE_IMAGE_URL + Constants.IMAGE_SIZE + mResult.getPosterPath()).into(mPosterView);
        mTitleView.setText(mResult.getTitle());
        mVoteAverageView.setText(mResult.getVoteAverage()+"");
        mReleaseDateView.setText(mResult.getReleaseDate());
        mSynopsisView.setText(mResult.getOverview());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(Constants.PARCELABLE_KEY, mResult);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void initUiWidgets() {
        mTitleView = (TextView) findViewById(R.id.movie_title);
        mReleaseDateView = (TextView) findViewById(R.id.release_date);
        mVoteAverageView = (TextView) findViewById(R.id.user_rating);
        mSynopsisView = (TextView) findViewById(R.id.synopsis);
        mPosterView = (ImageView) findViewById(R.id.movie_poster);

    }
}
