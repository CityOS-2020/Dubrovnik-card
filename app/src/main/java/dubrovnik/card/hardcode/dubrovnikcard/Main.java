package dubrovnik.card.hardcode.dubrovnikcard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;


public class Main extends Activity implements BeaconManager.MonitoringListener, View.OnClickListener, Constants{

    private final String TAG = "Monitoring: ";
    private Button exploreBtn;
    private Button mapBtn;
    private Button buyBtn;
    private Button visitUsBtn;

    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redLight)));
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.redDark));

        exploreBtn = (Button)findViewById(R.id.exploreMain);
        exploreBtn.setOnClickListener(this);
        mapBtn = (Button)findViewById(R.id.mapMain);
        mapBtn.setOnClickListener(this);
        buyBtn = (Button)findViewById(R.id.buyMain);
        buyBtn.setOnClickListener(this);
        visitUsBtn = (Button)findViewById(R.id.visitMain);
        visitUsBtn.setOnClickListener(this);

        Utils.beaconManager = new BeaconManager(this);
        Utils.beaconManager.setBackgroundScanPeriod(5000, 0);
        Utils.beaconManager.setMonitoringListener(this);

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

    protected void onStart() {
        super.onStart();
        Utils.beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    Utils.beaconManager.startMonitoring(DUBROVNIK_LANDMARKS);
                } catch (RemoteException e) {
                    Log.d(TAG, "Error while starting monitoring");
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onEnteredRegion(Region region, List<Beacon> list) {

        Intent notificationIntent = new Intent(getApplicationContext(), RedeemTicket.class);

        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent)
                        .setSmallIcon(R.drawable.ic_landmark_notif)
                        .setContentTitle("Dubrovnik Card")
                        .setContentText("You are near Knezev Dvor, use your card.");

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(001, mBuilder.build());

    }

    @Override
    public void onExitedRegion(Region region) {
        Toast.makeText(this, "Exited region", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == exploreBtn.getId()){
            Intent exploreIntent = new Intent(this, Explore.class);
            startActivity(exploreIntent);
        }

        else if(id == mapBtn.getId()){
            Toast.makeText(this, "Map clicked!", Toast.LENGTH_SHORT).show();
        }

        else if (id == buyBtn.getId()) {
            Toast.makeText(this, "Buy clicked!", Toast.LENGTH_SHORT).show();
        }

        else if (id == visitUsBtn.getId()) {
            String url = "http://dubrovnikcard.com/";
            Intent web = new Intent(Intent.ACTION_VIEW);
            web.setData(Uri.parse(url));
            startActivity(web);
        }
    }
}
