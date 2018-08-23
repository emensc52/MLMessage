package accusoft.mlmessage;

import android.companion.CompanionDeviceManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
        setContentView(R.layout.activity_main);
        CompanionDeviceManager cdm = getSystemService(CompanionDeviceManager.class);
        if (!cdm.hasNotificationAccess(new ComponentName(getApplicationContext(), NLService.class)))
        {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
            Log.i("MainActivity","hasNotificationAccess NO");
        }
        else
        {
            Log.i("MainActivity","hasNotificationAccess YES");
            Intent mServiceIntent = new Intent(this, NLService.class);
            startService(mServiceIntent);
        }
    }
}
