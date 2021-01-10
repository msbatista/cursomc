package com.marcelo.cursomc.services.validation.cliente;

import com.marcelo.cursomc.domain.dto.ClienteNewDTO;
import com.marcelo.cursomc.domain.enums.TipoCliente;
import com.marcelo.cursomc.resources.exception.FieldExceptionMessage;
import com.marcelo.cursomc.services.validation.cliente.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Override
    public void initialize(ClienteInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
        List<FieldExceptionMessage> fieldExceptionMessageList = new ArrayList<FieldExceptionMessage>();

        if (value.getTipo() == TipoCliente.PESSOA_FISICA.getCodigo() && !BR.CpfCnpj.isValidCpf(value.getCpfOuCnpj())) {
            fieldExceptionMessageList.add(new FieldExceptionMessage("cpfOuCnpj", "CPF is not Valid!"));
        }

        if (value.getTipo() == TipoCliente.PESSOA_JURIDICA.getCodigo() && !BR.CpfCnpj.isValidCnpj(value.getCpfOuCnpj())) {
            fieldExceptionMessageList.add(new FieldExceptionMessage("cpfOuCnpj", "CNPJ is not Valid!"));
        }

        for (FieldExceptionMessage fieldExceptionMessage: fieldExceptionMessageList) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldExceptionMessage.getMessage())
                    .addPropertyNode(fieldExceptionMessage.getFieldName())
                    .addConstraintViolation();
        }

        return fieldExceptionMessageList.isEmpty();
    }
}
