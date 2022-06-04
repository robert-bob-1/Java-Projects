package bll.validators;

/**
 * Validator interface
 */
public interface Validator<T> {

	public void validate(T t);
}
