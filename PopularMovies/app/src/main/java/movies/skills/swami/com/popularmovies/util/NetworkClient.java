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

package movies.skills.swami.com.popularmovies.util;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import movies.skills.swami.com.popularmovies.model.MovieResult;
import movies.skills.swami.com.popularmovies.model.MovieResults;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class for building url for API and fetching the response using OkHttpClient.
 */

public class NetworkClient {

    public static Request buildNetworkRequest(String url){
        Request request = new Request.Builder().url(url).build();
        return request;
    }

    public static String getApiResponse(Request request, OkHttpClient client) throws IOException{
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static ArrayList<MovieResult> extractMovies(String response) {
        Gson gson = new Gson();
        MovieResults movieResults = gson.fromJson(response, MovieResults.class);
        ArrayList<MovieResult> movies = new ArrayList<>();
        movies.addAll(movieResults.getResults());
        return movies;
    }
}
