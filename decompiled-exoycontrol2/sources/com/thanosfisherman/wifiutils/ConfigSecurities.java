package com.thanosfisherman.wifiutils;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import com.thanosfisherman.wifiutils.utils.SSIDUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes2.dex */
final class ConfigSecurities {
    static final String SECURITY_EAP = "EAP";
    static final String SECURITY_NONE = "OPEN";
    static final String SECURITY_PSK = "PSK";
    static final String SECURITY_WEP = "WEP";

    ConfigSecurities() {
    }

    static void setupSecurity(WifiConfiguration wifiConfiguration, String str, String str2) {
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        WifiUtils.wifiLog("Setting up security " + str);
        str.hashCode();
        switch (str) {
            case "EAP":
                wifiConfiguration.allowedProtocols.set(1);
                wifiConfiguration.allowedProtocols.set(0);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedKeyManagement.set(2);
                wifiConfiguration.allowedKeyManagement.set(3);
                wifiConfiguration.preSharedKey = SSIDUtils.convertToQuotedString(str2);
                break;
            case "PSK":
                wifiConfiguration.allowedProtocols.set(1);
                wifiConfiguration.allowedProtocols.set(0);
                wifiConfiguration.allowedKeyManagement.set(1);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedGroupCiphers.set(2);
                if (str2.matches("[0-9A-Fa-f]{64}")) {
                    wifiConfiguration.preSharedKey = str2;
                    break;
                } else {
                    wifiConfiguration.preSharedKey = SSIDUtils.convertToQuotedString(str2);
                    break;
                }
                break;
            case "WEP":
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.allowedProtocols.set(1);
                wifiConfiguration.allowedProtocols.set(0);
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedAuthAlgorithms.set(1);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                if (ConnectorUtils.isHexWepKey(str2)) {
                    wifiConfiguration.wepKeys[0] = str2;
                    break;
                } else {
                    wifiConfiguration.wepKeys[0] = SSIDUtils.convertToQuotedString(str2);
                    break;
                }
                break;
            case "OPEN":
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.allowedProtocols.set(1);
                wifiConfiguration.allowedProtocols.set(0);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedGroupCiphers.set(2);
                break;
            default:
                WifiUtils.wifiLog("Invalid security type: " + str);
                break;
        }
    }

    static void setupSecurityHidden(WifiConfiguration wifiConfiguration, String str, String str2) {
        wifiConfiguration.hiddenSSID = true;
        setupSecurity(wifiConfiguration, str, str2);
    }

    static void setupWifiNetworkSpecifierSecurities(WifiNetworkSpecifier.Builder builder, String str, String str2) {
        WifiUtils.wifiLog("Setting up WifiNetworkSpecifier.Builder " + str);
        str.hashCode();
        switch (str) {
            case "EAP":
            case "PSK":
                builder.setWpa2Passphrase(str2);
                break;
            case "WEP":
            case "OPEN":
                break;
            default:
                WifiUtils.wifiLog("Invalid security type: " + str);
                break;
        }
    }

    static WifiConfiguration getWifiConfiguration(WifiManager wifiManager, WifiConfiguration wifiConfiguration) {
        String str = wifiConfiguration.SSID;
        if (str != null && !str.isEmpty()) {
            String str2 = wifiConfiguration.BSSID != null ? wifiConfiguration.BSSID : "";
            String security = getSecurity(wifiConfiguration);
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            if (configuredNetworks == null) {
                WifiUtils.wifiLog("NULL configs");
                return null;
            }
            for (WifiConfiguration wifiConfiguration2 : configuredNetworks) {
                if (str2.equals(wifiConfiguration2.BSSID) || str.equals(wifiConfiguration2.SSID)) {
                    if (Objects.equals(security, getSecurity(wifiConfiguration2))) {
                        return wifiConfiguration2;
                    }
                }
            }
            WifiUtils.wifiLog("Couldn't find " + str);
        }
        return null;
    }

    static WifiConfiguration getWifiConfiguration(WifiManager wifiManager, String str) {
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        String str2 = "\"" + str + Typography.quote;
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (wifiConfiguration.SSID.equals(str2)) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    static WifiConfiguration getWifiConfiguration(WifiManager wifiManager, ScanResult scanResult) {
        if (scanResult.BSSID != null && scanResult.SSID != null && !scanResult.SSID.isEmpty() && !scanResult.BSSID.isEmpty()) {
            String strConvertToQuotedString = SSIDUtils.convertToQuotedString(scanResult.SSID);
            String str = scanResult.BSSID;
            String security = getSecurity(scanResult);
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            if (configuredNetworks == null) {
                return null;
            }
            for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                if (str.equals(wifiConfiguration.BSSID) || strConvertToQuotedString.equals(wifiConfiguration.SSID)) {
                    if (Objects.equals(security, getSecurity(wifiConfiguration))) {
                        return wifiConfiguration;
                    }
                }
            }
        }
        return null;
    }

    static String getSecurity(WifiConfiguration wifiConfiguration) {
        ArrayList arrayList = new ArrayList();
        boolean z = wifiConfiguration.allowedKeyManagement.get(0);
        String str = SECURITY_NONE;
        if (z) {
            if (wifiConfiguration.wepKeys[0] != null) {
                str = SECURITY_WEP;
            }
            arrayList.add(str);
        }
        if (wifiConfiguration.allowedKeyManagement.get(2) || wifiConfiguration.allowedKeyManagement.get(3)) {
            str = SECURITY_EAP;
            arrayList.add(SECURITY_EAP);
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            str = SECURITY_PSK;
            arrayList.add(SECURITY_PSK);
        }
        WifiUtils.wifiLog("Got Security Via WifiConfiguration " + arrayList);
        return str;
    }

    static String getSecurity(ScanResult scanResult) {
        String str = scanResult.capabilities;
        String str2 = SECURITY_WEP;
        if (!str.contains(SECURITY_WEP)) {
            str2 = SECURITY_NONE;
        }
        if (scanResult.capabilities.contains(SECURITY_PSK)) {
            str2 = SECURITY_PSK;
        }
        if (scanResult.capabilities.contains(SECURITY_EAP)) {
            str2 = SECURITY_EAP;
        }
        WifiUtils.wifiLog("ScanResult capabilities " + scanResult.capabilities);
        WifiUtils.wifiLog("Got security via ScanResult ".concat(str2));
        return str2;
    }

    static String getSecurity(String str) {
        String str2 = SECURITY_WEP;
        if (!str.contains(SECURITY_WEP)) {
            str2 = SECURITY_NONE;
        }
        if (str.contains(SECURITY_PSK)) {
            str2 = SECURITY_PSK;
        }
        return str.contains(SECURITY_EAP) ? SECURITY_EAP : str2;
    }

    public static String getSecurityPrettyPlusWps(ScanResult scanResult) {
        if (scanResult == null) {
            return "";
        }
        String security = getSecurity(scanResult);
        if (!scanResult.capabilities.contains("WPS")) {
            return security;
        }
        return security + ", WPS";
    }
}
