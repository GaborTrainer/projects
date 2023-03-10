package gym;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class TrainerRepository {

    private EntityManagerFactory factory;

    public TrainerRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void deleteTrainersGym(long trainerId) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Trainer trainer = manager.find(Trainer.class, trainerId);
            trainer.setGym(null);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

    public Trainer findTrainerById(long trainerId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.find(Trainer.class, trainerId);
        } finally {
            manager.close();
        }
    }

    public Athlete saveAthleteToTrainer(long trainerId, long athleteId) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Trainer trainer = manager.find(Trainer.class, trainerId);
            Athlete athlete = manager.find(Athlete.class, athleteId);
            trainer.addAthlete(athlete);
            manager.getTransaction().commit();
            return athlete;
        } finally {
            manager.close();
        }
    }

    public Trainer removeAthleteFromTrainer(long athleteId, long trainerId) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Athlete athlete = manager.createQuery(
                            "select a " +
                                    "from Athlete a " +
                                    "where id=:athleteId"
                            , Athlete.class)
                    .setParameter("athleteId", athleteId)
                    .getSingleResult();
            Trainer trainer = manager.find(Trainer.class, trainerId);
            trainer.removeAthlete(athlete);
            manager.getTransaction().commit();
            return trainer;
        } finally {
            manager.close();
        }
    }

    public void updateTrainerWithPersistedAthlete(long trainerId, long athleteId) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Trainer trainer = manager.find(Trainer.class, trainerId);
            Athlete athlete = manager.find(Athlete.class, athleteId);
            trainer.addAthlete(athlete);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }
}
