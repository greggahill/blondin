package com.greggahill.blondin.repository;

import com.greggahill.blondin.model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
}
