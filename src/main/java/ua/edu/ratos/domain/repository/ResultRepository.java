package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.edu.ratos.domain.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
