
package com.sap.jamsession.dtos;

import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.model.Location;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJamSessionDto {
  @NotNull(message = "Start time cannot be null.")
  @Future(message = "Start time must be in the future.")
  private LocalDateTime startTime;

  @NotNull(message = "Location cannot be null.")
  private Location location;

  @NotNull
  @Size(min = 1)
  private List<Instrument> requiredInstruments; // Zmiana na List

  @NotNull(message = "musicGenreId cannot be null.")
  @Min(value = 1, message = "musicGenreId must be grater than 0")
  private Integer musicGenreId;
}