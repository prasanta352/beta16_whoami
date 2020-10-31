package com.example.oflinemaphelper.Activity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DirectionProvider {
    private static final String TAG = "DirectionProvider";
    private static final String MAP_BOX_ACCESS_TOKEN = "pk.eyJ1IjoicHJhc2FudGExIiwiYSI6ImNqZWU4NGp1YTE4MWQycm1rODh0NmRiM2gifQ.X3sjLdGHxi5LOHo26YnibQ";

    public void getGeoCode(final String place_name, final OnLocationWatcher onLocationWatcher) {
        Log.d(TAG, "getGeoCode: ");
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    String url = String.format("https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?access_token=%s", URLEncoder.encode(place_name), MAP_BOX_ACCESS_TOKEN);

                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    String body;// "{\"type\":\"FeatureCollection\",\"query\":[\"raghubir\",\"singh\",\"junior\",\"modern\",\"school\",\"dilhi\",\"india\"],\"features\":[{\"id\":\"poi.1108101567404\",\"type\":\"Feature\",\"place_type\":[\"poi\"],\"relevance\":0.95582,\"properties\":{\"foursquare\":\"4dbb8381f7b1ab37dd47ba3b\",\"landmark\":true,\"address\":\"Humayun Road\",\"category\":\"education, school\",\"maki\":\"school\"},\"text\":\"Raghubir Singh Junior Modern School\",\"place_name\":\"Raghubir Singh Junior Modern School, Humayun Road, New Delhi, Delhi, India\",\"center\":[77.2274,28.603701],\"geometry\":{\"coordinates\":[77.2274,28.603701],\"type\":\"Point\"},\"context\":[{\"id\":\"locality.4977268799794990\",\"text\":\"Pandara Road Flats\"},{\"id\":\"place.8915687851165670\",\"wikidata\":\"Q987\",\"text\":\"New Delhi\"},{\"id\":\"district.6350047862165670\",\"wikidata\":\"Q987\",\"text\":\"New Delhi\"},{\"id\":\"region.9972194009026890\",\"wikidata\":\"Q1353\",\"short_code\":\"IN-DL\",\"text\":\"Delhi\"},{\"id\":\"country.2782945337\",\"wikidata\":\"Q668\",\"short_code\":\"in\",\"text\":\"India\"}]},{\"id\":\"locality.8252052999863780\",\"type\":\"Feature\",\"place_type\":[\"locality\"],\"relevance\":0.546429,\"properties\":{},\"text\":\"Raghubir Nagar\",\"place_name\":\"Raghubir Nagar, New Delhi, West Delhi, Delhi, India\",\"bbox\":[77.10521112,28.65217896,77.12061408,28.66135104],\"center\":[77.110534,28.656931],\"geometry\":{\"type\":\"Point\",\"coordinates\":[77.110534,28.656931]},\"context\":[{\"id\":\"place.8915687851165670\",\"wikidata\":\"Q987\",\"text\":\"New Delhi\"},{\"id\":\"district.17560998141842890\",\"wikidata\":\"Q549807\",\"text\":\"West Delhi\"},{\"id\":\"region.9972194009026890\",\"wikidata\":\"Q1353\",\"short_code\":\"IN-DL\",\"text\":\"Delhi\"},{\"id\":\"country.2782945337\",\"wikidata\":\"Q668\",\"short_code\":\"in\",\"text\":\"India\"}]},{\"id\":\"locality.15375253203008540\",\"type\":\"Feature\",\"place_type\":[\"locality\"],\"relevance\":0.481812,\"properties\":{},\"text\":\"Raghubar\",\"place_name\":\"Raghubar, Kunda, Pratapgarh, Uttar Pradesh, India\",\"bbox\":[81.5032262367543,25.8190602798821,81.5181055486208,25.8368329466945],\"center\":[81.510083,25.827924],\"geometry\":{\"type\":\"Point\",\"coordinates\":[81.510083,25.827924]},\"context\":[{\"id\":\"place.11054311761439980\",\"wikidata\":\"Q2413194\",\"text\":\"Kunda\"},{\"id\":\"district.5683953046427990\",\"wikidata\":\"Q1473962\",\"text\":\"Pratapgarh\"},{\"id\":\"region.11049584031190310\",\"wikidata\":\"Q1498\",\"short_code\":\"IN-UP\",\"text\":\"Uttar Pradesh\"},{\"id\":\"country.2782945337\",\"wikidata\":\"Q668\",\"short_code\":\"in\",\"text\":\"India\"}]},{\"id\":\"place.9972194009026890\",\"type\":\"Feature\",\"place_type\":[\"region\",\"place\"],\"relevance\":0.472487,\"properties\":{\"wikidata\":\"Q1353\",\"short_code\":\"IN-DL\"},\"text\":\"Delhi\",\"place_name\":\"Delhi, India\",\"bbox\":[76.8388830269287,28.4042620003073,77.3464387601731,28.8835889894397],\"center\":[77.21667,28.66667],\"geometry\":{\"type\":\"Point\",\"coordinates\":[77.21667,28.66667]},\"context\":[{\"id\":\"country.2782945337\",\"wikidata\":\"Q668\",\"short_code\":\"in\",\"text\":\"India\"}]},{\"id\":\"place.16178038123511540\",\"type\":\"Feature\",\"place_type\":[\"place\"],\"relevance\":0.472487,\"properties\":{},\"text\":\"Singa\",\"place_name\":\"Singa, Arunachal Pradesh, India\",\"bbox\":[94.965866,28.674243,95.395913,28.963881],\"center\":[95.168019,28.82169],\"geometry\":{\"type\":\"Point\",\"coordinates\":[95.168019,28.82169]},\"context\":[{\"id\":\"district.2419783850346030\",\"wikidata\":\"Q15465\",\"text\":\"Upper Siang\"},{\"id\":\"region.2994961998\",\"wikidata\":\"Q1162\",\"short_code\":\"IN-AR\",\"text\":\"Arunachal Pradesh\"},{\"id\":\"country.2782945337\",\"wikidata\":\"Q668\",\"short_code\":\"in\",\"text\":\"India\"}]}],\"attribution\":\"NOTICE: © 2020 Mapbox and its suppliers. All rights reserved. Use of this data is subject to the Mapbox Terms of Service (https://www.mapbox.com/about/maps/). This response and the information it contains may not be retained. POI(s) provided by Foursquare.\"}";
                    ResponseBody rb = response.body();
                    if (rb != null) {
                        body = rb.string();

                        Log.d(TAG, "getGeoCode: url ->" + url + " body ->" + body);

                        // data.features[0].center[longitude, latitude]

                        JSONArray features = new JSONObject(body).getJSONArray("features");
                        JSONArray center = new JSONObject(features.get(0).toString()).getJSONArray("center");

                        double longitude = center.getDouble(0);
                        double latitude = center.getDouble(1);

                        Log.d(TAG, "run: longitude->" + longitude + " latitude ->" + latitude);
                        onLocationWatcher.OnLocationGet(new Location(longitude, latitude));

                    } else {
                        Log.d(TAG, "body: null");
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "getGeoCode: ", ex);
                }
            }
        });
        thread.start();
    }

    public void getDirection(final Location from, final Location to, final OnDirectionWatcher onDirectionWatcher) {
        Log.d(TAG, "getGeoCode: ");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = String.format(
                            "https://api.mapbox.com/directions/v5/mapbox/cycling/%s,%s;%s,%s?steps=true&voice_instructions=true&banner_instructions=true&voice_units=imperial&waypoint_names=Home;Work&access_token=%s",
                            from.longitude, from.latitude, to.longitude, to.latitude, MAP_BOX_ACCESS_TOKEN
                    );

                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    String body;
                    ResponseBody rb = response.body();
                    if (rb != null) {
                        body = rb.string();

                        Log.d(TAG, "getGeoCode: body" + body);

                        // data.routes[0: selected_route].legs[0:firstLeg].steps

                        JSONArray routes = new JSONObject(body).getJSONArray("routes");
                        JSONArray legs = new JSONObject(routes.get(0).toString()).getJSONArray("legs");
                        JSONArray stepsUnFil = new JSONObject(legs.get(0).toString()).getJSONArray("steps");
                        ArrayList<String> steps = new ArrayList<>();

                        for (int i = 0; i < stepsUnFil.length(); i++) {
                            JSONArray voiceInstructions = new JSONObject(stepsUnFil.getJSONObject(i).toString()).getJSONArray("voiceInstructions");
                            for (int j = 0; j < voiceInstructions.length(); j++) {
                                String step = new JSONObject(voiceInstructions.get(j).toString()).getString("announcement");
                                steps.add(step);
                                Log.d(TAG, String.format("run: step -> %d %s", steps.size(), step));
                            }

                        }

                        onDirectionWatcher.OnStepsGet(steps);

                    } else {
                        Log.d(TAG, "body: null");
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "getGeoCode: ", ex);
                }
            }
        });
        thread.start();
    }

    interface OnLocationWatcher {
        void OnLocationGet(Location location);
    }

    interface OnDirectionWatcher {
        void OnStepsGet(ArrayList<String> steps);
    }

    static class Location {
        public double longitude;
        public double latitude;

        public Location(double lon, double lat) {
            this.longitude = lon;
            this.latitude = lat;
        }
    }

}
