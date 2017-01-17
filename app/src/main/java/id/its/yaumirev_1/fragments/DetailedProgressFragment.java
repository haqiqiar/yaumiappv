package id.its.yaumirev_1.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import id.its.yaumirev_1.SessionManager;
import id.its.yaumirev_1.adapter.ProfileDetailTargetAdapter;
import id.its.yaumirev_1.model.Amal;
import id.its.yaumirev_1.GsonRequest;
import id.its.yaumirev_1.model.Ibadah;
import id.its.yaumirev_1.MySingleton;
import id.its.yaumirev_1.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailedProgressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailedProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedProgressFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView gridView;

    private String[] column;
    private String[] satuan;
    private ProfileDetailTargetAdapter profile;

    private AsyncTask test;
    private ProgressBar mProgress;

    SessionManager manager;
    //    private OnFragmentInteractionListener mListener;

    int[] color = {
            Color.BLUE,Color.RED,Color.YELLOW,Color.BLACK,Color.GREEN
    };

    public DetailedProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailedProgressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailedProgressFragment newInstance(int position) {
        DetailedProgressFragment fragment = new DetailedProgressFragment();
//        Bundle args = new Bundle();
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
        View rootView = inflater.inflate(R.layout.fragment_detailed_progress, container, false);
        gridView =(GridView)rootView.findViewById(R.id.gridTarget);

        mProgress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        test = new FetchData().execute("Ok");
//        gridView.setAdapter(new ProfileDetailTargetAdapter(this.getContext(),column,DESCIPTION_VALUES));

        return rootView;
    }

    private class FetchData extends AsyncTask<String,Integer,Double> {

        @Override
        protected Double doInBackground(String... params) {
            manager = new SessionManager();
            String url = "http://10.151.33.33:8080/yaumiWS/rest/yaumi/progress"+"?key="+manager.getPreferences(getContext(),"key");
            GsonRequest jsObjRequest = new GsonRequest(Request.Method.GET,url,Ibadah.class,null,
                    new Response.Listener<Ibadah>() {
                        @Override
                        public void onResponse(Ibadah response) {

                            column = new String[response.getAmals().size()];
                            String[] value = new String[response.getAmals().size()];
                            satuan = new String[response.getAmals().size()];
                            for (int i= 0;i<response.getAmals().size();i++){
                                Amal amalItem = response.getAmals().get(i);
                                column[i] = amalItem.getNamaamal();
                                value[i] = amalItem.getValue();
                                satuan[i] = amalItem.getSatuan();

//                                System.out.println("Response: "+ column[i]);
                            }
                            mProgress.setVisibility(View.GONE);
                            profile = new ProfileDetailTargetAdapter(getContext(),column,value,color,satuan);
                            gridView.setAdapter(profile);
                            gridView.setVisibility(View.VISIBLE);


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
//            if(cek!=null)
//            {
////                profile = new TargetAdapter(getContext(),cek);
////                listView.setAdapter(profile);
////                listView.setVisibility(View.VISIBLE);
////                Log.d("masuk", "null");
//            }
//            Log.d("masuk", "gak null");
//            Log.d("status async2-->", test.getStatus().toString());
        }
    }

}
