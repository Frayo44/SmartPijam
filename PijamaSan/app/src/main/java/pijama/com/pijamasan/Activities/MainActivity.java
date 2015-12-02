package pijama.com.pijamasan.Activities;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import pijama.com.pijamasan.R;
import pijama.com.pijamasan.Server.PijamServerr;


public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;
    ImageView bConnect;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        bConnect = (ImageView) findViewById(R.id.connect);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoAnimation();

            }
        });

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            makeToast("Device is not supported");
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    private void makeToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void DoAnimation()
    {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                PijamServerr ps = new PijamServerr();
                ps.sendConnected();
                startActivity(new Intent(getApplicationContext(), SleepingActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        bConnect.startAnimation(animation);
    }


}
