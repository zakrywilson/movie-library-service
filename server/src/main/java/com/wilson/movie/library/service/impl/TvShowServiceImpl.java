package com.wilson.movie.library.service.impl;

import com.wilson.movie.library.domain.TvShowEntity;
import com.wilson.movie.library.repository.TvShowRepository;
import com.wilson.movie.library.service.TvShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TV show service.
 *
 * @author Zach Wilson
 */
@Service
@Transactional
@Slf4j
public class TvShowServiceImpl implements TvShowService {

    private final TvShowRepository repository;

    @Autowired
    public TvShowServiceImpl(TvShowRepository repository) {
        this.repository = repository;
    }

    @Override
    @Nonnull
    public TvShowEntity create(@Nonnull TvShowEntity tvShow) {
        log.trace("Creating TV show: {}", tvShow);

        TvShowEntity savedEntity = repository.save(tvShow);
        log.debug("Persisted new TV show: {}", savedEntity);

        return savedEntity;
    }

    @Override
    @Nonnull
    public Optional<TvShowEntity> getById(@Nonnull Integer id) {
        log.trace("Getting TV show by ID: {}", id);

        return Optional.ofNullable(repository.findOne(id));
    }

    @Override
    @Nonnull
    public Optional<TvShowEntity> getByTitle(@Nonnull String title) {
        log.trace("Getting TV show by title: \"{}\"", title);

        return Optional.ofNullable(repository.findByTitle(title));
    }

    @Override
    @Nonnull
    public Collection<TvShowEntity> getAllByDateAired(@Nonnull LocalDate dateAired) {
        log.trace("Getting all TV shows by date aired: {}", dateAired);

        return repository.findAllByDateAired(dateAired);
    }

    @Override
    @Nonnull
    public Collection<TvShowEntity> getAllByDateAired(@Nonnull Integer dateAiredEpochDay) {
        LocalDate dateAired = LocalDate.ofEpochDay(dateAiredEpochDay);

        log.trace("Getting all TV shows by date aired: {} ({})", dateAiredEpochDay, dateAired);

        return repository.findAllByDateAired(dateAired);
    }

    @Override
    @Nonnull
    public Collection<TvShowEntity> getAllByNetwork(@Nonnull String network) {
        log.trace("Getting all TV shows by network: \"{}\"", network);

        return repository.findAllByNetwork(network);
    }

    @Override
    @Nonnull
    public Collection<TvShowEntity> getAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Getting all TV shows by IDs: {}", ids);

        return repository.findAllById(ids);
    }

    @Override
    @Nonnull
    public Collection<TvShowEntity> getAll() {
        log.trace("Getting all TV shows");

        return repository.findAll();
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<TvShowEntity> update(@Nonnull Integer id, @Nonnull TvShowEntity tvShow) {
        log.trace("Updating TV show with ID {}: {}", id, tvShow);

        Optional<TvShowEntity> optionalSavedEntity = Optional.empty();

        Optional<TvShowEntity> optionalCurrentEntity = Optional.ofNullable(repository.findOne(id));
        if (optionalCurrentEntity.isPresent()) {
            TvShowEntity entity = optionalCurrentEntity.get();
            entity.setTitle(tvShow.getTitle());
            entity.setDateAired(tvShow.getDateAired());
            entity.setNetwork(tvShow.getNetwork());
            entity.setRatedId(tvShow.getRatedId());
            entity.setPlotSummary(tvShow.getPlotSummary());
            entity.setSeries(tvShow.isSeries());

            optionalSavedEntity = Optional.ofNullable(repository.save(entity));

            optionalSavedEntity.ifPresent((m) -> log.debug("Persisted update to TV show with ID {}: {}", id, m));
        } else {
            log.debug("No TV show exists with ID {}. Nothing to update", id);
        }

        return optionalSavedEntity;
    }

    @Override
    @Nonnull
    @Transactional
    public Optional<Integer> deleteById(@Nonnull Integer id) {
        log.trace("Deleting TV show by ID: {}", id);

        Optional<TvShowEntity> optionalEntity = Optional.ofNullable(repository.findOne(id));

        if (optionalEntity.isPresent()) {
            repository.delete(id);
            log.debug("Deleted TV show with ID {}: {}", id, optionalEntity.get());
            return Optional.of(id);
        } else {
            log.debug("No TV show exists with ID {}. Nothing to delete", id);
            return Optional.empty();
        }
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAllWithIds(@Nonnull Collection<Integer> ids) {
        log.trace("Deleting all TV shows by IDs: {}", ids);

        Collection<TvShowEntity> entities = repository.findAllById(ids);
        repository.deleteInBatch(entities);

        Collection<Integer> deletedEntityIds = entities.stream().map(TvShowEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted {} TV shows with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted {} TV shows", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    @Nonnull
    @Transactional
    public Collection<Integer> deleteAll() {
        log.trace("Deleting all TV shows");

        List<TvShowEntity> entities = repository.findAll();
        repository.deleteInBatch(entities);

        Set<Integer> deletedEntityIds = entities.stream().map(TvShowEntity::getId).collect(Collectors.toSet());
        if (log.isDebugEnabled()) {
            if (deletedEntityIds.size() <= 25) {
                log.debug("Deleted all {} TV shows with IDs: {}", deletedEntityIds.size(), deletedEntityIds);
            } else {
                log.debug("Deleted all {} TV shows", deletedEntityIds.size());
            }
        }

        return deletedEntityIds;
    }

    @Override
    public boolean exists(@Nonnull Integer id) {
        log.trace("Checking if TV show exists with ID: {}", id);

        return repository.exists(id);
    }

    @Override
    public boolean exists(@Nonnull String title) {
        log.trace("Checking if TV show exists with title: \"{}\"", title);

        return Optional.ofNullable(repository.findByTitle(title)).isPresent();
    }

}
