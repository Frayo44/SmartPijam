package pijama.com.pijamasan.Fragments;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pijama.com.pijamasan.R;
import pijama.com.pijamasan.Server.PijamServerr;
import pijama.com.pijamasan.Utils.PijamServer;

/**
 * Created by Yoav on 11/30/2015.
 */
public class DataFragment extends Fragment {

    TextView tvSkinTemp;
    TextView tvAirTemp;
    Handler handler = new Handler();
    private final int FIVE_SECONDS = 1000;
    PijamServer pijamServer;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.datafragment, container, false);

        tvSkinTemp = (TextView) v.findViewById(R.id.tvCurSkinTemp);
        tvAirTemp = (TextView) v.findViewById(R.id.tvCurAirTemp);

        pijamServer = new PijamServer();


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIR();
    }

    public void handleIR() {
        handler.postDelayed(new Runnable() {
            public void run() {

                pijamServer.getSkinTemprature();
                int currSkinTemp =  pijamServer.getCurrSkinTemp2();
                final int currAirTemp =  pijamServer.getCurrAirTemp();

                tvAirTemp.setText("Current AirCon Temp: " + currAirTemp);
                tvSkinTemp.setText("Current Skin Temp: " + currSkinTemp);

                handler.postDelayed(this, FIVE_SECONDS);
            }
        }, FIVE_SECONDS);
    }
}
