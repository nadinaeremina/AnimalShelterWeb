package org.top.animalshelterwebapp.guardian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuardianService {
    @Autowired
    private final GuardianRepository guardianRepository;

    public GuardianService(GuardianRepository guardianRepository) {
        this.guardianRepository = guardianRepository;
    }

    public List<Guardian> listAll() {
        return (List<Guardian>) guardianRepository.findAll();
    }

    public Page<Guardian> findPaginated(Integer pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        PageRequest pageable= PageRequest.of(pageNumber - 1, pageSize, sort);
        return this.guardianRepository.findAll(pageable);
    }
}