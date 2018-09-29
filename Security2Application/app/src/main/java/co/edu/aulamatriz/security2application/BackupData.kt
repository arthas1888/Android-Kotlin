package co.edu.aulamatriz.security2application

import android.app.backup.BackupAgentHelper
import android.app.backup.SharedPreferencesBackupHelper
import android.os.ParcelFileDescriptor
import android.app.backup.BackupDataInput
import android.app.backup.BackupDataOutput
import android.util.Log
import java.io.IOException


class BackupData : BackupAgentHelper()  {
    companion object {
        val PREFS_TEST = "testprefs"
        val MY_PREFS_BACKUP_KEY = "myprefs"
    }

    override fun onCreate() {
        super.onCreate()
        var helper = SharedPreferencesBackupHelper(this, PREFS_TEST)
        addHelper(MY_PREFS_BACKUP_KEY, helper)
    }

}