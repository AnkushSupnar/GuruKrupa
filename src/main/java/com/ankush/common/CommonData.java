package com.ankush.common;

import com.ankush.data.entities.Login;
import com.ankush.view.AlertNotification;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonData {
    public static Login loginUser;

    public static List<String>ITEMNAMES = new ArrayList<>();
    public static List<String>customerNames = new ArrayList<>();
    public static SuggestionProvider<String>PARTYNAMES;
    public CommonData() {
        super();
    }

    public static Login getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(Login loginUser) {
        CommonData.loginUser = loginUser;
    }

    public static List<String> getITEMNAMES() {
        return ITEMNAMES;
    }

    public static void setITEMNAMES(List<String> ITEMNAMES) {
        CommonData.ITEMNAMES.addAll(ITEMNAMES);
    }

    public static void setPARTYNAMES(List<String>names)
    {
        PARTYNAMES = SuggestionProvider.create(names);
    }
    public static SuggestionProvider<String> getPARTYNAMES()
    {
        return PARTYNAMES;
    }
}
