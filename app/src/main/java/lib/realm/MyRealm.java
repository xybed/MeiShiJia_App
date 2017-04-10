package lib.realm;

import com.mumu.meishijia.model.im.ContactsRealmModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * realm的配置类
 * Created by Administrator on 2017/4/7.
 */

public class MyRealm {
    private static MyRealm myRealm;
    private RealmConfiguration myConfig;
    private long version = 1;

    private MyRealm(){}

    public static MyRealm getInstance(){
        if(myRealm == null)
            myRealm = new MyRealm();
        return myRealm;
    }

    public void init(){
        myConfig = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .schemaVersion(version)
                .build();
    }

    public RealmConfiguration getMyConfig() {
        return myConfig;
    }

}
