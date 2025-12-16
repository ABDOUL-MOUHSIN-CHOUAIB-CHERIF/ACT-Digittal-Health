package com.clinic.controllers;

import com.clinic.utils.setNavigator;
public class DashboardController {
    public void GOTORECEPTIONIST(){
        setNavigator.goToPage("/FXML/receptionist.fxml" , "RECEPTIONIST DASHBOARD");
    }
}
