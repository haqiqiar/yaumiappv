package id.its.yaumirev_1.fragments;


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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import id.its.yaumirev_1.SessionManager;
import id.its.yaumirev_1.adapter.RecyclerViewAdapter;
import id.its.yaumirev_1.model.Amal;
import id.its.yaumirev_1.GsonRequest;
import id.its.yaumirev_1.model.Ibadah;
import id.its.yaumirev_1.MySingleton;
import id.its.yaumirev_1.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HaveYouDoneItFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HaveYouDoneItFragment extends Fragment {

    //    MyCustomAdapter dataAdapter;
    private View rootView;
    private GridView gridView;
    private RecyclerView recyclerView;

    private String[] idamal;
    private String[] column;
    private String[] satuan;
    private String[] nilai;
    private AsyncTask test;
    private GridLayoutManager ilayout;
    private ProgressBar mProgress;
    private RecyclerViewAdapter rcAdapter;
    private LinearLayout linearLayout;
    SessionManager manager;

    public HaveYouDoneItFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HaveYouDoneItFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HaveYouDoneItFragment newInstance(String param1, String param2) {
        HaveYouDoneItFragment fragment = new HaveYouDoneItFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_have_you_done_it, container, false);
        gridView =(GridView)rootView.findViewById(R.id.gridTarget);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler);
        linearLayout = (LinearLayout)rootView.findViewById(R.id.linearLayoutH);
        linearLayout.setVisibility(View.GONE);
        mProgress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        ilayout = new GridLayoutManager(getContext(),2);


        test = new FetchData().execute("Ok");



        checkButtonClick();

        return rootView;
    }
    private class FetchData extends AsyncTask<String,Integer,Double> {

        @Override
        protected Double doInBackground(String... params) {
            manager = new SessionManager();
            String url = "http://10.151.33.33:8080/yaumiWS/rest/yaumi/today"+"?key="+manager.getPreferences(getContext(),"key");

            GsonRequest jsObjRequest = new GsonRequest(Request.Method.GET,url,Ibadah.class,null,
                    new Response.Listener<Ibadah>() {
                        @Override
                        public void onResponse(Ibadah response) {
                            idamal = new String[response.getAmals().size()];
                            satuan = new String[response.getAmals().size()];
                            column = new String[response.getAmals().size()];
                            nilai = new String[response.getAmals().size()];
                            for (int i= 0;i<response.getAmals().size();i++){
                                Amal amalItem = response.getAmals().get(i);
                                idamal[i] = amalItem.getIdamal();
                                column[i] = amalItem.getNamaamal();
                                nilai[i] = amalItem.getValue();
                                satuan[i] = amalItem.getSatuan();
                            }
                            mProgress.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);

                            recyclerView.setLayoutManager(ilayout);
                            rcAdapter = new RecyclerViewAdapter(getContext(),column,nilai,satuan,1);
                            recyclerView.setAdapter(rcAdapter);


                        }
                    },
                    new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            if(error != null) Log.e("MainActivity", error.getMessage());
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
    private void checkButtonClick() {
        Button myButton = (Button) rootView.findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {
//
            String[] datamu;
            @Override
            public void onClick(View v) {

                String url = "http://10.151.33.33:8080/yaumiWS/rest/yaumi/today/add"+"?key="+manager.getPreferences(getContext(),"key");

                datamu = new String[rcAdapter.getItemCount()];
                datamu = rcAdapter.getAll();

                Ibadah inputku = new Ibadah();
                final List<Amal> amalanku = new ArrayList<Amal>();
//                for (int i=0;i<rcAdapter.getItemCount();i++){
////                    Log.d("Value",rcAdapter.getItem(i));
//                }
                JSONArray arr = new JSONArray();
                for (int i=0;i<rcAdapter.getItemCount();i++) {
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("idamal", idamal[i]);
                        obj.put("namaamal", column[i]);
                        obj.put("value", rcAdapter.getItem(i));
                        obj.put("satuan", satuan[i]);
                        arr.put(obj);
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

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
//                    inputku.setAmal(amalanku);
                // Ready to implement dont know right or wrong


            }
        });

    }
}
