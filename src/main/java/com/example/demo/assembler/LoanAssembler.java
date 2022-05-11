package com.example.demo.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.controller.LoanController;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.LoanDTO;
import com.example.demo.model.Loan;

@Component
public class LoanAssembler extends RepresentationModelAssemblerSupport<Loan, LoanDTO> {

    public LoanAssembler() {
        super(LoanController.class, LoanDTO.class);
    }

    @Override
    public LoanDTO toModel(Loan entity) {
        LoanDTO loan = super.createModelWithId(entity.getId(), entity);
        MapperConverter.parse(entity, loan);
        return loan;
    }

}
