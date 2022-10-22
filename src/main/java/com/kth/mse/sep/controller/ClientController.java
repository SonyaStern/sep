package com.kth.mse.sep.controller;

import com.kth.mse.sep.model.Client;
import com.kth.mse.sep.repository.ClientRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    @GetMapping("/{clientId}")
    public Client getClientById(@PathVariable("clientId") Long clientId) {
        return clientRepository.findById(clientId)
                .orElse(null);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PatchMapping("/{clientId}")
    public Client patchClient(@PathVariable("clientId") Long clientId, @RequestBody Client client) {
        Optional<Client> clientToUpdate = clientRepository.findById(clientId);
        if (clientToUpdate.isPresent()) {
            Client updatedClient = clientToUpdate.get()
                    .setFirstName(client.getFirstName())
                    .setLastName(client.getLastName())
                    .setEmail(client.getEmail())
                    .setPhoneNumber(client.getPhoneNumber())
                    .setAddress(client.getAddress());
            return clientRepository.save(updatedClient);
        }
        return null;
    }
}
