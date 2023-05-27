package com.eupc.appsalud.repositorio;

import com.eupc.appsalud.entidades.CentroSalud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCentroSalud extends JpaRepository<CentroSalud, Long> {
}
