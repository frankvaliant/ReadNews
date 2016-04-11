package com.readnews.chengwei.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.readnews.chengwei.app.fragment.ListFragment;
import com.readnews.chengwei.app.fragment.NewsFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ListFragment.OnSelcetTitleListener{
    private FragmentManager fm;
    private FragmentTransaction ft;
    ListFragment listFragemnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment();
    }

    private void loadFragment() {
       listFragemnt = new ListFragment();
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.content,listFragemnt);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            fm.popBackStack();
            if(fm.getBackStackEntryCount()==0){
                exitBy2Click();
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    @Override
    public void selectTitle(int position) {
        Log.i("Main",""+position);
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",position);
        newsFragment.setArguments(bundle);
        ft = fm.beginTransaction();
        ft.hide(listFragemnt);
        ft.add(R.id.content,newsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
