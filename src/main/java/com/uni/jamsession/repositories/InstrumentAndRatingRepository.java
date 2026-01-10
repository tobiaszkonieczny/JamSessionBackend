package com.sap.jamsession.repositories;

import com.sap.jamsession.model.InstrumentAndRating;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstrumentAndRatingRepository extends JpaRepository<InstrumentAndRating, Integer> {
  List<InstrumentAndRating> findByUserId(int userId);

  public boolean existsByInstrumentIdAndUserId(int instrumentId, int userId);

  public InstrumentAndRating findByInstrumentIdAndUserId(int instrumentId, int userId);

  public void removeByInstrumentIdAndUserId(int instrumentId, int userId);

  public List<InstrumentAndRating> findByInstrumentId(int instrumentId);

  public Set<InstrumentAndRating> findByIdIn(Set<Integer> instrumentAndRatingIds);

  @Query("SELECT ir.instrument.name FROM InstrumentAndRating ir WHERE ir.user.id = :userId")
  List<String> findInstrumentNamesByUserId(Integer userId);

  @Query(
      "SELECT ir FROM InstrumentAndRating ir WHERE ir.instrument.id IN :instrumentId AND ir.user.id = :userId")
  List<InstrumentAndRating> findByUserIdAndInstrumentId(
      @Param("instrumentId") List<Integer> instrumentId, @Param("userId") Integer userId);
}
