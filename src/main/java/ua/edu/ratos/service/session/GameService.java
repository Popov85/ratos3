package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.Result;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.dao.entity.game.Bonus;
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.dao.entity.game.Wins;
import ua.edu.ratos.dao.repository.ResultRepository;
import ua.edu.ratos.dao.repository.game.*;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.dto.out.game.*;
import ua.edu.ratos.service.transformer.entity_to_dto.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameService {

    @PersistenceContext
    private EntityManager em;

    private AppProperties appProperties;

    private ResultRepository resultRepository;

    private GameRepository gameRepository;

    private WeekRepository weekRepository;

    private BonusRepository bonusRepository;

    private WinsRepository winsRepository;

    private GamerRepository gamerRepository;

    private GamerDtoTransformer gamerDtoTransformer;

    private WinnerDtoTransformer winnerDtoTransformer;

    private WeeklyGamerDtoTransformer weeklyGamerDtoTransformer;

    private TotalTopDtoTransformer totalTopDtoTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    public void setWeekRepository(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    @Autowired
    public void setBonusRepository(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @Autowired
    public void setWinsRepository(WinsRepository winsRepository) {
        this.winsRepository = winsRepository;
    }

    @Autowired
    public void setGamerRepository(GamerRepository gamerRepository) {
        this.gamerRepository = gamerRepository;
    }

    @Autowired
    public void setGamerDtoTransformer(GamerDtoTransformer gamerDtoTransformer) {
        this.gamerDtoTransformer = gamerDtoTransformer;
    }

    @Autowired
    public void setWinnerDtoTransformer(WinnerDtoTransformer winnerDtoTransformer) {
        this.winnerDtoTransformer = winnerDtoTransformer;
    }

    @Autowired
    public void setWeeklyGamerDtoTransformer(WeeklyGamerDtoTransformer weeklyGamerDtoTransformer) {
        this.weeklyGamerDtoTransformer = weeklyGamerDtoTransformer;
    }

    @Autowired
    public void setTotalTopDtoTransformer(TotalTopDtoTransformer totalTopDtoTransformer) {
        this.totalTopDtoTransformer = totalTopDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Transactional
    public Optional<Integer> checkAndGetPoints(@NonNull final Long userId, @NonNull final Long schemeId, double percent) {
        if (!isGameOn()) return Optional.of(null);
        if (!isEnoughPercents(percent)) return Optional.of(0);
        if (!isFromFirstAttempt(userId, schemeId)) return Optional.of(0);
        // ok, calculate points
        AppProperties.Game props = appProperties.getGame();
        if (percent >= props.getLow_boundary_from() && percent < props.getLow_boundary_to())
            return Optional.of(props.getLow_boundary_points());
        if (percent >= props.getMiddle_boundary_from() && percent < props.getMiddle_boundary_to())
            return Optional.of(props.getMiddle_boundary_points());
        if (percent >= props.getHigh_boundary_from() && percent <= props.getHigh_boundary_to())
            return Optional.of(props.getHigh_boundary_points());
        throw new RuntimeException("Bad percent, cannot decide gamification points");
    }

    private boolean isGameOn() {
        AppProperties.Game props = appProperties.getGame();
        if (!props.isGame_on()) {
            log.debug("Gamification mode is turned off");
            return false;
        }
        return true;
    }

    private boolean isEnoughPercents(double percent) {
        AppProperties.Game props = appProperties.getGame();
        if (percent < props.getLow_boundary_from()) {
            log.debug("Not enough percents = {}", percent);
            return false;
        }
        return true;
    }

    private boolean isFromFirstAttempt(@NonNull final Long userId, @NonNull final Long schemeId) {
        // if user has already taken this scheme previously - no points can be granted!
        Slice<Result> results = resultRepository.findFirstByUserIdAndSchemeId(userId, schemeId, PageRequest.of(0, 1));
        if (results.hasContent()) {
            log.debug("Not from the first attempt, userId = {}, schemeId = {}", userId, schemeId);
            return false;
        }
        return true;
    }


    @Transactional
    public void savePoints(@NonNull final ResultDomain resultDomain) {
        Long userId = resultDomain.getUser().getUserId();
        if (!isGameOn()) return;
        if (!resultDomain.hasPoints()) {
            resetWeeklyStrike(userId);
            return;
        }
        Integer points = resultDomain.getPoints().get();
        long timeSpent = resultDomain.getTimeSpent();

        Optional<Week> week = weekRepository.findById(userId);

        if (!week.isPresent()) {
            Week w = new Week();
            w.setWeekPoints(points);
            w.setWeekStrike(1);
            w.setWeekTimeSpent(timeSpent);
            w.setStud(em.getReference(Student.class, userId));
            weekRepository.save(w);
            createOrUpdateGamePoints(userId, points);
        } else {
            // update week
            Week w = week.get();
            int weekPoints = w.getWeekPoints();
            int weekStrike = w.getWeekStrike();
            int weekBonuses = w.getWeekBonuses();
            long weekTimeSpent = w.getWeekTimeSpent();
            // check for weekly strike
            if (weekStrike>=2) {
                AppProperties.Game props = appProperties.getGame();
                int bonus = props.getBonus_size();
                // update/create game bonus, add bonus entry
                createOrUpdateGameBonuses(userId, bonus);
                createBonus(userId, bonus);
                // reset counter
                w.setWeekBonuses(weekBonuses + bonus);
                w.setWeekStrike(0);
            } else {
                w.setWeekStrike(++weekStrike);
            }
            w.setWeekPoints(weekPoints+points);
            w.setWeekTimeSpent(weekTimeSpent+timeSpent);
            createOrUpdateGamePoints(userId, points);
        }
    }

    private void resetWeeklyStrike(@NonNull final Long userId) {
        Optional<Week> week = weekRepository.findById(userId);
        if (week.isPresent()) {
            Week w = week.get();
            w.setWeekStrike(0);
        }
    }

    private void createOrUpdateGamePoints(@NonNull final Long userId, @NonNull final Integer points) {
        Optional<Game> game = gameRepository.findById(userId);
        if (!game.isPresent()) {
            Game g = new Game();
            g.setTotalPoints(points);
            g.setStud(em.getReference(Student.class, userId));
            gameRepository.save(g);
        } else { // just update value
            Game g = game.get();
            int totalPoints = g.getTotalPoints();
            g.setTotalPoints(totalPoints+points);
        }
    }

    private void createOrUpdateGameBonuses(@NonNull final Long userId, @NonNull final Integer bonus) {
        Optional<Game> game = gameRepository.findById(userId);
        if (!game.isPresent()) {
            Game g = new Game();
            g.setTotalBonuses(bonus);
            g.setStud(em.getReference(Student.class, userId));
            gameRepository.save(g);
        } else { // just update value
            Game g = game.get();
            int totalBonuses = g.getTotalBonuses();
            g.setTotalBonuses(totalBonuses+bonus);
        }
    }

    public void createBonus(@NonNull final Long userId, @NonNull final Integer bonus) {
        Bonus b = new Bonus();
        b.setBonus(bonus);
        b.setStudent(em.getReference(Student.class, userId));
        b.setWhenGranted(LocalDateTime.now());
        bonusRepository.save(b);
    }

    //-----------------------------------------------Quartz regular weekly job------------------------------------------

    /**
     * Launched automatically by quartz scheduler, mainly at the end of a week.
     * Winners of the week for an organisation are considered those n% of students who participated in the competition and:
     * gained the most points, then gained the most bonuses, and then spent the least time.
     */
    @Transactional
    public void calculateAndSaveWeeklyWinners() {
        AppProperties.Game props = appProperties.getGame();
        int topWeeklyPercentage = props.getTop_weekly();
        // Get all entries from week table;
        List<Week> all = weekRepository.findAll();
        if (all.isEmpty()) {
            log.debug("No gamers found! Hence, no winners of the week!");
            return;
        }
        // For each organisation calculate top-n% of students and obtain a list of winners
        Map<Long, List<Week>> allWeeklyByOrg = all.stream()
                .collect(Collectors.groupingBy(w -> w.getStud().getOrganisation().getOrgId()));
        for (List<Week> orgList : allWeeklyByOrg.values()) {
            if (orgList.isEmpty()) break;
            orgList.sort(Comparator.comparing(Week::getWeekPoints, Comparator.reverseOrder())
                    .thenComparing(Week::getWeekBonuses, Comparator.reverseOrder())
                    .thenComparing(Week::getWeekTimeSpent, Comparator.naturalOrder()));
            int size = orgList.size();
            // get n% of the sorted list
            int topNSize = size * topWeeklyPercentage / 100;
            List<Week> winners = orgList.subList(0, topNSize);
            // borderline case, if percentage of winners is too small,
            // the first person in the sorted list is the winner
            if (winners.isEmpty()) winners = Arrays.asList(orgList.get(0));
            // create Wins, and update totalWins in Game
            saveWeeklyWinners(winners);
        }
        // reset week table
        weekRepository.emptyWeek();
    }

    private void saveWeeklyWinners(@NonNull final List<Week> winners) {
        for (Week winner : winners) {
            Wins wins = new Wins();
            wins.setStudent(em.getReference(Student.class, winner.getWeekId()));
            wins.setWonPoints(winner.getWeekPoints());
            wins.setWonBonuses(winner.getWeekBonuses());
            wins.setWonTimeSpent(winner.getWeekTimeSpent());
            wins.setWonDate(LocalDate.now());
            winsRepository.save(wins);
            // winners for sure have entries in Game table!
            gameRepository.incrementWins(winner.getWeekId());
        }
    }

    //--------------------------------------------------SELECT----------------------------------------------------------

    @Transactional(readOnly = true)
    public GamerOutDto findMyGamingRating() {
        Long studId = securityUtils.getAuthStudId();
        return gamerDtoTransformer.toDto(gamerRepository.findById(studId).get());
    }

    //-------------------------------------------For TOP weekly rating table--------------------------------------------

    @Transactional(readOnly = true)
    public Page<WeeklyGamerOutDto> findWeeklyGamers(@NonNull final Pageable pageable) {
        return weekRepository.findWeeklyGamers(pageable).map(weeklyGamerDtoTransformer::toDto);
    }

    //------------------------------------------For TOP rating students table-------------------------------------------

    @Transactional(readOnly = true)
    public Page<AllTimesGamerOutDto> findBestGamers(@NonNull final Pageable pageable) {
        return gameRepository.findBestGamers(pageable).map(totalTopDtoTransformer::toDto);
    }

    //-------------------------------------------For last week winners table--------------------------------------------
    // sorted by points, bonuses and time spent
    @Transactional(readOnly = true)
    public Page<WinnerOutDto> findLastWeekWinners(@NonNull final Pageable pageable) {
        return winsRepository.findAllWinnersSince(LocalDate.now().minusDays(7), pageable).map(winnerDtoTransformer::toDto);
    }

}
