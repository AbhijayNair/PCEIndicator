package org.pceindicator.com;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.pceindicator.com.model.ChatMessage;
import org.pceindicator.com.model.NotificationModel;

public class NewsActivity extends AppCompatActivity {


    private FirebaseListAdapter<NotificationModel> adapter;
    private ListView listOfMessages;
    SharedPreferences sp;
    String TAG = "newsActivity";
    String year,branch,division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        sp = getSharedPreferences("USER_DATA",MODE_PRIVATE);
        year = sp.getString("YEAR","All");
        branch = sp.getString("BRANCH","All");
        division = sp.getString("DIVISION","All");
        listOfMessages = (ListView)findViewById(R.id.messagesNoti);
        Query query = FirebaseDatabase.getInstance().getReference().child("NEWS");

        FirebaseListOptions<NotificationModel> options = new FirebaseListOptions.Builder<NotificationModel>()
                .setQuery(query, NotificationModel.class)
                .setLayout(R.layout.push_noti)
                .build();

        adapter = new FirebaseListAdapter<NotificationModel>(options) {
            @Override
            protected void populateView(View v, NotificationModel model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.noti_text);
                TextView messageUser = v.findViewById(R.id.noti_user);
                TextView messageTime = v.findViewById(R.id.noti_time);
                if ((model.getMessageYear().equals(year)) && (model.getMessageBranch().equals(branch)) && (model.getMessageDivision().equals(division))){
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
                }

                else if((model.getMessageYear().equals("All")) && (model.getMessageBranch().equals("All")) && (model.getMessageDivision().equals("All"))){
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
                }
                else if((model.getMessageYear().equals("All")) && (model.getMessageBranch().equals(branch)) && (model.getMessageDivision().equals(division))){
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
                }
                else if((model.getMessageBranch().equals("All"))&&(model.getMessageYear().equals(year)) && (model.getMessageDivision().equals(division))){
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
                }
                else if((model.getMessageDivision().equals("All"))&&(model.getMessageYear().equals(year)) && (model.getMessageBranch().equals(branch))){
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
                }

            }
        };
        adapter.notifyDataSetChanged();
        listOfMessages.setAdapter(adapter);    }
    @Override
    public void onPause() {
        adapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }

}
