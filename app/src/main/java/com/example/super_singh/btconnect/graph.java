package com.example.super_singh.btconnect;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class graph extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        double x=10.00;

        double [] y_coordinates=new double[]{
                89, 93, 195, 82, 85, 105, 98, 145, 96, 130, 102, 96,
                117, 160, 142, 172, 132, 89, 93, 195, 82, 85, 105, 98,
                145, 96, 130, 1022, 96, 117, 160, 142, 172, 132
        };
        GraphView graph=(GraphView)findViewById(R.id.graph);
        series=new LineGraphSeries<DataPoint>();
        for(int i=0;i<y_coordinates.length;i++)
        {

            series.appendData(new DataPoint(x,y_coordinates[i]),true,34);
            x=x+0.25;

        }
        graph.addSeries(series);

        graph.getViewport().setMinX(10.00);
        graph.getViewport().setMaxX(20.00);
        graph.getViewport().setMinY(10.00);
        graph.getViewport().setMaxY(1100.00);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

    }

}
