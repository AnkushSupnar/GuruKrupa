package com.ankush.view;

import java.util.ResourceBundle;

public enum FxmlView {

    LOGIN {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/home/Login.fxml";
        }
    },
    HOME {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/home/Home.fxml";
        }
    },
    CUSTOMER {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/create/AddCustomer.fxml";
        }
    },
    BANK {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/create/AddBank.fxml";
        }
    },
    PURCHASE {
        @Override
        String getTitle() {
            return "Purchase Invoice";
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/transaction/PurchaseInvoice.fxml";
        }
    },
    BILLING {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/transaction/BillingFrame.fxml";
        }
    },
    RATE {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/transaction/SetRate.fxml";
        }
    },
    DASHBOARD {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/dashboard/Dashboard.fxml";
        }
    };
    abstract String getTitle();
    public abstract String getFxmlFile();
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
