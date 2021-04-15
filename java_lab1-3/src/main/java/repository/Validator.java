package repository;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}