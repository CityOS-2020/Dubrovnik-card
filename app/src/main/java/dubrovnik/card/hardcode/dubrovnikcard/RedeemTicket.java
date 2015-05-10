package dubrovnik.card.hardcode.dubrovnikcard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;


public class RedeemTicket extends Activity implements BeaconManager.RangingListener, Constants, Button.OnClickListener{

    BeaconManager beaconManager = Utils.beaconManager;
    AlertDialog alertDialog;
    ImageView okButton;
    TextView okToEnter;
    TextView getClose;
    Button exploreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_ticket);
        getActionBar().setElevation(0);
        okButton = (ImageView) findViewById(R.id.imageView);
        getClose = (TextView) findViewById(R.id.getCloseText);
        exploreBtn = (Button) findViewById(R.id.exploreBtn);
        if (Utils.beaconManager == null){
            Utils.beaconManager = new BeaconManager(this);
        }
        beaconManager.setRangingListener(this);
        exploreBtn.setOnClickListener(this);

        try {
            beaconManager.startRanging(DUBROVNIK_LANDMARKS);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Landmark");
        alertDialog.setMessage("This is some landmark");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_redeem_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBeaconsDiscovered(Region region, List<Beacon> list) {
        for(Beacon bc : list){
            if (region.getIdentifier() == DUBROVNIK_LANDMARKS.getIdentifier() && bc.getRssi() > -65 && !alertDialog.isShowing()){


                getClose.setText("You are free to enter!");
                okButton.setImageResource(R.drawable.checkmark_circle);
                okButton.setElevation(1);
                exploreBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == exploreBtn.getId()){
            Intent intent = new Intent(this, Explore.class);
            startActivity(intent);
        }
    }
}
