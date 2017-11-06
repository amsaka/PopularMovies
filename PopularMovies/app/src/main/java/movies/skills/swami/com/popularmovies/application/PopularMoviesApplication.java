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

package movies.skills.swami.com.popularmovies.application;

import android.app.Application;

import okhttp3.OkHttpClient;

/**
 * Subclass of Application class to initialize OkHttpClient when the application starts.
 */

public class PopularMoviesApplication extends Application {

    private OkHttpClient mClient;

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttpClient();
    }

    public OkHttpClient getOkHttpClient(){
        return mClient;
    }

    private void initOkHttpClient(){
        mClient = new OkHttpClient();
    }
}
