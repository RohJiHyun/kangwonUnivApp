package com.kangwon.a356.kangwonunivapp.database;

import com.google.gson.JsonParser;
import com.kangwon.a356.kangwonunivapp.dataprocess.JSONParser;
import com.kangwon.a356.kangwonunivapp.network.NetworkExecuteMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 메시지 객체이다. 내부에서 사용할 메시지와 외부에서 사용할 메시지를 구분하지 않고 사용 가능하게 해준다.
 * 모든 객체들은 이 메시지 객체를 사용하여 통신한다.
 * @see com.kangwon.a356.kangwonunivapp.database.datainterface.Message
 * @see com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter
 */
public class MessageObject {
    public static final String TYPE = "type";

    public static final String LOGIN_TYPE= "login";
    public static final String SIGNIN_TYPE ="signin";
    public static final String STUDENT_TIMETABLE_TYPE= "studenttimetable";
    public static final String INSTRUCTOR_TIME_TABLE_TYPE= "instructortimetable";;

    public static final String REQUEST_UPDATE_STUDNET_LIST = "studentlist";
    public static final String REQUEST_UPDATE_STUDNET_TABLE = "studenttimetable";
    public static final String REQUEST_UPDATE_INSTRUCTOR_LIST = "instructorlist";
    public static final String REQUEST_UPDATE_INSTRUCTOR_TABLE = "instructortimetable";

    public static final int PROCESS_MANAGER =0;
    public static final int DATA_MANAGER =1;
    public static final int NETWORK_MANAGER =2;


    public static final int NOT_REQUEST_QUERY = 0 ;
    public static final int REQUEST_QUERY = 1;

    private String type;
    private int MessageQueueType; //프로세스 매니저가 사용하는 메시지 큐의 위치
    private int requsetStatus=0; //requestStatus는 저장을 하고 데이터를 이용해서 쿼리문을 원한다는 유무이다. ProcessManager에서 쓰임.
    private ArrayList<LinkedHashMap> message;
    private NetworkExecuteMessage tag=null;
    public MessageObject(LinkedHashMap[] msg)
    {
        message =new ArrayList<>();
        for(int i = 0; i<msg.length; i++)
            message.add(msg[i]);
        type=(String)msg[0].get("type");
    }

    public MessageObject(ArrayList<LinkedHashMap> msg)
    {
        message = msg;
       type =(String)msg.get(0).get("type");

    }

    public MessageObject(JSONObject msg)
    {
        this( JSONParser.toArrayList(msg));
    }

    public MessageObject(JSONArray msg)
    {
        this(JSONParser.toArrayList(msg));
    }

    public MessageObject(LinkedHashMap msg)
    {
        message =new ArrayList<>();
        message.add(msg);
        type =(String)msg.get("type");
    }


    /**
     *
     * @return 메시지 타입을 리턴한다.
     */
    public String getType()
    {
        return type;
    }

    public void setNEM(NetworkExecuteMessage tag){this.tag = tag;}
    public NetworkExecuteMessage getNEM(){return tag;}

    /**
     * 메시지를 ArrayList를 반환해준다.
     * @return
     */
    public ArrayList<LinkedHashMap> getMessage() {
        return message;
    }

    public void setRequestStatus(int requsetStatus)
    {
        this.requsetStatus = requsetStatus;
    }
    public int getRequsetStatus()
    {
        return requsetStatus;
    }

    /**
     * 메시지를 GET 방식의 인자로 만들어준다.
     * @return GET타입의 인자. URL에 붙여서 보낼 수 있다.
     */
    public String toGETMessage()
    {
        String getMsg="/"+type+"?";
        LinkedHashMap msg = message.get(0);
        Iterator iter = msg.keySet().iterator();

        iter.next(); //첫 번째 거는 버린다. 이미 type에 존재하기 때문.
        while(iter.hasNext())
        {
            String key =(String)iter.next();
            getMsg += key + "=" + msg.get(key)+"&";
        }
        return getMsg;
    }

    public int getMessageQueueType()
    {return MessageQueueType;}
    public void setMessageQueueType(int messageQueueType)
    {
        this.MessageQueueType = messageQueueType;
    }


    /**
     * 메시지가 어떤 타입의 메시지인지 확인해 준다.
     * @param obj Message객체를 받을시 같은 타입인지 확인해준다.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(this.type.equals(obj))
            return true;
        return false;
    }
}
