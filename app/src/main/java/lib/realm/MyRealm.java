package lib.realm;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import lib.utils.MD5Util;

/**
 * realm的配置类
 * Created by Administrator on 2017/4/7.
 */

public class MyRealm {
    private static MyRealm myRealm;
    private RealmConfiguration myConfig;
    private long version = 1;
    private String KEY = "meishijia";

    private MyRealm(){}

    public static MyRealm getInstance(){
        if(myRealm == null)
            myRealm = new MyRealm();
        return myRealm;
    }

    public void init(){
        /*
        当版本变更时，realm会抛出异常RealmMigrationNeededException，
        此时会执行migration里的代码来处理异常，在realm的配置中需配置migration
         */
        RealmMigration migration = new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
//                RealmSchema schema = realm.getSchema();
//                if(oldVersion == 1){
//                    schema.get("ContactsRealmModel")
//                            .addField("principle_id", int.class);
//                    oldVersion++;
//                }
            }
        };
        myConfig = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .encryptionKey((MD5Util.MD5(KEY)+MD5Util.MD5(KEY)).getBytes())//这里的密钥需要是64位的byte[]
                .schemaVersion(version)
                .migration(migration)
                .build();
    }

    public RealmConfiguration getMyConfig(){
        return myConfig;
    }
}
