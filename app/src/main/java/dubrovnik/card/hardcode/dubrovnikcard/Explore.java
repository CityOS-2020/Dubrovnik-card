package dubrovnik.card.hardcode.dubrovnikcard;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;


public class Explore extends Activity implements BeaconManager.RangingListener, Constants{

    BeaconManager beaconManager = Utils.beaconManager;
    LandmarkDialog lmd = new LandmarkDialog();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        beaconManager.setRangingListener(this);
        getActionBar().setElevation(0);
        try {
            beaconManager.startRanging(KNEZ_DVOR_LANDMARKS);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_explore, menu);
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
            LandmarkDialog lmd = new LandmarkDialog();
            lmd.show(getFragmentManager(), "tagName");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBeaconsDiscovered(Region region, List<Beacon> list) {

        for(Beacon bc : list){
            if(bc.getMinor() == SOME_ART_MINOR && bc.getRssi() > -65 && lmd.getDialog() == null){
                lmd.show(getFragmentManager(), "tagName");
            }
        }
    }
}
