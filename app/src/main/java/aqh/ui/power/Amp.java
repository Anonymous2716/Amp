package aqh.ui.power;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.BatteryManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Context;

public class Amp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, 512);

        setContentView(R.layout.lifecycle);
        
        BatteryManager batteryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);

        final TextView message = findViewById(R.id.message);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                Intent batteryIntent = getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                long microamp = batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int batteryPercentage = (level * 100) / scale;
                double batteryTemperature = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10.f;
                double voltage = (double) batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) / 1000;
                double watt = ((double) microamp * voltage) / 1000000;
                message.setText(getString(R.string.current
                                             , microamp
                                             , voltage
                                             , watt
                                             , batteryPercentage
                                             , batteryTemperature
                                             , batteryHealth(batteryIntent)
                                             , status(batteryIntent)
                                             , plugged(batteryIntent)));
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private String batteryHealth(Intent batteryIntent) {
        int health = batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        
        String batteryHealth;
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                batteryHealth = "COLD";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                batteryHealth = "DEAD";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                batteryHealth = "GOOD";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                batteryHealth = "OVERHEAT";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                batteryHealth = "OVER_VOLTAGE";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                batteryHealth = "UNKNOWN";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                batteryHealth = "UNSPECIFIED_FAILURE";
                break;
            default:
                batteryHealth = Integer.toString(health);
        }

        return batteryHealth;
    }

    private String plugged(Intent batteryIntent) {
        int pluggedInt = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        String batteryPlugged;
        switch (pluggedInt) {
            case 0:
                batteryPlugged = "UNPLUGGED";
                break;
            case BatteryManager.BATTERY_PLUGGED_AC:
                batteryPlugged = "PLUGGED_AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                batteryPlugged = "PLUGGED_USB";
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                batteryPlugged = "PLUGGED_WIRELESS";
                break;
            default:
                batteryPlugged = "PLUGGED_" + pluggedInt;
        }
        return batteryPlugged;
    }

    private String status(Intent batteryIntent) {
        String batteryStatusString;
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                batteryStatusString = "CHARGING";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                batteryStatusString = "DISCHARGING";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                batteryStatusString = "FULL";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                batteryStatusString = "NOT_CHARGING";
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                batteryStatusString = "UNKNOWN";
                break;
            default:
                batteryStatusString = "INVALID";
        }
        return batteryStatusString;
    }
}
