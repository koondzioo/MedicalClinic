package com.app.medicalclient.controllers;

import com.app.medicalclient.dto.VisitDto;
import com.app.medicalclient.service.VisitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitController {

    private VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public VisitDto addVisit(@RequestBody VisitDto visitDto) {
        return visitService.addVisit(visitDto);
    }

    @GetMapping("/byPatient/{id}")
    public List<VisitDto> showVisitsByPatient(@PathVariable Long id) {
        return visitService.getVisitsByPatient(id);
    }


    @GetMapping("/{id}")
    public VisitDto showVisitById(@PathVariable Long id) {
        return visitService.getVisitById(id);
    }


    @DeleteMapping("/{id}")
    public VisitDto deleteVisit(@PathVariable Long id) {
        return visitService.deleteVisit(id);
    }

}
