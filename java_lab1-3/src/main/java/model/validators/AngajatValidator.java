package model.validators;

import model.Angajat;
import repository.ValidationException;
import repository.Validator;

public class AngajatValidator implements Validator<Angajat> {
    @Override
    public void validate(Angajat entity) throws ValidationException {
        StringBuffer msg=new StringBuffer();
         if (entity.getUsername()=="")
            msg.append("Username nu poate fi null: " );
        if (entity.getPass()=="")
            msg.append("Pass nu poate fi null: ");
        if (msg.length()>0)
            throw new ValidationException(msg.toString());
    }
}