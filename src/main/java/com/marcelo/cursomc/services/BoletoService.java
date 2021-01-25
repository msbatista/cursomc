package com.marcelo.cursomc.services;

import com.marcelo.cursomc.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoCompleto(PagamentoComBoleto  pagamentoComBoleto, Date instantePedido) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instantePedido);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
    }
}
