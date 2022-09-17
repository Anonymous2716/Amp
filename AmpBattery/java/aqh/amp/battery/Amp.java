package aqh.amp.battery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.BatteryManager;
import android.view.View;
import android.view.WindowManager;
import android.view.Window;
import android.widget.TextView;
import android.content.Context;
import java.lang.Thread;

public class Amp extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Window w = getWindow();
    w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    setContentView(R.layout.lifecycle);
    BatteryManager batteryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
    final  TextView message = (TextView)findViewById(R.id.message);
    Handler hh = new Handler();
    hh.postDelayed(new Runnable(){
     public void run(){
     message.setText(getString(R.string.current , batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)));
    hh.postDelayed(this, 800);
            }
     }, 800);
  }
}



