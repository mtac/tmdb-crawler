package org.jumpingtree.tmdbcrawler.preferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.jumpingtree.tmdbcrawler.R;
import org.jumpingtree.tmdbcrawler.models.ApiConfigurationObject;
import org.jumpingtree.tmdbcrawler.utilities.TMDBRequestsParserUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class TMDBPreferences {
    private static TMDBPreferences ourInstance = new TMDBPreferences();

    public static TMDBPreferences getInstance() {
        return ourInstance;
    }

    private TMDBPreferences() {

    }

    private ApiConfigurationObject mLastConfiguration;

    //wait 3 days before refreshing configuration from server
    public final int PREF_MIN_DAYS_BETWEEN_CONFIG_CHECKS = 3;
    public final Long PREF_MIN_TIME_BETWEEN_CONFIG_CHECKS = TimeUnit.DAYS.toMillis(PREF_MIN_DAYS_BETWEEN_CONFIG_CHECKS);

    public ApiConfigurationObject getLastConfiguration(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForConfiguration = context.getString(R.string.pref_last_configuration);
        ApiConfigurationObject config = null;
        try {
            config = TMDBRequestsParserUtils.parseJsonConfigResponse(sp.getString(keyForConfiguration, null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return config;
    }

    public void saveLastConfiguration(Context context, String configurationJSON) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        String lastNotificationKey = context.getString(R.string.pref_last_configuration);
        editor.putString(lastNotificationKey, configurationJSON);

        //Save time to avoid checking this too many times
        String lastNotificatioDatenKey = context.getString(R.string.pref_last_config_date);
        editor.putLong(lastNotificatioDatenKey, new Date().getTime());

        editor.apply();
    }

    public Long getLastConfigurationChangeDate(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String keyForConfigurationDate = context.getString(R.string.pref_last_config_date);
        return sp.getLong(keyForConfigurationDate,PREF_MIN_TIME_BETWEEN_CONFIG_CHECKS);
    }

    public ApiConfigurationObject getmLastConfiguration() {
        return mLastConfiguration;
    }

    public void setmLastConfiguration(ApiConfigurationObject mLastConfiguration) {
        this.mLastConfiguration = mLastConfiguration;
    }
}
