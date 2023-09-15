package com.video.music.downloader.statusandgallery.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.video.music.downloader.R;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private ArrayList<String> data;
    private final String server = "http://suggestqueries.google.com/complete/search?client=firefox&q=";
    private Context mContext;


    public AutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        mContext = context;
        this.data = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        try {
            return data.get(position);
        } catch (Exception ex) {
            return "";
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    HttpURLConnection conn = null;
                    InputStream input = null;
                    try {
                        if (!constraint.toString().startsWith("http://") && !constraint.toString().startsWith("https://") && !constraint.toString().equals(mContext.getResources().getString(R.string.home))) {
                            URL url = new URL(server + constraint.toString());
                            conn = (HttpURLConnection) url.openConnection();
                            input = conn.getInputStream();
                            InputStreamReader reader = new InputStreamReader(input, "UTF-8");
                            BufferedReader buffer = new BufferedReader(reader, 8192);
                            StringBuilder builder = new StringBuilder();
                            String line;
                            while ((line = buffer.readLine()) != null) {
                                builder.append(line);
                            }
                            JSONArray terms = new JSONArray(builder.toString());
                            JSONArray suggessions = terms.getJSONArray(1);
                            ArrayList<String> suggestions = new ArrayList<>();
                            for (int ind = 0; ind < suggessions.length(); ind++) {
                                String term = suggessions.getString(ind);
                                suggestions.add(term);
                            }
                            results.values = suggestions;
                            results.count = suggestions.size();
                            data = suggestions;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        if (input != null) {
                            try {
                                input.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (conn != null) conn.disconnect();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else notifyDataSetInvalidated();
            }
        };
    }

}
