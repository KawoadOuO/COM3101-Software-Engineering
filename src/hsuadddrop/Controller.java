package hsuadddrop;
import hsuadddrop.view.MainUI;
import hsuadddrop.model.Model;
public class Controller {
    Model model;
    MainUI view;
    public void setModel(Model m) {
            this.model = m;        
       }
    public void setView(MainUI v) {
            this.view = v;        
       }

    
}
