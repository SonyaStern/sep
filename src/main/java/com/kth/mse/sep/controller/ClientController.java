package com.kth.mse.sep.controller;

import com.kth.mse.sep.converter.ModelConvertor;
import com.kth.mse.sep.dto.ClientDto;
import com.kth.mse.sep.model.Client;
import com.kth.mse.sep.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    private final ModelConvertor convertor;

    @GetMapping("/{clientId}")
    public ClientDto getClientById(@PathVariable("clientId") Long clientId) {
        return clientRepository.findById(clientId).map(convertor::convertEmployeeEntityToDto).orElse(null);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ClientDto createClient(@RequestBody ClientDto client) {
        Client clientToSave = clientRepository.save(convertor.convertEmployeeDtoToEntity(client));
        return convertor.convertEmployeeEntityToDto(clientToSave);
    }

    @PatchMapping("/{clientId}")
    public ClientDto patchClient(@PathVariable("clientId") Long clientId, @RequestBody ClientDto client) {
        Optional<Client> clientToUpdate = clientRepository.findById(clientId);
        return clientToUpdate.map(value ->
                convertor.convertEmployeeEntityToDto(clientRepository.save(convertor.enrichClientEntity(value, client)))).orElse(null);
    }
}
