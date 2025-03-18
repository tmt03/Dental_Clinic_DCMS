package controller.coreController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CoreController {

    private String act;

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        act = req.getParameter("act");
        handleRequest(act);
    }

    public void handleRequest(String act) {
        switch (act) {
            case "":

                break;
            default:
                throw new AssertionError();
        }
    }

}
