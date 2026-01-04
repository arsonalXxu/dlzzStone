package com.ipaas.monitoringplstformsys.common.session;


import com.ipaas.monitoringplstformsys.common.entity.UserProfile;


public class MpaasSession {
    private static ThreadLocal<UserProfile> session = new ThreadLocal();

    public MpaasSession() {
    }

    public static void setUserProfile(UserProfile userProfile) {
        session.set(userProfile);
    }

    public static UserProfile getUserProfile() {
        return (UserProfile)session.get();
    }

    public static String getCurrentUser() {
        return session.get() == null ? "anonymous" : ((UserProfile)session.get()).getUid();
    }
}