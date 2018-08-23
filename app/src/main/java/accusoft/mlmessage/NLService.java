package accusoft.mlmessage;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBSaveExpression;
import com.amazonaws.models.nosql.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Corey on 6/15/2018.
 */

public class NLService extends NotificationListenerService {
    DynamoDBMapper dynamoDBMapper;
    AmazonDynamoDBClient dynamoDBClient;
    private String TAG = "NLService";

    // bind and unbind seems to make it work with Android 6...
    // but is never called with Android 4.4...
    @Override
    public IBinder onBind(Intent mIntent) {
        IBinder mIBinder = super.onBind(mIntent);
        Log.i(TAG, "onBind");
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent mIntent) {
        boolean mOnUnbind = super.onUnbind(mIntent);
        Log.i(TAG, "onUnbind");
        try {
        } catch (Exception e) {
            Log.e(TAG, "Error during unbind", e);
        }
        return mOnUnbind;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AWSMobileClient.getInstance().initialize(this).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        dynamoDBClient = new AmazonDynamoDBAsyncClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        NotificationDO notification = new NotificationDO();
        notification.setBDismissed(false);
        notification.setBSelected(false);
        notification.setNotificationId(sbn.getId());
        notification.setSDetails(sbn.getNotification().toString());
        notification.setSTimeAppear(Long.toString(sbn.getPostTime()));
        notification.setUserId(sbn.getUser().toString());
        notification.setSTimeDisappear("0");
        dynamoDBMapper.save(notification);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn,
                                      NotificationListenerService.RankingMap rankingMap,
                                      int reason) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("userId", new AttributeValue().withS(sbn.getUser().toString()));
        key.put("notificationId", new AttributeValue().withN(Integer.toString(sbn.getId())));

        Map<String, AttributeValue> attributeValues = new HashMap<>();
        attributeValues.put(":dismissed", new AttributeValue().withBOOL(reason == REASON_CANCEL || reason == REASON_CANCEL_ALL));
        attributeValues.put(":selected", new AttributeValue().withBOOL(reason == REASON_CLICK));
        attributeValues.put(":time", new AttributeValue().withS(Long.toString(System.currentTimeMillis())));
        attributeValues.put(":time", new AttributeValue().withS("0"));

        UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName("notification")
                .withKey(key)
                .withUpdateExpression("set bDismissed = :dismissed, bSelected = :selected, sTimeDisappear = :time")
                .withConditionExpression("sTimeDisappear = :zero")
                .withExpressionAttributeValues(attributeValues);
        dynamoDBClient.updateItem(updateItemRequest);
        //DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        //dynamoDBSaveExpression.setConditionalOperator("");
    }
}
