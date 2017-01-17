package id.its.yaumirev_1.fragments;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.its.yaumirev_1.SessionManager;
import id.its.yaumirev_1.model.Amal;
import id.its.yaumirev_1.GsonRequest;
import id.its.yaumirev_1.model.IbadahHarian;
import id.its.yaumirev_1.MySingleton;
import id.its.yaumirev_1.R;
import id.its.yaumirev_1.model.Tanggal;
import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
/**
 * A placeholder fragment containing a simple view.
 */
public class ChartProgressFragment extends Fragment {
    private SessionManager manager;
    int[] color = {
            Color.BLUE,Color.RED,Color.YELLOW,Color.BLACK,Color.GREEN,Color.WHITE
    };

    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;
    private int max;
    private int max2;
    private int valueYMax;
    private int valueXMax;
    private int[][][] point;
    private int[][] tanggal;
    private AsyncTask test;

    public ChartProgressFragment() {
    }

    public static ChartProgressFragment newInstance() {
        ChartProgressFragment fragment = new ChartProgressFragment();
        Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart_progress, container, false);

        chart = (LineChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());

        // Generate some random values.
        test = new FetchData().execute("Ok");

        // Disable viewport recalculations, see toggleCubic() method for more info.
        chart.setViewportCalculationEnabled(false);

        resetViewport(max);

