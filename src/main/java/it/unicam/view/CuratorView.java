package it.unicam.view;

import it.unicam.controller.Controller;

public class CuratorView extends AuthorizedContributorView{

    public CuratorView(Controller controller) {
        super(controller);
    }

    @Override
    public void viewPoi() {
        super.viewPoi();
    }

    @Override
    public void viewItinerary() {
        super.viewItinerary();
    }

    @Override
    public void insertPOI() {
        super.insertPOI();
    }

    @Override
    public void confirmPOI() {
        super.confirmPOI();
    }
}
