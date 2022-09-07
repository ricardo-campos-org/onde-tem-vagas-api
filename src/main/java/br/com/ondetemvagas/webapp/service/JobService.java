package br.com.ondetemvagas.webapp.service;

import br.com.ondetemvagas.webapp.entity.PortalJob;
import br.com.ondetemvagas.webapp.entity.User;
import br.com.ondetemvagas.webapp.entity.UserJob;
import br.com.ondetemvagas.webapp.repository.PortalJobRepository;
import br.com.ondetemvagas.webapp.repository.UserJobRepository;
import br.com.ondetemvagas.webapp.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Setter
@NoArgsConstructor
public class JobService {

  private PortalJobRepository portalJobRepository;

  private UserRepository userRepository;

  private UserJobRepository userJobRepository;

  @Autowired
  public JobService(PortalJobRepository portalJobRepository, UserRepository userRepository,
      UserJobRepository userJobRepository) {
    this.portalJobRepository = portalJobRepository;
    this.userRepository = userRepository;
    this.userJobRepository = userJobRepository;
  }

  public Map<User, Set<PortalJob>> processNewJobs() {
    List<PortalJob> portalJobList = portalJobRepository.findAllByProcessed(Boolean.FALSE);
    log.info("{} jobs to check!", portalJobList.size());

    if (portalJobList.isEmpty()) {
      return new HashMap<>();
    }

    Map<User, Set<PortalJob>> userSetMap = new HashMap<>();

    List<User> users = userRepository.findAllByEnabled(Boolean.TRUE);

    for (User user : users) {
      Set<PortalJob> userJobs = findUserJobs(portalJobList, user);
      if (!userJobs.isEmpty()) {
        userSetMap.put(user, userJobs);
      }
    }

    return userSetMap;
  }

  private Set<PortalJob> findUserJobs(List<PortalJob> portalJobList, User user) {
    List<String> terms = Arrays.asList(user.getTerms().split(","));
    Set<PortalJob> portalJobSet = new HashSet<>();

    // Collect all user jobs that are already saved
    LocalDateTime last3Days = LocalDateTime.now().minusDays(3L);
    List<UserJob> userSavedJobs = userJobRepository.findAllBySavedStartingAt(last3Days);
    userSavedJobs = userSavedJobs.stream()
        .filter((rec) -> rec.getUserId().equals(user.getId()))
        .collect(Collectors.toList());
    List<Long> userJobIds = userSavedJobs.stream()
        .map(UserJob::getPortalJobId).toList();

    for (PortalJob portalJob : portalJobList) {
      Boolean match = check(portalJob.getJobTitle(), terms);
      if (match && !userJobIds.contains(portalJob.getId())) {
        portalJobSet.add(portalJob);
      }
    }

    return portalJobSet;
  }

  private Boolean check(String jobName, List<String> terms) {
    for (String term : terms) {
      if (jobName.toLowerCase().contains(term.toLowerCase())) {
        return Boolean.TRUE;
      }
    }

    return Boolean.FALSE;
  }
}
