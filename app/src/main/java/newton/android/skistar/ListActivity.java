package newton.android.skistar;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;

import newton.android.skistar.Json.GetJson;
import newton.android.skistar.Models.ListAdapter;
import newton.android.skistar.ViewModels.ViewModel;
import newton.android.skistar.databinding.ActivityListBinding;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    public static ListAdapter adapter;

    GetJson getJson = new GetJson(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewModel viewmodel = new ViewModel(this);
        super.onCreate(savedInstanceState);

        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.setViewModel(viewmodel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.list_header, listView, false);

        listView.addHeaderView(header, null, false);
        listView.setAdapter(adapter);

        populateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                try {
                    refreshActionButton();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.action_settings:
                Intent intent = new Intent(ListActivity.this, SettingsActivity.class);
                ListActivity.this.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void populateListView() {

        adapter = new ListAdapter(GetJson.runs, getApplicationContext());
        listView.setAdapter(adapter);
    }

    public void refreshActionButton() throws InterruptedException {
        getJson.loadJson();

        populateListView();
    }
}
