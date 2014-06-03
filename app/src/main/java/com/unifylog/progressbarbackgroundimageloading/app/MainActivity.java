package com.unifylog.progressbarbackgroundimageloading.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        supportRequestWindowFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends ListFragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            String[] countries = new String[] {
            };
            ArrayAdapter<String> adapter = new LocationAdapter(inflater.getContext(), countries);
            new AsyncTask<Void,Void,Void>(){

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                       Log.w(TAG, e);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    String[] countries = new String[] {
                            "India",
                            "Pakistan",
                            "Sri Lanka",
                            "China",
                            "Bangladesh",
                            "Nepal",
                            "Afghanistan",
                            "North Korea",
                            "South Korea",
                            "Japan"
                    };
                    setListAdapter(new LocationAdapter(getActivity(), countries));
                }
            }.execute();

            setListAdapter(adapter);
            return rootView;
        }
    }

    private int asynchCounter = 0;
    private void updateCounter(int delta){
        asynchCounter+=delta;
        Log.i(TAG, "asynchCounter=" + asynchCounter);
        if(asynchCounter<=0){
            setSupportProgressBarIndeterminateVisibility(false);
        }else{
            setSupportProgressBarIndeterminateVisibility(true);
        }
    }

    class LocationAdapter extends ArrayAdapter<String>{

        LocationAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView image = (TextView) convertView.findViewById(R.id.image);
            title.setText(getItem(position));
            new CountedBitmapWorkerTask(image).execute();

            return convertView;
        }
    }


    class CountedBitmapWorkerTask extends AsyncTask<Void,Void,Void> {

        private TextView imageTextView;

        CountedBitmapWorkerTask(TextView textView){

            imageTextView = textView;
        }

        @Override
        protected Void doInBackground(Void... params) {


                    try {
                        Log.i(TAG, "Sleeping...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.w(TAG, e);
                    }
                    return null;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            updateCounter(1);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            imageTextView.setText("Image downloaded");
            updateCounter(-1);

        }
    }


}
