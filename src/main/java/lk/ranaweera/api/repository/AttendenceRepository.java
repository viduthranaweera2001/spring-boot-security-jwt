package lk.ranaweera.api.repository;

import lk.ranaweera.api.model.Attendence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendenceRepository extends JpaRepository<Attendence,Long> {
}
