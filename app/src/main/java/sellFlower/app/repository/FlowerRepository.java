package sellFlower.app.repository;

import android.content.Context;
import sellFlower.app.dao.FlowerDao;
import sellFlower.app.database.DatabaseInstance;
import sellFlower.app.model.Flower;
import java.util.List;

public class FlowerRepository {
    private FlowerDao flowerDao;
    private static FlowerRepository instance;

    private FlowerRepository(Context context) {
        flowerDao = DatabaseInstance.getInstance(context).flowerDao();
    }

    public static FlowerRepository getInstance(Context context) {
        if (instance == null) {
            instance = new FlowerRepository(context);
        }
        return instance;
    }

    public void insertFlower(Flower flower) {
        flowerDao.insertFlower(flower);
    }

    public List<Flower> getAllFlowers() {
        return flowerDao.getAllFlower();
    }

    public List<Flower> getAllFeaturedFlowers() {
        return flowerDao.getAllFeaturedFlowers();
    }

    // Add more methods as needed for other operations
}