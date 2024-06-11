package bodycheck_back.bodycheck.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bodycheck_back.bodycheck.models.entities.measurement.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
   List<Measurement> findAllByCustomerId(Long customerId);
}
