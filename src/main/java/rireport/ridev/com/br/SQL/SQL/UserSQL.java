package rireport.ridev.com.br.SQL.SQL;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import rireport.ridev.com.br.Puniments.Puniments;
import rireport.ridev.com.br.User.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserSQL implements SQLResultAdapter<User> {

    @Override
    public User adaptResult(SimpleResultSet resultSet) {
        User us = new User();
        Type type = new TypeToken<ArrayList<Puniments>>(){}.getType();
        ArrayList<Puniments> contactList = new Gson().fromJson((String) resultSet.get("puniments"), type);
        us.setUsername(resultSet.get("username"));
        us.setPuniments(contactList);
        return us;
    }
}