        return rootView;
    }

    private class FetchData extends AsyncTask<String,Integer,Double> {

        @Override
        protected Double doInBackground(String... params) {
            manager = new SessionManager();
            String url = "http://10.151.33.33:8080/yaumiWS/rest/yaumi/monthly"+"?key="+manager.getPreferences(getContext(),"key");
            GsonRequest jsObjRequest = new GsonRequest(Request.Method.GET,url,IbadahHarian.class,null,
                    new Response.Listener<IbadahHarian>() {
                        @Override
                        public void onResponse(IbadahHarian response) {
                            Tanggal tgl;
                            Amal amal;
                            String[] nama=null;
                            max = 0;
                            max2 = 0;
                            valueYMax = 0;
                            valueXMax = 0;

                            for (int i=0;i<response.getTgl().size();i++){
                                tgl = response.getTgl().get(i);
                                if (valueXMax < Integer.parseInt(tgl.getIdtgl()) )
                                {
                                    valueXMax = Integer.parseInt(tgl.getIdtgl());
                                }
                                nama = new String[tgl.getAmal().size()];
                                max = tgl.getAmal().size();
                                for (int j=0;j<tgl.getAmal().size();j++){
                                    amal = tgl.getAmal().get(j);
                                    if (valueYMax < Integer.parseInt(amal.getValue())){
                                        valueYMax = Integer.parseInt(amal.getValue());
                                    }
                                    nama[j] =  amal.getNamaamal();
                                }
                            }
                            max2 = response.getTgl().size();
                            tanggal = new int[max][response.getTgl().size()];
                            point = new int[max][response.getTgl().size()][response.getTgl().size()];
//                            Log.d("Max: ", String.valueOf(max));
//
//                            line = new Line[max];
//                            values = new ArrayList<PointValue>();
                            for (int x=0;x<max;x++){
//                                Log.d("Nama: ",nama[x]);
//                                values = new ArrayList<PointValue>();
                                for (int i=0;i<response.getTgl().size();i++){
                                    tgl = response.getTgl().get(i);

                                    for (int j=0;j<tgl.getAmal().size();j++){
                                        amal = tgl.getAmal().get(j);
                                        if (nama[x].equals(amal.getNamaamal())){
//                                            Log.d("nama: ",amal.getNamaamal());
//                                            Log.d("ValuePoint: ",tgl.getIdtgl()+" "+amal.getValue());
                                            tanggal[x][i] = Integer.parseInt(tgl.getIdtgl());
                                            point[x][i][j] = Integer.parseInt(amal.getValue());
//                                            Log.d("Paint: ", tanggal[x][i]+" "+String.valueOf(point[x][i][j]));
//                                            values.add(new PointValue(Float.parseFloat(tgl.getIdtgl()),Float.parseFloat(amal.getValue())));
                                        }
                                    }
                                }


//                                Log.d("Something","+++++++++++++++++++++++++++");
//                                line[x] =new Line(values).setColor(color[x]).setCubic(true);
//                                Log.d("Line ", String.valueOf(line[x]));
//                                lines.add(line[0]);
                            }

//                            Log.d("Lines ", String.valueOf(lines));
//                            generateData();
//                            =====================================


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
            generateData();
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



    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 100f;
            }
        }
    }

    private void reset() {
        numberOfLines = 1;

        hasAxes = true;
        hasAxesNames = true;
        hasLines = true;
        hasPoints = true;
        shape = ValueShape.CIRCLE;
        isFilled = false;
        hasLabels = false;
        isCubic = false;
        hasLabelForSelected = false;
        pointsHaveDifferentColor = false;

        chart.setValueSelectionEnabled(hasLabelForSelected);
        resetViewport(max);
    }

    private void resetViewport(int max) {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = valueYMax + 2;
        v.left = 0;
        v.right = valueXMax + 2;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }

    private void generateData() {

        List<Line> lines = new ArrayList<Line>();
        Line[] line=new Line[max] ;
        List<ArrayList<PointValue>> value = new ArrayList<ArrayList<PointValue>>();
        for (int x=0;x<max;x++){
            value.add(new ArrayList<PointValue>());
        }
        int y =0;
        int flag[] = new int[31];
        for (int x = 0; x < max; ++x) {
            y=0;
//            flag = new int[31];
            Arrays.fill(flag,0);

            for (int i = 0; i < max2; ++i) {
                int poin = 0;
                for (int j =0 ;j<max;j++){

                    if (point[x][i][j]>0){
                        poin = point[x][i][j];
                    }
                    else {
                        poin = point[x][i][j];
                    }
//                    value.get(x).add(y, new PointValue(tanggal[x][i],poin));
//                        flag[tanggal[x][i]] = -1;
//                    y++;
//                    List<PointValue> values = new ArrayList<PointValue>();
//                    Log.d("Point : ",x+" "+tanggal[x][i]+" "+point[x][i][j]);
                    if (point[x][i][j]!=0 && flag[tanggal[x][i]]==0 ) {
//                        Log.d("Point : ",x+" "+flag[tanggal[x][i]]+" "+tanggal[x][i]+" "+point[x][i][j]);
                        value.get(x).add(y, new PointValue(tanggal[x][i], point[x][i][j]));
                        flag[tanggal[x][i]] = -1;
                        y++;
                    }
                }
                if (flag[tanggal[x][i]]!=-1){
//                    Log.d("Point : ",x+" "+flag[tanggal[x][i]]+" "+tanggal[x][i]+" "+point[x][i][j]);
                    value.get(x).add(y, new PointValue(tanggal[x][i],0));
                    flag[tanggal[x][i]] = -1;
                    y++;
                }
//                value.get(x).add(y, new PointValue(tanggal[x][i],poin));
//                y++;

            }
            line[x] = new Line(value.get(x));
//            Line line = new Line(values);
            line[x].setColor(ChartUtils.COLORS[x]);
            line[x].setShape(shape);
            line[x].setCubic(isCubic);
            line[x].setFilled(isFilled);
            line[x].setHasLabels(hasLabels);
            line[x].setHasLabelsOnlyForSelected(hasLabelForSelected);
            line[x].setHasLines(hasLines);
            line[x].setHasPoints(hasPoints);
            if (pointsHaveDifferentColor){
                line[x].setPointColor(ChartUtils.COLORS[(x + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line[x]);
        }

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis().setTextColor(Color.WHITE);
            Axis axisY = new Axis().setHasLines(true).setTextColor(Color.WHITE);
            if (hasAxesNames) {
                axisX.setName("Tanggal");
                axisY.setName("Jumlah Ibadah");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

    }

    /**
     * Adds lines to data, after that data should be set again with
     * {@link LineChartView#setLineChartData(LineChartData)}. Last 4th line has non-monotonically x values.
     */
    private void addLineToData() {
        if (data.getLines().size() >= maxNumberOfLines) {
            Toast.makeText(getActivity(), "Samples app uses max 4 lines!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ++numberOfLines;
        }

        generateData();
    }

    private void toggleLines() {
        hasLines = !hasLines;

        generateData();
    }

    private void togglePoints() {
        hasPoints = !hasPoints;

        generateData();
    }

    private void toggleCubic() {
        isCubic = !isCubic;

        generateData();

        if (isCubic) {
            // It is good idea to manually set a little higher max viewport for cubic lines because sometimes line
            // go above or below max/min. To do that use Viewport.inest() method and pass negative value as dy
            // parameter or just set top and bottom values manually.
            // In this example I know that Y values are within (0,100) range so I set viewport height range manually
            // to (-5, 105).
            // To make this works during animations you should use Chart.setViewportCalculationEnabled(false) before
            // modifying viewport.
            // Remember to set viewport after you call setLineChartData().
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = -5;
            v.top = 105;
            // You have to set max and current viewports separately.
            chart.setMaximumViewport(v);
            // I changing current viewport with animation in this case.
            chart.setCurrentViewportWithAnimation(v);
        } else {
            // If not cubic restore viewport to (0,100) range.
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = 0;
            v.top = 100;

            // You have to set max and current viewports separately.
            // In this case, if I want animation I have to set current viewport first and use animation listener.
            // Max viewport will be set in onAnimationFinished method.
            chart.setViewportAnimationListener(new ChartAnimationListener() {

                @Override
                public void onAnimationStarted() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationFinished() {
                    // Set max viewpirt and remove listener.
                    chart.setMaximumViewport(v);
                    chart.setViewportAnimationListener(null);

                }
            });
            // Set current viewpirt with animation;
            chart.setCurrentViewportWithAnimation(v);
        }

    }

    private void toggleFilled() {
        isFilled = !isFilled;

        generateData();
    }

    private void togglePointColor() {
        pointsHaveDifferentColor = !pointsHaveDifferentColor;

        generateData();
    }

    private void setCircles() {
        shape = ValueShape.CIRCLE;

        generateData();
    }

    private void setSquares() {
        shape = ValueShape.SQUARE;

        generateData();
    }

    private void setDiamonds() {
        shape = ValueShape.DIAMOND;

        generateData();
    }

    private void toggleLabels() {
        hasLabels = !hasLabels;

        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }

        generateData();
    }

    private void toggleLabelForSelected() {
        hasLabelForSelected = !hasLabelForSelected;

        chart.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelForSelected) {
            hasLabels = false;
        }

        generateData();
    }

    private void toggleAxes() {
        hasAxes = !hasAxes;

        generateData();
    }

    private void toggleAxesNames() {
        hasAxesNames = !hasAxesNames;

        generateData();
    }

    /**
     * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
     * method(don't confuse with View.animate()). If you operate on data that was set before you don't have to call
     * {@link LineChartView#setLineChartData(LineChartData)} again.
     */
    private void prepareDataAnimation() {
        for (Line line : data.getLines()) {
            for (PointValue value : line.getValues()) {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                value.setTarget(value.getX(), (float) Math.random() * 100);
            }
        }
    }

    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

}