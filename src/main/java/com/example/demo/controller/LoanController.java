package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.assembler.LoanAssembler;
import com.example.demo.converter.MapperConverter;
import com.example.demo.dto.LoanDTO;
import com.example.demo.dto.LoanReturnDTO;
import com.example.demo.model.Loan;
import com.example.demo.service.LoanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;
    private final PagedResourcesAssembler<Loan> pagedAssembler;
    private final LoanAssembler loanAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDTO create(@RequestBody @Valid LoanDTO dto) {
        Loan loan = MapperConverter.parse(dto, Loan.class);
        return this.loanAssembler.toModel(this.loanService.create(loan, dto.getUserId(), dto.getIsbn()));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LoanDTO returnLoan(@PathVariable Long id, @RequestBody @Valid LoanReturnDTO dto) {
        return this.loanAssembler.toModel(this.loanService.update(id, dto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<LoanDTO> findAll(Pageable pageable) {
        Page<Loan> loans = this.loanService.findAll(pageable);
        return this.pagedAssembler.toModel(loans, this.loanAssembler);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LoanDTO findById(@PathVariable Long id) {
        return this.loanAssembler.toModel(this.loanService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.loanService.delete(id);
    }

}
