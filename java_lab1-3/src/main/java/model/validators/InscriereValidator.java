package model.validators;

import model.Inscriere;
import repository.ValidationException;
import repository.Validator;

public class InscriereValidator implements Validator<Inscriere> {
    @Override
    public void validate(Inscriere entity) throws ValidationException {
        StringBuffer msg=new StringBuffer();
//        if (entity.getCapacMotor()<100)
//            msg.append("Capac motor trebuie sa aiba o val mai mare: " + entity.getCapacMotor());

        if (msg.length()>0)
            throw new ValidationException(msg.toString());
    }
}
