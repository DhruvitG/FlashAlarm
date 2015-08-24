package com.flashalarm.dhruvit.flashalarm;

import android.provider.BaseColumns;

public final class AlarmReaderContract {
    public AlarmReaderContract(){}

    public static abstract class AlarmEntry implements BaseColumns{
        public static final String TABLE_NAME = "alarms";
        public static final String COLUMN_NAME_ALARM_ID = "_id";
        public static final String COLUMN_NAME_HOUR = "hour";
        public static final String COLUMN_NAME_MINUTE = "minute";
        public static final String COLUMN_NAME_IS_REPEATING = "is_repeating";
        public static final String COLUMN_NAME_IS_ON = "is_on";
    }
}
