package org.top.animalshelterwebapp.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.animalshelterwebapp.animal.Animal;
import org.top.animalshelterwebapp.animal.AnimalNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> listAll() {
        return (List<Card>) cardRepository.findAll();
    }

    public Card get(Integer id) throws CardNotFoundException {
        Optional<Card> result = cardRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new CardNotFoundException("Could not find any pets with ID" + id);
    }

    public void save(Card card) {
        cardRepository.save(card);
    }
}
