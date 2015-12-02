package pijama.com.pijamasan.Fragments;

import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pijama.com.pijamasan.Activities.MorningActivity;
import pijama.com.pijamasan.Activities.SleepingActivity;
import pijama.com.pijamasan.R;
import pijama.com.pijamasan.Utils.InfraRed;

/**
 * Created by Yoav on 11/30/2015.
 */
public class SleepingFragment extends Fragment {

    InfraRed infraRed;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.sleepingfragment, container, false);

        ImageView bWakeUp = (ImageView) v.findViewById(R.id.ivWakeUp);

        bWakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), MorningActivity.class));
            }
        });

        ConsumerIrManager ir = ((ConsumerIrManager) getActivity().getSystemService("consumer_ir"));
        infraRed = new InfraRed(ir);

        infraRed.ElectraOnOff();

        return v;
    }
}
