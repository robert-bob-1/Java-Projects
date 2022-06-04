package start;

import bll.ClientBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;
import presentation.Controller;
import presentation.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test Class
 */
public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
	public static <T> T getSelected(T t) {
		System.out.println(t.getClass() + "\n" + t);
		return t;
	}

	public static void main(String[] args){
		View view = new View();
		Controller controller = new Controller(view);
	}

}
