package id.its.yaumirev_1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import id.its.yaumirev_1.R;

/**
 * Created by Zachary on 5/5/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {
    private String[] itemList;
    private Context context;
    private String[] dataBefore;
    private String[] data;
    private String[] satuan;
    private int viewType;

    public RecyclerViewAdapter(Context context, String[] item, String[] dataBefore, String[] satuan, int viewType){
        this.itemList = item;
        this.context = context;
        this.dataBefore = dataBefore;
        this.data = new String[item.length];
        this.data = dataBefore;
        this.satuan = satuan;
        this.viewType = viewType;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_detail_input,null);
        if (this.viewType == 1){
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_detail_input,null);
        }
        else if (this.viewType == 2){
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_input,null);
        }
        RecyclerViewHolders rcv= new RecyclerViewHolders(layoutView,new MyCustomEditTextListener());
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.amalanName.setText(itemList[position]);
        holder.amalanInput.setText(dataBefore[position]);
        holder.amalanSatuan.setText(satuan[position]);
        holder.myCustomEditTextListener.updatePosition(position);
    }

    @Override
    public int getItemCount() {
        return this.data.length;
    }

    public String[] getAll(){
//        Log.d("All : ",data)
        return data;
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
    public String getItem(int position){
        return data[position];
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder{

        public TextView amalanName;
        public EditText amalanInput;
        public TextView amalanSatuan;
        public MyCustomEditTextListener myCustomEditTextListener;

        public RecyclerViewHolders(View itemView,MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            amalanName = (TextView)itemView.findViewById(R.id.textViewInput);
            amalanInput = (EditText)itemView.findViewById(R.id.editTextInput);
            amalanSatuan = (TextView)itemView.findViewById(R.id.textView2);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.amalanInput.addTextChangedListener(myCustomEditTextListener);
        }

    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            Log.d("position: ", String.valueOf(position));
//            Log.d("data ke position",data[position]);

            data[position] = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
