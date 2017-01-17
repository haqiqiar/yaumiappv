package id.its.yaumirev_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import id.its.yaumirev_1.R;

/**
 * Created by Zachary on 4/10/2016.
 */
public class TargetAdapter extends BaseAdapter {
    private Context context;
    private String[] mobileValues;
    private String[] target;

    private String[] satuan = null;
    private EditText[] inputCollection;

    public TargetAdapter(Context context, String[] mobileValues,String[] target,String[] targetPercent) {
        this.context = context;
        this.mobileValues = mobileValues;
        inputCollection = new EditText[getCount()];
        this.satuan = targetPercent;
        this.target = target;
    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

//        System.out.println(mobileValues[1]+ " " + mobileValues[2]);
        if (convertView == null){
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.list_detail_input,null);

            TextView textView = (TextView) gridView.findViewById(R.id.textViewInput);
            textView.setText(mobileValues[position]);

        }else {
            gridView = (View) convertView;
        }

        EditText editText = (EditText) gridView.findViewById(R.id.editTextInput);
        editText.setText(target[position]);
        inputCollection[position] = editText;

        return gridView;
    }
    public String getItemInput(int position){
//        System.out.println(inputCollection[position]);
//        String mobile = mobileValues[position];
        return inputCollection[position].getText().toString();
    }

    public String getItemPosition(int position){
        return mobileValues[position];
    }
}

