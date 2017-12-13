package com.famnet.famnet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Navigation bar
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.navigation_account);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_chat:
                        Intent intent1 = new Intent(AccountActivity.this, ChatActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tasks:
                        Intent intent2 = new Intent(AccountActivity.this, TasksActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_personal_task:
                        Intent intent3 = new Intent(AccountActivity.this, PersonalTasksActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_account:
                        break;
                }
                return false;
            }
        });
    }


    ///CLICK ON LogOut Button
    public void onClickLogOut(View view){


    }
    ///CLICK ON Profile Edit Edit Button
    public void onClickProfileImageEdit(View view){


    }

    ///CLICK ON Name Edit Button
    public void onClickNameEdit(View view){


    }

    ///CLICK ON GROUP Edit Button
    public void onClickGroupEdit(View view){


    }
    ///CLICK ON ROLE Edit Button
    public void onClickRoleEdit(View view){


    }
    ///CLICK ON Email Edit Button
    public void onClickEmailEdit(View view){


    }

    ///CLICK ON Password Edit Button
    public void onClickPasswordEdit(View view){


    }

    ///CLICK ON Password Edit Button
    public void onClickAddMember(View view){


    }
}
