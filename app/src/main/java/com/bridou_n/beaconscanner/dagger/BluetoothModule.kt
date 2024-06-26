package com.bridou_n.beaconscanner.dagger

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.bridou_n.beaconscanner.utils.PreferencesHelper
import dagger.Module
import dagger.Provides
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import javax.inject.Singleton



@Module
object BluetoothModule {

    const val RUUVI_LAYOUT = "m:0-2=0499,i:4-19,i:20-21,i:22-23,p:24-24" // TBD
    const val IBEACON_LAYOUT = "m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"
    const val ALTBEACON_LAYOUT = BeaconParser.ALTBEACON_LAYOUT
    const val EDDYSTONE_UID_LAYOUT = BeaconParser.EDDYSTONE_UID_LAYOUT
    const val EDDYSTONE_URL_LAYOUT = BeaconParser.EDDYSTONE_URL_LAYOUT
    const val EDDYSTONE_TLM_LAYOUT = BeaconParser.EDDYSTONE_TLM_LAYOUT

    @JvmStatic @Provides @Singleton
    fun providesBluetoothAdapter() = BluetoothAdapter.getDefaultAdapter()

    @JvmStatic @Provides // Not a Singleton
    fun providesBeaconManager(ctx: Context, prefs: PreferencesHelper): BeaconManager {
        val instance = BeaconManager.getInstanceForApplication(ctx)

        // Sets the delay between each scans according to the settings
        instance.foregroundBetweenScanPeriod = prefs.getScanDelay()

        // Add all the beacon types we want to discover
        instance.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_LAYOUT))
        instance.beaconParsers.add(BeaconParser().setBeaconLayout(EDDYSTONE_UID_LAYOUT))
        instance.beaconParsers.add(BeaconParser().setBeaconLayout(EDDYSTONE_URL_LAYOUT))
        instance.beaconParsers.add(BeaconParser().setBeaconLayout(EDDYSTONE_TLM_LAYOUT))

        return instance
    }
}
