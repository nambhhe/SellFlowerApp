package sellFlower.app.repository;

import android.content.Context;
import java.util.List;
import java.util.ArrayList;

import sellFlower.app.dao.FlowerDao;
import sellFlower.app.database.DatabaseInstance;
import sellFlower.app.model.Flower;

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

    public Flower getFlowerById(int id) {
        return flowerDao.getFlowerById(id);
    }

    public void updateFlower(Flower flower) {
        flowerDao.updateFlower(flower);
    }

    public void deleteFlower(Flower flower) {
        flowerDao.deleteFlower(flower);
    }

    public List<Flower> getFlowersByCategory(int categoryId) {
        return flowerDao.getFlowersByCategory(categoryId);
    }

    public List<Flower> searchFlowers(String query) {
        return flowerDao.searchFlowers("%" + query + "%");
    }

    public void updateStock(int flowerId, int newStock) {
        flowerDao.updateStock(flowerId, newStock);
    }


}