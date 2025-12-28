package com.sap.jamsession.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sap.jamsession.exceptions.ResourceNotFoundException;
import com.sap.jamsession.model.JamSession;
import com.sap.jamsession.repositories.JamSessionRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JamSessionServiceTest {
  @Mock JamSessionRepository jamSessionRepository;
  @Mock UserService userService;
  @InjectMocks @Spy JamSessionService jamSessionService;

  @Test
  void shouldDeleteSession_WhenUserIsOwner() throws IllegalAccessException {
    int jamSessionId = 1;
    JamSession mockSession = new JamSession();
    doReturn(mockSession).when(jamSessionService).getJamSessionAndCheckIfUserOwnsIt(jamSessionId);

    jamSessionService.deleteJamSession(jamSessionId);

    verify(jamSessionRepository, times(1)).delete(mockSession);
  }

  @Test
  void shouldThrowIllegalAccessException_WhenUserIsNotOwner() throws IllegalAccessException {
    int jamSessionId = 2;

    doThrow(IllegalAccessException.class)
        .when(jamSessionService)
        .getJamSessionAndCheckIfUserOwnsIt(jamSessionId);
    assertThrows(
        IllegalAccessException.class, () -> jamSessionService.deleteJamSession(jamSessionId));
    verify(jamSessionRepository, never()).delete(any());
  }

  @Test
  void shouldThrowResourceNotFoundException_WhenSessionDoesNotExist()
      throws IllegalAccessException {
    int jamSessionId = 1;

    doThrow(ResourceNotFoundException.class)
        .when(jamSessionService)
        .getJamSessionAndCheckIfUserOwnsIt(jamSessionId);
    assertThrows(
        ResourceNotFoundException.class, () -> jamSessionService.deleteJamSession(jamSessionId));
    verify(jamSessionRepository, never()).delete(any());
  }

  @Test
  @Disabled
  void createJamSession() {}

  @Test
  @Disabled
  void editJamSession() {}

  @Test
  @Disabled
  void getJamSessionById() {}

  @Test
  @Disabled
  void getAllJamSessions() {}

  @Test
  @Disabled
  void deleteJamSession() {}
}
