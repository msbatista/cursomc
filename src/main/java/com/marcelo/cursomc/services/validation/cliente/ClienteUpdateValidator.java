package com.marcelo.cursomc.services.validation.cliente;

import com.marcelo.cursomc.domain.Cliente;
import com.marcelo.cursomc.dto.ClienteDTO;
import com.marcelo.cursomc.repository.ClienteRepository;
import com.marcelo.cursomc.resources.exception.FieldExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteDTO value, ConstraintValidatorContext context) {
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldExceptionMessage> fieldExceptionMessageList = new ArrayList<FieldExceptionMessage>();

        Cliente cliente = clienteRepository.findByEmail(value.getEmail());

        if (cliente != null && !cliente.getId().equals(uriId)) {
            fieldExceptionMessageList.add(new FieldExceptionMessage("email", "This e-mail belongs to another client"));
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
