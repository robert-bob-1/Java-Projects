package main.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;

    Controller(Model model, View view) {
        this.model = model;
        this.view  = view;

        //... Add listeners to the view.
        view.addListener(new AddListener());
        view.subListener(new SubListener());
        view.mulListener(new MulListener());
        view.derListener(new DerListener());
        view.intListener(new IntListener());
    }

    class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstPolynomial = "", secondPolynomial = "", finalPolynomial = "";
            try {
                firstPolynomial = view.getFirstPolynomial();
                secondPolynomial = view.getSecondPolynomial();
                finalPolynomial=model.add(firstPolynomial,secondPolynomial);
                view.setFinalPolynomial(finalPolynomial.toString());

            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
                view.reset();
            }
        }
    }

    class SubListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstPolynomial = "", secondPolynomial = "", finalPolynomial = "";
            try {
                firstPolynomial = view.getFirstPolynomial();
                secondPolynomial = view.getSecondPolynomial();
                finalPolynomial=model.sub(firstPolynomial,secondPolynomial);
                view.setFinalPolynomial(finalPolynomial.toString());

            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \n Exceptie detectata: "+ exception);
                view.reset();
            }
        }
    }

    class MulListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstPolynomial = "", secondPolynomial = "", finalPolynomial = "";
            try {
                firstPolynomial = view.getFirstPolynomial();
                secondPolynomial = view.getSecondPolynomial();
                finalPolynomial=model.mul(firstPolynomial,secondPolynomial);
                view.setFinalPolynomial(finalPolynomial.toString());

            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
                view.reset();
            }
        }
    }
    class DerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstPolynomial = "", finalPolynomial = "";
            try {
                firstPolynomial = view.getFirstPolynomial();
                finalPolynomial = model.der(firstPolynomial);
                view.setFinalPolynomial(finalPolynomial.toString());

            } catch (Exception exception) {
                view.reset();
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }
    class IntListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstPolynomial = "", finalPolynomial = "";
            try {
                firstPolynomial = view.getFirstPolynomial();
                finalPolynomial = model.integrate(firstPolynomial);
                view.setFinalPolynomial(finalPolynomial.toString());
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
                view.reset();
            }
        }
    }
}
