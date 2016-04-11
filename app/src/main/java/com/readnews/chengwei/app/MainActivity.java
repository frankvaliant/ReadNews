package com.readnews.chengwei.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.readnews.chengwei.app.fragment.ListFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment();
    }

    private void loadFragment() {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.content,new ListFragment());
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exitBy2Click();
        }
        return false;
    }

    private boolean isExit = false;

    private void exitBy2Click() {
        if(isExit){
            finish();
            System.exit(0);
        } else {
            isExit = true;
            Toast.makeText(this,"再按一次退出应用",Toast.LENGTH_SHORT).show();
            Timer timer= new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            },2000);
        }
    }
}
