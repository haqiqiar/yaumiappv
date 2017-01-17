package id.its.yaumirev_1.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import id.its.yaumirev_1.SessionManager;
import id.its.yaumirev_1.adapter.RecyclerViewAdapter;
import id.its.yaumirev_1.adapter.TargetAdapter;
import id.its.yaumirev_1.model.Amal;
import id.its.yaumirev_1.GsonRequest;
import id.its.yaumirev_1.model.Ibadah;
import id.its.yaumirev_1.MySingleton;
import id.its.yaumirev_1.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TargetIbadahFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TargetIbadahFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TargetIbadahFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View rootView;
    private ListView listView;
    private Button button;

    private String[] idamal;
    private String[] cek;
    private String[] satuan;
    private String[] nilai;
    //private static String[] cek;
    private TargetAdapter profile;

    private LinearLayout llEnterTarget;
    private List<EditText> editTextList = new ArrayList<EditText>();
    private List<EditText> editTextList2 = new ArrayList<EditText>();
    private List<EditText> editTextList3 = new ArrayList<EditText>();
    private List<TextView> textViewList = new ArrayList<TextView>();
    private AsyncTask test;
    int _intMyLineCount;
    private ProgressBar mProgress;
    private ScrollView x;
    private Ibadah ib  ;
    private Amal amal;
    private List<Amal> amalan;
    private RecyclerView mRecycler;
    private GridLayoutManager lLayout;
    private RecyclerViewAdapter rcAdapter;
    final Gson gson= new Gson();

    SessionManager manager;

    String url = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TargetIbadahFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TargetIbadahFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TargetIbadahFragment newInstance(String param1, String param2) {
        TargetIbadahFragment fragment = new TargetIbadahFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_target_ibadah, container, false);
//        listView =(ListView) rootView.findViewById(R.id.listView1);
        x =(ScrollView) rootView.findViewById(R.id.targetScroll);
        x.setVisibility(View.GONE);
        mProgress = (ProgressBar)rootView.findViewById(R.id.progressBar);
        mRecycler = (RecyclerView)rootView.findViewById(R.id.mRecycler);
        lLayout = new GridLayoutManager(getContext(),1);
        test = new FetchData().execute("Ok");

        displayAddTarget();
        checkButtonClick();
        return rootView;
    }

    private Response.Listener<Ibadah> createMyRequestErrorListener() {
        return new Response.Listener<Ibadah>(){

            @Override
            public void onResponse(Ibadah response) {

            }
        };
    }

    private class FetchData extends AsyncTask<String,Integer,Double>{

        @Override
        protected Double doInBackground(String... params) {
            manager = new SessionManager();
//            String url = "https://api.myjson.com/bins/3x1tk";
            String url = "http://10.151.33.33:8080/yaumiWS/rest/yaumi/target"+"?key="+manager.getPreferences(getContext(),"key");
            GsonRequest jsObjRequest = new GsonRequest(Request.Method.GET,url,Ibadah.class,null,
                    new Response.Listener<Ibadah>() {
                        @Override
                        public void onResponse(Ibadah response) {
                            idamal = new String[response.getAmals().size()];
                            nilai = new String[response.getAmals().size()];
                            cek = new String[response.getAmals().size()];
                            satuan = new String[response.getAmals().size()];
                            for (int i= 0;i<response.getAmals().size();i++){
                                Amal amalItem = response.getAmals().get(i);
                                idamal[i]=amalItem.getIdamal();
                                cek[i] = amalItem.getNamaamal();
                                nilai[i]= amalItem.getValue();
                                satuan[i] =amalItem.getSatuan();
//                                System.out.println("Response: "+ cek[i]);
//                                Log.d("idamal ",idamal[i]);
//                                Log.d("nama ",cek[i]);
//                                Log.d("nilai ",nilai[i]);
//                                Log.d("satuan ",satuan[i]);
                            }
                            mRecycler.setLayoutManager(lLayout);
                            rcAdapter = new RecyclerViewAdapter(getContext(),cek,nilai,satuan,1);
                            mRecycler.setAdapter(rcAdapter);
                            mProgress.setVisibility(View.GONE);
                            x.setVisibility(View.VISIBLE);


                        }
                    },
                    new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(error != null) Log.e("MainActivity", error.getMessage());
                        }
                    });

            MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
