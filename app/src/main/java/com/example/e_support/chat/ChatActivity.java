package com.example.e_support.chat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_support.DB.MyHelper;
import com.example.e_support.R;

import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;

public class ChatActivity extends AppCompatActivity {

    private ListView listView;
    private EditText input;
    private Button send;
    private ArrayList<Msg> msgList = new ArrayList<>();
    private Adapter adapter = new Adapter();
    public static final String EXTRA_FROM_USERNAME = "from_username";
    public static final String EXTRA_FROM_APPKEY = "from_appkey";
    public static final String EXTRA_MSGID = "msgid";
    private Message message;
    private String user,appkey;
    private int msgid;
    private MyHelper helper = new MyHelper(this,"Box.db",null,1);
    private String id;
    private ImageButton btnMenu,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getDataFromDB();
        initView();
        //initData();
    }
    private void getDataFromDB(){
        Intent intentSpecialist = getIntent();
        id = intentSpecialist.getStringExtra("id");
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("messagebox",null,"id=?",new String[]{id},null,null,null);
        if(cursor.moveToFirst()){
            do{
                Msg msg = new Msg();
                msg.msg = cursor.getString(cursor.getColumnIndex("msg"));
                msg.type = cursor.getInt(cursor.getColumnIndex("type"));
                msgList.add(msg);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }
    private void initView(){
        listView = findViewById(R.id.msg_list_view);
        input = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        btnMenu = findViewById(R.id.btn_menu);
        btnMenu.setVisibility(View.INVISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = id;
                String text = input.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(text)) {
                    String appkey = "be03d664b7d4378df7d6dc6d";
                    Message message = JMessageClient.createSingleTextMessage(name,appkey,text);
                    MessageSendingOptions options = new MessageSendingOptions();
                    options.setRetainOffline(true);
                    options.setShowNotification(true);
                    JMessageClient.sendMessage(message,options);
                    Msg tempMsg = new Msg();
                    tempMsg.msg = text;
                    tempMsg.type = Msg.SEND;
                    tempMsg.id = id;
                    msgList.add(tempMsg);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("msg",text);
                    values.put("type",Msg.SEND);
                    values.put("id",id);
                    db.insert("messagebox",null,values);
                    adapter.notifyDataSetChanged();
                }
                input.setText("");
            }
        });
        listView.setAdapter(adapter);
    }

    public void initData(){
        Conversation conversation;
        conversation = JMessageClient.getSingleConversation(user, appkey);
        if (conversation == null) {
            return;
        }
        message = conversation.getMessage(msgid);

        TextContent textContent = (TextContent) message.getContent();
        Msg msg = new Msg();
        msg.type = Msg.RECEIVE;
        msg.msg = textContent.getText();
        msgList.add(msg);

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("msg",textContent.getText());
        values.put("type",Msg.RECEIVE);
        values.put("id",id);
        db.insert("messagebox",null,values);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        user = intent.getStringExtra(EXTRA_FROM_USERNAME);
        appkey = intent.getStringExtra(EXTRA_FROM_APPKEY);
        msgid = intent.getIntExtra(EXTRA_MSGID, 0);
    }

    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return msgList.size();
        }

        @Override
        public Object getItem(int position) {
            return msgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(),R.layout.chat_list_item,null);
                holder.leftLinear = convertView.findViewById(R.id.left_layout);
                holder.rightLinear = convertView.findViewById(R.id.right_Layout);
                holder.leftmsg = convertView.findViewById(R.id.left_msg);
                holder.rightmsg = convertView.findViewById(R.id.right_msg);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            if(msgList.get(position).type == Msg.SEND){
                holder.leftLinear.setVisibility(View.GONE);
                holder.rightLinear.setVisibility(View.VISIBLE);
                holder.rightmsg.setText(msgList.get(position).msg);
            }else{
                holder.leftLinear.setVisibility(View.VISIBLE);
                holder.rightLinear.setVisibility(View.GONE);
                holder.leftmsg.setText(msgList.get(position).msg);
            }
            return convertView;
        }
    }
    class ViewHolder{
        private LinearLayout leftLinear,rightLinear;
        private TextView leftmsg,rightmsg;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
