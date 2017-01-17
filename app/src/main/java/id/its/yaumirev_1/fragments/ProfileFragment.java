package id.its.yaumirev_1.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import id.its.yaumirev_1.adapter.ProfileDetailTargetAdapter;
import id.its.yaumirev_1.model.Amal;
import id.its.yaumirev_1.GsonRequest;
import id.its.yaumirev_1.model.Ibadah;
import id.its.yaumirev_1.MySingleton;
import id.its.yaumirev_1.R;
import id.its.yaumirev_1.SessionManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    int[] color = {
            Color.BLUE,Color.RED,Color.YELLOW,Color.BLACK,Color.GREEN
    };


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView gridView;
    private String[] column;
    private ProfileDetailTargetAdapter profile;
    private AsyncTask test;
    private ProgressBar mProgress;
    private LinearLayout linearLayout;
    private TextView name;
    private TextView nrp;

    SessionManager manager;


//    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        manager = new SessionManager();
        linearLayout = (LinearLayout)rootView.findViewById(R.id.linearLayoutTarget);
        linearLayout.setVisibility(View.GONE);
        gridView =(GridView)rootView.findViewById(R.id.gridTarget);
        mProgress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        test = new FetchData().execute("Ok");
        name = (TextView) rootView.findViewById(R.id.name);
        nrp = (TextView) rootView.findViewById(R.id.some);
        name.setText(manager.getPreferences(getActivity(),"user"));
        nrp.setText(manager.getPreferences(getActivity(),"nrp"));

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
                            String[] satuan = new String[response.getAmals().size()];
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
                            linearLayout.setVisibility(View.VISIBLE);
                            gridView.setVisibility(View.VISIBLE);


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

//            Log.d("masuk", "gak null");
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