//            Log.d("status async", test.getStatus().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);

        }
    }


    private void displayAddTarget(){
        llEnterTarget = (LinearLayout) rootView.findViewById(R.id.linearView);
        button = (Button)rootView.findViewById(R.id.buttonAdd);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(4, 0, 4, 0);
                llEnterTarget.addView(linearlayout(_intMyLineCount),layoutParams);
                _intMyLineCount++;
            }
        });


    }

    private EditText editText(int _intID) {
        EditText editText = new EditText(getActivity());
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        lparams.setMargins(0,0,0,0);
        editText.setId(_intID);
        editText.setHint("Tulis Disini");
//        editText.setWidth(300);
        editText.setBackgroundColor(Color.WHITE);
        editText.setLayoutParams(lparams);
        editTextList.add(editText);

        return editText;
    }
    private EditText editText2(int _intID) {
        EditText editText = new EditText(getActivity());
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        lparams.setMargins(0, 0, 0, 0);
        editText.setId(_intID);
        editText.setHint("Target apa ");
//        editText.setWidth(400);
        editText.setBackgroundColor(Color.WHITE);
        editTextList2.add(editText);

        return editText;
    }
    private EditText editText3(int _intID) {
        EditText editText = new EditText(getActivity());
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        lparams.setMargins(0,0,0,0);
        editText.setId(_intID);
        editText.setHint("Satuan ");
//        editText.setWidth(400);
        editText.setBackgroundColor(Color.WHITE);
        editTextList3.add(editText);

        return editText;
    }

    private TextView textView(int _intID){
        TextView txtviewAll=new TextView(getActivity());
        txtviewAll.setId(_intID);
        txtviewAll.setText("Step ");
        txtviewAll.setWidth(80);

        txtviewAll.setTextColor(Color.RED);
        txtviewAll.setTypeface(Typeface.DEFAULT_BOLD);
        //textviewList.add(txtviewAll);
        return txtviewAll;
    }
    private LinearLayout linearlayout(int _intID){
        LinearLayout LLMain=new LinearLayout(getActivity());
        LLMain.setId(_intID);

        LLMain.addView(editText(_intID));


        LLMain.addView(editText2(_intID));
        LLMain.addView(editText3(_intID));
        LLMain.setOrientation(LinearLayout.HORIZONTAL);
        //linearlayoutList.add(LLMain);
        return LLMain;

    }

    private void checkButtonClick() {
        Button myButton = (Button) rootView.findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = null;
                String[] datamu;

                url = "http://10.151.33.33:8080/yaumiWS/rest/yaumi/target/add"+"?key="+manager.getPreferences(getContext(),"key");

//                datamu = new String[profile.getCount()];
//                datamu = rcAdapter.getAll();

                Ibadah inputku = new Ibadah();
                final List<Amal> amalanku = new ArrayList<Amal>();
//                for (int i=0;i<profile.getCount();i++){
//                    Log.d("Value",profile.getItemInput(i));
//                }
                for (int i=0;i<rcAdapter.getItemCount();i++){
                    Log.d("Value",rcAdapter.getItem(i));
                }
                JSONArray arr = new JSONArray();
                for (int i=0;i<rcAdapter.getItemCount();i++) {
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("idamal", idamal[i]);
                        obj.put("namaamal", cek[i]);
                        obj.put("value", rcAdapter.getItem(i));
                        obj.put("satuan", satuan[i]);
                        arr.put(obj);
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


                for (int i=0; i< editTextList.size();i++) {
                    Log.i("Edit Text",editTextList.get(i).getText().toString());
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("idamal", 0);
                        obj.put("namaamal", editTextList.get(i).getText().toString());
                        obj.put("value", editTextList2.get(i).getText().toString());
                        obj.put("satuan", editTextList3.get(i).getText().toString());
                        arr.put(obj);
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


//                Log.d("ADD", "SUKSES");

                final String requestBody = arr.toString();
                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return String.format("application/json; charset=utf-8");
                    }
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                    requestBody, "utf-8");
                            return null;
                        }
                    }
                };
                MySingleton.getInstance(getActivity()).addToRequestQueue(sr);

                Toast myToast = Toast.makeText(getContext(),"Sudah Disimpan",Toast.LENGTH_SHORT);
                myToast.show();

            }
        });

    }

}
