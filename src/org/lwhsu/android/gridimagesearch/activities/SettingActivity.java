package org.lwhsu.android.gridimagesearch.activities;

import org.lwhsu.android.gridimagesearch.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends Activity {
        private Spinner spSize;
        private Spinner spColor;
        private Spinner spType;
        private EditText etSiteFilter;

        private String argSize;
        private String argColor;
        private String argType;
        private String argSiteFilter;

        private final String[] optionsSize = {"small", "medium", "large", "xlarge"};
        private final String[] optionsColor = {"black", "blue", "brown", "gray", "green", "orange", "pink", "purple", "red", "teal", "white", "yellow"};
        private final String[] optionsType = {"face", "photo", "clipart", "lineart"};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setupViews();
    }

    private void setupViews() {
        spSize = (Spinner) findViewById(R.id.spSize);
        final ArrayAdapter<CharSequence> adapterSize = ArrayAdapter.createFromResource(this,
                R.array.size_array, android.R.layout.simple_spinner_item);
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSize.setAdapter(adapterSize);
        spSize.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                argSize = optionsSize[position];
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });

        spColor = (Spinner) findViewById(R.id.spColor);
        final ArrayAdapter<CharSequence> adapterColor = ArrayAdapter.createFromResource(this,
                R.array.color_array, android.R.layout.simple_spinner_item);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColor.setAdapter(adapterColor);
        spColor.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                argColor = optionsColor[position];
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });

        spType = (Spinner) findViewById(R.id.spType);
        final ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapterType);
        spType.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                argType = optionsType[position];
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });

        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
    }

    public void onSettingSave(final View v) {
        argSiteFilter = etSiteFilter.getText().toString();
        final String str = argSize + ' ' + argColor + ' ' + argType + ' ' + argSiteFilter;
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
