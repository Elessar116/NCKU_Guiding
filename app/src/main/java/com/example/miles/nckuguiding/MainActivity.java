package com.example.miles.nckuguiding;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miles.nckuguiding.Classroom.SearchClassroom;
import com.example.miles.nckuguiding.GL.MyGLSurfaceView;
import com.example.miles.nckuguiding.LoaderNCalculater.VectorCal;
import com.example.miles.nckuguiding.Models.AllCampusData;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MyGLSurfaceView myGLSurfaceView;
    private RelativeLayout glViewLayout;

    private TextView building_text;
    private TextView level_text;
    private TextView room_text;
    private TextView campus_text;

    private SearchClassroom searchClassroom;
    private String qu;

    private AllCampusData app;
    public static boolean stop_info = false;
    public static boolean navi = false;
    public static int navi_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = new AllCampusData(MainActivity.this);
        myGLSurfaceView = new MyGLSurfaceView(MainActivity.this);
        setContentView(R.layout.activity_main);

        //drawer init
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        campus_text = (TextView) findViewById(R.id.campus_text);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AllCampusData.gpsTracker.canGetLocation()) {

                    AllCampusData.gpsTracker.getLocation();

                    myGLSurfaceView.setTranslationIdentity();
                    myGLSurfaceView.setRotationIdendity();
                    float[] temp = VectorCal.getMapPosition(AllCampusData.gpsTracker.getLatitude(), AllCampusData.gpsTracker.getLongitude());
                    myGLSurfaceView.setUserPosition(temp);
                    if (AllCampusData.test.inside_this(temp)) {
                        String str = AllCampusData.test.get_nearest(temp);
                        campus_text.setText("自強校區, " + str);
                    }
                    if (AllCampusData.ck.inside_this(temp)) {
                        campus_text.setText("成功校區");
                    }
                    if (AllCampusData.sl.inside_this(temp)) {
                        campus_text.setText("勝利校區");
                    }

                } else {
                    AllCampusData.gpsTracker.showSettingsAlert();
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //gl layout
        glViewLayout = (RelativeLayout) findViewById(R.id.GL_view);
        glViewLayout.addView(myGLSurfaceView);

        //infos
        building_text = (TextView) findViewById(R.id.building_text);
        level_text = (TextView) findViewById(R.id.level_text);
        room_text = (TextView) findViewById(R.id.room_text);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            building_text.setText("");
            level_text.setText("");
            room_text.setText("");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_arfunc) {
            Intent intent = new Intent(MainActivity.this, ARMAPActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_class) {
            searchClassroom = new SearchClassroom(MainActivity.this);
            final View search_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.class_search_dlg, null);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("輸入教室號碼；")
                    .setView(search_view)
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) search_view.findViewById(R.id.download_ip_text);
                            CheckBox checkBox = (CheckBox) search_view.findViewById(R.id.checkBox);
                            String[] ans;
                            qu = editText.getText().toString();
                            if (checkBox.isChecked()) {
                                ans = searchClassroom.getRoomInfobyClassroomName(qu);
                            } else {
                                ans = searchClassroom.getRoomInfo(qu);
                            }
                            building_text.setText(ans[0]);
                            level_text.setText(ans[1] + "樓");
                            room_text.setText(ans[2]);
                        }
                    })
                    .show();

        } else if (id == R.id.nav_park) {
            stop_info = !stop_info;
        } else if (id == R.id.nav_navigate) {
            final View search_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.class_search_dlg, null);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("輸入位置：")
                    .setView(search_view)
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) search_view.findViewById(R.id.download_ip_text);
                            qu = editText.getText().toString();
                            for(int i = 0; i < AllCampusData.test.model_name.length; i++){
                                if(qu.compareTo(AllCampusData.test.model_name[i]) == 0){
                                    navi = true;
                                    navi_num = i;
                                    break;
                                }
                            }

                        }
                    })
                    .show();
        } else if (id == R.id.nav_bug) {
            final View search_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.bug_report_dlg, null);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("回報錯誤:")
                    .setView(search_view)
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) search_view.findViewById(R.id.report_text);
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("plain/text");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"chaos88218@gmail.com"});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "成境巡遊錯誤回報");
                            intent.putExtra(Intent.EXTRA_TEXT, editText.getText());
                            startActivity(Intent.createChooser(intent, "Send...."));
                        }
                    })
                    .show();
        } else if (id == R.id.nav_info) {
            final View search_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.app_infromation, null);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("軟體資訊:")
                    .setView(search_view)
                    .setPositiveButton("確認", null)
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
