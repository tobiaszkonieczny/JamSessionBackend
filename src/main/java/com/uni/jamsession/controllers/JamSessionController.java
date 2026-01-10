package com.sap.jamsession.controllers;

import com.sap.jamsession.dtos.*;
import com.sap.jamsession.exceptions.UnauthorizedException;
import com.sap.jamsession.facade.JamSessionFacade;
import com.sap.jamsession.facade.MessageFacade;
import com.sap.jamsession.model.Message;
import com.sap.jamsession.model.ReactionType;
import com.sap.jamsession.services.JamSessionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/jam")
@AllArgsConstructor
public class JamSessionController {

  private final JamSessionService jamSessionService;
  private final JamSessionFacade jamSessionFacade;
  private final MessageFacade messageFacade;
  @PostMapping("/create")
  public ResponseEntity<?> createJamSession(@Valid @RequestBody CreateJamSessionDto jamSession) {
    var jamSessionId = jamSessionService.createJamSession(jamSession);
    return new ResponseEntity<>(jamSessionId, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findJamSessionById(@PathVariable("id") int id) {
    return ResponseEntity.ok(jamSessionService.getJamSessionById(id));
  }

  @PatchMapping("/edit/{id}")
  public ResponseEntity<?> editJamSession(
      @PathVariable("id") int id, @Valid @RequestBody EditJamSessionDto jamSessionDto)
      throws IllegalAccessException {
    var newJamSession = jamSessionService.editJamSession(jamSessionDto, id);

    return ResponseEntity.ok(newJamSession);
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllJamSessions() {
    return ResponseEntity.ok(jamSessionService.getAllJamSessions());
  }

  @GetMapping("/own/{userId}")
  public ResponseEntity<Set<?>> getAllJamSessionsByUserId(@PathVariable int userId) {
    return ResponseEntity.ok(jamSessionFacade.getAllJamSessionsByUserId(userId));
  }

  @GetMapping("/signed-up/{userId}")
  public ResponseEntity<Set<JamSessionDto>> getAllSignedUpJamSessionsByUserId(@PathVariable int userId) {
    return ResponseEntity.ok(jamSessionFacade.getAllSignedUpJamSessionsByUserId(userId));
  }



  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or @jamSessionService.isOwner(#id)")
  public ResponseEntity<?> deleteJamSession(@PathVariable int id) throws IllegalAccessException {
    jamSessionService.deleteJamSession(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/remove/instrument/{jamSessionId}/{instrumentAndRatingId}")
  public ResponseEntity<?> removeInstrumentFromJamSession(
      @PathVariable int jamSessionId, @PathVariable int instrumentAndRatingId)
      throws IllegalAccessException {
    jamSessionService.deleteInstrumentFromJamSession(jamSessionId, instrumentAndRatingId);
    return ResponseEntity.noContent().build();
  }



    @GetMapping("/comments/{jamSessionId}")
    public ResponseEntity<Set<MessageDto>> getCommentsForJamSession(@PathVariable int jamSessionId) {
        return ResponseEntity.ok(messageFacade.getCommentsForJamSession(jamSessionId));
    }

    @DeleteMapping("/comments/{jamSessionId}/{messageId}")
    public ResponseEntity<?> deleteCommentFromJamSession(@PathVariable int messageId) throws UnauthorizedException{
        jamSessionFacade.deleteCommentFromJamSession(messageId);
        return ResponseEntity.ok("Comment deleted successfully.");
    }

    @PostMapping("/comments/{jamSessionId}")
    public ResponseEntity<MessageDto> addComment(
            @PathVariable int jamSessionId,
            @Valid @RequestPart("data") MessageCreateDto data,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestParam(required = false) Integer parentId
    ) {
      return ResponseEntity.ok(jamSessionFacade.addCommentToJamSession(jamSessionId, data, image, parentId));
    }

    @PostMapping("/comments/{messageId}/react")
    public ResponseEntity<MessageDto> reactToMessage(
            @PathVariable int messageId,
            @RequestParam ReactionType type
    ) {
      MessageDto message = messageFacade.reactToMessage(messageId, type);
      return ResponseEntity.ok(message);
    }

    @PostMapping("/join/{jamSessionId}")
    public ResponseEntity<JamSessionDto> addUserToJamSession(
            @PathVariable int jamSessionId,
            @Valid @RequestBody AddUserToJamSessionDto dto) {
        var updatedJamSession = jamSessionFacade.addUserToJamSession(jamSessionId, dto.instrumentAndRatingId());
        return ResponseEntity.ok(updatedJamSession);
    }

    @DeleteMapping("/leave/{jamSessionId}/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @jamSessionService.isOwner(#jamSessionId) or @userService.isCurrentUser(#userId)")
    public ResponseEntity<?> removeUserFromJamSession(
            @PathVariable int jamSessionId,
            @PathVariable int userId) {
      System.out.println("Removing user " + userId + " from jam session CONTROLLER" + jamSessionId);
        jamSessionFacade.removeUserFromJamSession(jamSessionId, userId);
        return ResponseEntity.ok("User removed from jam session successfully.");
    }
}
