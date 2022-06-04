import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private SimulationSetup setup;
    public SimulationManager manager;

    Controller(SimulationSetup setup) {
        this.setup = setup;
        //... Add listeners to the view.
        setup.submitListener(new SubmitListener());

    }

    class SubmitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                manager=new SimulationManager(setup);
                setup.setVisible(false);
                Thread t=new Thread (manager);
                t.start();
            } catch (Exception exception) {
                setup.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }
}
