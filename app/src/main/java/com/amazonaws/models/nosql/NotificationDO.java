package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "mlmessaging-mobilehub-1909623918-Notification")

public class NotificationDO {
    private String _userId;
    private String _appId;
    private Boolean _bDismissed;
    private Boolean _bSelected;
    private int _notificationId;
    private String _sDetails;
    private String _sTimeAppear;
    private String _sTimeDisappear;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "appId")
    public String getAppId() {
        return _appId;
    }

    public void setAppId(final String _appId) {
        this._appId = _appId;
    }
    @DynamoDBAttribute(attributeName = "bDismissed")
    public Boolean getBDismissed() {
        return _bDismissed;
    }

    public void setBDismissed(final Boolean _bDismissed) {
        this._bDismissed = _bDismissed;
    }
    @DynamoDBAttribute(attributeName = "bSelected")
    public Boolean getBSelected() {
        return _bSelected;
    }

    public void setBSelected(final Boolean _bSelected) {
        this._bSelected = _bSelected;
    }
    @DynamoDBAttribute(attributeName = "notificationId")
    public int getNotificationId() {
        return _notificationId;
    }

    public void setNotificationId(final int _notificationId) {
        this._notificationId = _notificationId;
    }
    @DynamoDBAttribute(attributeName = "sDetails")
    public String getSDetails() {
        return _sDetails;
    }

    public void setSDetails(final String _sDetails) {
        this._sDetails = _sDetails;
    }
    @DynamoDBAttribute(attributeName = "sTimeAppear")
    public String getSTimeAppear() {
        return _sTimeAppear;
    }

    public void setSTimeAppear(final String _sTimeAppear) {
        this._sTimeAppear = _sTimeAppear;
    }
    @DynamoDBAttribute(attributeName = "sTimeDisappear")
    public String getSTimeDisappear() {
        return _sTimeDisappear;
    }

    public void setSTimeDisappear(final String _sTimeDisappear) {
        this._sTimeDisappear = _sTimeDisappear;
    }

}
