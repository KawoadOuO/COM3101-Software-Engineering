package hsuadddrop.model;
import hsuadddrop.Controller;
import hsuadddrop.view.MainUI;

public class Model {
    Model model;
    MainUI view;
    public void setModel(Model m) {
            this.model = m;        
       }
    public void setView(MainUI v) {
            this.view = v;        
       }
}
